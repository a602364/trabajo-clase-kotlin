package net.azarquiel.caravanretrofit.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import net.azarquiel.caravanretrofit.R
import net.azarquiel.caravanretrofit.adapter.MunicipioAdapter
import net.azarquiel.caravanretrofit.databinding.ActivityMunicipioBinding
import net.azarquiel.caravanretrofit.model.Municipio
import net.azarquiel.caravanretrofit.model.Provincia
import net.azarquiel.caravanretrofit.viewModel.MainViewModel

class MunicipioActivity : AppCompatActivity() {

    private lateinit var municipios: List<Municipio>
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MunicipioAdapter
    private lateinit var provinciaView: Provincia
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMunicipioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMunicipioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        provinciaView = intent.getSerializableExtra("provincia") as Provincia
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        title = "Municipios(${provinciaView.nombre})"

        initRV()
        getMunicipios(provinciaView.id)
    }

    private fun getMunicipios(idprovincia: String) {
        viewModel.getMunicipiosByProvincia(idprovincia).observe(this, Observer { it ->
            it?.let{
                adapter.setHeroes(it)
                municipios = it
            }
        })
    }

    private fun initRV() {
        adapter = MunicipioAdapter(this, R.layout.row_municipio)
        binding.cmMunicipios.rvMunicipios.layoutManager = LinearLayoutManager(this)
        binding.cmMunicipios.rvMunicipios.adapter = adapter
    }

    fun onClickMunicipio(v:View?){
        val municipio = v?.tag as Municipio
        val intent = Intent(this, AparcamientoActivity::class.java)
        intent.putExtra("municipio", municipio)
        //intent.putExtra("usuario", usuario)
        startActivity(intent)
    }


}