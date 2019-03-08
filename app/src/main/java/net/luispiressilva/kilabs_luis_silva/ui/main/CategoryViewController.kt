package net.luispiressilva.kilabs_luis_silva.ui.main

import io.reactivex.disposables.Disposable
import net.luispiressilva.kilabs_luis_silva.model.PhotoFlickr

/**
 * Created by Luis Silva on 07/03/2019.
 */
class CategoryViewController(
    var state : UIState = UIState.LOADING, //initial state
    val list : MutableList<PhotoFlickr> = ArrayList<PhotoFlickr>(),
    var fetcher: Disposable? = null
) {


    //    UI STATE
    // we have 5 states
    enum class UIState {
        LOADING, //no content just loading
        CONTENT, //we already have data, we can load more
        CONTENT_ERROR, //we already have data and are showing, load more error (user should retry to load more)
        NOCONTENT, //no content at all
        NOCONTENT_ERROR //no content and we got an error when loading content
    }



    enum class Order {
        ASCENDING,
        DESCENDING
    }
    fun orderByDate(order : Order) : List<PhotoFlickr> {
        when(order) {
            Order.ASCENDING -> return list.sortedBy { it.owner }
            Order.DESCENDING -> return list.sortedByDescending { it.owner }
        }
    }

}