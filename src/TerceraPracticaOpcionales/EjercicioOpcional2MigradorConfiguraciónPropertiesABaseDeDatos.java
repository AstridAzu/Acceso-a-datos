package TerceraPracticaOpcionales;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class EjercicioOpcional2MigradorConfiguraciónPropertiesABaseDeDatos {
    public static void main(String[] args)  {
        //creo el archivo propertis con datos por defecto
        crearArchivoPorDefecto("config.properties");
        try {
            //necesito crear la base de datos confi_db
            String url = "jdbc:mysql://localhost:3306/config_db";
            Connection conn = DriverManager.getConnection(url, "root", "123456");

            // Migrar de archivo a BD
            int migradas = migrarPropertiesABD("config.properties", conn);
            System.out.println("Propiedades migradas a BD: " + migradas);
            // Modificar en BD
            PreparedStatement pstmt = conn.prepareStatement(
                    "UPDATE configuracion SET valor = ? WHERE clave = ?");
            pstmt.setString(1, "3307");
            pstmt.setString(2, "db.port");
            pstmt.executeUpdate();
            // Exportar de BD a archivo
            int exportadas = exportarBDaProperties(conn, "config_exportado.properties");
            System.out.println("Propiedades exportadas a archivo: " + exportadas);
            conn.close();
        }  catch (Exception e) {
            System.out.println("Error al cargar configuracion: " + e.getMessage());
        }


    }
    /**
     * Crea archivo
     * y genera un archivo .properties con valores por defecto.
     *
     * @param conn conexión JDBC
     * @param nombreArchivo nombre del archivo .properties a crear
     */
    public static void crearArchivoPorDefecto(String nombreArchivo)  {
            try{
                //  Crear el archivo .properties con los valores por defecto
                String rutaRaiz = Paths.get("").toAbsolutePath().toString();
                String rutaProperties = rutaRaiz + "\\src\\TerceraPracticaOpcionales\\";
                String RutaFinalArchivo = rutaProperties + nombreArchivo;
                // Propiedades por defecto
                Properties props = new Properties();
                props.setProperty("db.host", "localhost");
                props.setProperty("db.port", "3306");
                props.setProperty("app.nombre", "Mi App");
                props.setProperty("app.version", "2.0");
                // Guardar el archivo .properties
                // Crear y guardar el archivo .properties
                FileOutputStream fileouput = new FileOutputStream(RutaFinalArchivo);
                props.store(fileouput,"");
            }catch(Exception e){}
    }


    /**
     * Migra todas las propiedades de archivo a base de datos
     * @param archivo ruta del archivo Properties
     * @param conn conexión JDBC
     * @return número de propiedades migradas
     * @throws IOException si hay error al leer archivo
     * @throws SQLException si hay error de BD
     */

    public static int migrarPropertiesABD(String archivo, Connection conn)
            throws IOException, SQLException {
        // Crear la ruta absoluta del archivo
        String rutaRaiz = Paths.get("").toAbsolutePath().toString();
        // Carpeta donde están los archivos .properties
        String rutaProperties = rutaRaiz + "\\src\\TerceraPracticaOpcionales\\";
        // Ruta final del archivo
        String RutaFinalArchivo = rutaProperties + archivo;

        // Cargar las propiedades desde el archivo
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(RutaFinalArchivo)) {
            props.load(fis);
        }
        System.out.println(props.getProperty("db.host"));

        // Crear la tabla si no existe
        String crearTabla = """
        CREATE TABLE IF NOT EXISTS configuracion (
            clave VARCHAR(255) PRIMARY KEY,
            valor VARCHAR(255)
        )
    """;
        try (Statement st = conn.createStatement()) {
            st.executeUpdate(crearTabla);
        }

        // Insertar o actualizar propiedades
        String insertar = "INSERT INTO configuracion (clave, valor) VALUES (?, ?)";
        int contador = 0;

        try (PreparedStatement ps = conn.prepareStatement(insertar)) {
            for (String clave : props.stringPropertyNames()) {
                String valor = props.getProperty(clave);
                ps.setString(1, clave);
                ps.setString(2, valor);
                contador += ps.executeUpdate();
            }
        }

        return contador; // número de propiedades migradas
    }
    /**
     * Exporta configuración de base de datos a archivo Properties
     * @param conn conexión JDBC
     * @param archivo ruta del archivo destino
     * @return número de propiedades exportadas
     * @throws SQLException si hay error de BD
     * @throws IOException si hay error al escribir
     */
    public static int exportarBDaProperties(Connection conn, String archivo)
            throws SQLException, IOException {

        // Crear la ruta absoluta del archivo destino
        String rutaRaiz = Paths.get("").toAbsolutePath().toString();
        String rutaProperties = rutaRaiz + "\\src\\TerceraPracticaOpcionales\\";
        String RutaFinalArchivo = rutaProperties + archivo;

        // Crear objeto Properties para exportar
        Properties props = new Properties();

        // Leer todas las configuraciones desde la base de datos
        String consulta = "SELECT clave, valor FROM configuracion";
        int contador = 0;

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(consulta)) {

            while (rs.next()) {
                String clave = rs.getString("clave");
                String valor = rs.getString("valor");
                props.setProperty(clave, valor);
                contador++;
            }
        }

        // Guardar las propiedades en el archivo destino
        try (FileOutputStream fos = new FileOutputStream(RutaFinalArchivo)) {
            props.store(fos, "Exportación de configuración desde la base de datos");
        }

        return contador; // número de propiedades exportadas
    }
    /**
     * Sincroniza: actualiza BD con valores de Properties que hayan cambiado
     * @param archivo ruta del archivo Properties
     * @param conn conexión JDBC
     * @return número de propiedades actualizadas
     * @throws IOException si hay error al leer
     * @throws SQLException si hay error de BD
     */
    public static int sincronizarPropiedades(String archivo, Connection conn)
            throws IOException, SQLException {

        // Construir la ruta absoluta del archivo
        String rutaRaiz = Paths.get("").toAbsolutePath().toString();
        String rutaProperties = rutaRaiz + "\\src\\TerceraPracticaOpcionales\\";
        String RutaFinalArchivo = rutaProperties + archivo;

        // Cargar propiedades desde el archivo
        Properties propsArchivo = new Properties();
        try (FileInputStream fis = new FileInputStream(RutaFinalArchivo)) {
            propsArchivo.load(fis);
        }

        // Asegurarse de que la tabla exista
        String crearTabla = """
        CREATE TABLE IF NOT EXISTS configuracion (
            clave VARCHAR(255) PRIMARY KEY,
            valor VARCHAR(255)
        )
    """;
        try (Statement st = conn.createStatement()) {
            st.executeUpdate(crearTabla);
        }

        // Cargar las propiedades actuales desde la base de datos
        Map<String, String> propsBD = new HashMap<>();
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT clave, valor FROM configuracion")) {
            while (rs.next()) {
                propsBD.put(rs.getString("clave"), rs.getString("valor"));
            }
        }

        // Preparar sentencia de actualización/inserción
        String upsert = "INSERT OR REPLACE INTO configuracion (clave, valor) VALUES (?, ?)";
        int contadorActualizados = 0;

        try (PreparedStatement ps = conn.prepareStatement(upsert)) {
            for (String clave : propsArchivo.stringPropertyNames()) {
                String valorArchivo = propsArchivo.getProperty(clave);
                String valorBD = propsBD.get(clave);

                // Solo actualiza si el valor cambió o no existe
                if (valorBD == null || !valorBD.equals(valorArchivo)) {
                    ps.setString(1, clave);
                    ps.setString(2, valorArchivo);
                    contadorActualizados += ps.executeUpdate();
                }
            }
        }

        return contadorActualizados; // número de propiedades actualizadas
    }



}
