package com.example.examenretrofit.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.examenretrofit.R
import com.example.examenretrofit.adapter.ChisteAdapter
import com.example.examenretrofit.databinding.ActivityChistesBinding
import com.example.examenretrofit.model.Categoria
import com.example.examenretrofit.model.Chiste
import com.example.examenretrofit.model.Usuario
import com.example.examenretrofit.viewModel.MainViewModel
import com.google.gson.Gson

class ChistesActivity : AppCompatActivity() {

    private var usuario: Usuario? = null
    private lateinit var usuarioSH: SharedPreferences
    private lateinit var chistes: List<Chiste>
    private lateinit var adapter: ChisteAdapter
    private lateinit var viewModel: MainViewModel
    private lateinit var categoriaView: Categoria
    private lateinit var binding: ActivityChistesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChistesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        categoriaView = intent.getSerializableExtra("categoria") as Categoria
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        usuarioSH = getSharedPreferences("usuarios", Context.MODE_PRIVATE)


        initRV()
        getChistes(categoriaView.id.toString())
        getUsuarioSH()
        binding.fab.setOnClickListener { view ->
           if (usuario != null) onClickNuevoChiste() else {
               val toast = Toast.makeText(this,"Inicia sesión", Toast.LENGTH_SHORT)
               toast.show()
           }
        }
    }

    private fun onClickNuevoChiste() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("New Chiste")
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.dialog, null)
        val chisteEditText = dialogLayout.findViewById<EditText>(R.id.etChiste)

        builder.setView(dialogLayout)
        builder.setPositiveButton("Aceptar") { dialog, which ->
            val chisteTXT = chisteEditText.text.toString()

            writeChiste(chisteTXT)


            // Aquí puedes hacer algo con el nombre de usuario y la contraseña
        }
        builder.setNegativeButton("Cancelar") { dialog, which ->
            // Aquí puedes hacer algo si el usuario cancela
        }

        builder.show()
    }

    private fun getUsuarioSH() {
        val usuarioTXT = usuarioSH.getString("username","No está")
        if (!usuarioTXT.equals("No está")){
            usuario = Gson().fromJson(usuarioTXT, Usuario::class.java)

        }
    }

    private fun writeChiste(chiste: String) {
        viewModel.saveChiste(Chiste(69, contenido = chiste, idcategoria = categoriaView.id.toInt())).observe(this, Observer { it ->
            it?.let {
                val toast = Toast.makeText(this, "Chiste Guardado", Toast.LENGTH_SHORT)
                toast.show()

                // Actualiza la lista de chistes
                getChistes(categoriaView.id.toString())
                adapter.notifyDataSetChanged()
            }
        })
    }


    fun onClickChiste(v : View?){
        val chiste = v?.tag as Chiste
        val intent = Intent(this, DetalleActivity::class.java)
        intent.putExtra("chiste", chiste)
        intent.putExtra("categoria", categoriaView)
        //intent.putExtra("usuario", usuario)
        startActivity(intent)
    }


    private fun getChistes(idCategoria: String) {
        viewModel.getChistesByCategoria(idCategoria).observe(this, Observer { it ->
            it?.let{
                adapter.setChistes(it)
                chistes = it
            }
        })
    }

    private fun initRV() {
        adapter = ChisteAdapter(this, R.layout.row_chiste)
        binding.cmChistes.rvChistes.layoutManager = LinearLayoutManager(this)
        binding.cmChistes.rvChistes.adapter = adapter
    }

}