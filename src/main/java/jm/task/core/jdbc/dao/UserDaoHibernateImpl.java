package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.HelperHibernate;
import jm.task.core.jdbc.util.Util;
import org.hibernate.*;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory;

    public UserDaoHibernateImpl() {
        sessionFactory = Util.getSessionFactory();
    }

    @Override
    public void createUsersTable() {
        HelperHibernate.Transaction(sessionFactory, x -> x.createSQLQuery("CREATE TABLE users "
                        + "(id BIGINT AUTO_INCREMENT PRIMARY KEY, "
                        + "name VARCHAR(60), "
                        + "lastName VARCHAR(60), "
                        + "age TINYINT)").executeUpdate());
    }

    @Override
    public void dropUsersTable() {
        HelperHibernate.Transaction(sessionFactory, x -> x.createSQLQuery("DROP TABLE users").executeUpdate());
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        HelperHibernate.Transaction(sessionFactory, x -> {
            x.persist(new User(name, lastName, age));
            return null;
        });
    }

    @Override
    public void removeUserById(long id) {
        HelperHibernate.Transaction(sessionFactory, x -> x.createQuery("DELETE FROM User WHERE id = :id")
                .setParameter("id", id).executeUpdate());
    }

    @Override
    public List<User> getAllUsers() {
        return HelperHibernate.Transaction(sessionFactory, x -> x.createQuery("FROM User", User.class).list());
    }

    @Override
    public void cleanUsersTable() {
        HelperHibernate.Transaction(sessionFactory, x -> x.createQuery("DELETE FROM User").executeUpdate());
    }
}
