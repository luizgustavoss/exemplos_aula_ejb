package acme.exemplo.client;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import acme.exemplo.business.ConversorTemperaturaRemote;

public class ClienteConversor implements ActionListener{
	
	private JButton btnConverter;
	private JTextField fldValor;
	private JTextField fldResultado;
	private JRadioButton celcius;
	private JRadioButton fahrenheit;
	private ButtonGroup group;
	
	private ConversorTemperaturaRemote bean;
	
	public ClienteConversor(){
		try {
			Hashtable env = new Hashtable();
			env.put("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");		
			env.put("java.naming.factory.url.pkgs", "org.jboss.naming:org.jnp.interfaces");
			env.put("java.naming.provider.url", "jnp://localhost:1099");
			
			Context ctx = new InitialContext(env);
			bean = ( ConversorTemperaturaRemote) ctx.lookup("ConversorTemperaturaBean/remote");
		} catch (NamingException e) {
			JOptionPane.showMessageDialog(null, "Não foi possível acessar o EJB!", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private JPanel montarPainelPrincipal(){
		
		fldValor = new JTextField();
		fldResultado = new JTextField();
		fldResultado.setEnabled(false);
		btnConverter = new JButton("Converter");
		btnConverter.addActionListener(this);
		
		JPanel painel = new JPanel();	
		painel.setLayout(new GridLayout(4, 1, 5, 5));
		
		painel.add(montarPainelRadioButtons());										
		painel.add(fldValor);		
		painel.add(fldResultado);	
		painel.add(btnConverter);
		
		painel.setSize(300, 300);
		
		return painel;
		
	}
	
	
	private JPanel montarPainelRadioButtons(){
		
		JPanel radioPanel = new JPanel();
		radioPanel.setLayout(new GridLayout(1, 2));
		celcius = new JRadioButton("Celcius > Fahrenheit", true);
		fahrenheit= new JRadioButton("Fahrenheit > Celcius");
		
		radioPanel.add(celcius);
		radioPanel.add(fahrenheit);
		
		group = new ButtonGroup();
		group.add(celcius);
		group.add(fahrenheit);
		
		return radioPanel;
	}

	
	public static void main(String[] args){
		
		ClienteConversor conversorCliente = new ClienteConversor();
		
		JFrame frame = new JFrame("Exemplo Tres - Stateless Remoto");
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(conversorCliente.montarPainelPrincipal());
		frame.setSize(300, 300);
		frame.setVisible(true);		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		String valor = fldValor.getText();
		BigDecimal valorDecimal = null;
		
		if(valor == null || "".equals(valor)){
			fldResultado.setText("");
		}
		else{
			try{
				valorDecimal = new BigDecimal(valor);
				
				/* apenas verificar qual radiobutton está selecionado */
				if(celcius.isSelected()){
					fldResultado.setText(bean.celciusParaFahrenheit(valorDecimal).toString());
				}
				else{
					fldResultado.setText(bean.fahrenheitParaCelcius(valorDecimal).toString());
				}
			}
			catch(Exception exp){
				fldResultado.setText("");
				fldValor.setText("");
			}
		}

	}
	
}
