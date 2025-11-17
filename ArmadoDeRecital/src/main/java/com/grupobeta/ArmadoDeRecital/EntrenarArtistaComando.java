package com.grupobeta.ArmadoDeRecital;

import java.util.Scanner;

public class EntrenarArtistaComando implements Comando{
	
	private Scanner scanner = null;
	private Recital recital = null;
	private Menu menu = null;
	
	public EntrenarArtistaComando(Scanner scanner, Recital recital, Menu menu) {
		this.scanner = scanner;
		this.recital = recital;
		this.menu = menu;
	}
	
	//punto 5
	@Override
	public void ejecutar() {
		menu.mostrarArtistasContratables();
		
		System.out.printf("\nIngrese el nombre del artista: ");
		scanner.nextLine();
		String nombre = scanner.nextLine();
		
		try {
			ArtistaContratable artista = recital.buscarArtistaContratable(nombre);
			System.out.printf("Ingrese el rol que quieres darle: ");
			String rol = scanner.nextLine();
			
			double costoAnt = artista.getCosto();
			artista.entrenar(rol);
			System.out.println("El Artista " + Menu.ANSI_CYAN + nombre + Menu.ANSI_RESET +
				" ha sido entrenado.Su costo se ha incrementado en un" + Menu.ANSI_RED +
				" %50" + Menu.ANSI_RESET + " (" + Menu.ANSI_GREEN + costoAnt + Menu.ANSI_YELLOW +" -> " + Menu.ANSI_RED + artista.getCosto() + Menu.ANSI_RESET +")");
			
		}catch(ArtistaNoEncontradoException e) {
			System.out.println(e.getMessage());
		}
		
		
	}
}
