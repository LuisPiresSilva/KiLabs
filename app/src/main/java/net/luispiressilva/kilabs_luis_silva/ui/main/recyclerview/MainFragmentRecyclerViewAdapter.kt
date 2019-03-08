package net.luispiressilva.kilabs_luis_silva.ui.main.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import net.luispiressilva.kilabs_luis_silva.R
import net.luispiressilva.kilabs_luis_silva.model.PhotoFlickr
import net.luispiressilva.kilabs_luis_silva.ui.main.MainFragment

/**
 * Created by Luis Silva on 06/03/2019.
 */
class MainFragmentRecyclerViewAdapter(
    mainFragment: MainFragment,
    private val category: String
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    interface AdapterCallBack {
        fun photoClick(photo: PhotoFlickr, holder: RecyclerView.ViewHolder)
    }

    private var callback: AdapterCallBack = mainFragment


    private val mDiffer = AsyncListDiffer(
        this,
        PhotoRecyclerViewDiffCallback()
    )

    fun setList(list: List<PhotoFlickr>) {
        mDiffer.submitList(list)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PhotoRecyclerViewViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.main_fragment_category_view_photo_viewholder,
                parent,
                false
            )
        )


    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PhotoRecyclerViewViewHolder -> {
                holder.bind(holder.itemView, mDiffer.currentList[position], callback)
            }
        }
    }

    override fun getItemCount(): Int = mDiffer.currentList.size

}