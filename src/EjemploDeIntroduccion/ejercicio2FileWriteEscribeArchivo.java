package EjemploDeIntroduccion;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

public class ejercicio2FileWriteEscribeArchivo {
    public static void main(String[] args)  {
        //creo la variable
        String contenido= "primera linea\n segunda linea\n tercera linea";
        // Obtener la ruta raíz del proyecto
        String rutaRaiz = Paths.get("").toAbsolutePath().toString();
        // Construir la ruta completa al archivo salida.txt
        String rutaArchivo = rutaRaiz + "\\src\\EjemploDeIntroduccion\\salida.txt";
        //creo un file con rutaArchivo
        File miArchivo = new File(rutaArchivo);
        //reviso si existe la ruta ingresada y el archivo
        if(miArchivo.getParentFile().exists()){
            System.out.println("el archivo ya existe");
        }else{
            System.out.println("el archivo no existe");
        }
        //escribo en el archivo el contenido de la variable contenido
        try (FileWriter writer = new FileWriter(rutaArchivo)) {
            //escribe el contenido
            writer.write(contenido);
            writer.flush(); // Asegura que todos los datos se escriban en disco
            System.out.println(" Archivo creado correctamente en: " + rutaArchivo);
        } catch (IOException e) {
            //en caso que no se escriba el archivo imprime el error
            e.printStackTrace();
        }
    }

}
