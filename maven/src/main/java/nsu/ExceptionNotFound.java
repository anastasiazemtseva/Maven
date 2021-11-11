package nsu;

public class ExceptionNotFound extends Exception{
    @Override
    public void printStackTrace() {
        System.out.println("\nДанные о курсе валюты не найдены.");
    }
}