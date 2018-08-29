package it.ingsw.address.model;


public class Deposito {
	private final static int MAX = 50;
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
	
	public static int getMax() {
		return MAX;
	}
}
