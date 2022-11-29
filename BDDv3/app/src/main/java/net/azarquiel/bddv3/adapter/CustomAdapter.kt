package net.azarquiel.bddv3.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import net.azarquiel.bddv3.R
import net.azarquiel.bddv3.model.Amigo
import net.azarquiel.bddv3.model.Grupo
import net.azarquiel.bddv3.util.Util


/**
 * Created by pacopulido on 9/10/18.
 */
class CustomAdapterGrupos(
    val context: Context,
    val layout: Int
) : RecyclerView.Adapter<CustomAdapterGrupos.ViewHolder>() {

    private var dataList: List<Grupo> = emptyList()


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

    internal fun setGrupos(Grupos: List<Grupo>) {
        this.dataList = Grupos
        notifyDataSetChanged()
    }


    class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: Grupo) {
            val tvNombres = itemView.findViewById(R.id.tvnombre) as TextView
            val tvEmails = itemView.findViewById(R.id.tvemailrowgrupo) as TextView
            tvNombres.setTextColor(dataItem.color)
            tvEmails.setTextColor(dataItem.color)
            tvNombres.text = dataItem.nombre
            tvEmails.text = dataItem.email

            (itemView as CardView).setCardBackgroundColor(Util.getTransparentColor(dataItem.color))

            itemView.tag = dataItem

        }

    }
}

class CustomAdapterAmigos(
    val context: Context,
    val layout: Int
) : RecyclerView.Adapter<CustomAdapterAmigos.ViewHolder>() {

    private var dataList: List<Amigo> = emptyList()


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

    internal fun setAmigos(Amigos: List<Amigo>) {
        this.dataList = Amigos
        notifyDataSetChanged()
    }


    class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: Amigo) {


            val tvNombres = itemView.findViewById(R.id.tvnombreAmigos) as TextView
            tvNombres.setTextColor(dataItem.color)

            tvNombres.text = dataItem.nombre

            (itemView as CardView).setCardBackgroundColor(Util.getTransparentColor(dataItem.color))

            itemView.tag = dataItem

        }

    }
}