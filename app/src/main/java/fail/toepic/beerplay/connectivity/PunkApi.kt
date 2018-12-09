package fail.toepic.beerplay.connectivity

import fail.toepic.beerplay.model.Beer
import fail.toepic.beerplay.model.Beers
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.Result
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface PunkApi {
    companion object {
        fun create() : PunkApi {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(
                    OkHttpClient.Builder()
                        .addInterceptor(
                            HttpLoggingInterceptor()
                                .setLevel(HttpLoggingInterceptor.Level.BODY))
                        .build())
                .baseUrl("https://api.punkapi.com/")
                .build()
                .create(PunkApi::class.java)
        }
    }

    @GET("v2/beers")
    fun getBeers(@Query("page") page: Int,
                   @Query("per_page") per_page: Int): Single<List<Beer>>

//    @GET("/count.json")
//    fun getCount(): Single<Result<Int>>
//
//    @PUT("/cheeses/{id}/like.json")
//    fun like(@Path("id") id: Int): Single<Result<Cheese>>
}