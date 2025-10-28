package TerceraPracticaObligatoriosDatosBinariosYBasesDatos;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;


public class Ejercicio1GestorInventarioBinario {
    //ddeclaro 3 variables con rutas
    static String Path=System.getProperty("user.dir");//obtengo la ruta raiz del proyecto
    static String rutaArchivos= Path+"\\src\\TerceraPracticaObligatoriosDatosBinariosYBasesDatos\\Archivos";//agrego a la ruta del la carpeta archivos
    static String MYarchivoBinario=rutaArchivos+"\\Inventario.dat";//agrego el nombre del archivo binario

    public static void main(String[] args) {
        //declaro un file con el nombre de la carpeta archivos
        File file = new File(rutaArchivos);
        File ArchivoBinario=new File(MYarchivoBinario);//creo un file con el nombre de mi archivo binario
        //reviso la existencia de la carpeta archivos si no existe la creo
        if (!file.exists()) {
            //crea la carpeta archivos
            file.mkdirs();
        }
        //reviso la existencia del archivo binario y sino existe lo creo
        try {
            if (ArchivoBinario.exists()) {
                System.out.println("El archivo ya existe.");
                System.out.println(ArchivoBinario);
            } else {
                Files.createFile(ArchivoBinario.toPath());
                //llama a la variable MYarchivoBinario y crea el tx
                System.out.println("Archivo creado: " + MYarchivoBinario );
            }
        } catch (IOException e) {
            System.err.println("Error al crear el archivo: " + e.getMessage());
        }

        Producto p1 = new Producto(1, "Laptop", 999.99, 10);
        Producto p2 = new Producto(2, "Mouse", 19.99, 50);
        try{
            escribirProducto("Inventario.dat", p1);

            agregarProducto("Inventario.dat", p2);
            leerProductos("Inventario.dat").toArray().toString();
        }catch (Exception e){
            System.err.println("Error al crear el archivo: " + e.getMessage());
        }

    }



    public static void escribirProducto(String archivo, Producto producto) throws IOException {
        /**
         * Escribe un producto en el archivo binario
         * @param archivo ruta del archivo donde guardar
         * @param producto objeto Producto a guardar
         * @throws IOException si hay error al escribir
         */
        String ruta=rutaArchivos+"\\"+archivo;
        //MYarchivoBinario
        DataOutputStream archivoBinario = new DataOutputStream(new FileOutputStream(ruta, false));
        archivoBinario.writeInt(producto.getId());
        archivoBinario.writeUTF(producto.getNombre());
        archivoBinario.writeDouble(producto.getPrecio());
        archivoBinario.writeInt(producto.getStock());
        System.out.println("Producto guardado: " + producto.getNombre());
    }

    public static List<Producto> leerProductos(String archivo) throws IOException{
        /**
         * Lee todos los productos del archivo binario
         * @param archivo ruta del archivo a leer
         * @return lista de productos leídos
         * @throws IOException si hay error al leer
         */
        List<Producto> productos=new ArrayList<>();
        String ruta=rutaArchivos+"\\"+archivo;
        DataInputStream dis = new DataInputStream(new FileInputStream(ruta));
        System.out.println("Contenido del archivo binario:");
        while (dis.available() > 0) {  // Mientras queden bytes por leer
            int numero = dis.readInt();
            String texto = dis.readUTF();
            double valor = dis.readDouble();
            int cantidad = dis.readInt();
            System.out.println(numero + " - " + texto + " - " + valor);
            Producto miProducto=new Producto(numero,texto,valor, cantidad);
            productos.add(miProducto);
        }
        return productos;
    }
    public static void agregarProducto(String archivo, Producto producto) throws IOException{
        /**
         * Añade un producto al final del archivo (modo append)
         * @param archivo ruta del archivo
         * @param producto producto a añadir
         * @throws IOException si hay error
         */

        String ruta=rutaArchivos+"\\"+archivo;
        //MYarchivoBinario
        DataOutputStream archivoBinario = new DataOutputStream(new FileOutputStream(ruta, true));
        archivoBinario.writeInt(producto.getId());
        archivoBinario.writeUTF(producto.getNombre());
        archivoBinario.writeDouble(producto.getPrecio());
        archivoBinario.writeInt(producto.getStock());
        System.out.println("Producto añadido: " + producto.getNombre());
    }
}


class Producto {
    private int id;
    private String nombre;
    private double precio;
    private int stock;
    public Producto() {

    }

    public Producto(int id, String nombre, double precio, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }




}
