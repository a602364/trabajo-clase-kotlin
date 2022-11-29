package net.azarquiel.traductor.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import net.azarquiel.traductor.R
import net.azarquiel.traductor.adapter.CustomAdapter
import net.azarquiel.traductor.databinding.ActivityMainBinding
import net.azarquiel.traductor.model.Palabra
import net.azarquiel.traductor.util.Util
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), CustomAdapter.OnClickRecycler, SearchView.OnQueryTextListener, TextToSpeech.OnInitListener {

    private lateinit var tts : TextToSpeech
    private lateinit var searchView: SearchView
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: CustomAdapter
    private lateinit var espanolSH: SharedPreferences
    private lateinit var inglesSH: SharedPreferences
    private lateinit var palabras: java.util.ArrayList<Palabra>
    private lateinit var ivSpeaker: ImageView
    private var bandera: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        Util.inyecta(this, "espanol.xml")
        Util.inyecta(this, "ingles.xml")
        espanolSH = getSharedPreferences("espanol", Context.MODE_PRIVATE)
        inglesSH = getSharedPreferences("ingles", Context.MODE_PRIVATE)

        initRV()
        getPalabrasIng()
        getPalabrasEsp()
        showPalabras()


        tts = TextToSpeech(this, this)

    }

    private fun showPalabras() {
        adapter.setPalabras(palabras)
    }

    private fun initRV() {
        adapter = CustomAdapter(this, R.layout.row, this)
        binding.cm.rvPalabras.adapter = adapter
        binding.cm.rvPalabras.layoutManager = LinearLayoutManager(this)
    }

    private fun getPalabrasEsp() {
        palabras = ArrayList<Palabra>()
        val espanolAll = espanolSH.all
        for ((key, value) in espanolAll) {
            val ingles = inglesSH.getString(key,"")
            val palabra = Palabra(value.toString(),ingles!!)
            palabras.add(palabra)
        }
    }

    private fun getPalabrasIng() {
        palabras = ArrayList<Palabra>()
        val inglesAll = inglesSH.all
        for ((key, value) in inglesAll) {
            val espanol = espanolSH.getString(key,"")
            val palabra = Palabra(value.toString(),espanol!!)
            palabras.add(palabra)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchItem = menu.findItem(R.id.search)
        searchView = searchItem.actionView as SearchView
        searchView.setQueryHint("Search...")
        searchView.setOnQueryTextListener(this)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.itemEspanol ->  {(Toast.makeText(this, "Idioma cambiado al español", Toast.LENGTH_SHORT).show())
                bandera = false
            }
            R.id.itemIngles ->  {(Toast.makeText(this, "Idioma cambiado al inglés", Toast.LENGTH_SHORT).show())
                bandera = true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun OnClick(v: View){
        val pr = v.tag as Palabra
        val cv = v as CardView

        cv.setOnClickListener{speakOut(pr)}
    }

    override fun onQueryTextChange(query: String): Boolean {
        val original = ArrayList<Palabra>(palabras)
        adapter.setPalabras(original.filter { palabra -> palabra.ingles.contains(query) })
        if (!bandera){
            adapter.setPalabras(original.filter { palabra -> palabra.espanol.contains(query) })
        }

        return bandera
    }

    override fun onQueryTextSubmit(text: String): Boolean {
        return false
    }

    override fun onInit(status: Int) {

            if (status == TextToSpeech.SUCCESS) {
                // set US English as language for tts
                val result = tts!!.setLanguage(Locale.US)
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTS","The Language specified is not supported!")
                } else {
                }

            } else {
                Log.e("TTS", "Initilization Failed!")
            }
    }

    private fun speakOut(pr: Palabra) {

            tts!!.language = (Locale("es","ES"))
            tts!!.speak(pr.espanol, TextToSpeech.QUEUE_ADD, null,"")

            tts!!.language = (Locale.US)
            tts!!.speak(pr.ingles, TextToSpeech.QUEUE_ADD, null,"")


    }

    public override fun onDestroy() {
        // Shutdown TTS
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }
}