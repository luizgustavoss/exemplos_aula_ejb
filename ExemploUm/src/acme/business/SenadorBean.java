package acme.business;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import acme.model.Senador;

@Stateless
public class SenadorBean {

    @PersistenceContext(unitName="ExUm")
    private EntityManager em;
 
    public List<Senador> listaSenadores(String estado) {
        TypedQuery<Senador> query = em.createQuery(
        		"FROM Senador s WHERE s.estado = " +
        		":estado ORDER BY s.nome", 
        		Senador.class);
        query.setParameter("estado", estado);
        return query.getResultList();
    }

    public List<String> listaEstados() {
        TypedQuery<String> query = em.createQuery(
        		"SELECT DISTINCT s.estado FROM Senador s " +
        		"ORDER BY s.estado", 
        		String.class);
        return query.getResultList();
    }
}
