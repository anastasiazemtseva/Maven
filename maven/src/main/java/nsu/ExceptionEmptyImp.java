package nsu;

public class ExceptionEmptyImp extends Exception{
    @Override
    public void printStackTrace() {
        System.out.println("\nНет данных о валюте и дате.\n");
    }
}
