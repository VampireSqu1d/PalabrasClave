import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;
//Ayala Bautista Cuauhtémoc
public class Main {

    private static ArrayList<String> CargaPalabras(String ruta) throws IOException {
        Scanner scanner = new Scanner(new File(ruta));
        ArrayList<String> palabras = new ArrayList<>();
        while (scanner.hasNext())  palabras.add(scanner.next().toLowerCase());
        return palabras;
    }

    private static String[] CargaArchivo(String ruta) throws IOException {
        Scanner scanner = new Scanner(new File(ruta));
        ArrayList<String> palabras = new ArrayList<>(); String palabra;
        while (scanner.hasNext()) {
            palabra = scanner.next().replaceAll("\\W", "").toLowerCase();
            if (!palabra.equals("")) palabras.add(palabra);
        }
        return palabras.toArray(new String[0]);
    }

    private static ArrayList<String> GeneraLista(ArrayList<String> lista, String[] arreglo){
        ArrayList<String> repetidas = new ArrayList<>();
        String actual = "";
        for (String s : arreglo){
            if (s != null) {
                int repeticiones = 0;
                if (!lista.contains(s)) {
                    actual = s;
                    for (int i = 0; i < arreglo.length; i++) {
                        if (arreglo[i] != null) {
                            if (arreglo[i].equals(s)) {
                                repeticiones++;
                                arreglo[i] = null;
                            }
                        }
                    }
                }
                if (repeticiones > 0 && !actual.matches("^[0-9]+")) repetidas.add(actual + "       " + repeticiones);
            }
        }
        return repetidas;
    }

    private static void Despliega(ArrayList<String> palabras){
        for (String s: palabras) System.out.println(s);
    }

    private  static void Salida(ArrayList<String> palabras, String ruta) throws IOException {
        int indice = ruta.lastIndexOf("\\") + 1;
        String nombre = ruta.substring(indice, ruta.length() - 4) + "-FH.txt";
        File salida = new File(nombre);
        PrintStream stream = new PrintStream(salida);
        for (String s : palabras) stream.println(s);
        stream.flush();
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 2) System.exit(0);
        if (args.length == 2){
            ArrayList<String> palabrasComunes = CargaPalabras(args[0]);
            String[] archivo = CargaArchivo(args[1]);
            ArrayList<String> lista = GeneraLista(palabrasComunes, archivo);
            Despliega(lista);
            Salida(lista, args[1]);
        }
    }
}
