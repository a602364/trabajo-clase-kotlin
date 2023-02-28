package net.azarquiel.chatdam.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.azarquiel.chatdam.R
import net.azarquiel.chatdam.model.Mensaje

class ChatAdapter(val context: Context,
                  val layout: Int
) : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    private var dataList: List<Mensaje> = emptyList()

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

    internal fun setMensajes(heroes: List<Mensaje>) {
        this.dataList = heroes
        notifyDataSetChanged()
    }


    class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: Mensaje){
            // itemview es el item de diseño
            // al que hay que poner los datos del objeto dataItem
            val tvMensaje = itemView.findViewById(R.id.tvMensaje) as TextView

            tvMensaje.text = dataItem.msg
            itemView.tag = dataItem
        }

    }
}