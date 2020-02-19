package thuy.ptithcm.flicks.adapter

import android.graphics.Bitmap
import android.view.View
import thuy.ptithcm.flicks.model.Movie
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.item_poster.view.*
import thuy.ptithcm.flicks.R


class PosterViewHolder(itemView: View) : BaseViewHolder<View>(itemView) {
    override fun bind(movie: Movie) {
        val multi = MultiTransformation<Bitmap>(
            BlurTransformation(25),
            RoundedCornersTransformation(128, 0, RoundedCornersTransformation.CornerType.ALL)
        )

        //set image rounded
        Glide.with(itemView)
            .load(movie.poster_path)
            .apply(RequestOptions.bitmapTransform(multi))
            .into(itemView.iv_poster)
        itemView.tv_title_poster.text = movie.title
        itemView.tv_overview_poster.text = movie.overview
    }
}