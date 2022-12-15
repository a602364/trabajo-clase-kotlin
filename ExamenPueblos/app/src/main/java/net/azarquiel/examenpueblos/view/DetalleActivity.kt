package net.azarquiel.examenpueblos.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import net.azarquiel.examenpueblos.R
import net.azarquiel.examenpueblos.databinding.ActivityDetalleBinding
import net.azarquiel.examenpueblos.model.Comunidad
import net.azarquiel.examenpueblos.model.Pueblo
import net.azarquiel.examenpueblos.model.PuebloView
import net.azarquiel.examenpueblos.viewModel.PuebloViewModel

class DetalleActivity : AppCompatActivity() {

    private lateinit var comunidadView: Comunidad
    private lateinit var puebloViewModel: PuebloViewModel
    private lateinit var puebloView: PuebloView
    private lateinit var binding: ActivityDetalleBinding
    private lateinit var btnFav: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalleBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_detalle)
        puebloViewModel = ViewModelProvider(this).get(PuebloViewModel::class.java)
        puebloView = intent.getSerializableExtra("pueblo") as PuebloView
        comunidadView = intent.getSerializableExtra("comunidad") as Comunidad
        setTitle(puebloView.nombre)


        val tvNombrePuebloDetalle = findViewById(R.id.tvNombrePuebloDetalle) as TextView
        val ivImagenPuebloDetalle = findViewById(R.id.ivPuebloDetalle) as ImageView
        val tvNombreProvinciaComunidad = findViewById(R.id.tvProvinciaComunidad) as TextView
        val tvEnlace = findViewById(R.id.tvEnlace) as TextView
        btnFav = findViewById(R.id.fabDetalle)


        tvNombrePuebloDetalle.text = puebloView.nombre
        tvNombreProvinciaComunidad.text = "${puebloView.nombreProvincia} (${comunidadView.nombre})"
        Picasso.get().load(puebloView.imagen).into(ivImagenPuebloDetalle)



        if (puebloView.fav == 0){
            btnFav.setImageResource(android.R.drawable.star_big_off)
        }else{
            btnFav.setImageResource(android.R.drawable.star_big_on)
        }

        btnFav.setOnClickListener {
             onClickBotonEstrella(puebloView.id)
        }

        tvEnlace.setOnClickListener{
            onClickNuevaPagina(puebloView)
        }

    }

    fun onClickNuevaPagina(puebloView: PuebloView) {
        val intent = Intent(this, WebActivity::class.java)
        // Agregar el ID del pueblo como un extra a la Intent
        intent.putExtra("pueblo", puebloView)
        startActivity(intent)
    }

    fun onClickBotonEstrella(id: Int) {
        if (puebloView.fav == 0) {
            puebloViewModel.update(id)
            btnFav.setImageResource(android.R.drawable.star_big_on)
            puebloView.fav = 1
            Log.e("1", "Es favorito")

        } else {
            puebloViewModel.update(id)
            btnFav.setImageResource(android.R.drawable.star_big_off)
            puebloView.fav = 0
            Log.e("1", "No es favorito")
        }
    }
}