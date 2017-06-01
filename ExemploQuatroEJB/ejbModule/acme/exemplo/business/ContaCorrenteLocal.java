package acme.exemplo.business;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Local;

import acme.exemplo.model.ContaCorrente;
import acme.exemplo.util.ContaCorrenteException;

@Local
public interface ContaCorrenteLocal {

	public void cadastrarContaCorrente(String numero, String titular,
			BigDecimal saldo) throws ContaCorrenteException;

	public ContaCorrente consultarContaCorrente(String numero);

	public void transferirValorEntreContas(String contaOrigem,
			String contaDestino, BigDecimal valor)
			throws ContaCorrenteException;

	public List<ContaCorrente> listarContasCorrentes();
}
