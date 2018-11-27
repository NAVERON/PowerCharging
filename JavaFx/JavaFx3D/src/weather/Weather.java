package weather;

public final class Weather {

	private String citiname;
	private double temp;
	public Weather(String citiName, double temp) {
		this.citiname = citiName;
		this.temp = temp;
	}
	public String getCitiName() {
		return citiname;
	}
	public void setCitiName(String citiName) {
		this.citiname = citiName;
	}
	public double getTemp() {
		return temp;
	}
	public void setTemp(double temp) {
		this.temp = temp;
	}



}
