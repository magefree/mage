package com.xmage.core.entity.repositories;

import com.xmage.core.entity.model.ServerStats;

/**
 * Repository interface for XMage server stats.
 *
 * Responsible for fetching stats information.
 *
 * @author noxx
 */
public interface XMageStatsRepository {

    ServerStats getServerStats();

}
