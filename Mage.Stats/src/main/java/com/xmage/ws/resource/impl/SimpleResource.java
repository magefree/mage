package com.xmage.ws.resource.impl;

import com.xmage.ws.model.SimpleResponse;
import com.xmage.ws.representer.SimpleResponseRepresenter;
import com.xmage.ws.resource.DefaultResource;

public class SimpleResource extends DefaultResource<SimpleResponse> {

    public SimpleResource() {
        super(new SimpleResponseRepresenter());
    }

    @Override
    public String getName() {
        return "simple";
    }
}
