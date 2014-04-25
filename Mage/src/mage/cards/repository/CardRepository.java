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
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import mage.constants.CardType;

import java.io.File;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.Callable;

/**
 *
 * @author North
 */
public enum CardRepository {

    instance;

    private static final String JDBC_URL = "jdbc:sqlite:db/cards.db";
    private static final String VERSION_ENTITY_NAME = "card";
    private static final long CARD_DB_VERSION = 30;

    private final Random random = new Random();
    private Dao<CardInfo, Object> cardDao;
    private Set<String> classNames;

    private CardRepository() {
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
            ex.printStackTrace();
        }
    }

    public void addCards(final List<CardInfo> cards) {
        try {
            cardDao.callBatchTasks(new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    try {
                        for (CardInfo card : cards) {
                            cardDao.create(card);
                            if (classNames != null) {
                                classNames.add(card.getClassName());
                            }
                        }
                    } catch (SQLException ex) {
                    }
                    return null;
                }
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
                    names.add(card.getName().substring(result+4));
                } else {
                    names.add(card.getName());
                }
            }
        } catch (SQLException ex) {
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
                    names.add(card.getName().substring(result+4));
                } else {
                    names.add(card.getName());
                }
            }
        } catch (SQLException ex) {
        }
        return names;
    }

    public Set<String> getNonLandAndNonCreatureNames() {
        Set<String> names = new TreeSet<>();
        try {
            QueryBuilder<CardInfo, Object> qb = cardDao.queryBuilder();
            qb.distinct().selectColumns("name");
            Where where = qb.where();
            where.and(where.not().like("types", '%' + CardType.CREATURE.name() +'%'),where.not().like("types", '%' + CardType.LAND.name() + '%'));
//            qb.where()
//                    .not().like("types", '%' + CardType.CREATURE.name() + '%')
//                    .and()
//                    .not().like("types", '%' + CardType.LAND.name() + '%');
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
        } catch (SQLException ex) {
        }
        return subtypes;
    }

    public CardInfo findCard(String setCode, int cardNumber) {
        try {
            QueryBuilder<CardInfo, Object> queryBuilder = cardDao.queryBuilder();
            queryBuilder.where().eq("setCode", new SelectArg(setCode)).and().eq("cardNumber", cardNumber).and().eq("nightCard", false);
            List<CardInfo> result = cardDao.query(queryBuilder.prepare());
            if (!result.isEmpty()) {
                return result.get(0);
            }
        } catch (SQLException ex) {
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
        }
        return names;
    }

    public List<CardInfo> getMissingCards(List<String> classNames) {
        try {
            QueryBuilder<CardInfo, Object> queryBuilder = cardDao.queryBuilder();
            queryBuilder.where().not().in("className", classNames);

            return cardDao.query(queryBuilder.prepare());
        } catch (SQLException ex) {
        }
        return new ArrayList<>();
    }

    /**
     *
     * @param name
     * @return random card with the provided name or null if none is found
     */
    public CardInfo findCard(String name) {
        List<CardInfo> cards = findCards(name);
        if (!cards.isEmpty()) {
            return cards.get(random.nextInt(cards.size()));
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
        return new ArrayList<>();
    }

    public List<CardInfo> findCards(CardCriteria criteria) {
        try {
            QueryBuilder<CardInfo, Object> queryBuilder = cardDao.queryBuilder();
            criteria.buildQuery(queryBuilder);
            
            return cardDao.query(queryBuilder.prepare());
        } catch (SQLException ex) {
        }
        return new ArrayList<>();
    }
}
