package dao;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import entidades.Agenda;
import util.JPAUtil;

public class AgendaDAO {
    
    public static void salvar(Agenda agenda) {
        EntityManager entityManager = null;
        try {
            entityManager = JPAUtil.criarEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(agenda);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager != null && entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public static void editar(Agenda agenda) {
        EntityManager entityManager = null;
        try {
            entityManager = JPAUtil.criarEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(agenda);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager != null && entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public static void excluir(Agenda agenda) {
        EntityManager entityManager = null;
        try {
            entityManager = JPAUtil.criarEntityManager();
            entityManager.getTransaction().begin();
            agenda = entityManager.find(Agenda.class, agenda.getId());
            entityManager.remove(agenda);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager != null && entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public static List<Agenda> listar() {
        EntityManager entityManager = null;
        try {
            entityManager = JPAUtil.criarEntityManager();
            Query query = entityManager.createQuery("SELECT a FROM Agenda a");
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public static int contagem() {
        EntityManager entityManager = null;
        try {
            entityManager = JPAUtil.criarEntityManager();
            Query query = entityManager.createQuery("SELECT COUNT(a) FROM Agenda a");
            return ((Number) query.getSingleResult()).intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public static List<Agenda> listarPorData(Date data) {
        EntityManager entityManager = null;
        try {
            entityManager = JPAUtil.criarEntityManager();
            Query query = entityManager.createQuery("SELECT a FROM Agenda a WHERE a.dataHoraConsulta = :data");
            query.setParameter("data", data);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}
