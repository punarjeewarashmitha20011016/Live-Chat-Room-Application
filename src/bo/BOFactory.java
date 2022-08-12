package bo;

import bo.custom.impl.MessageBOImpl;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {
    }

    public static BOFactory getBoFactory() {
        return boFactory == null ? boFactory = new BOFactory() : boFactory;
    }

    public SuperBO getBOTypes(BOTypes types) {
        switch (types) {
            case MESSAGE:
                return new MessageBOImpl();
            default:
                break;
        }
        return null;
    }

    public enum BOTypes {
        MESSAGE
    }
}
