package net.azarquiel.juegoparejas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var tapando: Boolean = false
    private var time: Long = 0L
    private var aciertos: Int = 0
    private var primerClick : Boolean = true
    private lateinit var ivPrimera : ImageView
    private lateinit var lv: LinearLayout
    private lateinit var random: Random
    private val vectorPokemons = IntArray(809) { i -> i+1}
    private val vectorTablero = IntArray(20) {0}



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        time = System.currentTimeMillis()
        lv = findViewById<LinearLayout>(R.id.LV)
        random = Random(System.currentTimeMillis())

        newGame()

    }

    private fun newGame() {
        inventaPokemon()
        pintaPokemon()
        tapando = false
        aciertos = 0
        primerClick = true
        time = System.currentTimeMillis()
    }

    private fun pintaPokemon() {
        var contador = 0
        for (i in 0 until lv.childCount){
            val lh = lv.getChildAt(i) as LinearLayout
            for (j in 0 until lh.childCount) {
                val iv = lh.getChildAt(j) as ImageView
                val id = resources.getIdentifier("pokemon${vectorTablero[contador]}", "drawable", packageName)
                iv.tag = vectorTablero[contador]

                contador++

                iv.setBackgroundResource(id)
                iv.setOnClickListener(this)
                iv.setImageResource(R.drawable.tapa)
                iv.isEnabled = true
                //iv.setImageResource(android.R.color.transparent)
            }
        }
    }

    private fun inventaPokemon() {
        vectorPokemons.shuffle(random)
        var contador = 0
        for (i in 1 .. 2) {
            for (j in 0 until 10){
                vectorTablero[contador] = vectorPokemons[j]

                contador++
            }
        }
        vectorTablero.shuffle(random)
//        for (n in vectorTablero){
//            Log.d("paco", "$n")
//        }
    }

    override fun  onClick(v: View?) {
        if (tapando) return
        val ivpulsada = v as ImageView
        if (!primerClick && ivpulsada == ivPrimera) return
        ivpulsada.setImageResource(android.R.color.transparent)

        if (primerClick){
            ivPrimera = ivpulsada
        } else{
            checkCartas(ivpulsada)
        }
        primerClick = !primerClick

    }

    private fun checkCartas(ivSegunda: ImageView) {
        if (ivPrimera.tag.equals(ivSegunda.tag)){
            aciertos++
            ivPrimera.isEnabled = false
            ivSegunda.isEnabled = false
            ivPrimera.setImageResource(android.R.drawable.screen_background_light_transparent)
            ivSegunda.setImageResource(android.R.drawable.screen_background_light_transparent)
            if (aciertos == 10){
                dialogoGameOver()
            }
        } else {
            tapar(ivSegunda)
        }
    }

    private fun dialogoGameOver() {
        val segundos = ((System.currentTimeMillis() - time)/1000)
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Game Over")
        builder.setMessage("Lo has conseguido en ${segundos} segundos")
        builder.setCancelable(false)
        builder.setPositiveButton("Ok") { dialog, which ->
            newGame()
        }
        builder.setNegativeButton("Cancel") { dialog, which ->
            finish()
        }
        builder.show()

    }



    private fun tapar(ivSegunda: ImageView) {
        GlobalScope.launch() {
            tapando = true
            SystemClock.sleep(750)
            launch(Dispatchers.Main) {
                ivPrimera.setImageResource(R.drawable.tapa)
                ivSegunda.setImageResource(R.drawable.tapa)
                tapando = false
            }
        }
    }


}