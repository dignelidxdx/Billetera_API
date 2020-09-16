package ar.com.billetera.api.models.response;

public class ErrorResponseItem {
    
    public ErrorResponseItem(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String field;
    public String message;

}
