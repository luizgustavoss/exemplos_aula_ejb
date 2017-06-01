package acme.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="livro")
public class Livro implements java.io.Serializable
{
    private static final long serialVersionUID = 1L;

    @Id 
    private Long id;
    private String titulo;
    private String autor;
    private String editora;

    public Livro() {}

    public Livro(Long id, String titulo, String autor, String editora) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.editora = editora;
    }
    
    public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
        return id;
    }

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getEditora() {
		return editora;
	}

	public void setEditora(String editora) {
		this.editora = editora;
	}

    
}

