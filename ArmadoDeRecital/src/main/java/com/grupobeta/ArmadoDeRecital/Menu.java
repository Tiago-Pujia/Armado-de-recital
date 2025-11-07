package com.grupobeta.ArmadoDeRecital;

import java.util.HashMap;
import java.util.Scanner;
import java.util.function.Consumer;

public class Menu {
	
	public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final int MIN_ACEPTADO = 1;
    
    private HashMap<Integer, Consumer<Scanner>> opciones;
    private Recital recital = null;
    private Scanner scanner = null;
    private boolean estaCorriendo = true;
    
    public Menu() {
    	this.scanner = new Scanner(System.in);
    	opciones = new HashMap<Integer, Consumer<Scanner>>();
    	this.configurarOpciones();
    	this.recital = new Recital();
    }
    
    private void configurarOpciones() {
    	opciones.put(1, this::obtenerRolesFaltantesParaCancion);
    	opciones.put(2, this::obtenerRolesFaltantesAll);
    	opciones.put(3, this::contratarArtistasParaCancion);
    	opciones.put(4, this::contratarArtistasAll);
    	opciones.put(5, this::entrenarArtista);
    	opciones.put(6, this::listarArtistasContratados);
    	opciones.put(7, this::listarCanciones);
    	opciones.put(8, this::prolog);
    	opciones.put(9, this::salir);
    }
    
    public void iniciar() {
    	
    	int eleccion;
    	
    	while(this.estaCorriendo) {
    		this.mostrarMenu();
    		do {
    			eleccion = this.procesarEleccion();
    		}while(eleccion < MIN_ACEPTADO || eleccion > opciones.size());
    		
    		this.ejecutarOpcion(eleccion);
    	}
    }
    
	public void mostrarMenu() {
		System.out.println("=============================" + ANSI_GREEN + "ARMADO DE RECITAL" + ANSI_RESET + "============================");
		System.out.println("|| " + "\t\t\t\t\t\t\t\t\t" + "||");
		System.out.println("|| " + ANSI_CYAN + "1." + ANSI_RESET + "¿Qué roles me faltan para tocar una canción X del recital?\t\t" + "||");
		System.out.println("|| " + ANSI_CYAN + "2." + ANSI_RESET + "¿Qué roles me faltan para tocar todas las canciones?\t\t" + "||");
		System.out.println("|| " + ANSI_CYAN + "3." + ANSI_RESET + "Contratar artistas para una canción X del recital\t\t\t" + "||");
		System.out.println("|| " + ANSI_CYAN + "4." + ANSI_RESET + "Contratar artistas para todas las canciones a la vez\t\t" + "||");
		System.out.println("|| " + ANSI_CYAN + "5." + ANSI_RESET + "Entrenar artista\t\t\t\t\t\t\t" + "||");
		System.out.println("|| " + ANSI_CYAN + "6." + ANSI_RESET + "Listar artistas contratados, su información relevante y su costo\t" + "||");
		System.out.println("|| " + ANSI_CYAN + "7." + ANSI_RESET + "Listar canciones con su estado\t\t\t\t\t" + "||");
		System.out.println("|| " + ANSI_CYAN + "8." + ANSI_RESET + "[PROLOG]\t\t\t\t\t\t\t\t" + "||");
		System.out.println("|| " + ANSI_CYAN + "9." + ANSI_RESET + "Salir\t\t\t\t\t\t\t\t" + "||");
		System.out.println("|| " + "\t\t\t\t\t\t\t\t\t" + "||");
		System.out.println("|| " + "Seleccione una opcion\t\t\t\t\t\t" + "||");
		System.out.println("|| " + "\t\t\t\t\t\t\t\t\t" + "||");
		System.out.println("==========================================================================");
	}
	
	public int procesarEleccion() {
		return this.scanner.nextInt();
	}
	
	public void ejecutarOpcion(int opcion){
		Consumer<Scanner> accion = this.opciones.get(opcion);
        accion.accept(this.scanner);		
	}
	
	///punto 1
	public void obtenerRolesFaltantesParaCancion(Scanner scanner) {
		
	}
	
	///punto 2
	public void obtenerRolesFaltantesAll(Scanner scanner) {
		
	}
	
	///punto 3
	public void contratarArtistasParaCancion(Scanner scanner) {
		
	}
	
	///punto 4
	public void contratarArtistasAll(Scanner scanner) {
		
	}
	
	///punto 5
	public void entrenarArtista(Scanner scanner) {
		
	}
	
	///punto 6
	public void listarArtistasContratados(Scanner scanner) {
		
	}
	
	///punto 7
	public void listarCanciones(Scanner scanner) {
		
	}
	
	public void prolog(Scanner scanner) {
			
	}

	public void salir(Scanner scanner) {
		this.estaCorriendo = false;
		scanner.close();
		System.out.println("\nGuardando estado antes de salir...");
		ManejadorSalida manejador = new ManejadorSalida();
		manejador.guardarEstado(this.recital);
	}
	// faltan los métodos para manejar las opciones, aunque cómo lo hacemos sin spamear ifs o un switch ??
}
