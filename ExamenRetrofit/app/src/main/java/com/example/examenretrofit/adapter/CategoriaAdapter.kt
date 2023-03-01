package com.example.examenretrofit.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.examenretrofit.R
import com.example.examenretrofit.model.Categoria
import com.squareup.picasso.Picasso


/**
 * Created by pacopulido on 9/10/18.
 */
class CategoriaAdapter(val context: Context,
                       val layout: Int
) : RecyclerView.Adapter<CategoriaAdapter.ViewHolder>() {

    private var dataList: List<Categoria> = emptyList()

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

    internal fun setCategorias(categorias: List<Categoria>) {
        this.dataList = categorias
        notifyDataSetChanged()
    }


    class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: Categoria){
            // itemview es el item de diseño
            // al que hay que poner los datos del objeto dataItem
            val tvNombreCategoria = itemView.findViewById(R.id.tvNombreCategoria) as TextView
            val ivCategoria = itemView.findViewById(R.id.ivCategoria) as ImageView

            tvNombreCategoria.text = dataItem.nombre
            Picasso.get().load("http://www.ies-azarquiel.es/paco/apichistes/img/${dataItem.id}.png").into(ivCategoria)
            itemView.tag = dataItem
        }

    }
}