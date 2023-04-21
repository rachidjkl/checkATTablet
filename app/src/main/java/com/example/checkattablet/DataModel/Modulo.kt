import com.google.gson.annotations.SerializedName

data class Modulo (@SerializedName("id_modulo") var idModulo: Int,
                   @SerializedName("nombre_modulo") var nombreModulo: String)