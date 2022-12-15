package net.azarquiel.examenpueblos.view

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.azarquiel.alltricks.util.Util
import net.azarquiel.examenpueblos.R
import net.azarquiel.examenpueblos.adapter.CustomAdapterComunidad
import net.azarquiel.examenpueblos.adapter.CustomAdapterProvincia
import net.azarquiel.examenpueblos.databinding.ActivityMainBinding
import net.azarquiel.examenpueblos.model.Comunidad
import net.azarquiel.examenpueblos.model.Provincia
import net.azarquiel.examenpueblos.viewModel.ComunidadViewModel
import net.azarquiel.examenpueblos.viewModel.ProvinciaViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var apapterProvincia: CustomAdapterProvincia
    private lateinit var comunidadViewModel: ComunidadViewModel
    private lateinit var provinciaViewModel: ProvinciaViewModel
    private lateinit var rvComunidad: RecyclerView
    private lateinit var adapter: CustomAdapterComunidad
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        rvComunidad = findViewById(R.id.rvComunidades)
        comunidadViewModel = ViewModelProvider(this).get(ComunidadViewModel::class.java)
        Util.inyecta(this, "pueblosbonitos.sqlite")

        initRV()
        getAllMarcas()


    }

    private fun getAllMarcas() {
        comunidadViewModel.getAllComunidades().observe(this, Observer { comunidades ->
            // Update the cached copy of the products in the adapter.
            comunidades?.let {
                adapter.setComunidadView(it)
            }
        })
    }

    private fun initRV() {
        adapter = CustomAdapterComunidad(this, R.layout.rowcomunidad)
        rvComunidad.layoutManager = LinearLayoutManager(this)
        rvComunidad.adapter = adapter
    }

    fun onClickComunidad(v: View?) {
        val comunidad = v?.tag as Comunidad
        val intent = Intent(this, PueblosActivity::class.java)
        intent.putExtra("comunidad", comunidad)
        startActivity(intent)
    }
}