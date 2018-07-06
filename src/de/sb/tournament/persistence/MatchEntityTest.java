package de.sb.tournament.persistence;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Test;

public class MatchEntityTest extends EntityTest{
	
	@Test
	public void testConstraints() {
		ValidatorFactory vf = super.getEntityValidatorFactory();
		Validator validator;
		try {
		
			validator = vf.getValidator();
			Match entity = new Match( ) ;
			entity.setLeftParentRank((byte) 0);
			entity.setRightParentRank((byte) 0);
			entity.setRightScore((short) 0);
			entity.setLeftScore((short) 0);
			
			Set<ConstraintViolation<Match>> constraintViolations = validator.validate(entity);
			assertEquals( 0, constraintViolations.size() );
		} finally {
			vf.close();
		}
      
	}
  
	@Test
	public void testLifeCycle() {
		EntityManager em = super.getEntityManagerFactory().createEntityManager();
		Set<Long> wb = super.getWasteBasket();
		
		em.getTransaction().begin();
		Match entity = new Match() ;
		em.persist(entity);
		em.getTransaction().commit();
		try {
			EntityTest obj = em.getReference(EntityTest.class, entity.getIdentity());
		   
		} catch (EntityNotFoundException e) {
		    // Entity Not Found
		}
		em.flush();
		wb.add( entity.getIdentity());
	    
	     javax.persistence.Cache cache = em.getEntityManagerFactory().getCache();
	    cache.evict(entity.getClass(), entity.getIdentity());
	    
	    em.close();
	    em.clear();
	}
	

}
