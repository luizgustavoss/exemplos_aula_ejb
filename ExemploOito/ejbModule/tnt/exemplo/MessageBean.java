package tnt.exemplo;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;


@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destination",
        propertyValue = "queue/ExemploCinco"),
    @ActivationConfigProperty(propertyName="destinationType",
        propertyValue="javax.jms.Queue"),
    @ActivationConfigProperty(propertyName = "acknowledgeMode",
        propertyValue = "Auto-acknowledge")
})
public class MessageBean implements MessageListener {
	
	public void onMessage(Message message) {
		try {
			TextMessage msg = (TextMessage) message;
			System.out.println("Message received: " + msg.getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
