package mage.cards.repository;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.table.TableUtils;
import mage.constants.CardType;
import mage.constants.SetType;
import mage.constants.SuperType;
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

    private static final String JDBC_URL = "jdbc:h2:file:./db/cards.h2;AUTO_SERVER=TRUE;IGNORECASE=TRUE";
    private static final String VERSION_ENTITY_NAME = "card";
    // raise this if db structure was changed
    private static final long CARD_DB_VERSION = 54;
    // raise this if new cards were added to the server
    private static final long CARD_CONTENT_VERSION = 241;
    private Dao<CardInfo, Object> cardDao;
    private Set<String> classNames;

    // sets with exclusively snow basics
    public static final Set<String> snowLandSetCodes = new HashSet<>(Arrays.asList(
            "CSP",
            "MH1",
            "ME2"
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
        } catch (SQLException ex) {
            Logger.getLogger(CardRepository.class).error("Error creating card repository - ", ex);
        }
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
        } catch (Exception ex) {
            //
        }
    }

    private void addNewNames(CardInfo card, Set<String> namesList) {
        // require before call: qb.distinct().selectColumns("name", "modalDoubleFacedSecondSideName"...);

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
        if (card.getModalDoubleFacedSecondSideName() != null && !card.getModalDoubleFacedSecondSideName().isEmpty()) {
            namesList.add(card.getModalDoubleFacedSecondSideName());
        }
        if (card.getFlipCardName() != null && !card.getFlipCardName().isEmpty()) {
            namesList.add(card.getFlipCardName());
        }
        if (card.getMeldsToCardName() != null && !card.getMeldsToCardName().isEmpty()) {
            namesList.add(card.getMeldsToCardName());
        }
    }

    public static Boolean haveSnowLands(String setCode) {
        return snowLandSetCodes.contains(setCode);
    }

    public Set<String> getNames() {
        Set<String> names = new TreeSet<>();
        try {
            QueryBuilder<CardInfo, Object> qb = cardDao.queryBuilder();
            qb.distinct().selectColumns("name", "modalDoubleFacedSecondSideName", "secondSideName", "flipCardName");
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
            qb.distinct().selectColumns("name", "modalDoubleFacedSecondSideName", "secondSideName", "flipCardName");
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
            qb.distinct().selectColumns("name", "modalDoubleFacedSecondSideName", "secondSideName", "flipCardName");
            Where<CardInfo, Object> where = qb.where();
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
            qb.distinct().selectColumns("name", "modalDoubleFacedSecondSideName", "secondSideName", "flipCardName");
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
            qb.distinct().selectColumns("name", "modalDoubleFacedSecondSideName", "secondSideName", "flipCardName");
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
            qb.distinct().selectColumns("name", "modalDoubleFacedSecondSideName", "secondSideName", "flipCardName");
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
            qb.distinct().selectColumns("name", "modalDoubleFacedSecondSideName", "secondSideName", "flipCardName");
            Where<CardInfo, Object> where = qb.where();
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
            qb.distinct().selectColumns("name", "modalDoubleFacedSecondSideName", "secondSideName", "flipCardName");
            Where<CardInfo, Object> where = qb.where();
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

                // some double faced cards can use second side card with same number as main side
                // (example: vow - 65 - Jacob Hauken, Inspector), so make priority for main side first
                queryBuilder.orderBy("nightCard", true);
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

    public CardInfo findPreferredCoreExpansionCard(String name) {
        return findPreferredCoreExpansionCard(name, "");
    }

    public CardInfo findPreferredCoreExpansionCard(String name, String preferredSetCode) {
        List<CardInfo> cards;
        cards = findCards(name);

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

                    if (preferredSetCode.equals(set.getCode())) {
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

    /**
     * Function to find a card by name from a specific set.
     * Used for building cubes, packs, and for ensuring that dual faces and split cards have sides/halves from
     * the same set and variant art.
     *
     * @param name                  name of the card, or side of the card, to find
     * @param expansion             the set name from which to find the card
     * @param cardNumber            the card number for variant arts in one set
     * @param returnSplitCardHalf   whether to return a half of a split card or the corresponding full card.
     *                              Want this `false` when user is searching by either names in a split card so that
     *                              the full card can be found by either name.
     * @return
     */
    public CardInfo findCardWithPreferredSetAndNumber(String name, String expansion, String cardNumber, boolean returnSplitCardHalf) {
        List<CardInfo> cards;

        cards = findCards(name, 0, returnSplitCardHalf);
        CardInfo bestCard = cards.stream()
                .filter(card -> expansion == null || expansion.equalsIgnoreCase(card.getSetCode()))
                .filter(card -> cardNumber == null || cardNumber.equals(card.getCardNumber()))
                .findFirst()
                .orElse(null);

        if (bestCard != null) {
            return bestCard;
        } else {
            return findPreferredCoreExpansionCard(name);
        }
    }

    public CardInfo findCardWithPreferredSetAndNumber(String name, String expansion, String cardNumber) {
        return findCardWithPreferredSetAndNumber(name, expansion, cardNumber, false);
    }

    public List<CardInfo> findCards(String name) {
        return findCards(name, 0);
    }

    /**
     * Find a card's reprints from all sets.
     * It allows for cards to be searched by their full name, or in the case of multi-name cards of the type "A // B"
     * To search for them using "A", "B", or "A // B".
     *
     * Note of how the function works:
     *      Out of all card types (Split, MDFC, Adventure, Flip, Transform)
     *      ONLY Split cards (Fire // Ice) MUST be queried in the DB by the full name when querying by "name".
     *      Searching for it by either half will return an incorrect result.
     *      ALL the others MUST be queried for by the first half of their full name (i.e. "A" from "A // B")
     *      when querying by "name".
     *
     * @param name                  the name of the card to search for
     * @param limitByMaxAmount      return max amount of different cards (if 0 then return card from all sets)
     * @param returnSplitCardHalf   whether to return a half of a split card or the corresponding full card.
     *                              Want this `false` when user is searching by either names in a split card so that
     *                              the full card can be found by either name.
     *                              Want this `true` when the client is searching for info on both halves to display it.
     * @return                      a list of the reprints of the card if it was found (up to limitByMaxAmount number),
     *                              or an empty list if the card was not found.
     */
    public List<CardInfo> findCards(String name, long limitByMaxAmount, boolean returnSplitCardHalf) {
        List<CardInfo> results;
        QueryBuilder<CardInfo, Object> queryBuilder = cardDao.queryBuilder();
        if (limitByMaxAmount > 0) {
            queryBuilder.limit(limitByMaxAmount);
        }

        try {
            if (name.contains(" // ")) { //
                // Try to see if it's a split card first. (Split card stored in DB under full card name)
                // Could be made faster by searching assuming it's NOT a split card and first searching by the first
                // half of the name, but this is easier to understand.
                queryBuilder.where().eq("name", new SelectArg(name));
                results = cardDao.query(queryBuilder.prepare());

                // Result comes back empty, try to search using the first half (could be Adventure, MDFC, etc.)
                if (results.isEmpty()) {
                    String mainCardName = name.split(" // ", 2)[0];
                    queryBuilder.where().eq("name", new SelectArg(mainCardName));
                    results = cardDao.query(queryBuilder.prepare());  // If still empty, then card can't be found
                }
            } else { // Cannot tell if string represents the full name of a card or only part of it.
                // Assume it is the full card name
                queryBuilder.where().eq("name", new SelectArg(name));
                results = cardDao.query(queryBuilder.prepare());

                if (results.isEmpty()) {
                    // Nothing found when looking for main name, try looking under the other names
                    queryBuilder.where()
                            .eq("flipCardName",                     new SelectArg(name)).or()
                            .eq("secondSideName",                   new SelectArg(name)).or()
                            .eq("adventureSpellName",               new SelectArg(name)).or()
                            .eq("modalDoubleFacedSecondSideName",   new SelectArg(name));
                    results = cardDao.query(queryBuilder.prepare());
                } else {
                    // Check that a full card was found and not a SplitCardHalf
                    // Can be caused by searching for "Fire" instead of "Fire // Ice"
                    CardInfo firstCardInfo = results.get(0);
                    if (firstCardInfo.isSplitCardHalf() && !returnSplitCardHalf) {
                        // Find the main card by its setCode and CardNumber
                        queryBuilder.where()
                                .eq("setCode", new SelectArg(firstCardInfo.setCode)).and()
                                .eq("cardNumber", new SelectArg(firstCardInfo.cardNumber));
                        List<CardInfo> tmpResults = cardDao.query(queryBuilder.prepare());

                        String fullSplitCardName = null;
                        for (CardInfo cardInfo : tmpResults) {
                            if (cardInfo.isSplitCard()) {
                                fullSplitCardName = cardInfo.name;
                                break;
                            }
                        }
                        if (fullSplitCardName == null) {
                            return Collections.emptyList();
                        }

                        queryBuilder.where().eq("name", new SelectArg(fullSplitCardName));
                        results = cardDao.query(queryBuilder.prepare());
                    }
                }
            }
            return results;
        } catch (SQLException ex) {
            Logger.getLogger(CardRepository.class).error("Error during execution of raw sql statement", ex);
        }

        return Collections.emptyList();
    }

    public List<CardInfo> findCards(String name, long limitByMaxAmount) {
        return findCards(name, limitByMaxAmount, false);
    }

    public List<CardInfo> findCardsByClass(String canonicalClassName) {
        try {
            QueryBuilder<CardInfo, Object> queryBuilder = cardDao.queryBuilder();
            queryBuilder.where().eq("className", new SelectArg(canonicalClassName));
            return cardDao.query(queryBuilder.prepare());
        } catch (SQLException ex) {
            Logger.getLogger(CardRepository.class).error("Error during execution of raw sql statement", ex);
        }
        return Collections.emptyList();
    }

    /**
     * Warning, don't use db functions in card's code - it generates heavy db loading in AI simulations. If you
     * need that feature then check for simulation mode. See https://github.com/magefree/mage/issues/7014
     *
     * Ignoring night cards by default
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
