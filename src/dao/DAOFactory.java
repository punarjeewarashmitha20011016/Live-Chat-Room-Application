package dao;

import dao.custom.impl.MessageDaoImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getDaoFactory() {
        if (daoFactory == null) {
            daoFactory = new DAOFactory();
        }
        return daoFactory;
    }

    public SuperDAO getDao(DAOTypes types) {
        switch (types) {
            case MESSAGE:
                return new MessageDaoImpl();
            default:
                return null;
        }
    }

    public enum DAOTypes {
        MESSAGE
    }
}