package services;

import models.things.Term;
import org.hibernate.SessionFactory;
import repos.TermRepository;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class TermService {
    private final TermRepository termRepository;
    public TermService(EntityManagerFactory entityManagerFactory){
        termRepository = new TermRepository(entityManagerFactory);
    }

    public Term initiate(Term term) {
        return termRepository.ins(term);
    }

    public void endTerm(){
        Term oldTerm = findCurrentTerm();
        Term termToUpdate = new Term(0,oldTerm.getTerm() + 1,null);
        termRepository.ins(termToUpdate);
    }

    public Term findCurrentTerm(){
        return termRepository.read();
    }

    public Term findFirstTerm(){
        return termRepository.readFirst();
    }

    public List<Term> findAll() {
        return termRepository.readAll();
    }
}
