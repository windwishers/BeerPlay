package fail.toepic.beerplay

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import fail.toepic.beerplay.fragment.BeerDetailFragment
import kotlinx.android.synthetic.main.activity_bear_play.*


class BeerPlayActivity : AppCompatActivity() , TitleChangeable,MoveFragment{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bear_play)

    }

    override fun changeTitle(title: String) {
        this.supportActionBar?.title = title
    }

    override fun onBackPressed() {
        when(NavHostFragment.findNavController(screen).currentDestination?.id) {
            R.id.beerList-> {
                //NOTE 부채_종료_팝업_안내_고려 : 종료시 바로 종료하지 말고 팝업을 올리는걸 고려합니다.
                finish()
            }
            else -> {
                //NOTE 모든 프레그먼트에서 백키는 리스트로 이동. 단 새로 생성 되기 때문에 스크롤 포지션을 유지 하는 작업을 추가 해야함.
                NavHostFragment.findNavController(screen).navigate(R.id.action_move_list)
            }
        }
    }

    override fun Move(resid: Int, bunble: Bundle) {
        NavHostFragment.findNavController(screen).navigate(R.id.action_show_detail, bunble)
    }
}


interface TitleChangeable{
    fun changeTitle(title : String)
}

interface MoveFragment{
    fun Move(resid : Int,bunble : Bundle)
}