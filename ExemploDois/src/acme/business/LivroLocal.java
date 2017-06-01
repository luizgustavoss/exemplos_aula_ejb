package acme.business;

import java.util.List;

import javax.ejb.Local;

import acme.model.Livro;

@Local
public interface LivroLocal {
	
	public void adicionarLivro(String titulo, String autor, 
			String editora);
	public void removerLivro(Long id);
	public void confirmar();
	public List<Livro> listaTodosCadastrados();
	public List<Livro> listarTodosTemporarios();
}
