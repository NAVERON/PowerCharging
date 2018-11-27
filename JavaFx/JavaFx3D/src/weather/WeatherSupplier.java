package weather;


public interface WeatherSupplier{

	public Weather getWeatherByName(String cityName);
}
