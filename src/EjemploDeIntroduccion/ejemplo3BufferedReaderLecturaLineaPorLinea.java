package EjemploDeIntroduccion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class ejemplo3BufferedReaderLecturaLineaPorLinea {
    public static void main(String[] args) {
        // Obtener la ruta raíz del proyecto
        String rutaRaiz = Paths.get("").toAbsolutePath().toString();

        // Construir la ruta completa del archivo entrada.txt dentro de src

        String rutaArchivo = rutaRaiz + "\\src\\EjemploDeIntroduccion\\entrada.txt";
        // Leer el archivo línea por línea
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            int numeroLinea = 1;

            while ((linea = br.readLine()) != null) {
                System.out.println("Línea " + numeroLinea + ": " + linea);
                numeroLinea++;
            }

            System.out.println("\n Lectura completada correctamente desde: " + rutaArchivo);

        } catch (IOException e) {
            System.err.println("⚠️ Error al leer el archivo: " + e.getMessage());
        }
    }
}
