package net.azarquiel.caravanretrofit.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import net.azarquiel.caravanretrofit.R
import net.azarquiel.caravanretrofit.adapter.AparcamientoAdapter
import net.azarquiel.caravanretrofit.databinding.ActivityAparcamientoBinding
import net.azarquiel.caravanretrofit.model.Lugar
import net.azarquiel.caravanretrofit.model.Municipio
import net.azarquiel.caravanretrofit.model.Usuario
import net.azarquiel.caravanretrofit.viewModel.MainViewModel

class AparcamientoActivity : AppCompatActivity() {

    private lateinit var usuarioView: Usuario
    private lateinit var lugares: List<Lugar>
    private lateinit var usuarioSH: SharedPreferences
    private lateinit var adapter: AparcamientoAdapter
    private lateinit var viewModel: MainViewModel
    private lateinit var municipioView: Municipio
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityAparcamientoBinding
    private lateinit var usuarioTXT : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAparcamientoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)


        municipioView = intent.getSerializableExtra("municipio") as Municipio
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        usuarioSH = getSharedPreferences("usuarios", Context.MODE_PRIVATE)

        title = "Lugares (${municipioView.nombre})"

        Log.d("municipio", municipioView.toString())

        initRV()
        getUsuarioSH()
        getLugar()

    }

    private fun getLugar() {
        viewModel.getLugar(municipioView.latitud,municipioView.longitud).observe(this, Observer { it ->
            it?.let{
                adapter.setHeroes(it)
                lugares = it

            }
        })
    }

    private fun getUsuarioSH() {
        usuarioTXT = usuarioSH.getString("username","No está").toString()
        if (!usuarioTXT.equals("No está")){
            usuarioView = Gson().fromJson(usuarioTXT, Usuario::class.java)
        }

    }

    private fun initRV() {
        adapter = AparcamientoAdapter(this, R.layout.row_aparcamiento)
        binding.cmAparcamientos.rvAparcamientos.layoutManager = LinearLayoutManager(this)
        binding.cmAparcamientos.rvAparcamientos.adapter = adapter
    }

    fun onClickAparcamiento(v: View?){
        if (usuarioTXT.equals("No está")){
            Toast.makeText(this, "Por favor, inicia sesión para ver detalles del aparcamiento", Toast.LENGTH_SHORT).show()
        } else {
            val aparcamiento = v?.tag as Lugar
            val intent = Intent(this, LugarActivity::class.java)
            intent.putExtra("aparcamiento", aparcamiento)
            startActivity(intent)
        }
    }

}