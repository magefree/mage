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
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;
import java.util.concurrent.Callable;

/**
 *
 * @author North
 */
public enum CardRepository {

    instance;
    private static final String JDBC_URL = "jdbc:sqlite:mage.db";
    private Random random = new Random();
    private Dao<CardInfo, Object> cardDao;
    private TreeSet<String> classNames;

    private CardRepository() {
        try {
            ConnectionSource connectionSource = new JdbcConnectionSource(JDBC_URL);

            TableUtils.createTableIfNotExists(connectionSource, CardInfo.class);
            cardDao = DaoManager.createDao(connectionSource, CardInfo.class);
        } catch (SQLException ex) {
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
                classNames = new TreeSet<String>();
                for (CardInfo card : results) {
                    classNames.add(card.getClassName());
                }
            }
            return classNames.contains(className);
        } catch (SQLException ex) {
        }
        return false;
    }

    public List<String> getSetCodes() {
        List<String> setCodes = new ArrayList<String>();
        try {
            QueryBuilder<CardInfo, Object> qb = cardDao.queryBuilder();
            qb.distinct().selectColumns("setCode");
            List<CardInfo> results = cardDao.query(qb.prepare());
            for (CardInfo card : results) {
                setCodes.add(card.getSetCode());
            }
        } catch (SQLException ex) {
        } finally {
            return setCodes;
        }
    }

    public CardInfo findCard(String setCode, int cardNumber) {
        try {
            QueryBuilder<CardInfo, Object> queryBuilder = cardDao.queryBuilder();
            queryBuilder.where().eq("setCode", setCode).and().eq("cardNumber", cardNumber);
            List<CardInfo> result = cardDao.query(queryBuilder.prepare());
            if (!result.isEmpty()) {
                return result.get(0);
            }
        } catch (SQLException ex) {
        }
        return null;
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
            queryBuilder.where().eq("name", name);

            return cardDao.query(queryBuilder.prepare());
        } catch (SQLException ex) {
        }
        return new ArrayList<CardInfo>();
    }

    public List<CardInfo> findCards(CardCriteria criteria) {
        try {
            QueryBuilder<CardInfo, Object> queryBuilder = cardDao.queryBuilder();
            criteria.buildQuery(queryBuilder);

            return cardDao.query(queryBuilder.prepare());
        } catch (SQLException ex) {
        }
        return new ArrayList<CardInfo>();
    }

    public List<CardInfo> getAllCards() {
        try {
            QueryBuilder<CardInfo, Object> queryBuilder = cardDao.queryBuilder();

            return cardDao.query(queryBuilder.prepare());
        } catch (SQLException ex) {
        }
        return new ArrayList<CardInfo>();
    }
}
