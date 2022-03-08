package repos;

import models.things.Course;
import models.things.Term;
import models.users.Professor;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public class CourseRep extends BaseRepository<Course> {
    public CourseRep(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Course read(Integer id) {
        try (var session = sessionFactory.openSession()) {
            try {
                return session.get(Course.class, id);
            } catch (Exception e) {
                return null;
            }
        }
    }

    public List<Course> readAll() {
        try (var session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            var criteriaQuery = criteriaBuilder.createQuery(Course.class);
            var root = criteriaQuery.from(Course.class);
            var query = criteriaQuery.select(root);
            return session.createQuery(query).list();
        }
    }

    public List<Course> readAll(Term term) {
        try (var session = sessionFactory.openSession()){
            var query = session
                    .createQuery("from Course c where c.term.term = :term",Course.class)
                    .setParameter("term",term.getTerm());
            return query.list();
        }
    }

    public List<Course> readAllByProfessor(Professor professor) {
        try (var session = sessionFactory.openSession()) {
            return session
                    .createQuery("select c from Course c left join fetch c.professor where c.professor.id = :pId",Course.class)
                    .setParameter("pId",professor.getId())
                    .list();
        } catch (Exception e) {
            return null;
        }
    }

    public void detachProfessor(Professor professor) {
        try (var session = sessionFactory.openSession()) {
            var transaction = session.beginTransaction();
            session
                    .createQuery("update Course c set c.professor = null where c.professor.id = :pId")
                    .setParameter("pId",professor.getId())
                    .executeUpdate();
            transaction.commit();
            session.close();
        }
    }


        /*HashMap<Course, Double> courseWithGrade = new HashMap<>();
        String readStmt = "SELECT c.*,p.*,grade FROM course_to_student " +
                "INNER JOIN students s on s.student_id = course_to_student.student_id " +
                "INNER JOIN courses c on c.course_id = course_to_student.course_id " +
                "INNER JOIN professors p on p.prof_id = c.prof_id " +
                " WHERE course_to_student.student_id = ?;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(readStmt);
            ps.setInt(1, student.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                courseWithGrade.put(
                        new Course(
                                rs.getInt("course_id"),
                                rs.getInt("course_unit"),
                                rs.getString("course_name"),
                                new Professor(
                                        rs.getInt("prof_id"),
                                        rs.getString("prof_firstname"),
                                        rs.getString("prof_lastname"),
                                        rs.getString("prof_username"),
                                        rs.getString("prof_password"),
                                        ProfPosition.valueOf(rs.getString("prof_position"))
                                ),
                                rs.getInt("term")
                        ),
                        rs.getDouble("grade")
                );
            }
            return courseWithGrade;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;*/
}
