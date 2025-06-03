import com.google.gson.GsonBuilder
import net.simplifiedcoding.androidphpmysql.WebService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://192.168.1.35/Android/v1/"

    private val gson = GsonBuilder()
        .setLenient()
        .create()

    val instance: WebService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(WebService::class.java)
    }
}
