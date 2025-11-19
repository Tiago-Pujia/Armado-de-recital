package com.grupobeta.ArmadoDeRecital;

import java.util.Map;
import java.util.Scanner;

public class ConsultarRolesFaltantesParaCancionComando implements Comando{
	
	Scanner scanner = null;
	Recital recital = null;
	
	public ConsultarRolesFaltantesParaCancionComando(Scanner scanner, Recital recital) {
		this.scanner = scanner;
		this.recital = recital;
	}
	
	//punto 1
	@Override
	public void ejecutar() {
		System.out.printf("Ingrese el nombre de la canción: ");
		scanner.nextLine();
		String nombreCancion = scanner.nextLine().trim().toLowerCase();
		
		try {
			Cancion cancion = recital.buscarCancion(nombreCancion);
			Map<String, Integer> rolesFaltantes = recital.consultarRolesFaltantesParaCancion(cancion);
			
			if(rolesFaltantes == null) {
				System.out.println("El nombre ingresado" + Menu.ANSI_RED + " no coincide " + Menu.ANSI_RESET + "con ninguna canción registrada para el recital. Intente nuevamente.");
				return;
			}
			if(rolesFaltantes.isEmpty()) {
				System.out.println("La canción ingresada tiene todos sus roles" + Menu.ANSI_GREEN + " cubiertos" + Menu.ANSI_RESET);
				return;
			}
			System.out.println(Menu.ANSI_PURPLE + nombreCancion + Menu.ANSI_RESET + "\nRoles faltantes por cubrir:\n" + Menu.ANSI_RED + rolesFaltantes + Menu.ANSI_RESET + "\n");	
		}catch(RuntimeException e) {
			System.out.println("El nombre ingresado no coincide con el de ninguna canción. Intente nuevamente");
		}
			
	}
}
