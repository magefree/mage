
package mage.cards;

import mage.ObjectColor;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.Rarity;
import mage.constants.SetType;
import mage.util.CardUtil;
import mage.util.RandomUtil;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author BetaSteward_at_googlemail.com
 */
public abstract class ExpansionSet implements Serializable {

    public final static CardGraphicInfo NON_FULL_USE_VARIOUS = new CardGraphicInfo(null, true);
    public final static CardGraphicInfo FULL_ART_BFZ_VARIOUS = new CardGraphicInfo(FrameStyle.BFZ_FULL_ART_BASIC, true);

    public class SetCardInfo implements Serializable {

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
    }

    protected final List<SetCardInfo> cards = new ArrayList<>();

    protected String name;
    protected String code;
    protected Date releaseDate;
    protected ExpansionSet parentSet;
    protected SetType setType;
    protected boolean hasBasicLands = true;

    protected String blockName;
    protected boolean hasBoosters = false;
    protected int numBoosterSpecial;

    protected int numBoosterLands;
    protected int ratioBoosterSpecialLand = 0; // if > 0 basic lands are replaced with special land with probability ratioBoosterSpecialLandNumerator / ratioBoosterSpecialLand
    protected int ratioBoosterSpecialLandNumerator = 1;

    protected int numBoosterCommon;
    protected int numBoosterUncommon;
    protected int numBoosterRare;
    protected int numBoosterDoubleFaced; // -1 = include normally 0 = exclude  1-n = include explicit
    protected int ratioBoosterMythic;

    protected boolean needsLegendCreature = false;
    protected boolean validateBoosterColors = true;
    protected double rejectMissingColorProbability = 0.8;
    protected double rejectSameColorUncommonsProbability = 0.8;

    protected int maxCardNumberInBooster; // used to omit cards with collector numbers beyond the regular cards in a set for boosters

    protected final EnumMap<Rarity, List<CardInfo>> savedCards;

    public ExpansionSet(String name, String code, Date releaseDate, SetType setType) {
        this.name = name;
        this.code = code;
        this.releaseDate = releaseDate;
        this.setType = setType;
        this.maxCardNumberInBooster = Integer.MAX_VALUE;
        savedCards = new EnumMap<>(Rarity.class);
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

    public List<Card> createBooster() {
        for (int i = 0; i < 100; i++) {//don't want to somehow loop forever
            List<Card> booster = tryBooster();
            if (boosterIsValid(booster)) {
                return booster;
            }
        }
        return tryBooster();
    }

    protected boolean boosterIsValid(List<Card> booster) {
        if (validateBoosterColors) {
            if (!validateColors(booster)) {
                return false;
            }
        }

        if (needsLegendCreature) {
            if (booster.stream().noneMatch(card -> card.isLegendary() && card.isCreature())) {
                return false;
            }
        }

        return true;
    }

    protected boolean validateColors(List<Card> booster) {
        List<ObjectColor> magicColors =
                Arrays.asList(ObjectColor.WHITE, ObjectColor.BLUE, ObjectColor.BLACK, ObjectColor.RED, ObjectColor.GREEN);

        // all cards colors
        Map<ObjectColor, Integer> colorWeight = new HashMap<>();
        // uncommon/rare/mythic cards colors
        Map<ObjectColor, Integer> uncommonWeight = new HashMap<>();

        for (ObjectColor color : magicColors) {
            colorWeight.put(color, 0);
            uncommonWeight.put(color, 0);
        }

        // count colors in the booster
        for (Card card : booster) {
            ObjectColor cardColor = card.getColor(null);
            if (cardColor != null) {
                List<ObjectColor> colors = cardColor.getColors();
                // todo: do we need gold color?
                colors.remove(ObjectColor.GOLD);
                if (!colors.isEmpty()) {
                    // 60 - full card weight
                    // multicolored cards add part of the weight to each color
                    int cardColorWeight = 60 / colors.size();
                    for (ObjectColor color : colors) {
                        colorWeight.put(color, colorWeight.get(color) + cardColorWeight);
                        if (card.getRarity() != Rarity.COMMON) {
                            uncommonWeight.put(color, uncommonWeight.get(color) + cardColorWeight);
                        }
                    }
                }
            }
        }

        // check that all colors are present
        if (magicColors.stream().anyMatch(color -> colorWeight.get(color) < 60)) {
            // reject only part of the boosters
            if (RandomUtil.nextDouble() < rejectMissingColorProbability) {
                return false;
            }
        }

        // check that we don't have 3 or more uncommons/rares of the same color
        if (magicColors.stream().anyMatch(color -> uncommonWeight.get(color) >= 180)) {
            // reject only part of the boosters
            if (RandomUtil.nextDouble() < rejectSameColorUncommonsProbability) {
                return false;
            }
        }

        return true;
    }

    public List<Card> tryBooster() {
        List<Card> booster = new ArrayList<>();
        if (!hasBoosters) {
            return booster;
        }

        if (numBoosterLands > 0) {
            List<CardInfo> specialLands = getSpecialLand();
            List<CardInfo> basicLands = getCardsByRarity(Rarity.LAND);
            for (int i = 0; i < numBoosterLands; i++) {
                if (ratioBoosterSpecialLand > 0 && RandomUtil.nextInt(ratioBoosterSpecialLand) < ratioBoosterSpecialLandNumerator && specialLands != null) {
                    addToBooster(booster, specialLands);
                } else {
                    addToBooster(booster, basicLands);
                }
            }
        }
        int numSpecialCommons = getNumberOfSpecialCommons();
        int numCommonsToGenerate = numBoosterCommon - numSpecialCommons;

        List<CardInfo> commons = getCardsByRarity(Rarity.COMMON);
        for (int i = 0; i < numCommonsToGenerate; i++) {
            addToBooster(booster, commons);
        }

        if (numSpecialCommons > 0) { // e.g. used to conditionaly replace common cards in the booster
            addSpecialCommon(booster, numSpecialCommons);
        }

        List<CardInfo> uncommons = getCardsByRarity(Rarity.UNCOMMON);
        for (int i = 0; i < numBoosterUncommon; i++) {
            addToBooster(booster, uncommons);
        }

        List<CardInfo> rares = getCardsByRarity(Rarity.RARE);
        List<CardInfo> mythics = getCardsByRarity(Rarity.MYTHIC);
        for (int i = 0; i < numBoosterRare; i++) {
            if (ratioBoosterMythic > 0 && RandomUtil.nextInt(ratioBoosterMythic) == 0) {
                addToBooster(booster, mythics);
            } else {
                addToBooster(booster, rares);
            }
        }

        if (numBoosterDoubleFaced > 0) {
            addDoubleFace(booster);
        }

        if (numBoosterSpecial > 0) {
            addSpecial(booster);
        }

        return booster;
    }

    /* add double faced card for Innistrad booster
     * rarity near as the normal distribution
     */
    public void addDoubleFace(List<Card> booster) {
        for (int i = 0; i < numBoosterDoubleFaced; i++) {
            CardCriteria criteria = new CardCriteria();
            criteria.setCodes(this.code).doubleFaced(true);
            if (RandomUtil.nextInt(15) < 10) {
                criteria.rarities(Rarity.COMMON);
            } else if (RandomUtil.nextInt(5) < 4) {
                criteria.rarities(Rarity.UNCOMMON);
            } else if (RandomUtil.nextInt(8) < 7) {
                criteria.rarities(Rarity.RARE);
            } else {
                criteria.rarities(Rarity.MYTHIC);
            }
            List<CardInfo> doubleFacedCards = CardRepository.instance.findCards(criteria);
            addToBooster(booster, doubleFacedCards);
        }
    }

    public static Date buildDate(int year, int month, int day) {
        // The month starts with 0 = jan ... dec = 11
        return new GregorianCalendar(year, month - 1, day).getTime();
    }

    /**
     * Can be overwritten if sometimes special cards will be generated instead
     * of common slots
     *
     * @return
     */
    public int getNumberOfSpecialCommons() {
        return 0;
    }

    /**
     * Can be overwritten to add a replacement for common card in boosters
     *
     * @param booster
     * @param number
     */
    public void addSpecialCommon(List<Card> booster, int number) {

    }

    private void addSpecial(List<Card> booster) {
        int specialCards = 0;
        List<CardInfo> specialBonus = getSpecialBonus();
        specialCards += specialBonus.size();

        List<CardInfo> specialMythic = getSpecialMythic();
        specialCards += specialMythic.size();
        List<CardInfo> specialRare = getSpecialRare();
        specialCards += specialRare.size();
        List<CardInfo> specialUncommon = getSpecialUncommon();
        specialCards += specialUncommon.size();
        List<CardInfo> specialCommon = getSpecialCommon();

        specialCards += specialCommon.size();

        if (specialCards > 0) {
            for (int i = 0; i < numBoosterSpecial; i++) {
                if (RandomUtil.nextInt(15) < 10) {
                    if (!specialCommon.isEmpty()) {
                        addToBooster(booster, specialCommon);
                    } else {
                        i--;
                    }
                    continue;
                }
                if (RandomUtil.nextInt(4) < 3) {
                    if (!specialUncommon.isEmpty()) {
                        addToBooster(booster, specialUncommon);
                    } else {
                        i--;
                    }
                    continue;
                }
                if (RandomUtil.nextInt(8) < 7) {
                    if (!specialRare.isEmpty()) {
                        addToBooster(booster, specialRare);
                    } else {
                        i--;
                    }
                    continue;
                }
                if (!specialMythic.isEmpty()) {
                    if (!specialBonus.isEmpty()) {
                        if (RandomUtil.nextInt(3) < 2) {
                            addToBooster(booster, specialMythic);
                            continue;
                        }
                    } else {
                        addToBooster(booster, specialMythic);
                        continue;
                    }
                } else {
                    i--;
                }
                if (!specialBonus.isEmpty()) {
                    addToBooster(booster, specialBonus);
                }
            }
        }
    }

    public boolean hasBoosters() {
        return hasBoosters;
    }

    public boolean hasBasicLands() {
        return hasBasicLands;
    }

    public List<CardInfo> getCardsByRarity(Rarity rarity) {
        List<CardInfo> savedCardsInfos = savedCards.get(rarity);
        if (savedCardsInfos == null) {
            CardCriteria criteria = new CardCriteria();
            if (rarity == Rarity.LAND) {
                criteria.setCodes(!hasBasicLands && parentSet != null ? parentSet.code : this.code);
            } else {
                criteria.setCodes(this.code);
            }
            criteria.rarities(rarity);
            if (numBoosterDoubleFaced > -1) {
                criteria.doubleFaced(false);
            }
            savedCardsInfos = CardRepository.instance.findCards(criteria);
            // Workaround after card number is numeric
            if (maxCardNumberInBooster != Integer.MAX_VALUE) {
                savedCardsInfos.removeIf(next -> next.getCardNumberAsInt() > maxCardNumberInBooster && rarity != Rarity.LAND);
            }

            savedCards.put(rarity, savedCardsInfos);
        }
        // Return a copy of the saved cards information, as not to let modify the original.
        return new ArrayList<>(savedCardsInfos);
    }

    public List<CardInfo> getSpecialCommon() {
        return new ArrayList<>();
    }

    public List<CardInfo> getSpecialUncommon() {
        return new ArrayList<>();
    }

    public List<CardInfo> getSpecialRare() {
        return new ArrayList<>();
    }

    public List<CardInfo> getSpecialMythic() {
        return new ArrayList<>();
    }

    public List<CardInfo> getSpecialBonus() {
        return new ArrayList<>();
    }

    public List<CardInfo> getSpecialLand() {
        return new ArrayList<>();
    }

    public boolean isCustomSet() {
        return setType == SetType.CUSTOM_SET;
    }

    public void removeSavedCards() {
        savedCards.clear();
    }

    public int getMaxCardNumberInBooster() {
        return maxCardNumberInBooster;
    }

}
