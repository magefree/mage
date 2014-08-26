package com.xmage.ws.json;

import com.xmage.core.entity.model.EntityModel;
import com.xmage.ws.resource.Resource;
import net.minidev.json.JSONObject;

/**
 * Converts {@link com.xmage.core.entity.model.EntityModel} to json.
 *
 * @author noxx
 */
public interface JSONBuilder<R extends EntityModel> {

    JSONObject buildFrom(Resource<R> resource);
}
