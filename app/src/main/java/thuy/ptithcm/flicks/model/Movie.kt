package thuy.ptithcm.flicks.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class MovieList(
    val dates: Dates?,
    val page: Int?,
    val results: ArrayList<Movie>?,
    val total_pages: Int?,
    val total_results: Int?
)

data class Dates(
    val maximum: String?,
    val minimum: String?
)

@Parcelize
data class Movie(
    val adult: Boolean?,
    val backdrop_path: String?,
    val genre_ids: List<Int>?,
    val id: Int?,
    val original_language: String?,
    val original_title: String?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String?,
    val release_date: String?,
    val title: String?,
    val video: Boolean?,
    val vote_average: Double?,
    val vote_count: Int?
) : Parcelable