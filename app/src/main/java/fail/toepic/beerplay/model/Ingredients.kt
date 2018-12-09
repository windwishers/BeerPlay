package fail.toepic.beerplay.model

data class Ingredients(val malt: List<Malt> = arrayListOf(), val hops: List<Hops> = arrayListOf(),
                       val yeast: String = "")

data class Malt(val name: String = "", val amount: ValueUnit = ValueUnit())

data class Hops(val name: String = "", val amount: ValueUnit = ValueUnit(), val add: String = "",
                val attribute: String = "")