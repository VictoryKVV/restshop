package helpers;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class BaseResponse<T extends Serializable> implements Serializable {

    @JsonProperty("method")
    private String method;

    @JsonProperty("status")
    private String status;

    @JsonProperty("field_error")
    private String fieldError;

    @JsonProperty("error")
    private String error;

    @JsonProperty("message")
    private String message;

    public String getMessage() {
        return message;
    }

    @JsonProperty("result")
    private T result;

    public T getResults() {
        return result;
    }

    public String getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

}
