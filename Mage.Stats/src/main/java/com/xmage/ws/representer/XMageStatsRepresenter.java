package com.xmage.ws.representer;

import com.xmage.core.entity.model.ServerStats;
import com.xmage.ws.json.XMageStatsJSONBuilder;
import com.xmage.ws.resource.Resource;
import net.minidev.json.JSONObject;

/**
 *
 * @author noxx
 */
public class XMageStatsRepresenter implements Representer<ServerStats> {

    public XMageStatsRepresenter() {
    }

    public JSONObject toJSON(Resource<ServerStats> resource) {
        return XMageStatsJSONBuilder.getInstance().buildFrom(resource);
    }
}
