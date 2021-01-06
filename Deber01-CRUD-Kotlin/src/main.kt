fun main(){


    var x: Int? =null


    while(x != 0) {
        println("Seleccione una opción")
        println("1.-Listar Marcas")
        println("2.-Listas Celulares")
        println("3.-Crear Marca")
        println("4.-Crear Celular")
        println("5.-Actualizar Marca")
        println("6.-Actualizar Celular")
        println("7.-Eliminar Marca")
        println("8.-ELiminar Celular")

        x = readLine()?.toInt() as Int

        when (x) {
            1 -> (ListaMarcas.listarMarcas(Archivos.leer()))
            2 -> (ListaCelulares.listarCelulares(Archivos.leerCelulares()))
            3 -> (ListaMarcas.crearMarca(Archivos.leer()))
            4 -> (ListaCelulares.crearCelular(Archivos.leerCelulares()))
            5 -> {
                ListaMarcas.listarMarcas(Archivos.leer())
                println("Escriba el Id de la marca que desea editar")
                var idM = readLine()?.toInt() as Int
                ListaMarcas.actualizarMarca(Archivos.leer(), idM)
            }
            6 -> {
                ListaCelulares.listarCelulares(Archivos.leerCelulares())
                println("Escriba el Id del Celular que desea editar")
                var idM = readLine()?.toInt() as Int
                ListaCelulares.actualizarCelular(Archivos.leerCelulares(), idM)
            }
            7 -> {
                ListaMarcas.listarMarcas(Archivos.leer())
                println("Escriba el Id de la marca que desea eliminar")
                var idM = readLine()?.toInt() as Int
                ListaMarcas.eliminarMarca(Archivos.leer(), idM)
            }
            8 -> {
                ListaCelulares.listarCelulares(Archivos.leerCelulares())
                println("Escriba el Id del Celular que desea eliminiar")
                var idM = readLine()?.toInt() as Int
                ListaCelulares.eliminarCelular(Archivos.leerCelulares(), idM)
            }
            0 -> {
                print("Cerrando el programa")
            }
            else -> {
                print("Opción seleccionada incorrecta")
            }
        }
    }


}




