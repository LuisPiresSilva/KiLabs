package net.luispiressilva.kilabs_luis_silva.ui.photo_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import net.luispiressilva.kilabs_luis_silva.R

class PhotoDetail : Fragment() {

    companion object {
        const val TAG = "PHOTODETAIL_FRAGMENT"
        fun newInstance() = PhotoDetail()
    }

    private lateinit var viewModel: PhotoDetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.photo_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PhotoDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
