package thuy.ptithcm.flicks.adapter

import android.content.ContentValues.TAG
import android.content.Context
import android.provider.SyncStateContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubeThumbnailLoader
import com.google.android.youtube.player.YouTubeThumbnailLoader.OnThumbnailLoadedListener
import com.google.android.youtube.player.YouTubeThumbnailView
import kotlinx.android.synthetic.main.item_trailer.view.*
import thuy.ptithcm.flicks.R
import thuy.ptithcm.flicks.model.Movie
import thuy.ptithcm.flicks.model.Youtube
import thuy.ptithcm.flicks.utils.YOUTUBE_API


class TrailerAdapter(private var listYoutube: ArrayList<Youtube>?,
                     private val context: Context?,
                     var trailerAdapterEvent: TrailerAdapterEvent

) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var youtube: Youtube? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val view = LayoutInflater
            .from(viewGroup.context)
            .inflate(R.layout.item_trailer, viewGroup, false)
        return TrailerViewHolder(view, trailerAdapterEvent)
    }

    override fun getItemCount(): Int {
        return listYoutube?.size ?: 0
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        holder.bind(listYoutube?.get(position))
    }

    inner class TrailerViewHolder(view: View, trailerAdapterEvent: TrailerAdapterEvent
    ) : BaseViewHolder<View>(view)  {
        override fun bind(movie: Movie?) {}

        override fun bind(position: Int?) {}

        override fun bind(trailer: Youtube?) {
            itemView.tv_title_trailer.text = trailer?.name
            itemView.videoThumbnailImageView.setOnClickListener {
                trailerAdapterEvent.onItemMovieClick(trailer)
            }

            itemView.videoThumbnailImageView.initialize(
                YOUTUBE_API,
                object : YouTubeThumbnailView.OnInitializedListener {
                    override fun onInitializationSuccess(
                        youTubeThumbnailView: YouTubeThumbnailView,
                        youTubeThumbnailLoader: YouTubeThumbnailLoader
                    ) { //when initialization is sucess, set the video id to thumbnail to load
                        youTubeThumbnailLoader.setVideo(trailer?.source)
                        youTubeThumbnailLoader.setOnThumbnailLoadedListener(object :
                            OnThumbnailLoadedListener {
                            override fun onThumbnailLoaded(
                                youTubeThumbnailView: YouTubeThumbnailView,
                                s: String
                            ) { //when thumbnail loaded successfully release the thumbnail loader as we are showing thumbnail in adapter
                                youTubeThumbnailLoader.release()
                            }

                            override fun onThumbnailError(
                                youTubeThumbnailView: YouTubeThumbnailView,
                                errorReason: YouTubeThumbnailLoader.ErrorReason
                            ) { //print or show error when thumbnail load failed
                            }
                        })
                    }

                    override fun onInitializationFailure(
                        youTubeThumbnailView: YouTubeThumbnailView,
                        youTubeInitializationResult: YouTubeInitializationResult
                    ) { //print or show error when initialization failed
                    }
                })
        }

    }

}