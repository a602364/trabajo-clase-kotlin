package net.azarquiel.examenpueblos.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import net.azarquiel.examenpueblos.R
import net.azarquiel.examenpueblos.model.*
import net.azarquiel.examenpueblos.viewModel.PuebloViewModel


/**
 * Created by pacopulido on 9/10/18.
 */
class CustomAdapterComunidad(
    val context: Context,
    val layout: Int
) : RecyclerView.Adapter<CustomAdapterComunidad.ViewHolder>() {

    private var dataList: List<Comunidad> = emptyList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewlayout = layoutInflater.inflate(layout, parent, false)
        return ViewHolder(viewlayout, context)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    internal fun setComunidadView(ComunidadView: List<Comunidad>) {
        this.dataList = ComunidadView
        notifyDataSetChanged()
    }


    inner class ViewHolder(viewlayout: View, val context: Context) :
        RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: Comunidad) {

            val tvNombreComunidad = itemView.findViewById(R.id.tvNombreProvincia) as TextView

            tvNombreComunidad.text = dataItem.nombre
            itemView.tag = dataItem

        }


    }
}

class CustomAdapterProvincia(
    val context: Context,
    val layout: Int
) : RecyclerView.Adapter<CustomAdapterProvincia.ViewHolder>() {

    private var dataList: List<Provincia> = emptyList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewlayout = layoutInflater.inflate(layout, parent, false)
        return ViewHolder(viewlayout, context)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    internal fun setProvinciaView(ProvinciaView: List<Provincia>) {
        this.dataList = ProvinciaView
        notifyDataSetChanged()
    }


    inner class ViewHolder(viewlayout: View, val context: Context) :
        RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: Provincia) {

        }


    }
}

class CustomAdapterPueblo(
    val context: Context,
    val layout: Int
) : RecyclerView.Adapter<CustomAdapterPueblo.ViewHolder>() {

    private var dataList: List<PuebloView> = emptyList()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewlayout = layoutInflater.inflate(layout, parent, false)
        return ViewHolder(viewlayout, context)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    internal fun setPuebloView(PuebloView: List<PuebloView>) {
        this.dataList = PuebloView
        notifyDataSetChanged()
    }


    inner class ViewHolder(viewlayout: View, val context: Context) :
        RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: PuebloView) {

            val tvNombreProvincia = itemView.findViewById(R.id.tvNombreProvincia) as TextView
            val tvNombrePueblo = itemView.findViewById(R.id.tvNombrePueblo) as TextView
            val ivPueblo = itemView.findViewById(R.id.ivFotoPueblo) as ImageView
            val ivEstrella = itemView.findViewById(R.id.ivEstrella) as ImageView


            tvNombreProvincia.text = dataItem.nombreProvincia
            tvNombrePueblo.text = dataItem.nombre
            Picasso.get().load(dataItem.imagen).into(ivPueblo)


            if (dataItem.fav == 0) {
                ivEstrella.setImageResource(android.R.drawable.star_big_off)
            } else {
                ivEstrella.setImageResource(android.R.drawable.star_big_on)
            }

            itemView.tag = dataItem

        }


    }
}



