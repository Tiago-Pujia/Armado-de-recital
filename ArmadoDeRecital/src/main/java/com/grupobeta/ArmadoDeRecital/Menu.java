package com.grupobeta.ArmadoDeRecital;

public class Menu {
	
	public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
	
	public void mostrarMenu() {
		System.out.println("=============================" + ANSI_GREEN + "ARMADO DE RECITAL" + ANSI_RESET + "============================");
		System.out.println("|| " + "\t\t\t\t\t\t\t\t\t" + "||");
		System.out.println("|| " + "Seleccione una opcion:\t\t\t\t\t\t" + "||");
		System.out.println("|| " + "\t\t\t\t\t\t\t\t\t" + "||");
		System.out.println("|| " + ANSI_CYAN + "1." + ANSI_RESET + "¿Qué roles me faltan para tocar una canción X del recital?\t\t" + "||");
		System.out.println("|| " + ANSI_CYAN + "2." + ANSI_RESET + "¿Qué roles me faltan para tocar todas las canciones?\t\t" + "||");
		System.out.println("|| " + ANSI_CYAN + "3." + ANSI_RESET + "Contratar artistas para una canción X del recital\t\t\t" + "||");
		System.out.println("|| " + ANSI_CYAN + "4." + ANSI_RESET + "Contratar artistas para todas las canciones a la vez\t\t" + "||");
		System.out.println("|| " + ANSI_CYAN + "5." + ANSI_RESET + "Entrenar artista\t\t\t\t\t\t\t" + "||");
		System.out.println("|| " + ANSI_CYAN + "6." + ANSI_RESET + "Listar artistas contratados, su información relevante y su costo\t" + "||");
		System.out.println("|| " + ANSI_CYAN + "7." + ANSI_RESET + "Listar canciones con su estado\t\t\t\t\t" + "||");
		System.out.println("|| " + ANSI_CYAN + "8." + ANSI_RESET + "[PROLOG]\t\t\t\t\t\t\t\t" + "||");
		System.out.println("|| " + ANSI_CYAN + "9." + ANSI_RESET + "Salir\t\t\t\t\t\t\t\t" + "||");
		System.out.println("|| " + "\t\t\t\t\t\t\t\t\t" + "||");
		System.out.println("==========================================================================");
	}
	
	// faltan los métodos para manejar las opciones, aunque cómo lo hacemos sin spamear ifs o un switch ??
}
