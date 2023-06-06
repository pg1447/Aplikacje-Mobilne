package pl.app.newton.data;


import com.google.gson.annotations.SerializedName;

public class ApiResponse {
    @SerializedName("operation")
    private final String operation;
    @SerializedName("expression")
    private final String expression;
    @SerializedName("result")
    private final String result;

    public ApiResponse(String operation, String expression, String result) {
        this.operation = operation;
        this.expression = expression;
        this.result = result;
    }

    public String getOperation() {
        return operation;
    }

    public String getExpression() {
        return expression;
    }

    public String getResult() {
        return result;
    }
}
