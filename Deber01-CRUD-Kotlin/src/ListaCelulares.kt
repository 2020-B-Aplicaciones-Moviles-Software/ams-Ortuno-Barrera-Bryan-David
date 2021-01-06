import java.text.SimpleDateFormat

class ListaCelulares {

    companion object {

        val arregloCelulares = arrayListOf<Celular>()


        fun iniciarArreglo():ArrayList<Celular>{
            arregloCelulares.add(Celular(1,"Note 10",SimpleDateFormat("dd/MM/yyyy").parse("02/09/2016"),456.80,false,16,1))
            arregloCelulares.add(Celular(2,"Note 8",SimpleDateFormat("dd/MM/yyyy").parse("02/09/2016"),456.80,false,16,2))
            arregloCelulares.add(Celular(3,"Galaxy 10",SimpleDateFormat("dd/MM/yyyy").parse("02/09/2016"),456.80,false,16,3))
            return arregloCelulares
        }

        fun crearCelular(arrayCel: ArrayList<Celular>){

            println("Id:")
            var idC = readLine()?.toInt() as Int
            println("Nombre: ")
            var nombreC =  readLine()?.toString() as String
            println("Año:")
            var anioC = SimpleDateFormat("dd/MM/yyyy").parse(readLine()?.toString() as String)

            println("Precio: ")
            var precioC = readLine()?.toDouble() as Double

            println("Lector de Huellas: \n"+
                    "1.- SI\n 2. NO")
            var x:Int
            var lectorC:Boolean = false
            x = readLine()?.toInt() as Int
            when(x){
                1 -> {lectorC = true}
                2 -> {lectorC= false}
                else -> println("Opción incorrecta")
            }
            println("Pixeles de la cámara")
            var pixelesC = readLine()?.toInt() as Int

            println("Marca del celular")
            var marcaC = readLine()?.toInt() as Int

            arrayCel.add(Celular(
                idCelular =idC,
                nombre = nombreC,
                anio = anioC,
                precio = precioC,
                lectorDeHuellas = lectorC,
                pixelesCamara = pixelesC,
                marca = marcaC,
            ))
            Archivos.crearCel(arrayCel)
            Archivos.crear(Archivos.leer())
        }

        fun listarArreglo(idMarca:Int,array: ArrayList<Celular>):ArrayList<Celular>{
            val arregloCelMarca = arrayListOf<Celular>()
            array.forEach { valorIteracion ->
                if (valorIteracion.marca == idMarca){
                    arregloCelMarca.add(valorIteracion)
                }
            }
            return arregloCelMarca
        }

        fun listarCelulares(array: ArrayList<Celular>){
            println(array)
        }

        fun eliminarCelular(arrayCel: ArrayList<Celular>,idCelular:Int){

            with(arrayCel.iterator()) {
                forEach {
                    if (it.idCelular == idCelular) {
                        // do some stuff with it
                        remove()
                    }
                }
            }
            Archivos.crearCel(arrayCel)
            Archivos.crear(Archivos.leer())
        }

        fun actualizarCelular(arrayCel: ArrayList<Celular>,idCelular:Int){

            with(arrayCel.iterator()) {
                forEach {
                    if (it.idCelular == idCelular) {
                        // do some stuff with it
                        remove()
                    }
                    else{
                        println("Marca no encontrada")
                    }
                }
            }
            println("Nombre: ")
            var nombreC =  readLine()?.toString() as String
            println("Año:")
            var anioC = SimpleDateFormat("dd/MM/yyyy").parse(readLine()?.toString() as String)
            println("Precio: ")
            var precioC = readLine()?.toDouble() as Double

            println("Lector de Huellas: \n"+
            "1.- SI\n 2. NO")
            var x:Int
            var lectorC:Boolean = false
            x = readLine()?.toInt() as Int
            when(x){
                1 -> {lectorC = true}
                2 -> {lectorC= false}
                else -> println("Opción incorrecta")
            }
            println("Pixeles de la cámara")
            var pixelesC = readLine()?.toInt() as Int

            println("Marca del celular")
            var marcaC = readLine()?.toInt() as Int

            arrayCel.add(Celular(
                idCelular = idCelular,
                nombre = nombreC,
                anio = anioC,
                precio = precioC,
                lectorDeHuellas = lectorC,
                pixelesCamara = pixelesC,
                marca = marcaC,
            ))
            Archivos.crearCel(arrayCel)
            Archivos.crear(Archivos.leer())
        }

    }
}