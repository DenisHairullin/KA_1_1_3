package jm.task.core.jdbc.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.PersistenceException;
import java.util.function.Function;

public class HelperHibernate {
    public static <T> T Transaction(SessionFactory sessionFactory, Function<Session, T> function) {
        Session session = null;
        Transaction transaction = null;
        T result = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            result = function.apply(session);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (HibernateException r) {
                    r.printStackTrace();
                }
            }
        }
        return result;
    }
}
