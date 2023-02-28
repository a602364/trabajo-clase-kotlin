package net.azarquiel.caravanretrofit.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.azarquiel.caravanretrofit.R
import net.azarquiel.caravanretrofit.model.Comunidad


/**
 * Created by pacopulido on 9/10/18.
 */
class ComunidadAdapter(val context: Context,
                  val layout: Int
) : RecyclerView.Adapter<ComunidadAdapter.ViewHolder>() {

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

    internal fun setHeroes(heroes: List<Comunidad>) {
        this.dataList = heroes
        notifyDataSetChanged()
    }


    class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: Comunidad){
            // itemview es el item de diseño
            // al que hay que poner los datos del objeto dataItem
            val tvNombreComunidad = itemView.findViewById(R.id.tvNombreComunidad) as TextView
            val ivBanderaComunidad = itemView.findViewById(R.id.ivBanderaComunidad) as ImageView

            tvNombreComunidad.text = dataItem.nombre
            ivBanderaComunidad.setImageResource(context.resources.getIdentifier("ccaa${dataItem.id}", "drawable", context.packageName))
            itemView.tag = dataItem
        }

    }
}