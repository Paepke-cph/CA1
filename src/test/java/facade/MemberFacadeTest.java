package facade;
/*
 * author benjaminp
 * version 1.0
 */

import entity.Member;
import entity.dto.MemberDTO;
import utils.EMF_Creator;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class MemberFacadeTest {

    private static EntityManagerFactory emf;
    private static MemberFacade memberfacade;

    public MemberFacadeTest() {
    }

    @BeforeAll
    public static void setUpClassV2() {
        emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.CREATE);
        memberfacade = MemberFacade.getMemberFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
        // Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Member.Truncate").executeUpdate();
            em.persist(new Member("Person1", "11111", "Red"));
            em.getTransaction().commit(); // Just to make sure that member 1 is the first to be inserted into the db.
            em.getTransaction().begin();
            em.persist(new Member("Person2", "22222", "Green"));
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testCreateMember() {
        Member aNewMember = new Member("Person3", "33333", "Yellow");
        MemberDTO dto = new MemberDTO(aNewMember);
        assertNull(dto.getId());
        memberfacade.create(dto);
        assertEquals(new Long(3L), dto.getId());
    }

    @Test
    public void testGetById_with_invalid_id() {
        Long id = 12313141L;
        MemberDTO member = memberfacade.getById(id);
        assertNull(member);
    }

    @Test
    public void testGetById_with_valid_id() {
        Long id = 1L;
        MemberDTO member = memberfacade.getById(id);
        assertEquals("Person1", member.getName());
        assertEquals("11111", member.getStudentId());
        assertEquals("Red", member.getColorLevel());
    }

    @Test
    public void testGetByName_with_multiple_members_found() {
        String name = "P%";
        List<MemberDTO> members = memberfacade.getByName(name);
        assertEquals(2, members.size());
    }

    @Test
    public void testGetByName_with_invalid_name() {
        String name = "This should really not be a name in the db";
        List<MemberDTO> members = memberfacade.getByName(name);
        assertTrue(members.isEmpty());
    }

    @Test
    public void testGetByName_with_valid_name() {
        String name = "Person1";
        List<MemberDTO> members = memberfacade.getByName(name);
        MemberDTO member = members.get(0);
        assertEquals(1, members.size());
        assertEquals(name, member.getName());
        assertEquals("11111", member.getStudentId());
        assertEquals("Red", member.getColorLevel());
    }

    @Test
    public void testGetAll() {
        List<MemberDTO> members = memberfacade.getAll();
        assertEquals(2, members.size());
        MemberDTO first = members.get(0);
        MemberDTO second = members.get(1);
        assertEquals("Person1", first.getName());
        assertEquals("11111", first.getStudentId());
        assertEquals("Red", first.getColorLevel());

        assertEquals("Person2", second.getName());
        assertEquals("22222", second.getStudentId());
        assertEquals("Green", second.getColorLevel());
    }

    @Test
    public void testCount() {
        assertEquals(2, memberfacade.getCount());
    }


}
