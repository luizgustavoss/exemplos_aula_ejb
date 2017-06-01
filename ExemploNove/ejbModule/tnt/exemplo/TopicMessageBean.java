package tnt.exemplo;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;


@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destination",
        propertyValue = "topic/ExemploSete"),
    @ActivationConfigProperty(propertyName="destinationType",
        propertyValue="javax.jms.Topic"),
    @ActivationConfigProperty(propertyName = "acknowledgeMode",
        propertyValue = "Auto-acknowledge")
})
public class TopicMessageBean implements MessageListener {
	
	@EJB
	TopicProcessorBean processorBean;
	
	public void onMessage(Message message) {
		try {
			
			TextMessage msg = (TextMessage) message;
			processorBean.processarMensagen(msg.getText());
			
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
