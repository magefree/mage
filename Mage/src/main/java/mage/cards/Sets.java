package mage.cards;

import mage.Mana;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Rarity;
import mage.filter.FilterMana;
import mage.util.ClassScanner;
import mage.util.RandomUtil;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * @author BetaSteward_at_googlemail.com, JayDi85
 */
public class Sets extends HashMap<String, ExpansionSet> {

    private static final Logger logger = Logger.getLogger(Sets.class);
    private static final Sets instance = new Sets();

    public static Sets getInstance() {
        return instance;
    }

    private final Set<String> customSets = new HashSet<>();

    private Sets() {
        List<String> packages = new ArrayList<>();
        packages.add("mage.sets");
        for (Class c : ClassScanner.findClasses(null, packages, ExpansionSet.class)) {
            try {
                addSet((ExpansionSet) c.getMethod("getInstance").invoke(null));
            } catch (Exception ex) {
                logger.error(ex);
            }
        }
    }

    public void addSet(ExpansionSet set) {
        if (containsKey(set.getCode())) {
            throw new IllegalArgumentException("Set code " + set.getCode() + " already exists.");
        }
        this.put(set.getCode(), set);
        if (set.getSetType().isCustomSet()) {
            customSets.add(set.getCode());
        }
    }

    /**
     * Generates card pool of cardsCount cards that have manacost of allowed
     * colors.
     *
     * @param cardsCount
     * @param allowedColors
     * @return
     */
    public static List<Card> generateRandomCardPool(int cardsCount, List<ColoredManaSymbol> allowedColors) {
        return generateRandomCardPool(cardsCount, allowedColors, false);
    }

    public static List<Card> generateRandomCardPool(int cardsCount, List<ColoredManaSymbol> allowedColors, boolean onlyBasicLands) {
        return generateRandomCardPool(cardsCount, allowedColors, onlyBasicLands, null);
    }

    public static List<Card> generateRandomCardPool(int cardsCount, List<ColoredManaSymbol> allowedColors, boolean onlyBasicLands, List<String> allowedSets) {
        CardCriteria criteria = new CardCriteria();

        if (onlyBasicLands) {
            // only lands
            criteria.rarities(Rarity.LAND);
            criteria.colorless(true); // basic lands is colorless
        } else {
            // any card, but not basic lands
            criteria.notTypes(CardType.LAND);

            // clear colors
            criteria.white(false);
            criteria.blue(false);
            criteria.black(false);
            criteria.red(false);
            criteria.green(false);
            criteria.colorless(false); // colorless is not allowed for gen
        }

        if (allowedSets != null && allowedSets.size() > 0) {
            for (String code : allowedSets) {
                criteria.setCodes(code);
            }
        }

        FilterMana manaNeed = new FilterMana();
        for (ColoredManaSymbol color : allowedColors) {
            switch (color) {
                case W:
                    manaNeed.setWhite(true);
                    criteria.white(true);
                    break;
                case U:
                    manaNeed.setBlue(true);
                    criteria.blue(true);
                    break;
                case B:
                    manaNeed.setBlack(true);
                    criteria.black(true);
                    break;
                case R:
                    manaNeed.setRed(true);
                    criteria.red(true);
                    break;
                case G:
                    manaNeed.setGreen(true);
                    criteria.green(true);
                    break;
            }
        }
        List<CardInfo> cards = CardRepository.instance.findCards(criteria);
        if (cards.isEmpty()) {
            throw new IllegalStateException("Can't find cards for chosen colors to generate deck: " + allowedColors);
        }

        int count = 0;
        int tries = 0;
        List<Card> cardPool = new ArrayList<>();
        while (count < cardsCount) {
            CardInfo cardInfo = cards.get(RandomUtil.nextInt(cards.size()));
            Card card = cardInfo != null ? cardInfo.getCard() : null;
            if (card != null) {

                FilterMana manaCard = card.getColorIdentity();
                boolean cardManaOK = true;

                if (onlyBasicLands) {
                    // lands is colorless
                    // discard not needed color by mana produce
                    //Assert.assertEquals("only basic lands allow, but found " + card.getName(), 1, card.getMana().size());
                    for (Mana manaLand : card.getMana()) {
                        if (manaLand.getWhite() > 0 && !manaNeed.isWhite()) {
                            cardManaOK = false;
                        }
                        if (manaLand.getBlue() > 0 && !manaNeed.isBlue()) {
                            cardManaOK = false;
                        }
                        if (manaLand.getBlack() > 0 && !manaNeed.isBlack()) {
                            cardManaOK = false;
                        }
                        if (manaLand.getRed() > 0 && !manaNeed.isRed()) {
                            cardManaOK = false;
                        }
                        if (manaLand.getGreen() > 0 && !manaNeed.isGreen()) {
                            cardManaOK = false;
                        }
                        if (manaLand.getColorless() > 0) {
                            cardManaOK = false;
                        } // ignore colorless land (wastes)
                    }
                } else {
                    // cards
                    // discard any card that have not needed color
                    if (manaCard.isWhite() && !manaNeed.isWhite()) {
                        cardManaOK = false;
                    }
                    if (manaCard.isBlue() && !manaNeed.isBlue()) {
                        cardManaOK = false;
                    }
                    if (manaCard.isBlack() && !manaNeed.isBlack()) {
                        cardManaOK = false;
                    }
                    if (manaCard.isRed() && !manaNeed.isRed()) {
                        cardManaOK = false;
                    }
                    if (manaCard.isGreen() && !manaNeed.isGreen()) {
                        cardManaOK = false;
                    }
                }

                if (cardManaOK) {
                    cardPool.add(card);
                    count++;
                }
            }

            tries++;
            if (tries > 4096) { // to avoid infinite loop
                throw new IllegalStateException("Not enough cards for chosen colors to generate deck: " + allowedColors);
            }
        }

        return cardPool;
    }

    public static ExpansionSet findSet(String code) {
        if (instance.containsKey(code)) {
            return instance.get(code);
        }
        return null;
    }

    public static ExpansionSet findSetByName(String setName) {
        return instance.values().stream()
                .filter(expansionSet -> expansionSet.getName().equals(setName))
                .findFirst()
                .orElse(null);
    }

    public static ExpansionSet.SetCardInfo findCardByClass(Class<?> clazz, String preferredSetCode, String preferredCardNumber) {
        ExpansionSet.SetCardInfo info = null;
        if (instance.containsKey(preferredSetCode)) {
            info = instance.get(preferredSetCode).findCardInfoByClass(clazz)
                    .stream()
                    .filter(card -> preferredCardNumber == null || card.getCardNumber().equals(preferredCardNumber))
                    .findFirst()
                    .orElse(null);
        }

        if (info == null) {
            for (Map.Entry<String, ExpansionSet> entry : instance.entrySet()) {
                info = entry.getValue().findCardInfoByClass(clazz)
                        .stream()
                        .filter(card -> preferredCardNumber == null || card.getCardNumber().equals(preferredCardNumber))
                        .findFirst()
                        .orElse(null);
                if (info != null) {
                    break;
                }
            }
        }

        return info;
    }
}
