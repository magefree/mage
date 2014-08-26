package com.xmage.ws.representer;

import com.xmage.ws.model.SimpleResponse;
import com.xmage.ws.resource.Resource;
import net.minidev.json.JSONObject;

/**
 * This is useful when we have {@link SimpleResponse}
 *
 * @author noxx
 */
public class SimpleResponseRepresenter implements Representer<SimpleResponse> {

    public JSONObject toJSON(Resource<SimpleResponse> resource) {
        SimpleResponse response = resource.getDefault();

        JSONObject json = new JSONObject();
        json.put("code", response.getCode());
        json.put("message", response.getMessage());

        return json;
    }

}
