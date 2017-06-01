package acme.exemplo.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ccorrente")
public class ContaCorrente implements java.io.Serializable
{
    private static final long serialVersionUID = 1L;

    @Id 
    private Long id;
    private String numero;
    private String titular;
    private BigDecimal saldo;

    public ContaCorrente() {}

    public ContaCorrente(Long id, String numero, String titular, BigDecimal saldo) {
        this.id = id;
        this.numero = numero;
        this.titular = titular;
        this.saldo = saldo;
    }
    
    public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
        return id;
    }

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	
}

