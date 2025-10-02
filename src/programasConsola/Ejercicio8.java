package programasConsola;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class Ejercicio8 {
    public  static void main(String [] args){
        Scanner sc=new Scanner(System.in);
        System.out.println("=====EXPLORADOR INTELIGENTE=====");
        System.out.print("Ingrese la ruta a explorar: ");
        String ruta=sc.nextLine();
        System.out.println("Explorando:  "+ ruta);
        File file=new File(ruta);
        if (!file.exists()){
            System.out.println("El archivo no existe");
        }else if (file.isDirectory()){
            //verificar si es un directorio
            String[] NombreDocumentos = file.list();
            //mostrar el contenido de el directorio
            System.out.println("cantidad elementos: "+NombreDocumentos.length);
            System.out.println("cantidad elementos"+ Arrays.toString(NombreDocumentos));
            for ( String nombre : NombreDocumentos ){
                //hago un para cada directorio y saber la cantidad de archivos que contiene
                String rutaBase=ruta+"\\"+nombre;
                File newFile=new File(rutaBase);
                String[] Documentos = newFile.list();
                System.out.println("- "+ nombre+" [ Directorio - "+Documentos.length+" elementos ]");
            }
            System.out.println("total elementos encontrados:"+  NombreDocumentos.length);

        } else if (file.isFile() ) {
            System.out.println("es un archivo ");
        }

        System.out.println("CONVERSION A URI:");
        System.out.println("RUTA ORIGINAL:"+ruta);
        //convierto ruta a URI
        String RutaURI="file:/";
        for (int i=0; i<ruta.length(); i++){
            if (ruta.charAt(i)=='\\'){
                RutaURI=RutaURI+'/';
            }else {
                RutaURI=RutaURI+ruta.charAt(i);
            }

        }
        System.out.println("URI EQUIVALENTE: "+RutaURI+"/");



    }
}
