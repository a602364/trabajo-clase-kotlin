package net.azarquiel.chatdam.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import net.azarquiel.chatdam.R
import net.azarquiel.chatdam.adapter.ChatAdapter
import net.azarquiel.chatdam.databinding.ActivityMainBinding
import net.azarquiel.chatdam.model.Mensaje

class MainActivity : AppCompatActivity() {

    private var mensajesAL: ArrayList<Mensaje> = ArrayList()
    private val TAG = "Firebase 2022"
    private lateinit var adapter: ChatAdapter
    private var db = Firebase.firestore
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        db = FirebaseFirestore.getInstance()


        initRV()
        setListener()
        binding.fab.setOnClickListener { onClickNewMensaje() }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos = viewHolder.adapterPosition
                val mensaje = mensajesAL[pos]
                itemEliminarDeslizar(mensaje)
                Snackbar.make(
                    binding.cmChat.rvPost,
                    mensaje.msg,
                    Snackbar.LENGTH_LONG
                ).setAction("Undo") {
                    mensajesAL.add(mensaje)
                    adapter.setMensajes(mensajesAL)
                }.show()
            }
        }).attachToRecyclerView(binding.cmChat.rvPost)
    }

    private fun itemEliminarDeslizar(mensaje: Mensaje) {
        db.collection("mensajes")
            .whereEqualTo("mensaje", mensaje.msg) // Busca el documento por el mensaje
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    document.reference.delete() // Elimina el documento
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
        mensajesAL.remove(mensaje)
        adapter.setMensajes(mensajesAL)
    }



    private fun initRV() {
        adapter = ChatAdapter(this, R.layout.row_mensaje)
        binding.cmChat.rvPost.adapter = adapter
        binding.cmChat.rvPost.layoutManager = LinearLayoutManager(this)
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
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addData(mensaje : Mensaje) {
        val msg: MutableMap<String, Any> = HashMap() // diccionario key value
        msg["mensaje"] = mensaje.msg
        db.collection("mensajes")
            .add(msg)
            .addOnSuccessListener(OnSuccessListener<DocumentReference> { documentReference ->
                Log.d("Firebase2022","DocumentSnapshot added with ID: " + documentReference.id)
            })
            .addOnFailureListener(OnFailureListener { e ->
                Log.w("Firebase2022","Error adding document", e)
            })
    }

    private fun setListener() {
        db.collection("mensajes").addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null) {
                documentToList(snapshot.documents)
                adapter.setMensajes(mensajesAL)
            } else {
                Log.d(TAG, "Current data: null")
            }
        }
    }

    private fun onClickNewMensaje(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Ingresa tus credenciales")
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.dialog, null)
        val msgEditText = dialogLayout.findViewById<EditText>(R.id.etMensaje)

        builder.setView(dialogLayout)
        builder.setPositiveButton("Enviar Mensaje") { dialog, which ->
            val msg = msgEditText.text.toString()
            addData(Mensaje(msg))
        }
        builder.setNegativeButton("Cancelar") { dialog, which ->
            // Aquí puedes hacer algo si el usuario cancela
        }

        builder.show()
    }

    private fun documentToList(documents: List<DocumentSnapshot>) {
        mensajesAL.clear()
        documents.forEach { d ->
            val msg = d["mensaje"] as String
            mensajesAL.add(Mensaje(msg))
        }
    }

    private fun procesarData(data: Map<String, Any>) {
        for ((k, v) in data){
            Log.d(TAG, "$k => $v")
        }
    }

    fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        TODO("Not yet implemented")
    }

    fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        TODO("Not yet implemented")
    }

    fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        TODO("Not yet implemented")
    }


}