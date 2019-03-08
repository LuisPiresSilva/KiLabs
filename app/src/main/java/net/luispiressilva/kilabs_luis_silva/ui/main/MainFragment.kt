package net.luispiressilva.kilabs_luis_silva.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.main_fragment.view.*
import kotlinx.android.synthetic.main.main_fragment_category_view.view.*
import net.luispiressilva.kilabs_luis_silva.*
import net.luispiressilva.kilabs_luis_silva.components.viewmodel.ViewModelFactory
import net.luispiressilva.kilabs_luis_silva.di.component.DaggerViewModelComponent
import net.luispiressilva.kilabs_luis_silva.di.modules.network.NetworkModule
import net.luispiressilva.kilabs_luis_silva.di.modules.network.OkHttpClientModule
import net.luispiressilva.kilabs_luis_silva.model.PhotoFlickr
import net.luispiressilva.kilabs_luis_silva.ui.main.recyclerview.MainFragmentRecyclerViewAdapter
import javax.inject.Inject


class MainFragment : Fragment(),
    MainFragmentRecyclerViewAdapter.AdapterCallBack,
    Contracts.IFlickrPhotosView {

    //map that contains earch row views based on a key (category)
    private lateinit var viewMap: HashMap<String, View>

    companion object {
        const val TAG = "MAIN_FRAGMENT"
        fun newInstance() = MainFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var presenter: MainViewModel


    //lifecycle functions are ordered
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewMap = HashMap()


        //prepare dependencies
        //presenter comunicates with bottom layers
        DaggerViewModelComponent.builder()
            .applicationContext(KiLabsApp.app)
            .useCache(OkHttpClientModule.UseCache(false))
            .host(NetworkModule.Host(FLICKR_URL))
            .build()
            .inject(this)


        presenter = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        presenter.attachView(this, lifecycle)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //dinamicaly add each category row view (ie, view that contains: list, error with no content scenario, no content scenario and loading)
        addCategory(KITTENS)
        addCategory(DOGS)
        addCategory(PUBLIC_FEED)


        view.main_fragment_list_maincontainer_swiperefresh.setOnRefreshListener {
            presenter.reset(KITTENS)
            presenter.reset(DOGS)
            presenter.reset(PUBLIC_FEED)
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        presenter.start(KITTENS)
        presenter.start(DOGS)
        presenter.start(PUBLIC_FEED)
    }


    override fun photoClick(photo: PhotoFlickr, holder: RecyclerView.ViewHolder) {
//        activity?.supportFragmentManager?.hpInTransaction {
//            addSharedElement(holder.itemView.main_fragment_category_view_category_viewholder_image, "")
//            add(R.id.container, PhotoDetail.newInstance(), PhotoDetail.TAG)
//        }
    }


    override fun showLoading(category: String) {
        main_fragment_list_maincontainer_swiperefresh.isRefreshing = false

        if (viewMap.containsKey(category)) {
            viewMap[category]?.main_fragment_category_view_category_list?.visibility = View.INVISIBLE
            viewMap[category]?.main_fragment_category_view_category_no_content_main_container?.visibility = View.VISIBLE
            viewMap[category]?.main_fragment_category_view_category_no_content_container?.visibility = View.GONE
            viewMap[category]?.main_fragment_category_view_category_no_content_loading_progressBar?.visibility =
                View.VISIBLE
        }
    }

    override fun showPhotos(category: String, photos: List<PhotoFlickr>) {
        main_fragment_list_maincontainer_swiperefresh.isRefreshing = false

        if (viewMap.containsKey(category)) {
            viewMap[category]?.main_fragment_category_view_category_list?.visibility = View.VISIBLE
            viewMap[category]?.main_fragment_category_view_category_no_content_main_container?.visibility = View.GONE
            viewMap[category]?.main_fragment_category_view_category_no_content_container?.visibility = View.GONE
            viewMap[category]?.main_fragment_category_view_category_no_content_loading_progressBar?.visibility =
                View.GONE
            (viewMap[category]?.main_fragment_category_view_category_list?.adapter as MainFragmentRecyclerViewAdapter).setList(
                photos
            )
        }
    }

    override fun showNoContent(category: String) {
        main_fragment_list_maincontainer_swiperefresh.isRefreshing = false

        if (viewMap.containsKey(category)) {
            viewMap[category]?.main_fragment_category_view_category_list?.visibility = View.INVISIBLE
            viewMap[category]?.main_fragment_category_view_category_no_content_main_container?.visibility = View.VISIBLE
            viewMap[category]?.main_fragment_category_view_category_no_content_loading_progressBar?.visibility =
                View.GONE
            viewMap[category]?.main_fragment_category_view_category_no_content_container?.visibility = View.VISIBLE
            viewMap[category]?.main_fragment_category_view_category_no_content_message?.text =
                getString(R.string.no_content)
            viewMap[category]?.main_fragment_category_view_category_no_content_action_button?.text =
                getString(R.string.refresh)

        }
    }

    override fun showNoContentError(category: String, error: String) {
        main_fragment_list_maincontainer_swiperefresh.isRefreshing = false

        if (viewMap.containsKey(category)) {
            viewMap[category]?.main_fragment_category_view_category_list?.visibility = View.INVISIBLE
            viewMap[category]?.main_fragment_category_view_category_no_content_main_container?.visibility = View.VISIBLE
            viewMap[category]?.main_fragment_category_view_category_no_content_loading_progressBar?.visibility =
                View.GONE
            viewMap[category]?.main_fragment_category_view_category_no_content_container?.visibility = View.VISIBLE
            viewMap[category]?.main_fragment_category_view_category_no_content_message?.text = error
            viewMap[category]?.main_fragment_category_view_category_no_content_action_button?.text =
                getString(R.string.retry)

        }
    }

    override fun showContentError(category: String, error: String) {
        //for load more scenario
        //change recycler footer from progress to retry
    }

    //we add each category row dinamicaly
    //each row has a title, more, RecyclerView and no content dedicated layout
    override fun addCategory(category: String) {
        if (!viewMap.containsKey(category)) {

            val view =
                layoutInflater.inflate(R.layout.main_fragment_category_view, main_fragment_list_maincontainer, false)

            view.id = ViewCompat.generateViewId()
            view.tag = TAG + category


            with(view.main_fragment_category_view_category_list) {
                layoutManager = LinearLayoutManager(this@MainFragment.context, RecyclerView.HORIZONTAL, false)
                adapter = MainFragmentRecyclerViewAdapter(this@MainFragment, category)
            }


            //both no content error or no content request refresh buttons (same button)
            view.main_fragment_category_view_category_no_content_action_button.setOnClickListener {
                presenter.getCategoryPhotos(category)
            }

//            //problem lies now here, we could not use local localization unless we have fixed categories from server
//            //we could rely on
//            //either way for this exercise much better this way (avoid lots of boilerplate)
            when (category) {
                KITTENS -> view.main_fragment_category_view_category_title.text =
                    resources.getString(R.string.main_fragment_category_view_kittens_title)
                DOGS -> view.main_fragment_category_view_category_title.text =
                    resources.getString(R.string.main_fragment_category_view_dogs_title)
                PUBLIC_FEED -> view.main_fragment_category_view_category_title.text =
                    resources.getString(R.string.main_fragment_category_view_public_feed_title)
            }

            viewMap[category] = view

            //-1 because of the dummy already present empty FrameLayout
            main_fragment_list_maincontainer.addView(view, main_fragment_list_maincontainer.childCount - 1)
        }
    }

}
