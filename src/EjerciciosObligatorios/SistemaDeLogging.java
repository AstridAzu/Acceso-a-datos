package EjerciciosObligatorios;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SistemaDeLogging {

    enum NivelLog {
        INFO, WARNING, ERROR
    }
    private String archivoLog;
    private long TamañoMaximo;
    private int numeroRotacion;
    private DateTimeFormatter FormatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public SistemaDeLogging(String archivoLog, long tamañoMaximo) {
        if (archivoLog == null || archivoLog.trim().isEmpty() ) {
            throw new IllegalArgumentException("archivoLog no puede ser nulo.");

        }
        if(tamañoMaximo<=0){
            throw new IllegalArgumentException("el tamaño maximo debe ser positivo.");
        }
        this.archivoLog = archivoLog;
        TamañoMaximo = tamañoMaximo;
        this.numeroRotacion = numeroRotacion;
    }

    public long getTamañoMaximo() {
        return TamañoMaximo;
    }

    public int getNumeroRotacion() {
        return numeroRotacion;
    }

    public void escribirLog(String mensaje, NivelLog nivel) throws IOException {
        if(mensaje==null){
            throw new IllegalArgumentException("el mensaje no puede ser nulo");
        }
        if(nivel==null){
            throw new IllegalArgumentException("el nivel no puede ser nulo");
        }
        //verifico si es necesario rotar antes de escribir
        rotarSiNecesario();
        //creo mensaje formateado
        String Time= LocalDateTime.now().format(FormatoFecha);
        String lineaLog= "["+Time+"]"+"{"+nivel+"}"+ " "+ mensaje;
        try(BufferedWriter writer=new BufferedWriter(new FileWriter(archivoLog, true)))
        {
            writer.write(lineaLog);
            writer.flush();//aseguro que se escribe inmediatamente
        }
       System.out.println("["+Time+"]"+"["+nivel+"]"+mensaje);



    }
    private boolean rotarSiNecesario() throws IOException{
        long tamañoActual=obtenerTamanoLog();
        if(tamañoActual>=TamañoMaximo){
            realizarRotacion();
            return true;

        }
        return false;
    }
    private void realizarRotacion() throws IOException{
        Path archivoActual = Paths.get(archivoLog);
        if(!Files.exists(archivoActual)){
            return;
        }
        numeroRotacion++;
        Path archivoRotado=Paths.get(archivoLog+"."+numeroRotacion);
        //renombro el archivo actual
        Files.move(archivoActual, archivoRotado, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("Se ha rotado "+archivoLog+" renombrado a "+numeroRotacion);
    }
    private long obtenerTamanoLog(){
        try{
            Path path= Paths.get(archivoLog);
            if(Files.exists(path)){
                return Files.size(path);
            }
        } catch (IOException e) {
            System.err.println("Error al obtener el tamaño del archivo ");
        }
        return 0;
    }
    public static void main(String[] args) {
        try{
            SistemaDeLogging log=new SistemaDeLogging("app.log",1024);
            // Escribir algunos mensajes
            log.escribirLog("Aplicación iniciada", NivelLog.INFO);
            log.escribirLog("Usuario conectado", NivelLog.INFO);
            log.escribirLog("Cargando configuración", NivelLog.INFO);
            log.escribirLog("Advertencia: memoria baja", NivelLog.WARNING);
            log.escribirLog("Error de conexión", NivelLog.ERROR);

            // Escribir muchos mensajes para forzar la rotación
            System.out.println("\n=== Generando mensajes para forzar rotación ===\n");
            for (int i = 1; i <= 50; i++) {
                log.escribirLog("Mensaje de prueba número " + i +
                                " - Contenido adicional para aumentar el tamaño del archivo",
                        NivelLog.INFO);
            }

            log.escribirLog("Proceso completado exitosamente", NivelLog.INFO);
            log.escribirLog("Aplicación finalizada", NivelLog.INFO);

            System.out.println("\n=== Sistema de Log finalizado ===");
            System.out.println("Número de rotaciones realizadas: " + log.getNumeroRotacion());


        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

