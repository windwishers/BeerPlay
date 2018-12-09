package fail.toepic.beerplay

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView


class BeerPlayActivity : AppCompatActivity() , TitleChangeable{


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bear_play)

    }

    override fun changeTitle(title: String) {
        this.supportActionBar?.title = title
    }

}


interface TitleChangeable{
    fun changeTitle(title : String)
}