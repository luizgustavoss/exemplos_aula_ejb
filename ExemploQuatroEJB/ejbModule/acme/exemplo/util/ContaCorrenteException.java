package acme.exemplo.util;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
public class ContaCorrenteException extends Exception{

	private static final long serialVersionUID = 1L;

	public ContaCorrenteException(){
		super();
	}
	
	public ContaCorrenteException(String msg){
		super(msg);
	}
}
