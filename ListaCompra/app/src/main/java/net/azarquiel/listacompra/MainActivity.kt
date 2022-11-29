package net.azarquiel.listacompra

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import net.azarquiel.listacompra.adapter.CustomAdapter
import net.azarquiel.listacompra.databinding.ActivityMainBinding
import net.azarquiel.listacompra.model.Producto

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: CustomAdapter
    private lateinit var carroAL: java.util.ArrayList<Producto>
    private lateinit var carroSH: SharedPreferences
    private lateinit var contadorSH: SharedPreferences
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        carroSH = getSharedPreferences("carro", Context.MODE_PRIVATE)
        contadorSH = getSharedPreferences("contador", Context.MODE_PRIVATE)
        initContador()
        initRV()
        getProductos()
        showProductos()

        binding.fab.setOnClickListener {
            newProducto()
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos = viewHolder.adapterPosition
                val product = carroAL[pos]
                itemEliminarDeslizar(product)
                Snackbar.make(
                    binding.cm.rvcarro,
                    product.nombre,
                    Snackbar.LENGTH_LONG
                ).setAction("Undo") {
                    carroAL.add(product)
                    adapter.setProductos(carroAL)
                }.show()
            }
        }).attachToRecyclerView(binding.cm.rvcarro)

    }

    private fun initRV() {
        adapter = CustomAdapter(this, R.layout.row)
        binding.cm.rvcarro.adapter = adapter
        binding.cm.rvcarro.layoutManager = LinearLayoutManager(this)
    }

    private fun initContador() {
        val c = contadorSH.getInt("c", -1)
        if (c == -1) {
            val editor = contadorSH.edit()
            editor.putInt("c", 0)
            editor.commit()
        }

    }

    private fun showProductos() {
        adapter.setProductos(carroAL)
    }

    private fun getProductos() {
        val carroAll = carroSH.all
        carroAL = ArrayList<Producto>()
        for ((key, value) in carroAll) {
            val productoJson = value.toString()
            val producto = Gson().fromJson(productoJson, Producto::class.java)
            carroAL.add(producto)
        }
    }

    private fun newProducto() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add Product")
        val ll = LinearLayout(this)
        ll.setPadding(30, 30, 30, 30)
        ll.orientation = LinearLayout.VERTICAL

        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        lp.setMargins(0, 50, 0, 50)

        val textInputLayoutNombre = TextInputLayout(this)
        textInputLayoutNombre.layoutParams = lp
        val textInputLayoutCantidad = TextInputLayout(this)
        textInputLayoutCantidad.layoutParams = lp
        val textInputLayoutPrecio = TextInputLayout(this)
        textInputLayoutPrecio.layoutParams = lp

        val etnombre = EditText(this)
        etnombre.setPadding(0, 80, 0, 80)
        etnombre.textSize = 20.0F
        etnombre.hint = "Nombre"
        textInputLayoutNombre.addView(etnombre)

        val etcantidad = EditText(this)
        etcantidad.setPadding(0, 80, 0, 80)
        etcantidad.textSize = 20.0F
        etcantidad.hint = "Cantidad"
        textInputLayoutCantidad.addView(etcantidad)

        val etprecio = EditText(this)
        etprecio.setPadding(0, 80, 0, 80)
        etprecio.textSize = 20.0F
        etprecio.hint = "Precio"
        textInputLayoutPrecio.addView(etprecio)


        ll.addView(textInputLayoutNombre)
        ll.addView(textInputLayoutCantidad)
        ll.addView(textInputLayoutPrecio)

        builder.setView(ll)

        builder.setPositiveButton("Save") { dialog, which ->
            if (etnombre.text.isEmpty() || etcantidad.text.isEmpty() || etprecio.text.isEmpty()) {
                Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_LONG).show()
            } else {
                saveSH(
                    Producto(
                        getContador(),
                        etnombre.text.toString(),
                        etcantidad.text.toString().toFloat(),
                        etprecio.text.toString().toFloat()
                    )
                )
            }
        }

        builder.setNegativeButton("Cancel") { dialog, which ->
        }

        builder.show()

    }

    private fun getContador(): Int {
        val c = contadorSH.getInt("c", -1)
        incContador(c)
        return c + 1
    }

    private fun incContador(c: Int) {
        val editor = contadorSH.edit()
        editor.putInt("c", c + 1)
        editor.commit()
    }

    private fun saveSH(producto: Producto) {
        //save en el Share
        val editor = carroSH.edit()
        editor.putString("${producto.id}", Gson().toJson(producto))
        editor.commit()
        //add al ArrayList
        carroAL.add(0, producto)
        adapter.setProductos(carroAL)
    }


    fun onClickProducto(v: View) {
        // val producto = v.tag as Producto
        val cv = v as CardView

        if (cv.getChildAt(0).tag as Boolean) {
            v.setCardBackgroundColor(Color.WHITE)
        } else {
            v.setCardBackgroundColor(Color.GREEN)
        }

        cv.getChildAt(0).tag = !(v.getChildAt(0).tag as Boolean)


        v.setOnLongClickListener {

            itemAviso(v.tag as Producto)
            //itemEliminar(v.tag as Producto)
            true
        }

    }

    private fun itemAviso(producto: Producto) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Borrar producto?")
        builder.setMessage("¡Prueba deslizando a la derecha para borrar el producto de la lista!")
        builder.setPositiveButton("Ok") { dialog, which ->

        }
        builder.show()
    }


    fun Eliminar() {
        val editor = carroSH.edit()

        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Eliminar productos?")
        builder.setMessage("¿Eliminar estos productos de la lista de la compra?")
        builder.setPositiveButton("No") { dialog, which ->

        }
        builder.setNegativeButton("Sí") { dialog, which ->
            editor.clear()
            editor.commit()
            carroAL.clear()
            adapter.setProductos(carroAL)
        }
        builder.show()


    }

    fun AcercaDe() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Información")
        builder.setMessage("App desarrollada por Israel Gómez en 2ºDAM.")
        builder.setPositiveButton("Ok") { dialog, which ->

        }
        builder.show()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.itemEliminar -> Eliminar()
            R.id.itemInfo -> AcercaDe()
        }
        return super.onOptionsItemSelected(item)
    }

//    private fun itemEliminar(producto: Producto) {
//
//        val editor = carroSH.edit()
//
//        val builder = AlertDialog.Builder(this)
//        builder.setTitle("¿Eliminar producto?")
//        builder.setMessage("¿Eliminar este producto de la lista de la compra?")
//        builder.setPositiveButton("No") { dialog, which ->
//
//        }
//        builder.setNegativeButton("Sí") { dialog, which ->
//            editor.remove("${producto.id}")
//            editor.commit()
//            carroAL.remove(producto)
//            adapter.setProductos(carroAL)
//        }
//        builder.show()
//
//
//
//    }



    private fun itemEliminarDeslizar(producto: Producto) {

        val editor = carroSH.edit()
        editor.remove("${producto.id}")
        editor.commit()
        carroAL.remove(producto)
        adapter.setProductos(carroAL)


    }


}