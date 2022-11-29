package net.azarquiel.traductor.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.azarquiel.traductor.R
import net.azarquiel.traductor.model.Palabra


/**
 * Created by pacopulido on 9/10/18.
 */
class CustomAdapter(val context: Context,
                    val layout: Int,
                    val listener: OnClickRecycler
                    ) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    private var dataList: List<Palabra> = emptyList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewlayout = layoutInflater.inflate(layout, parent, false)
        return ViewHolder(viewlayout, context)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item, listener)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    internal fun setPalabras(palabras: List<Palabra>) {
        this.dataList = palabras
        notifyDataSetChanged()
    }


    class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: Palabra, listener: OnClickRecycler){
            // itemview es el item de diseño
            // al que hay que poner los datos del objeto dataItem
            val tvEspanol = itemView.findViewById<TextView>(R.id.tvEspanol) as TextView
            val tvIngles = itemView.findViewById<TextView>(R.id.tvIngles) as TextView
            val ivSpeaker = itemView.findViewById<ImageView>(R.id.ivSpeaker) as ImageView

            tvEspanol.text = dataItem.espanol
            tvIngles.text = dataItem.ingles
            ivSpeaker.tag = dataItem


            itemView.tag = dataItem
            ivSpeaker.setOnClickListener{listener.OnClick(itemView)}
        }

    }
    interface OnClickRecycler {
        fun OnClick(itemView: View) {

        }
    }
}