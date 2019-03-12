package net.luispiressilva.kilabs_luis_silva.ui.main.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.main_fragment_category_view_photo_viewholder.view.*
import net.luispiressilva.kilabs_luis_silva.R
import net.luispiressilva.kilabs_luis_silva.model.PhotoFlickr

/**
 * Created by Luis Silva on 06/03/2019.
 */
class PhotoRecyclerViewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    fun bind(
        view: View,
        photo: PhotoFlickr,
        callback: MainFragmentRecyclerViewAdapter.AdapterCallBack
    ) {

        val url = if (photo.urlC.isBlank()) photo.urlO else photo.urlC

        Glide.with(view.context).load(url)
            .apply(
                RequestOptions().error(R.drawable.ic_launcher_background)
            )
            .into(view.main_fragment_category_view_category_viewholder_image)



        view.main_fragment_category_view_category_viewholder_title.text = photo.title


        view.setOnClickListener {
            callback.photoClick(photo, this)
        }


    }

}