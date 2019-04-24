package com.xmage.ws.rest.services.base;

import com.xmage.ws.json.ResponseBuilder;
import com.xmage.ws.model.DomainErrors;
import com.xmage.ws.resource.Resource;
import net.minidev.json.JSONObject;
import org.apache.sling.commons.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;

/**
 * General approach for ws requests/responses.
 *
 * Consists of building response object, verifying response, prettifying, execution time calculating.
 *
 * @author noxx
 */
public abstract class AbstractService {

    private static final Logger logger = LoggerFactory.getLogger(AbstractService.class);

    /**
     * Create {@link Response} from {@link com.xmage.ws.resource.Resource}
     *
     * @param resource Resource to build response based on
     * @return
     */
    public final Response responseWithError(Resource resource) {
        long t1 = System.currentTimeMillis();
        JSONObject response = buildResponse(resource);
        response = verifyResponse(response);
        String json = prettifyResponse(response);

        Response responseObject = Response.status(200).entity(json).build();
        long t2 = System.currentTimeMillis();
        logger.info("responseWithError time: " + (t2 - t1) + "ms");
        return responseObject;
    }

    private JSONObject buildResponse(Resource resource) {
        JSONObject response = null;
        try {
            response = ResponseBuilder.build(resource);
        } catch (Exception e) {
            logger.error("responseWithError: ", e);

        }

        return response;
    }

    private String prettifyResponse(JSONObject response) {
        String json = response.toJSONString();

        try {
            json = new org.apache.sling.commons.json.JSONObject(json).toString(1);
        } catch (JSONException jse) {
            jse.printStackTrace();
        }

        return json;
    }

    private JSONObject verifyResponse(JSONObject response) {
        if (response == null) {
            logger.error("Something bad happened on response creation");
            response = ResponseBuilder.build(DomainErrors.Errors.STATUS_SERVER_ERROR.getCode());
        }
        return response;
    }
}
