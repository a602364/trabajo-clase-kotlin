package net.azarquiel.caravanretrofit.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.azarquiel.caravanretrofit.R
import net.azarquiel.caravanretrofit.model.Lugar


/**
 * Created by pacopulido on 9/10/18.
 */
class AparcamientoAdapter(val context: Context,
                          val layout: Int
) : RecyclerView.Adapter<AparcamientoAdapter.ViewHolder>() {

    private var dataList: List<Lugar> = emptyList()

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

    internal fun setHeroes(lugares: List<Lugar>) {
        this.dataList = lugares
        notifyDataSetChanged()
    }


    class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: Lugar){
            // itemview es el item de diseño
            // al que hay que poner los datos del objeto dataItem
            val tvNombreAparcamiento = itemView.findViewById(R.id.tvNombreAparcamiento) as TextView
            val tvDescripcionLugar = itemView.findViewById(R.id.tvDescripcionLugar) as TextView

            tvNombreAparcamiento.text = dataItem.titre
            tvDescripcionLugar.text = dataItem.description_es
            itemView.tag = dataItem
        }

    }
}