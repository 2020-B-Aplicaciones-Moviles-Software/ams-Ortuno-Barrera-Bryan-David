import java.io.BufferedReader
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Archivos {

    companion object {
        fun crear(datos: ArrayList<Marca>) {
            var fileWriter: FileWriter? = null
            try {
                fileWriter = FileWriter("archivo.txt")

                for (marca in datos) {
                    fileWriter.append("Marca:\n")
                    fileWriter.append(marca.idMarca.toString())
                    fileWriter.append('|')
                    fileWriter.append(marca.nombre)
                    fileWriter.append('|')
                    fileWriter.append(SimpleDateFormat("dd/MM/yyyy").format(marca.fechaFundacion))
                    fileWriter.append('|')
                    fileWriter.append(marca.empresa)
                    fileWriter.append('|')
                    fileWriter.append(marca.pais)
                    fileWriter.append('|')
                    fileWriter.append("{Celulares:")
                    fileWriter.append(marca.arregloCelulares.toString())
                    fileWriter.append("}")
                    fileWriter.append('\n')
                }

            } catch (e: Exception) {
                println("Writing Txt error!")
                e.printStackTrace()
            } finally {
                try {
                    fileWriter!!.flush()
                    fileWriter.close()
                } catch (e: IOException) {
                    println("Flushing/closing error!")
                    e.printStackTrace()
                }
            }
        }

        fun crearCel(datos: ArrayList<Celular>) {
            var fileWriter: FileWriter? = null
            try {
                fileWriter = FileWriter("archivoCel.txt")
                fileWriter.append("Celulares \n")
                for (celular in datos) {
                    fileWriter.append(celular.idCelular.toString())
                    fileWriter.append('|')
                    fileWriter.append(celular.nombre)
                    fileWriter.append('|')
                    fileWriter.append(SimpleDateFormat("dd/MM/yyyy").format(celular.anio))
                    fileWriter.append('|')
                    fileWriter.append(celular.precio.toString())
                    fileWriter.append('|')
                    fileWriter.append(celular.lectorDeHuellas.toString())
                    fileWriter.append('|')
                    fileWriter.append(celular.pixelesCamara.toString())
                    fileWriter.append('|')
                    fileWriter.append(celular.marca.toString())
                    fileWriter.append('\n')
                }

            } catch (e: Exception) {
                println("Writing Txt error!")
                e.printStackTrace()
            } finally {
                try {
                    fileWriter!!.flush()
                    fileWriter.close()
                } catch (e: IOException) {
                    println("Flushing/closing error!")
                    e.printStackTrace()
                }
            }
        }

        fun leer(): ArrayList<Marca> {

            var fileReader: BufferedReader? = null
            val marcas = ArrayList<Marca>()
            var line: String?
            try {
                fileReader = BufferedReader(FileReader("archivo.txt"))
                fileReader.readLine()
                // Read the file line by line starting from the second line
                line = fileReader.readLine()
                while (line != null) {
                    val tokens = line.split("|")
                    if (tokens.size > 0) {
                        val marca = Marca(
                            Integer.parseInt(tokens[0]),
                            tokens[1],
                            SimpleDateFormat("dd/MM/yyyy").parse(tokens[2]),
                            tokens[3],
                            tokens[4],
                            ListaCelulares.listarArreglo(Integer.parseInt(tokens[0]),leerCelulares())
                        )
                        marcas.add(marca)
                        line = fileReader.readLine()
                    }
                    line = fileReader.readLine()
                }
            } catch (e: Exception) {
                println("Reading Txt Error!")
                e.printStackTrace()
            } finally {
                try {
                    fileReader!!.close()
                } catch (e: IOException) {
                    println("Closing fileReader Error!")
                    e.printStackTrace()
                }
            }
            return marcas
        }

        fun leerCelulares():ArrayList<Celular>{
            var fileReader: BufferedReader? = null
            val celulares = ArrayList<Celular>()
            var line: String?

            try {
                fileReader = BufferedReader(FileReader("archivoCel.txt"))
                fileReader.readLine()
                // Read the file line by line starting from the second line
                line = fileReader.readLine()
                while (line != null) {
                    val tokens = line.split("|")
                    if (tokens.size > 0) {
                        val celular = Celular(
                            Integer.parseInt(tokens[0]),
                            tokens[1],
                            SimpleDateFormat("dd/MM/yyyy").parse(tokens[2]),
                            tokens[3].toDouble(),
                            tokens[4].toBoolean(),
                            tokens[5].toInt(),
                            tokens[6].toInt()
                        )
                        celulares.add(celular)
                        line = fileReader.readLine()
                    }
                }
            } catch (e: Exception) {
                println("Reading Txt Error!")
                e.printStackTrace()
            } finally {
                try {
                    fileReader!!.close()
                } catch (e: IOException) {
                    println("Closing fileReader Error!")
                    e.printStackTrace()
                }
            }
            return celulares
        }

    }
}