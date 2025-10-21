package EjemploDeIntroduccion;

import java.io.RandomAccessFile;
import java.nio.file.Paths;

public class ejemplo5RandomAccessFileAccesoAleatorio {
    public static void main(String[] args) {
        //try que cierrra automaticamente
        // Obtener la ruta raíz del proyecto
        String rutaRaiz = Paths.get("").toAbsolutePath().toString();
        // Construir la ruta completa al archivo salida.txt
        String rutaArchivo = rutaRaiz + "\\src\\EjemploDeIntroduccion\\salida.txt";
        try (RandomAccessFile raf=new RandomAccessFile(rutaArchivo,"rw");){
            raf.writeBytes("INICI0");
            raf.seek(20);

            raf.writeBytes("MEDIO");
            raf.seek(40);

            raf.writeBytes("FINAL");
            raf.seek(0);
            System.out.println("posicion 0 "+raf.readLine());
            raf.seek(20);
            System.out.println("posicion 20 "+raf.readLine());
            raf.seek(40);
            System.out.println("posicion 40 "+raf.readLine());

            System.out.println("tamaño del archivo "+raf.readLine()+"bytes");

        }catch (Exception e){
            System.out.println("error a leer el archivo: "+e.getMessage());
        }
    }
}
