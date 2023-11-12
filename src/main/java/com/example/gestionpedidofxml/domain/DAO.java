package com.example.gestionpedidofxml.domain;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DAO <T> {
    public ArrayList<T> loadAll ();
    public T get(Long id);
    public T save(T data);
    public void update(T data);
    public void delete (T data);

}
