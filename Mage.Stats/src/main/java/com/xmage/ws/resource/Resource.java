package com.xmage.ws.resource;

import com.xmage.core.decorators.Decorator;
import net.minidev.json.JSONObject;

/**
 *
 * @author noxx
 */
public interface Resource<R> {

    int getError();

    String getErrorMessage();

    String getName();

    JSONObject getJSONBody();

    R getDefault();

    java.util.List<Decorator> getDecorators();

    void addDecorator(Decorator decorator);
}
