import java.io.File;

public class Fichero2 {
    public static void main(String[] args){
        // Directorio padre creado
        File ruta = new File("C:\\Users\\astrid\\Documents\\PruebasJavaAccesoDatos");
        //File ruta = new File("C:\\Users\\AlumnoAfternoon\\Documents\\PruebasJavaAccesoDatos\\hijo.txt");

        // Verficación para comprobar si la ruta existe
        if (ruta.exists()){
            // Verificar si la ruta especificada es un directorio
            if (ruta.isDirectory()){
                System.out.println("La ruta especificada es un directorio: " + ruta.getAbsolutePath());
            } else if (ruta.isFile()){
                System.out.println("La ruta especificada es un archivo: " + ruta.getAbsolutePath());
            }
        }
    }
}