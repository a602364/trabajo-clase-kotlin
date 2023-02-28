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
import net.azarquiel.caravanretrofit.adapter.ProvinciaAdapter
import net.azarquiel.caravanretrofit.databinding.ActivityProvinciasBinding
import net.azarquiel.caravanretrofit.model.Comunidad
import net.azarquiel.caravanretrofit.model.Provincia
import net.azarquiel.caravanretrofit.model.Usuario
import net.azarquiel.caravanretrofit.viewModel.MainViewModel

class ProvinciasActivity : AppCompatActivity() {

    private lateinit var provincias: List<Provincia>
    private lateinit var adapter: ProvinciaAdapter
    private lateinit var viewModel: MainViewModel
    private lateinit var comunidadView: Comunidad
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityProvinciasBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProvinciasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        comunidadView = intent.getSerializableExtra("comunidad") as Comunidad
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        initRV()
        getProvincias(comunidadView.id)


    }

    private fun getProvincias(idcomunidad:String) {
        viewModel.getProvinciasByComunidad(idcomunidad).observe(this, Observer { it ->
            it?.let{
                adapter.setHeroes(it)
                provincias = it
            }
        })
    }

    private fun initRV() {
        adapter = ProvinciaAdapter(this, R.layout.row_provincia)
        binding.cmProvincia.rvProvincias.layoutManager = LinearLayoutManager(this)
        binding.cmProvincia.rvProvincias.adapter = adapter
    }

    fun onClickProvincia(v:View?){
        val provincia = v?.tag as Provincia
        val intent = Intent(this, MunicipioActivity::class.java)
        intent.putExtra("provincia", provincia)
        //intent.putExtra("usuario", usuario)
        startActivity(intent)
    }

}