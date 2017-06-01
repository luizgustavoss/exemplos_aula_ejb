package tnt.exemplo;

import java.util.Hashtable;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JOptionPane;

public class MessageReceiver {
	
	private Context ctx = null;

	private static ConnectionFactory connectionFactory;
	private static Queue queue;	
	private static Connection connection = null;
	
	public MessageReceiver(){
		
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
		
		MessageReceiver msgSender = new MessageReceiver();		
		msgSender.inicializar();		
		boolean continuar = true;
		
		while(continuar){
			continuar = msgSender.receberMensagens();
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
		
		
	public boolean receberMensagens() {
				
		MessageConsumer messageConsumer;
		TextMessage textMessage;
		
		try {						
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			messageConsumer = session.createConsumer(queue);
			connection.start();
			boolean sair = false;
			
			do{				
				Message msg = messageConsumer.receive();				
				if(msg != null){
					System.out.println("Waiting for messages...");
					textMessage = (TextMessage) msg;

					JOptionPane.showMessageDialog(null, textMessage.getText());
				}
				else{
					sair = true;
				}
			}while(!sair);
			
			messageConsumer.close();
			session.close();
			
		} catch (JMSException e) {
			e.printStackTrace();		
		}		
		return true;
	}
}