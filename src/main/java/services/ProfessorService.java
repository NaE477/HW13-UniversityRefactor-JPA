package services;

import models.users.Professor;
import org.hibernate.SessionFactory;
import repos.ProfessorRep;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ProfessorService extends BaseService{

    private final ProfessorRep professorRep;
    private final CourseService courseService;

    public ProfessorService(SessionFactory sessionFactory) {
        super(sessionFactory);
        professorRep = new ProfessorRep(super.getSessionFactory());
        courseService = new CourseService(sessionFactory);
    }

    public Professor signUpProfessor(Professor professor){
        return professorRep.ins(professor);
    }
    public Professor find(Integer professorId){
        return professorRep.read(professorId);
    }
    public Professor find(String username){
        return professorRep.read(username);
    }
    public List<Professor> findAll(){
        return professorRep.readAll();
    }
    public void editProfile(Professor professor){
        professorRep.update(professor);
    }
    public void deleteProfessor(Professor professor){
        courseService.detachProfessor(professor);
        professorRep.delete(professor);
    }

}
