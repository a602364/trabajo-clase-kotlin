package com.example.examenretrofit.adapter

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.examenretrofit.R
import com.example.examenretrofit.model.Categoria
import com.example.examenretrofit.model.Chiste
import com.squareup.picasso.Picasso


/**
 * Created by pacopulido on 9/10/18.
 */
class ChisteAdapter(val context: Context,
                    val layout: Int
) : RecyclerView.Adapter<ChisteAdapter.ViewHolder>() {

    private var dataList: List<Chiste> = emptyList()

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

    internal fun setChistes(chistes: List<Chiste>) {
        this.dataList = chistes
        notifyDataSetChanged()
    }


    class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: Chiste){
            // itemview es el item de diseño
            // al que hay que poner los datos del objeto dataItem
            val tvTituloChiste = itemView.findViewById(R.id.tvTituloChiste) as TextView
            val ivChiste = itemView.findViewById(R.id.ivChiste) as ImageView

            if (dataItem.contenido.length > 25 ) tvTituloChiste.setText(Html.fromHtml(dataItem.contenido.substring(0, 25) + "...")) else tvTituloChiste.setText(Html.fromHtml(dataItem.contenido + "..."))
            Picasso.get().load("http://www.ies-azarquiel.es/paco/apichistes/img/${dataItem.idcategoria}.png").into(ivChiste)
            itemView.tag = dataItem
        }

    }
}