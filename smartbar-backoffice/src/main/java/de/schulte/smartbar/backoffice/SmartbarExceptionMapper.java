package de.schulte.smartbar.backoffice;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.hibernate.exception.ConstraintViolationException;

@Provider
public class SmartbarExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        if(causedByConstaintViolation(exception)) {
            return Response.status(Response.Status.CONFLICT).build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    private static boolean causedByConstaintViolation(Exception exception) {
        Throwable cause = exception.getCause();
        while (cause != null) {
            if(cause instanceof ConstraintViolationException) {
                return true;
            }
            cause = cause.getCause();
        }
        return false;
    }
}
