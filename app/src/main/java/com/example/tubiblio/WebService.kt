package net.simplifiedcoding.androidphpmysql;


import com.example.tubiblio.Libro
import com.example.tubiblio.Usuario
import com.example.tubiblio.UsuarioResponse
import com.google.gson.GsonBuilder
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

object AppConstans{
    const val URL = "http://192.168.1.35/Android/v1/"
}
interface  WebService {

    @GET("Usuarios.php")
    suspend fun obtenerUsuarios(): Response<UsuarioResponse>

    @GET("getUsuario.php")
        suspend fun obtenerUsuario(
        @Query("usuario") usuario: String
        ): Response<UsuarioResponse>

    @FormUrlEncoded
    @POST("registrarUsuario.php")
    suspend fun agregarUsuario(
        @Field("usuario") nombre: String,
        @Field("pass") pass: String,
        @Field("email") email: String,
        @Field("fecha_nacimiento") fechaNacimiento: String,
        @Field("poblacion") poblacion: String
    ): Response<GenericResponse>

    @FormUrlEncoded
    @POST("modificarUsuario.php")
    suspend fun modificarUsuario(
        @Field("id") id: Int,
        @Field("usuario") usuario: String,
        @Field("pass") pass: String,
        @Field("email") email: String,
        @Field("fecha_nacimiento") fecha_nacimiento: String,
        @Field("poblacion") poblacion: String
    ): Response<UsuarioResponse>

    @FormUrlEncoded
    @POST("eliminarUsuario.php")
    suspend fun eliminarUsuario(
        @Field("id") id: Int
    ): Response<GenericResponse>


    @GET("buscarLibros.php")
    suspend fun buscarLibros(
        @Query("q") query: String
    ): Response<LibrosResponse>

    @FormUrlEncoded
    @POST("reservar_libro.php")
    suspend fun reservarLibro(
        @Field("usuario_usuario")  usuarioUsuario: String,
        @Field("usuario_email")  usuarioEmail: String,
        @Field("libro_titulo")       libroTitulo: String,
        @Field("lugar_recogida") lugarRecogida: String
    ): Response<ReservarResponse>

    @FormUrlEncoded
    @POST("login.php")
    suspend fun loginUsuario(
        @Field("usuario") usuario: String,
        @Field("pass") pass: String
    ): Response<LoginResponse>
}

object RetrofitClient{
    val webService: WebService by lazy {
        Retrofit.Builder()
            .baseUrl(AppConstans.URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WebService::class.java)

    }
}


data class GenericResponse(
    val error: Boolean,
    val message: String
)

data class LibrosResponse(
    val error: Boolean,
    val libros: List<Libro>?
)

data class ReservarResponse(
    val error: Boolean,
    val message: String,
    val fecha_devolucion: String?
)
data class LoginResponse(
    val error: Boolean,
    val message: String? = null,
    val usuario: Usuario? = null
)