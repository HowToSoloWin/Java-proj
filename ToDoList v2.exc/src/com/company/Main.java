package com.company;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;

// v2.0 ln
public class Main {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        ToDoList toDoList = new ToDoList();
        System.out.println("\nWelcome to ToDoList\n");
        try {
            toDoList.start();
        } catch (IOException | ParseException e) {
            System.out.println("Ошибка чтения из файла");
        }catch (Exception e){
            e.printStackTrace();
        }
        while (true) {
            try {
                toDoList.mainMenu();
            } catch (NumberFormatException e) {
                System.out.println("Введенный номер неверен.");
            } catch (ParseException e) {
                System.out.println("Введенная дата неверна.");
            } catch (IOException e) {
                System.out.println("Ошибка записи в файл");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
