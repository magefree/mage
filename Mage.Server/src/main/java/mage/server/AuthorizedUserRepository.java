package mage.server;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.table.TableUtils;
import mage.cards.repository.CardRepository;
import mage.cards.repository.RepositoryUtil;
import org.apache.log4j.Logger;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

public class AuthorizedUserRepository {

    private static final String JDBC_URL = "jdbc:h2:file:./db/authorized_user.h2;AUTO_SERVER=TRUE";
    private static final String VERSION_ENTITY_NAME = "authorized_user";
    // raise this if db structure was changed
    private static final long DB_VERSION = 2;
    private static final RandomNumberGenerator rng = new SecureRandomNumberGenerator();

    private static final AuthorizedUserRepository instance;
    static {
        instance = new AuthorizedUserRepository(JDBC_URL);
    }

    private Dao<AuthorizedUser, Object> dao;

    public AuthorizedUserRepository(String connectionString) {
        File file = new File("db");
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            ConnectionSource connectionSource = new JdbcConnectionSource(connectionString);
            TableUtils.createTableIfNotExists(connectionSource, AuthorizedUser.class);
            dao = DaoManager.createDao(connectionSource, AuthorizedUser.class);
        } catch (SQLException ex) {
            Logger.getLogger(AuthorizedUserRepository.class).error("Error creating / assigning authorized_user repository - ", ex);
        }
    }

    public static AuthorizedUserRepository getInstance() {
        return instance;
    }

    public void add(final String userName, final String password, final String email) {
        try {
            Hash hash = new SimpleHash(Sha256Hash.ALGORITHM_NAME, password, rng.nextBytes(), 1024);
            AuthorizedUser user = new AuthorizedUser(userName, hash, email);
            dao.create(user);
        } catch (SQLException ex) {
            Logger.getLogger(AuthorizedUserRepository.class).error("Error adding a user to DB - ", ex);
        }
    }

    public void remove(final String userName) {
        try {
            DeleteBuilder<AuthorizedUser, Object> db = dao.deleteBuilder();
            db.where().eq("name", new SelectArg(userName));
            db.delete();
        } catch (SQLException ex) {
            Logger.getLogger(AuthorizedUserRepository.class).error("Error removing a user from DB - ", ex);
        }
    }

    public AuthorizedUser getByName(String userName) {
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

    public void update(AuthorizedUser authorizedUser) {
        try {
            dao.update(authorizedUser);
        } catch (SQLException ex) {
            Logger.getLogger(AuthorizedUserRepository.class).error("Error updating authorized_user", ex);
        }
    }

    public AuthorizedUser getByEmail(String userName) {
        try {
            QueryBuilder<AuthorizedUser, Object> qb = dao.queryBuilder();
            qb.where().eq("email", new SelectArg(userName));
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
                DatabaseConnection conn = dao.getConnectionSource().getReadWriteConnection(dao.getTableName());
                conn.executeStatement("shutdown compact", 0);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AuthorizedUserRepository.class).error("Error closing authorized_user repository - ", ex);
        }
    }

    public long getDBVersionFromDB() {
        try {
            ConnectionSource connectionSource = new JdbcConnectionSource(JDBC_URL);
            return RepositoryUtil.getDatabaseVersion(connectionSource, VERSION_ENTITY_NAME);
        } catch (SQLException ex) {
            Logger.getLogger(CardRepository.class).error("Error getting DB version from DB - ", ex);
        }
        return 0;
    }

    public boolean checkAlterAndMigrateAuthorizedUser() {
        long currentDBVersion = getDBVersionFromDB();
        if (currentDBVersion == 1 && DB_VERSION == 2) {
            return migrateFrom1To2();
        }
        return true;
    }

    private boolean migrateFrom1To2() {
        try {
            Logger.getLogger(AuthorizedUserRepository.class).info("Starting " + VERSION_ENTITY_NAME + " DB migration from version 1 to version 2");
            dao.executeRaw("ALTER TABLE authorized_user ADD COLUMN active BOOLEAN DEFAULT true;");
            dao.executeRaw("ALTER TABLE authorized_user ADD COLUMN lockedUntil DATETIME;");
            dao.executeRaw("ALTER TABLE authorized_user ADD COLUMN chatLockedUntil DATETIME;");
            dao.executeRaw("ALTER TABLE authorized_user ADD COLUMN lastConnection DATETIME;");
            RepositoryUtil.updateVersion(dao.getConnectionSource(), VERSION_ENTITY_NAME, DB_VERSION);
            Logger.getLogger(AuthorizedUserRepository.class).info("Migration finished.");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AuthorizedUserRepository.class).error("Error while migrating from version 1 to version 2 - ", ex);
            return false;
        }
    }
}
