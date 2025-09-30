import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

public class fichero4 {


    //fichero4-> copia exacta del fichero1, pero que mostreis dentro del if el contenido de la carpeta,
    //sus nombres list(),lenght)
     public static void main(String[] args) {

         try {
             //crearcion de un uri
             URI uri = new URI("file:///C:/Users/astrid/Documents/PruebasJavaAccesoDatos");
             //creamos el objeto
             File file=new File(uri);
             if (!file.exists()){
                 System.out.println("El archivo no existe");
             }else if (file.isDirectory()){
                 //verificar si es un directorio
                 System.out.println("El archivo es un directorio ");
                 String[] NombreDocumentos = file.list();
                 //mostrar el contenido de el directorio
                 for ( String nombre : NombreDocumentos ){
                     System.out.println( nombre);
                 }

             } else if (file.isFile() ) {
                 System.out.println("es un archivo ");
             }
             //las excepciones de uri que se maneja con un catch
         } catch (URISyntaxException e) {
             throw new RuntimeException(e);
         }

     }
}
