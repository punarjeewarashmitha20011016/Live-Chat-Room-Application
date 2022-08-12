package dto;

import java.io.Serializable;

public class MessageDTO implements Serializable {
    private String name;
    private String message;

    public MessageDTO() {
    }

    public MessageDTO(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "MessageDTO{" +
                "name='" + name + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
