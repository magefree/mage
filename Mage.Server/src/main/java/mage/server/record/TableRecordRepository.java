package mage.server.record;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.table.TableUtils;
import mage.cards.repository.RepositoryUtil;
import org.apache.log4j.Logger;

import java.io.File;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public enum TableRecordRepository {

    instance;

    private static final String JDBC_URL = "jdbc:sqlite:./db/table_record.db";
    private static final String VERSION_ENTITY_NAME = "table_record";
    // raise this if db structure was changed
    private static final long DB_VERSION = 0;

    private Dao<TableRecord, Object> dao;

    TableRecordRepository() {
        File file = new File("db");
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            ConnectionSource connectionSource = new JdbcConnectionSource(JDBC_URL);
            boolean obsolete = RepositoryUtil.isDatabaseObsolete(connectionSource, VERSION_ENTITY_NAME, DB_VERSION);

            if (obsolete) {
                TableUtils.dropTable(connectionSource, TableRecord.class, true);
            }

            TableUtils.createTableIfNotExists(connectionSource, TableRecord.class);
            dao = DaoManager.createDao(connectionSource, TableRecord.class);
        } catch (SQLException ex) {
            Logger.getLogger(TableRecordRepository.class).error("Error creating table_record repository - ", ex);
        }
    }

    public void add(TableRecord tableHistory) {
        try {
            dao.create(tableHistory);
        } catch (SQLException ex) {
            Logger.getLogger(TableRecordRepository.class).error("Error adding a table_record to DB - ", ex);
        }
    }

    public List<TableRecord> getAfter(long endTimeMs) {
        try {
            QueryBuilder<TableRecord, Object> qb = dao.queryBuilder();
            qb.where().gt("endTimeMs", new SelectArg(endTimeMs));
            qb.orderBy("endTimeMs", true);
            return dao.query(qb.prepare());
        } catch (SQLException ex) {
            Logger.getLogger(TableRecordRepository.class).error("Error getting table_records from DB - ", ex);
        }
        return Collections.emptyList();
    }

    public void closeDB() {
        try {
            if (dao != null && dao.getConnectionSource() != null) {
                DatabaseConnection conn = dao.getConnectionSource().getReadWriteConnection(dao.getTableName());
                conn.executeStatement("shutdown compact", 0);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TableRecordRepository.class).error("Error closing table_record repository - ", ex);
        }
    }
}
