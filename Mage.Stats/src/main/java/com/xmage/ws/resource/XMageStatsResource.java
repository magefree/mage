package com.xmage.ws.resource;

import com.xmage.core.entity.model.ServerStats;
import com.xmage.core.entity.repositories.XMageStatsRepository;
import com.xmage.core.entity.repositories.impl.XMageStatsRepositoryImpl;
import com.xmage.ws.model.DomainErrors;
import com.xmage.ws.representer.XMageStatsRepresenter;
import com.xmage.ws.representer.Representer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XMageStatsResource extends DefaultResource<ServerStats> {

    private static final Logger logger = LoggerFactory.getLogger(XMageStatsResource.class);

    private static XMageStatsRepository xmageStatsRepository = new XMageStatsRepositoryImpl();

    private static final Representer<ServerStats> defaultRepresenter = new XMageStatsRepresenter();

    public XMageStatsResource() {
        super(defaultRepresenter);
    }

    public XMageStatsResource(ServerStats event) {
        super(defaultRepresenter);
        defaultResource = event;
    }

    public Resource getAll() {
        try {
            ServerStats serverStats = xmageStatsRepository.getServerStats();
            if (serverStats != null) {
                defaultResource = serverStats;
            } else {
                error = DomainErrors.Errors.STATUS_NOT_FOUND;
            }
        } catch (Exception e) {
            logger.error("Getting server stats error:", e);
            error = DomainErrors.Errors.STATUS_SERVER_ERROR;
        }

        return this;
    }

    @Override
    public String getName() {
        return "serverStats";
    }


}
