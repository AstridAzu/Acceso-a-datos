package programasConsola;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Ejercicio9 {
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {

        int opcion=0;
        do{
            imprimirMenu();
            opcion = sc.nextInt();
            sc.nextLine();

            switch(opcion){
                case 1:
                    verificarExisteArchivo(sc);
                    break;
                case 2:
                    ExplorarCarpeta(sc);
                    break;
                case 3:
                    crearCarpeta(sc);
                    break;
                case 4:
                    crearDocumento(sc);
                    break;
                case 5:
                    TrabajarConURIs(sc);
                    break;
                case 6:
                    System.out.println("¡hasta pronto! gracias por usar el asistente.");

            }
        }while(opcion!=6);

    }

    public static void imprimirMenu() {

        System.out.println("MI ASISTENTE DE ARCHIVOS");
        System.out.println("1. Verificar si un archivo existe");
        System.out.println("2. Explorar una carpeta");
        System.out.println("3. Crear una nueva carpeta");
        System.out.println("4. Crear un nuevo archivo");
        System.out.println("5. Trabajar con URIs");
        System.out.println("6. Salir\n");

    }
    public static void verificarExisteArchivo(Scanner sc){

        System.out.print("introduce el directorio padre: ");
        String directorio=sc.nextLine();
        System.out.print("introduce el nombre del archivo: ");
        String nombreArchivo=sc.nextLine();
        File file=new File(directorio+"/"+nombreArchivo);
        if(file.exists()){
            System.out.println("✓ El archivo existe en: "+directorio+"/"+nombreArchivo);

        }else {
            System.out.println("x El archivo no existe");
        }

    }
    public static void ExplorarCarpeta(Scanner sc){

        System.out.print("introduce la ruta del directorio : ");
        String directorio=sc.nextLine();
        File file=new File(directorio);
        if(!file.exists()){
            System.out.println("El archivo no existe");
        }else if(file.isDirectory()){
            //el contenido del directorio es
            String[] NombreDocumentos = file.list();
            for (int i=0;i<NombreDocumentos.length;i++){
                System.out.println(NombreDocumentos[i]);
            }
            System.out.println("Total: "+NombreDocumentos.length+" elementos");

        }

    }
    public static void crearCarpeta(Scanner sc){
        System.out.print("--- CREAR CARPETA ---");

        System.out.print("introduce la ruta de la nueva carpeta: ");
        String directorio=sc.nextLine();
        File file=new File(directorio);
        if(!file.exists()){
            System.out.println("Creando carpeta...");
            file.mkdirs();
            if (file.exists()){
                System.out.println("✓ Carpeta creada exitosamente en:"+directorio);
            }

        }else{
            System.out.println("el archivo ya existe");
        }


    }
    public static void crearDocumento(Scanner sc){

        System.out.print("introduce la ruta completa del nuevo archivo: ");
        String archivo=sc.nextLine();
        File file=new File(archivo);
        //me da la ruta del archivo padre
        File rutaPadre= file.getParentFile();
        if(!rutaPadre.exists()){
            file.mkdirs();
        }
        if(!file.exists()){
            try{
                if(file.createNewFile()){
                    System.out.println("✓ archivo creado exitosamente en:"+archivo);
                }
            }catch (IOException e ){

            }
        }
    }
    public static void TrabajarConURIs(Scanner sc){

        menuURIs();
        int opcion = sc.nextInt();
        sc.nextLine();
        switch (opcion){
            case 1:
                verificarExisteURI(sc);
                break;
            case 2:
                convertirRutaToURI(sc);
                break;

        }

    }
    public static void menuURIs(){
        System.out.println("TRABAJAR CON URIs");
        System.out.println("ELIGE UNA OPCION:");
        System.out.println("1. verificar una URI existente");
        System.out.println("2. convertir ruta a URI");

    }
    public static void verificarExisteURI(Scanner sc){

        System.out.println("introduce la URI (ejemplo: file///c:/ruta/archivo.txt) : ");
        String URI=sc.nextLine();
        File file=new File(URI);
        if(file.exists()){
            System.out.println("La URI representa un directorio en: "+URI);
        }
    }
    public static void convertirRutaToURI(Scanner sc){

        System.out.print("introduce la ruta a convertir: ");
        String ruta=sc.nextLine();
        System.out.println("RUTA ORIGINAL:"+ruta);
        //convierto ruta a URI
        File file=new File(ruta);
        if(file.exists()){
            System.out.println("✓ La ruta existe y la URI es valida");
        }else{
            System.out.println("El archivo no existe");
        }
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
