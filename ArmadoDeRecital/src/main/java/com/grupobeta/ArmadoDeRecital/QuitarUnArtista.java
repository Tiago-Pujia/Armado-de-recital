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
			
			ArtistaContratable artista = recital.buscarArtistaContratable(nombre);
			ArrayList<Contratacion> contratosDeArtista = new ArrayList<Contratacion>(recital.getContratosDeArtista(artista));
			
			for(Contratacion contrato : contratosDeArtista) {
				recital.removerContratacion(contrato);
			}
			
			
		}catch(ArtistaNoEncontradoException e) {
			System.out.println(e.getMessage());
		}
		
	}

}
