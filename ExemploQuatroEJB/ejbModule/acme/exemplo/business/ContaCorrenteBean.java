package acme.exemplo.business;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import acme.exemplo.model.ContaCorrente;
import acme.exemplo.util.ContaCorrenteException;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class ContaCorrenteBean implements ContaCorrenteLocal{

	//Capturando inst�ncia do UserTransaction
	@Resource
	private UserTransaction ut;
	
    @PersistenceContext(unitName="ExQuatro")
    private EntityManager em;

    private static final BigDecimal ZERO = new BigDecimal(0);
   
    public void cadastrarContaCorrente(String numero, String titular,
			BigDecimal saldo){
    	
    	try{
	    		ut.begin(); //Inicia a transa��o
	        	if(empty(numero) || empty(titular)){
	        		return;
	        	}
	        	
	        	/* se saldo nulo, valor = ZERO */
	        	saldo = saldo != null ? saldo : ZERO;
	        	
	        	/* se saldo menor que zero, n�o cadastra */
	        	if(saldo.compareTo(ZERO) < 0){
	        		return;
	        	}
	        	
	        	ContaCorrente conta = new ContaCorrente(generateID(), numero, titular, saldo);
	        	em.persist(conta);
	    		ut.commit(); //Finaliza a transa��o
    		}catch(Exception e){
    		  	e.printStackTrace();
    		  	try {
					ut.rollback(); //Desfaz opera��o caso algo d� errado
				} catch (IllegalStateException e1) {
					e1.printStackTrace();
				} catch (SecurityException e1) {
					e1.printStackTrace();
				} catch (SystemException e1) {
					e1.printStackTrace();
				}
				
    		}	
	}   
    

	public ContaCorrente consultarContaCorrente(String numero) {
		try {
			TypedQuery<ContaCorrente> query = 
				em.createQuery("FROM ContaCorrente c WHERE c.numero = :numero", ContaCorrente.class);
			query.setParameter("numero", numero);
			return query.getSingleResult();
			
		} catch (NoResultException e) {
			return null;
		}
	}

	
	public void transferirValorEntreContas(String contaOrigem,
			String contaDestino, BigDecimal valor) throws ContaCorrenteException{

		try{
				ut.begin(); //Inicia a transa��o
				ContaCorrente origem = null;
				
				/* Se o valor � inv�lido (nulo ou menor que zero) */
				if(valor == null || valor.compareTo(ZERO) < 0){
					ut.rollback();
					throw new ContaCorrenteException("Valor inv�lido para a transfer�ncia!");
				}
				
				/* valida conta origem */	
				if(!empty(contaOrigem)){
					
					origem = consultarContaCorrente(contaOrigem);
					
					if(origem == null){
						ut.rollback();
						throw new ContaCorrenteException("Conta Origem Inv�lida!");
					}
					
					origem.setSaldo(origem.getSaldo().subtract(valor));
					em.merge(origem);
					
				}
				else{
					ut.rollback();
					throw new ContaCorrenteException("Conta Origem Inv�lida!");
				}
					
				
				/* valida conta destino */
				if(!empty(contaDestino)){
					
					ContaCorrente destino = consultarContaCorrente(contaDestino);
					
					if(destino ==  null){
						ut.rollback();
						throw new ContaCorrenteException("Conta Destino Inv�lida!");
					}
					
					destino.setSaldo(destino.getSaldo().add(valor));
					em.merge(destino);
				}
				else{
					ut.rollback();
					throw new ContaCorrenteException("Conta Destino Inv�lida!");
				}
				
				/* Esta valida��o deveria ser feita antes mesmo de se realizar a persist�ncia das
				 * altera��es na origem, mas est� sendo feita aqui apenas para demonstra��o da
				 * transa��o. */
				
				/* Se o saldo da origem ficou negativo, desfaz toda a opera��o */
				if(origem.getSaldo().compareTo(ZERO) < 0){
					ut.rollback();
					throw new ContaCorrenteException("Saldo insuficiente para a transa��o!");
				}
				
				ut.commit(); //Finaliza a transa��o
		} catch(ContaCorrenteException e){
		  	e.printStackTrace();
		  	try {
				ut.rollback(); //Desfaz opera��o caso algo d� errado
			} catch (IllegalStateException e1) {
				e1.printStackTrace();
			} catch (SecurityException e1) {
				e1.printStackTrace();
			} catch (SystemException e1) {
				e1.printStackTrace();
			}
			throw e;
			
		} catch(Exception e){
		  	e.printStackTrace();
		  	try {
				ut.rollback(); //Desfaz opera��o caso algo d� errado
			} catch (IllegalStateException e1) {
				e1.printStackTrace();
			} catch (SecurityException e1) {
				e1.printStackTrace();
			} catch (SystemException e1) {
				e1.printStackTrace();
			}
			throw new ContaCorrenteException("Falha na transa��o de transfer�ncia!");
		}		
		
		
				
	}
       
	public List<ContaCorrente> listarContasCorrentes() {
        TypedQuery<ContaCorrente> query = em.createQuery("FROM ContaCorrente c ORDER BY c.numero", ContaCorrente.class);
        return query.getResultList();
    }
	
	
    private boolean empty(String value){
    	return value == null || "".equals(value);
    }
    
    private long generateID() {
		long random = super.hashCode();
		try {
			random = SecureRandom.getInstance("SHA1PRNG").nextInt(99999999);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Math.abs(random);
	}
}
