package mage.cards.repository;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author North
 */
public class RepositoryUtil {

    public static boolean isDatabaseObsolete(ConnectionSource connectionSource, String entityName, long version) throws SQLException {
        TableUtils.createTableIfNotExists(connectionSource, DatabaseVersion.class);
        Dao<DatabaseVersion, Object> dbVersionDao = DaoManager.createDao(connectionSource, DatabaseVersion.class);

        QueryBuilder<DatabaseVersion, Object> queryBuilder = dbVersionDao.queryBuilder();
        queryBuilder.where().eq("entity", new SelectArg(entityName)).and().eq("version", version);
        List<DatabaseVersion> dbVersions = dbVersionDao.query(queryBuilder.prepare());

        if (dbVersions.isEmpty()) {
            DatabaseVersion dbVersion = new DatabaseVersion();
            dbVersion.setEntity(entityName);
            dbVersion.setVersion(version);
            dbVersionDao.create(dbVersion);
        }
        return dbVersions.isEmpty();
    }
}
