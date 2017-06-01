package tnt.exemplo;

import java.util.Hashtable;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JOptionPane;


public class MessageSender {
	
	private Context ctx = null;

	private static ConnectionFactory connectionFactory;
	private static Queue queue;	
	private static Connection connection = null;
	
	public MessageSender(){
		
		try {
			Hashtable env = new Hashtable();
			env.put("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");		
			env.put("java.naming.factory.url.pkgs", "org.jboss.naming:org.jnp.interfaces");
			env.put("java.naming.provider.url", "jnp://localhost:1099");
			
			ctx = new InitialContext(env);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	
	public void inicializar(){
		try {
			connectionFactory = (ConnectionFactory) ctx.lookup("ConnectionFactory");
			queue = (Queue) ctx.lookup("queue/ExemploCinco");			
			connection = connectionFactory.createConnection();
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
		
	public static void main(String[] args) {
		
		MessageSender msgSender = new MessageSender();		
		msgSender.inicializar();		
		boolean continuar = true;
		
		while(continuar){
			continuar = msgSender.produzirMensagem();
		}
		msgSender.finalizar();
	}
	
	public void finalizar(){
		try {
			connection.close();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
		
		
	public boolean produzirMensagem() {
				
		MessageProducer messageProducer;
		TextMessage textMessage;
		
		try {
						
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			messageProducer = session.createProducer(queue);
			textMessage = session.createTextMessage();
			
			String mensagem = JOptionPane.showInputDialog(null, "Informe a mensagem, ou \"SAIR\" " +
					"para sair da aplicação:");			
			
			if("SAIR".equals(mensagem)){
				return false;
			}
			
			textMessage.setText(mensagem);
			System.out.println("Sending the following message: " + mensagem);
			messageProducer.send(textMessage);
			messageProducer.close();
			session.close();
			
		} catch (JMSException e) {
			e.printStackTrace();		
		}
		
		return true;
	}

	
}

