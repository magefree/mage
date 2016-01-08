package mage.server;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.table.TableUtils;
import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;
import mage.cards.repository.RepositoryUtil;
import org.apache.log4j.Logger;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

public enum AuthorizedUserRepository {

    instance;

    private static final String JDBC_URL = "jdbc:h2:file:./db/authorized_user.h2;AUTO_SERVER=TRUE";
    private static final String VERSION_ENTITY_NAME = "authorized_user";
    // raise this if db structure was changed
    private static final long DB_VERSION = 1;
    private static final RandomNumberGenerator rng = new SecureRandomNumberGenerator();

    private Dao<AuthorizedUser, Object> dao;

    private AuthorizedUserRepository() {
        File file = new File("db");
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            ConnectionSource connectionSource = new JdbcConnectionSource(JDBC_URL);
            boolean obsolete = RepositoryUtil.isDatabaseObsolete(connectionSource, VERSION_ENTITY_NAME, DB_VERSION);

            if (obsolete) {
                TableUtils.dropTable(connectionSource, AuthorizedUser.class, true);
            }

            TableUtils.createTableIfNotExists(connectionSource, AuthorizedUser.class);
            dao = DaoManager.createDao(connectionSource, AuthorizedUser.class);
        } catch (SQLException ex) {
            Logger.getLogger(AuthorizedUserRepository.class).error("Error creating authorized_user repository - ", ex);
        }
    }

    public void add(final String userName, final String password, final String email) {
        try {
            dao.callBatchTasks(new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    try {
                        Hash hash = new SimpleHash(Sha256Hash.ALGORITHM_NAME, password, rng.nextBytes(), 1024);
                        AuthorizedUser user = new AuthorizedUser(userName, hash, email);
                        dao.create(user);
                    } catch (SQLException ex) {
                        Logger.getLogger(AuthorizedUserRepository.class).error("Error adding a user to DB - ", ex);
                    }
                    return null;
                }
            });
        } catch (Exception ex) {
            Logger.getLogger(AuthorizedUserRepository.class).error("Error adding a authorized_user - ", ex);
        }
    }

    public AuthorizedUser get(String userName) {
        try {
            QueryBuilder<AuthorizedUser, Object> qb = dao.queryBuilder();
            qb.where().eq("name", new SelectArg(userName));
            List<AuthorizedUser> results = dao.query(qb.prepare());
            if (results.size() == 1) {
                return results.get(0);
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(AuthorizedUserRepository.class).error("Error getting a authorized_user - ", ex);
        }
        return null;
    }

    public void closeDB() {
        try {
            if (dao != null && dao.getConnectionSource() != null) {
                DatabaseConnection conn = dao.getConnectionSource().getReadWriteConnection();
                conn.executeStatement("shutdown compact", 0);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AuthorizedUserRepository.class).error("Error closing authorized_user repository - ", ex);
        }
    }
}
