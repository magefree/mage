package com.xmage.ws.json;

import com.xmage.ws.model.DomainErrors;
import com.xmage.ws.resource.Resource;
import net.minidev.json.JSONObject;

public final class ResponseBuilder {

    public static JSONObject build(int code) {
        JSONObject response = new JSONObject();
        response.put("code", code);

        return response;
    }

    public static JSONObject build(int code, String name, JSONObject jsonObject) {
        JSONObject response = new JSONObject();
        response.put("code", code);
        response.put(name, jsonObject);

        return response;
    }

    public static JSONObject build(Resource resource) {
        if (resource.getError() != DomainErrors.Errors.STATUS_OK.getCode()) {
            JSONObject response = ResponseBuilder.build(resource.getError());
            response.put("message", resource.getErrorMessage());
            return response;
        } else {
            JSONObject json = resource.getJSONBody();
            return ResponseBuilder.build(DomainErrors.Errors.STATUS_OK.getCode(), resource.getName(), json);
        }

    }

}
