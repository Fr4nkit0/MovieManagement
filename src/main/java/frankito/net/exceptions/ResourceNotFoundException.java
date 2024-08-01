package frankito.net.exceptions;

public class ResourceNotFoundException extends RuntimeException{
    private final String resourceNotFoundName;
    private final Throwable cause;

    public ResourceNotFoundException ( Throwable cause, String resourceNotFoundName) {
        this.resourceNotFoundName = resourceNotFoundName;
        this.cause = cause;
    }

    public ResourceNotFoundException( String resourceNotFoundName) {
        this.resourceNotFoundName = resourceNotFoundName;
        this.cause=null;
    }
    @Override
    public String getMessage() {

        String message = super.getMessage();

        if(message == null){
            message = "";
        }

        return message
                .concat("(object not found: ")
                .concat(this.resourceNotFoundName).concat(")");
    }

    public String getObjectNotFoundName() {
        return resourceNotFoundName;
    }
}
