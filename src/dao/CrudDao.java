package dao;

import java.util.ArrayList;

public interface CrudDao <T,ID> extends SuperDAO {
    public boolean save(T t);
    public boolean update(T t);
    public boolean delete(T t);
    public T search(ID id);
    public ArrayList<T> getAll();
}
