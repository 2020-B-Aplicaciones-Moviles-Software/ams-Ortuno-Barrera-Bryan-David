import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ListaMarcas {

    companion object {

        val arregloMarcas = arrayListOf<Marca>()

        fun iniciarMarca():ArrayList<Marca>{
            arregloMarcas.add(Marca(1,"Samsung", SimpleDateFormat("dd/MM/yyyy").parse("02/09/2016"),"Samsung","Korea",ListaCelulares.listarArreglo(1,Archivos.leerCelulares())))
            arregloMarcas.add(Marca(2,"Xiaomi", SimpleDateFormat("dd/MM/yyyy").parse("02/09/2016"),"Xiaomi","Korea",ListaCelulares.listarArreglo(2,Archivos.leerCelulares())))
            arregloMarcas.add(Marca(3,"Huawei", SimpleDateFormat("dd/MM/yyyy").parse("02/09/2016"),"Huawei","Korea",ListaCelulares.listarArreglo(3,Archivos.leerCelulares())))
            return arregloMarcas
        }

        fun crearMarca(array: ArrayList<Marca>){
            println("Id:")
            var idM = readLine()?.toInt() as Int
            println("Nombre: ")
            var nombreM =  readLine()?.toString() as String
            println("Fecha Fundación:")
            var anioM = SimpleDateFormat("dd/MM/yyyy").parse(readLine()?.toString() as String)
            println("Empresa: ")
            var empresaM = readLine()?.toString() as String

            println("Pais:")
            var paisM = readLine()?.toString() as String

            array.add(Marca(
                idMarca = idM,
                nombre = nombreM,
                fechaFundacion = anioM,
                empresa = empresaM,
                pais = paisM,
                null
            ))
            Archivos.crear(array)
        }

        fun listarMarcas(array: ArrayList<Marca>){
            println(array)
        }

        fun eliminarMarca(array: ArrayList<Marca>,idMarca: Int){

            with(array.iterator()) {
                forEach {
                    if (it.idMarca == idMarca) {
                        remove()
                    }else{
                        println("No se encontro la marca indicada")
                    }
                }
            }
           Archivos.crear(array)
        }

        fun actualizarMarca(array: ArrayList<Marca>,idMarca: Int){

            with(array.iterator()) {
                forEach {
                    if (it.idMarca == idMarca) {
                        remove()
                    }
                }
            }
            println("Nombre: ")
            var nombreM =  readLine()?.toString() as String
            println("Fecha Fundación:")
            var anioM = SimpleDateFormat("dd/MM/yyyy").parse(readLine()?.toString() as String)
            println("Empresa: ")
            var empresaM = readLine()?.toString() as String

            println("Pais:")
            var paisM = readLine()?.toString() as String

            array.add(Marca(
                idMarca = idMarca,
                nombre = nombreM,
                fechaFundacion = anioM,
                empresa = empresaM,
                pais = paisM,
                null
            ))
            Archivos.crear(array)
        }
    }
}
