package ${package}.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController
@RequestMapping("/error")
public class DefaultErrorController implements ErrorController {

	private final ErrorAttributes errorAttributes;

	@Autowired
	public DefaultErrorController(ErrorAttributes errorAttributes) {

		this.errorAttributes = errorAttributes;
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}

	@RequestMapping
	public Map<String, Object> error(HttpServletRequest aRequest) {
		Map<String, Object> body = getErrorAttributes(aRequest);
		return body;
	}

	private Map<String, Object> getErrorAttributes(HttpServletRequest req) {
		RequestAttributes requestAttributes = new ServletRequestAttributes(req);
		return errorAttributes.getErrorAttributes(requestAttributes, false);
	}

}
