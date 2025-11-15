package com.grupobeta.ArmadoDeRecital;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.function.Consumer;

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
    
    private HashMap<Integer, Consumer<Scanner>> opciones;
    private CargadorDeArchivos cargadorDeArchivos = null;
    
    private Recital recital = null;
    private Scanner scanner = null;
    private boolean estaCorriendo = true;
    
    public Menu() {
    	
    	this.scanner = new Scanner(System.in);
    	opciones = new HashMap<Integer, Consumer<Scanner>>();
    	this.configurarOpciones();
    	this.cargadorDeArchivos = new CargadorDeArchivos();
    	this.crearRecital();
    }
    
    private void crearRecital() {
    	this.recital = new Recital(this.cargadorDeArchivos);
	}

	private void configurarOpciones() {
    	opciones.put(1, this::consultarRolesFaltantesParaCancion);
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
		Consumer<Scanner> accion = this.opciones.get(opcion);
        accion.accept(this.scanner);		
	}
	
	///punto 1
	public void consultarRolesFaltantesParaCancion(Scanner scanner) {
		
		System.out.printf("Ingrese el nombre de la canción: ");
		scanner.nextLine();
		String nombreCancion = scanner.nextLine();
		Cancion cancion = recital.buscarCancion(nombreCancion);
		HashMap<String, Integer> rolesFaltantes = this.recital.consultarRolesFaltantesParaCancion(cancion);
		
		if(rolesFaltantes == null) {
			System.out.println("El nombre ingresado" + ANSI_RED + " no coincide " + ANSI_RESET + "con ninguna canción registrada para el recital. Intente nuevamente.");
			return;
		}
		if(rolesFaltantes.isEmpty()) {
			System.out.println("La canción ingresada tiene todos sus roles" + ANSI_GREEN + " cubiertos" + ANSI_RESET);
			return;
		}
		System.out.println(ANSI_PURPLE + nombreCancion + ANSI_RESET + "\nRoles faltantes por cubrir:\n" + ANSI_RED + rolesFaltantes + ANSI_RESET + "\n");		
	}
	
	///punto 2
	public void obtenerRolesFaltantesAll(Scanner scanner) {
		int i = 1;
		System.out.println("Listado de " + ANSI_YELLOW + "canciones" + ANSI_RESET + " del recital. Se muestra para cada una de ellas los roles aún no cubiertos.\n");
		for(Cancion cancion : this.recital.getCanciones()) {
			if(cancion.getRolesRequeridos().isEmpty()) {
				System.out.println(i + ". " + ANSI_PURPLE + cancion.getTitulo() + ANSI_CYAN + "\nNo quedan roles por cubrir !\n" + ANSI_RESET);
			}
			else {
				System.out.println(i + ". " + ANSI_PURPLE + cancion.getTitulo() + ANSI_RESET + "\nRoles faltantes por cubrir:\n" + ANSI_RED + cancion.getRolesRequeridos() + ANSI_RESET + "\n");
			}
			i++;
		}
	}
	
	///punto 3 <L>---------ACÁ ESTOY HOY 13/11 ----------<\L>

	public void contratarArtistasParaCancion(Scanner scanner) {
		
		System.out.printf("Ingrese el nombre de la canción: ");
		scanner.nextLine();
		String nombreCancion = scanner.nextLine();
		Cancion cancion = recital.buscarCancion(nombreCancion);
		
		if(cancion == null) {
			System.out.println("La canción ingresada es inválida");
		}
		
		if(recital.contratarArtistasParaCancion(cancion) == CodigoDeRetorno.NO_SE_PUEDEN_CUBRIR_TODOS_LOS_ROLES) {
			System.out.println("NO SE PUEDEN CUBRIR TODOS LOS ROLES");
		}
	
	}
	
	///punto 4
	public void contratarArtistasAll(Scanner scanner) {
		
	}
	
	///punto 5
	public void entrenarArtista(Scanner scanner) {
		
		System.out.printf("Ingrese el nombre del artista: ");
		scanner.nextLine();
		String nombre = scanner.nextLine();
		ArtistaContratable artista = recital.buscarArtistaContratable(nombre);
		
		System.out.printf("Ingrese el rol que quieres darle: ");
		scanner.nextLine();
		String rol = scanner.nextLine();
	
		recital.entrenarArtista(artista, rol);
		System.out.printf("El Artista" + nombre + "ha sido entrenado");
	}
	
	///punto 6
	public void listarArtistasContratados(Scanner scanner) {
		ArrayList<Contratacion> contratos = this.recital.listarArtistasContratados();
		System.out.println("Listado de " + ANSI_CYAN + "artistas" + ANSI_RESET + " contratados\n");
		for(Contratacion contrato : contratos) {
			System.out.println(ANSI_CYAN + contrato.getArtista().getNombre() + ANSI_RESET + " - " + ANSI_YELLOW + contrato.getCancion().getTitulo() + ANSI_RESET);
		}
	}
	
	///punto 7
	public void listarCanciones(Scanner scanner) {
		
		int i = 1;
		
		for(Cancion cancion : recital.getCanciones()) {
			System.out.println(ANSI_YELLOW + i + ". " + ANSI_PURPLE + cancion.getTitulo() + ANSI_GREEN + " - Costo: $" + recital.obtenerCostoContratacionesCancion(cancion) + ANSI_RESET);
			i++;
			if(cancion.getRolesRequeridos().isEmpty()) {
				System.out.println(ANSI_CYAN + "No quedan roles por cubrir !\n" + ANSI_RESET);
			}
			else {
				System.out.println("Roles faltantes por cubrir:\n" + ANSI_RED + cancion.getRolesRequeridos() + ANSI_RESET + "\n");		
			}
		}
	}
	
	public void prolog(Scanner scanner) {
			
	}

	public void salir(Scanner scanner) {
		this.estaCorriendo = false;
		System.out.println("\nGuardando estado antes de salir...");
		//ManejadorSalida manejador = new ManejadorSalida();
		//manejador.guardarEstado(this.recital);
	}
}
