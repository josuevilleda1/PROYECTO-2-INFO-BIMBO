// Esta es su clase principal.
// Debe implementar todos los metodos aqui listados.
// No cambie la firma del constructor ni de los metodos ya creados.
// Puede crear cualquier metodo extra que desee
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.TreeSet;

public class AFD {
    private static final String stringFormat = "Alphabet: %s, Final States: %s, Error States: %s, Minimum: %b";

    // arreglo usado para almacenar los datos de lectura que tenga el el archivo .AFD
    private ArrayList<String> contenido;
    /*Este es quien nos ayuda a crear nuestro AFD se usa el mismo isstema de creacion de nodos 
    como se utiliza 
    1) el primer ineteger nos indica el nombre del estado 
    2) el tre map nos ayuda a saber hacia quien esta unido esto es como una recursuvada interna porque todo se va a ir guardando dentro de ese tre map y ser aempacado uno encima del otro 
    el string es el peso de la cuerda osea como es que se mueve hacia ese estado si es a o b o c y el integer no indic hacia que estado es quien puede realizar ese momvimiento  */
    private TreeMap<Integer, TreeMap<String, Integer>> grafo;
    // guarda el alfabto de este AFD
    private String[] alfabeto;
    // esto nos ayuda a guradar los estados finales se uso el nos servira para comparar cuando se recorra si es o no es una cuerda aceptada 
    private ArrayList<Integer> estadosFinales;

    public AFD(String path) {
        this.contenido = new ArrayList<>();
        this.grafo = new TreeMap<>();
        this.estadosFinales = new ArrayList<>();

        // lector del archivo AFD
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String linea;
            
            while ((linea = reader.readLine()) != null) {
                contenido.add(linea);
            }
            reader.close();
        } catch (IOException e) {
            
        }
        this.alfabeto = contenido.get(0).split(",");
        // Creacion del AFD
        construirGrafo();        
    }
    public  ArrayList<String> parseo(){
        return this.contenido;
    }
    public boolean accept(String str) {
        // Revisa si la cuerda str es aceptada o no por el AFD
        int estadoInicial = 0; 
        for(int i=0; i < str.length();i++){
        // comparamos letra por letra para irla buscaando 
            String buscado = String.valueOf(str.charAt(i));
            //se para en el estado inicial y luego busca la palabra para ver si es cierto
            if(this.grafo.get(estadoInicial).containsKey(buscado)){
                // se cambia de estado se ueve al siguiente estado 
                estadoInicial = this.grafo.get(estadoInicial).get(buscado);
            } else return false ;
        }
        // hace una comparativa entre el estado donde llego si es un estado final para ver si donde llego es un estado final 
        boolean comprobante = estadosFinales.contains(estadoInicial);
        return comprobante;
    }
    
    public boolean isMin() {
        // Devuelve true si este AFD esta en su forma minima. Falso de lo contrario
        return false;
    }
    
    public String toString() {
        String alphabet = Arrays.toString(this.getAlphabet());
        String finalStates = Arrays.toString(this.getFinalStates());
        String errorStates = Arrays.toString(this.getErrorStates());
        return String.format(stringFormat, alphabet, finalStates, errorStates, this.isMin());
    }
    private String[] getAlphabet() {
        return new String[0];
    }
    
    private int[] getFinalStates() {
        return new int[0];
    }
    
    private int[] getErrorStates() {
        return new int[0];
    }
    
    // Implemente los metodos que desee a partir de aqui
    /*Metodo utilizado para crear el AFD 
    como funciona utilizando los metodos del tremap usamos par apoder unir los pedasos de la informacion leida 
    como el put
    en este tambien se asigna a sus lugares correpondientes los estados finales y se les da su nombre 
    el putIfAbsent se usa en dado caso que el esatdo no exista aun en los grafos se crea uno para que no tengamos un error en ese moemnto 
    */
    private void construirGrafo() {
        // se añade al arraylist los estados finales
        String[] finales = contenido.get(2).split(",");
        for (String f : finales) {
            estadosFinales.add(Integer.parseInt(f));
        }
        // se cuenta la cantidad de estados que tenemos 
        int cantidadEstados = Integer.parseInt(contenido.get(1));
        // Union de los estados cracion de sus lineas de trasncicion 
        
        for (int i = 0; i < cantidadEstados; i++) {
            int estadoOrigen = i; 
            String lineaTransicion = contenido.get(i + 3);
            String[] destinos = lineaTransicion.split(",");
            // CASO ESPECIAL DODNE NO EXISTA UN ESTADO Y SE TENGA QUE CREAR DE EMERGENCIA PARA QUE NO TENGAMOS UN ERROR 
            this.grafo.putIfAbsent(estadoOrigen, new TreeMap<>());
            // AQUI UNIMOS LOS ESTADOS A SUS ESTADOS QUE SE DIRIJEN 
            for (int j = 0; j < destinos.length; j++) {
                String simbolo = alfabeto[j];
                int estadoDestino = Integer.parseInt(destinos[j]);
                this.grafo.get(estadoOrigen).put(simbolo, estadoDestino);
            }
        }
    }
    // se usa solo para la veridicacio de nuestra propoa clase de test 
    public void imprimirEstructuraInterna() {
        System.out.println("Verificando Grafo Dirigido:");
        
        // Recorremos el mapa principal (los estados origen)
        for (Integer estadoOrigen : this.grafo.keySet()) {
            System.out.print("Estado [" + estadoOrigen + "] conexiones: ");
            
            // Obtenemos el mapa interno de ese estado (sus flechas)
            TreeMap<String, Integer> misTransiciones = this.grafo.get(estadoOrigen);
            
            // Recorremos cada transición posible (cada símbolo del alfabeto)
            misTransiciones.forEach((simbolo, estadoDestino) -> {
                System.out.print(" --(" + simbolo + ")--> " + estadoDestino + " | ");
            });
            
            System.out.println(); // Salto de línea para el siguiente estado
        }
    }
}

