package com.grupobeta.ArmadoDeRecital;

import java.util.Scanner;
import java.util.ArrayList;

public class QuitarUnArtistaComando implements Comando {
	
	private Scanner scanner;
	private Recital recital;
	
	public QuitarUnArtistaComando(Recital recital, Scanner scanner) {
		this.recital = recital;
		this.scanner = scanner;
	}

	@Override
	public void ejecutar() {
		
		System.out.printf("\nIngrese el nombre del artista: ");
		scanner.nextLine();
		String nombre = scanner.nextLine().trim().toLowerCase();
		
		try {
			Artista artista = recital.buscarArtistaAll(nombre);
			ArrayList<Contratacion> contratosDeArtista = new ArrayList<Contratacion>(recital.getContratosDeArtista(artista));
			if(contratosDeArtista.isEmpty()) {
				throw new ArtistaSinContratosException("El artista no tiene contratos vigentes");
			}
			
			for(Contratacion contrato : contratosDeArtista) {
				recital.removerContratacion(contrato);
				System.out.println("Se removió al artista " + artista.getNombre() + " de la canción " + Menu.ANSI_PURPLE +
						contrato.getCancion().getTitulo() + Menu.ANSI_RESET + " y del rol " + Menu.ANSI_RED + contrato.getRol() + Menu.ANSI_RESET);
			}
			
		}catch(ArtistaNoEncontradoException e) {
			System.out.println(e.getMessage());
		}catch(ArtistaSinContratosException e) {
			System.out.println(e.getMessage());
		}
		
	}

}
