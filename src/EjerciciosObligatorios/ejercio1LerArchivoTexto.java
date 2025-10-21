package EjerciciosObligatorios;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class ejercio1LerArchivoTexto {
    /**
     * Lee un archivo y cuenta palabras, líneas y caracteres
     * @param nombreArchivo ruta del archivo a analizar
     * @return objeto EstadisticasTexto con los resultados
     * @throws IOException si hay error al leer el archivo
     */
    private EstadisticasDeTexto estadisticas;
    private String nombreArchivo;

    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.println("Ingrese el nombre del archivo:");
        String nombreArchivo=sc.nextLine();

    }
    /*public static EstadisticasDeTexto analizarArchivo(String ruta) throws FileNotFoundException {
        File archivo=new File(ruta);
        if(archivo.exists()){
            BufferedReader br=new BufferedReader(new FileReader(ruta));

        }else{
            System.out.println("No existe el archivo");
        }


    }
*/
}
class EstadisticasDeTexto{
    //variables
    private int numeroLineas;
    private int numeroPalabras;
    private int numeroCaracteres;
    private String palabraMasLarga;
    //creamos el constructor
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
