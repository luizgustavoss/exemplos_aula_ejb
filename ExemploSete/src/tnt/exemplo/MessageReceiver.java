package tnt.exemplo;

import java.awt.HeadlessException;
import java.util.Hashtable;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JOptionPane;

public class MessageReceiver implements MessageListener{
	
	private static Context ctx = null;
	private static ConnectionFactory connectionFactory;
	private static Topic topic;	
	private static Connection connection = null;
		
	public static void main(String[] args) {
		
		try {
			Hashtable env = new Hashtable();
			env.put("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");		
			env.put("java.naming.factory.url.pkgs", "org.jboss.naming:org.jnp.interfaces");
			env.put("java.naming.provider.url", "jnp://localhost:1099");
			
			ctx = new InitialContext(env);			
			connectionFactory = (ConnectionFactory) ctx.lookup("ConnectionFactory");
			topic = (Topic) ctx.lookup("topic/ExemploSete");			
			connection = connectionFactory.createConnection();
			
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageConsumer messageConsumer = session.createConsumer(topic);
			messageConsumer.setMessageListener(new MessageReceiver());
			connection.start();	
			
			while(true){				
			}
			
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void onMessage(Message message) {

		try {
			TextMessage textMessage	 = (TextMessage) message;
			JOptionPane.showMessageDialog(null, textMessage.getText());
		} catch (HeadlessException e) {
			e.printStackTrace();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}