package acme.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="senador")
public class Senador implements java.io.Serializable
{
    private static final long serialVersionUID = 1L;

    @Id 
    @GeneratedValue	
    private Integer id;
    private String nome;
    private String partido;
    private String estado;
    private String periodos;

    public Senador() {}

    public Senador(int id, String nome, String partido, String estado, String periodos) {
        this.id = id;
        this.nome = nome;
        this.partido = partido;
        this.estado = estado;
        this.periodos = periodos;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

	public String getPartido() {
		return partido;
	}

	public String getPeriodos() {
		return periodos;
	}

	public String getEstado() {
		return estado;
	}
    
}

