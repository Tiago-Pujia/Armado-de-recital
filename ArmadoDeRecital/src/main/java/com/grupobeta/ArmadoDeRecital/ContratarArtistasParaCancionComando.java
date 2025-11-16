package com.grupobeta.ArmadoDeRecital;

import java.util.ArrayList;
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
		String nombreCancion = scanner.nextLine();
		Cancion cancion = recital.buscarCancion(nombreCancion);
		
		if(cancion==null) {
			System.out.println("El nombre ingresado no corresponde con ninguna canción del recital");
			return;
		}
		
		ArrayList<Contratacion> contratosRealizados = recital.contratarArtistasParaCancion(cancion);
		if(contratosRealizados.isEmpty()) {
			System.out.println(Menu.ANSI_CYAN + "La cancion ya tiene todos sus roles cubiertos." + Menu.ANSI_RESET);
		}

		mostrarContratosRealizados(contratosRealizados);
		if(!cancion.getRolesRequeridos().isEmpty()) {
			System.out.println("No se pudieron cubrir todos los roles de la cancion " + Menu.ANSI_PURPLE + cancion.getTitulo() + Menu.ANSI_RESET + " ¿Deseas entrenar a un artista?"  );
		}
	}
	
	public static void mostrarContratosRealizados(ArrayList<Contratacion> contratosRealizados) {
		for(Contratacion contrato : contratosRealizados) {
			System.out.println("Se contrató al artista " + Menu.ANSI_CYAN + contrato.getArtista().getNombre() +
					Menu.ANSI_RESET + " para el rol: " + Menu.ANSI_RED + contrato.getRol() + Menu.ANSI_RESET +
					" en la canción: " + Menu.ANSI_PURPLE + contrato.getCancion().getTitulo() + Menu.ANSI_RESET);
		}
	}
}
