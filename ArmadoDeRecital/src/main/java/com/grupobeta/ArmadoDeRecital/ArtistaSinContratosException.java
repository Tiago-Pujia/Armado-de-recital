package com.grupobeta.ArmadoDeRecital;

public class ArtistaSinContratosException extends RuntimeException {
	
	private static final long serialVersionUID = 684937250038703258L;

	public ArtistaSinContratosException(String mensaje) {
		super(mensaje);
	}
}