package com.xmage.ws.representer;

import com.xmage.ws.resource.Resource;
import net.minidev.json.JSONObject;

/**
 * Now we have only JSON based representation.
 *
 * @author noxx
 */
public interface Representer<R> {

    JSONObject toJSON(Resource<R> resource);
}
