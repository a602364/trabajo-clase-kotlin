package net.azarquiel.darksky

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.azarquiel.darksky.adapter.CustomAdapter
import net.azarquiel.darksky.model.Currently
import net.azarquiel.darksky.model.Dia
import net.azarquiel.darksky.model.Result
import net.azarquiel.darksky.model.util
import java.net.URL

class MainActivity : AppCompatActivity() {
    private lateinit var tvtempCurrent: TextView
    private lateinit var tvpronoCurrent: TextView
    private lateinit var ivCurrent: ImageView
    private lateinit var tvpopCurrent: TextView
    private lateinit var rv: RecyclerView
    private lateinit var adapter: CustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rv = findViewById<RecyclerView>(R.id.rv)
        tvpopCurrent = findViewById(R.id.tvpopcurrent)
        ivCurrent = findViewById(R.id.ivCurrent)
        tvpronoCurrent = findViewById(R.id.tvpronocurrent)
        tvtempCurrent = findViewById(R.id.tvtempcurrent)
        initRV()
        getData()
    }

    private fun getData() {
        GlobalScope.launch() {
            val jsontxt = URL("https://api.darksky.net/forecast/21259f9de3537b4f719c53580fa39c3a/39.8710026,-4.0251675?lang=es&exclude=minutely,hourly,alerts,flags").readText(Charsets.UTF_8)
            launch(Main) {
                val result = Gson().fromJson(jsontxt, Result::class.java)
                showCurrent(result.currently)
                adapter.setDias(result.daily.data)

            }
        }

    }

    private fun showCurrent(currently: Currently) {
        tvpopCurrent.text = "${currently.precipProbability} %"
        tvtempCurrent.text = "Temp: ${util.farToCel(currently.temperature)}º"
        tvpronoCurrent.text = "${currently.summary}"

        // foto de internet a traves de Picasso
        Picasso.get().load("https://darksky.net/images/weather-icons/${currently.icon}.png").into(ivCurrent)
    }

    private fun initRV() {
        adapter = CustomAdapter(this, R.layout.row)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(this)
    }

    fun onClickDia(v: View){
        val dia = v.tag as Dia
        Toast.makeText(this,"${dia.toString()}",Toast.LENGTH_LONG)
    }
}