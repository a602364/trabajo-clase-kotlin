package net.azarquiel.juegotopossolucin

import android.graphics.drawable.AnimationDrawable
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        const val NTOPOS = 8
    }

    private lateinit var mpfondo: MediaPlayer
    private lateinit var mp: MediaPlayer
    private var time: Long = 0L
    private var isGameOver: Boolean = false
    private lateinit var tvscore: TextView
    private var puntos: Int = 0
    private val iv = arrayOfNulls<ImageView>(NTOPOS)
    private lateinit var cl: ConstraintLayout
    private lateinit var random : Random

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        time = System.currentTimeMillis()
        random = Random(System.currentTimeMillis())
        cl = findViewById(R.id.cl)
        tvscore = findViewById<TextView>(R.id.TVScore)


        loadResources()
        cargarCancionFondo()
        getImages()
        sacaTopos()
    }

    private fun cargarCancionFondo() {
        mpfondo.start()
    }

    private fun sacaTopos() {
        val ntopos = (1..NTOPOS/2).random(random)
        val vector = IntArray(8){i->i}
        vector.shuffle(random)
        val vtopos = vector.copyOfRange(0, ntopos)

        showScore()
        showTopos(vtopos)
        tapaTopos(vtopos)
    }

    private fun tapaTopos(vtopos: IntArray) {
        val time = (1..3).random(random)*500
        GlobalScope.launch() {
            SystemClock.sleep(time.toLong())
            launch(Main) {
                for (n in vtopos) {
                    iv[n]!!.setBackgroundResource(android.R.color.transparent)
                    iv[n]!!.isEnabled = false
                }
                if (!isGameOver) sacaTopos()
            }
        }


    }

    private fun showTopos(vtopos: IntArray) {
        for (n in vtopos) {
            val imageShow = iv[n]
            val mor = (1 .. 10).random(random)
            if (mor == 1) {
                showTopo(imageShow, 'r')
            } else {
                showTopo(imageShow, 'm')
            }

        }
    }

    private fun showTopo(imageShow: ImageView?, tipo: Char) {
        val id = resources.getIdentifier("animaciontopo$tipo", "drawable", packageName)
        imageShow!!.setBackgroundResource(id)
        val imageShowAD = imageShow.background as AnimationDrawable
        imageShowAD.start()
        imageShow.isEnabled = true
        imageShow.tag = tipo
    }

    private fun getImages() {
        for (i in 0 until cl.childCount){
            iv[i] = cl.getChildAt(i) as ImageView
            iv[i]!!.setOnClickListener(this)
            iv[i]!!.isEnabled = false

        }
    }

    override fun onClick(v: View?) {
        val iv = v as ImageView
        val tag = v.tag
        if (tag=='m')puntos++ else if (tag == 'r') puntos+=10
        iv.isEnabled= false
        hacerSonido()
        val dead = if (tag == 'm') "tdead" else "tedead"
        val id = resources.getIdentifier(dead, "drawable", packageName)
        iv.setBackgroundResource(id)

        showScore()

        if (puntos >= 100){
            gameOver()
        }
    }

    private fun hacerSonido() {
        mp.start()
        GlobalScope.launch() {
            SystemClock.sleep(1000)
            launch(Main) {
                mp.pause()
            }
        }
    }

    private fun gameOver() {
        isGameOver = true
        var segundos = (System.currentTimeMillis()-time)/1000
        AlertDialog.Builder(this)
        .setTitle("¡Enhorabuena!")
        .setMessage("Has conseguido los 100 puntos en $segundos segundos")
            .setCancelable(false)
        .setPositiveButton("Ok") { _, _ ->
            finish()
        }
        .show()

    }

    private fun showScore() {
        tvscore.text = "$puntos puntos"
    }

    private fun loadResources(){
        mp = MediaPlayer.create(this, R.raw.sonidomuertebueno)
        mpfondo = MediaPlayer.create(this, R.raw.canciondefondobuena)
        mpfondo.isLooping = true
    }


}