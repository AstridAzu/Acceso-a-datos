package TerceraPracticaOpcionales;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EjercicioOpcional3AnalizadorArchivosBinariosReporte {
    public static void main(String[] args) {
        try{
            // Construir la ruta absoluta del archivo
            String rutaRaiz = Paths.get("").toAbsolutePath().toString();
            String rutaProperties = rutaRaiz + "\\src\\TerceraPracticaOpcionales\\datos.dat";

            //Crear archivo binario:
            DataOutputStream dos = new DataOutputStream(new FileOutputStream(rutaProperties));
            dos.writeInt(100);
            dos.writeUTF("Producto A");
            dos.writeDouble(99.99);
            dos.writeBoolean(true);
            dos.writeInt(200);
            dos.close();

        }catch(Exception e){
            System.err.println("Error al guardar datos.dat");
        }
        try{
            // Analizar:
            Reporte reporte = analizarArchivoBinario("datos.dat");
            mostrarReporte(reporte);
            guardarReporte(reporte, "reporte_datos.txt");
        }catch (Exception e){
            System.err.println("Error al guardar datos.dat");
        }
    }
    public static Reporte analizarArchivoBinario(String archivo) throws IOException {
        String rutaRaiz = Paths.get("").toAbsolutePath().toString();
        String rutaProperties = rutaRaiz + "\\src\\TerceraPracticaOpcionales\\";
        String rutaFinal = rutaProperties + archivo;

        File file = new File(rutaFinal);
        if (!file.exists()) {
            throw new FileNotFoundException("No se encuentra el archivo: " + rutaFinal);
        }

        Reporte reporte = new Reporte();
        reporte.setNombreArchivo(file.getName());
        reporte.setTamañoBytes(file.length());
        reporte.setElementos(new ArrayList<>());

        DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
        long posicion = 0;

        while (dis.available() > 0) {
            String tipo = detectarTipoDato(dis);
            String valor = "";

            // ✅ LEER Y CONSUMIR los datos según el tipo detectado
            try {
                if (tipo.equals("int")) {
                    int v = dis.readInt();
                    valor = String.valueOf(v);
                    reporte.setTotalInts(reporte.getTotalInts() + 1);
                } else if (tipo.equals("String")) {
                    valor = dis.readUTF();
                    reporte.setTotalStrings(reporte.getTotalStrings() + 1);
                } else if (tipo.equals("double")) {
                    double v = dis.readDouble();
                    valor = String.valueOf(v);
                    reporte.setTotalDoubles(reporte.getTotalDoubles() + 1);
                } else if (tipo.equals("boolean")) {
                    boolean v = dis.readBoolean();
                    valor = String.valueOf(v);
                }
            } catch (EOFException e) {
                break; // Fin del archivo
            }

            // Crear el elemento con el valor leído
            ElementoDato elemento = new ElementoDato();
            elemento.setPosicion((int) posicion);
            elemento.setTipo(tipo);
            elemento.setValor(valor);
            reporte.getElementos().add(elemento);

            posicion++;
        }

        dis.close();
        return reporte;
    }

    static String detectarTipoDato(DataInputStream dis) throws IOException {
        //  Marcar con suficiente espacio (65535 es el máximo para UTF)
        dis.mark(65535);

        try {
            // Intentar leer como String UTF primero
            String valorString = dis.readUTF();
            dis.reset();

            // Verificar que contiene caracteres válidos
            if (valorString.length() > 0 &&
                    valorString.chars().allMatch(c ->
                            (c >= 32 && c <= 126) || c == 9 || c == 10 || c == 13 || (c >= 160))) {
                return "String";
            }
        } catch (Exception e) {
            dis.reset();
        }

        // Intentar leer como int
        try {
            dis.mark(4);
            int valorInt = dis.readInt();
            dis.reset();

            if (valorInt >= -1000000 && valorInt <= 1000000) {
                return "int";
            }
        } catch (Exception e) {
            dis.reset();
        }

        // Intentar leer como double
        try {
            dis.mark(8);
            double valorDouble = dis.readDouble();
            dis.reset();

            if (!Double.isNaN(valorDouble) && !Double.isInfinite(valorDouble)) {
                return "double";
            }
        } catch (Exception e) {
            dis.reset();
        }

        return "boolean";
    }
    public static void guardarReporte(Reporte reporte, String archivo) throws IOException {
        // Construir la ruta absoluta del archivo
        String rutaRaiz = Paths.get("").toAbsolutePath().toString();
        String rutaProperties = rutaRaiz + "\\src\\TerceraPracticaOpcionales\\";
        String rutaFinal = rutaProperties + archivo;
        BufferedWriter bw = new BufferedWriter(new FileWriter(rutaFinal));

        bw.write("=== REPORTE DE ARCHIVO BINARIO ===");
        bw.newLine();
        bw.write("Nombre del archivo: " + reporte.getNombreArchivo());
        bw.newLine();
        bw.write("Tamaño (bytes): " + reporte.getTamañoBytes());
        bw.newLine();
        bw.newLine();
        bw.write("Totales detectados:");
        bw.newLine();
        bw.write(" - Enteros (int): " + reporte.getTotalInts());
        bw.newLine();
        bw.write(" - Dobles (double): " + reporte.getTotalDoubles());
        bw.newLine();
        bw.write(" - Cadenas (String): " + reporte.getTotalStrings());
        bw.newLine();
        bw.newLine();
        bw.write("=== Detalle de elementos detectados ===");
        bw.newLine();

        for (ElementoDato elemento : reporte.getElementos()) {
            bw.write("Posición: " + elemento.getPosicion());
            bw.newLine();
            bw.write("Tipo: " + elemento.getTipo());
            bw.newLine();
            bw.write("Valor: " + elemento.getValor());
            bw.newLine();
            bw.write("----------------------------------------");
            bw.newLine();
        }

        bw.newLine();
        bw.write("=== FIN DEL REPORTE ===");
        bw.newLine();

        bw.close();
    }
    public static void mostrarReporte(Reporte reporte) {
        System.out.println("=== REPORTE DE ARCHIVO BINARIO ===");
        System.out.println("Nombre del archivo: " + reporte.getNombreArchivo());
        System.out.println("Tamaño (bytes): " + reporte.getTamañoBytes());
        System.out.println();

        System.out.println("Totales detectados:");
        System.out.println(" - Enteros (int): " + reporte.getTotalInts());
        System.out.println(" - Dobles (double): " + reporte.getTotalDoubles());
        System.out.println(" - Cadenas (String): " + reporte.getTotalStrings());
        System.out.println();

        System.out.println("=== Detalle de elementos detectados ===");
        for (ElementoDato elemento : reporte.getElementos()) {
            System.out.println("Posición: " + elemento.getPosicion());
            System.out.println("Tipo: " + elemento.getTipo());
            System.out.println("Valor: " + elemento.getValor());
            System.out.println("----------------------------------------");
        }

        System.out.println("=== FIN DEL REPORTE ===");
    }

}


class Reporte {
    private String nombreArchivo;
    private long tamañoBytes;
    private List<ElementoDato> elementos;
    private int totalInts;
    private int totalDoubles;
    private int totalStrings;
    public Reporte(){}
    public Reporte(String nombreArchivo, long tamañoBytes, List<ElementoDato> elementos, int totalInts, int totalDoubles, int totalStrings) {
        this.nombreArchivo = nombreArchivo;
        this.tamañoBytes = tamañoBytes;
        this.elementos = elementos;
        this.totalInts = totalInts;
        this.totalDoubles = totalDoubles;
        this.totalStrings = totalStrings;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public long getTamañoBytes() {
        return tamañoBytes;
    }

    public void setTamañoBytes(long tamañoBytes) {
        this.tamañoBytes = tamañoBytes;
    }

    public List<ElementoDato> getElementos() {
        return elementos;
    }

    public void setElementos(List<ElementoDato> elementos) {
        this.elementos = elementos;
    }

    public int getTotalInts() {
        return totalInts;
    }

    public void setTotalInts(int totalInts) {
        this.totalInts = totalInts;
    }

    public int getTotalDoubles() {
        return totalDoubles;
    }

    public void setTotalDoubles(int totalDoubles) {
        this.totalDoubles = totalDoubles;
    }

    public int getTotalStrings() {
        return totalStrings;
    }

    public void setTotalStrings(int totalStrings) {
        this.totalStrings = totalStrings;
    }
    // Constructor, getters y setters
}
class ElementoDato {
    private int posicion;
    private String tipo;
    private String valor;
    public ElementoDato(){}
    public ElementoDato(int posicion, String tipo, String valor) {
        this.posicion = posicion;
        this.tipo = tipo;
        this.valor = valor;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
