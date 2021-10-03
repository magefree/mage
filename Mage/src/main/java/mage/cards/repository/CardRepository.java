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
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetType;
import mage.constants.SuperType;
import mage.game.events.Listener;
import mage.util.RandomUtil;
import org.apache.log4j.Logger;

import java.io.File;
import java.sql.SQLException;
import java.util.*;

/**
 * @author North, JayDi85
 */
public enum CardRepository {

    instance;

    private static final Logger logger = Logger.getLogger(CardRepository.class);

    private static final String JDBC_URL = "jdbc:h2:file:./db/cards.h2;AUTO_SERVER=TRUE";
    private static final String VERSION_ENTITY_NAME = "card";
    // raise this if db structure was changed
    private static final long CARD_DB_VERSION = 53;
    // raise this if new cards were added to the server
    private static final long CARD_CONTENT_VERSION = 241;
    private Dao<CardInfo, Object> cardDao;
    private Set<String> classNames;
    private final RepositoryEventSource eventSource = new RepositoryEventSource();

    public static final Set<String> snowLandSetCodes = new HashSet<>(Arrays.asList(
            "CSP",
            "MH1",
            "SLD",
            "ME2",
            "ICE",
            "KHM"
    ));

    CardRepository() {
        File file = new File("db");
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            ConnectionSource connectionSource = new JdbcConnectionSource(JDBC_URL);

            boolean isObsolete = RepositoryUtil.isDatabaseObsolete(connectionSource, VERSION_ENTITY_NAME, CARD_DB_VERSION);
            boolean isNewBuild = RepositoryUtil.isNewBuildRun(connectionSource, VERSION_ENTITY_NAME, CardRepository.class); // recreate db on new build
            if (isObsolete || isNewBuild) {
                //System.out.println("Local cards db is outdated, cleaning...");
                TableUtils.dropTable(connectionSource, CardInfo.class, true);
            }

            TableUtils.createTableIfNotExists(connectionSource, CardInfo.class);
            cardDao = DaoManager.createDao(connectionSource, CardInfo.class);
            eventSource.fireRepositoryDbLoaded();
        } catch (SQLException ex) {
            Logger.getLogger(CardRepository.class).error("Error creating card repository - ", ex);
        }
    }

    public void subscribe(Listener<RepositoryEvent> listener) {
        eventSource.addListener(listener);
    }

    public void saveCards(final List<CardInfo> newCards, long newContentVersion) {
        try {
            cardDao.callBatchTasks(() -> {
                // add
                if (newCards != null && !newCards.isEmpty()) {
                    logger.info("DB: need to add " + newCards.size() + " new cards");
                    try {
                        for (CardInfo card : newCards) {
                            cardDao.create(card);
                            if (classNames != null) {
                                classNames.add(card.getClassName());
                            }
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(CardRepository.class).error("Error adding cards to DB - ", ex);
                    }
                }

                // no card updates

                return null;
            });

            setContentVersion(newContentVersion);
            eventSource.fireRepositoryDbUpdated();
        } catch (Exception ex) {
            //
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

    private void addNewNames(CardInfo card, Set<String> namesList) {
        // require before call: qb.distinct().selectColumns("name", "modalDoubleFacesSecondSideName"...);

        // normal names
        int result = card.getName().indexOf(" // ");
        if (result > 0) {
            namesList.add(card.getName().substring(0, result));
            namesList.add(card.getName().substring(result + 4));
        } else {
            namesList.add(card.getName());
        }

        // additional names from double side cards
        if (card.getSecondSideName() != null && !card.getSecondSideName().isEmpty()) {
            namesList.add(card.getSecondSideName());
        }
        if (card.getModalDoubleFacesSecondSideName() != null && !card.getModalDoubleFacesSecondSideName().isEmpty()) {
            namesList.add(card.getModalDoubleFacesSecondSideName());
        }
        if (card.getFlipCardName() != null && !card.getFlipCardName().isEmpty()) {
            namesList.add(card.getFlipCardName());
        }
    }

    public static Boolean haveSnowLands(String setCode) {
        return snowLandSetCodes.contains(setCode);
    }

    public Set<String> getNames() {
        Set<String> names = new TreeSet<>();
        try {
            QueryBuilder<CardInfo, Object> qb = cardDao.queryBuilder();
            qb.distinct().selectColumns("name", "modalDoubleFacesSecondSideName", "secondSideName", "flipCardName");
            List<CardInfo> results = cardDao.query(qb.prepare());
            for (CardInfo card : results) {
                addNewNames(card, names);
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
            qb.distinct().selectColumns("name", "modalDoubleFacesSecondSideName", "secondSideName", "flipCardName");
            qb.where().not().like("types", new SelectArg('%' + CardType.LAND.name() + '%'));
            List<CardInfo> results = cardDao.query(qb.prepare());
            for (CardInfo card : results) {
                addNewNames(card, names);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CardRepository.class).error("Error getting non-land names from DB : " + ex);

        }
        return names;
    }

    public Set<String> getNonbasicLandNames() {
        Set<String> names = new TreeSet<>();
        try {
            QueryBuilder<CardInfo, Object> qb = cardDao.queryBuilder();
            qb.distinct().selectColumns("name", "modalDoubleFacesSecondSideName", "secondSideName", "flipCardName");
            Where where = qb.where();
            where.and(
                    where.not().like("supertypes", '%' + SuperType.BASIC.name() + '%'),
                    where.like("types", '%' + CardType.LAND.name() + '%')
            );
            List<CardInfo> results = cardDao.query(qb.prepare());
            for (CardInfo card : results) {
                addNewNames(card, names);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CardRepository.class).error("Error getting non-land names from DB : " + ex);

        }
        return names;
    }

    public Set<String> getNotBasicLandNames() {
        Set<String> names = new TreeSet<>();
        try {
            QueryBuilder<CardInfo, Object> qb = cardDao.queryBuilder();
            qb.distinct().selectColumns("name", "modalDoubleFacesSecondSideName", "secondSideName", "flipCardName");
            qb.where().not().like("supertypes", new SelectArg('%' + SuperType.BASIC.name() + '%'));
            List<CardInfo> results = cardDao.query(qb.prepare());
            for (CardInfo card : results) {
                addNewNames(card, names);
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
            qb.distinct().selectColumns("name", "modalDoubleFacesSecondSideName", "secondSideName", "flipCardName");
            qb.where().like("types", new SelectArg('%' + CardType.CREATURE.name() + '%'));
            List<CardInfo> results = cardDao.query(qb.prepare());
            for (CardInfo card : results) {
                addNewNames(card, names);
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
            qb.distinct().selectColumns("name", "modalDoubleFacesSecondSideName", "secondSideName", "flipCardName");
            qb.where().like("types", new SelectArg('%' + CardType.ARTIFACT.name() + '%'));
            List<CardInfo> results = cardDao.query(qb.prepare());
            for (CardInfo card : results) {
                addNewNames(card, names);
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
            qb.distinct().selectColumns("name", "modalDoubleFacesSecondSideName", "secondSideName", "flipCardName");
            Where where = qb.where();
            where.and(
                    where.not().like("types", '%' + CardType.CREATURE.name() + '%'),
                    where.not().like("types", '%' + CardType.LAND.name() + '%')
            );
            List<CardInfo> results = cardDao.query(qb.prepare());
            for (CardInfo card : results) {
                addNewNames(card, names);
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
            qb.distinct().selectColumns("name", "modalDoubleFacesSecondSideName", "secondSideName", "flipCardName");
            Where where = qb.where();
            where.and(
                    where.not().like("types", '%' + CardType.ARTIFACT.name() + '%'),
                    where.not().like("types", '%' + CardType.LAND.name() + '%')
            );
            List<CardInfo> results = cardDao.query(qb.prepare());
            for (CardInfo card : results) {
                addNewNames(card, names);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CardRepository.class).error("Error getting non-artifact non-land names from DB : " + ex);

        }
        return names;
    }

    public CardInfo findCard(String setCode, String cardNumber) {
        return findCard(setCode, cardNumber, true);
    }

    public CardInfo findCard(String setCode, String cardNumber, boolean ignoreNightCards) {
        try {
            QueryBuilder<CardInfo, Object> queryBuilder = cardDao.queryBuilder();
            if (ignoreNightCards) {
                queryBuilder.limit(1L).where()
                        .eq("setCode", new SelectArg(setCode))
                        .and().eq("cardNumber", new SelectArg(cardNumber))
                        .and().eq("nightCard", new SelectArg(false));
            } else {
                queryBuilder.limit(1L).where()
                        .eq("setCode", new SelectArg(setCode))
                        .and().eq("cardNumber", new SelectArg(cardNumber));
            }
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

    public CardInfo findCard(String name) {
        return findCard(name, false);
    }

    /**
     * @param name
     * @param returnAnySet return card from first available set (WARNING, it's a performance optimization for tests,
     *                     don't use it in real games - users must get random set)
     * @return random card with the provided name or null if none is found
     */
    public CardInfo findCard(String name, boolean returnAnySet) {
        List<CardInfo> cards = returnAnySet ? findCards(name, 1) : findCards(name);
        if (!cards.isEmpty()) {
            return cards.get(RandomUtil.nextInt(cards.size()));
        }
        return null;
    }

    public CardInfo findPreferredCoreExpansionCard(String name, boolean caseInsensitive) {
        return findPreferredCoreExpansionCard(name, caseInsensitive, null);
    }

    public CardInfo findPreferredCoreExpansionCard(String name, boolean caseInsensitive, String preferredSetCode) {
        List<CardInfo> cards;
        if (caseInsensitive) {
            cards = findCardsCaseInsensitive(name);
        } else {
            cards = findCards(name);
        }
        return findPreferredOrLatestCard(cards, preferredSetCode);
    }

    public CardInfo findPreferredCoreExpansionCardByClassName(String canonicalClassName, String preferredSetCode) {
        List<CardInfo> cards = findCardsByClass(canonicalClassName);
        return findPreferredOrLatestCard(cards, preferredSetCode);
    }

    private CardInfo findPreferredOrLatestCard(List<CardInfo> cards, String preferredSetCode) {
        if (!cards.isEmpty()) {
            Date lastReleaseDate = null;
            Date lastExpansionDate = null;
            CardInfo cardToUse = null;
            for (CardInfo cardinfo : cards) {
                ExpansionInfo set = ExpansionRepository.instance.getSetByCode(cardinfo.getSetCode());
                if (set != null) {

                    if ((preferredSetCode != null) && (preferredSetCode.equals(set.getCode()))) {
                        return cardinfo;
                    }

                    if (set.getType().isStandardLegal() && (lastExpansionDate == null || set.getReleaseDate().after(lastExpansionDate))) {
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

    public CardInfo findCardWPreferredSet(String name, String expansion, boolean caseInsensitive) {
        List<CardInfo> cards;
        if (caseInsensitive) {
            cards = findCardsCaseInsensitive(name);
        } else {
            cards = findCards(name);
        }
        if (!cards.isEmpty()) {
            CardInfo cardToUse = null;
            for (CardInfo cardinfo : cards) {
                if (cardinfo.getSetCode() != null && expansion != null && expansion.equalsIgnoreCase(cardinfo.getSetCode())) {
                    return cardinfo;
                }
            }
        }
        return findPreferredCoreExpansionCard(name, true);
    }

    public List<CardInfo> findCards(String name) {
        return findCards(name, 0);
    }

    /**
     * Find card's reprints from all sets
     *
     * @param name
     * @param limitByMaxAmount return max amount of different cards (if 0 then return card from all sets)
     * @return
     */
    public List<CardInfo> findCards(String name, long limitByMaxAmount) {
        try {
            QueryBuilder<CardInfo, Object> queryBuilder = cardDao.queryBuilder();
            queryBuilder.where().eq("name", new SelectArg(name));
            if (limitByMaxAmount > 0) {
                queryBuilder.limit(limitByMaxAmount);
            }
            return cardDao.query(queryBuilder.prepare());
        } catch (SQLException ex) {
        }
        return Collections.emptyList();
    }

    public List<CardInfo> findCardsByClass(String canonicalClassName) {
        try {
            QueryBuilder<CardInfo, Object> queryBuilder = cardDao.queryBuilder();
            queryBuilder.where().eq("className", new SelectArg(canonicalClassName));
            return cardDao.query(queryBuilder.prepare());
        } catch (SQLException ex) {
        }
        return Collections.emptyList();
    }

    public List<CardInfo> findCardsCaseInsensitive(String name) {
        try {
            String sqlName = name.toLowerCase(Locale.ENGLISH).replaceAll("'", "''");
            GenericRawResults<CardInfo> rawResults = cardDao.queryRaw(
                    "select * from " + CardRepository.VERSION_ENTITY_NAME + " where lower_name = '" + sqlName + '\'',
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

    /**
     * Warning, don't use db functions in card's code - it generate heavy db loading in AI simulations. If you
     * need that feature then check for simulation mode. See https://github.com/magefree/mage/issues/7014
     *
     * @param criteria
     * @return
     */
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

    public CardInfo findOldestNonPromoVersionCard(String name) {
        List<CardInfo> allVersions = this.findCards(name);
        if (!allVersions.isEmpty()) {
            allVersions.sort(new OldestNonPromoComparator());
            return allVersions.get(0);
        } else {
            return null;
        }
    }

    static class OldestNonPromoComparator implements Comparator<CardInfo> {
        @Override
        public int compare(CardInfo a, CardInfo b) {
            ExpansionInfo aSet = ExpansionRepository.instance.getSetByCode(a.getSetCode());
            ExpansionInfo bSet = ExpansionRepository.instance.getSetByCode(b.getSetCode());
            if (aSet.getType() == SetType.PROMOTIONAL && bSet.getType() != SetType.PROMOTIONAL) {
                return 1;
            }
            if (bSet.getType() == SetType.PROMOTIONAL && aSet.getType() != SetType.PROMOTIONAL) {
                return -1;
            }
            if (aSet.getReleaseDate().after(bSet.getReleaseDate())) {
                return 1;
            }
            if (aSet.getReleaseDate().before(bSet.getReleaseDate())) {
                return -1;
            }
            return Integer.compare(a.getCardNumberAsInt(), b.getCardNumberAsInt());
        }
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
                DatabaseConnection conn = cardDao.getConnectionSource().getReadWriteConnection(cardDao.getTableName());
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
