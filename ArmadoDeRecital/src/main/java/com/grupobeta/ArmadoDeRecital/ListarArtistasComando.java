package com.grupobeta.ArmadoDeRecital;

import java.util.ArrayList;
import java.util.List;

public class ListarArtistasComando implements Comando{
	
	Recital recital = null;
	
	public ListarArtistasComando(Recital recital) {
		this.recital = recital;
	}
	
	//punto 6
	@Override
	public void ejecutar() {
		ArrayList<Artista> repertorio = new ArrayList<Artista>();
		repertorio.addAll(recital.getArtistasContratables());
		repertorio.addAll(recital.getArtistasBase());
		
		System.out.println("Listado de " + Menu.ANSI_CYAN + "artistas" + Menu.ANSI_RESET + " contratados\n\n");
		
		for(Artista artista : repertorio) {
			
			List<Contratacion> contratosArtista = recital.getContratosDeArtista(artista);
			if(!contratosArtista.isEmpty()) {
				System.out.println(Menu.ANSI_CYAN + artista.getNombre() + Menu.ANSI_GREEN + "\nCosto: " 
			+ artista.getCosto() + Menu.ANSI_RESET + "\nRoles: " + Menu.ANSI_RED + artista.getRoles() + Menu.ANSI_RESET + "\nContrataciones para este artista: ");
				for(Contratacion c : contratosArtista) {
					System.out.println(Menu.ANSI_PURPLE + c.getCancion().getTitulo() + Menu.ANSI_RESET + " - Rol: " + Menu.ANSI_RED+ c.getRol() + Menu.ANSI_RESET);
				}
				System.out.println();
			}
		}
	}
}
