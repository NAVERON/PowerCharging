package weather;

public class WeatherProvider implements WeatherSupplier{

	@Override
	public Weather getWeatherByName(String cityName) {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Weather("London", 14.5);
	}

}
