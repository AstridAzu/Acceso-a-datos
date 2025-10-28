package TerceraPracticaObligatoriosDatosBinariosYBasesDatos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Ejercicio2SistemaDeUsuariosConJDBC {
    static String url = "jdbc:mysql://localhost:3306/testdb";
    //crea  el main
    public static void main(String[] args)  {
        //crea la coneccion de base de datos
        try {
            Connection conn = DriverManager.getConnection(url, "root", "123456");
            crearTabla(conn);
            //creo y inserto  de dos usuarios
            int id1 = insertarUsuario(conn, "Juan Pérez", "juan@email.com", 25);
            int id2 = insertarUsuario(conn, "María García", "maria@email.com", 30);
           //busco al usuario juan
            List<Usuario> usuarios = buscarPorNombre(conn, "Juan");
            for (Usuario u : usuarios) {
                System.out.println("id: "+u.getId()+" Nombre: "+u.getNombre()+" Email: "+u.getEmail() +" Edad: "+u.getEdad());
            }
            //actualizo el email de juan perez
            actualizarEmail(conn, id1, "juan.nuevo@email.com");
            //elimino al usuario maria garcia
            eliminarUsuario(conn, id2);
            //cieero la coneccion a la base de datos
            conn.close();
        }catch ( SQLException ex) {
            System.err.println(ex.getMessage());
        }


    }
    public static void crearTabla(Connection conn) throws SQLException {
        /**
         * Crea la tabla usuarios si no existe
         * @param conn conexión a la base de datos
         * @throws SQLException si hay error al crear
         *  private int id;
         *     private String nombre;
         *     private String email;
         *     private int edad;
         */
        // 1. Definir la sentencia SQL
        String sql = "CREATE TABLE IF NOT EXISTS usuarios ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "nombre VARCHAR(100),"
                + "email VARCHAR(100) UNIQUE,"
                + "edad INT"
                + ")";

        // 2. Crear un Statement (JDBC) y ejecutar la sentencia
        Statement stmt = null;
        stmt = conn.createStatement();
        stmt.execute(sql);
        System.out.println("Tabla 'usuarios' creada.");
        if (stmt != null) {
            stmt.close();
        }

    }

    public static int insertarUsuario(Connection conn, String nombre, String email, int edad)throws SQLException{
        /**
         * Inserta un nuevo usuario en la base de datos
         * @param conn conexión activa
         * @param nombre nombre del usuario
         * @param email email del usuario
         * @param edad edad del usuario
         * @return ID generado del usuario insertado
         * @throws SQLException si hay error
         */
        String sql = "INSERT INTO usuarios (nombre, email, edad) VALUES (?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            // Asignar los valores de los parámetros
            pstmt.setString(1, nombre);
            pstmt.setString(2, email);
            pstmt.setInt(3, edad);

            // Ejecutar la inserción
            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas == 0) {
                throw new SQLException("No se pudo insertar el usuario, ninguna fila afectada.");
            }
            int id;
            //genera los atributos del usuario insertado
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    //obtengo el id del usuario
                    id=generatedKeys.getInt(1);
                    System.out.println("usuario insertado con ID: " + id);
                    //retorno el id del usuario insertado
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("No se pudo obtener el ID generado del usuario.");
                }
            }
    }

    public static List<Usuario> buscarPorNombre(Connection conn, String nombre)
            throws SQLException{
        /**
         * Busca usuarios por nombre (búsqueda parcial)
         * @param conn conexión activa
         * @param nombre fragmento de nombre a buscar
         * @return lista de usuarios encontrados
         * @throws SQLException si hay error
         */
        String sql = "SELECT id, nombre, email, edad FROM usuarios WHERE nombre LIKE ?";
        List<Usuario> usuarios = new ArrayList<>();
        //prepara el statement que va a sql
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, "%" + nombre + "%"); // búsqueda parcial
        ResultSet rs = pstmt.executeQuery();
        //guardo todos los usuarios encontrados en la base de datos con el nombre
        while (rs.next()) {
            int id = rs.getInt("id");
            String nom = rs.getString("nombre");
            String email = rs.getString("email");
            int edad = rs.getInt("edad");
            usuarios.add(new Usuario(id, nom, email, edad));
        }
        System.out.println("usuarios encontrado:");
        return usuarios;
    }

    public static boolean actualizarEmail(Connection conn, int id, String nuevoEmail)
            throws SQLException{
        /**
         * Actualiza el email de un usuario
         * @param conn conexión activa
         * @param id ID del usuario
         * @param nuevoEmail nuevo email
         * @return true si se actualizó, false si no existe
         * @throws SQLException si hay error
         */

        String sql = "UPDATE usuarios SET email = ? WHERE id = ?";
        //creo el statement con el sql para actualizar el email
        PreparedStatement pstmt = conn.prepareStatement(sql);
        //indico el email
        pstmt.setString(1, nuevoEmail);
        //indico el id
        pstmt.setInt(2, id);
        //ejecuto el statement
        int filasAfectadas = pstmt.executeUpdate();
        System.out.println("Email actualizado para el usuario ID: " + id);
        //devuelvo true
        return filasAfectadas > 0; // true si se actualizó
    }
     //use testdb;
    //DROP TABLE IF EXISTS usuarios;
    //elimino la base de datos creadas para ejecutar el codigo cree los datos en sql

    public static boolean eliminarUsuario(Connection conn, int id) throws SQLException{
        /**
         * Elimina un usuario por ID
         * @param conn conexión activa
         * @param id ID del usuario a eliminar
         * @return true si se eliminó, false si no existía
         * @throws SQLException si hay error
         */
        String sql = "DELETE FROM usuarios WHERE id = ?";
        //preparo el statement
        PreparedStatement pstmt = conn.prepareStatement(sql);
        //indico el id del usuario a eliminar
        pstmt.setInt(1, id);
        //ejecuto el statement
        int filasAfectadas = pstmt.executeUpdate();

        System.out.println("usuario eliminado: ID " + id);
        return filasAfectadas > 0; // true si se eliminó al menos una fila

    }

}

class Usuario {
    //variables
    private int id;
    private String nombre;
    private String email;
    private int edad;

    //constructor vacio
    public Usuario() {
    }

    ;

    //contructor
    public Usuario(int id, String nombre, String email, int edad) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.edad = edad;

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public int getEdad() {
        return edad;
    }
}
