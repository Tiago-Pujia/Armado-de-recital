package com.grupobeta.ArmadoDeRecital;

public class CancionNoEncontradaException extends RuntimeException {
	
	private static final long serialVersionUID = 684937250038703258L;

	public CancionNoEncontradaException(String mensaje) {
		super(mensaje);
	}
}
