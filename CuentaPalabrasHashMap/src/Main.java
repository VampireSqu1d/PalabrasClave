import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

public class Main {

    private static ArrayList<String> CargaPalabras(String ruta) throws IOException {
        Scanner scanner = new Scanner(new File(ruta));
        ArrayList<String> palabras = new ArrayList<>();
        while (scanner.hasNext())   palabras.add(scanner.next());
        return palabras;
    }

    private static HashMap<String, Integer> CargaMapa(Queue<String> rutas, ArrayList<String> palabrasComunes) throws IOException {
        HashMap<String, Integer> palabrasArchivo = new HashMap<>();
        while (!rutas.isEmpty()){
            Scanner scanner = new Scanner(new File(rutas.remove()));
            String palabra;
            while (scanner.hasNext()) {
                palabra = scanner.next().replaceAll("\\W", "").toLowerCase();
                if ( !palabrasComunes.contains(palabra) && !palabra.matches("^[0-9]*")) {
                    if (palabrasArchivo.containsKey(palabra))  palabrasArchivo.replace(palabra, palabrasArchivo.get(palabra) + 1);
                    else palabrasArchivo.put(palabra, 1);
                }
            }
        }
        return palabrasArchivo;
    }

    private static Queue<String> ListaRutas(String ruta){
        Queue<String> archivos = new LinkedList<>();
        Stack<String> directorios = new Stack<>();
        directorios.push(ruta);
        while (!directorios.empty()){
            File fail = new File(directorios.pop());
            File[] listado = fail.listFiles();
            if (listado !=null) {
                for( File archivo : listado){
                    if (archivo.isDirectory()) directorios.push(archivo.getPath());
                    else if (archivo.isFile()) archivos.add(archivo.getAbsolutePath());
                }
            }
        }
        return archivos;
    }

    private static void Despliega(HashMap<String,Integer> palabras){
        //System.out.println("Numero de palabras clave " + palabras.size());
        for (String s : palabras.keySet()) System.out.printf("%s       %d%n", s, palabras.get(s));
    }

    private  static void Salida(HashMap<String, Integer> palabras) throws IOException {
        //File salida = new File(ruta.substring(ruta.lastIndexOf("\\") + 1, ruta.length() - 4) + "-HF.txt");
        File salida = new File("global-HF.txt");
        PrintStream stream = new PrintStream(salida);
        for (String s : palabras.keySet())  stream.printf( "%s      %d%n" ,s, palabras.get(s));
        stream.flush();
    }

    private static void Especifica(HashMap<String, Integer> mapa, String palabra){
        System.out.println("=================================");
        if (mapa.containsKey(palabra.toLowerCase()))   System.out.printf("la palabra %s apararece %d veces :)%n",palabra.toLowerCase() , mapa.get(palabra.toLowerCase()));
        else System.err.printf("La palabra '%s' no aparece en los archivos :(%n", palabra);
        System.out.println("=================================");
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 2) System.exit(0);

        ArrayList<String> palabrasComunes = CargaPalabras(args[0]);
        Queue<String> rutas = ListaRutas(args[1]);
        HashMap mapa = CargaMapa( rutas, palabrasComunes);
        Despliega(mapa);
        Salida(mapa);
        if (args.length ==3) Especifica(mapa, args[2]);
    }
}
