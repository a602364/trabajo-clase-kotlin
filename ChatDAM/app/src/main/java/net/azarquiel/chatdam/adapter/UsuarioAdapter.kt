package net.azarquiel.chatdam.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.auth.User
import net.azarquiel.chatdam.R
import net.azarquiel.chatdam.model.Mensaje

class UsuarioAdapter(val context: Context,
                  val layout: Int
) : RecyclerView.Adapter<UsuarioAdapter.ViewHolder>() {

    private var dataList: List<User> = emptyList()

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

    internal fun setUsers(usuarios: List<User>) {
        this.dataList = usuarios
        notifyDataSetChanged()
    }


    class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: User){
//            // itemview es el item de diseño
//            // al que hay que poner los datos del objeto dataItem
//            val tvAutor = itemView.findViewById(R.id.tvAutor) as TextView
//
//            tvAutor.text = dataItem.toString()
//            itemView.tag = dataItem
        }

    }
}