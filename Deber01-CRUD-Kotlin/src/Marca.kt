import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class Marca(
    val idMarca: Int,
    val nombre: String,
    val fechaFundacion: Date,
    val empresa: String,
    val pais: String,
    val arregloCelulares: ArrayList<Celular>?
    ) {
    override fun toString(): String{
        return "\n Marca:\n" +
                "IdMarca:${idMarca}; Nombre:${nombre}; Fecha Fundación:${fechaFundacion}; Empresa:${empresa}; País:${pais}" +
                "\nCelulares:{${arregloCelulares}}"
    }

}