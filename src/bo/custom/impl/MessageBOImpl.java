package bo.custom.impl;

import bo.custom.MessageBO;
import dao.DAOFactory;
import dao.custom.MessageDao;
import dto.MessageDTO;
import enitity.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageBOImpl implements MessageBO {
    private MessageDao messageDao = (MessageDao) DAOFactory.getDaoFactory().getDao(DAOFactory.DAOTypes.MESSAGE);

    @Override
    public void save(MessageDTO m) {
        System.out.println(m.toString());
        if (m != null) {
            messageDao.save(new Message(m.getName(), m.getMessage()));
        }
    }

    @Override
    public List<MessageDTO> getAll() {
        List<Message> all = messageDao.getAll();
        List<MessageDTO> dtos = new ArrayList<>();
        for (Message m : all
        ) {
            dtos.add(new MessageDTO(m.getName(), m.getMessage()));
        }
        return dtos;
    }
}
