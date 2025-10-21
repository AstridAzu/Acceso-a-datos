package EjemploDeIntroduccion;

import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInput;

public class ejempli1FileReaderLecturaBasica {
    public static void main(String[] args) {
        //variable para almacebar caracter leido
        int caracter;

        //try cierra automaticamente el fileReader
        try(FileReader fr=  new FileReader("C:\\Users\\astrid\\IdeaProjects\\Acceso-a-datos\\src\\EjemploDeIntroduccion\\entrada.txt")){
            //read retorna uno cuando llega al final de archivo
            //hacemos el bucle while
            while((caracter = fr.read()) !=-1){
                System.out.print((char)caracter);
            }
         //para las excepciones
        }catch (IOException e){
            System.out.println("Error al leer el archivo"+ e.getMessage());
        }
    }
}
