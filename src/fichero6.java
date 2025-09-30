import java.io.File;
import java.util.Scanner;

public class fichero6 {
    public static void main(String args[]){
        //verificar que dicho archivo existe, en caso de que no exista crearlo
        File archivoTxt=new File("C:\\Users\\astrid\\Documents\\PruebasJavaAccesoDatos\\archivo.txt");
        if(archivoTxt.exists()){
            System.out.println("archivo existe");
        }else {
            System.out.println("archivo no existe");
            System.out.println("creando archivo");
            try{
             //llamamos a la funcion createNewFile para crear un archivo nuevo
                if(archivoTxt.createNewFile()){
                    System.out.println(" archivo creado");
                }else {
                    System.out.println("archivo no creado");
                }
                //vemos si hay un error al crearlo
            }catch(Exception e){
                System.out.println("error al crear archivo");
            }
        }


    }
}
