package com.company;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ToDoList implements ToDoListI {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    static ArrayList<Task> mainList = new ArrayList<>();
    static Date todayDate = new Date();
    TaskEditor taskEditor = new TaskEditor();

    public void mainMenu() throws IOException, ParseException {
        System.out.println("Main menu:");
        System.out.printf("%d. Добавить задание\n%d. Выбрать задание\n" +
                "%d. Показать список дел\n%d. Очистить список дел\n%d. Завершите работу\n", 1, 2, 3, 4, 5);
        int choice = Integer.parseInt(reader.readLine());
        switch (choice) {
            case 1:
                addTask();
                taskEditor.saveInFile();
                break;
            case 2:
                editTask();
                break;
            case 3:
                showList();
                break;
            case 4:
                clearList();
                taskEditor.saveInFile();
                break;
            case 5:
                taskEditor.saveInFile();
                System.exit(0);
            default:
                System.out.println("Введите число от 1 до 5\n");
        }
    }
    public void start() throws IOException, ParseException {
        File save = new File("TaskaSneponyatnimsimvolom.txt");
        if (save.exists()) {
            FileReader reader = new FileReader("TaskaSneponyatnimsimvolom.txt");
            int c = reader.read();
            if (c != -1) {
                loadFromFile();
            }
        } else {
            save.createNewFile();
        }
    }
    public void addTask() throws ParseException, IOException {
        System.out.println("Введите дату в формате dd/MM/yyyy");
        String userDate = reader.readLine();
        String today = dateFormat.format(todayDate);
        if (dateFormat.parse(userDate).before(dateFormat.parse(today))) {
            System.out.println("Вы не можете добавить задачу с прошедшей датой\n");
            return;
        }
        String description = "";
        do {
            System.out.println("Введите описание вашей задачи");
            description = reader.readLine();
            if (description.equals("")) {
                System.out.println("Вы не можете добавить задачу без описания\n");
            }

        } while (description.equals(""));
        for (Task task : mainList) {
            if (task.getDate().equals(dateFormat.parse(userDate))) {
                if (task.getDescription().equals(description)) {
                    System.out.println("Задача уже существует\n");
                    return;
                }
            }
        }
        Task task = new Task(description, dateFormat.parse(userDate));
        mainList.add(task);
    }
    public void editTask() throws ParseException, IOException, IndexOutOfBoundsException {
        ArrayList<Task> editList = new ArrayList<>();
        ArrayList<Integer> indexes = new ArrayList<>();
        System.out.println("Введите дату в формате day/month/year");
        Date userDate = dateFormat.parse(reader.readLine());
        for (Task task : mainList) {
            if (task.getDate().equals(userDate)) {
                editList.add(task);
                indexes.add(mainList.indexOf(task));
            }
        }
        if (taskEditor.showEditList(editList, indexes) != -1) {
            taskEditor.showMenu(indexes, editList);
        }
    }
    public void showList() throws ParseException, IOException {
        if (mainList.isEmpty()) {
            System.out.println("Список дел пуст\n");
            return;
        }
        TaskPrinter printer = new TaskPrinter();
        while (true) {
            System.out.printf("%d. На сегодня\n%d. На эту неделю\n%d. На выбранную дату\n%d. Весь список\n" +
                    "%d. Назад\n", 1, 2, 3, 4, 5);
            int changeAction = Integer.parseInt(reader.readLine());
            switch (changeAction) {
                case 1:
                    printer.printTodayTasks(mainList);
                    break;
                case 2:
                    printer.printWeekTasks(mainList);
                    break;
                case 3:
                    System.out.println("Введите дату в формате day/month/year");
                    Date userDate = dateFormat.parse(reader.readLine());
                    printer.printSpecificTasks(mainList, userDate);
                    break;
                case 4:
                    printer.printList(mainList);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Введите число от 1 до 5 \n");
            }
        }
    }
    public void clearList() throws IOException {
        System.out.println("Are you sure? (y/n)");
        String answer = reader.readLine();
        if (answer.equalsIgnoreCase("y")) {
            mainList.clear();
            System.out.println("Clear\n");
        }
    }
    public void loadFromFile() throws IOException, ParseException {
        FileReader fileReader = new FileReader("TaskaSneponyatnimsimvolom.txt");
        String fromSaveList = "";
        int c = fileReader.read();
        while (c != -1) {
            fromSaveList = fromSaveList + (char) c;
            c = fileReader.read();
        }
        fileReader.close();

        String[] chunks = fromSaveList.split(String.valueOf((char) 174));
        for (int i = 0; i < chunks.length; i += 3) {
            Task task = new Task(dateFormat.parse(chunks[i]), chunks[i + 1], chunks[i + 2]);
            mainList.add(task);
        }
        for (Task task : mainList) {
            String status = task.getStatus();
            String taskDate = dateFormat.format(task.getDate());
            String today = dateFormat.format(todayDate);
            if (status.equals("UNCOMPLETED") && dateFormat.parse(taskDate).before(dateFormat.parse(today))) {
                task.setStatus(Status.UNCOMPLETED);
            }
        }
    }
}