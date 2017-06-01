package acme.presentation;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import acme.business.SenadorBean;
import acme.model.Senador;

@ManagedBean(name="senadores")
@SessionScoped
public class SenadorMB implements java.io.Serializable {

	@EJB
    private SenadorBean senadorBean;
	
	private List<String> estados;
    private String estado;

    @PostConstruct
    public void inicializa() {
    	this.estados = senadorBean.listaEstados();
        this.estado = estados.get(0);        
    }

    public List<Senador> getTodos() {
        return senadorBean.listaSenadores(estado);
    }

    public List<String> getEstados() {
        return estados;
    }    
    
    public void setEstados(List<String> estados){
    	this.estados = estados;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void busca() {
    	estados = senadorBean.listaEstados();
    }
}
