package thuy.ptithcm.flicks.adapter

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import thuy.ptithcm.flicks.R
import thuy.ptithcm.flicks.model.Movie
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import thuy.ptithcm.flicks.model.Youtube


class MovieAdapter(
    private val context: Context,
    private var listMovieInfor: ArrayList<Movie>? = arrayListOf(),
    var movieAdapterEvent: MovieAdapterEvent
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
                PosterViewHolder(movieAdapterEvent, view)
            }
            TYPE_VIDEO -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.item_video, parent, false)
                VideoViewHolder(movieAdapterEvent, context, view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    fun updateData(list: ArrayList<Movie>) {
        listMovieInfor?.addAll(list ?: arrayListOf())
        notifyDataSetChanged()
    }

    fun removeAllData() {
        listMovieInfor = arrayListOf()
        notifyDataSetChanged()
    }

    fun addData(list: ArrayList<Movie>) {
        listMovieInfor = list
    }

    override fun getItemViewType(position: Int): Int {
        val comparable = listMovieInfor?.get(position)?.vote_average
        if (comparable != null) {
            return when {
                comparable <= 5 -> TYPE_POSTER
                comparable > 5 -> TYPE_VIDEO
                else -> TYPE_POSTER
            }
        } else return TYPE_POSTER
    }

    override fun getItemCount(): Int {
        return listMovieInfor?.size ?: 0
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        if (position == listMovieInfor?.size?.minus(1)) {
            movieAdapterEvent.onLoadMore()
        } else {
            val element = listMovieInfor?.get(position)
            when (holder) {
                is PosterViewHolder -> element?.let {
                    holder.bind(it)
                }
                is VideoViewHolder -> element?.let {
                    holder.bind(it)
                }
                else -> throw IllegalArgumentException()
            }
        }
    }
}