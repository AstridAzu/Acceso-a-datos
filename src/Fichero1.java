import java.io.File;

public class Fichero1 {
    public static void main(String[] args){
        // Directorio padre creado
        File directorioPadre = new File("C:\\Users\\astrid\\Documents\\PruebasJavaAccesoDatos");

        // Ruta relativa al fichero
        String nombreHijo = "hijo.txt";

        // Instancio File utilizando el constructor y la variable
        File fichero = new File(directorioPadre, nombreHijo);

        // Verficación para comprobar si el archivo existe
        if (fichero.exists()){
            // Si el archivo existe, muestra este mensaje
            System.out.println("El archivo existe en: "+fichero.getAbsolutePath());
        } else  {
            System.out.println("El archivo no existe en: "+fichero.getAbsolutePath());
        }
    }
}