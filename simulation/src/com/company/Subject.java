package com.company;

public interface Subject {
    void register_observer( Observer o );
    void remove_observer( Observer o );
    void notify_observer();
}
