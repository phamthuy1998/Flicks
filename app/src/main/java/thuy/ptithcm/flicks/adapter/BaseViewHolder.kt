package thuy.ptithcm.flicks.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import thuy.ptithcm.flicks.model.Movie

abstract class BaseViewHolder<view: View>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(movie: Movie)

}