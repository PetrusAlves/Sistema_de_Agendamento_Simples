package util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

//esse código é uma configuração básica para criar e fornecer instâncias de EntityManager em aplicação Java utilizando JPA.
public class JPAUtil {
	private static EntityManagerFactory fabricaDeGerenciamentoDeEntidade = Persistence.createEntityManagerFactory("Conexao");
	
	public static EntityManager criarEntityManager() {
		return fabricaDeGerenciamentoDeEntidade.createEntityManager();
	}
}
