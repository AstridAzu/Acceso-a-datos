import java.io.File;

public class fichero5 {
    public static void main ( String[]args){
        //verificar que dicho direectorio(s) existe, en caso de que no exista crearlo
        File miCarpetaDeTrabajo = new File("C:\\Users\\astrid\\Documents\\PruebasJavaAccesoDatos");
        if (miCarpetaDeTrabajo.exists()){
            System.out.println("existe directorio");
        }else {
            System.out.println("el directorio no existe ");
            System.out.println("creando directorio ");
            //miCarpetaDeTrabajo contiene a la ruta de la carpeta que estamos creando
            //y llamo al metodo mkdir() para crear esa carpeta en dicha ruta

            if(miCarpetaDeTrabajo.mkdir()){
                System.out.println("directorio creado");
            }else{
                System.out.println("directorio no creado");
            }

        }
    }
}
