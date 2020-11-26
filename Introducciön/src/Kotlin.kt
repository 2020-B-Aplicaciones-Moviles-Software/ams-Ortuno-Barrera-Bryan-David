import java.util.*

fun main(){
    println("Hola mundo");
    // Ejemplo JAVA Int edad = 31
    var edadProfesor = 31
    // var edadProfesor: Int = 31
    var sueldoProfesor = 31.23
    // var sueldoProfesor: Double = 31.23;
    // Duck Typing
    // Variables MUTABLES (Reasignando valores)
    var edadCachorro: Int
    edadCachorro = 1
    edadCachorro = 5
    edadCachorro = 6
    // Variables INMUTABLES (NO PODEMOS REASIGNAR EL VALOR)
    val numeroCedula = 1818181818
    // numeroCedula = 1518256

    // Tipos de variables

    val nombrProfesor: String = "Adrian Eguez"
    val sueldo: Double = 12.2
    val estadoCivil: Char = 'S'
    val fechaNacimiento = Date()

    // Condicionales

    if (sueldo == 12.20) {
        // Verdadero
    } else {

    }

    when (sueldo) {
        12.2 -> { //  Inicio bloque codigo
            println(" Sueldo Normal ")
        }//  Fin bloque codigo
        -12.2 -> println(" Sueldo negativo ")
        else -> println("Sueldo no reconocido")
    }

    val sueldoMayorAlEstablecido = if (sueldo == 12.2) false else true
    // val sueldoMayorAlEstablecido = if (sueldo == 12.2) 2 else 20
    // condicion ? bloque-true : bloque-false
    // imprimirNombre("Adrian")
    imprimirNombre("Adrian")
    // calcularSueldo(1000.00)
    calcularSueldo(1000.00)
    // calcularSueldo(1000.00, 14.00)
    calcularSueldo(1000.00, 14.00)
    // calcularSueldo(1000.00, 14.00, 3)
    calcularSueldo(1000.00, 14.00, 3)

    // Quiero solo enviar sueldo y calculoEspecial, y que se mantenga la tasa por defecto

    calcularSueldo(
        1000.00,
        // 12.00,
        calculoEspecial = 3 // Named Parameters (parametros nombrados)
    )
    calcularSueldo(
        calculoEspecial = 3, // Named Parameters (parametros nombrados)
        tasa = 14.00,
        sueldo = 1000.00
    )


    // Arreglos
    // ctrl + alt + l = INDENTACIÓN

    // Arreglo ESTATICO (no se puede modificar los elementos del arreglo)
    var arregloEstatico: Array<Int> = arrayOf(1, 2, 3)
    arregloEstatico = arrayOf(2, 3, 4, 5, 6)
    // arregloInmutable.add(12) NO ES POSIBLE

    // Arreglo DINAMICO (se puede modificar los elementos del arreglo)
    val arregloDinamico: ArrayList<Int> = arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    println(arregloDinamico)
    arregloDinamico.add(11)
    arregloDinamico.add(12)
    println(arregloDinamico)

    // OPERADORES - Sirven para los arreglos Estaticos y Dinamicos

    // arregloMutable.forEach{  }
    // arregloInmutable.forEach {  }

    // Void = Unit (El unit es cuando no se devuelve nada en Kotlin)
    // Operador FOREACH
    val respuestaForEach: Unit = arregloDinamico.forEach { valorIteracion -> // Sin definir el tipo de dato (DUCK TYPING)
        // valorIteracion: Int -> // Sin definir el tipo de dato (DUCK TYPING)
        println("Valor: ${valorIteracion}")
    }
    // Operador FOREACHINDEXED
    val respuestaForEachIndexed: Unit = arregloDinamico.forEachIndexed { indice, valorIteracion ->
        // valorIteracion: Int -> // Sin definir el tipo de dato (DUCK TYPING)
        println("Valor: ${valorIteracion} Indice: ${indice}")
    }

    // MAP -> Muta el arreglo (Cambia el arreglo)
    // 1) Enviemos el nuevo valor de la iteracion
    // 2) Nos devuelve es un NUEVO ARREGLO con los valores modificados
    val respuestaMap: List<Int> = arregloDinamico.map { valorActualIteracion ->
        // Linea 1
        // Linea 2
        return@map valorActualIteracion * 10
    }
    println(respuestaMap)
    arregloDinamico.map { valorActualIteracion -> valorActualIteracion + 15 } // Sintaxis corta

    // Filter -> FILTRAR EL ARREGLO
    // 1) Devolver una expresion (TRUE o FALSE)
    // 2) Nuevo arreglo filtrado
    val respuestaFilterr: List<Int> = arregloDinamico.filter { valorActualIteracion ->
        val mayoresACinco: Boolean = valorActualIteracion > 5
        return@filter mayoresACinco // Devolver un booleano
    }
    println(respuestaFilterr)

    //Any ALL -> Condicion -> Boolean
    //OR <-> AND
    //OR = NY
    //OR (FALSO - TODOS SON FALSOS ES FALSO)
    //OR (TRUE - UNO ES TRUE YA ES TRUE)
    //AND = ALL
    //AND (FALSO - UNO ES FALSO YA ES FALSO)
    //AND (TRUE - TODOS SON TRUE ES TRUE

    val respuestaAny:Boolean = arregloEstatico
            .any{
                valorActualIteracion ->
                return@any (valorActualIteracion > 5)
            }
    println(respuestaAny)

    val respuestaAll: Boolean = arregloEstatico
            .all{
                valorActualIteracion ->
                return@all valorActualIteracion > 5
            }
    println(respuestaAll) //false

    //REDUCE
    //1) Devuelve el acumulado
    //2) En que valor empieza
    // [1,2,3,4,5]
    //0-=0+1
    //1-=1+2
    //3-=3+3
    //6-=6+4
    //10=10+5
    //15

    val respuestaFilter:Int = arregloDinamico
            .reduce{    //Valor inicial = 0
                acumulado, valorActualItereacion ->
                return@reduce acumulado + valorActualItereacion
            }
    println(respuestaFilter) //78

    val respuestaReduceFold:Int = arregloDinamico
            .fold(
                    100,{
                acumulado, valorActualIteracion ->
                return@fold acumulado - valorActualIteracion
                }
            )
    println(respuestaReduceFold)
    // arregloMutable.fold (empieza desde el principio
    // arregloMutable.foldRight (empieza desde el final
    // arregloMutable.reduce (empieza desde el final
    // arregloMutable.reduceRight (empieza desde el final

    // OPERADORES
    // forEach -> Unit (void)
    // map -> Arreglo
    // filter -> Arreglo
    // all -> Booleano
    // any -> Booleano
    // reduce -> Valor
    // fold -> Valor

    val vidaActual: Double = arregloDinamico
            .map{it * 2.3} //arreglo
            .filter{it > 20} //arreglo
            .fold(100.00 ,{acc,i -> acc - i})//valor
            .also{ println(it)} //ejecutar codigo extra
    println(vidaActual)

    val ejemploUno = Suma(1,2)
    //val ejemploUno = Suma(1,2)
    val ejemploDos = Suma(null,2)
    //val ejemploDos = Suma(null,2)
    val ejemploTres = Suma(1,null)
    //val ejemploTres = Suma(1,null)
    val ejemploCuatro = Suma(null,null)
    //val ejemploCuatro = Suma(null,null)

    println(ejemploUno.sumar())
    println(Suma.historialSumas)
    println(ejemploDos.sumar())
    println(Suma.historialSumas)
    println(ejemploTres.sumar())
    println(Suma.historialSumas)
    println(ejemploCuatro.sumar())
    println(Suma.historialSumas)



} // Fin main


fun imprimirNombre(nombre: String) {
    println("Nombre: ${nombre}")
}

fun calcularSueldo(
    sueldo: Double, // Requerido
    tasa: Double = 12.00, // Opcional con valor por defecto 12.00,
    calculoEspecial: Int? = null // Calculo especial es un entero
    // con valor inicial de "null"
): Double {
    // String -> String? (puede ser nulo)
    // Int -> Int? (puede ser nulo)
    // Date -> Date? (puede ser nulo)
    if (calculoEspecial == null) {
        return sueldo * (100.00 / tasa)
    } else {
        return sueldo * (100.00 / tasa) * calculoEspecial
    }
}

abstract class NumerosJava { //Cosntructor primario
    protected val numeroUno: Int
    private val numeroDos: Int

    constructor(
            uno: Int,
            dos: Int
    ) { //Bloque de constructor primario
        //this.numeroUno
        numeroUno = uno
        //this.numeroDos
        numeroDos = dos
    }
}
//Instancia.numeroUno
//Instancia.numeroDos
abstract class Numeros(//constructor primario
    protected var numeroUno:Int,
    protected var numeroDos:Int
){
    init {
        println("Hola")
    }
}


class Suma (
        uno: Int, //parametros
        dos: Int // parametros
):Numeros(uno,dos){
    init {
        //this.numeroUno
        //this.numeroDos
        // X -> this.uno -> No existen
        // X -> this.dos -> No existen
    }

    constructor( //Segundo Constructor
            uno:Int?, //parametros
            dos:Int  //parametros

    ): this( //llamada constructor primario
            if(uno == null) 0 else uno,
            dos
    ){

    }

    constructor( //Tercer Constructor
            uno:Int, //parametros
            dos:Int?  //parametros

    ): this( //llamada constructor primario
            uno,
            if(dos == null) 0 else dos

    ){

    }

    constructor( //Cuarto Constructor
            uno:Int?, //parametros
            dos:Int?  //parametros

    ): this( //llamada constructor primario
            if(uno == null) 0 else uno,
            if(dos == null) 0 else dos
            ){

    }

    public fun sumar ():Int{
            //this.numeroUno
            //this.numeroDos
            val total:Int = numeroUno+numeroDos
            Suma.agregarHistorial(total)
            return total
    }

    //SINGLETON
    companion object{ //Metodos y propiedades
        val historialSumas = arrayListOf<Int>()
        fun agregarHistorial(nuevaSuma:Int){
                this.historialSumas.add(nuevaSuma)
        }
    }
}

class BaseDeDatos(){
    companion object{
        val datos = arrayListOf<Int>()
    }
}
//BaseDeDatos.datos