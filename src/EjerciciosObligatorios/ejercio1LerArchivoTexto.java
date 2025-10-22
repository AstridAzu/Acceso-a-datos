package EjerciciosObligatorios;

import java.io.*;
import java.nio.file.Paths;
import java.util.Scanner;

public class ejercio1LerArchivoTexto {
    /**
     * Lee un archivo y cuenta palabras, líneas y caracteres
     * @param nombreArchivo ruta del archivo a analizar
     * @return objeto EstadisticasTexto con los resultados
     * @throws IOException si hay error al leer el archivo
     */
    private static EstadisticasDeTexto estadisticas;
    private String nombreArchivo;

    public static void main(String[] args) {

        Scanner sc=new Scanner(System.in);

        //ingreso el nombre del archivo que deseo que lo lea
        System.out.println("Ingrese el nombre del archivo:");
        String nombreArchivo=sc.nextLine();
        //uso el metodo que me retorna las estadisticas
        try{
            estadisticas=analizarArchivo(nombreArchivo);
        }catch(Exception e){
            System.out.println("Error: "+e.getMessage());
        }        ;

        // Mostrar resultados por consola
        System.out.println("=== Estadísticas del archivo ===");
        System.out.println("Líneas: " + estadisticas.getNumeroLineas());
        System.out.println("Palabras: " + (estadisticas.getNumeroPalabras()));
        System.out.println("Caracteres: " + estadisticas.getNumeroCaracteres());
        System.out.printf("Palabra más larga: %s (%d caracteres)%n", estadisticas.getPalabraMasLarga(), estadisticas.getPalabraMasLarga().length());

    }
    //este metodo retorna las estaditicas
    public static EstadisticasDeTexto analizarArchivo(String nombreArchivo) throws IOException {
        estadisticas=new EstadisticasDeTexto();
        //obtengo la ruta del proyecto automaticamente
        String rutaRaiz = Paths.get("").toAbsolutePath().toString();
        //aqui puedo cambiar la ruta de donde leo los archivos txt
        String rutaArchivo = rutaRaiz + "\\src\\EjerciciosObligatorios\\";
        String RutaFinal=rutaArchivo+nombreArchivo;
        File file=new File(RutaFinal);
    //verificar si el archivo  existe
        if(!file.exists()){
            System.err.println("El archivo no existe: " + nombreArchivo);
            return estadisticas;
        }
        //lee el archivo
        BufferedReader br=new BufferedReader(new FileReader(file));
            String linea;
            //lee linea por linea
            while((linea=br.readLine())!=null){
                estadisticas.setNumeroLineas(estadisticas.getNumeroLineas()+1);
                estadisticas.setNumeroCaracteres(estadisticas.getNumeroCaracteres()+linea.length());
                //declaras un arrais con todas la palbras de la linea
                String[] partes = linea.split("\\s+");
                //recorro el array  en el bucle for parala por palabra
                for (String palabra : partes) {
                    if (!palabra.isEmpty()) {
                        estadisticas.setNumeroPalabras(estadisticas.getNumeroPalabras()+1);

                        if (palabra.length() > estadisticas.getPalabraMasLarga().length()) {
                            estadisticas.setPalabraMasLarga(palabra);
                        }
                    }
                }
            }
            return estadisticas;
    }

}
class EstadisticasDeTexto{
    //variables
    private int numeroLineas=0;
    private int numeroPalabras=0;
    private int numeroCaracteres=0;
    private String palabraMasLarga="";
    //creamos el constructor vacio
    public EstadisticasDeTexto(){}
    //creamos un constructor
    public EstadisticasDeTexto(int numeroLineas, int numeroPalabras, int numeroCaracteres, String palabraMasLarga) {
        this.numeroLineas = numeroLineas;
        this.numeroPalabras = numeroPalabras;
        this.numeroCaracteres = numeroCaracteres;
        this.palabraMasLarga = palabraMasLarga;
    }
    //creamos los get
    public int getNumeroLineas() {
        return numeroLineas;
    }

    public int getNumeroPalabras() {
        return numeroPalabras;
    }

    public int getNumeroCaracteres() {
        return numeroCaracteres;
    }

    public String getPalabraMasLarga() {
        return palabraMasLarga;
    }
     //creamos los set
    public void setNumeroLineas(int numeroLineas) {
        this.numeroLineas = numeroLineas;
    }

    public void setNumeroPalabras(int numeroPalabras) {
        this.numeroPalabras = numeroPalabras;
    }

    public void setNumeroCaracteres(int numeroCaracteres) {
        this.numeroCaracteres = numeroCaracteres;
    }

    public void setPalabraMasLarga(String palabraMasLarga) {
        this.palabraMasLarga = palabraMasLarga;
    }
}
