package dao.custom;

import dao.SuperDAO;
import enitity.Message;

import java.util.List;

public interface MessageDao extends SuperDAO {
    boolean save(Message m);

    List<Message> getAll();
}
