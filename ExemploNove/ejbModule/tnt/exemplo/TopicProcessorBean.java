package tnt.exemplo;

import javax.ejb.Stateless;

@Stateless
public class TopicProcessorBean {

	
	public void processarMensagen(String mensagem){
		
		System.out.println("Message received: " + mensagem);
	}
}
