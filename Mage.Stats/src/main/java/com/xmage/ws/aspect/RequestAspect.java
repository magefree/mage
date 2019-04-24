package com.xmage.ws.aspect;

import com.xmage.ws.json.ResponseBuilder;
import com.xmage.ws.model.DomainErrors;
import com.xmage.ws.resource.ErrorResource;
import com.xmage.ws.resource.Resource;
import com.xmage.ws.util.IPHolderUtil;
import net.minidev.json.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;

/**
 * Base aspect for getting request metadata
 *
 * @author noxx
 */
@Aspect
public class RequestAspect {

    private static final Logger logger = LoggerFactory.getLogger(RequestAspect.class);

    @Around("execution(* *(..)) && within(com.xmage.ws.rest.services.*)")
    public Object advice(ProceedingJoinPoint pjp) throws Throwable {

        try {
            String ip = IPHolderUtil.getRememberedIP();
            String userAgent = IPHolderUtil.getRememberedUserAgent();
            logger.info("ip: " + ip + ", user-agent: " + userAgent);

            return pjp.proceed();
        } catch (Exception e) {
            logger.error("Error: ", e);
        }

        Resource resource = new ErrorResource(DomainErrors.Errors.STATUS_SERVER_ERROR, "server_error");
        JSONObject serverError = ResponseBuilder.build(resource);

        return Response.status(200).entity(serverError.toJSONString()).build();
	}
    

}