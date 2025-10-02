package programasConsola;

import java.io.File;
import java.util.Scanner;

public class Ejercicio7 {
    public static void main(String[] args){
        //
        Scanner sc=new Scanner(System.in);
        String RutaBase="C:\\app\\acceso a datos";

        System.out.println("=== ORGANIZADOR DE BIBLIOTECA ===");
        System.out.println("introduce el nombre de la categoria:");
        String nombre=sc.nextLine();
        //creo l estructura de carpetas  que esta guardado en la variable nombre
        //esta estructura la creo en la RutaBase
        //remplazo el / en nombre por \\
        String nuevoNombre=nombre.replace("/","\\");
        //"C:\\app\\acceso a datos\\FICCION\\CIENCIA";
        String Ruta=RutaBase+"\\"+nombre;
        //inicializo un nuevo file con la ruta que cree
        File nuevoFile=new File(Ruta);
        //
        if(!nuevoFile.exists()){
            //creo las rutas con el metodo mkdirs()
            if(nuevoFile.mkdirs()){
                System.out.println("✓ Categoria '"+nombre+"' creado exitosamente");
                System.out.println("✓ El catalogo creado en: "+Ruta);
            }else {
                System.out.println("x El catalogo no existe");
            }

        }

        //creacion del libro
        System.out.println("Introduce la categoria del libro:");
        String Categoria=sc.nextLine();
        System.out.println("Introduce el nombre del libro:");
        String NombreLibre=sc.nextLine();
        //verificar si el libro existe en la categoria ingresada si no existe preguntar si deseo crearlo
        String RutaLibro=RutaBase+"\\"+Categoria.replace("/","\\")+"\\"+NombreLibre;
        File nuevoLibro=new File(RutaLibro);
        if(!nuevoLibro.exists()){
            System.out.println("x El libro no existe en: "+RutaLibro);
            System.out.println("¿quieres crear el libro?(s/n)");
            String respuesta=sc.nextLine();
            if (respuesta.equalsIgnoreCase("s")) {
                //creo el libro
                try{
                    nuevoLibro.createNewFile();
                    System.out.println("✓El libro se crea con exitosamente");
                }catch(Exception e){}
            }else{
                System.out.println("fin.............");
            }
        }

    }
}
