package net.luispiressilva.kilabs_luis_silva.model

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


/**
 * Created by Luis Silva on 06/03/2019.
 */
@JsonClass(generateAdapter = true)
data class PhotoFlickr
    (
    @Json(name = "id")
    val id: String,
    @Json(name ="owner")
    var owner: String = "",
    @Json(name ="secret")
    var secret: String = "",
    @Json(name ="server")
    var server: String = "",
    @Json(name ="farm")
    var farm: Int = 0,
    @Json(name ="title")
    var title: String = "",
    @Json(name ="ispublic")
    var ispublic: Int = 0,
    @Json(name ="isfriend")
    var isfriend: Int = 0,
    @Json(name ="isfamily")
    var isfamily: Int = 0,
    @Json(name ="url_c")
    var urlC: String = "",
    @Json(name ="height_c")
    var heightC: String = "",
    @Json(name ="width_c")
    var widthC: String = "",
    @Json(name ="url_o")
    var urlO: String = "",
    @Json(name ="height_o")
    var heightO: String = "",
    @Json(name ="width_o")
    var widthO: String = ""
) : Parcelable {

    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readInt(),
        source.readString(),
        source.readInt(),
        source.readInt(),
        source.readInt(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString()
    )


    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(id)
        writeString(owner)
        writeString(secret)
        writeString(server)
        writeInt(farm)
        writeString(title)
        writeInt(ispublic)
        writeInt(isfriend)
        writeInt(isfamily)
        writeString(urlC)
        writeString(heightC)
        writeString(widthC)
        writeString(urlO)
        writeString(heightO)
        writeString(widthO)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<PhotoFlickr> = object : Parcelable.Creator<PhotoFlickr> {
            override fun createFromParcel(source: Parcel): PhotoFlickr = PhotoFlickr(source)
            override fun newArray(size: Int): Array<PhotoFlickr?> = arrayOfNulls(size)
        }
    }
    //generated code use (Parcelable Code Generator(for kotlin)) plugin

}




