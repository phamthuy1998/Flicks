package thuy.ptithcm.flicks.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_trailer.view.*
import thuy.ptithcm.flicks.R
import thuy.ptithcm.flicks.interface1.TrailerEvent
import thuy.ptithcm.flicks.model.Movie
import thuy.ptithcm.flicks.model.Youtube
import android.graphics.Bitmap
import android.util.DisplayMetrics
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.item_video.view.*
import thuy.ptithcm.flicks.utils.IMAGE_URL


class TrailerAdapter(
    private var listYoutube: ArrayList<Youtube>? = arrayListOf(),
    private var trailerEvent: TrailerEvent,
    private var movie: Movie? = null
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

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

    fun updateData(list: ArrayList<Youtube>) {
        listYoutube?.addAll(list ?: arrayListOf())
        notifyDataSetChanged()
    }

    fun removeAllData() {
        listYoutube = arrayListOf()
        notifyDataSetChanged()
    }

    fun addData(list: ArrayList<Youtube>) {
        listYoutube = list
    }

    inner class TrailerViewHolder(view: View) : BaseViewHolder<View>(view) {
        override fun bind(movie: Movie?) {}

        override fun bind(position: Int?) {
            youtube = position?.let { listYoutube?.get(it) }
            itemView.tv_title_trailer.text = youtube?.name
            //set image rounded
            val multi = MultiTransformation<Bitmap>(
                RoundedCornersTransformation(7, 0, RoundedCornersTransformation.CornerType.ALL)
            )
            Glide.with(itemView)
                .load(IMAGE_URL + movie?.backdrop_path)
                .apply(RequestOptions.bitmapTransform(multi))
                .into(itemView.im_trailer_item)
            itemView.im_trailer_item.setOnClickListener {trailerEvent.onItemTrailerClick(youtube)}
            itemView.btn_play_video.setOnClickListener {trailerEvent.onItemTrailerClick(youtube)}
        }
    }
}