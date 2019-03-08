package net.luispiressilva.kilabs_luis_silva.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


/**
 * Created by Luis Silva on 06/03/2019.
 */
data class PhotoFlickr
    (
    @SerializedName("id")
    val id: String,
    @SerializedName("owner")
    var owner: String = "",
    @SerializedName("secret")
    var secret: String = "",
    @SerializedName("server")
    var server: String = "",
    @SerializedName("farm")
    var farm: Int = 0,
    @SerializedName("title")
    var title: String = "",
    @SerializedName("ispublic")
    var ispublic: Int = 0,
    @SerializedName("isfriend")
    var isfriend: Int = 0,
    @SerializedName("isfamily")
    var isfamily: Int = 0,
    @SerializedName("url_c")
    var urlC: String = "",
    @SerializedName("height_c")
    var heightC: String = "",
    @SerializedName("width_c")
    var widthC: String = "",
    @SerializedName("url_o")
    var urlO: String = "",
    @SerializedName("height_o")
    var heightO: String = "",
    @SerializedName("width_o")
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




