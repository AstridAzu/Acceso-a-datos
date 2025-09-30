import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

public class Fichero3 {
    public static void main (String[] args){
    //Fichero3 -> Hagais una ruta con URI, creáis el objeto,
    // verificais que existe (else if), si es un directorio o archivo (else if) (try-catch)
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
            } else if (file.isFile() ) {
                System.out.println("es un archivo ");
            }
         //las excepciones de uri que se maneja con un catch
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

    }


}
