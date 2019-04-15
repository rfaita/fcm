package com.product.fcm.server.interceptor;

import com.product.fcm.util.TenantContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class TenantOrganizationInteceptor implements HandlerInterceptor {

    private static final String PARAM_TENANT = "organizationId";

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) {


        final Map<String, String> pathVariables =
                (Map<String, String>) request
                        .getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        String dado = null;

        if (pathVariables != null && pathVariables.get(PARAM_TENANT) != null) {
            dado = String.valueOf(pathVariables.get(PARAM_TENANT));
        } else if (request.getHeader(PARAM_TENANT) != null) {
            dado = request.getHeader(PARAM_TENANT);
        }

        if (dado != null) {

            TenantContext.setTenantId(dado);

        }
        return true;


    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) {
        TenantContext.clear();
    }

}