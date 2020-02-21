package thuy.ptithcm.flicks.adapter

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import thuy.ptithcm.flicks.R
import thuy.ptithcm.flicks.model.Movie
import android.util.Log
import thuy.ptithcm.flicks.model.Youtube


class MovieAdapter(
    private val context: Context,
    private var listMovieInfor: List<Movie?>? = null,
    var movieAdapterEvent: MovieAdapterEvent
    // higher function
//    val listener: (id: Int, position : Int) -> Unit
) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {
    companion object {
        private const val TYPE_POSTER = 0
        private const val TYPE_VIDEO = 1
        private const val TYPE_LOADING = 2
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
            TYPE_LOADING -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.item_loading, parent, false)
                LoadingViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    fun updateData(list: List<Movie>) {
        listMovieInfor = list
        notifyDataSetChanged()
    }

    fun clear() {
        with(listMovieInfor) {
            clear()
        }
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        val comparable = listMovieInfor?.get(position)?.vote_average
        if (comparable != null) {
            return when {
                comparable <= 5 -> TYPE_POSTER
                comparable > 5 -> TYPE_VIDEO
                else -> throw IllegalArgumentException("Invalid type of data " + position)
            }
        } else return TYPE_POSTER
    }

    fun addLoadingView() {
        //Add loading item
        Handler().post {
            listMovieInfor?.toMutableList()?.add(null)
            //notifyItemInserted(listMovieInfor.size - 1)
            listMovieInfor?.size?.minus(1)?.let { notifyItemInserted(it) }
        }
    }

    fun removeLoadingView() {
        //Remove loading item
        if (listMovieInfor?.size != 0) {
            // listMovieInfor?.toMutableList()?.removeAt(listMovieInfor?.size - 1)
            listMovieInfor?.size?.minus(1)?.let { listMovieInfor?.toMutableList()?.removeAt(it) }
            listMovieInfor?.size?.let { notifyItemRemoved(it) }
        }
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