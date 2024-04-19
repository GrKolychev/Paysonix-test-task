package com.gr.kolychev.signatureproviderservice.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gr.kolychev.signatureproviderservice.model.GenericResponse;
import com.gr.kolychev.signatureproviderservice.model.RequestStatus;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ConfigurationProperties(prefix = "security.authentication")
@Setter
@Component
public class SignatureInterceptor implements HandlerInterceptor {

    private static final String HEADER_TOKEN = "Token";
    private static final String INVALID_HEADER_TOKEN_MESSAGE = "Invalid or missing Token header";
    private static final String MISSING_PARAMETERS_MESSAGE = "Missing request parameters";

    private String validToken;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        var token = request.getHeader(HEADER_TOKEN);

        if (StringUtils.isBlank(token) || !token.equals(validToken)) {
            constructFailedResponse(response, HttpServletResponse.SC_FORBIDDEN,
                    RequestStatus.FAILED, INVALID_HEADER_TOKEN_MESSAGE);
            return false;
        } else if (request.getParameterMap().isEmpty()) {
            constructFailedResponse(response, HttpServletResponse.SC_BAD_REQUEST,
                    RequestStatus.ERROR, MISSING_PARAMETERS_MESSAGE);
            return false;
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private void constructFailedResponse(HttpServletResponse response, int responseStatus,
                                         RequestStatus genericResponseStatus, String resultMessage) throws IOException {
        response.setStatus(responseStatus);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        var failedResponse = GenericResponse.<String>builder()
                .status(genericResponseStatus)
                .result(resultMessage)
                .build();
        ObjectMapper mapper = new ObjectMapper();
        var writer = response.getWriter();
        writer.write(mapper.writeValueAsString(failedResponse));
        writer.flush();
    }
}
