package com.grupobeta.ArmadoDeRecital;

import java.util.HashMap;
import java.util.Scanner;

public class Menu {
	
	//colores para decorar el texto de la interfaz
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
    
    private HashMap<Integer, Comando> opciones;
    private CargadorDeArchivos cargadorDeArchivos = null;
    
    private Recital recital = null;
    private Scanner scanner = null;
    private boolean estaCorriendo = true;
    
   	public Menu() {
    	
    	this.scanner = new Scanner(System.in);
    	opciones = new HashMap<>();
    	this.cargadorDeArchivos = new CargadorDeArchivos();
    	this.crearRecital();
    	this.configurarOpciones();
    }
    
    private void crearRecital() {
    	this.recital = new Recital(this.cargadorDeArchivos);
	}

	private void configurarOpciones() {
    	opciones.put(1, new ConsultarRolesFaltantesParaCancionComando(scanner, recital));
    	opciones.put(2, new ConsultarRolesFaltantesAllComando(recital));
    	opciones.put(3, new ContratarArtistasParaCancionComando(scanner, recital));
    	opciones.put(4, new ContratarArtistasAllComando(recital));
    	opciones.put(5, new EntrenarArtistaComando(scanner, recital));
    	opciones.put(6, new ListarArtistasComando(recital));
    	opciones.put(7, new ListarCancionesComando(recital));
    	opciones.put(8, new PrologComando());
    	opciones.put(9, new SalirComando(this));
    }
    
    public void iniciar() {
    	
    	int eleccion;
    	
    	// desea cargar un estado previo ? Y/NO -> manejadorDeArchivosDeEntrada.cargarEstado()
    	
    	while(this.estaCorriendo) {
    		
    		this.mostrarMenu();
    		do {
    			eleccion = this.scanner.nextInt();
    		}while(eleccion < MIN_ACEPTADO || eleccion > opciones.size());
    		
    		this.ejecutarOpcion(eleccion);
    		
    		scanner.nextLine();
    		System.out.println("Presione enter");
    		scanner.nextLine();
    	}
    	
    	this.scanner.close();
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
	
	public void ejecutarOpcion(int opcion){
		Comando comando = this.opciones.get(opcion);
        comando.ejecutar();		
	}
	
	public void setEstaCorriendo(boolean estaCorriendo) {
			this.estaCorriendo = estaCorriendo;
	}

}
