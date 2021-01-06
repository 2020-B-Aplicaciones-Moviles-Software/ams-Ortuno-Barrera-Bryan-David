package com.example.aplicacionmovil_software

class BBaseDeDatos {

    companion object {
        val arregloEnteros = arrayListOf<Int>()
        val arregloEntrenadores = arrayListOf<BEntrenador>()

        /*
        fun inicializarEntrenadores() {
            arregloEntrenadores.add(BEntrenador("Juan Topo", "Maestro Poquemon"))

        }
    */
        fun inicializarArreglo() {
            arregloEnteros.add(1)
            arregloEnteros.add(2)
            arregloEnteros.add(3)
            arregloEnteros.add(4)
            arregloEnteros.add(5)
        }

        fun anadirItemAlArreglo(item: Int){
            arregloEnteros.add(item)
        }

        fun anadirItemAlArreglo(item: BEntrenador){
            arregloEntrenadores.add(item)
        }


    }
}