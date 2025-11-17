package com.grupobeta.ArmadoDeRecital;

import java.util.ArrayList;

public class ListarCancionesComando implements Comando{
	
	Recital recital = null;
	
	public ListarCancionesComando(Recital recital) {
		this.recital = recital;
	}
	
	//punto 7
	@Override
	public void ejecutar() {
		int i = 1;
		double costoCancion;
		
		for(Cancion cancion : recital.getCanciones()) {
			ArrayList<Contratacion> contratos = new ArrayList<Contratacion>();
			costoCancion = recital.obtenerCostoContratacionesYContratosCancion(cancion, contratos);
			System.out.println(Menu.ANSI_YELLOW + i + ". " + Menu.ANSI_PURPLE + cancion.getTitulo() + Menu.ANSI_GREEN + " - Costo: $" + costoCancion + Menu.ANSI_RESET);
			i++;
			for(Contratacion contrato : contratos) {
				System.out.println(Menu.ANSI_CYAN + contrato.getArtista().getNombre()+ Menu.ANSI_RESET + " - " + Menu.ANSI_RED + contrato.getRol() + Menu.ANSI_RESET);
			}
			if(cancion.getRolesRequeridos().isEmpty()) {
				System.out.println(Menu.ANSI_GREEN + "No quedan roles por cubrir !\n" + Menu.ANSI_RESET);
			}
			else {
				System.out.println("Roles faltantes por cubrir:\n" + Menu.ANSI_RED + cancion.getRolesRequeridos() + Menu.ANSI_RESET + "\n");		
			}
		}
		System.out.println(Menu.ANSI_YELLOW + "Costo total del recital: " + Menu.ANSI_GREEN + recital.getCostoTotal() + Menu.ANSI_RESET);		
	}
}
