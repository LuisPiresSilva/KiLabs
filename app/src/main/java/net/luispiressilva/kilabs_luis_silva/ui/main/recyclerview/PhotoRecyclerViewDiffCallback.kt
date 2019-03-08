package net.luispiressilva.kilabs_luis_silva.ui.main.recyclerview

import androidx.recyclerview.widget.DiffUtil
import net.luispiressilva.kilabs_luis_silva.model.PhotoFlickr

class PhotoRecyclerViewDiffCallback : DiffUtil.ItemCallback<PhotoFlickr>() {

    override fun areItemsTheSame(oldItem: PhotoFlickr, newItem: PhotoFlickr): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PhotoFlickr, newItem: PhotoFlickr): Boolean {
        return oldItem.urlC == newItem.urlC && oldItem.title == newItem.title
    }

}