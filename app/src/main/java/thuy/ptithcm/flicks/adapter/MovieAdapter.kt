package thuy.ptithcm.flicks.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import thuy.ptithcm.flicks.R
import thuy.ptithcm.flicks.model.Movie
import android.util.Log
import thuy.ptithcm.flicks.model.Youtube


class MovieAdapter(
    private val context: Context,
    private var listMovieInfor: List<Movie>? = null,
//    private var listYoutube: List<Youtube>? = null,
    val listener: (id: Int, position : Int) -> Unit
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
                PosterViewHolder(context, view)
            }
            TYPE_VIDEO -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.item_video, parent, false)
                VideoViewHolder(context, view, listener)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    fun updateData(list: List<Movie>) {
        listMovieInfor = list
        notifyDataSetChanged()
    }
//
//    fun updateYouTube(list: List<Youtube>) {
//        listMovieInfor = list
//        notifyDataSetChanged()
//    }

    fun clear() {
        with(listMovieInfor) {
            clear()
        }
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        val comparable = listMovieInfor?.get(position)?.vote_average
        Log.d("fieldtyppe", comparable.toString())
        if (comparable != null) {
            return when {
                comparable <= 5 -> TYPE_POSTER
                comparable > 5 -> TYPE_VIDEO
                else -> throw IllegalArgumentException("Invalid type of data " + position)
            }
        } else return TYPE_POSTER
    }

    override fun getItemCount(): Int {
        return listMovieInfor!!.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val element = listMovieInfor?.get(position)
        when (holder) {
            is PosterViewHolder -> element?.let { holder.bind(it) }
            is VideoViewHolder -> element?.let { holder.bind(it) }
            else -> throw IllegalArgumentException()
        }
    }
}