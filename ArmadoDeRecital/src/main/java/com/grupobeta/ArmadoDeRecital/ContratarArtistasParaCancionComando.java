package com.grupobeta.ArmadoDeRecital;

import java.util.List;
import java.util.Scanner;

public class ContratarArtistasParaCancionComando implements Comando{
	
	Scanner scanner = null;
	Recital recital = null;
	
	public ContratarArtistasParaCancionComando(Scanner scanner, Recital recital) {
		this.scanner = scanner;
		this.recital = recital;
	}
	
	//punto 3
	@Override
	public void ejecutar() {
		
		System.out.printf("Ingrese el nombre de la canción: ");
		scanner.nextLine();
		String nombreCancion = scanner.nextLine().trim().toLowerCase();
		
		try {
			
			Cancion cancion = recital.buscarCancion(nombreCancion);
						
			List<Contratacion> contratosRealizados = recital.contratarArtistasParaCancion(cancion);
			if(contratosRealizados.isEmpty()) {
				System.out.println(Menu.ANSI_CYAN + "La cancion ya tiene todos sus roles cubiertos." + Menu.ANSI_RESET);
			}

			Menu.mostrarContratosRealizados(contratosRealizados);
			if(!cancion.getRolesRequeridos().isEmpty()) {
				System.out.println("No se pudieron cubrir todos los roles de la cancion " 
			+ Menu.ANSI_PURPLE + cancion.getTitulo() + Menu.ANSI_RESET +
			". Utilice la opción \"Entrenar artista\" en el menú principal"  );
			}
			
		}catch(RuntimeException e) {
			System.out.println(e.getMessage());
		}		
	}
}
