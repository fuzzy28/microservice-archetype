#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.controller;

import ${package}.dto.FieldErrorDTO;
import ${package}.dto.RestErrorDTO;
import ${package}.dto.ValidationErrorDTO;
import ${package}.exception.${domainName}IdNotConsistentException;
import ${package}.exception.${domainName}NotFoundException;
import ${package}.exception.${domainName}NotPersistedException;
import ${package}.service.MessageLocalizer;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * The ExceptionHandlerController class is a global exception handler for all
 * controllers.
 * 
 * @author ${author}
 * @since 1.0
 */

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageLocalizer localizer;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
	    final MethodArgumentNotValidException ex, final HttpHeaders headers,
	    final HttpStatus status, final WebRequest request) {
	ValidationErrorDTO validationErrorDTO = new ValidationErrorDTO();
	for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
	    final FieldErrorDTO fe = new FieldErrorDTO(
		    fieldError.getField(),
		    localizer.localizeMessage(fieldError.getDefaultMessage()));
	    validationErrorDTO.addFieldError(fe);
	}
	return handleExceptionInternal(ex, validationErrorDTO, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
	    final HttpMessageNotReadableException ex, final HttpHeaders headers,
	    final HttpStatus status, final WebRequest request) {
	RestErrorDTO restErrorDTO = new RestErrorDTO(
		System.currentTimeMillis(),
		status,
		localizer.localizeMessage("err.rest.messagenotreadable.error"),
		localizer.localizeMessage("err.rest.messagenotreabable.message"));

	return handleExceptionInternal(ex, restErrorDTO, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
	    final HttpMediaTypeNotSupportedException ex, final HttpHeaders headers,
	    final HttpStatus status, final WebRequest request) {
	final StringBuilder message = new StringBuilder(
		localizer.localizeMessage("err.rest.unsupportedmediatype.message"));
	ex.getSupportedMediaTypes().forEach(m -> message.append(m + " "));

	final RestErrorDTO restErrorDTO = new RestErrorDTO(
		System.currentTimeMillis(),
		status,
		localizer.localizeMessage("err.rest.unsupportedmediatype.error"),
		trimStringBuilder(message));

	return new ResponseEntity<Object>(restErrorDTO, new HttpHeaders(), status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
	    final HttpRequestMethodNotSupportedException ex, final HttpHeaders headers,
	    final HttpStatus status, final WebRequest request) {
	final StringBuilder message = new StringBuilder(
		localizer.localizeMessage("err.rest.methodnotsupported.message"));
	ex.getSupportedHttpMethods().forEach(m -> message.append(m).append(" "));

	final RestErrorDTO restErrorDTO = new RestErrorDTO(
		System.currentTimeMillis(),
		status,
		localizer.localizeMessage("err.rest.methodnotsupported.error"),
		trimStringBuilder(message));

	return new ResponseEntity<Object>(restErrorDTO, new HttpHeaders(), status);
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAllException(final Exception ex,
	    final WebRequest request) {

	final RestErrorDTO restErrorDTO = new RestErrorDTO(
		System.currentTimeMillis(),
		HttpStatus.INTERNAL_SERVER_ERROR,
		localizer.localizeMessage("err.rest.internalservererror.error"),
		localizer.localizeMessage("err.rest.internalservererror.message"));
	return new ResponseEntity<Object>(
		restErrorDTO,
		new HttpHeaders(),
		HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
	    final NoHandlerFoundException ex, final HttpHeaders headers,
	    final HttpStatus status, final WebRequest request) {

	final StringBuilder message = new StringBuilder(
		localizer.localizeMessage("err.rest.notfound.message"))
			.append(ex.getHttpMethod() + " ")
			.append(ex.getRequestURL());

	final RestErrorDTO restErrorDTO = new RestErrorDTO(
		System.currentTimeMillis(),
		HttpStatus.NOT_FOUND,
		localizer.localizeMessage("err.rest.notfound.error"),
		message.toString());

	return new ResponseEntity<Object>(
		restErrorDTO,
		new HttpHeaders(),
		HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex,
	    final HttpHeaders headers, final HttpStatus status,
	    final WebRequest request) {

	final String error = String.format(
		localizer.localizeMessage("err.rest.typemismatch.message"),
		ex.getValue(),
		ex.getRequiredType().getSimpleName());

	final RestErrorDTO restErrorDTO = new RestErrorDTO(
		System.currentTimeMillis(),
		status,
		localizer.localizeMessage("err.rest.typemismatch.error"),
		error);
	return new ResponseEntity<Object>(restErrorDTO, headers, status);
    }

    @ExceptionHandler(${domainName}NotFoundException.class)
    public ResponseEntity<RestErrorDTO> handle${domainName}NotFound(
	    final ${domainName}NotFoundException dnf) {

	return handleCustomException(
		HttpStatus.NOT_FOUND,
		"err.rest.notfound.error",
		"err.ctrl.departmentnotfound.message");
    }

    @ExceptionHandler(${domainName}NotPersistedException.class)
    public ResponseEntity<RestErrorDTO> handle${domainName}NotPersisted(
	    final ${domainName}NotPersistedException dnf) {

	return handleCustomException(
		HttpStatus.BAD_REQUEST,
		"err.rest.badrequest.error",
		"err.ctrl.departmentnotpersisted.message");
    }

    @ExceptionHandler(${domainName}IdNotConsistentException.class)
    public ResponseEntity<RestErrorDTO> handle${domainName}NotPersisted(
	    final ${domainName}IdNotConsistentException dnf) {

	return handleCustomException(
		HttpStatus.BAD_REQUEST,
		"err.rest.badrequest.error",
		"err.ctrl.departmentidnotconsistent.message");
    }

    public ResponseEntity<RestErrorDTO> handleCustomException(HttpStatus status,
	    String error, String message) {

	final RestErrorDTO restErrorDTO = new RestErrorDTO(
		System.currentTimeMillis(),
		status,
		localizer.localizeMessage(error),
		localizer.localizeMessage(message));
	return new ResponseEntity<RestErrorDTO>(restErrorDTO, restErrorDTO.getStatus());
    }

    private String trimStringBuilder(StringBuilder sb) {
	return sb.substring(0, sb.length() - 1);
    }
}
