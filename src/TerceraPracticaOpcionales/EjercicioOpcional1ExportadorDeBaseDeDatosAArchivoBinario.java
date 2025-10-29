package TerceraPracticaOpcionales;

import java.io.*;
import java.nio.file.Paths;
import java.sql.*;

public class EjercicioOpcional1ExportadorDeBaseDeDatosAArchivoBinario {
    public static void main(String[] args) {

        try{
            String url = "jdbc:mysql://localhost:3306/inventario";
            Connection conn = DriverManager.getConnection(url, "root", "123456");
            //crea la tabla productos e inserta 2 productos
            crearProductosPorDefecto(conn);

            // Exportar
            int exportados = exportarProductos(conn, "backup_productos.dat");
            System.out.println("Productos exportados: " + exportados);
            // Limpiar tabla (simulación de pérdida de datos)
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM productos");
            // Importar
            int importados = importarProductos(conn, "backup_productos.dat");
            System.out.println("Productos importados: " + importados);
            conn.close();
        }catch (Exception e){
            System.out.println("Error al ejecutar el archivo: " + e.getMessage());
        }

    }
    /**
     * Exporta todos los productos de la base de datos a archivo binario
     * @param conn conexión JDBC activa
     * @param archivo ruta del archivo destino
     * @return número de productos exportados
     * @throws SQLException si hay error de BD
     * @throws IOException si hay error de archivo
     */
    public static int exportarProductos(Connection conn, String archivo)
            throws SQLException, IOException {
        String sql = "SELECT cod_producto, nombre, precio, stock FROM productos";
        int contador = 0;

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        String rutaRaiz = Paths.get("").toAbsolutePath().toString();
        //aqui puedo cambiar la ruta de donde leo los archivos
        String rutaProperties = rutaRaiz + "\\src\\TerceraPracticaOpcionales\\";
        String RutaFinalArchivo = rutaProperties + archivo;
        File file = new File(RutaFinalArchivo);
        if (!file.exists()) {
            file.createNewFile();
        }
        System.out.println("Exportando productos..." );


        DataOutputStream dos = new DataOutputStream(new FileOutputStream(file));
        // Guardamos los datos uno por uno en el archivo
        while (rs.next()) {
            dos.writeInt(rs.getInt("cod_producto"));
            dos.writeUTF(rs.getString("nombre"));
            dos.writeDouble(rs.getDouble("precio"));
            dos.writeInt(rs.getInt("stock"));
            System.out.println("producto exportado: ID="+rs.getInt("cod_producto")+", Nombre="+rs.getString("nombre"));
            contador++;
        }

        return contador;
    }
    /**
     * Importa productos desde archivo binario a la base de datos
     * @param conn conexión JDBC activa
     * @param archivo ruta del archivo fuente
     * @return número de productos importados
     * @throws SQLException si hay error de BD
     * @throws IOException si hay error de archivo
     */
    public static int importarProductos(Connection conn, String archivo)
            throws SQLException, IOException {
        String sql = "INSERT INTO productos (cod_producto, nombre, precio, stock) VALUES (?, ?, ?, ?)";
        int contador = 0;
        String rutaRaiz = Paths.get("").toAbsolutePath().toString();
        //aqui puedo cambiar la ruta de donde leo los archivos
        String rutaProperties = rutaRaiz + "\\src\\TerceraPracticaOpcionales\\";
        String RutaFinalArchivo = rutaProperties + archivo;
        File file = new File(RutaFinalArchivo);
        if (!file.exists()) {
            file.createNewFile();
        }


        System.out.println("Importando productos..." );
        DataInputStream dis = new DataInputStream(new FileInputStream(RutaFinalArchivo));
        PreparedStatement ps = conn.prepareStatement(sql);
        //importo productos
        while (dis.available() > 0) { // mientras haya datos
            int cod = dis.readInt();
            String nombre = dis.readUTF();
            double precio = dis.readDouble();
            int stock = dis.readInt();

            ps.setInt(1, cod);
            ps.setString(2, nombre);
            ps.setDouble(3, precio);
            ps.setInt(4, stock);
            ps.executeUpdate();
            System.out.println(" Producto importado: ID="+cod+" Nombre="+nombre);
            contador++;
        }

        return contador;
    }
    /**
     * Crea la tabla producto productos y añade estos por defecto en la base de datos.
     * ID=1 (Laptop), ID=2 (Mouse), ID=3 (Teclado)
     *
     * @param conn conexión JDBC activa
     * @return número de productos insertados
     * @throws SQLException si ocurre un error en la base de datos
     */
    public static void crearProductosPorDefecto(Connection conn) throws SQLException {


        Statement st = conn.createStatement();
        //if si la tabla existe return
        DatabaseMetaData metaData = conn.getMetaData();

        ResultSet rs = metaData.getTables(conn.getCatalog(), null, "productos", new String[]{"TABLE"});

        if (rs.next()) {
            System.out.println("La tabla inventario existe.");
            return ;
        } else {
            System.out.println("La tabla inventario NO existe.");

        }



        String sqlCrearTabla = """
            CREATE TABLE IF NOT EXISTS productos (
                cod_producto INTEGER PRIMARY KEY,
                nombre VARCHAR(255) NOT NULL,
                precio DOUBLE PRECISION DEFAULT 0.0,
                stock INTEGER DEFAULT 0
            )
        """;
        // Crear tabla si no existe
        st.execute(sqlCrearTabla);

        String sql = "INSERT INTO productos (cod_producto, nombre, precio, stock) VALUES (?, ?,?,?)";


        PreparedStatement ps = conn.prepareStatement(sql);
        // Producto 1
        ps.setInt(1, 1);
        ps.setString(2, "Laptop");
        ps.setDouble(3, 0.5);
        ps.setInt(4, 1);

        ps.executeUpdate();


        // Producto 2
        ps.setInt(1, 2);
        ps.setString(2, "Mouse");
        ps.setDouble(3, 0.5);
        ps.setInt(4, 1);
        ps.executeUpdate();


        // Producto 3
        ps.setInt(1, 3);
        ps.setString(2, "Teclado");
        ps.setDouble(3, 0.5);
        ps.setInt(4, 1);




    }
}
