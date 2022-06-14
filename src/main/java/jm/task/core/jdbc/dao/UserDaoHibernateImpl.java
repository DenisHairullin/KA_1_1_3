package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.*;

import javax.persistence.PersistenceException;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory;

    public UserDaoHibernateImpl() {
        sessionFactory = Util.getSessionFactory();
    }

    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE users "
                    + "(id BIGINT AUTO_INCREMENT PRIMARY KEY, "
                    + "name VARCHAR(60), "
                    + "lastName VARCHAR(60), "
                    + "age TINYINT)").executeUpdate();
            session.getTransaction().commit();
        } catch (IllegalStateException | PersistenceException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE users").executeUpdate();
            session.getTransaction().commit();
        } catch (IllegalStateException | PersistenceException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(new User(name, lastName, age));
            transaction.commit();
        } catch (IllegalStateException | PersistenceException e) {
            e.printStackTrace();
            if (transaction != null) {
                try {
                    transaction.rollback();
                } catch (IllegalStateException | PersistenceException r) {
                    r.printStackTrace();
                }
            }
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (HibernateException r) {
                    r.printStackTrace();
                }
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM User WHERE id = :id").setParameter("id", id).executeUpdate();
            transaction.commit();
        } catch (IllegalStateException | PersistenceException e) {
            e.printStackTrace();
            if (transaction != null) {
                try {
                    transaction.rollback();
                } catch (IllegalStateException | PersistenceException r) {
                    r.printStackTrace();
                }
            }
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (HibernateException r) {
                    r.printStackTrace();
                }
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM User", User.class).list();
        } catch (IllegalStateException | PersistenceException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void cleanUsersTable() {
        Session session = null;
        Transaction transaction = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            transaction.commit();
        } catch (IllegalStateException | PersistenceException e) {
            e.printStackTrace();
            if (transaction != null) {
                try {
                    transaction.rollback();
                } catch (IllegalStateException | PersistenceException r) {
                    r.printStackTrace();
                }
            }
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (HibernateException r) {
                    r.printStackTrace();
                }
            }
        }
    }
}
