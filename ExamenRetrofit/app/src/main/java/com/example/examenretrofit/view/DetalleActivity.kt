package com.example.examenretrofit.view

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.examenretrofit.R
import com.example.examenretrofit.model.Categoria
import com.example.examenretrofit.model.Chiste
import com.example.examenretrofit.model.Punto
import com.example.examenretrofit.model.Usuario
import com.example.examenretrofit.viewModel.MainViewModel
import com.google.gson.Gson
import com.squareup.picasso.Picasso

class DetalleActivity : AppCompatActivity() {
    private lateinit var usuarioSH: SharedPreferences
    private var usuario: Usuario? = null
    private lateinit var categoriaView: Categoria
    private lateinit var viewModel: MainViewModel
    private lateinit var chisteView: Chiste

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle)

        chisteView = intent.getSerializableExtra("chiste") as Chiste
        categoriaView = intent.getSerializableExtra("categoria") as Categoria
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        usuarioSH = getSharedPreferences("usuarios", Context.MODE_PRIVATE)

        getUsuarioSH()
        showUI()
    }

    private fun showUI() {
        val tvDetalle = findViewById<TextView>(R.id.tvDetalle)
        val rbDetalle = findViewById<RatingBar>(R.id.rbDetalle)
        val ivDetalle = findViewById<ImageView>(R.id.ivDetalle)
        val tvDescripcionDetalle = findViewById<TextView>(R.id.tvDescripcionDetalle)
        val rbDetalleUsuario = findViewById<RatingBar>(R.id.rbDetalleUsuario)
        tvDetalle.text = categoriaView.nombre
        tvDescripcionDetalle.setText(Html.fromHtml(chisteView.contenido))
        Picasso.get().load("http://www.ies-azarquiel.es/paco/apichistes/img/${chisteView.idcategoria}.png").into(ivDetalle)


        viewModel.getAvgChiste(chisteView.id.toLong()).observe(this, Observer { it ->
            it?.let{
                rbDetalle.rating = it.toFloat()
            }
        })

        rbDetalleUsuario.setOnRatingBarChangeListener { rbDetalleUsuario , _, _ ->
            ratingOnClick(rbDetalleUsuario.rating)
            showUI()
        }


    }
    
    private fun getUsuarioSH() {
        val usuarioTXT = usuarioSH.getString("username","No está")
        if (!usuarioTXT.equals("No está")){
            usuario = Gson().fromJson(usuarioTXT, Usuario::class.java)
        }
    }

    private fun ratingOnClick(rating: Float) {
        var estrellas = rating.toInt()
        if (usuario != null) {
            viewModel.savePuntoChiste(chisteView.id.toLong(), Punto(0, chisteView.id.toLong(), estrellas))
                .observe(this, Observer {
                    it?.let {
                        val toast = Toast.makeText(
                            this,
                            "Has dado $estrellas estrellas",
                            Toast.LENGTH_SHORT
                        )
                        toast.show()
                    }
                })
        } else{
            val toast = Toast.makeText(this,"Inicia sesión para enviar tu puntaje", Toast.LENGTH_SHORT)
            toast.show()
        }
    }
}