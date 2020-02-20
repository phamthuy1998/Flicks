package thuy.ptithcm.flicks.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.view.View
import android.widget.TextView
import androidx.core.view.doOnPreDraw
import thuy.ptithcm.flicks.model.Movie
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.item_poster.view.*
import thuy.ptithcm.flicks.R
import thuy.ptithcm.flicks.activity.DetailPosterFilmActivity
import thuy.ptithcm.flicks.model.Youtube
import thuy.ptithcm.flicks.utils.IMAGE_URL


class PosterViewHolder(val context: Context, itemView: View) : BaseViewHolder<View>(itemView) {

    override fun bind(movie: Movie?) {
        val multi = MultiTransformation<Bitmap>(
            RoundedCornersTransformation(7, 0, RoundedCornersTransformation.CornerType.ALL)
        )

        //set image rounded
        Glide.with(itemView)
            .load(IMAGE_URL + movie?.poster_path)
            .apply(RequestOptions.bitmapTransform(multi))
            .into(itemView.iv_poster)

        itemView.tv_title_poster.text = movie?.title
        itemView.tv_overview_poster.text = movie?.overview

        // intent into DetailPosterFilmActivity
        itemView.setOnClickListener {
            val intent = Intent(context, DetailPosterFilmActivity.getInstance().javaClass)
            intent.putExtra("id", movie?.id)
            context.startActivity(intent)
        }
    }
}