package com.xmage.ws.json;

import com.xmage.core.entity.model.ServerStats;
import com.xmage.ws.resource.Resource;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;

/**
 * Converts {@link com.xmage.core.entity.model.ServerStats} resource to json.
 *
 * @author noxx
 */
public class XMageStatsJSONBuilder implements JSONBuilder<ServerStats> {

    private static final Logger logger = LoggerFactory.getLogger(XMageStatsJSONBuilder.class);

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

    static final class StaticHolder {
        static XMageStatsJSONBuilder instance = new XMageStatsJSONBuilder();
    }

    public static XMageStatsJSONBuilder getInstance() {
        return StaticHolder.instance;
    }

    public JSONObject buildFrom(Resource<ServerStats> resource) {
        
        ServerStats serverStats = resource.getDefault();

        JSONObject statsJson = new JSONObject();

        statsJson.put("numberOfGamesPlayed", serverStats.getNumberOfGamesPlayed());
        statsJson.put("numberOfUniquePlayers", serverStats.getNumberOfUniquePlayers());
        statsJson.put("numberOfPlayersPlayedOnlyOnce", serverStats.getNumberOfPlayersPlayedOnce());
        statsJson.put("top3Players", serverStats.getTop3Players());

        return statsJson;
    }

}
