package mage.server.record;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.table.TableUtils;
import java.io.File;
import java.sql.SQLException;
import java.util.List;
import mage.cards.repository.RepositoryUtil;
import org.apache.log4j.Logger;

public enum UserStatsRepository {

    instance;

    private static final String JDBC_URL = "jdbc:sqlite:./db/user_stats.db";
    private static final String VERSION_ENTITY_NAME = "user_stats";
    // raise this if db structure was changed
    private static final long DB_VERSION = 0;

    private Dao<UserStats, Object> dao;

    private UserStatsRepository() {
        File file = new File("db");
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            ConnectionSource connectionSource = new JdbcConnectionSource(JDBC_URL);
            boolean obsolete = RepositoryUtil.isDatabaseObsolete(connectionSource, VERSION_ENTITY_NAME, DB_VERSION);

            if (obsolete) {
                TableUtils.dropTable(connectionSource, UserStats.class, true);
            }

            TableUtils.createTableIfNotExists(connectionSource, UserStats.class);
            dao = DaoManager.createDao(connectionSource, UserStats.class);
        } catch (SQLException ex) {
            Logger.getLogger(UserStatsRepository.class).error("Error creating user_stats repository - ", ex);
        }
    }

    public void add(UserStats userStats) {
        try {
            dao.create(userStats);
        } catch (SQLException ex) {
            Logger.getLogger(UserStatsRepository.class).error("Error adding a user_stats to DB - ", ex);
        }
    }

    public void update(UserStats userStats) {
        try {
            dao.update(userStats);
        } catch (SQLException ex) {
            Logger.getLogger(UserStatsRepository.class).error("Error updating a user_stats in DB - ", ex);
        }
    }

    public UserStats getUser(String userName) {
        try {
            QueryBuilder<UserStats, Object> qb = dao.queryBuilder();
            qb.where().eq("userName", userName);
            List<UserStats> users = dao.query(qb.prepare());
            if (users.size() == 1) {
                return users.get(0);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserStatsRepository.class).error("Error getting a user from DB - ", ex);
        }
        return null;
    }

    public List<UserStats> getAllUsers() {
        try {
            QueryBuilder<UserStats, Object> qb = dao.queryBuilder();
            return dao.query(qb.prepare());
        } catch (SQLException ex) {
            Logger.getLogger(UserStatsRepository.class).error("Error getting all users from DB - ", ex);
        }
        return null;
    }

    public long getLatestEndTimeMs() {
        try {
          QueryBuilder<UserStats, Object> qb = dao.queryBuilder();
          qb.orderBy("endTimeMs", false).limit(1);
          List<UserStats> users = dao.query(qb.prepare());
          if (users.size() == 1) {
              return users.get(0).getEndTimeMs();
          }
        } catch (SQLException ex) {
            Logger.getLogger(UserStatsRepository.class).error("Error getting the latest end time from DB - ", ex);
        }
        return 0;
    }

    public void closeDB() {
        try {
            if (dao != null && dao.getConnectionSource() != null) {
                DatabaseConnection conn = dao.getConnectionSource().getReadWriteConnection();
                conn.executeStatement("shutdown compact", 0);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserStatsRepository.class).error("Error closing user_stats repository - ", ex);
        }
    }
}
