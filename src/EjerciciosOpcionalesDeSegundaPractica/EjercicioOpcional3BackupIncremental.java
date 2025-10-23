package EjerciciosOpcionalesDeSegundaPractica;

import java.io.*;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EjercicioOpcional3BackupIncremental {
    public static void main(String[] args) {
        //obtengo la ruta del proyecto automaticamente
        String rutaRaiz = Paths.get("").toAbsolutePath().toString();
        //aqui puedo cambiar la ruta de donde leo los archivos txt
        String rutaArchivo = rutaRaiz + "\\src\\EjerciciosOpcionalesDeSegundaPractica\\CarpetaBackups\\";
        String rutaBackup=rutaArchivo+"backup";
        String rutaDocuments=rutaArchivo+"documents";
        String lastBackup=rutaArchivo+"backup\\.lastbackup";
        int archivosCopiados=0;
        try{
            archivosCopiados = backupIncremental(rutaDocuments ,rutaBackup,lastBackup);
        }catch(Exception e){
            System.out.println("Error al abrir archivos de la backup");
        }finally {
            System.out.println("Backup completado: "+archivosCopiados +" archivos");
        }

    }
    public static int backupIncremental(String carpetaOrigen, String carpetaDestino, String archivoControl) throws IOException{
        System.out.println("Iniciando backup incremental...");

        // Leer timestamp del último backup
        long ultimoBackup = leerUltimoBackup(archivoControl);

        // Mostrar fecha del último backup
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (ultimoBackup == 0) {
            System.out.println("Último backup: nunca");
        } else {
            System.out.println("Último backup: " + sdf.format(new Date(ultimoBackup)));
        }

        // Crear carpeta destino si no existe
        File dirDestino = new File(carpetaDestino);
        if (!dirDestino.exists()) {
            dirDestino.mkdirs();
        }

        // Procesar archivos
        File dirOrigen = new File(carpetaOrigen);
        int contador = 0;

        File[] archivos = dirOrigen.listFiles();
        if (archivos != null) {
            for (File archivo : archivos) {
                if (archivo.isFile() && archivo.lastModified() > ultimoBackup) {
                    File archivoDestino = new File(carpetaDestino, archivo.getName());

                    if (ultimoBackup == 0) {
                        System.out.println("Copiando: " + archivo.getName());
                    } else {
                        System.out.println("Copiando: " + archivo.getName() + " (modificado)");
                    }

                    copiarArchivo(archivo, archivoDestino);
                    contador++;
                }
            }
        }

        // Actualizar archivo de control
        long timestampActual = System.currentTimeMillis();
        File control = new File(archivoControl);
        control.getParentFile().mkdirs();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(control))) {
            writer.write(String.valueOf(timestampActual));
        }

        System.out.println("Registro actualizado: " + sdf.format(new Date(timestampActual)));

        return contador;
    }
    private static long leerUltimoBackup(String archivoControl) throws IOException{
        File archivo = new File(archivoControl);

        if (!archivo.exists()) {
            return 0;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea = reader.readLine();
            if (linea != null && !linea.isEmpty()) {
                return Long.parseLong(linea.trim());
            }
        } catch (NumberFormatException e) {
            return 0;
        }

        return 0;
    }
    private static void copiarArchivo(File origen, File destino) throws IOException{
        try (InputStream in = new FileInputStream(origen);
             OutputStream out = new FileOutputStream(destino)) {

            byte[] buffer = new byte[8192];
            int bytesLeidos;

            while ((bytesLeidos = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesLeidos);
            }
        }
    }
}
