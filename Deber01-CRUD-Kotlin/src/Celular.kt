import java.util.*


class Celular(
    val idCelular: Int,
    var nombre: String,
    var anio: Date,
    var precio: Double,
    var lectorDeHuellas: Boolean,
    var pixelesCamara: Int,
    var marca: Int

) {
    override fun toString(): String{
        return "++${idCelular}++${nombre}++${anio}++${precio}++${lectorDeHuellas}++${pixelesCamara}++${marca}++"
    }
}