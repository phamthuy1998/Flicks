package thuy.ptithcm.flicks.adapter

import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_video.view.*
import thuy.ptithcm.flicks.model.Movie
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import thuy.ptithcm.flicks.utils.IMAGE_URL


class VideoViewHolder(
    private val movieAdapterEvent: MovieAdapterEvent,
    val context: Context,
    itemView: View
) :
    BaseViewHolder<View>(itemView) {
    override fun bind(position: Int?) {}

    override fun bind(movie: Movie?) {

        val orientation = context.getResources().getConfiguration().orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            itemView.tv_title_video?.text = movie?.title
            itemView.tv_overview_video?.text = movie?.overview
        }

        //set image rounded
        val multi = MultiTransformation<Bitmap>(
            RoundedCornersTransformation(7, 0, RoundedCornersTransformation.CornerType.ALL)
        )

        Glide.with(itemView)
            .load(IMAGE_URL + movie?.backdrop_path)
            .apply(RequestOptions.bitmapTransform(multi))
            .into(itemView.iv_trailer)

        itemView.setOnClickListener {
            movieAdapterEvent.onItemMovieClick(movie)
        }
    }
}