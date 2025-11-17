package com.grupobeta.ArmadoDeRecital;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.util.function.Consumer;


//Cosas de prolog
import org.jpl7.Query;
import org.jpl7.JPLException;
import org.jpl7.Term;
//Cosas de prolog

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
	
	///punto 3 <L>---------ACÁ sigo HOY 15/11 ----------<\L>

	public void contratarArtistasParaCancion(Scanner scanner) {
		
		System.out.printf("Ingrese el nombre de la canción: ");
		scanner.nextLine();
		String nombreCancion = scanner.nextLine();
		Cancion cancion = recital.buscarCancion(nombreCancion);
		
		if(cancion==null) {
			System.out.println("El nombre ingresado no corresponde con ninguna canción del recital");
			return;
		}
		
		CodigoDeRetorno resultado = recital.contratarArtistasParaCancion(cancion);
		
		if( resultado == CodigoDeRetorno.NO_SE_PUEDEN_CUBRIR_TODOS_LOS_ROLES) {
			System.out.println("Hubo roles que no pudieron ser cubiertos, ¿Quiere entrenar a un artista?");
		}	
		if( resultado == CodigoDeRetorno.YA_TIENE_LOS_ROLES_CUBIERTOS) {
			System.out.println(ANSI_CYAN + "La cancion ya tiene todos sus roles cubiertos." + ANSI_RESET);
		}	
	}
	
	///punto 4 <-------------FALTAN SOLO EL 3 Y EL 4----------------->
	public void contratarArtistasAll(Scanner scanner) {
		recital.contratarArtistasAll();
	}
	
	///punto 5
	public void entrenarArtista(Scanner scanner) {
		
		System.out.printf("Ingrese el nombre del artista: ");
		scanner.nextLine();
		String nombre = scanner.nextLine();
		ArtistaContratable artista = recital.buscarArtistaContratable(nombre);
		
		System.out.printf("Ingrese el rol que quieres darle: ");
		String rol = scanner.nextLine();
	
		recital.entrenarArtista(artista, rol);
		System.out.printf("El Artista" + nombre + "ha sido entrenado");
	}
	
	///punto 6
	public void listarArtistasContratados(Scanner scanner) {
		
		ArrayList<Artista> repertorio = new ArrayList<Artista>();
		repertorio.addAll(recital.getArtistasContratables());
		repertorio.addAll(recital.getArtistasBase());
		
		System.out.println("Listado de " + ANSI_CYAN + "artistas" + ANSI_RESET + " contratados\n\n");
		
		for(Artista artista : repertorio) {
			
			ArrayList<Contratacion> contratosArtista = recital.getContratosDeArtista(artista);
			if(!contratosArtista.isEmpty()) {
				System.out.println(ANSI_CYAN + artista.getNombre() + ANSI_GREEN + "\nCosto: " 
			+ artista.getCosto() + ANSI_RESET + "\nRoles: " + ANSI_RED + artista.getRoles() + ANSI_RESET + "\nContrataciones para este artista: ");
				for(Contratacion c : contratosArtista) {
					System.out.println(ANSI_PURPLE + c.getCancion().getTitulo() + ANSI_RESET + " - Rol: " + ANSI_RED+ c.getRol() + ANSI_RESET);
				}
				System.out.println();
			}
		}
	}
	
	///punto 7
	public void listarCanciones(Scanner scanner) {
		
		int i = 1;
		double costoTotal = 0, costoCancion;
		
		for(Cancion cancion : recital.getCanciones()) {
			costoCancion = recital.obtenerCostoContratacionesCancion(cancion);
			System.out.println(ANSI_YELLOW + i + ". " + ANSI_PURPLE + cancion.getTitulo() + ANSI_GREEN + " - Costo: $" + costoCancion + ANSI_RESET);
			i++;
			costoTotal += costoCancion;
			if(cancion.getRolesRequeridos().isEmpty()) {
				System.out.println(ANSI_CYAN + "No quedan roles por cubrir !\n" + ANSI_RESET);
			}
			else {
				System.out.println("Roles faltantes por cubrir:\n" + ANSI_RED + cancion.getRolesRequeridos() + ANSI_RESET + "\n");		
			}
		}
		System.out.println("Costo total del recital: " + ANSI_GREEN +costoTotal + ANSI_RESET);
	}
	
	public void prolog(Scanner scanner) {
		try {
	        // Inicio el Motor con la PRIMER CONSULTA
	        String initCmd = "write('Conexión Prolog exitosa.')"; 

	        Query q = new Query(initCmd);

	        if (!q.hasSolution()) {
	            System.err.println("Error al inicializar Prolog.");
	            return;
	        }

	        System.out.println("Motor Prolog inicializado correctamente.");
	        q.close();

	    } catch (JPLException e) {
	        System.err.println("Error de JPL: Asegúrate de que jpl.jar y SWI-Prolog están configurados.");
	        e.printStackTrace(); // Extra de porque salio mal
	        return;
	    } catch (UnsatisfiedLinkError ule) {
	        System.err.println("ERROR DE ENLACE NATIVO: No se encontró la biblioteca de SWI-Prolog.");
	        System.err.println("Asegúrate de que -Djava.library.path apunta a la carpeta 'bin' de SWI-Prolog.");
	        ule.printStackTrace();
	        return;
	    }
		// ruta directorio
		String directorioProyecto = System.getProperty("user.dir");

		// para las barras invertidas
		directorioProyecto = directorioProyecto.replace("\\", "/");

		// 3. construimos la ruta del archivo .pl
		String rutaArchivoProlog = directorioProyecto + "/jarvisllamadios.pl";

		System.out.println("Consultando archivo en: " + rutaArchivoProlog); // Para depurar

		String consultCmd = "consult('" + rutaArchivoProlog + "')";

		Query qConsult = new Query(consultCmd);

		if (qConsult.hasSolution()) {
		    System.out.println("Archivo de reglas cargado con éxito.");
		} else {
		    System.err.println("Error al cargar el archivo de reglas.");
		}

	    if (qConsult.hasSolution()) {
	        System.out.println("Archivo de reglas cargado con éxito: " + rutaArchivoProlog);
	    } else {
	        System.err.println("Error al cargar el archivo de reglas.");
	    }
		
		int contadorCancion = 1;
		int contadorRoles = 1;
		for (ArtistaBase artista : recital.getArtistasBase()) {
	        String hechoBase = String.format("assertz(artista_base('%s'))", artista.getNombre());
	        
	        // Ejecutar la aserción
	        if (new Query(hechoBase).hasSolution()) {
	            System.out.printf("Asertado: artista_base('%s').\n", artista.getNombre());
	        } else {
	            System.err.printf("Error al asertar artista_base('%s').\n", artista.getNombre());
	        }
	        for(String rol: artista.getRoles()) {
	        	String hechoCubre = String.format("assertz(artista_cubre('%s', '%s'))", artista.getNombre(), rol);
	        	if(new Query(hechoCubre).hasSolution()) {
		            System.out.printf("Asertado: artista_cubre('%s', '%s').\n", artista.getNombre(),rol);
		        } else {
		            System.err.printf("Error al asertar artista_cubre('%s', '%s').\n", artista.getNombre(),rol);
	        	}
	        }
	    }
		
		for (ArtistaContratable candidato : recital.getArtistasContratables()) {
	        // 1. Hecho: artista_candidato(Nombre)
	        String hechoBase = String.format("assertz(artista_candidato('%s'))", candidato.getNombre());
	        new Query(hechoBase).hasSolution();

	        // 2. Hecho: max_canciones_candidato(Nombre, MaxCanciones)
	        String hechoMax = String.format("assertz(max_canciones_candidato('%s', %d))", 
	                                        candidato.getNombre(), 
	                                        candidato.getMaxCanciones());
	        new Query(hechoMax).hasSolution();
	        
	        System.out.printf("Asertado: candidato %s con max_canciones %d.\n", candidato.getNombre(), candidato.getMaxCanciones());
	    }
		
		for (Cancion cancion : recital.getCanciones()) {
	        
	        // 1. Hecho: cancion(ID, Titulo)
			String cancionSanitizado = cancion.getTitulo().replace("'", "''");
			String hechoCancion = String.format("assertz(cancion(%d, '%s'))", 
	                                            contadorCancion, 
	                                            cancionSanitizado);
	        new Query(hechoCancion).hasSolution();
	        System.out.printf("Asertado: cancion %s con id %d.\n", cancion.getTitulo(), contadorCancion);
	        
	        // 2. Contrataciones Previas
	        for(Contratacion contrato : recital.getContratosDeCancion(cancion)) {
	        	String hechoAsignacion = String.format("assertz(artista_ya_contratado('%s', %d))", 
                        contrato.getArtista().getNombre(),
	        			contadorCancion);
	        	new Query(hechoAsignacion).hasSolution();
	        	System.out.printf("Asertado: artista %s en cancion %s.\n", contrato.getArtista().getNombre(), cancion.getTitulo());
	        }
	        
	        // 3. Iterar sobre el Map<Rol, Cantidad>
	        for (Map.Entry<String, Integer>  entry : cancion.getRolesRequeridos().entrySet()) {
	            String rol = entry.getKey();
	            int cantidadApariciones = entry.getValue();
	            String rolSanitizado = rol.replace("'", "''");
	            //System.out.printf("debugging: rol %s cancion %d\n", rol, contadorCancion);
	            
	            while(cantidadApariciones != 0) {
		            String hechoRol = String.format("assertz(rol_requerido(%d, %d, '%s'))", 
		                                            contadorCancion, 
		                                            contadorRoles, 
		                                            rolSanitizado);
		            new Query(hechoRol).hasSolution();
		            System.out.printf("Asertado Rol: cancion %s necesita %s %d vez/veces\n", 
		                              cancion.getTitulo(), rol, cantidadApariciones);
		            cantidadApariciones--;
		            contadorRoles++;
	            }
	        }
	        contadorCancion++;
	    }
		
		String consultaProlog = "entrenamientos_minimos(X, _, _)"; 
	    Query q = new Query(consultaProlog);
	    System.out.println("--- Resultados para X en 'entrenamientos_minimos(X, _, _)' ---");

	    if (q.hasSolution()) {
	    	
	        //guardo la solucion    
	    	Map<String, Term> solucion = q.oneSolution();
	        
	    	// 1. Extraigo el valor de la variable 'X'
	        Term resultadoX = solucion.get("X");
	            
	        // 2. Imprimo el resultado
	        System.out.println("Solución X encontrada: " + resultadoX.toString());
	        }
	    else {
	       	System.out.println("No se encontraron soluciones para la consulta.");
	    }
	    q.close();
		
	}

	public void salir(Scanner scanner) {
		this.estaCorriendo = false;
		System.out.println("\nGuardando estado antes de salir...");
		//ManejadorSalida manejador = new ManejadorSalida();
		//manejador.guardarEstado(this.recital);
	}
}
