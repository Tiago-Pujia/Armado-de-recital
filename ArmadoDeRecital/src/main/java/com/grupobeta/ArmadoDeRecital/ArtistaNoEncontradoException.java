package com.grupobeta.ArmadoDeRecital;

public class ArtistaNoEncontradoException extends RuntimeException {
	private static final long serialVersionUID = -495059613251522648L;

	public ArtistaNoEncontradoException(String mensaje) {
		super(mensaje);
	}
}
