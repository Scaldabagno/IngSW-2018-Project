package it.ingsw.address.model;


public class Deposito {
	private final int MAX = 50;
	private int[] posto;
	private Deposito() {
		setPosto(new int[MAX]);
	}
	public int[] getPosto() {
		return posto;
	}
	public void setPosto(int[] posto) {
		this.posto = posto;
	}
}
