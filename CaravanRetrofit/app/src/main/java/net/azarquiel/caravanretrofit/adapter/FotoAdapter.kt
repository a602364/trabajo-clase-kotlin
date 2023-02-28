package net.azarquiel.caravanretrofit.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import net.azarquiel.caravanretrofit.R
import net.azarquiel.caravanretrofit.model.Foto


/**
 * Created by pacopulido on 9/10/18.
 */
class FotoAdapter(val context: Context,
                  val layout: Int
) : RecyclerView.Adapter<FotoAdapter.ViewHolder>() {

    private var dataList: List<Foto> = emptyList()

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

    internal fun setHeroes(fotos: List<Foto>) {
        this.dataList = fotos
        notifyDataSetChanged()
    }


    class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: Foto){

            val ivFotoLugar = itemView.findViewById(R.id.ivFotoLugar) as ImageView
            Picasso.get().load(dataItem.link_large).into(ivFotoLugar)

            Log.d("BBB", dataItem.toString())

            itemView.tag = dataItem
        }

    }
}