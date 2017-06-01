package acme.exemplo.presentation;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import acme.exemplo.business.ContaCorrenteLocal;
import acme.exemplo.model.ContaCorrente;
import acme.exemplo.util.ContaCorrenteException;

@ManagedBean(name="ccorrente")
@SessionScoped
public class ContaCorrenteMB implements java.io.Serializable {

	@EJB
    private ContaCorrenteLocal contaCorrenteBean;
	
	private String numero;
	private String titular;
	private BigDecimal saldo;
	
	private String contaOrigem;
	private String contaDestino;
	private BigDecimal valorTransferencia;
	
	private List<ContaCorrente> contasCorrentes;

    
    @PostConstruct
    public void inicializa() {
    	this.contasCorrentes = contaCorrenteBean.listarContasCorrentes();
    }

    
    public void cadastrarContaCorrente(){
    	
    	try {
			contaCorrenteBean.cadastrarContaCorrente(numero, titular, saldo);
			
			this.contasCorrentes = contaCorrenteBean.listarContasCorrentes();
			
		} catch (ContaCorrenteException e) {
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(e.getMessage()));
		}
    }

    
    public void transferir(){
    	
    	try{
    		contaCorrenteBean.transferirValorEntreContas(contaOrigem, contaDestino, valorTransferencia);
    		
    		this.contasCorrentes = contaCorrenteBean.listarContasCorrentes();
    		
    	} catch (ContaCorrenteException e) {
    		FacesContext.getCurrentInstance().addMessage("", new FacesMessage(e.getMessage()));
		}    	
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

	public List<ContaCorrente> getContasCorrentes() {
		return contasCorrentes;
	}

	public void setContasCorrentes(List<ContaCorrente> contasCorrentes) {
		this.contasCorrentes = contasCorrentes;
	}


	public String getContaOrigem() {
		return contaOrigem;
	}


	public void setContaOrigem(String contaOrigem) {
		this.contaOrigem = contaOrigem;
	}


	public String getContaDestino() {
		return contaDestino;
	}


	public void setContaDestino(String contaDestino) {
		this.contaDestino = contaDestino;
	}


	public BigDecimal getValorTransferencia() {
		return valorTransferencia;
	}


	public void setValorTransferencia(BigDecimal valorTransferencia) {
		this.valorTransferencia = valorTransferencia;
	}   
    
	
}
