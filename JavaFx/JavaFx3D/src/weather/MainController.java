package weather;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class MainController implements Initializable{

	private WeatherSupplier weatherSupplier;
	private WeatherService service = new WeatherService();

	@FXML TextField fieldSearch;
	@FXML Label searchInfo;

	public MainController(WeatherSupplier supplier) {
		this.weatherSupplier = supplier;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	}

	@FXML
	public void onSearch(){
		service.cityName = searchInfo.getText();
		service.restart();
		/*
		Weather weather = weatherSupplier.getWeatherByName("");
		searchInfo.setText(weather.getCitiName() + "  " + weather.getTemp());
		*/
	}

	private class WeatherService extends Service<Weather> {

		private String cityName;

		@Override
		protected Task<Weather> createTask() {
			// TODO Auto-generated method stub

			return new Task<Weather>() {

				@Override
				protected Weather call() throws Exception {
					// TODO Auto-generated method stub
					return weatherSupplier.getWeatherByName(cityName);
				}

				@Override
				protected void failed() {
					// TODO Auto-generated method stub
					super.failed();
					Throwable error = getException();
					searchInfo.setText("Error : " + error);
				}

				@Override
				protected void succeeded() {
					// TODO Auto-generated method stub
					super.succeeded();
					Weather weather = getValue();
					searchInfo.setText(weather.getCitiName() + "  " + weather.getTemp());
				}

			};
		}

	}

}
