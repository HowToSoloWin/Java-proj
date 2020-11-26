package com.company;

import java.io.IOException;
import java.text.ParseException;

interface ToDoListI {
    void start() throws ParseException, IOException;
    void addTask() throws ParseException, IOException;
    void editTask() throws ParseException, IOException;
    void showList() throws ParseException, IOException;
    void loadFromFile() throws ParseException, IOException;
}
