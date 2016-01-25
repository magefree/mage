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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import mage.cards.repository.RepositoryUtil;
import mage.game.result.ResultProtos;
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

    // updateUserStats reads tables finished after the last DB update and reflects it to the DB.
    // It returns the list of user names that are upated.
    public List<String> updateUserStats() {
        HashSet<String> updatedUsers = new HashSet();
        // Lock the DB so that no other updateUserStats runs at the same time.
        synchronized(this) {
            long latestEndTimeMs = this.getLatestEndTimeMs();
            List<TableRecord> records = TableRecordRepository.instance.getAfter(latestEndTimeMs);
            for (TableRecord record : records) {
                ResultProtos.TableProto table = record.getProto();
                if (table.getControllerName().equals("System")) {
                    // This is a sub table within a tournament, so it's already handled by the main
                    // tournament table.
                    continue;
                }
                if (table.hasMatch()) {
                    ResultProtos.MatchProto match = table.getMatch();
                    for (ResultProtos.MatchPlayerProto player : match.getPlayersList()) {
                        UserStats userStats = this.getUser(player.getName());
                        ResultProtos.UserStatsProto proto = userStats != null ? userStats.getProto()
                                : ResultProtos.UserStatsProto.newBuilder().setName(player.getName()).build();
                        ResultProtos.UserStatsProto.Builder builder = ResultProtos.UserStatsProto.newBuilder(proto)
                                .setMatches(proto.getMatches() + 1);
                        switch (player.getQuit()) {
                            case IDLE_TIMEOUT:
                                builder.setMatchesIdleTimeout(proto.getMatchesIdleTimeout() + 1);
                                break;
                            case TIMER_TIMEOUT:
                                builder.setMatchesTimerTimeout(proto.getMatchesTimerTimeout() + 1);
                                break;
                            case QUIT:
                                builder.setMatchesQuit(proto.getMatchesQuit() + 1);
                                break;
                        }
                        if (userStats == null) {
                            this.add(new UserStats(builder.build(), table.getEndTimeMs()));
                        } else {
                            this.update(new UserStats(builder.build(), table.getEndTimeMs()));
                        }
                        updatedUsers.add(player.getName());
                    }
                } else if (table.hasTourney()) {
                    ResultProtos.TourneyProto tourney = table.getTourney();
                    for (ResultProtos.TourneyPlayerProto player : tourney.getPlayersList()) {
                        UserStats userStats = this.getUser(player.getName());
                        ResultProtos.UserStatsProto proto = userStats != null ? userStats.getProto()
                                : ResultProtos.UserStatsProto.newBuilder().setName(player.getName()).build();
                        ResultProtos.UserStatsProto.Builder builder = ResultProtos.UserStatsProto.newBuilder(proto)
                                .setTourneys(proto.getTourneys() + 1);
                        switch (player.getQuit()) {
                            case DURING_ROUND:
                                builder.setTourneysQuitDuringRound(proto.getTourneysQuitDuringRound() + 1);
                                break;
                            case DURING_DRAFTING:
                                builder.setTourneysQuitDuringDrafting(proto.getTourneysQuitDuringDrafting() + 1);
                                break;
                            case DURING_CONSTRUCTION:
                                builder.setTourneysQuitDuringConstruction(proto.getTourneysQuitDuringConstruction() + 1);
                                break;
                        }
                        if (userStats == null) {
                            this.add(new UserStats(builder.build(), table.getEndTimeMs()));
                        } else {
                            this.update(new UserStats(builder.build(), table.getEndTimeMs()));
                        }
                        updatedUsers.add(player.getName());
                    }
                }
            }
        }
        return new ArrayList(updatedUsers);
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
