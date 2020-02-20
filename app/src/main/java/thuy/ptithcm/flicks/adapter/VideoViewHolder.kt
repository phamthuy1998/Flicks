package thuy.ptithcm.flicks.adapter

import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_video.view.*
import thuy.ptithcm.flicks.model.Movie
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerFragment
import com.google.android.youtube.player.YouTubePlayerView
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.item_poster.view.*
import thuy.ptithcm.flicks.model.Youtube
import thuy.ptithcm.flicks.utils.IMAGE_URL
import thuy.ptithcm.flicks.utils.YOUTUBE_API


class VideoViewHolder(val context: Context, itemView: View, val listener: (id: Int, position : Int) -> Unit) :
    BaseViewHolder<View>(itemView) {
    override fun bind(movie: Movie?) {

        val orientation = context.getResources().getConfiguration().orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            itemView.tv_title_video?.text = movie?.title
            itemView.tv_overview_video?.text = movie?.overview
        }

        itemView.iv_trailer.setOnClickListener {
            movie?.id?.let { it1 -> listener(it1,adapterPosition) }

        }

        //set image rounded
        val multi = MultiTransformation<Bitmap>(
            RoundedCornersTransformation(7, 0, RoundedCornersTransformation.CornerType.ALL)
        )
        Glide.with(itemView)
            .load(IMAGE_URL + movie?.backdrop_path)
            .apply(RequestOptions.bitmapTransform(multi))
            .into(itemView.iv_trailer)


        Log.d("sizeArr",movie?.listYoutube?.size.toString())
//        val youTubePlayerView =
//            itemView.findViewById(thuy.ptithcm.flicks.R.id.youtube_player) as YouTubePlayerView
//
//        youTubePlayerView.initialize(
//            YOUTUBE_API,
//            object : YouTubePlayer.OnInitializedListener {
//                override fun onInitializationSuccess(
//                    provider: YouTubePlayer.Provider,
//                    youTubePlayer: YouTubePlayer, b: Boolean
//                ) {
//
//                    // do any work here to cue video, play video, etc.
//                    youTubePlayer.cueVideo(list?.get(0)?.source)
//                }
//
//                override fun onInitializationFailure(
//                    provider: YouTubePlayer.Provider,
//                    youTubeInitializationResult: YouTubeInitializationResult
//                ) {
//
//                }
//            })
    }
}