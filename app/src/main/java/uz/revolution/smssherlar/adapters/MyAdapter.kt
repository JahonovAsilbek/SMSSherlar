package uz.revolution.smssherlar.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_card.view.*
import uz.revolution.smssherlar.R
import uz.revolution.smssherlar.models.Sher

class MyAdapter(var data:ArrayList<Sher>):RecyclerView.Adapter<MyAdapter.VH>() {

    private var onItemClick: OnItemClick? = null

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(sher: Sher,position: Int) {
            itemView.sarlavha.text = sher.sarlavha
            itemView.matn.text = sher.matn

            itemView.setOnClickListener {
                if (onItemClick != null) {
                    onItemClick!!.onClick(sher,itemView,position)
                }
            }

            if (sher.liked) {
                itemView.liked_btn.visibility = View.VISIBLE
            } else {
                itemView.liked_btn.visibility = View.INVISIBLE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(data[position],position)
    }

    override fun getItemCount(): Int = data.size

    interface OnItemClick{
        fun onClick(sher: Sher, itemView: View, position: Int)
    }

    fun setOnItemClick(onItemClick: OnItemClick) {
        this.onItemClick=onItemClick
    }

}