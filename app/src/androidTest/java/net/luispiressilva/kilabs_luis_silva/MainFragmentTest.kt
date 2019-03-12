package net.luispiressilva.kilabs_luis_silva


import android.content.pm.ActivityInfo
import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import net.luispiressilva.kilabs_luis_silva.model.PhotoFlickr
import net.luispiressilva.kilabs_luis_silva.ui.TestFragmentActivity
import net.luispiressilva.kilabs_luis_silva.ui.main.CategoryViewController
import net.luispiressilva.kilabs_luis_silva.ui.main.MainFragment
import net.luispiressilva.kilabs_luis_silva.ui.main.MainViewModel
import org.hamcrest.CoreMatchers.*
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito


@LargeTest
@RunWith(AndroidJUnit4::class)
class MainFragmentTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(TestFragmentActivity::class.java)


    private lateinit var mainFrag: MainFragment
    private lateinit var mockedPresenter: MainViewModel

    @Before
    fun setUp() {
        mainFrag = MainFragment.newInstance()

        mockedPresenter = Mockito.mock(MainViewModel::class.java)

        mainFrag.presenter = mockedPresenter

        mActivityTestRule.runOnUiThread {
            mActivityTestRule.activity.setFragment(mainFrag)
        }


    }


    private fun getSomeValidPhotos(): ArrayList<PhotoFlickr> {
        val apps = ArrayList<PhotoFlickr>()

        apps.add(PhotoFlickr("1"))
        apps.add(PhotoFlickr("2"))
        apps.add(PhotoFlickr("3"))
        apps.add(PhotoFlickr("4"))
        apps.add(PhotoFlickr("5"))

        return apps
    }


    private fun getViewOfCategory(view: Int, containerTag: String): Matcher<View> {
        return allOf(withId(view), isDescendantOfA(withTagValue(`is`(containerTag as Any))))
    }


    private fun getContainerTAG(category: String): String {
        return MainFragment.TAG + category
    }


    private fun checkInitialState() {
        onView(withId(R.id.main_fragment_list_maincontainer_swiperefresh)).check(matches(isDisplayed()))


        //lists are invisible (recyclerviews are heavy going from invisible to visible is much better than using GONE)

        onView(
            getViewOfCategory(
                R.id.main_fragment_category_view_category_list, getContainerTAG(KITTENS)
            )
        ).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)))

        /*
            only the progress bars are visible
         */
        onView(
            getViewOfCategory(
                R.id.main_fragment_category_view_category_no_content_loading_progressBar, getContainerTAG(KITTENS)

            )
        ).check(matches(isDisplayed()))


        onView(
            getViewOfCategory(
                R.id.main_fragment_category_view_category_no_content_container, getContainerTAG(KITTENS)
            )
        )
            .check(matches(not(isDisplayed())))


    }


    private fun checkNoContentErrorState(errorMessage: String) {
        onView(
            getViewOfCategory(
                R.id.main_fragment_category_view_category_no_content_loading_progressBar, getContainerTAG(KITTENS)
            )
        ).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))


        onView(
            getViewOfCategory(
                R.id.main_fragment_category_view_category_no_content_message, getContainerTAG(KITTENS)
            )
        )
            .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
            .check(matches(withText(errorMessage)))
        onView(
            getViewOfCategory(
                R.id.main_fragment_category_view_category_no_content_action_button, getContainerTAG(KITTENS)
            )
        )
            .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
            .check(matches(withText(mActivityTestRule.activity.resources.getString(R.string.retry))))
    }


    private fun checkNoContentEmptyDataState() {
        onView(
            getViewOfCategory(R.id.main_fragment_category_view_category_no_content_loading_progressBar, getContainerTAG(KITTENS))
        ).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))


        onView(
            getViewOfCategory(R.id.main_fragment_category_view_category_no_content_message, getContainerTAG(KITTENS))
        )
            .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
            .check(matches(withText(mActivityTestRule.activity.resources.getString(R.string.no_content))))

        onView(
            getViewOfCategory(R.id.main_fragment_category_view_category_no_content_action_button, getContainerTAG(KITTENS))
        )
            .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
            .check(matches(withText(mActivityTestRule.activity.resources.getString(R.string.refresh))))
    }


    private fun checkValidDataState() {
        onView(
            getViewOfCategory(R.id.main_fragment_category_view_category_no_content_loading_progressBar, getContainerTAG(KITTENS))
        ).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))


        onView(
            getViewOfCategory(R.id.main_fragment_category_view_category_no_content_main_container, getContainerTAG(KITTENS))
        )
            .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))


        onView(
            getViewOfCategory(R.id.main_fragment_category_view_category_list, getContainerTAG(KITTENS))
        ).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))


    }


    //TESTS START HERE


    @Test
    fun checkInitialCondition_Empty() {
        checkInitialState()
    }


    @Test
    fun checkInitialCondition_to_Error() {
        checkInitialState()

        val errorMessage = "error"

        //simulate a response from the presenter with error (mocking would be better)
        mActivityTestRule.runOnUiThread {
            mainFrag.showNoContentError(KITTENS, errorMessage)
        }


        /*
            recheck views states
            only the no content is visible and with error layout
         */
        checkNoContentErrorState(errorMessage)

    }


    @Test
    fun checkInitialCondition_to_NoData() {
        checkInitialState()

        //simulate a response from the presenter with error (mocking would be better)
        mActivityTestRule.runOnUiThread {
            mainFrag.showNoContent(KITTENS)
        }

        /*
            recheck views states
            only the no content is visible and with empty data layout
         */
        checkNoContentEmptyDataState()
    }


    @Test
    fun checkInitialCondition_to_ValidData() {

        checkInitialState()

        val apps = getSomeValidPhotos()

        //simulate a response from the presenter with error (mocking would be better)
        mActivityTestRule.runOnUiThread {
            mainFrag.showPhotos(KITTENS, apps)
        }


        /*
            recheck views states
            only the list is visible
         */
        checkValidDataState()

    }


    @Test
    fun checkRetryCalls() {
        checkInitialState()

        val errorMessage = "error"


        //simulate a response from the presenter with error (mocking would be better)
        mActivityTestRule.runOnUiThread {
            mainFrag.showNoContentError(KITTENS, errorMessage)
        }

        /*
            recheck views states
            only the no content is visible and with error layout
         */
        checkNoContentErrorState(errorMessage)


        onView(
            getViewOfCategory(
                R.id.main_fragment_category_view_category_no_content_action_button, getContainerTAG(KITTENS)
            )
        ).perform(ViewActions.click())


        Mockito.verify(mockedPresenter, Mockito.atMost(1)).getCategoryPhotos(KITTENS)

    }

    @Test
    fun checkConfigurationChange_MaintainsViews() {
        checkInitialState()

        val photos = getSomeValidPhotos()

        //simulate a response from the presenter with error (mocking would be better)
        mActivityTestRule.runOnUiThread {
            mainFrag.showPhotos(KITTENS, photos)
        }


        /*
            recheck views states
            only the list is visible
        */
        checkValidDataState()

        //we must sync the presenter first
        mainFrag.presenter.categoryMap[KITTENS]?.list?.addAll(photos)
        mainFrag.presenter.categoryMap[KITTENS]?.state = CategoryViewController.UIState.CONTENT


        mActivityTestRule.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE


        /*
            recheck views states
            only the list is visible
         */
        checkValidDataState()
    }


}
