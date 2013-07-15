package mage.cards.repository;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author North
 */
public enum ExpansionRepository {

    instance;

    private static final String JDBC_URL = "jdbc:sqlite:db/cards.db";
    private static final String VERSION_ENTITY_NAME = "expansion";
    private static final long EXPANSION_DB_VERSION = 2;

    private Dao<ExpansionInfo, Object> expansionDao;

    private ExpansionRepository() {
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
        }
    }

    public void add(ExpansionInfo expansion) {
        try {
            expansionDao.create(expansion);
        } catch (SQLException e) {
        }
    }

    public List<String> getSetCodes() {
        List<String> setCodes = new ArrayList<String>();
        try {
            List<ExpansionInfo> expansions = expansionDao.queryForAll();
            for (ExpansionInfo expansion : expansions) {
                setCodes.add(expansion.getCode());
            }
        } catch (SQLException ex) {
        }
        return setCodes;
    }

    public ExpansionInfo[] getWithBoostersSortedByReleaseDate() {
        ExpansionInfo[]  sets = new ExpansionInfo[0];
        try {
            QueryBuilder<ExpansionInfo, Object> qb = expansionDao.queryBuilder();
            qb.orderBy("releaseDate", false);
            qb.where().eq("boosters", new SelectArg(true));
            List<ExpansionInfo> expansions = expansionDao.query(qb.prepare());
            sets = expansions.toArray(new ExpansionInfo[0]);
        } catch (SQLException ex) {
        }
        return sets;
    }

    public List<ExpansionInfo> getSetsWithBasicLandsByReleaseDate() {
        List<ExpansionInfo> sets = new LinkedList<ExpansionInfo>();
        try {
            QueryBuilder<ExpansionInfo, Object> qb = expansionDao.queryBuilder();
            qb.orderBy("releaseDate", false);
            qb.where().eq("basicLands", new SelectArg(true));
            sets = expansionDao.query(qb.prepare());
        } catch (SQLException ex) {
        }
        return sets;
    }
    
    public ExpansionInfo[] getSetsFromBlock(String blockName) {
        ExpansionInfo[]  sets = new ExpansionInfo[0];
        try {
            QueryBuilder<ExpansionInfo, Object> qb = expansionDao.queryBuilder();
            qb.where().eq("blockName", new SelectArg(blockName));
            List<ExpansionInfo> expansions = expansionDao.query(qb.prepare());
            sets = expansions.toArray(new ExpansionInfo[0]);
        } catch (SQLException ex) {
        }
        return sets;
    }

    public ExpansionInfo getSetByCode(String setCode) {
        ExpansionInfo set = null;
        try {
            QueryBuilder<ExpansionInfo, Object> qb = expansionDao.queryBuilder();
            qb.where().eq("code", new SelectArg(setCode));
            List<ExpansionInfo> expansions = expansionDao.query(qb.prepare());
            if (expansions.size() > 0) {
                set = expansions.get(0);
            }
        } catch (SQLException ex) {
        }
        return set;        
    }

    public List<ExpansionInfo> getAll() {
        try {
            return expansionDao.queryForAll();
        } catch (SQLException ex) {
        }
        return new ArrayList<ExpansionInfo>();
    }
}
