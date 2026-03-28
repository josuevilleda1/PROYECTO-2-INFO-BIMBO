import java.util.ArrayList;
import java.util.Scanner;

public class TestRunner {
    private static void mostrarEncabezado() {
        System.out.println(
"████████╗███████╗███████╗████████╗    ██╗   ██╗██╗███╗   ███╗██████╗  ██████╗\n" +
"╚══██╔══╝██╔════╝██╔════╝╚══██╔══╝    ██║   ██║██║████╗ ████║██╔══██╗██╔═══██╗\n" +
"   ██║   █████╗  ███████╗   ██║       ██║   ██║██║██╔████╔██║██████╔╝██║   ██║\n" +
"   ██║   ██╔══╝  ╚════██║   ██║       ╚██╗ ██╔╝██║██║╚██╔╝██║██╔══██╗██║   ██║\n" +
"   ██║   ███████╗███████║   ██║        ╚████╔╝ ██║██║ ╚═╝ ██║██████╔╝╚██████╔╝\n" +
"   ╚═╝   ╚══════╝╚══════╝   ╚═╝         ╚═══╝  ╚═╝╚═╝     ╚═╝╚═════╝  ╚═════╝"
        );
    }

    private static void mostrarMenu() {
        System.out.println("1) Prueba lectura de AFD");
        System.out.println("2) GRafos creados");
        System.out.println("3) prueba de estructura del arbol");
        System.out.println("4) busqueda de arbol");
        System.out.println("0) Salir");
    }

    private static void pausa(Scanner scanner) {
        System.out.println("Presione ENTER para continuar...");
        scanner.nextLine(); 
        scanner.nextLine();
    }

    private static void limpiarPantalla() {
        for (int i = 0; i < 2; i++) {
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;
        String rutaAFD = "/Users/josuevilleda/Downloads/archivos_base/tests/afds/binary.afd";

        do {
            limpiarPantalla();
            mostrarEncabezado();
            mostrarMenu();
            
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            
            if (opcion == 0) {
                System.out.println("Saliendo...");
                break; 
            }

            // Variable para usar en los casos sin redeclarar
            AFD pruebaAFD; 

            switch (opcion) {
                case 1:
                    pruebaAFD = new AFD(rutaAFD);
                    ArrayList<String> datos = pruebaAFD.parseo();
                    System.out.println("abecedario: " + datos.get(0));
                    System.out.println("Numero de estados: " + datos.get(1));
                    System.out.println("Estados Finales: " + datos.get(2));
                    
                    int nEstados = Integer.parseInt(datos.get(1));
                    for(int i = 0; i < nEstados; i++){
                        System.out.println("transiciones del estado " + i + ": " + datos.get(i+3));
                    }
                    pausa(scanner);
                    break;

                case 2:
                    System.out.println("--- AUDITORÍA DE CONEXIONES INTERNAS (GRAFO) ---");
                    pruebaAFD = new AFD(rutaAFD); 
                    pruebaAFD.imprimirEstructuraInterna(); 
                    pausa(scanner);
                    break;
                
                default:
                    System.out.println("Opción no implementada aún.");
                    pausa(scanner);
                    break;
            }
        } while (opcion != 0); 
        
        scanner.close();
    }
}