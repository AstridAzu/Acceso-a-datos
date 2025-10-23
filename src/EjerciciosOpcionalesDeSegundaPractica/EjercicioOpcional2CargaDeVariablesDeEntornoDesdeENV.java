package EjerciciosOpcionalesDeSegundaPractica;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class EjercicioOpcional2CargaDeVariablesDeEntornoDesdeENV {
    private static Map<String, String> variables = new HashMap<>();
    public static void main(String[] args) {
        //obtengo la ruta del proyecto automaticamente
        String rutaRaiz = Paths.get("").toAbsolutePath().toString();
        //aqui puedo cambiar la ruta de donde leo los archivos txt
        String rutaArchivo = rutaRaiz + "\\src\\EjerciciosOpcionalesDeSegundaPractica\\.env";
        Map<String, String> variablesEntorno = new HashMap<>();
        try {
            variablesEntorno = cargarEnv(rutaArchivo);
            System.out.println("Cargadas " +variablesEntorno.size()+" variables desde .env");
        }catch (IOException e){
            System.out.println("Error al cargar archivo");
        }
        finally {
            System.out.println("Base de datos: " + variablesEntorno.get("DB_HOST") + ":" + variablesEntorno.get("DB_PORT"));
            String debug = getEnv("DEBUG", "false");
        }
    }
    public static Map<String, String> cargarEnv(String archivoEnv) throws IOException {
       // variables.clear(); // Limpiar variables anteriores
        int contador = 0;
        // Usar BufferedReader para leer el archivo línea por línea
        try (BufferedReader br = new BufferedReader(new FileReader(archivoEnv))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                // Eliminar espacios al inicio y final
                linea = linea.trim();
                // Ignorar líneas vacías y comentarios
                if (linea.isEmpty() || linea.startsWith("#")) {
                    continue;
                }

                // Buscar el primer '=' para separar clave y valor
                int indiceSeparador = linea.indexOf('=');

                if (indiceSeparador > 0) {
                    String clave = linea.substring(0, indiceSeparador).trim();
                    String valor = linea.substring(indiceSeparador + 1).trim();

                    variables.put(clave, valor);
                    contador++;
                }
            }
        }

        System.out.println("Cargadas " + contador + " variables desde " + archivoEnv);

        return new HashMap<>(variables); // Devolver una copia
    }
    public static String getEnv(String clave, String valorPorDefecto){
        return variables.getOrDefault(clave, valorPorDefecto);
    }


}
