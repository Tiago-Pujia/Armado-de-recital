package com.grupobeta.ArmadoDeRecital;


import java.util.Map;

import org.jpl7.Query;
import org.jpl7.JPLException;
import org.jpl7.Term;

public class PrologComando implements Comando{
	
	private Recital recital;
	
	public PrologComando(Recital recital) {
		this.recital = recital;
	}
	
	//punto 8
	@Override
	public void ejecutar() {
		try {
	        
			// Inicio el Motor con la PRIMER CONSULTA
	        String initCmd = "write('Conexión Prolog exitosa.')"; 
	        Query q = new Query(initCmd);
	        
	        if (!q.hasSolution()) {
	            System.err.println(Menu.ANSI_RED + "Error al inicializar Prolog." + Menu.ANSI_RESET);
	            return;
	        }
	        
	        System.out.println(Menu.ANSI_GREEN + "Motor Prolog inicializado correctamente." + Menu.ANSI_RESET);
	        q.close();
	        
	    } catch (JPLException e) {
	        System.err.println(Menu.ANSI_RED + "Error de JPL: " + Menu.ANSI_RESET + "Asegurate de que jpl.jar y SWI-Prolog están configurados.");
	        e.printStackTrace(); // Extra de porque salio mal
	        return;
	    } catch (UnsatisfiedLinkError ule) {
	        System.err.println(Menu.ANSI_RED + "ERROR DE ENLACE NATIVO: " + Menu.ANSI_RESET + "No se encontró la biblioteca de SWI-Prolog.");
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
		System.out.println("Consultando archivo en: " + Menu.ANSI_CYAN + rutaArchivoProlog + Menu.ANSI_RESET); // Para depurar
		String consultCmd = "consult('" + rutaArchivoProlog + "')";
		Query qConsult = new Query(consultCmd);

	    if (qConsult.hasSolution()) {
	        System.out.println("Archivo de reglas cargado con éxito: " + Menu.ANSI_GREEN + rutaArchivoProlog + Menu.ANSI_RESET);
	    } else {
	        System.err.println(Menu.ANSI_RED + "Error al cargar el archivo de reglas." + Menu.ANSI_RED);
	    }
	    
	    //Limpieza de hechos previos para funcionar con cambios.
	    String limpiezaConsulta = "limpiar_base_conocimiento";
	    new Query(limpiezaConsulta).hasSolution();
	    System.out.printf(Menu.ANSI_CYAN + "Hechos Limpiados\n\n" + Menu.ANSI_RESET);
	    
	    System.out.printf(Menu.ANSI_CYAN + "Declaracion de hechos:\n" + Menu.ANSI_RESET);

		int contadorCancion = 1;
		int contadorRoles = 1;
		for (ArtistaBase artista : recital.getArtistasBase()) {
	        String hechoBase = String.format("assertz(artista_base('%s'))", artista.getNombre());
	        // Ejecutar la aserción
	        if (new Query(hechoBase).hasSolution()) {
	            System.out.printf(Menu.ANSI_YELLOW + "Asertado: " + Menu.ANSI_RESET + "artista_base(" + Menu.ANSI_GREEN + "'%s'" + Menu.ANSI_RESET + ").\n", artista.getNombre());
	        } else {
	            System.err.printf(Menu.ANSI_RED +"Error al asertar artista_base" + Menu.ANSI_RESET + "(" + Menu.ANSI_GREEN + "'%s'" + Menu.ANSI_RESET + ").\n", artista.getNombre());
	        }

	        for(String rol: artista.getRoles()) {
	        	String hechoCubre = String.format("assertz(artista_cubre('%s', '%s'))", artista.getNombre(), rol);

	        	if(new Query(hechoCubre).hasSolution()) {
		            System.out.printf(Menu.ANSI_YELLOW + "Asertado: " + Menu.ANSI_RESET + "artista_cubre(" + Menu.ANSI_GREEN + "'%s', '%s'" + Menu.ANSI_RESET + ").\n", artista.getNombre(),rol);
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

	        System.out.printf(Menu.ANSI_YELLOW + "Asertado: " + Menu.ANSI_RESET + "candidato" + Menu.ANSI_GREEN + "'%s' " + Menu.ANSI_RESET + "con max_canciones " + Menu.ANSI_GREEN + "'%d'" + Menu.ANSI_RESET + ".\n", candidato.getNombre(), candidato.getMaxCanciones());
	    }

		for (Cancion cancion : recital.getCanciones()) {

	        // 1. Hecho: cancion(ID, Titulo)
			String cancionSanitizado = cancion.getTitulo().replace("'", "''");
			String hechoCancion = String.format("assertz(cancion(%d, '%s'))", 
	                                            contadorCancion, 
	                                            cancionSanitizado);
	        new Query(hechoCancion).hasSolution();
	        
	        System.out.printf(Menu.ANSI_YELLOW + "Asertado: " + Menu.ANSI_RESET + "cancion" + Menu.ANSI_GREEN + "'%s' " + Menu.ANSI_RESET + "con id " + Menu.ANSI_GREEN + "%d" + Menu.ANSI_RESET + ".\n", cancion.getTitulo(), contadorCancion);

	        // 2. Contrataciones Previas
	        for(Contratacion contrato : recital.getContratosDeCancion(cancion)) {
	        	String hechoAsignacion = String.format("assertz(artista_ya_contratado('%s', %d))", 
                        contrato.getArtista().getNombre(),
	        			contadorCancion);
	        	new Query(hechoAsignacion).hasSolution();
	        	
	        	System.out.printf(Menu.ANSI_YELLOW + "Asertado: " + Menu.ANSI_RESET + "artista"  + Menu.ANSI_GREEN + "'%s' " + Menu.ANSI_RESET + "en cancion " + Menu.ANSI_GREEN + "'%s' " + Menu.ANSI_RESET + ".\n", contrato.getArtista().getNombre(), cancion.getTitulo());
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

		            System.out.printf(Menu.ANSI_YELLOW + "Asertado: " + Menu.ANSI_RESET + "cancion"  + Menu.ANSI_GREEN + "'%s' " + Menu.ANSI_RESET + "necesita "  + Menu.ANSI_GREEN + "%s %d " + Menu.ANSI_RESET + "vez/veces\n", cancion.getTitulo(), rol, cantidadApariciones);
		            cantidadApariciones--;
		            contadorRoles++;
	            }
	        }
	        contadorCancion++;
	    }
		String consultaProlog = "entrenamientos_minimos(X, _, _)"; 
	    Query q = new Query(consultaProlog);
	    System.out.println(Menu.ANSI_CYAN +"\n--- Resultados para X en 'entrenamientos_minimos(X, _, _)' ---" + Menu.ANSI_RESET);

	    if (q.hasSolution()) {
	        //guardo la solucion    
	    	Map<String, Term> solucion = q.oneSolution();

	    	// 1. Extraigo el valor de la variable 'X'
	        Term resultadoX = solucion.get("X");

	        // 2. Imprimo el resultado
	        System.out.println(Menu.ANSI_GREEN + "Solución X encontrada: " + resultadoX.toString() + Menu.ANSI_RESET);
	        }
	    else {
	       	System.out.println(Menu.ANSI_RED + "No se encontraron soluciones para la consulta." + Menu.ANSI_RESET);
	    }
	    q.close();
		
	}
	
}
