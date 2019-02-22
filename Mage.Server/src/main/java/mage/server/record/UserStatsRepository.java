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
import mage.game.result.ResultProtos;
import mage.server.rating.GlickoRating;
import mage.server.rating.GlickoRatingSystem;
import org.apache.log4j.Logger;

import java.io.File;
import java.sql.SQLException;
import java.util.*;

public enum UserStatsRepository {

    instance;

    private static final String JDBC_URL = "jdbc:sqlite:./db/user_stats.db";
    private static final String VERSION_ENTITY_NAME = "user_stats";
    // raise this if db structure was changed
    private static final long DB_VERSION = 0;

    private Dao<UserStats, Object> dao;

    UserStatsRepository() {
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
            qb.limit(1L).where().eq("userName", new SelectArg(userName));
            List<UserStats> users = dao.query(qb.prepare());
            if (!users.isEmpty()) {
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
        return Collections.emptyList();
    }

    public long getLatestEndTimeMs() {
        try {
            QueryBuilder<UserStats, Object> qb = dao.queryBuilder();
            qb.orderBy("endTimeMs", false).limit(1L);
            List<UserStats> users = dao.query(qb.prepare());
            if (!users.isEmpty()) {
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
        Set<String> updatedUsers = new HashSet<>();
        // Lock the DB so that no other updateUserStats runs at the same time.
        synchronized (this) {
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
                        ResultProtos.UserStatsProto proto =
                                userStats != null
                                        ? userStats.getProto()
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
                    updateRating(match, table.getEndTimeMs());
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

                    for (ResultProtos.TourneyRoundProto round : tourney.getRoundsList()) {
                        for (ResultProtos.MatchProto match : round.getMatchesList()) {
                            updateRating(match, table.getEndTimeMs());
                        }
                    }
                }
            }
        }
        return new ArrayList<>(updatedUsers);
    }

    private void updateRating(ResultProtos.MatchProto match, long tableEndTimeMs) {
        long matchEndTimeMs;
        if (match.hasEndTimeMs()) {
            matchEndTimeMs = match.getEndTimeMs();
        } else {
            matchEndTimeMs = tableEndTimeMs;
        }

        // process only match with options
        if (!match.hasMatchOptions()) {
            return;
        }
        ResultProtos.MatchOptionsProto matchOptions = match.getMatchOptions();

        // process only rated matches
        if (!matchOptions.getRated()) {
            return;
        }

        // rating only for duels
        if (match.getPlayersCount() != 2) {
            return;
        }

        ResultProtos.MatchPlayerProto player1 = match.getPlayers(0);
        ResultProtos.MatchPlayerProto player2 = match.getPlayers(1);

        // rate only games between human players
        if (!player1.getHuman() || !player2.getHuman()) {
            return;
        }

        double outcome;
        if ((player1.getQuit() == ResultProtos.MatchQuitStatus.NO_MATCH_QUIT && player1.getWins() > player2.getWins())
                || player2.getQuit() != ResultProtos.MatchQuitStatus.NO_MATCH_QUIT) {
            // player1 won
            outcome = 1;
        } else if ((player2.getQuit() == ResultProtos.MatchQuitStatus.NO_MATCH_QUIT && player1.getWins() < player2.getWins())
                || player1.getQuit() != ResultProtos.MatchQuitStatus.NO_MATCH_QUIT) {
            // player2 won
            outcome = 0;
        } else {
            // draw
            outcome = 0.5;
        }

        // get players stats
        UserStats player1Stats = getOrCreateUserStats(player1.getName(), tableEndTimeMs);
        ResultProtos.UserStatsProto player1StatsProto = player1Stats.getProto();
        UserStats player2Stats = getOrCreateUserStats(player2.getName(), tableEndTimeMs);
        ResultProtos.UserStatsProto player2StatsProto = player2Stats.getProto();

        ResultProtos.UserStatsProto.Builder player1StatsBuilder =
                ResultProtos.UserStatsProto.newBuilder(player1StatsProto);
        ResultProtos.UserStatsProto.Builder player2StatsBuilder =
                ResultProtos.UserStatsProto.newBuilder(player2StatsProto);

        // update general rating
        ResultProtos.GlickoRatingProto player1GeneralRatingProto = null;
        if (player1StatsProto.hasGeneralGlickoRating()) {
            player1GeneralRatingProto = player1StatsProto.getGeneralGlickoRating();
        }

        ResultProtos.GlickoRatingProto player2GeneralRatingProto = null;
        if (player2StatsProto.hasGeneralGlickoRating()) {
            player2GeneralRatingProto = player2StatsProto.getGeneralGlickoRating();
        }

        ResultProtos.GlickoRatingProto.Builder player1GeneralGlickoRatingBuilder =
                player1StatsBuilder.getGeneralGlickoRatingBuilder();

        ResultProtos.GlickoRatingProto.Builder player2GeneralGlickoRatingBuilder =
                player2StatsBuilder.getGeneralGlickoRatingBuilder();

        updateRating(player1GeneralRatingProto, player2GeneralRatingProto, outcome, matchEndTimeMs,
                player1GeneralGlickoRatingBuilder, player2GeneralGlickoRatingBuilder);

        if (matchOptions.hasLimited()) {
            if (matchOptions.getLimited()) {
                // update limited rating
                ResultProtos.GlickoRatingProto player1LimitedRatingProto = null;
                if (player1StatsProto.hasLimitedGlickoRating()) {
                    player1LimitedRatingProto = player1StatsProto.getLimitedGlickoRating();
                }

                ResultProtos.GlickoRatingProto player2LimitedRatingProto = null;
                if (player2StatsProto.hasLimitedGlickoRating()) {
                    player2LimitedRatingProto = player2StatsProto.getLimitedGlickoRating();
                }

                ResultProtos.GlickoRatingProto.Builder player1LimitedGlickoRatingBuilder =
                        player1StatsBuilder.getLimitedGlickoRatingBuilder();

                ResultProtos.GlickoRatingProto.Builder player2LimitedGlickoRatingBuilder =
                        player2StatsBuilder.getLimitedGlickoRatingBuilder();

                updateRating(player1LimitedRatingProto, player2LimitedRatingProto, outcome, matchEndTimeMs,
                        player1LimitedGlickoRatingBuilder, player2LimitedGlickoRatingBuilder);
            } else {
                // update constructed rating

                ResultProtos.GlickoRatingProto player1ConstructedRatingProto = null;
                if (player1StatsProto.hasConstructedGlickoRating()) {
                    player1ConstructedRatingProto = player1StatsProto.getConstructedGlickoRating();
                }

                ResultProtos.GlickoRatingProto player2ConstructedRatingProto = null;
                if (player2StatsProto.hasConstructedGlickoRating()) {
                    player2ConstructedRatingProto = player2StatsProto.getConstructedGlickoRating();
                }

                ResultProtos.GlickoRatingProto.Builder player1ConstructedGlickoRatingBuilder =
                        player1StatsBuilder.getConstructedGlickoRatingBuilder();

                ResultProtos.GlickoRatingProto.Builder player2ConstructedGlickoRatingBuilder =
                        player2StatsBuilder.getConstructedGlickoRatingBuilder();

                updateRating(player1ConstructedRatingProto, player2ConstructedRatingProto, outcome, matchEndTimeMs,
                        player1ConstructedGlickoRatingBuilder, player2ConstructedGlickoRatingBuilder);
            }
        }


        this.update(new UserStats(player1StatsBuilder.build(), player1Stats.getEndTimeMs()));
        this.update(new UserStats(player2StatsBuilder.build(), player2Stats.getEndTimeMs()));
    }

    private void updateRating(
            ResultProtos.GlickoRatingProto player1RatingProto,
            ResultProtos.GlickoRatingProto player2RatingProto,
            double outcome,
            long tableEndTimeMs,
            ResultProtos.GlickoRatingProto.Builder player1GlickoRatingBuilder,
            ResultProtos.GlickoRatingProto.Builder player2GlickoRatingBuilder) {

        GlickoRating player1GlickoRating;
        if (player1RatingProto != null) {
            player1GlickoRating = new GlickoRating(
                    player1RatingProto.getRating(),
                    player1RatingProto.getRatingDeviation(),
                    player1RatingProto.getLastGameTimeMs());
        } else {
            player1GlickoRating = GlickoRatingSystem.getInitialRating();
        }

        GlickoRating player2GlickoRating;
        if (player2RatingProto != null) {
            player2GlickoRating = new GlickoRating(
                    player2RatingProto.getRating(),
                    player2RatingProto.getRatingDeviation(),
                    player2RatingProto.getLastGameTimeMs());
        } else {
            player2GlickoRating = GlickoRatingSystem.getInitialRating();
        }

        GlickoRatingSystem glickoRatingSystem = new GlickoRatingSystem();
        glickoRatingSystem.updateRating(player1GlickoRating, player2GlickoRating, outcome, tableEndTimeMs);

        player1GlickoRatingBuilder
                .setRating(player1GlickoRating.getRating())
                .setRatingDeviation(player1GlickoRating.getRatingDeviation())
                .setLastGameTimeMs(tableEndTimeMs);

        player2GlickoRatingBuilder
                .setRating(player2GlickoRating.getRating())
                .setRatingDeviation(player2GlickoRating.getRatingDeviation())
                .setLastGameTimeMs(tableEndTimeMs);
    }

    private UserStats getOrCreateUserStats(String playerName, long endTimeMs) {
        UserStats userStats = this.getUser(playerName);
        if (userStats == null) {
            ResultProtos.UserStatsProto userStatsProto = ResultProtos.UserStatsProto.newBuilder().setName(playerName).build();
            userStats = new UserStats(userStatsProto, endTimeMs);
            this.add(userStats);
        }
        return userStats;
    }

    public void closeDB() {
        try {
            if (dao != null && dao.getConnectionSource() != null) {
                DatabaseConnection conn = dao.getConnectionSource().getReadWriteConnection(dao.getTableName());
                conn.executeStatement("shutdown compact", 0);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserStatsRepository.class).error("Error closing user_stats repository - ", ex);
        }
    }
}
