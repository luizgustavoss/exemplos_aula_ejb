package acme.business;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import acme.model.Livro;

@Stateful
public class LivroBean implements LivroLocal{

    @PersistenceContext(unitName="ExDois")
    private EntityManager em;
    
    private Map<Long, Livro> livros;    
    
    @PostConstruct
    private void inicializar(){
    	livros = new HashMap<Long, Livro>();
    }
    
    public void adicionarLivro(String titulo, 
    		String autor, String editora){
    	
    	if(empty(titulo) || empty(autor) || empty(editora)){
    		return;
    	}
    	Livro livro = new Livro(generateID(), titulo, 
    			autor, editora);    	
    	livros.put(livro.getId(), livro);
    }
        
    public void removerLivro(Long id){    	
    	livros.remove(id);
    }
    
    public void confirmar(){    	
    	for(Livro l : livros.values()){
    		inserirLivro(l);
    	}
    	livros.clear();
    }    
 
    private void inserirLivro(Livro livro){
    	em.persist(livro);
    }    
    
    public List<Livro> listaTodosCadastrados() {
        TypedQuery<Livro> query = em.createQuery(
        		"FROM Livro l ORDER BY l.titulo", Livro.class);
        return query.getResultList();
    }

    /**
     * Lista todos os livros que ainda não foram gravados no banco de dados
     * */
    public List<Livro> listarTodosTemporarios(){
    	List<Livro> lista = new ArrayList<Livro>();
    	
    	if(!livros.isEmpty()){
    		lista.addAll(livros.values());
    	}
    	return lista;
    }
    
    
    private boolean empty(String value){
    	return value == null || "".equals(value);
    }
    
    private long generateID() {
		long random = super.hashCode();
		try {
			random = SecureRandom.getInstance("SHA1PRNG")
			.nextInt(99999999);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Math.abs(random);
	}
}
