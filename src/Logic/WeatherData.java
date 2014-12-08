package Logic;

import java.sql.Date;
import java.util.ArrayList;

import Shared.Forecast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Shared.Note;
import Shared.TCPClient;

public class WeatherData {
	
	TCPClient TcpClient = new TCPClient();
	private ArrayList<Forecast> forecastList = new ArrayList();
	
	public void getWeatherData() throws Exception{
		String inFromServer = TcpClient.getWeatherData();
		
		// get 'forecastList' ArrayList from server
		java.lang.reflect.Type listType = new TypeToken<ArrayList<Forecast>>() {
		        }.getType();
		forecastList = new Gson().fromJson(inFromServer, listType);
		
		for (int i = 0; i < forecastList.size(); i++){
			System.out.println("celsius: " + forecastList.get(i).getCelsius());
			System.out.println("description: " + forecastList.get(i).getDesc());
		}
	}
	
	public String getTemperature(int fromToday){
		// return temperature
		String temperature = forecastList.get(fromToday).getCelsius();
		return temperature;
	}
	
	public String getDescription(int fromToday){
		// return description
		String desc = forecastList.get(fromToday).getDesc();
		return desc;
	}

}
