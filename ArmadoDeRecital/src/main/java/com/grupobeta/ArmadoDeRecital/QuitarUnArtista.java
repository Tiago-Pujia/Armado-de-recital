package com.grupobeta.ArmadoDeRecital;

import java.util.Scanner;
import java.util.ArrayList;

public class QuitarUnArtista implements Comando {
	
	private Scanner scanner;
	private Recital recital;
	
	public QuitarUnArtista(Recital recital, Scanner scanner) {
		this.recital = recital;
		this.scanner = scanner;
	}

	@Override
	public void ejecutar() {
		
		System.out.printf("\nIngrese el nombre del artista: ");
		scanner.nextLine();
		String nombre = scanner.nextLine();
		
		try {
			
			Artista artista = recital.buscarArtistaAll(nombre);
			ArrayList<Contratacion> contratosDeArtista = new ArrayList<Contratacion>(recital.getContratosDeArtista(artista));
			
			for(Contratacion contrato : contratosDeArtista) {
				recital.removerContratacion(contrato);
				System.out.println("Se removió al artista de la canción " + Menu.ANSI_PURPLE +
						contrato.getCancion().getTitulo() + Menu.ANSI_RESET + " y del rol " + Menu.ANSI_RED + contrato.getRol() + Menu.ANSI_RESET);
			}
			
		}catch(ArtistaNoEncontradoException e) {
			System.out.println(e.getMessage());
		}
		
	}

}
