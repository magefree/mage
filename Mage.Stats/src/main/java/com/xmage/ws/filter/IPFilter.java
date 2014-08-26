package com.xmage.ws.filter;

import com.xmage.ws.util.IPHolderUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Filter gets ip address and user agent and stores it using {@link com.xmage.ws.util.IPHolderUtil}
 *
 * @author noxx
 */
public class IPFilter implements Filter {

    private FilterConfig config;

    public IPFilter() {}

    public void init(FilterConfig filterConfig) throws ServletException {
        this.config = filterConfig;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        String ip = request.getRemoteAddr();
        IPHolderUtil.rememberIP(ip);

        if (request instanceof HttpServletRequest) {
            HttpServletRequest req = (HttpServletRequest) request;
            String uaString = req.getHeader("User-Agent");
            IPHolderUtil.rememberUserAgent(uaString);
        }

        chain.doFilter(request, response);

    }// doFilter

    public void destroy() {
        /*
        * called before the Filter instance is removed from service by the web
        * container
        */
    }

}
