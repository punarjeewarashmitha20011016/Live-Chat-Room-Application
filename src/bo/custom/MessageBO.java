package bo.custom;

import bo.SuperBO;
import dto.MessageDTO;

import java.util.List;

public interface MessageBO extends SuperBO {
    void save(MessageDTO m);

    List<MessageDTO> getAll();
}
