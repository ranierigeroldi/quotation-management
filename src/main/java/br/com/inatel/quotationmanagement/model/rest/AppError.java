package br.com.inatel.quotationmanagement.model.rest;

public class AppError {

    private final String error;

    public AppError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

}
