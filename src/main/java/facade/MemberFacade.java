package facade;
/*
 * author benjaminp
 * version 1.0
 */

import entity.Member;
import entity.dto.MemberDTO;

import java.util.ArrayList;
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

    public List<MemberDTO> toMemberDTOList(List<Member> members) {
        List<MemberDTO> result = new ArrayList<>();
        members.forEach(member -> {
            result.add(new MemberDTO(member));
        });
        return result;
    }

    public List<MemberDTO> getAll() {
        EntityManager entityManager = getEntityManager();
        List<Member> members = entityManager.createQuery("SELECT m FROM Member m", Member.class).getResultList();
        return toMemberDTOList(members);
    }

    public List<MemberDTO> getByName(String name) {
        EntityManager entityManager = getEntityManager();
        List<Member> members = entityManager.createNamedQuery("Member.findByName", Member.class).setParameter("name", name).getResultList();
        return toMemberDTOList(members);
    }

    public MemberDTO getById(long id) {
        EntityManager entityManager = getEntityManager();
        Member member = entityManager.find(Member.class, id);
        if(member != null) return new MemberDTO(member);
        else return null;
    }

    public MemberDTO create(MemberDTO member) {
        EntityManager em = emf.createEntityManager();
        Member persistMember = new Member(member.getName(), member.getStudentId(), member.getColorLevel());
        try {
            em.getTransaction().begin();
            em.persist(persistMember);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        member.setId(persistMember.getId());
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
