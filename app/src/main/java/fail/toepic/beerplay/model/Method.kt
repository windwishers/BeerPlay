package fail.toepic.beerplay.model

data class Method(val mash_temp : List<MashTemp> = arrayListOf(),
                  val fermentation : Fermentation = Fermentation(),
                  val twist : String = "")

data class MashTemp(val temp: ValueUnit = ValueUnit(), val duration: Int = 0)

data class Fermentation(val temp: ValueUnit = ValueUnit())