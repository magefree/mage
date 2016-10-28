package com.xmage.ws.resource;

import com.xmage.core.decorators.Decorator;
import com.xmage.ws.model.DomainErrors;
import com.xmage.ws.representer.Representer;
import java.util.ArrayList;
import net.minidev.json.JSONObject;

/**
 *
 * @author noxx
 */
public abstract class DefaultResource<R> implements Resource<R> {

    protected DomainErrors.Errors error = DomainErrors.Errors.STATUS_OK;

    protected R defaultResource;

    protected Representer<R> representer;

    protected java.util.List<Decorator> decorators = new ArrayList<>();

    protected int version;

    protected DefaultResource(Representer<R> representer) {
        this.representer = representer;
    }

    @Override
    public int getError() {
        return error.getCode();
    }

    @Override
    public R getDefault() {
        return defaultResource;
    }

    @Override
    public java.util.List<Decorator> getDecorators() {
        return decorators;
    }

    @Override
    public void addDecorator(Decorator decorator) {
        if (decorator != null) {
            this.decorators.add(decorator);
        }
    }

    @Override
    public JSONObject getJSONBody() {
        return representer.toJSON(this);
    }

    @Override
    public String getErrorMessage() {
        return error.getMessage();
    }

    public int getVersion() {
        return version;
    }
}
