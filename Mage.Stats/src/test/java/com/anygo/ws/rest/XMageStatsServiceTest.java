package com.anygo.ws.rest;

import com.xmage.ws.model.DomainErrors;
import com.xmage.ws.rest.services.XMageStatsService;
import com.xmage.ws.util.json.JSONParser;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Response;

/**
 * Testings XMage stats service without need to deploy.
 *
 * @author noxx
 */
public class XMageStatsServiceTest {

    @Test
    public void testAddNewAndGet() throws Exception {

        XMageStatsService xMageStatsService = new XMageStatsService();

        Response response = xMageStatsService.getAllStats();

        JSONParser parser = new JSONParser();
        parser.parseJSON((String) response.getEntity());

        Assert.assertEquals(DomainErrors.Errors.STATUS_NOT_FOUND.getCode(), parser.getInt("code"));
        System.out.println("response = " + response.getEntity().toString());
    }


}
