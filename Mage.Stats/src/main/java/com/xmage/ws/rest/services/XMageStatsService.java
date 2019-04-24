package com.xmage.ws.rest.services;

import com.xmage.ws.resource.XMageStatsResource;
import com.xmage.ws.resource.Resource;
import com.xmage.ws.rest.services.base.AbstractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 *
 * @author noxx
 */
@Path("/xmage/stats")
@Produces("application/json;charset=utf-8")
public class XMageStatsService extends AbstractService {

    static final Logger logger = LoggerFactory.getLogger(XMageStatsService.class);

    @GET
    @Path("/getAll")
    public Response getAllStats() {
        logger.trace("getAllStats");
        Resource resource = new XMageStatsResource().getAll();

        return responseWithError(resource);
    }


}
