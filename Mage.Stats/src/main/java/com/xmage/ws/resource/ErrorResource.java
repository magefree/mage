package com.xmage.ws.resource;

import com.xmage.ws.model.DomainErrors;

/**
 *
 * @author noxx
 */
public class ErrorResource extends DefaultResource {

    private String name;

    public ErrorResource(DomainErrors.Errors error, String name) {
        super(null);
        this.name = name;
        this.error = error;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
