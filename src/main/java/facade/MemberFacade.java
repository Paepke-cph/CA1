package facade;
/*
 * author benjaminp
 * version 1.0
 */

import entity.Member;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;


public class MemberFacade {

    private static MemberFacade instance;
    private static EntityManagerFactory emf;

    private MemberFacade() { }

    /**
     * @param _emf
     * @return an instance of this facade class.
     */
    public static MemberFacade getMemberFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new MemberFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public int getCount() {
        EntityManager entityManager = getEntityManager();
        return Integer.parseInt(entityManager.createQuery("SELECT count(m) FROM Member m", Long.class).getSingleResult().toString());
    }

    public List<Member> getAll() {
        List<Member> members = null;
        EntityManager entityManager = getEntityManager();
        return entityManager.createQuery("SELECT m FROM Member m", Member.class).getResultList();
    }

    public List<Member> getByName(String name) {
        List<Member> members = null;
        EntityManager entityManager = getEntityManager();
        members = entityManager.createNamedQuery("Member.findByName", Member.class).setParameter("name", name).getResultList();
        return members;
    }

    public Member getById(long id) {
        EntityManager entityManager = getEntityManager();
        return entityManager.find(Member.class, id);
    }

    public Member create(Member member) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(member);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return member;
    }

    public void populate() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Member.deleteAllRows").executeUpdate();
            em.persist(new Member("Benjamin", 21313L, "Green"));
            em.persist(new Member("Mads", 241939L, "Green"));
            em.persist(new Member("Oscar", 1241511L, "Yellow"));
            em.persist(new Member("Alexander", 2315111L, "Red"));
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
