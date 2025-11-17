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
	    
	    //Limpieza de hechos previos para funcionar con cambios.
	    String limpiezaConsulta = "limpiar_base_conocimiento";
	    new Query(limpiezaConsulta).hasSolution();
	    System.out.printf("Hechos Limpiados\n");
	    
	    

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

		            System.out.printf("Asertado Rol: cancion %s necesita %s %d vez/veces\n", cancion.getTitulo(), rol, cantidadApariciones);
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
	
}
