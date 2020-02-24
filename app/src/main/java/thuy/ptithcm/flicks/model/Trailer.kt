package thuy.ptithcm.flicks.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class Trailer(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("quicktime")
    val quicktime: List<Any>?,

    @SerializedName("youtube")
    val youtube: ArrayList<Youtube>?
)
@Parcelize
data class Youtube(
    @SerializedName("name")
    val name: String?,

    @SerializedName("size")
    val size: String?,

    @SerializedName("source")
    val source: String?,

    @SerializedName("type")
    val type: String?
): Parcelable