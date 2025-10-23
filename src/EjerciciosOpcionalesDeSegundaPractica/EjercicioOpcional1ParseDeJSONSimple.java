package EjerciciosOpcionalesDeSegundaPractica;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EjercicioOpcional1ParseDeJSONSimple {

    /**
     * Lee un archivo JSON y extrae pares clave-valor simples
     * @param archivoJson ruta del archivo JSON
     * @return Map con las claves y valores parseados
     * @throws IOException si hay error de lectura
     */
    public static void main(String[] args) throws IOException {
        //obtengo la ruta del proyecto automaticamente
        String rutaRaiz = Paths.get("").toAbsolutePath().toString();
        //aqui puedo cambiar la ruta de donde leo los archivos txt
        String rutaArchivo = rutaRaiz + "\\src\\EjerciciosOpcionalesDeSegundaPractica\\";
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingresa el nombre del archivo Json: ");
        String nombre = sc.nextLine();
        //aqui creo la ruta final que voy a leer
        String rutaFinal = rutaArchivo + nombre;
        Map<String, String> json = new HashMap<>();
        json=leerJsonSimple(rutaFinal);
        System.out.println(" host: " + json.get("host"));
        json.put("Version","1.0");
        String rutaEscribirArchivo= rutaArchivo+"config_nuevo.json";
        escribirJsonSimple(json,rutaEscribirArchivo);
        System.out.println(" json escrito: " + json.size() +" propiedades en config_nuevo.json");


    }
    public static Map<String, String> leerJsonSimple(String archivoJson) throws IOException {
        Map<String, String> resultado = new HashMap<>();
        String Contenido="";
        /*leo todo el contenido del archivo*/
        File file = new File(archivoJson);
        //vwerifico que existe el archivo
        if (!file.exists()) {
            System.out.println("No se encontro el archivo Json: " + archivoJson);
        }
        //leo todo el contenido del archivo usando bufferedReader
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                Contenido += linea;
            }
        }
        Contenido= Contenido.trim();
        Pattern pattern = Pattern.compile("\"([^\"]+)\"\\s*:\\s*\"([^\"]*)\"");
        Matcher matcher = pattern.matcher(Contenido);
        //extraigo los pares
        while (matcher.find()) {
            String palabra = matcher.group(1);
            String caracteres = matcher.group(2);
            resultado.put(palabra, caracteres);
        }
        return resultado;
    }
    public static void escribirJsonSimple(Map<String, String> datos, String archivoJson) throws IOException
    {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivoJson))) {
            bw.write("{");
            bw.newLine();

            int contador = 0;
            int totalElementos = datos.size();

            for (Map.Entry<String, String> entry : datos.entrySet()) {
                contador++;
                String clave = entry.getKey();
                String valor = entry.getValue();

                // Escribir par clave-valor
                bw.write(" \"" + clave + "\": \"" + valor + "\"");


                // Agregar coma si no es el último elemento
                if (contador < totalElementos) {
                    bw.write(",");
                }
                bw.newLine();
            }

            bw.write("}");
        }
    }


}
