package EjerciciosObligatorios;

import java.io.*;
import java.nio.file.Paths;
import java.util.Scanner;

public class Ejercicio2MergeDeArchivosConFiltrado {
    public static void main(String[] args)  {
        //obtengo la ruta del proyecto automaticamente
        String rutaRaiz = Paths.get("").toAbsolutePath().toString();
        //aqui puedo cambiar la ruta de donde leo los archivos txt
        String rutaArchivo = rutaRaiz + "\\src\\EjerciciosObligatorios\\";

        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese el nombre archivo: ");
        String nombreArchivo = sc.nextLine();
        System.out.println("Ingrese el nombre archivo: ");
        String nombreArchivo1 = sc.nextLine();
        //cuando ingreses el filtro recuerda tiene que estar escrito como en los archivos
        //si tienen mayusculas lo escribes con mayuscula sino no funciona
        System.out.println("Ingrese el filtro: ");
        String filtro = sc.nextLine();

        String[] archivos = {rutaArchivo+nombreArchivo,rutaArchivo+nombreArchivo1};
        String archivoSalida = rutaArchivo+"combinado.txt";
        System.out.println("Iniciando combinación de archivos");
        System.out.println("Filtro aplicado: " + filtro );
        //recibe ñlos el total de lineas escritas
        try{

            int totalLineas= combinarArchivos(archivos, archivoSalida, filtro);
            System.out.println("Total lineas escritas en combinado: "+totalLineas);

        }catch(Exception e){
            System.out.println("Error en el comando");
        }



    }
    //metodo creado
    public static int combinarArchivos(String[] archivosEntrada, String archivoSalida, String filtro) throws IOException {
        int lineasEscritas = 0;
        BufferedWriter writer = null;
        try{
            writer= new BufferedWriter(new FileWriter(archivoSalida));
            for(String ArchivoEntrada: archivosEntrada){
                int LineasIguales = 0;
                int LineasProcesadas = 0;
                BufferedReader reader=null;
                try{
                    reader = new BufferedReader(new FileReader(ArchivoEntrada));
                    String linea;
                    //leo linea por linea
                    while((linea= reader.readLine())!=null){
                        LineasProcesadas++;
                        //verifico si culple el filptro
                        if(cumpleFiltro(linea,filtro)){
                            //vuelve a revisarlo al final
                            writer.write(linea+"\n");
                            writer.newLine();
                            LineasIguales++;
                            lineasEscritas++;
                        }
                    }
                    //mostrar estadisticas del archivo procesado
                    System.out.println("procesando "+ archivosEntrada+" :"+LineasIguales+ " lineas coinciden");
                }catch (FileNotFoundException e){
                    System.out.println("Archivo no encontrado");
                }
                finally{
                    if(reader!=null){
                        reader.close();
                    }
                }
            }
        }catch (FileNotFoundException e){

        }finally {
            if(writer!=null){
                writer.close();
            }
        }
        System.out.println("Total: "+lineasEscritas+" lineas escritas"+archivoSalida);
        return lineasEscritas;
    }
    private static boolean cumpleFiltro(String linea, String filtro) {
        // Si no hay filtro, todas las líneas cumplen
        if (filtro == null || filtro.isEmpty()) {
            return true;
        }
        // Verificar si la línea contiene el filtro
        //si lo contiene retorna true
        return linea.contains(filtro);
    }

}
