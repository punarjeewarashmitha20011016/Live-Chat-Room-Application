package dao.custom.impl;

import dao.custom.MessageDao;
import enitity.Message;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.FactoryConfiguration;

import java.util.List;

public class MessageDaoImpl implements MessageDao {
    private Session session = null;

    @Override
    public boolean save(Message m) {
        if (m != null) {
            session = FactoryConfiguration.getFactoryConfiguration().getSession();
            Transaction transaction = session.beginTransaction();
            session.save(m);
            transaction.commit();
            session.close();
            return true;
        }
        return false;
    }

    @Override
    public List<Message> getAll() {
        session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();
        String hql = "FROM Message ";
        Query query = session.createQuery(hql);
        List<Message> list = query.list();
        transaction.commit();
        session.close();
        return list;
    }
}
