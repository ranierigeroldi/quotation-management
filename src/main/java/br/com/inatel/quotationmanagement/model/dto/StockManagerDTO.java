package br.com.inatel.quotationmanagement.model.dto;

public class StockManagerDTO {

    private String id;
    private String description;

    public StockManagerDTO() {}

    public StockManagerDTO(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

}
