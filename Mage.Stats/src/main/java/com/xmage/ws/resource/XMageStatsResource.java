package com.xmage.ws.resource;

import com.xmage.core.entity.model.ServerStats;
import com.xmage.ws.model.DomainErrors;
import com.xmage.ws.representer.XMageStatsRepresenter;
import com.xmage.ws.representer.Representer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XMageStatsResource extends DefaultResource<ServerStats> {

    private static final Logger logger = LoggerFactory.getLogger(XMageStatsResource.class);

    private static final Representer<ServerStats> defaultRepresenter = new XMageStatsRepresenter();

    public XMageStatsResource() {
        super(defaultRepresenter);
    }

    public Resource getAll() {
        error = DomainErrors.Errors.STATUS_NOT_FOUND;
        return this;
    }

    @Override
    public String getName() {
        return "serverStats";
    }


}
