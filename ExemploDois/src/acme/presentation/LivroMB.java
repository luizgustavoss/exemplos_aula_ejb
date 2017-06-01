package acme.presentation;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import acme.business.LivroLocal;
import acme.model.Livro;

@ManagedBean(name="livros")
@SessionScoped
public class LivroMB implements java.io.Serializable {

	@EJB
    private LivroLocal livroBean;
	
	private Livro livro;

	private List<Livro> livrosCadastrados;

	
    @PostConstruct
    public void inicializa() {
    	livro = new Livro();
        this.livrosCadastrados = livroBean.listaTodosCadastrados();      
    }


	public List<Livro> getLivrosTemporarios() {
		return livroBean.listarTodosTemporarios();
	}

	public List<Livro> getLivrosCadastrados() {
		return livrosCadastrados;
	}

	
	public void adicionarLivro(){
		livroBean.adicionarLivro(livro.getTitulo(), livro.getAutor(), livro.getEditora());		
		livro = new Livro();
	}
	
	public void confirmar(){
		livroBean.confirmar();		
		this.livrosCadastrados = livroBean.listaTodosCadastrados(); 
	}


	public Livro getLivro() {
		return livro;
	}


	public void setLivro(Livro livro) {
		this.livro = livro;
	}
    
}
