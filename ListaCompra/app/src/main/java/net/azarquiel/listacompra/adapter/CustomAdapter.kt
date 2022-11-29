package net.azarquiel.listacompra.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import net.azarquiel.listacompra.R
import net.azarquiel.listacompra.model.Producto


/**
 * Created by pacopulido on 9/10/18.
 */
class CustomAdapter(val context: Context,
                    val layout: Int
                    ) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    private var dataList: List<Producto> = emptyList()


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

    internal fun setProductos(productos: List<Producto>) {
        this.dataList = productos
        notifyDataSetChanged()
    }


    class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: Producto){
            // itemview es el item de diseño
            // al que hay que poner los datos del objeto dataItem
            val tvfecharow = itemView.findViewById(R.id.tvfecharow) as TextView
            val tvpoprow = itemView.findViewById(R.id.tvpoprow) as TextView
            val tvpronorow = itemView.findViewById(R.id.tvpronorow) as TextView


            tvfecharow.text =dataItem.nombre
            tvpoprow.text ="x${dataItem.cantidad}"
            tvpronorow.text ="${dataItem.precio}€"

            itemView.tag = dataItem
            (itemView as CardView).setCardBackgroundColor(ContextCompat.getColor(context, R.color.white))
            (itemView as CardView).getChildAt(0).tag = false
        }

    }
}