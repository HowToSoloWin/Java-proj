package com.company;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import static com.company.ToDoList.mainList;


public class TaskEditor {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private int changeTask;
    public int showEditList(ArrayList<Task> editList, ArrayList<Integer> indexes) throws IOException {
        if (editList.isEmpty()) {
            System.out.println("В этот день нет никаких заданий\n");
            return -1;
        } else {
            for (Task task : editList) {
                TaskPrinter printer = new TaskPrinter();
                System.out.print(editList.indexOf(task)+1);
                printer.printTask(task);
            }
            while (true) {
                System.out.println("Введите номер задания для его редактирования");
                changeTask = Integer.parseInt(reader.readLine()) - 1;
                if (changeTask > editList.size() - 1) {
                    System.out.println("Введенный индекс неверен.");
                }else {
                    break;
                }
            }
        }
        return 0;
    }
    public void showMenu(ArrayList<Integer> indexes, ArrayList<Task> editList) throws IOException {
        while (true) {
            System.out.printf("%d. Изменить описание\n%d. Удалить\n%d. Пометить как выполненное\n%d. Пометить как невыполненное\n%d. Отмена\n", 1, 2, 3, 4, 5);
            int changeAction = Integer.parseInt(reader.readLine());
            switch (changeAction) {
                case 1:
                    changeDescription(editList);
                    saveInFile();
                    break;
                case 2:
                    deleteTask(indexes);
                    saveInFile();
                    return;
                case 3:
                    editList.get(changeTask).markCompleted();
                    saveInFile();
                    break;
                case 4:
                    editList.get(changeTask).markUnCompleted();
                    saveInFile();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Enter the correct number");
            }
        }
    }
    public void changeDescription(ArrayList<Task> editList) throws IOException {
        System.out.println("Enter the new description:");
        String newDescription = reader.readLine();
        if (newDescription.equals(editList.get(changeTask).getDescription())) {
            System.out.println("The new description must be different!\n");
        } else {
            editList.get(changeTask).setDescription(newDescription);
        }

    }
    public void deleteTask(ArrayList<Integer> indexes) throws IOException {
        System.out.println("Do you really want to delete this task? (y/n)");
        if (reader.readLine().equalsIgnoreCase("y")) {
            int index = indexes.get(changeTask);
            mainList.remove(index);
            System.out.println("Task deleted\n");
        }

    }
    public void saveInFile() throws IOException {
        FileWriter fileWriter = new FileWriter("TaskaSneponyatnimsimvolom.txt");
        for (Task task : mainList) {
            fileWriter.write(ToDoList.dateFormat.format(task.getDate()));
            fileWriter.write((char) 294);   //разделитель
            fileWriter.write(task.getDescription());
            fileWriter.write((char) 294);   //разделитель
            fileWriter.write(task.getStatus());
            fileWriter.write((char) 294);
        }
        fileWriter.close();
    }
}