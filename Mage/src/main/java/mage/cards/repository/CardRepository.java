/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.cards.repository;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.table.TableUtils;

import java.io.File;
import java.sql.SQLException;
import java.util.*;

import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetType;
import mage.util.RandomUtil;
import org.apache.log4j.Logger;

/**
 * @author North
 */
public enum CardRepository {

    instance;

    private static final String JDBC_URL = "jdbc:h2:file:./db/cards.h2;AUTO_SERVER=TRUE";
    private static final String VERSION_ENTITY_NAME = "card";
    // raise this if db structure was changed
    private static final long CARD_DB_VERSION = 50;
    // raise this if new cards were added to the server
    private static final long CARD_CONTENT_VERSION = 74;
    private final TreeSet<String> landTypes = new TreeSet<>();
    private Dao<CardInfo, Object> cardDao;
    private Set<String> classNames;

    CardRepository() {
        File file = new File("db");
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            ConnectionSource connectionSource = new JdbcConnectionSource(JDBC_URL);
            boolean obsolete = RepositoryUtil.isDatabaseObsolete(connectionSource, VERSION_ENTITY_NAME, CARD_DB_VERSION);

            if (obsolete) {
                TableUtils.dropTable(connectionSource, CardInfo.class, true);
            }

            TableUtils.createTableIfNotExists(connectionSource, CardInfo.class);
            cardDao = DaoManager.createDao(connectionSource, CardInfo.class);
        } catch (SQLException ex) {
            Logger.getLogger(CardRepository.class).error("Error creating card repository - ", ex);
        }
    }

    public void addCards(final List<CardInfo> cards) {
        try {
            cardDao.callBatchTasks(() -> {
                try {
                    for (CardInfo card : cards) {
                        cardDao.create(card);
                        if (classNames != null) {
                            classNames.add(card.getClassName());
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(CardRepository.class).error("Error adding cards to DB - ", ex);
                }
                return null;
            });
        } catch (Exception ex) {
        }
    }

    public boolean cardExists(String className) {
        try {
            if (classNames == null) {
                QueryBuilder<CardInfo, Object> qb = cardDao.queryBuilder();
                qb.distinct().selectColumns("className").where().isNotNull("className");
                List<CardInfo> results = cardDao.query(qb.prepare());
                classNames = new TreeSet<>();
                for (CardInfo card : results) {
                    classNames.add(card.getClassName());
                }
            }
            return classNames.contains(className);
        } catch (SQLException ex) {
        }
        return false;
    }

    public boolean cardExists(CardSetInfo className) {
        try {
            if (classNames == null) {
                QueryBuilder<CardInfo, Object> qb = cardDao.queryBuilder();
                qb.distinct().selectColumns("className").where().isNotNull("className");
                List<CardInfo> results = cardDao.query(qb.prepare());
                classNames = new TreeSet<>();
                for (CardInfo card : results) {
                    classNames.add(card.getClassName());
                }
            }
            return classNames.contains(className.getName());
        } catch (SQLException ex) {
        }
        return false;
    }

    public Set<String> getNames() {
        Set<String> names = new TreeSet<>();
        try {
            QueryBuilder<CardInfo, Object> qb = cardDao.queryBuilder();
            qb.distinct().selectColumns("name");
            List<CardInfo> results = cardDao.query(qb.prepare());
            for (CardInfo card : results) {
                int result = card.getName().indexOf(" // ");
                if (result > 0) {
                    names.add(card.getName().substring(0, result));
                    names.add(card.getName().substring(result + 4));
                } else {
                    names.add(card.getName());
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CardRepository.class).error("Error getting names from DB : " + ex);
        }
        return names;
    }

    public Set<String> getNonLandNames() {
        Set<String> names = new TreeSet<>();
        try {
            QueryBuilder<CardInfo, Object> qb = cardDao.queryBuilder();
            qb.distinct().selectColumns("name");
            qb.where().not().like("types", new SelectArg('%' + CardType.LAND.name() + '%'));
            List<CardInfo> results = cardDao.query(qb.prepare());
            for (CardInfo card : results) {
                int result = card.getName().indexOf(" // ");
                if (result > 0) {
                    names.add(card.getName().substring(0, result));
                    names.add(card.getName().substring(result + 4));
                } else {
                    names.add(card.getName());
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CardRepository.class).error("Error getting non-land names from DB : " + ex);

        }
        return names;
    }

    public Set<String> getCreatureNames() {
        Set<String> names = new TreeSet<>();
        try {
            QueryBuilder<CardInfo, Object> qb = cardDao.queryBuilder();
            qb.distinct().selectColumns("name");
            qb.where().like("types", new SelectArg('%' + CardType.CREATURE.name() + '%'));
            List<CardInfo> results = cardDao.query(qb.prepare());
            for (CardInfo card : results) {
                int result = card.getName().indexOf(" // ");
                if (result > 0) {
                    names.add(card.getName().substring(0, result));
                    names.add(card.getName().substring(result + 4));
                } else {
                    names.add(card.getName());
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CardRepository.class).error("Error getting creature names from DB : " + ex);

        }
        return names;
    }

    public Set<String> getArtifactNames() {
        Set<String> names = new TreeSet<>();
        try {
            QueryBuilder<CardInfo, Object> qb = cardDao.queryBuilder();
            qb.distinct().selectColumns("name");
            qb.where().like("types", new SelectArg('%' + CardType.ARTIFACT.name() + '%'));
            List<CardInfo> results = cardDao.query(qb.prepare());
            for (CardInfo card : results) {
                int result = card.getName().indexOf(" // ");
                if (result > 0) {
                    names.add(card.getName().substring(0, result));
                    names.add(card.getName().substring(result + 4));
                } else {
                    names.add(card.getName());
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CardRepository.class).error("Error getting artifact names from DB : " + ex);

        }
        return names;
    }

    public Set<String> getNonLandAndNonCreatureNames() {
        Set<String> names = new TreeSet<>();
        try {
            QueryBuilder<CardInfo, Object> qb = cardDao.queryBuilder();
            qb.distinct().selectColumns("name");
            Where where = qb.where();
            where.and(where.not().like("types", '%' + CardType.CREATURE.name() + '%'), where.not().like("types", '%' + CardType.LAND.name() + '%'));
            List<CardInfo> results = cardDao.query(qb.prepare());
            for (CardInfo card : results) {
                int result = card.getName().indexOf(" // ");
                if (result > 0) {
                    names.add(card.getName().substring(0, result));
                    names.add(card.getName().substring(result + 4));
                } else {
                    names.add(card.getName());
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CardRepository.class).error("Error getting non-land and non-creature names from DB : " + ex);
        }
        return names;
    }

    public Set<String> getNonArtifactAndNonLandNames() {
        Set<String> names = new TreeSet<>();
        try {
            QueryBuilder<CardInfo, Object> qb = cardDao.queryBuilder();
            qb.distinct().selectColumns("name");
            Where where = qb.where();
            where.and(where.not().like("types", '%' + CardType.ARTIFACT.name() + '%'), where.not().like("types", '%' + CardType.LAND.name() + '%'));
            List<CardInfo> results = cardDao.query(qb.prepare());
            for (CardInfo card : results) {
                int result = card.getName().indexOf(" // ");
                if (result > 0) {
                    names.add(card.getName().substring(0, result));
                    names.add(card.getName().substring(result + 4));
                } else {
                    names.add(card.getName());
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CardRepository.class).error("Error getting non-artifact non-land names from DB : " + ex);

        }
        return names;
    }

    public Set<String> getCreatureTypes() {
        TreeSet<String> subtypes = new TreeSet<>();
        try {
            QueryBuilder<CardInfo, Object> qb = cardDao.queryBuilder();
            qb.distinct().selectColumns("subtypes");
            qb.where().like("types", new SelectArg('%' + CardType.CREATURE.name() + '%'));
            List<CardInfo> results = cardDao.query(qb.prepare());
            for (CardInfo card : results) {
                subtypes.addAll(card.getSubTypes());
            }
            // Removing Forest because of Dryad Arbor
            subtypes.remove("Forest");
            // Some creature types are not directly included in card types and are added here manually
            subtypes.add("Blinkmoth");
            subtypes.add("Camarid");
            subtypes.add("Caribou");
            subtypes.add("Citizen");
            subtypes.add("Coward");
            subtypes.add("Deserter");
            subtypes.add("Germ");
            subtypes.add("Graveborn");
            subtypes.add("Orb");
            subtypes.add("Pentavite");
            subtypes.add("Pincher");
            subtypes.add("Prism");
            subtypes.add("Reflection");
            subtypes.add("Sand");
            subtypes.add("Saproling");
            subtypes.add("Scion");
            subtypes.add("Serf");
            subtypes.add("Servo");
            subtypes.add("Splinter");
            subtypes.add("Survivor");
            subtypes.add("Tetravite");
            subtypes.add("Triskelavite");

        } catch (SQLException ex) {
            Logger.getLogger(CardRepository.class).error("Error getting creaturetypes from DB : " + ex);

        }
        return subtypes;
    }

    public Set<String> getLandTypes() {
        if (landTypes.isEmpty()) {
            try {
                QueryBuilder<CardInfo, Object> qb = cardDao.queryBuilder();
                qb.distinct().selectColumns("subtypes");
                qb.where().like("types", new SelectArg('%' + CardType.LAND.name() + '%'));
                List<CardInfo> results = cardDao.query(qb.prepare());
                for (CardInfo card : results) {
                    landTypes.addAll(card.getSubTypes());
                }
                // Removing Dryad because of Dryad Arbor
                landTypes.remove("Dryad");

            } catch (SQLException ex) {
                Logger.getLogger(CardRepository.class).error("Error getting landtypes from DB : " + ex);
            }
        }
        return landTypes;
    }

    public CardInfo findCard(String setCode, String cardNumber) {
        try {
            QueryBuilder<CardInfo, Object> queryBuilder = cardDao.queryBuilder();
            queryBuilder.limit(1L).where().eq("setCode", new SelectArg(setCode)).and().eq("cardNumber", cardNumber).and().eq("nightCard", false);
            List<CardInfo> result = cardDao.query(queryBuilder.prepare());
            if (!result.isEmpty()) {
                return result.get(0);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CardRepository.class).error("Error finding card from DB : " + ex);

        }
        return null;
    }

    public List<String> getClassNames() {
        List<String> names = new ArrayList<>();
        try {
            List<CardInfo> results = cardDao.queryForAll();
            for (CardInfo card : results) {
                names.add(card.getClassName());
            }
        } catch (SQLException ex) {
            Logger.getLogger(CardRepository.class).error("Error getting classnames from DB : " + ex);
        }
        return names;
    }

    public List<CardInfo> getMissingCards(List<String> classNames) {
        try {
            QueryBuilder<CardInfo, Object> queryBuilder = cardDao.queryBuilder();
            queryBuilder.where().not().in("className", classNames);

            return cardDao.query(queryBuilder.prepare());
        } catch (SQLException ex) {
            Logger.getLogger(CardRepository.class).error("Error getting missing cards from DB : " + ex);

        }

        return Collections.emptyList();
    }

    /**
     * @param name
     * @return random card with the provided name or null if none is found
     */
    public CardInfo findCard(String name) {
        List<CardInfo> cards = findCards(name);
        if (!cards.isEmpty()) {
            return cards.get(RandomUtil.nextInt(cards.size()));
        }
        return null;
    }

    public CardInfo findPreferedCoreExpansionCard(String name, boolean caseInsensitive) {
        List<CardInfo> cards;
        if (caseInsensitive) {
            cards = findCardsCaseInsensitive(name);
        } else {
            cards = findCards(name);
        }
        if (!cards.isEmpty()) {
            Date lastReleaseDate = null;
            Date lastExpansionDate = null;
            CardInfo cardToUse = null;
            for (CardInfo cardinfo : cards) {
                ExpansionInfo set = ExpansionRepository.instance.getSetByCode(cardinfo.getSetCode());
                if (set != null) {
                    if ((set.getType() == SetType.EXPANSION || set.getType() == SetType.CORE)
                            && (lastExpansionDate == null || set.getReleaseDate().after(lastExpansionDate))) {
                        cardToUse = cardinfo;
                        lastExpansionDate = set.getReleaseDate();
                    }
                    if (lastExpansionDate == null && (lastReleaseDate == null || set.getReleaseDate().after(lastReleaseDate))) {
                        cardToUse = cardinfo;
                        lastReleaseDate = set.getReleaseDate();
                    }
                }
            }
            return cardToUse;
        }
        return null;
    }

    public List<CardInfo> findCards(String name) {
        try {
            QueryBuilder<CardInfo, Object> queryBuilder = cardDao.queryBuilder();
            queryBuilder.where().eq("name", new SelectArg(name));
            return cardDao.query(queryBuilder.prepare());
        } catch (SQLException ex) {
        }
        return Collections.emptyList();
    }

    public List<CardInfo> findCardsCaseInsensitive(String name) {
        try {
            String sqlName = name.toLowerCase().replaceAll("\'", "\'\'");
            GenericRawResults<CardInfo> rawResults = cardDao.queryRaw(
                    "select * from " + CardRepository.VERSION_ENTITY_NAME + " where lower(name) = '" + sqlName + '\'',
                    cardDao.getRawRowMapper());
            List<CardInfo> result = new ArrayList<>();
            for (CardInfo cardinfo : rawResults) {
                result.add(cardinfo);
            }
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(CardRepository.class).error("Error during execution of raw sql statement", ex);
        }
        return Collections.emptyList();
    }

    public List<CardInfo> findCards(CardCriteria criteria) {
        try {
            QueryBuilder<CardInfo, Object> queryBuilder = cardDao.queryBuilder();
            criteria.buildQuery(queryBuilder);

            return cardDao.query(queryBuilder.prepare());
        } catch (SQLException ex) {
            Logger.getLogger(CardRepository.class).error("Error during execution of card repository query statement", ex);
        }
        return Collections.emptyList();
    }

    public long getContentVersionFromDB() {
        try {
            ConnectionSource connectionSource = new JdbcConnectionSource(JDBC_URL);
            return RepositoryUtil.getDatabaseVersion(connectionSource, VERSION_ENTITY_NAME + "Content");
        } catch (SQLException ex) {
            Logger.getLogger(CardRepository.class).error("Error getting content version from DB - ", ex);
        }
        return 0;
    }

    public void setContentVersion(long version) {
        try {
            ConnectionSource connectionSource = new JdbcConnectionSource(JDBC_URL);
            RepositoryUtil.updateVersion(connectionSource, VERSION_ENTITY_NAME + "Content", version);
        } catch (SQLException ex) {
            Logger.getLogger(CardRepository.class).error("Error getting content version - ", ex);
        }
    }

    public long getContentVersionConstant() {
        return CARD_CONTENT_VERSION;
    }

    public void closeDB() {
        try {
            if (cardDao != null && cardDao.getConnectionSource() != null) {
                DatabaseConnection conn = cardDao.getConnectionSource().getReadWriteConnection();
                conn.executeStatement("shutdown compact", 0);
            }

        } catch (SQLException ex) {

        }
    }

    public void openDB() {
        try {
            ConnectionSource connectionSource = new JdbcConnectionSource(JDBC_URL);
            cardDao = DaoManager.createDao(connectionSource, CardInfo.class);
        } catch (SQLException ex) {
            Logger.getLogger(CardRepository.class).error("Error opening card repository - ", ex);
        }
    }
}
