package TerceraPracticaObligatoriosDatosBinariosYBasesDatos;

import java.io.*;
import java.nio.file.Paths;
import java.util.Properties;

public class Ejercicio3ConfiguradorDeAplicaciónConProperties {
    public static void main(String[] args) {
        try {
            Properties config = cargarConfiguracion("app.properties");
            // Leer configuración
            String dbHost = getString(config, "db.host", "localhost");
            int dbPort = getInt(config, "db.port", 3306);
            boolean debug = getBoolean(config, "app.debug", false);
            mostrarConfiguracion(config);
            // Modificar configuración
            config.setProperty("app.idioma", "es");
            config.setProperty("ui.tema", "claro");
            config.setProperty("db.port", "3307");
            guardarConfiguracion(config, "app.properties", "Configuración de Mi Aplicación");
            mostrarConfiguracion(config);
        }catch (Exception e){
            System.out.println("Error al cargar configuracion");
        }



    }

    /**
     * Carga la configuración desde archivo o crea una por defecto
     * @param archivo ruta del archivo de configuración
     * @return objeto Properties cargado
     * @throws IOException si hay error de lectura
     */
    public static Properties cargarConfiguracion(String archivo) throws IOException{
        //obtengo la ruta del proyecto automaticamente
        String rutaRaiz = Paths.get("").toAbsolutePath().toString();
        //aqui puedo cambiar la ruta de donde leo los archivos txt
        String rutaProperties = rutaRaiz + "\\src\\TerceraPracticaObligatoriosDatosBinariosYBasesDatos\\";
        String ArchivoConfiguracion= rutaProperties+archivo;
        File file = new File(ArchivoConfiguracion);
        Properties props = new Properties();
        if(!file.exists()){
            file.createNewFile();
            props.setProperty("app.debug", "false");
            props.setProperty("app.idioma", "es");
            props.setProperty("app.titulo", "Mi Aplicación");
            props.setProperty("app.version", "1.0.0");
            props.setProperty("db.host", "localhost");
            props.setProperty("db.name", "mi_base_datos");
            props.setProperty("db.port", "3307");
            props.setProperty("db.user", "admin");
            props.setProperty("ui.tamano_fuente", "12");
            props.setProperty("ui.tema", "oscuro");
            // Guardamos los valores por defecto
            try (FileOutputStream fos = new FileOutputStream(file)) {
                props.store(fos,"" );
            }

        }else{
            //cargo los properties
            DataInputStream datos = new DataInputStream(new FileInputStream(file));
            props.load(datos);
        }
        return props;

    }
    /**
     * Obtiene una propiedad como String con valor por defecto
     * @param props objeto Properties
     * @param clave clave de la propiedad
     * @param valorDefecto valor si no existe
     * @return valor de la propiedad o valorDefecto
     */
    public static String getString(Properties props, String clave, String valorDefecto){
        if (props == null || clave == null) {
            return valorDefecto;
        }
        return props.getProperty(clave, valorDefecto);
    }
    /**
     * Obtiene una propiedad como int con validación
     * @param props objeto Properties
     * @param clave clave de la propiedad
     * @param valorDefecto valor si no existe o es inválido
     * @return valor int de la propiedad
     */
    public static int getInt(Properties props, String clave, int valorDefecto){
        if (props == null || clave == null) {
            return valorDefecto;
        }
        String valor = props.getProperty(clave);
        if (valor == null) {
            return valorDefecto;
        }
        try {
            return Integer.parseInt(valor.trim());
        } catch (NumberFormatException e) {
            return valorDefecto;
        }
    }
    /**
     * Obtiene una propiedad como boolean
     * @param props objeto Properties
     * @param clave clave de la propiedad
     * @param valorDefecto valor si no existe
     * @return valor boolean de la propiedad
     */
    public static boolean getBoolean(Properties props, String clave, boolean valorDefecto){
        if (props == null || clave == null) {
            return valorDefecto;
        }
        String valor = props.getProperty(clave);
        if (valor == null) {
            return valorDefecto;
        }
        valor = valor.trim().toLowerCase();
        // Acepta varias formas comunes de representar booleanos
        if (valor.equals("true") || valor.equals("1") || valor.equals("yes") || valor.equals("si")) {
            return true;
        } else if (valor.equals("false") || valor.equals("0") || valor.equals("no")) {
            return false;
        } else {
            return valorDefecto; // Si el valor no coincide con ninguno, devuelve el predeterminado
        }
    }
    /**
     * Guarda la configuración en archivo
     * @param props objeto Properties a guardar
     * @param archivo ruta del archivo destino
     * @param comentario comentario para el archivo
     * @throws IOException si hay error de escritura
     */
    public static void guardarConfiguracion(Properties props, String archivo, String comentario)throws IOException{
        if (props == null || archivo == null) {
            throw new IllegalArgumentException("El objeto Properties y la ruta del archivo no pueden ser nulos.");
        }

        // Obtiene la ruta raíz del proyecto
        String rutaRaiz = Paths.get("").toAbsolutePath().toString();
        String rutaProperties = rutaRaiz + "\\src\\TerceraPracticaObligatoriosDatosBinariosYBasesDatos\\";
        String archivoConfiguracion = rutaProperties + archivo;

        File file = new File(archivoConfiguracion);

        // Crea el archivo si no existe
        if (!file.exists()) {
            file.createNewFile();
        }
        // Guarda los properties en el archivo
        FileOutputStream fos = new FileOutputStream(file);
        props.store(fos, comentario);

    }
    /**
     * Muestra todas las propiedades por consola
     * @param props objeto Properties
     */
    public static void mostrarConfiguracion(Properties props){
        if (props == null || props.isEmpty()) {
            System.out.println("No hay propiedades cargadas.");
            return;
        }

        System.out.println("=== CONFIGURACIÓN ACTUAL ===");
        for (String clave : props.stringPropertyNames()) {
            String valor = props.getProperty(clave);
            System.out.println(clave + " = " + valor);
        }
        System.out.println("============================");
    }
}
