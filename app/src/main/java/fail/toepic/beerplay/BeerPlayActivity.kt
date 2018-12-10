package fail.toepic.beerplay

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_bear_play.*


class BeerPlayActivity : AppCompatActivity() , TitleChangeable{


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bear_play)

    }

    override fun changeTitle(title: String) {
        this.supportActionBar?.title = title
    }

    override fun onBackPressed() {
        Log.d("dlwlrma",""+NavHostFragment.findNavController(screen).currentDestination?.id)
        when(NavHostFragment.findNavController(screen).currentDestination?.id) {
            R.id.beerList-> {
                //NOTE 부채_종료_팝업_안내_고려 : 종료시 바로 종료하지 말고 팝업을 올리는걸 고려합니다.
                finish()
            }
            else -> {
                //NOTE  이슈_리스트_클릭_앱죽음 :  리스트 -> 디테일 이동 후 '백'키 눌러서  돌아간 다음에는 클릭시 네비게이션 컨트롤러를 못 찾아서 죽어버림.
                // 그래서 임시로 백키 막음. 글로벌 엑션으로 바꿔야 하나 고민중.
//                super.onBackPressed()  // 백키를 무력화.
            }
        }
    }

}


interface TitleChangeable{
    fun changeTitle(title : String)
}