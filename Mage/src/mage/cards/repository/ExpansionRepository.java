package mage.cards.repository;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author North
 */
public enum ExpansionRepository {

    instance;

    private static final String JDBC_URL = "jdbc:sqlite:db/cards.db";
    private static final String VERSION_ENTITY_NAME = "expansion";
    private static final long EXPANSION_DB_VERSION = 1;

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

    public List<ExpansionInfo> getAll() {
        try {
            return expansionDao.queryForAll();
        } catch (SQLException ex) {
        }
        return new ArrayList<ExpansionInfo>();
    }
}
