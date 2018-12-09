package fail.toepic.beerplay.model

import com.google.gson.annotations.SerializedName

data class Beer(val id : String,val name: String = ""){

    val tagline : String = ""
    val first_brewed : String = ""
    val description : String = ""
    val image_url : String = ""
    // Alcohol by Volume
    val ABV : Float = 0.0F
    // International Bitterness Unit
    val IBU : Int = 0
    val target_final_gravity : Int = 0
    val target_original_gravity : Int = 0
    // color value by European Brewery Convention
    val EBC : Int = 0
    // Sandard Reference Method : 글라스 안에서의 맥주 색.
    val srm : Float = 0.0F
    val ph : Float = 0.0F
    val attenuation_level : Float = 0.0F
    val volume : ValueUnit = ValueUnit()
    val boil_volume :  ValueUnit = ValueUnit()
    val method :  Method = Method()
    val ingredients : Ingredients = Ingredients()
    val food_pairing : List<String> = arrayListOf()
    val brewers_tips : String = ""
    val contributed_by : String = ""
    
}