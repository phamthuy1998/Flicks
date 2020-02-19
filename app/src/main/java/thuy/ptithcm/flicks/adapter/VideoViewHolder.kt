package thuy.ptithcm.flicks.adapter

import android.content.res.Configuration
import android.view.View
import kotlinx.android.synthetic.main.item_video.view.*
import thuy.ptithcm.flicks.model.Movie

class VideoViewHolder(itemView: View) : BaseViewHolder<View>(itemView) {
    override fun bind(movie: Movie) {

        val orientation = itemView.context.getResources().getConfiguration().orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            itemView.tv_title_video?.text = movie.title
            itemView.tv_overview_video?.text = movie.overview
        }
    }
}