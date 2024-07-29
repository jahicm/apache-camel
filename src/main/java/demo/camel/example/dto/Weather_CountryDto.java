package demo.camel.example.dto;

public class Weather_CountryDto {

	static int counter = 1;
	private int id = counter++;

	private String city;
	private String temp;
	private String country;
	private String receivedTime;

	public static int getCounter() {
		return counter;
	}

	public static void setCounter(int counter) {
		Weather_CountryDto.counter = counter;
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getReceivedTime() {
		return receivedTime;
	}

	public void setReceivedTime(String receivedTime) {
		this.receivedTime = receivedTime;
	}
}
