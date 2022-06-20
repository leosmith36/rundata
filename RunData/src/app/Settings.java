package app;

import java.io.Serializable;

public class Settings implements Serializable{

	private static final long serialVersionUID = 1L;
	private String tmz;
	private String unit;

	public Settings(String unit, String tmz) {
		this.tmz = tmz;
		this.unit = unit;
	}

	public String getUnit() {
		return unit;
	}
	
	public String getTMZ() {
		return tmz;
	}
	
}
