package net.azarquiel.examenpueblos.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import net.azarquiel.examenpueblos.R
import net.azarquiel.examenpueblos.model.PuebloView

class WebActivity : AppCompatActivity() {
    private lateinit var puebloView: PuebloView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        puebloView = intent.getSerializableExtra("pueblo") as PuebloView

        val wv = findViewById<WebView>(R.id.wv)
        wv.loadUrl(puebloView.link!!)

    }
}