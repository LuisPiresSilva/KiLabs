package net.luispiressilva.kilabs_luis_silva.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import net.luispiressilva.kilabs_luis_silva.R
import net.luispiressilva.kilabs_luis_silva.helpers.hpInTransaction
import net.luispiressilva.kilabs_luis_silva.ui.main.MainFragment


class MainActivity : AppCompatActivity() {


    //no need to be preserved only needed when we do a fragment based navigation
    // to hide parent without its reference (fragment, ID, TAG, etc)
    lateinit var lastFragment : Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.hpInTransaction {
                val frag = MainFragment.newInstance()
                replace(R.id.main_fragment_container, frag, MainFragment.TAG)
                runOnCommit { lastFragment =  frag}
            }
        }
    }


    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount

        if (count == 0) {
            super.onBackPressed()

        } else {
            supportFragmentManager.popBackStack()
        }

    }
}
