package net.luispiressilva.kilabs_luis_silva.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.luispiressilva.kilabs_luis_silva.R
import net.luispiressilva.kilabs_luis_silva.helpers.hpInTransaction
import net.luispiressilva.kilabs_luis_silva.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.hpInTransaction {
                add(R.id.main_fragment_container, MainFragment.newInstance(), MainFragment.TAG)
            }
        }
    }


}
