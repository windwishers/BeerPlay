package fail.toepic.beerplay

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView


class BeerPlayActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bear_play)
        // setOnNavigationItemReselectedListener 가 지정 되어 있으면. 이미 선택 된 아이템을
        // 다시 선택시 setOnNavigationItemReselectedListener 를 호출 합니다.
        // 지정 되어 있지 않으면 재선택 시에도 setOnNavigationItemSelectedListener 를 호출 합니다.
//        bottom_navigation.setOnNavigationItemReselectedListener(object : BottomNavigationView.OnNavigationItemReselectedListener{
//            override fun onNavigationItemReselected(item: MenuItem) {
//            }
//
//        })
//        bottom_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.screen) as NavHostFragment
//        findViewById<BottomNavigationView>(R.id.bottom_navigation)?.let { bottomNavView ->
//            NavigationUI.setupWithNavController(bottomNavView, navHostFragment.navController)
//        }

    }

}