package thuy.ptithcm.flicks.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import thuy.ptithcm.flicks.R
import thuy.ptithcm.flicks.model.Movie

class MovieAdapter(
    private val context: Context,
    private var items: List<Movie>?
) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {
    companion object {
        private const val TYPE_POSTER = 0
        private const val TYPE_VIDEO = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return when (viewType) {
            TYPE_POSTER -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.item_poster, parent, false)
                PosterViewHolder(view)
            }
            TYPE_VIDEO -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.item_video, parent, false)
                VideoViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    fun updateData(list: List<Movie>) {
        items = list
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        val comparable = items?.get(position)?.vote_average
        if (comparable != null) {
            return when {
                comparable <= 5 -> TYPE_POSTER
                comparable > 5 -> TYPE_VIDEO
                else -> throw IllegalArgumentException("Invalid type of data " + position)
            }
        } else return TYPE_POSTER
    }

    override fun getItemCount(): Int {
        return items!!.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val element = items?.get(position)
        when (holder) {
            is PosterViewHolder -> element?.let { holder.bind(it) }
            is VideoViewHolder -> element?.let { holder.bind(it) }
            else -> throw IllegalArgumentException()
        }
    }
}