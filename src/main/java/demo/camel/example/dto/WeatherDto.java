package demo.camel.example.dto;

import java.io.Serializable;

public class WeatherDto implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static int counter = 1;
    private int id = counter++;

    private String city;
    private String temp;
    private String unit;
    private String receivedTime;
    
    public static int getCounter() {
		return counter;
	}
	public static void setCounter(int counter) {
		WeatherDto.counter = counter;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getTemp() {
		return temp;
	}
	public void setTemp(String temp) {
		this.temp = temp;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getReceivedTime() {
		return receivedTime;
	}
	public void setReceivedTime(String receivedTime) {
		this.receivedTime = receivedTime;
	}
}
