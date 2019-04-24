package mage.cards.repository;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author North
 */
public enum ExpansionRepository {

    instance;

    private static final Logger logger = Logger.getLogger(ExpansionRepository.class);

    private static final String JDBC_URL = "jdbc:h2:file:./db/cards.h2;AUTO_SERVER=TRUE";
    private static final String VERSION_ENTITY_NAME = "expansion";
    private static final long EXPANSION_DB_VERSION = 5;
    private static final long EXPANSION_CONTENT_VERSION = 17;

    private Dao<ExpansionInfo, Object> expansionDao;

    ExpansionRepository() {
        File file = new File("db");
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            ConnectionSource connectionSource = new JdbcConnectionSource(JDBC_URL);
            boolean obsolete = RepositoryUtil.isDatabaseObsolete(connectionSource, VERSION_ENTITY_NAME, EXPANSION_DB_VERSION);

            if (obsolete) {
                TableUtils.dropTable(connectionSource, ExpansionInfo.class, true);
            }

            TableUtils.createTableIfNotExists(connectionSource, ExpansionInfo.class);
            expansionDao = DaoManager.createDao(connectionSource, ExpansionInfo.class);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void add(ExpansionInfo expansion) {
        try {
            expansionDao.create(expansion);
        } catch (SQLException ex) {
            logger.error(ex);
        }
    }

    public void update(ExpansionInfo expansion) {
        try {
            expansionDao.update(expansion);
        } catch (SQLException ex) {
            logger.error(ex);
        }
    }

    public List<String> getSetCodes() {
        List<String> setCodes = new ArrayList<>();
        try {
            List<ExpansionInfo> expansions = expansionDao.queryForAll();
            for (ExpansionInfo expansion : expansions) {
                setCodes.add(expansion.getCode());
            }
        } catch (SQLException ex) {
            logger.error("Can't get the expansion set codes from database.", ex);
            return setCodes;
        }
        return setCodes;
    }

    public ExpansionInfo[] getWithBoostersSortedByReleaseDate() {

        try {
            // only with boosters and cards
            GenericRawResults<ExpansionInfo> setsList = expansionDao.queryRaw(
                    "select * from expansion e "
                    + " where e.boosters = 1 "
                    + "   and exists(select (1) from  card c where c.setcode = e.code) "
                    + " order by e.releasedate desc",
                    expansionDao.getRawRowMapper());

            List<ExpansionInfo> resList = new ArrayList<>();
            for (ExpansionInfo info : setsList) {
                resList.add(info);
            }
            return resList.toArray(new ExpansionInfo[0]);

        } catch (SQLException ex) {
            logger.error(ex);
            return new ExpansionInfo[0];
        }
    }

    public List<ExpansionInfo> getSetsWithBasicLandsByReleaseDate() {
        List<ExpansionInfo> sets = new LinkedList<>();
        try {
            QueryBuilder<ExpansionInfo, Object> qb = expansionDao.queryBuilder();
            qb.orderBy("releaseDate", false);
            qb.where().eq("basicLands", new SelectArg(true));
            sets = expansionDao.query(qb.prepare());
        } catch (SQLException ex) {
            logger.error(ex);
        }
        return sets;
    }

    public List<ExpansionInfo> getSetsFromBlock(String blockName) {
        List<ExpansionInfo> sets = new LinkedList<>();
        try {
            QueryBuilder<ExpansionInfo, Object> qb = expansionDao.queryBuilder();
            qb.where().eq("blockName", new SelectArg(blockName));
            return expansionDao.query(qb.prepare());
        } catch (SQLException ex) {
            logger.error(ex);
        }
        return sets;
    }

    public ExpansionInfo getSetByCode(String setCode) {
        ExpansionInfo set = null;
        try {
            QueryBuilder<ExpansionInfo, Object> qb = expansionDao.queryBuilder();
            qb.limit(1L).where().eq("code", new SelectArg(setCode));
            List<ExpansionInfo> expansions = expansionDao.query(qb.prepare());
            if (!expansions.isEmpty()) {
                set = expansions.get(0);
            }
        } catch (SQLException ex) {
            logger.error(ex);
        }
        return set;
    }

    public ExpansionInfo getSetByName(String setName) {
        ExpansionInfo set = null;
        try {
            QueryBuilder<ExpansionInfo, Object> qb = expansionDao.queryBuilder();
            qb.limit(1L).where().eq("name", new SelectArg(setName));
            List<ExpansionInfo> expansions = expansionDao.query(qb.prepare());
            if (!expansions.isEmpty()) {
                set = expansions.get(0);
            }
        } catch (SQLException ex) {
            logger.error(ex);
        }
        return set;
    }

    public List<ExpansionInfo> getAll() {
        try {
            QueryBuilder<ExpansionInfo, Object> qb = expansionDao.queryBuilder();
            qb.orderBy("releaseDate", true);
            return expansionDao.query(qb.prepare());
        } catch (SQLException ex) {
            logger.error(ex);
        }
        return Collections.emptyList();
    }

    public List<String> getAllSetNames() {
        try {
            QueryBuilder<ExpansionInfo, Object> qb = expansionDao.queryBuilder();
            qb.orderBy("releaseDate", true);
            List<ExpansionInfo> expansions = expansionDao.query(qb.prepare());
            List<String> setNames = new LinkedList<>();
            for (ExpansionInfo expansionInfo : expansions) {
                setNames.add(expansionInfo.getName());
            }
            return setNames;
        } catch (SQLException ex) {
            logger.error(ex);
        }
        return Collections.emptyList();
    }

    public long getContentVersionFromDB() {
        try {
            ConnectionSource connectionSource = new JdbcConnectionSource(JDBC_URL);
            return RepositoryUtil.getDatabaseVersion(connectionSource, VERSION_ENTITY_NAME + "Content");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public void setContentVersion(long version) {
        try {
            ConnectionSource connectionSource = new JdbcConnectionSource(JDBC_URL);
            RepositoryUtil.updateVersion(connectionSource, VERSION_ENTITY_NAME + "Content", version);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public long getContentVersionConstant() {
        return EXPANSION_CONTENT_VERSION;
    }
}
