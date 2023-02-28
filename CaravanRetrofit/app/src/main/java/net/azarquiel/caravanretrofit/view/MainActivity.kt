package net.azarquiel.caravanretrofit.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import net.azarquiel.caravanretrofit.R
import net.azarquiel.caravanretrofit.adapter.ComunidadAdapter
import net.azarquiel.caravanretrofit.databinding.ActivityMainBinding
import net.azarquiel.caravanretrofit.model.Comunidad
import net.azarquiel.caravanretrofit.model.Usuario
import net.azarquiel.caravanretrofit.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {

    private var usuario: Usuario? = null
    private lateinit var usuarioSH: SharedPreferences
    private lateinit var comunidades: List<Comunidad>
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ComunidadAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        title = "Comunidades"

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        usuarioSH = getSharedPreferences("usuarios", Context.MODE_PRIVATE)

        initRV()
        getUsuarioSH()
        getComunidades()

    }

    private fun getComunidades() {
        viewModel.getAllComunidades().observe(this, Observer { it ->
            it?.let{
                adapter.setHeroes(it)
                comunidades = it
            }
        })
    }

    private fun initRV() {
        adapter = ComunidadAdapter(this, R.layout.row_comunidad)
        binding.cmComunidad.rvComunidades.layoutManager = LinearLayoutManager(this)
        binding.cmComunidad.rvComunidades.adapter = adapter
    }

    fun onClickComunidad(v: View?){

        val comunidad = v?.tag as Comunidad
        val intent = Intent(this, ProvinciasActivity::class.java)

        intent.putExtra("comunidad", comunidad)
        startActivity(intent)

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
            R.id.logout -> {
                if (usuario ==null) {
                    onClickNewUser()
                } else {
                    onClickLogout()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun onClickLogout() {
        val prefrencias = usuarioSH.edit()
        prefrencias.clear()
        prefrencias.apply()
        usuario = null

        val toast = Toast.makeText(this,"Sesión cerrada", Toast.LENGTH_SHORT)
        toast.show()
    }

    private fun getUsuarioSH() {
        val usuarioTXT = usuarioSH.getString("username","No está")
        if (!usuarioTXT.equals("No está")){
            usuario = Gson().fromJson(usuarioTXT, Usuario::class.java)
            val toast = Toast.makeText(this,"Welcome ${usuario!!.nick}", Toast.LENGTH_SHORT)
            toast.show()

        }
    }

    private fun onClickNewUser() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Ingresa tus credenciales")
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.dialog, null)
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