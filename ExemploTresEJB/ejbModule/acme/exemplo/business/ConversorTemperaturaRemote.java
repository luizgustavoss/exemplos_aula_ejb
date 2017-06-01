package acme.exemplo.business;

import java.math.BigDecimal;

import javax.ejb.Remote;

@Remote
public interface ConversorTemperaturaRemote {

	public BigDecimal celciusParaFahrenheit(BigDecimal celcius);
	public BigDecimal fahrenheitParaCelcius(BigDecimal fahrenheit);
}
