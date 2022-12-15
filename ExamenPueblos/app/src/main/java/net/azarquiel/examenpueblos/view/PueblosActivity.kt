package net.azarquiel.examenpueblos.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import net.azarquiel.examenpueblos.R
import net.azarquiel.examenpueblos.adapter.CustomAdapterPueblo
import net.azarquiel.examenpueblos.databinding.ActivityPueblosBinding
import net.azarquiel.examenpueblos.model.Comunidad
import net.azarquiel.examenpueblos.model.Pueblo
import net.azarquiel.examenpueblos.model.PuebloView
import net.azarquiel.examenpueblos.viewModel.PuebloViewModel

class PueblosActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var pueblos: java.util.ArrayList<PuebloView>
    private lateinit var puebloView: PuebloView
    private lateinit var searchView: SearchView
    private lateinit var PuebloAL: List<PuebloView>
    private lateinit var comunidadView: Comunidad
    private lateinit var rvPueblo: RecyclerView
    private lateinit var binding: ActivityPueblosBinding
    private lateinit var adapter: CustomAdapterPueblo
    private lateinit var puebloViewModel: PuebloViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPueblosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        rvPueblo = findViewById(R.id.rvPueblos)
        comunidadView = intent.getSerializableExtra("comunidad") as Comunidad
        puebloViewModel = ViewModelProvider(this).get(PuebloViewModel::class.java)


        binding.fabPueblos.setOnClickListener { view ->
            onClickCardsFav()
        }


        initRV()
        getPueblosByComunidad(comunidadView.id)
        setTitle(comunidadView.nombre)
        pueblos = java.util.ArrayList<PuebloView>()
    }


    private fun getPueblosByComunidad(id: Int) {
        puebloViewModel.getPueblosByComunidad(id).observe(this, Observer { pueblo ->
            // Update the cached copy of the products in the adapter.
            pueblo?.let {
                adapter.setPuebloView(it)
                PuebloAL = it
                showPueblos(it)
            }
        })

    }

    private fun showPueblos(puebloViews: List<PuebloView>) {
        adapter.setPuebloView(puebloViews)
    }


    private fun initRV() {
        adapter = CustomAdapterPueblo(this, R.layout.rowpueblo)
        rvPueblo.layoutManager = LinearLayoutManager(this)
        rvPueblo.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchItem = menu.findItem(R.id.sv)
        searchView = searchItem.actionView as SearchView
        searchView.setQueryHint("Search...")
        searchView.setOnQueryTextListener(this)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
        }
        return super.onOptionsItemSelected(item)
    }


    fun onClickPueblo(v: View?) {
        val pueblo = v?.tag as PuebloView
        val comunidad = comunidadView
        val intent = Intent(this, DetalleActivity::class.java)
        intent.putExtra("pueblo", pueblo)
        intent.putExtra("comunidad", comunidad)
        startActivity(intent)
    }

    fun onClickCardsFav() {
        adapter.setPuebloView(PuebloAL.filter { pueblo -> (pueblo.fav == 1) })
        binding.fabPueblos.setImageResource(android.R.drawable.star_big_on)

    }

    override fun onQueryTextSubmit(name: String): Boolean {

        return false
    }

    override fun onQueryTextChange(name: String): Boolean {

        adapter.setPuebloView(PuebloAL.filter { pueblo -> pueblo.nombreProvincia.contains(name,true) })
        return false
    }



}