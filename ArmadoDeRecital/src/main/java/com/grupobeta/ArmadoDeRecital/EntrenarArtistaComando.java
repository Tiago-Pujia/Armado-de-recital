package com.grupobeta.ArmadoDeRecital;

import java.util.Scanner;

public class EntrenarArtistaComando implements Comando{
	
	Scanner scanner = null;
	Recital recital = null;
	
	public EntrenarArtistaComando(Scanner scanner, Recital recital) {
		this.scanner = scanner;
		this.recital = recital;
	}
	
	//punto 5
	@Override
	public void ejecutar() {
		System.out.printf("Ingrese el nombre del artista: ");
		scanner.nextLine();
		String nombre = scanner.nextLine();
		ArtistaContratable artista = recital.buscarArtistaContratable(nombre);
		
		System.out.printf("Ingrese el rol que quieres darle: ");
		String rol = scanner.nextLine();
	
		recital.entrenarArtista(artista, rol);
		System.out.printf("El Artista " + Menu.ANSI_CYAN + nombre + Menu.ANSI_RESET + " ha sido entrenado");
	}
}
