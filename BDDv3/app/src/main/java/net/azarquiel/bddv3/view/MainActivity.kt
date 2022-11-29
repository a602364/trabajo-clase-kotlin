package net.azarquiel.bddv3.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import net.azarquiel.bddv3.R
import net.azarquiel.bddv3.adapter.CustomAdapterGrupos
import net.azarquiel.bddv3.databinding.ActivityMainBinding
import net.azarquiel.bddv3.model.Grupo
import net.azarquiel.bddv3.model.Grupos
import net.azarquiel.bddv3.viewModel.GrupoViewModel
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var gruposAL: List<Grupo>
    private lateinit var adapterGrupos: CustomAdapterGrupos
    private lateinit var binding: ActivityMainBinding
    private lateinit var rnd: Random
    private lateinit var grupoViewModel: GrupoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener { view ->
            dialogGrupo()
        }

        rnd = Random(System.currentTimeMillis())
        grupoViewModel = ViewModelProvider(this).get(GrupoViewModel::class.java)
        initRV()

        getAllGrupos()

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos = viewHolder.adapterPosition
                val grupo = gruposAL[pos]
                itemEliminarDeslizar(grupo)
                Snackbar.make(
                    binding.cm.rvgrupos,
                    grupo.nombre,
                    Snackbar.LENGTH_LONG
                ).setAction("Undo") {
                    addGrupo(grupo)
                    adapterGrupos.setGrupos(gruposAL)
                }.show()
            }
        }).attachToRecyclerView(binding.cm.rvgrupos)


    }

    private fun dialogGrupo() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add Grupo")
        val ll = LinearLayout(this)
        ll.setPadding(30,30,30,30)
        ll.orientation = LinearLayout.VERTICAL

        val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        lp.setMargins(0,50,0,50)

        val textInputLayoutNombreGrupo = TextInputLayout(this)
        textInputLayoutNombreGrupo.layoutParams = lp
        val etNombreGrupo = EditText(this)
        etNombreGrupo.setPadding(0, 80, 0, 80)
        etNombreGrupo.textSize = 20.0F
        etNombreGrupo.hint = "Nombre"
        textInputLayoutNombreGrupo.addView(etNombreGrupo)

        val textInputLayoutEmailGrupo = TextInputLayout(this)
        textInputLayoutEmailGrupo.layoutParams = lp
        val etEmailGrupo = EditText(this)
        etEmailGrupo.setPadding(0, 80, 0, 80)
        etEmailGrupo.textSize = 20.0F
        etEmailGrupo.hint = "Email"
        textInputLayoutEmailGrupo.addView(etEmailGrupo)

        ll.addView(textInputLayoutNombreGrupo)
        ll.addView(textInputLayoutEmailGrupo)

        builder.setView(ll)

        builder.setPositiveButton("Aceptar") { dialog, which ->
            val color = Color.argb(255, (1..255).random(rnd), (1..255).random(rnd), (1..255).random(rnd))
            addGrupo(Grupo(0,etNombreGrupo.text.toString(), etEmailGrupo.text.toString(), color))
        }

        builder.setNegativeButton("Cancelar") { dialog, which ->
        }

        builder.show()
    }

    private fun addGrupo(grupo: Grupo) {
        grupoViewModel.insert(grupo)
    }

    private fun getAllGrupos() {
        grupoViewModel.getAllGrupos().observe(this, Observer { grupos ->
            // Update the cached copy of the products in the adapter.
            grupos?.let {
                gruposAL = it
                showGrupos()
            }
        })
    }

    private fun deleteGrupo(grupo: Grupo){
        grupoViewModel.delete(grupo)
    }

    private fun showGrupos() {
        adapterGrupos.setGrupos(gruposAL)
    }

    private fun initRV() {
        adapterGrupos = CustomAdapterGrupos(this, R.layout.row)
        binding.cm.rvgrupos.adapter = adapterGrupos
        binding.cm.rvgrupos.layoutManager = LinearLayoutManager(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
            return when (item.itemId) {
                R.id.action_friends -> {
                    val intent = Intent(this, AmigosActivity::class.java)
                    val grupos = Grupos(gruposAL)
                    intent.putExtra("grupos",grupos)
                    startActivity(intent)
                    true

                } else -> super.onOptionsItemSelected(item)

            }



    }

    private fun itemEliminarDeslizar(grupo: Grupo) {

        deleteGrupo(grupo)
        adapterGrupos.setGrupos(gruposAL)

    }

}

