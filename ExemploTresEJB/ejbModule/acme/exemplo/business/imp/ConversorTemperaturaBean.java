package acme.exemplo.business.imp;

import java.math.BigDecimal;

import javax.ejb.Stateless;

import acme.exemplo.business.ConversorTemperaturaRemote;

@Stateless
public class ConversorTemperaturaBean implements ConversorTemperaturaRemote{

	private final BigDecimal ZERO = new BigDecimal("0");
	private final BigDecimal NOVE = new BigDecimal("9");
	private final BigDecimal CINCO = new BigDecimal("5");
	private final BigDecimal TRINTA_E_DOIS = new BigDecimal("32");	
	
	@Override
	public BigDecimal celciusParaFahrenheit(BigDecimal celcius) {
		if(celcius == null || ZERO.compareTo(celcius) == 0){
			return ZERO;
		}		
		return celcius.multiply(NOVE).divide(CINCO, 6, 
				BigDecimal.ROUND_HALF_UP).add(TRINTA_E_DOIS);
	}

	@Override
	public BigDecimal fahrenheitParaCelcius(BigDecimal fahrenheit) {
		if(fahrenheit == null || ZERO.compareTo(fahrenheit) == 0){
			return ZERO;
		}		
		return fahrenheit.min(TRINTA_E_DOIS)
		.multiply(CINCO).divide(NOVE, 6, BigDecimal.ROUND_HALF_UP);
	}
}
