package thuy.ptithcm.flicks.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerFragment
import kotlinx.android.synthetic.main.item_trailer.view.*
import thuy.ptithcm.flicks.R
import thuy.ptithcm.flicks.model.Movie
import thuy.ptithcm.flicks.model.Youtube
import thuy.ptithcm.flicks.utils.YOUTUBE_API



class TrailerAdapter(private var listYoutube: ArrayList<Youtube>?, private val context: Context?) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var youtube: Youtube? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val view = LayoutInflater
            .from(viewGroup.context)
            .inflate(R.layout.item_trailer, viewGroup, false);
        return TrailerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listYoutube?.size ?: 0
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        holder.bind(position)
    }

    inner class TrailerViewHolder(view: View) : BaseViewHolder<View>(view)  {
        override fun bind(movie: Movie?) {}

        override fun bind(position: Int?) {
            youtube = position?.let { listYoutube?.get(it) }
            itemView.tv_title_trailer.text = youtube?.name

//            val youtubeFragment =
//                itemView.findViewById(thuy.ptithcm.flicks.R.id.movie_player_detail) as YouTubePlayerFragment
//
//            youtubeFragment.initialize(
//                YOUTUBE_API,
//                object : YouTubePlayer.OnInitializedListener {
//                    override fun onInitializationSuccess(
//                        provider: YouTubePlayer.Provider,
//                        youTubePlayer: YouTubePlayer, b: Boolean
//                    ) { // do any work here to
//                        youTubePlayerG = youTubePlayer
//                        movie?.id?.let { trailerViewModel.getTrailer(it) }
//                    }
//
//                    override fun onInitializationFailure(
//                        provider: YouTubePlayer.Provider,
//                        youTubeInitializationResult: YouTubeInitializationResult
//                    ) {
//                    }
//                })
        }

    }

}