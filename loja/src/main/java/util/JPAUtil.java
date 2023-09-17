package util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

	// Se tivéssemos vários bancos de dados na aplicação, teríamos várias tags persistence-unit, 
	// cada uma com um name distinto, e, na hora de criar a factory, passaríamos
	// qual é o persistence-unit. Desta maneira, a JPA fica sabendo com qual banco ela deve se 
	// conectar. Portanto, temos que adicionar o nome do persistence-unit, que, no nosso caso, é "loja".
	private static final EntityManagerFactory FACTORY = Persistence.createEntityManagerFactory("loja");

	public static EntityManager getEntityManager() {
		return FACTORY.createEntityManager();
	}
}
