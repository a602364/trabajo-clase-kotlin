package net.azarquiel.darksky.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import net.azarquiel.darksky.R
import net.azarquiel.darksky.model.Dia
import net.azarquiel.darksky.model.util

class CustomAdapter(val context: Context,
                    val layout: Int
                    ) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    private var dataList: List<Dia> = emptyList()

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

    internal fun setDias(Dias: List<Dia>) {
        this.dataList = Dias
        notifyDataSetChanged()
    }


    class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: Dia){
            // itemview es el item de diseño
            // al que hay que poner los datos del objeto dataItem
            val ivrow = itemView.findViewById(R.id.ivrow) as ImageView
            val tvfecharow = itemView.findViewById(R.id.tvfecharow) as TextView
            val tvpoprow = itemView.findViewById(R.id.tvpoprow) as TextView
            val tvpronorow = itemView.findViewById(R.id.tvpronorow) as TextView
            val tvtemprow = itemView.findViewById(R.id.tvtemprow) as TextView

            tvfecharow.text ="${util.timeStampToDate(dataItem.time)}"
            tvpoprow.text ="${dataItem.precipProbability} %"
            tvpronorow.text ="${dataItem.summary}"
            tvtemprow.text ="Max: ${util.farToCel(dataItem.temperatureMax)}º - Min: ${util.farToCel(dataItem.temperatureMin)}º"


            // foto de internet a traves de Picasso
            Picasso.get().load("https://darksky.net/images/weather-icons/${dataItem.icon}.png").into(ivrow)
            itemView.tag = dataItem

        }

    }
}