package net.azarquiel.bddv3.view

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import net.azarquiel.bddv3.R
import net.azarquiel.bddv3.adapter.CustomAdapterAmigos
import net.azarquiel.bddv3.databinding.ActivityAmigosBinding
import net.azarquiel.bddv3.model.Amigo
import net.azarquiel.bddv3.model.Grupo
import net.azarquiel.bddv3.model.Grupos
import net.azarquiel.bddv3.viewModel.AmigoViewModel
import net.azarquiel.bddv3.viewModel.GrupoViewModel
import kotlin.random.Random

class AmigosActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivityAmigosBinding
    private lateinit var gruposAL: List<Grupo>
    private lateinit var amigosAL: List<Amigo>
    private lateinit var grupoViewModel: GrupoViewModel
    private lateinit var amigoViewModel: AmigoViewModel
    private lateinit var adapter: CustomAdapterAmigos
    private lateinit var rnd: Random



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAmigosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        gruposAL = (intent.getSerializableExtra("grupos") as Grupos).grupos


        setSupportActionBar(binding.toolbar)

        rnd = Random(System.currentTimeMillis())

        grupoViewModel = ViewModelProvider(this).get(GrupoViewModel::class.java)
        amigoViewModel = ViewModelProvider(this).get(AmigoViewModel::class.java)
        initRV()
        getAllAmigos()




        binding.fab.setOnClickListener { view ->
            dialogoAmigo()
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos = viewHolder.adapterPosition
                val amigo = amigosAL[pos]
                itemEliminarDeslizar(amigo)
                Snackbar.make(
                    binding.cma.rvamigos,
                    amigo.nombre,
                    Snackbar.LENGTH_LONG
                ).setAction("Undo") {
                    addAmigo(amigo)
                    adapter.setAmigos(amigosAL)
                }.show()
            }
        }).attachToRecyclerView(binding.cma.rvamigos)
    }

    private fun initRV() {
        adapter = CustomAdapterAmigos(this, R.layout.rowamigos)
        binding.cma.rvamigos.adapter = adapter
        binding.cma.rvamigos.layoutManager = LinearLayoutManager(this)
    }


    private fun initSpinner(spGrupo: Spinner) {
        val grupostxt = gruposAL.mapIndexed { index, grupo -> grupo.nombre }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, grupostxt)


// Drop down layout style - list view with radio button
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)


// attaching data adapter to spinner

        spGrupo.adapter = adapter
    }

    private fun dialogoAmigo() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add Amigo")


        var inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.dialogonewamigo, null)
        var spgrupo = dialogLayout.findViewById<Spinner>(R.id.spGrupo)


        val ll = LinearLayout(this)
        ll.setPadding(30, 30, 30, 30)
        ll.orientation = LinearLayout.VERTICAL

        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        lp.setMargins(0, 50, 0, 50)

        val textInputLayoutNombreAmigo = TextInputLayout(this)
        textInputLayoutNombreAmigo.layoutParams = lp
        val etNombreAmigo = dialogLayout.findViewById(R.id.etNombre) as EditText

        ll.addView(textInputLayoutNombreAmigo)

        builder.setView(ll)

        initSpinner(spgrupo)
        builder.setView(dialogLayout)

        builder.setPositiveButton("Aceptar") { dialog, which ->
            val color = Color.argb(255, (1..255).random(rnd), (1..255).random(rnd), (1..255).random(rnd))
            addAmigo(Amigo(0, etNombreAmigo.text.toString() , gruposAL[spgrupo.selectedItemPosition].idGrupo, color))

        }

        builder.setNegativeButton("Cancelar") { dialog, which ->
        }

        builder.show()

    }


    private fun addAmigo(amigo: Amigo) {
        amigoViewModel.insert(amigo)

    }

    private fun getAllAmigos() {
        amigoViewModel.getAllAmigos().observe(this, Observer { amigos ->
            // Update the cached copy of the products in the adapter.
            amigos?.let {
                amigosAL = it
                showAmigos()
            }
        })
    }

    private fun deleteAmigo(amigo: Amigo) {
        amigoViewModel.delete(amigo)
    }

    private fun showAmigos() {
        adapter.setAmigos(amigosAL)
    }


    // métodos que obliga la interfaz OnItemSelectedListener
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        var grupoPosicion = gruposAL[position]

        Toast.makeText(this, "Selected: $grupoPosicion", Toast.LENGTH_LONG).show()

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    private fun itemEliminarDeslizar(amigo: Amigo) {

        deleteAmigo(amigo)
        adapter.setAmigos(amigosAL)

    }

}