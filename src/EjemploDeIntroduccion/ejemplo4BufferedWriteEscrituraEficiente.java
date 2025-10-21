package EjemploDeIntroduccion;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

public class ejemplo4BufferedWriteEscrituraEficiente {
    public static void main(String[] args) {
        String[] lineaEscribir = {
                "encabezado de documento ",
                "esta es la primera linea del contenido",
                "esta es la segunda linea del contenido",
                "final de documento"
        };
        // Obtener la ruta raíz del proyecto
        String rutaRaiz = Paths.get("").toAbsolutePath().toString();
        String rutaArchivo = rutaRaiz + "\\src\\EjemploDeIntroduccion\\salida.txt";
        //hacemos un try catch
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {

            //hacemos un for
            for (String linea : lineaEscribir) {
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("error al escribir el mensaje:" + e.getMessage());
        }
    }
}
