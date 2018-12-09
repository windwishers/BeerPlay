package fail.toepic.beerplay.util

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation

@Suppress("unused")
fun Fragment.MoveTo(@IdRes id : Int, v : View, bundle: Bundle){
    Navigation.findNavController(v).navigate(id,bundle)
}