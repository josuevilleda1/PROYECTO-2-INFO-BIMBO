import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
        System.out.println("3) busqueda de AFD");
        System.out.println("4) AFD MIN");
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
        String rutaAFD = "/Users/josuevilleda/PROYECTO-2-INFO-BIMBO/tests/afds/2.afd";
        String pareseo = "/Users/josuevilleda/PROYECTO-2-INFO-BIMBO/tests/strings/2.txt";
        
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
            AFD pruebaAFD; 
            switch (opcion) {
                case 1:
                    pruebaAFD = new AFD(rutaAFD);
                    ArrayList<String> datos = pruebaAFD.parseo();
                    System.out.println("--- PRUEBA DE LECTURAS DE ARCHIVOS .AFD ---");
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
                    System.out.println("--- ARAMADO DE AFD ---");
                    pruebaAFD = new AFD(rutaAFD); 
                    pruebaAFD.imprimirEstructuraInterna(); 
                    pausa(scanner);
                    break;
                
                case 3:
                    System.out.println("--- PARSEO DE CUERDAS ---");
                    ArrayList <String> contenido = new ArrayList<>();
                    pruebaAFD = new AFD(rutaAFD); 
                    try {
                        BufferedReader reader = new BufferedReader(new FileReader(pareseo));
                        String linea;
                        while ((linea = reader.readLine()) != null) {
                            contenido.add(linea);
                        }
                        reader.close();
                    } catch (IOException e) {
                            
                        }
                    for(int i = 0; i < contenido.size(); i++){
                        if((pruebaAFD.accept(contenido.get(i)))){
                            System.out.println("Cuerdad: " + contenido.get(i) + " Fue aceptada por el AFD" );     
                        } else {
                            System.out.println("Cuerdad: " + contenido.get(i) + " No fue aceptada por el AFD" );     
                        }
                    }
                    pausa(scanner);
                    break;
                
                    case 4: 
                        System.out.println("--- VERIFICAR SI EL AFD ES MÍNIMO ---");
                        pruebaAFD = new AFD(rutaAFD);
                        if (pruebaAFD.isMin()) {
                            System.out.println("El AFD ES mínimo");
                        } else {
                            System.out.println(" El AFD NO es mínimo");
                        }
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