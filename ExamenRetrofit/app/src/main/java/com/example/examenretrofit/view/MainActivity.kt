package com.example.examenretrofit.view

import android.content.ClipData.Item
import android.content.Context
import android.content.Intent
import android.content.RestrictionsManager
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.edit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.examenretrofit.R
import com.example.examenretrofit.adapter.CategoriaAdapter
import com.example.examenretrofit.databinding.ActivityMainBinding
import com.example.examenretrofit.model.Categoria
import com.example.examenretrofit.model.Usuario
import com.example.examenretrofit.viewModel.MainViewModel
import com.google.gson.Gson

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var usuarioSH: SharedPreferences
    private var usuario: Usuario? = null
    private lateinit var searchView: SearchView
    private lateinit var categorias: List<Categoria>
    private lateinit var adapter: CategoriaAdapter
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        initRV()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        usuarioSH = getSharedPreferences("usuarios", Context.MODE_PRIVATE)
        getUsuarioSH()
        getCategorias()
    }

    private fun getUsuarioSH() {
        val usuarioTXT = usuarioSH.getString("username","No está")
        if (!usuarioTXT.equals("No está")){
            usuario = Gson().fromJson(usuarioTXT, Usuario::class.java)
            val toast = Toast.makeText(this,"Welcome ${usuario!!.nick}", Toast.LENGTH_SHORT)
            toast.show()

        }
    }

    private fun initRV() {
        adapter = CategoriaAdapter(this, R.layout.row_categoria)
        binding.cmCategorias.rvCategorias.layoutManager = LinearLayoutManager(this)
        binding.cmCategorias.rvCategorias.adapter = adapter
    }

    private fun getCategorias() {
        viewModel.getAllCategorias().observe(this, Observer { it ->
            it?.let{
                adapter.setCategorias(it)
                categorias = it
            }
        })
    }

    fun onClickCategoria(v : View?){
        val categoria = v?.tag as Categoria
        val intent = Intent(this, ChistesActivity::class.java)

        intent.putExtra("categoria", categoria)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchItem = menu.findItem(R.id.search)
        searchView = searchItem.actionView as SearchView
        searchView.setQueryHint("Search...")
        searchView.setOnQueryTextListener(this)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.logout -> {
                if (usuario ==null) {
                    onClickNewUser()
                    item.setIcon(R.drawable.ic_logout)
                } else {
                    onClickLogout()
                    item.setIcon(R.drawable.ic_account)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(query: String): Boolean {
        val original = ArrayList<Categoria>(categorias)
        adapter.setCategorias(original.filter { categoria -> categoria.nombre.contains(query, true) })
        return false
    }

    private fun onClickLogout() {
        val prefrencias = usuarioSH.edit()
        prefrencias.clear()
        prefrencias.commit()
        usuario = null

        val toast = Toast.makeText(this,"Sesión cerrada", Toast.LENGTH_SHORT)
        toast.show()
    }

    private fun onClickNewUser() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Ingresa tus credenciales")
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.dialoglogin, null)
        val usernameEditText = dialogLayout.findViewById<EditText>(R.id.username)
        val passwordEditText = dialogLayout.findViewById<EditText>(R.id.password)

        builder.setView(dialogLayout)
        builder.setPositiveButton("Registrarse") { dialog, which ->
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            login(username, password)


            // Aquí puedes hacer algo con el nombre de usuario y la contraseña
        }
        builder.setNegativeButton("Cancelar") { dialog, which ->
            // Aquí puedes hacer algo si el usuario cancela
        }

        builder.show()
    }

    private fun login(Nick: String, Pass: String){
        viewModel.getDataUsuarioPorNickPass(Nick, Pass).observe(this, Observer { it ->
            if (it == null){
                viewModel.saveUsuario(Usuario(0,Nick,Pass)).observe(this, Observer { it ->
                    it?.let {
                        usuario = it
                        saveUsuario(usuario!!)
                        val toast = Toast.makeText(this,"Usuario Guardado", Toast.LENGTH_SHORT)
                        toast.show()
                    }

                })
            }else{
                usuario = it
                saveUsuarioSH(usuario!!)
                val toast = Toast.makeText(this,"Login Correcto", Toast.LENGTH_SHORT)
                toast.show()
            }
        })
    }

    private fun saveUsuario(usuario: Usuario){
        viewModel.saveUsuario(usuario)
    }

    private fun saveUsuarioSH(username: Usuario) {
        usuarioSH.edit(true) {
            putString("username", Gson().toJson(username))
            commit()
        }
    }
}

