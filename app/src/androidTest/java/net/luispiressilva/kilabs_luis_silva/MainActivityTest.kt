package net.luispiressilva.kilabs_luis_silva


import android.content.pm.ActivityInfo
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import net.luispiressilva.kilabs_luis_silva.ui.MainActivity
import net.luispiressilva.kilabs_luis_silva.ui.main.MainFragment
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    private lateinit var act: MainActivity


    @Before
    fun testSetup() {
        act = mActivityTestRule.activity
    }


    @Test
    fun checkInitialState() {

        //we have 1 fragment in the stack
        assert(act.supportFragmentManager.backStackEntryCount == 1)

        //the current fragment is MainFragment
        assert(act.supportFragmentManager.findFragmentByTag(MainFragment.TAG) != null)

    }

    @Test
    fun checkNoMore_NoLess_Same_Fragments() {

        val testFragRef = act.supportFragmentManager.findFragmentByTag(MainFragment.TAG)!!

        assert(act.supportFragmentManager.backStackEntryCount == 1)


        mActivityTestRule.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE


        //backstack is still the same after configuration change
        assert(act.supportFragmentManager.backStackEntryCount == 1)

        //fragment is still the same after configuration change
        assert(act.supportFragmentManager.findFragmentByTag(MainFragment.TAG) == testFragRef)

    }
}
