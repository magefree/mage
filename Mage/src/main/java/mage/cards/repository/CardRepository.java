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
import mage.constants.CardType;
import mage.constants.SetType;
import mage.constants.SuperType;
import mage.util.RandomUtil;
import org.apache.log4j.Logger;

import java.io.File;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author North, JayDi85
 */
public enum CardRepository {

    instance;

    private static final Logger logger = Logger.getLogger(CardRepository.class);

    // fixes limit for out of memory problems
    private static final AtomicInteger databaseFixes = new AtomicInteger();

    private static final int MAX_DATABASE_FIXES = 10;

    // TODO: delete db version from cards and expansions due un-used (cause dbs re-created on each update now)
    private static final String VERSION_ENTITY_NAME = "card";
    private static final long CARD_DB_VERSION = 54; // raise this if db structure was changed
    private static final long CARD_CONTENT_VERSION = 241; // raise this if new cards were added to the server

    private Dao<CardInfo, Object> cardsDao;

    // store names lists like all cards, lands, etc (it's static data and can be calculated one time only)
    private static final Map<String, Set<String>> namesQueryCache = new HashMap<>();

    // sets with exclusively snow basics
    public static final Set<String> snowLandSetCodes = new HashSet<>(Arrays.asList(
            "CSP",
            "MH1",
            "ME2",
            "MB2"
    ));

    CardRepository() {
        File file = new File("db");
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            ConnectionSource connectionSource = new JdbcConnectionSource(DatabaseUtils.prepareH2Connection(DatabaseUtils.DB_NAME_CARDS, true));

            boolean isObsolete = RepositoryUtil.isDatabaseObsolete(connectionSource, VERSION_ENTITY_NAME, CARD_DB_VERSION);
            boolean isNewBuild = RepositoryUtil.isNewBuildRun(connectionSource, VERSION_ENTITY_NAME, CardRepository.class); // recreate db on new build
            if (isObsolete || isNewBuild) {
                //System.out.println("Local cards db is outdated, cleaning...");
                TableUtils.dropTable(connectionSource, CardInfo.class, true);
            }

            TableUtils.createTableIfNotExists(connectionSource, CardInfo.class);
            cardsDao = DaoManager.createDao(connectionSource, CardInfo.class);
        } catch (SQLException e) {
            Logger.getLogger(CardRepository.class).error("Error creating card repository - " + e, e);
            processMemoryErrors(e);
        }
    }

    private void processMemoryErrors(Exception e) {
        // TODO: implement same logic for set repository, users repository and other db sources?!
        //   or delete workaround with that fix
        // TODO: it's for small servers only (if one game/request can eat all memory), remove after auto-restart implements
        if (e.toString().contains("file") || e.toString().contains("closed")) {
            // errors:
            // - java.lang.IllegalStateException: Reading from nio:xxx/Mage.Server/db/cards.h2.mv.db failed; file length -1 read length 384 at 9384925 [1.4.197/1]
            // - java.lang.IllegalStateException: This store is closed [1.4.197/4]"; SQL statement: xxx
            // reason:
            // - no more free memory, DB can't read big amount of data and broke it
            //
            // steps to reproduce:
            // - run server with low memory like -Xmx200m;
            // - cast card with name choose dialog like Brain Pry;
            // - now server can't add new cards to game (whole server, not current game);
            //
            // possible fix:
            // - try to restart DB
            checkDatabaseHealthAndFix();
        }
    }

    public void saveCards(final List<CardInfo> newCards, long newContentVersion) {
        if (newCards == null || newCards.isEmpty()) {
            return;
        }

        try {
            cardsDao.callBatchTasks(() -> {
                // only add new cards (no updates)
                logger.info("DB: need to add " + newCards.size() + " new cards");
                try {
                    for (CardInfo card : newCards) {
                        cardsDao.create(card);
                    }
                } catch (SQLException e) {
                    Logger.getLogger(CardRepository.class).error("Error adding cards to DB - " + e, e);
                    processMemoryErrors(e);
                }
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
        if (card.getSpellOptionCardName() != null && !card.getSpellOptionCardName().isEmpty()) {
            namesList.add(card.getSpellOptionCardName());
        }
    }

    public static Boolean haveSnowLands(String setCode) {
        return snowLandSetCodes.contains(setCode);
    }

    public synchronized Set<String> getNames() {
        Set<String> names = namesQueryCache.computeIfAbsent("getNames", x -> new TreeSet<>());
        if (!names.isEmpty()) {
            return names;
        }
        try {
            QueryBuilder<CardInfo, Object> qb = cardsDao.queryBuilder();
            qb.distinct().selectColumns("name", "modalDoubleFacedSecondSideName", "secondSideName", "flipCardName", "spellOptionCardName");
            List<CardInfo> results = cardsDao.query(qb.prepare());
            for (CardInfo card : results) {
                addNewNames(card, names);
            }
        } catch (SQLException e) {
            Logger.getLogger(CardRepository.class).error("Error getting names from DB, possible low memory: " + e, e);
            processMemoryErrors(e);
        }
        return names;
    }

    public synchronized Set<String> getNonLandNames() {
        Set<String> names = namesQueryCache.computeIfAbsent("getNonLandNames", x -> new TreeSet<>());
        if (!names.isEmpty()) {
            return names;
        }
        try {
            QueryBuilder<CardInfo, Object> qb = cardsDao.queryBuilder();
            qb.distinct().selectColumns("name", "modalDoubleFacedSecondSideName", "secondSideName", "flipCardName", "spellOptionCardName");
            qb.where().not().like("types", new SelectArg('%' + CardType.LAND.name() + '%'));
            List<CardInfo> results = cardsDao.query(qb.prepare());
            for (CardInfo card : results) {
                addNewNames(card, names);
            }
        } catch (SQLException e) {
            Logger.getLogger(CardRepository.class).error("Error getting non-land names from DB, possible low memory: " + e, e);
            processMemoryErrors(e);
        }
        return names;
    }

    public synchronized Set<String> getNonbasicLandNames() {
        Set<String> names = namesQueryCache.computeIfAbsent("getNonbasicLandNames", x -> new TreeSet<>());
        if (!names.isEmpty()) {
            return names;
        }
        try {
            QueryBuilder<CardInfo, Object> qb = cardsDao.queryBuilder();
            qb.distinct().selectColumns("name", "modalDoubleFacedSecondSideName", "secondSideName", "flipCardName", "spellOptionCardName");
            Where<CardInfo, Object> where = qb.where();
            where.and(
                    where.not().like("supertypes", '%' + SuperType.BASIC.name() + '%'),
                    where.like("types", '%' + CardType.LAND.name() + '%')
            );
            List<CardInfo> results = cardsDao.query(qb.prepare());
            for (CardInfo card : results) {
                addNewNames(card, names);
            }
        } catch (SQLException e) {
            Logger.getLogger(CardRepository.class).error("Error getting non-land names from DB, possible low memory: " + e, e);
            processMemoryErrors(e);
        }
        return names;
    }

    public synchronized Set<String> getNotBasicLandNames() {
        Set<String> names = namesQueryCache.computeIfAbsent("getNotBasicLandNames", x -> new TreeSet<>());
        if (!names.isEmpty()) {
            return names;
        }
        try {
            QueryBuilder<CardInfo, Object> qb = cardsDao.queryBuilder();
            qb.distinct().selectColumns("name", "modalDoubleFacedSecondSideName", "secondSideName", "flipCardName", "spellOptionCardName");
            qb.where().not().like("supertypes", new SelectArg('%' + SuperType.BASIC.name() + '%'));
            List<CardInfo> results = cardsDao.query(qb.prepare());
            for (CardInfo card : results) {
                addNewNames(card, names);
            }
        } catch (SQLException e) {
            Logger.getLogger(CardRepository.class).error("Error getting non-land names from DB, possible low memory: " + e, e);
            processMemoryErrors(e);
        }
        return names;
    }

    public synchronized Set<String> getCreatureNames() {
        Set<String> names = namesQueryCache.computeIfAbsent("getCreatureNames", x -> new TreeSet<>());
        if (!names.isEmpty()) {
            return names;
        }
        try {
            QueryBuilder<CardInfo, Object> qb = cardsDao.queryBuilder();
            qb.distinct().selectColumns("name", "modalDoubleFacedSecondSideName", "secondSideName", "flipCardName", "spellOptionCardName");
            qb.where().like("types", new SelectArg('%' + CardType.CREATURE.name() + '%'));
            List<CardInfo> results = cardsDao.query(qb.prepare());
            for (CardInfo card : results) {
                addNewNames(card, names);
            }
        } catch (SQLException e) {
            Logger.getLogger(CardRepository.class).error("Error getting creature names from DB, possible low memory: " + e, e);
            processMemoryErrors(e);
        }
        return names;
    }

    public synchronized Set<String> getArtifactNames() {
        Set<String> names = namesQueryCache.computeIfAbsent("getArtifactNames", x -> new TreeSet<>());
        if (!names.isEmpty()) {
            return names;
        }
        try {
            QueryBuilder<CardInfo, Object> qb = cardsDao.queryBuilder();
            qb.distinct().selectColumns("name", "modalDoubleFacedSecondSideName", "secondSideName", "flipCardName", "spellOptionCardName");
            qb.where().like("types", new SelectArg('%' + CardType.ARTIFACT.name() + '%'));
            List<CardInfo> results = cardsDao.query(qb.prepare());
            for (CardInfo card : results) {
                addNewNames(card, names);
            }
        } catch (SQLException e) {
            Logger.getLogger(CardRepository.class).error("Error getting artifact names from DB, possible low memory: " + e, e);
            processMemoryErrors(e);
        }
        return names;
    }

    public synchronized Set<String> getNonLandAndNonCreatureNames() {
        Set<String> names = namesQueryCache.computeIfAbsent("getNonLandAndNonCreatureNames", x -> new TreeSet<>());
        if (!names.isEmpty()) {
            return names;
        }
        try {
            QueryBuilder<CardInfo, Object> qb = cardsDao.queryBuilder();
            qb.distinct().selectColumns("name", "modalDoubleFacedSecondSideName", "secondSideName", "flipCardName", "spellOptionCardName");
            Where<CardInfo, Object> where = qb.where();
            where.and(
                    where.not().like("types", '%' + CardType.CREATURE.name() + '%'),
                    where.not().like("types", '%' + CardType.LAND.name() + '%')
            );
            List<CardInfo> results = cardsDao.query(qb.prepare());
            for (CardInfo card : results) {
                addNewNames(card, names);
            }
        } catch (SQLException e) {
            Logger.getLogger(CardRepository.class).error("Error getting non-land and non-creature names from DB, possible low memory: " + e, e);
            processMemoryErrors(e);
        }
        return names;
    }

    public synchronized Set<String> getNonArtifactAndNonLandNames() {
        Set<String> names = namesQueryCache.computeIfAbsent("getNonArtifactAndNonLandNames", x -> new TreeSet<>());
        if (!names.isEmpty()) {
            return names;
        }
        try {
            QueryBuilder<CardInfo, Object> qb = cardsDao.queryBuilder();
            qb.distinct().selectColumns("name", "modalDoubleFacedSecondSideName", "secondSideName", "flipCardName", "spellOptionCardName");
            Where<CardInfo, Object> where = qb.where();
            where.and(
                    where.not().like("types", '%' + CardType.ARTIFACT.name() + '%'),
                    where.not().like("types", '%' + CardType.LAND.name() + '%')
            );
            List<CardInfo> results = cardsDao.query(qb.prepare());
            for (CardInfo card : results) {
                addNewNames(card, names);
            }
        } catch (SQLException e) {
            Logger.getLogger(CardRepository.class).error("Error getting non-artifact non-land names from DB, possible low memory: " + e, e);
            processMemoryErrors(e);
        }
        return names;
    }

    public CardInfo findCard(String setCode, String cardNumber) {
        return findCard(setCode, cardNumber, true);
    }

    public CardInfo findCard(String setCode, String cardNumber, boolean ignoreNightCards) {
        try {
            QueryBuilder<CardInfo, Object> queryBuilder = cardsDao.queryBuilder();
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
            List<CardInfo> result = cardsDao.query(queryBuilder.prepare());
            if (!result.isEmpty()) {
                return result.get(0);
            }
        } catch (SQLException e) {
            Logger.getLogger(CardRepository.class).error("Error finding card from DB: " + e, e);
            processMemoryErrors(e);
        }
        return null;
    }

    public List<String> getClassNames() {
        List<String> names = new ArrayList<>();
        try {
            List<CardInfo> results = cardsDao.queryForAll();
            for (CardInfo card : results) {
                names.add(card.getClassName());
            }
        } catch (SQLException e) {
            Logger.getLogger(CardRepository.class).error("Error getting classnames from DB, possible low memory:" + e, e);
            processMemoryErrors(e);
        }
        return names;
    }

    public List<CardInfo> getMissingCards(List<String> classNames) {
        try {
            QueryBuilder<CardInfo, Object> queryBuilder = cardsDao.queryBuilder();
            queryBuilder.where().not().in("className", classNames);

            return cardsDao.query(queryBuilder.prepare());
        } catch (SQLException e) {
            Logger.getLogger(CardRepository.class).error("Error getting missing cards from DB: " + e, e);
            processMemoryErrors(e);
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
     * @param name                name of the card, or side of the card, to find
     * @param expansion           the set name from which to find the card
     * @param cardNumber          the card number for variant arts in one set
     * @param returnSplitCardHalf whether to return a half of a split card or the corresponding full card.
     *                            Want this `false` when user is searching by either names in a split card so that
     *                            the full card can be found by either name.
     * @return
     */
    public CardInfo findCardWithPreferredSetAndNumber(String name, String expansion, String cardNumber, boolean returnSplitCardHalf) {
        List<CardInfo> cards;

        cards = findCards(name, 0, returnSplitCardHalf, true);
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
     * <p>
     * Note of how the function works:
     * Out of all card types (Split, MDFC, Adventure, Flip, Transform)
     * ONLY Split cards (Fire // Ice) MUST be queried in the DB by the full name when querying by "name".
     * Searching for it by either half will return an incorrect result.
     * ALL the others MUST be queried for by the first half of their full name (i.e. "A" from "A // B")
     * when querying by "name".
     *
     * @param name                the name of the card to search for
     * @param limitByMaxAmount    return max amount of different cards (if 0 then return card from all sets)
     * @param returnSplitCardHalf whether to return a half of a split card or the corresponding full card.
     *                            Want this `false` when user is searching by either names in a split card so that
     *                            the full card can be found by either name.
     *                            Want this `true` when the client is searching for info on both halves to display it.
     * @return a list of the reprints of the card if it was found (up to limitByMaxAmount number),
     * or an empty list if the card was not found.
     * @canCheckDatabaseHealth try to fix database on any errors (use true anytime except fix methods itself)
     */
    public List<CardInfo> findCards(String name, long limitByMaxAmount, boolean returnSplitCardHalf, boolean canCheckDatabaseHealth) {
        List<CardInfo> results;
        QueryBuilder<CardInfo, Object> queryBuilder = cardsDao.queryBuilder();
        if (limitByMaxAmount > 0) {
            queryBuilder.limit(limitByMaxAmount);
        }

        try {
            if (name.contains(" // ")) { //
                // Try to see if it's a split card first. (Split card stored in DB under full card name)
                // Could be made faster by searching assuming it's NOT a split card and first searching by the first
                // half of the name, but this is easier to understand.
                queryBuilder.where().eq("name", new SelectArg(name));
                results = cardsDao.query(queryBuilder.prepare());

                // Result comes back empty, try to search using the first half (could be Adventure, MDFC, etc.)
                if (results.isEmpty()) {
                    String mainCardName = name.split(" // ", 2)[0];
                    queryBuilder.where().eq("name", new SelectArg(mainCardName));
                    results = cardsDao.query(queryBuilder.prepare());  // If still empty, then card can't be found
                }
            } else { // Cannot tell if string represents the full name of a card or only part of it.
                // Assume it is the full card name
                queryBuilder.where().eq("name", new SelectArg(name));
                results = cardsDao.query(queryBuilder.prepare());

                if (results.isEmpty()) {
                    // Nothing found when looking for main name, try looking under the other names
                    queryBuilder.where()
                            .eq("flipCardName", new SelectArg(name)).or()
                            .eq("secondSideName", new SelectArg(name)).or()
                            .eq("spellOptionCardName", new SelectArg(name)).or()
                            .eq("modalDoubleFacedSecondSideName", new SelectArg(name));
                    results = cardsDao.query(queryBuilder.prepare());
                } else {
                    // Check that a full card was found and not a SplitCardHalf
                    // Can be caused by searching for "Fire" instead of "Fire // Ice"
                    CardInfo firstCardInfo = results.get(0);
                    if (firstCardInfo.isSplitCardHalf() && !returnSplitCardHalf) {
                        // Find the main card by its setCode and CardNumber
                        queryBuilder.where()
                                .eq("setCode", new SelectArg(firstCardInfo.setCode)).and()
                                .eq("cardNumber", new SelectArg(firstCardInfo.cardNumber));
                        List<CardInfo> tmpResults = cardsDao.query(queryBuilder.prepare());

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
                        results = cardsDao.query(queryBuilder.prepare());
                    }
                }
            }
            return results;
        } catch (SQLException e) {
            Logger.getLogger(CardRepository.class).error("Error during execution of raw sql statement: " + e, e);
            if (canCheckDatabaseHealth) {
                processMemoryErrors(e);
            }
        }

        return Collections.emptyList();
    }

    public List<CardInfo> findCards(String name, long limitByMaxAmount) {
        return findCards(name, limitByMaxAmount, false, true);
    }

    public List<CardInfo> findCardsByClass(String canonicalClassName) {
        try {
            QueryBuilder<CardInfo, Object> queryBuilder = cardsDao.queryBuilder();
            queryBuilder.where().eq("className", new SelectArg(canonicalClassName));
            return cardsDao.query(queryBuilder.prepare());
        } catch (SQLException e) {
            Logger.getLogger(CardRepository.class).error("Error during execution of raw sql statement" + e, e);
            processMemoryErrors(e);
        }
        return Collections.emptyList();
    }

    /**
     * Warning, don't use db functions in card's code - it generates heavy db loading in AI simulations. If you
     * need that feature then check for simulation mode. See https://github.com/magefree/mage/issues/7014
     * <p>
     * Ignoring night cards by default
     *
     * @param criteria
     * @return
     */
    public List<CardInfo> findCards(CardCriteria criteria) {
        try {
            QueryBuilder<CardInfo, Object> queryBuilder = cardsDao.queryBuilder();
            criteria.buildQuery(queryBuilder);

            return cardsDao.query(queryBuilder.prepare());
        } catch (SQLException e) {
            Logger.getLogger(CardRepository.class).error("Error during execution of card repository query statement: " + e, e);
            processMemoryErrors(e);
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
            ConnectionSource connectionSource = new JdbcConnectionSource(DatabaseUtils.prepareH2Connection(DatabaseUtils.DB_NAME_CARDS, false));
            return RepositoryUtil.getDatabaseVersion(connectionSource, VERSION_ENTITY_NAME + "Content");
        } catch (SQLException e) {
            Logger.getLogger(CardRepository.class).error("Error getting content version from DB - " + e, e);
            processMemoryErrors(e);
        }
        return 0;
    }

    public void setContentVersion(long version) {
        try {
            ConnectionSource connectionSource = new JdbcConnectionSource(DatabaseUtils.prepareH2Connection(DatabaseUtils.DB_NAME_CARDS, false));
            RepositoryUtil.updateVersion(connectionSource, VERSION_ENTITY_NAME + "Content", version);
        } catch (SQLException e) {
            Logger.getLogger(CardRepository.class).error("Error setting content version - " + e, e);
            processMemoryErrors(e);
        }
    }

    public long getContentVersionConstant() {
        return CARD_CONTENT_VERSION;
    }

    public void closeDB(boolean writeCompact) {
        try {
            if (cardsDao != null && cardsDao.getConnectionSource() != null) {
                DatabaseConnection conn = cardsDao.getConnectionSource().getReadWriteConnection(cardsDao.getTableName());
                // TODO: works but generate silent errors in cards.h2.trace.db on app close (maybe new ormlite library version fixed it)
                /*
                at org.h2.jdbc.JdbcStatement.checkClosed(JdbcStatement.java:1175)
                at org.h2.jdbc.JdbcStatement.getUpdateCount(JdbcStatement.java:290)
                at com.j256.ormlite.jdbc.JdbcDatabaseConnection.executeStatement(JdbcDatabaseConnection.java:141)
                at mage.cards.repository.CardRepository.closeDB(CardRepository.java:651)
                 */
                if (writeCompact) {
                    conn.executeStatement("SHUTDOWN COMPACT", DatabaseConnection.DEFAULT_RESULT_FLAGS); // compact data and rewrite whole db
                } else {
                    conn.executeStatement("SHUTDOWN IMMEDIATELY", DatabaseConnection.DEFAULT_RESULT_FLAGS); // close without any writes
                }
                cardsDao.getConnectionSource().releaseConnection(conn);
            }
        } catch (SQLException ignore) {
        }
    }

    public void openDB() {
        try {
            ConnectionSource connectionSource = new JdbcConnectionSource(DatabaseUtils.prepareH2Connection(DatabaseUtils.DB_NAME_CARDS, true));
            cardsDao = DaoManager.createDao(connectionSource, CardInfo.class);
        } catch (SQLException e) {
            Logger.getLogger(CardRepository.class).error("Error opening card repository - " + e, e);
        }
    }

    public void printDatabaseStats(String info) {
        List<List<String>> allSettings = querySQL("SELECT NAME, VALUE FROM INFORMATION_SCHEMA.SETTINGS");
        if (allSettings == null) {
            return;
        }

        // cache
        logger.info("Database cache settings (" + info + "):");
        allSettings.stream().filter(values -> values.get(0).equals("CACHE_SIZE")).forEach(values -> {
            logger.info(" - cache size, setup: " + values.get(1) + " kb");
        });
        allSettings.stream().filter(values -> values.get(0).equals("info.CACHE_MAX_SIZE")).forEach(values -> {
            logger.info(" - cache size, max: " + values.get(1) + " mb");
        });
        allSettings.stream().filter(values -> values.get(0).equals("info.CACHE_SIZE")).forEach(values -> {
            logger.info(" - cache size, current: " + values.get(1) + " mb");
        });

        // memory
        allSettings = querySQL("SELECT MEMORY_FREE(), MEMORY_USED()");
        if (allSettings == null) {
            return;
        }
        logger.info("Database memory stats (" + info + "):");
        logger.info(" - free: " + allSettings.get(0).get(0) + " kb");
        logger.info(" - used: " + allSettings.get(0).get(1) + " kb");
    }

    /**
     * Exec any SQL query and return result table as string values
     */
    public List<List<String>> querySQL(String sql) {
        try {
            GenericRawResults<String[]> query = cardsDao.queryRaw(sql);
            return query.getResults().stream()
                    .map(Arrays::asList)
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            logger.error("Can't query sql due error: " + sql + " - " + e, e);
            return null;
        }
    }

    /**
     * Exec any SQL code without result. Can be used to change db settings like SET xxx = YYY
     */
    public void execSQL(String sql) {
        try {
            cardsDao.executeRaw(sql);
        } catch (SQLException e) {
            logger.error("Can't exec sql due error: " + sql + " - " + e, e);
        }
    }

    private static CardInfo safeFindKnownCard() {
        // safe find of known card with memory/db fixes
        return instance.findCards("Silvercoat Lion", 1, false, false)
                .stream()
                .findFirst()
                .orElse(null);
    }

    public static boolean checkDatabaseHealthAndFix() {
        // see details in processMemoryErrors

        // card must exist
        CardInfo cardInfo = safeFindKnownCard();
        if (cardInfo != null) {
            logger.info("Database: checking broken status... GOOD");
            return true;
        }

        Logger.getLogger(CardRepository.class).error("Database: checking broken status... BAD");

        if (databaseFixes.incrementAndGet() > MAX_DATABASE_FIXES) {
            logger.error("Critical error: no more db memory fixes allows, server must be restarted");
            return false;
        }

        // DB seems to have a problem - try to restart the DB (useless in 99% due out of memory problems)
        instance.closeDB(false);
        instance.openDB();
        cardInfo = safeFindKnownCard();
        if (cardInfo != null) {
            logger.warn(String.format("Database: trying to restart (%d try)... GOOD - db fixed", databaseFixes.get()));
            return true;
        } else {
            // TODO: add here:
            //  - admin notification by email
            //  - players notification by message,
            //  - timer with auto-restart feature (restart java app, restart docker container - e.g. docker health check)
            //  see related issue: https://github.com/magefree/mage/issues/8130
            logger.warn(String.format("Database: trying to restart  (%d try)... FAIL - server must be restarted", databaseFixes.get()));
            return false;
        }
    }
}
