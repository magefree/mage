package mage.cards;

import mage.ObjectColor;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.collation.BoosterCollator;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.SetType;
import mage.filter.FilterMana;
import mage.util.CardUtil;
import mage.util.RandomUtil;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author BetaSteward_at_googlemail.com
 */
public abstract class ExpansionSet implements Serializable {

    private static final Logger logger = Logger.getLogger(ExpansionSet.class);
    public static final CardGraphicInfo NON_FULL_USE_VARIOUS = new CardGraphicInfo(null, true);
    public static final CardGraphicInfo FULL_ART_BFZ_VARIOUS = new CardGraphicInfo(FrameStyle.BFZ_FULL_ART_BASIC, true);
    public static final CardGraphicInfo FULL_ART_ZEN_VARIOUS = new CardGraphicInfo(FrameStyle.ZEN_FULL_ART_BASIC, true);

    public static class SetCardInfo implements Serializable {

        private final String name;
        private final String cardNumber;
        private final Rarity rarity;
        private final Class<?> cardClass;
        private final CardGraphicInfo graphicInfo;

        public SetCardInfo(String name, int cardNumber, Rarity rarity, Class<?> cardClass) {
            this(name, String.valueOf(cardNumber), rarity, cardClass, null);
        }

        public SetCardInfo(String name, String cardNumber, Rarity rarity, Class<?> cardClass) {
            this(name, cardNumber, rarity, cardClass, null);
        }

        public SetCardInfo(String name, int cardNumber, Rarity rarity, Class<?> cardClass, CardGraphicInfo graphicInfo) {
            this(name, String.valueOf(cardNumber), rarity, cardClass, graphicInfo);
        }

        public SetCardInfo(String name, String cardNumber, Rarity rarity, Class<?> cardClass, CardGraphicInfo graphicInfo) {
            this.name = name;
            this.cardNumber = cardNumber;
            this.rarity = rarity;
            this.cardClass = cardClass;
            this.graphicInfo = graphicInfo;
        }

        public String getName() {
            return this.name;
        }

        public String getCardNumber() {
            return this.cardNumber;
        }

        public int getCardNumberAsInt() {
            return CardUtil.parseCardNumberAsInt(this.cardNumber);
        }

        public Rarity getRarity() {
            return this.rarity;
        }

        public Class<?> getCardClass() {
            return this.cardClass;
        }

        public CardGraphicInfo getGraphicInfo() {
            return this.graphicInfo;
        }

        public boolean isFullArt() {
            return this.graphicInfo != null
                    && this.graphicInfo.getFrameStyle() != null
                    && this.graphicInfo.getFrameStyle().isFullArt();
        }
    }

    private static enum ExpansionSetComparator implements Comparator<ExpansionSet> {
        instance;

        @Override
        public int compare(ExpansionSet lhs, ExpansionSet rhs) {
            return lhs.getReleaseDate().after(rhs.getReleaseDate()) ? -1 : 1;
        }
    }

    public static ExpansionSetComparator getComparator() {
        return ExpansionSetComparator.instance;
    }

    protected final List<SetCardInfo> cards = new ArrayList<>();

    protected String name;
    protected String code;
    protected Date releaseDate;
    protected ExpansionSet parentSet;
    protected SetType setType;

    // TODO: 03.10.2018, hasBasicLands can be removed someday -- it's uses to optimize lands search in deck generation and lands adding (search all available lands from sets)
    protected boolean hasBasicLands = true;

    protected String blockName;
    protected boolean hasBoosters = false;
    protected int numBoosterSpecial;

    protected int numBoosterLands;

    // if ratioBoosterSpecialLand > 0, one basic land may be replaced with a special card
    // with probability ratioBoosterSpecialLandNumerator / ratioBoosterSpecialLand
    protected int ratioBoosterSpecialLand = 0;
    protected int ratioBoosterSpecialLandNumerator = 1;

    // if ratioBoosterSpecialCommon > 0, one common may be replaced with a special card
    // with probability 1 / ratioBoosterSpecialCommon
    protected int ratioBoosterSpecialCommon = 0;

    // if ratioBoosterSpecialRare > 0, one uncommon or rare is always replaced with a special card
    // probability that a rare rather than an uncommon is replaced is 1 / ratioBoosterSpecialRare
    // probability that the replacement card for a rare is a mythic is 1 / ratioBoosterSpecialMythic
    protected double ratioBoosterSpecialRare = 0;
    protected double ratioBoosterSpecialMythic;

    protected int numBoosterCommon;
    protected int numBoosterUncommon;
    protected int numBoosterRare;
    protected int numBoosterDoubleFaced; // -1 = include normally 0 = exclude  1-n = include explicit
    protected double ratioBoosterMythic;

    protected boolean hasUnbalancedColors = false;
    protected boolean hasOnlyMulticolorCards = false;

    protected int maxCardNumberInBooster; // used to omit cards with collector numbers beyond the regular cards in a set for boosters

    protected final EnumMap<Rarity, List<CardInfo>> savedCards = new EnumMap<>(Rarity.class);
    protected final EnumMap<Rarity, List<CardInfo>> savedSpecialCards = new EnumMap<>(Rarity.class);
    protected final Map<String, CardInfo> inBoosterMap = new HashMap<>();

    public ExpansionSet(String name, String code, Date releaseDate, SetType setType) {
        this.name = name;
        this.code = code;
        this.releaseDate = releaseDate;
        this.setType = setType;
        this.maxCardNumberInBooster = Integer.MAX_VALUE;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public int getReleaseYear() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.getReleaseDate());
        return cal.get(Calendar.YEAR);
    }

    public ExpansionSet getParentSet() {
        return parentSet;
    }

    public SetType getSetType() {
        return setType;
    }

    public String getBlockName() {
        return blockName;
    }

    public List<SetCardInfo> getSetCardInfo() {
        return cards;
    }

    @Override
    public String toString() {
        return name;
    }

    public List<SetCardInfo> findCardInfoByClass(Class<?> clazz) {
        return cards.stream().filter(info -> info.getCardClass().equals(clazz)).collect(Collectors.toList());
    }

    public List<Card> create15CardBooster() {
        // Forces 15 card booster packs.
        // if the packs are too small, it adds commons to fill it out.
        // if the packs are too big, it removes the first cards.
        // since it adds lands then commons before uncommons
        // and rares this should be the least disruptive.
        List<Card> theBooster = this.createBooster();

        if (15 > theBooster.size()) {
            List<CardInfo> commons = getCardsByRarity(Rarity.COMMON);
            while (15 > theBooster.size() && !commons.isEmpty()) {
                addToBooster(theBooster, commons);
                if (commons.isEmpty()) {
                    commons = getCardsByRarity(Rarity.COMMON);
                }
            }
        }

        while (theBooster.size() > 15) {
            theBooster.remove(0);
        }

        return theBooster;
    }

    protected void addToBooster(List<Card> booster, List<CardInfo> cards) {
        if (!cards.isEmpty()) {
            CardInfo cardInfo = cards.remove(RandomUtil.nextInt(cards.size()));
            if (cardInfo != null) {
                Card card = cardInfo.getCard();
                if (card != null) {
                    booster.add(card);
                }
            }
        }
    }

    public BoosterCollator createCollator() {
        return null;
    }

    public List<Card> createBooster() {
        BoosterCollator collator = createCollator();
        if (collator != null) {
            return createBoosterUsingCollator(collator);
        }

        for (int i = 0; i < 100; i++) {//don't want to somehow loop forever
            List<Card> booster = tryBooster();
            if (boosterIsValid(booster)) {
                return booster;
            }
        }

        // return random booster if can't do valid
        logger.error(String.format("Can't generate valid booster for set [%s - %s]", this.getCode(), this.getName()));
        return tryBooster();
    }

    private List<Card> createBoosterUsingCollator(BoosterCollator collator) {
        synchronized (inBoosterMap) {
            if (inBoosterMap.isEmpty()) {
                generateBoosterMap();
            }
        }
        return collator
                .makeBooster()
                .stream()
                .map(inBoosterMap::get)
                .map(CardInfo::getCard)
                .collect(Collectors.toList());
    }

    protected void generateBoosterMap() {
        CardRepository
                .instance
                .findCards(new CardCriteria().setCodes(code))
                .stream()
                .forEach(cardInfo -> inBoosterMap.put(cardInfo.getCardNumber(), cardInfo));
        // get basic lands from parent set if this set doesn't have them
        if (!hasBasicLands && parentSet != null) {
            String parentCode = parentSet.code;
            CardRepository
                .instance
                .findCards(new CardCriteria().setCodes(parentCode).rarities(Rarity.LAND))
                .stream()
                .forEach(cardInfo -> inBoosterMap.put(parentCode + "_" + cardInfo.getCardNumber(), cardInfo));
        }
    }

    protected boolean boosterIsValid(List<Card> booster) {
        if (!validateCommonColors(booster)) {
            return false;
        }

        if (!validateUncommonColors(booster)) {
            return false;
        }

        // TODO: add partner check
        // TODO: add booster size check?
        return true;
    }

    private static ObjectColor getColorForValidate(Card card) {
        ObjectColor color = card.getColor();
        // treat colorless nonland cards with exactly one ID color as cards of that color
        // (e.g. devoid, emerge, spellbombs... but not mana fixing artifacts)
        if (color.isColorless() && !card.isLand()) {
            FilterMana colorIdentity = card.getColorIdentity();
            if (colorIdentity.getColorCount() == 1) {
                return new ObjectColor(colorIdentity.toString());
            }
        }
        return color;
    }

    protected boolean validateCommonColors(List<Card> booster) {
        List<ObjectColor> commonColors = booster.stream()
                .filter(card -> card.getRarity() == Rarity.COMMON)
                .map(ExpansionSet::getColorForValidate)
                .collect(Collectors.toList());

        // for multicolor sets, count not just the colors present at common,
        // but also the number of color combinations (guilds/shards/wedges)
        // e.g. a booster with three UB commons, three RW commons and four G commons
        // has all five colors but isn't "balanced"
        ObjectColor colorsRepresented = new ObjectColor();
        Set<ObjectColor> colorCombinations = new HashSet<>();
        int colorlessCountPlusOne = 1;

        for (ObjectColor color : commonColors) {
            colorCombinations.add(color);
            int colorCount = color.getColorCount();
            if (colorCount == 0) {
                ++colorlessCountPlusOne;
            } else if (colorCount > 1 && !hasOnlyMulticolorCards) {
                // to prevent biasing toward multicolor over monocolor cards,
                // count them as one of their colors chosen at random
                List<ObjectColor> multiColor = color.getColors();
                colorsRepresented.addColor(multiColor.get(RandomUtil.nextInt(multiColor.size())));
            } else {
                colorsRepresented.addColor(color);
            }
        }

        int colors = Math.min(colorsRepresented.getColorCount(), colorCombinations.size());

        // if booster has all five colors in five unique combinations, or if it has
        // one card per color and all but one of the rest are colorless, accept it
        // ("all but one" adds some leeway for sets with small boosters)
        if (colors >= Math.min(5, commonColors.size() - colorlessCountPlusOne)) return true;
        // otherwise, if booster is missing more than one color, reject it
        if (colors < 4) return false;
        // for Torment and Judgment, always accept boosters with four out of five colors
        if (hasUnbalancedColors) return true;
        // if a common was replaced by a special card, increase the chance to accept four colors
        if (commonColors.size() < numBoosterCommon) ++colorlessCountPlusOne;

        // otherwise, stochiastically treat each colorless card as 1/5 of a card of the missing color
        return (RandomUtil.nextDouble() > Math.pow(0.8, colorlessCountPlusOne));
    }

    private static final ObjectColor COLORLESS = new ObjectColor();

    protected boolean validateUncommonColors(List<Card> booster) {
        List<ObjectColor> uncommonColors = booster.stream()
                .filter(card -> card.getRarity() == Rarity.UNCOMMON)
                .map(ExpansionSet::getColorForValidate)
                .collect(Collectors.toList());

        // if there are only two uncommons, they can be the same color
        if (uncommonColors.size() < 3) return true;
        // boosters of artifact sets can have all colorless uncommons
        if (uncommonColors.contains(COLORLESS)) return true;
        // otherwise, reject if all uncommons are the same color combination
        return (new HashSet<>(uncommonColors).size() > 1);
    }

    protected boolean checkMythic() {
        return ratioBoosterMythic > 0 && ratioBoosterMythic * RandomUtil.nextDouble() <= 1;
    }

    protected boolean checkSpecialMythic() {
        return ratioBoosterSpecialMythic > 0 && ratioBoosterSpecialMythic * RandomUtil.nextDouble() <= 1;
    }

    public List<Card> tryBooster() {
        List<Card> booster = new ArrayList<>();
        if (!hasBoosters) {
            return booster;
        }

        if (numBoosterLands > 0) {
            List<CardInfo> specialLands = getSpecialCardsByRarity(Rarity.LAND);
            List<CardInfo> basicLands = getCardsByRarity(Rarity.LAND);
            for (int i = 0; i < numBoosterLands; i++) {
                if (ratioBoosterSpecialLand > 0 && RandomUtil.nextInt(ratioBoosterSpecialLand) < ratioBoosterSpecialLandNumerator) {
                    addToBooster(booster, specialLands);
                } else {
                    addToBooster(booster, basicLands);
                }
            }
        }

        int numCommonsToGenerate = numBoosterCommon;
        int numSpecialToGenerate = numBoosterSpecial;
        if (ratioBoosterSpecialCommon > 0 && RandomUtil.nextInt(ratioBoosterSpecialCommon) < 1) {
            --numCommonsToGenerate;
            ++numSpecialToGenerate;
        }

        List<CardInfo> commons = getCardsByRarity(Rarity.COMMON);
        for (int i = 0; i < numCommonsToGenerate; i++) {
            addToBooster(booster, commons);
        }

        int numUncommonsToGenerate = numBoosterUncommon;
        int numRaresToGenerate = numBoosterRare;
        if (ratioBoosterSpecialRare > 0) {
            Rarity specialRarity = Rarity.UNCOMMON;
            if (ratioBoosterSpecialRare * RandomUtil.nextDouble() <= 1) {
                specialRarity = (checkSpecialMythic() ? Rarity.MYTHIC : Rarity.RARE);
                --numRaresToGenerate;
            } else {
                --numUncommonsToGenerate;
            }
            addToBooster(booster, getSpecialCardsByRarity(specialRarity));
        }

        List<CardInfo> uncommons = getCardsByRarity(Rarity.UNCOMMON);
        for (int i = 0; i < numUncommonsToGenerate; i++) {
            addToBooster(booster, uncommons);
        }

        if (numRaresToGenerate > 0) {
            List<CardInfo> rares = getCardsByRarity(Rarity.RARE);
            List<CardInfo> mythics = getCardsByRarity(Rarity.MYTHIC);
            for (int i = 0; i < numRaresToGenerate; i++) {
                addToBooster(booster, checkMythic() ? mythics : rares);
            }
        }

        if (numBoosterDoubleFaced > 0) {
            addDoubleFace(booster);
        }

        if (numSpecialToGenerate > 0) {
            addSpecialCards(booster, numSpecialToGenerate);
        }

        return booster;
    }

    /* add double faced card for Innistrad booster
     * rarity near as the normal distribution
     */
    protected void addDoubleFace(List<Card> booster) {
        Rarity rarity;
        for (int i = 0; i < numBoosterDoubleFaced; i++) {
            int rarityKey = RandomUtil.nextInt(121);
            if (rarityKey < 66) {
                rarity = Rarity.COMMON;
            } else if (rarityKey < 108) {
                rarity = Rarity.UNCOMMON;
            } else if (rarityKey < 120) {
                rarity = Rarity.RARE;
            } else {
                rarity = Rarity.MYTHIC;
            }
            addToBooster(booster, getSpecialCardsByRarity(rarity));
        }
    }

    protected void addSpecialCards(List<Card> booster, int number) {
        List<CardInfo> specialCards = getCardsByRarity(Rarity.SPECIAL);
        for (int i = 0; i < number; i++) {
            addToBooster(booster, specialCards);
        }
    }

    public static Date buildDate(int year, int month, int day) {
        // The month starts with 0 = jan ... dec = 11
        return new GregorianCalendar(year, month - 1, day).getTime();
    }

    public boolean hasBoosters() {
        return hasBoosters;
    }

    public boolean hasBasicLands() {
        return hasBasicLands;
    }

    public final synchronized List<CardInfo> getCardsByRarity(Rarity rarity) {
        List<CardInfo> savedCardInfos = savedCards.get(rarity);
        if (savedCardInfos == null) {
            savedCardInfos = findCardsByRarity(rarity);
            savedCards.put(rarity, savedCardInfos);
        }
        // Return a copy of the saved cards information, as not to let modify the original.
        return new ArrayList<>(savedCardInfos);
    }

    public final synchronized List<CardInfo> getSpecialCardsByRarity(Rarity rarity) {
        List<CardInfo> savedCardInfos = savedSpecialCards.get(rarity);
        if (savedCardInfos == null) {
            savedCardInfos = findSpecialCardsByRarity(rarity);
            savedSpecialCards.put(rarity, savedCardInfos);
        }
        // Return a copy of the saved cards information, as not to let modify the original.
        return new ArrayList<>(savedCardInfos);
    }

    protected List<CardInfo> findCardsByRarity(Rarity rarity) {
        // get basic lands from parent set if this set doesn't have them
        if (rarity == Rarity.LAND && !hasBasicLands && parentSet != null) {
            return parentSet.getCardsByRarity(rarity);
        }

        List<CardInfo> cardInfos = CardRepository.instance.findCards(new CardCriteria()
                .setCodes(this.code)
                .rarities(rarity));

        cardInfos.removeIf(next -> (
                next.getCardNumberAsInt() > maxCardNumberInBooster
                || next.getCardNumber().contains("*")
                || next.getCardNumber().contains("+")));

        // special slot cards must not also appear in regular slots of their rarity
        // special land slot cards must not appear in regular common slots either
        List<CardInfo> specialCards = getSpecialCardsByRarity(rarity);
        if (rarity == Rarity.COMMON && ratioBoosterSpecialLand > 0) {
            specialCards.addAll(getSpecialCardsByRarity(Rarity.LAND));
        }
        cardInfos.removeAll(specialCards);
        return cardInfos;
    }

    /**
     * "Special cards" are cards that have common/uncommon/rare/mythic rarities
     * but can only appear in a specific slot in boosters. Examples are DFCs in
     * Innistrad sets and common nonbasic lands in many post-2018 sets.
     *
     * Note that Rarity.SPECIAL and Rarity.BONUS cards are not normally treated
     * as "special cards" because by default boosters don't even have slots for
     * those rarities.
     *
     * Also note that getCardsByRarity calls getSpecialCardsByRarity to exclude
     * special cards from non-special booster slots, so sets that override this
     * method must not call getCardsByRarity in it or infinite recursion will occur.
     */
    protected List<CardInfo> findSpecialCardsByRarity(Rarity rarity) {
        List<CardInfo> cardInfos = new ArrayList<>();

        // if set has special land slot, assume all common lands are special cards
        if (rarity == Rarity.LAND && ratioBoosterSpecialLand > 0) {
            cardInfos.addAll(CardRepository.instance.findCards(new CardCriteria()
                    .setCodes(this.code)
                    .rarities(Rarity.COMMON)
                    .types(CardType.LAND)));
        }

        // if set has special slot(s) for DFCs, they are special cards
        if (numBoosterDoubleFaced > 0) {
            cardInfos.addAll(CardRepository.instance.findCards(new CardCriteria()
                    .setCodes(this.code)
                    .rarities(rarity)
                    .doubleFaced(true)));
        }

        cardInfos.removeIf(next -> (
                next.getCardNumberAsInt() > maxCardNumberInBooster
                || next.getCardNumber().contains("*")
                || next.getCardNumber().contains("+")));

        return cardInfos;
    }

    public int getMaxCardNumberInBooster() {
        return maxCardNumberInBooster;
    }

    public int getNumBoosterDoubleFaced() {
        return numBoosterDoubleFaced;
    }

}
