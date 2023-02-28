package net.azarquiel.caravanretrofit.view

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.azarquiel.caravanretrofit.R
import net.azarquiel.caravanretrofit.adapter.FotoAdapter
import net.azarquiel.caravanretrofit.databinding.ActivityLugarBinding
import net.azarquiel.caravanretrofit.model.Foto
import net.azarquiel.caravanretrofit.model.Lugar
import net.azarquiel.caravanretrofit.model.Usuario
import net.azarquiel.caravanretrofit.viewModel.MainViewModel

class LugarActivity : AppCompatActivity() {
    private lateinit var adapter: FotoAdapter
    private lateinit var fotos: List<Foto>
    private lateinit var binding: ActivityLugarBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var aparcamientoView: Lugar
    private lateinit var usuarioView: Usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLugarBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_lugar)


        aparcamientoView = intent.getSerializableExtra("aparcamiento") as Lugar
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        getLugar()
        showUI()


    }

    private fun showUI() {
        val tvNombreLugar = findViewById<TextView>(R.id.tvNombreLugar)
        val tvDescripcionAparcamiento = findViewById<TextView>(R.id.tvDescripcionAparcamiento)
        val rvFotografias = findViewById<RecyclerView>(R.id.rvFotografias)

        tvNombreLugar.text = aparcamientoView.titre
        tvDescripcionAparcamiento.text = aparcamientoView.description_es
        rvFotografias.layoutManager = LinearLayoutManager(this)
        adapter = FotoAdapter(this, R.layout.row_fotos)
        rvFotografias.adapter = adapter


    }


    private fun getLugar() {
        viewModel.getFotosLugar(aparcamientoView.id).observe(this, Observer { it ->
            it?.let{

                fotos = it
                adapter.setHeroes(it)
                Log.d("CCC", fotos.toString())

            }
        })
    }
}