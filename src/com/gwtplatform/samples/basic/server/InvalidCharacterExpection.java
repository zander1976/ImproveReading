package com.gwtplatform.samples.basic.server;

public class InvalidCharacterExpection extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5591633263313723749L;

	public InvalidCharacterExpection() {
		super();
	}

	public InvalidCharacterExpection(String string) {
		super(string);
	}
}
