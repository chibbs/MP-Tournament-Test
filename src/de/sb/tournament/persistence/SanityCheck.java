package de.sb.tournament.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class SanityCheck {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("tournament");
		try {
			EntityManager em = emf.createEntityManager();
			try {
				// der default für die Avatare hat immer den key 1
				Document document = em.find(Document.class, 1l);
				System.out.println(document.getContentType());
				
				List<TournamentType> ttlist = em.createQuery("select tt from TournamentType as tt", TournamentType.class).getResultList();
				System.out.println(ttlist.size());
				
				List<Tournament> tlist = em.createQuery("select t from Tournament as t", Tournament.class).getResultList();
				System.out.println(tlist.size());
				
				List<Match> mlist = em.createQuery("select m from Match as m", Match.class).getResultList();
				System.out.println(mlist.size());
				
				List<Group> glist = em.createQuery("select g from Group as g", Group.class).getResultList();
				System.out.println(glist.size());
				
				List<Competitor> clist = em.createQuery("select c from Competitor as c", Competitor.class).getResultList();
				System.out.println(clist.size());
			} finally {
				em.close();
			}
		} finally {
			emf.close();
		}

	}

}
