package mage.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.SpellAbility;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.mana.*;
import mage.abilities.effects.ContinuousEffect;
import mage.cards.Card;
import mage.cards.MeldCard;
import mage.constants.*;
import mage.filter.Filter;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.CardState;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.game.permanent.PermanentMeld;
import mage.game.permanent.token.Token;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.Target;
import mage.util.functions.CopyTokenFunction;

/**
 * @author nantuko
 */
public final class CardUtil {

    private static final String SOURCE_EXILE_ZONE_TEXT = "SourceExileZone";

    static final String[] numberStrings = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine",
        "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen", "twenty"};

    static final String[] ordinalStrings = {"first", "second", "third", "fourth", "fifth", "sixth", "seventh", "eightth", "ninth",
        "tenth", "eleventh", "twelfth", "thirteenth", "fourteenth", "fifteenth", "sixteenth", "seventeenth", "eighteenth", "nineteenth", "twentieth"};

    public static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");

    /**
     * Increase spell or ability cost to be paid.
     *
     * @param ability
     * @param increaseCount
     */
    public static void increaseCost(Ability ability, int increaseCount) {
        adjustAbilityCost(ability, -increaseCount);
    }

    /**
     * Reduces ability cost to be paid.
     *
     * @param ability
     * @param reduceCount
     */
    public static void reduceCost(Ability ability, int reduceCount) {
        adjustAbilityCost(ability, reduceCount);
    }

    /**
     * Adjusts spell or ability cost to be paid.
     *
     * @param spellAbility
     * @param reduceCount
     */
    public static void adjustCost(SpellAbility spellAbility, int reduceCount) {
        CardUtil.adjustAbilityCost(spellAbility, reduceCount);
    }

    public static ManaCosts<ManaCost> increaseCost(ManaCosts<ManaCost> manaCosts, int increaseCount) {
        return adjustCost(manaCosts, -increaseCount);
    }

    public static ManaCosts<ManaCost> reduceCost(ManaCosts<ManaCost> manaCosts, int reduceCount) {
        return adjustCost(manaCosts, reduceCount);
    }

    /**
     * Adjusts ability cost to be paid.
     *
     * @param ability
     * @param reduceCount
     */
    private static void adjustAbilityCost(Ability ability, int reduceCount) {
        ManaCosts<ManaCost> adjustedCost = adjustCost(ability.getManaCostsToPay(), reduceCount);
        ability.getManaCostsToPay().clear();
        ability.getManaCostsToPay().addAll(adjustedCost);
    }

    private static ManaCosts<ManaCost> adjustCost(ManaCosts<ManaCost> manaCosts, int reduceCount) {
        ManaCosts<ManaCost> adjustedCost = new ManaCostsImpl<>();

        // nothing to change
        if (reduceCount == 0) {
            for (ManaCost manaCost : manaCosts) {
                adjustedCost.add(manaCost.copy());
            }
            return adjustedCost;
        }

        // remove or save cost
        if (reduceCount > 0) {
            int restToReduce = reduceCount;

            // first run - priority for single option costs (generic)
            for (ManaCost manaCost : manaCosts) {
                if (manaCost instanceof SnowManaCost) {
                    adjustedCost.add(manaCost);
                    continue;
                }

                if (manaCost.getOptions().size() == 0) {
                    adjustedCost.add(manaCost);
                    continue;
                }

                // ignore monohybrid and other multi-option mana (for potential support)
                if (manaCost.getOptions().size() > 1) {
                    continue;
                }

                // generic mana reduce
                Mana mana = manaCost.getOptions().get(0);
                int colorless = mana != null ? mana.getGeneric() : 0;
                if (restToReduce != 0 && colorless > 0) {
                    if ((colorless - restToReduce) > 0) {
                        // partly reduce
                        int newColorless = colorless - restToReduce;
                        adjustedCost.add(new GenericManaCost(newColorless));
                        restToReduce = 0;
                    } else {
                        // full reduce - ignore cost
                        restToReduce -= colorless;
                    }
                } else {
                    // nothing to reduce
                    adjustedCost.add(manaCost.copy());
                }
            }

            // second run - priority for multi option costs (monohybrid)
            //
            // from Reaper King:
            // If an effect reduces the cost to cast a spell by an amount of generic mana, it applies to a monocolored hybrid
            // spell only if you’ve chosen a method of paying for it that includes generic mana.
            // (2008-05-01)
            // TODO: xmage don't use announce for hybrid mana (instead it uses auto-pay), so that's workaround uses first hybrid to reduce (see https://github.com/magefree/mage/issues/6130 )
            for (ManaCost manaCost : manaCosts) {
                if (manaCost.getOptions().size() <= 1) {
                    continue;
                }

                if (manaCost instanceof MonoHybridManaCost) {
                    // current implemention supports reduce from left to right hybrid cost without cost parts announce
                    MonoHybridManaCost mono = (MonoHybridManaCost) manaCost;
                    int colorless = mono.getOptions().get(1).getGeneric();
                    if (restToReduce != 0 && colorless > 0) {
                        if ((colorless - restToReduce) > 0) {
                            // partly reduce
                            int newColorless = colorless - restToReduce;
                            adjustedCost.add(new MonoHybridManaCost(mono.getManaColor(), newColorless));
                            restToReduce = 0;
                        } else {
                            // full reduce
                            adjustedCost.add(new MonoHybridManaCost(mono.getManaColor(), 0));
                            restToReduce -= colorless;
                        }
                    } else {
                        // nothing to reduce
                        adjustedCost.add(mono.copy());
                    }
                    continue;
                }

                // unsupported multi-option mana types for reduce (like HybridManaCost)
                adjustedCost.add(manaCost.copy());
            }
        }

        // increase cost (add to first generic or add new)
        if (reduceCount < 0) {
            boolean added = false;
            for (ManaCost manaCost : manaCosts) {
                if (reduceCount != 0 && manaCost instanceof GenericManaCost) {
                    // add increase cost to existing generic
                    GenericManaCost gen = (GenericManaCost) manaCost;
                    adjustedCost.add(new GenericManaCost(gen.getOptions().get(0).getGeneric() + -reduceCount));
                    reduceCount = 0;
                    added = true;
                } else {
                    // non-generic mana
                    adjustedCost.add(manaCost.copy());
                }
            }
            if (!added) {
                // add increase cost as new
                adjustedCost.add(new GenericManaCost(-reduceCount));
            }
        }

        // cost modifying effects requiring snow mana unnecessarily (fixes #6000)
        Filter filter = manaCosts.stream()
                .filter(manaCost -> !(manaCost instanceof SnowManaCost))
                .map(ManaCost::getSourceFilter)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
        if (filter != null) {
            adjustedCost.setSourceFilter(filter);
        }
        return adjustedCost;
    }

    public static void reduceCost(SpellAbility spellAbility, ManaCosts<ManaCost> manaCostsToReduce) {
        adjustCost(spellAbility, manaCostsToReduce, true);
    }

    public static void increaseCost(SpellAbility spellAbility, ManaCosts<ManaCost> manaCostsToIncrease) {
        ManaCosts<ManaCost> increasedCost = spellAbility.getManaCostsToPay().copy();

        for (ManaCost manaCost : manaCostsToIncrease) {
            increasedCost.add(manaCost.copy());
        }

        spellAbility.getManaCostsToPay().clear();
        spellAbility.getManaCostsToPay().addAll(increasedCost);
    }

    /**
     * Adjusts spell or ability cost to be paid by colored and generic mana.
     *
     * @param spellAbility
     * @param manaCostsToReduce costs to reduce
     * @param convertToGeneric  colored mana does reduce generic mana if no
     *                          appropriate colored mana is in the costs
     *                          included
     */
    public static void adjustCost(SpellAbility spellAbility, ManaCosts<ManaCost> manaCostsToReduce, boolean convertToGeneric) {
        ManaCosts<ManaCost> previousCost = spellAbility.getManaCostsToPay();
        ManaCosts<ManaCost> adjustedCost = new ManaCostsImpl<>();
        // save X value (e.g. convoke ability)
        for (VariableCost vCost : previousCost.getVariableCosts()) {
            if (vCost instanceof VariableManaCost) {
                adjustedCost.add((VariableManaCost) vCost);
            }
        }

        Mana reduceMana = new Mana();
        for (ManaCost manaCost : manaCostsToReduce) {
            if (manaCost instanceof MonoHybridManaCost) {
                reduceMana.add(Mana.GenericMana(2));
            } else {
                reduceMana.add(manaCost.getMana());
            }
        }
        ManaCosts<ManaCost> manaCostToCheckForGeneric = new ManaCostsImpl<>();
        // subtract non-generic mana
        for (ManaCost newManaCost : previousCost) {
            Mana mana = newManaCost.getMana();
            if (!(newManaCost instanceof MonoHybridManaCost) && mana.getGeneric() > 0) {
                manaCostToCheckForGeneric.add(newManaCost);
                continue;
            }
            boolean hybridMana = newManaCost instanceof HybridManaCost;
            if (mana.getBlack() > 0 && reduceMana.getBlack() > 0) {
                if (reduceMana.getBlack() > mana.getBlack()) {
                    reduceMana.setBlack(reduceMana.getBlack() - mana.getBlack());
                    mana.setBlack(0);
                } else {
                    mana.setBlack(mana.getBlack() - reduceMana.getBlack());
                    reduceMana.setBlack(0);
                }
                if (hybridMana) {
                    continue;
                }
            }
            if (mana.getRed() > 0 && reduceMana.getRed() > 0) {
                if (reduceMana.getRed() > mana.getRed()) {
                    reduceMana.setRed(reduceMana.getRed() - mana.getRed());
                    mana.setRed(0);
                } else {
                    mana.setRed(mana.getRed() - reduceMana.getRed());
                    reduceMana.setRed(0);
                }
                if (hybridMana) {
                    continue;
                }
            }
            if (mana.getBlue() > 0 && reduceMana.getBlue() > 0) {
                if (reduceMana.getBlue() > mana.getBlue()) {
                    reduceMana.setBlue(reduceMana.getBlue() - mana.getBlue());
                    mana.setBlue(0);
                } else {
                    mana.setBlue(mana.getBlue() - reduceMana.getBlue());
                    reduceMana.setBlue(0);
                }
                if (hybridMana) {
                    continue;
                }
            }
            if (mana.getGreen() > 0 && reduceMana.getGreen() > 0) {
                if (reduceMana.getGreen() > mana.getGreen()) {
                    reduceMana.setGreen(reduceMana.getGreen() - mana.getGreen());
                    mana.setGreen(0);
                } else {
                    mana.setGreen(mana.getGreen() - reduceMana.getGreen());
                    reduceMana.setGreen(0);
                }
                if (hybridMana) {
                    continue;
                }
            }
            if (mana.getWhite() > 0 && reduceMana.getWhite() > 0) {
                if (reduceMana.getWhite() > mana.getWhite()) {
                    reduceMana.setWhite(reduceMana.getWhite() - mana.getWhite());
                    mana.setWhite(0);
                } else {
                    mana.setWhite(mana.getWhite() - reduceMana.getWhite());
                    reduceMana.setWhite(0);
                }
                if (hybridMana) {
                    continue;
                }
            }

            if (mana.getColorless() > 0 && reduceMana.getColorless() > 0) {
                if (reduceMana.getColorless() > mana.getColorless()) {
                    reduceMana.setColorless(reduceMana.getColorless() - mana.getColorless());
                    mana.setColorless(0);
                } else {
                    mana.setColorless(mana.getColorless() - reduceMana.getColorless());
                    reduceMana.setColorless(0);
                }
            }

            if (mana.count() > 0) {
                if (newManaCost instanceof MonoHybridManaCost) {
                    if (mana.count() == 2) {
                        reduceMana.setGeneric(reduceMana.getGeneric() - 2);
                        continue;
                    }
                }
                manaCostToCheckForGeneric.add(newManaCost);
            }

        }

        // subtract colorless mana, use all mana that is left
        int reduceAmount;
        if (convertToGeneric) {
            reduceAmount = reduceMana.count();
        } else {
            reduceAmount = reduceMana.getGeneric();
        }
        if (reduceAmount > 0) {
            for (ManaCost newManaCost : manaCostToCheckForGeneric) {
                Mana mana = newManaCost.getMana();
                if (mana.getGeneric() == 0 || reduceAmount == 0) {
                    adjustedCost.add(newManaCost);
                    continue;
                }
                if (newManaCost instanceof MonoHybridManaCost) {
                    if (reduceAmount > 1) {
                        reduceAmount -= 2;
                        mana.clear();
                    }
                    continue;
                }
                if (mana.getGeneric() > 0) {
                    if (reduceAmount > mana.getGeneric()) {
                        reduceAmount -= mana.getGeneric();
                        mana.setGeneric(0);
                    } else {
                        mana.setGeneric(mana.getGeneric() - reduceAmount);
                        reduceAmount = 0;
                    }
                }
                if (mana.count() > 0) {
                    adjustedCost.add(0, new GenericManaCost(mana.count()));
                }
            }
        } else {
            adjustedCost.addAll(manaCostToCheckForGeneric);
        }
        if (adjustedCost.isEmpty()) {
            adjustedCost.add(new GenericManaCost(0)); // neede to check if cost was reduced to 0
        }
        adjustedCost.setSourceFilter(previousCost.getSourceFilter());  // keep mana source restrictions
        spellAbility.getManaCostsToPay().clear();
        spellAbility.getManaCostsToPay().addAll(adjustedCost);
    }

    /**
     * Returns function that copies params\abilities from one card to
     * {@link Token}.
     *
     * @param target
     * @return
     */
    public static CopyTokenFunction copyTo(Token target) {
        return new CopyTokenFunction(target);
    }

    /**
     * Converts an integer number to string Numbers > 20 will be returned as
     * digits
     *
     * @param number
     * @return
     */
    public static String numberToText(int number) {
        return numberToText(number, "one");
    }

    /**
     * Converts an integer number to string like "one", "two", "three", ...
     * Numbers > 20 will be returned as digits
     *
     * @param number number to convert to text
     * @param forOne if the number is 1, this string will be returnedinstead of
     *               "one".
     * @return
     */
    public static String numberToText(int number, String forOne) {
        if (number == 1 && forOne != null) {
            return forOne;
        }
        if (number >= 0 && number < 21) {
            return numberStrings[number];
        }
        if (number == Integer.MAX_VALUE) {
            return "any number of";
        }
        return Integer.toString(number);
    }

    public static String numberToText(String number) {
        return numberToText(number, "one");
    }

    public static String numberToText(String number, String forOne) {
        if (checkNumeric(number)) {
            return numberToText(Integer.parseInt(number), forOne);
        }
        return number;
    }

    public static String numberToOrdinalText(int number) {
        if (number >= 1 && number < 21) {
            return ordinalStrings[number - 1];
        }
        return number + "th";
    }

    public static String replaceSourceName(String message, String sourceName) {
        return message.replace("{this}", sourceName);
    }

    public static String booleanToFlipName(boolean flip) {
        if (flip) {
            return "Heads";
        }
        return "Tails";
    }

    public static boolean checkNumeric(String s) {
        return s.chars().allMatch(Character::isDigit);

    }

    /**
     * Parse card number as int (support base [123] and alternative numbers
     * [123b], [U123]).
     *
     * @param cardNumber origin card number
     * @return int
     */
    public static int parseCardNumberAsInt(String cardNumber) {

        if (cardNumber.isEmpty()) {
            throw new IllegalArgumentException("Card number is empty.");
        }

        try {
            if (!Character.isDigit(cardNumber.charAt(0))) {
                // U123
                return Integer.parseInt(cardNumber.substring(1));
            } else if (!Character.isDigit(cardNumber.charAt(cardNumber.length() - 1))) {
                // 123b
                return Integer.parseInt(cardNumber.substring(0, cardNumber.length() - 1));
            } else {
                // 123
                return Integer.parseInt(cardNumber);
            }
        } catch (NumberFormatException e) {
            // wrong numbers like RA5 and etc
            return -1;
        }
    }

    /**
     * Creates and saves a (card + zoneChangeCounter) specific exileId.
     *
     * @param game   the current game
     * @param source source ability
     * @return the specific UUID
     */
    public static UUID getCardExileZoneId(Game game, Ability source) {
        return getCardExileZoneId(game, source.getSourceId());
    }

    public static UUID getCardExileZoneId(Game game, UUID sourceId) {
        return getCardExileZoneId(game, sourceId, false);
    }

    public static UUID getCardExileZoneId(Game game, UUID sourceId, boolean previous) {
        return getExileZoneId(getCardZoneString(SOURCE_EXILE_ZONE_TEXT, sourceId, game, previous), game);
    }

    public static UUID getExileZoneId(Game game, UUID objectId, int zoneChangeCounter) {
        return getExileZoneId(getObjectZoneString(SOURCE_EXILE_ZONE_TEXT, objectId, game, zoneChangeCounter, false), game);
    }

    public static UUID getExileZoneId(String key, Game game) {
        UUID exileId = (UUID) game.getState().getValue(key);
        if (exileId == null) {
            exileId = UUID.randomUUID();
            game.getState().setValue(key, exileId);
        }
        return exileId;
    }

    /**
     * Creates a string from text + cardId and the zoneChangeCounter of the card
     * (from cardId). This string can be used to save and get values that must
     * be specific to a permanent instance. So they won't match, if a permanent
     * was e.g. exiled and came back immediately.
     *
     * @param text   short value to describe the value
     * @param cardId id of the card
     * @param game   the game
     * @return
     */
    public static String getCardZoneString(String text, UUID cardId, Game game) {
        return getCardZoneString(text, cardId, game, false);
    }

    public static String getCardZoneString(String text, UUID cardId, Game game, boolean previous) {
        int zoneChangeCounter = 0;
        Card card = game.getCard(cardId); // if called for a token, the id is enough
        if (card != null) {
            zoneChangeCounter = card.getZoneChangeCounter(game);
        }
        return getObjectZoneString(text, cardId, game, zoneChangeCounter, previous);
    }

    public static String getObjectZoneString(String text, MageObject mageObject, Game game) {
        int zoneChangeCounter = 0;
        if (mageObject instanceof Permanent) {
            zoneChangeCounter = mageObject.getZoneChangeCounter(game);
        } else if (mageObject instanceof Card) {
            zoneChangeCounter = mageObject.getZoneChangeCounter(game);
        }
        return getObjectZoneString(text, mageObject.getId(), game, zoneChangeCounter, false);
    }

    public static String getObjectZoneString(String text, UUID objectId, Game game, int zoneChangeCounter, boolean previous) {
        StringBuilder uniqueString = new StringBuilder();
        if (text != null) {
            uniqueString.append(text);
        }
        uniqueString.append(objectId);
        uniqueString.append(previous ? zoneChangeCounter - 1 : zoneChangeCounter);
        return uniqueString.toString();
    }

    /**
     * Adds tags to mark the additional info of a card (e.g. blue font color)
     *
     * @param text text body
     * @return
     */
    public static String addToolTipMarkTags(String text) {
        return "<font color = 'blue'>" + text + "</font>";
    }

    public static boolean cardCanBePlayedNow(Card card, UUID playerId, Game game) {
        if (card.isLand()) {
            return game.canPlaySorcery(playerId) && game.getPlayer(playerId).canPlayLand();
        } else {
            return card.getSpellAbility() != null && card.getSpellAbility().spellCanBeActivatedRegularlyNow(playerId, game);
        }
    }

    public static int addWithOverflowCheck(int base, int increment) {
        long result = ((long) base) + increment;
        if (result > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        } else if (result < Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        }
        return base + increment;
    }

    public static int subtractWithOverflowCheck(int base, int decrement) {
        long result = ((long) base) - decrement;
        if (result > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        } else if (result < Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        }
        return base - decrement;
    }

    public static String createObjectRealtedWindowTitle(Ability source, Game game, String textSuffix) {
        String title;
        if (source != null) {
            MageObject sourceObject = game.getObject(source.getSourceId());
            if (sourceObject != null) {
                title = sourceObject.getIdName()
                        + " [" + source.getSourceObjectZoneChangeCounter() + "]"
                        + (textSuffix == null ? "" : " " + textSuffix);
            } else {
                title = textSuffix == null ? "" : textSuffix;
            }
        } else {
            title = textSuffix == null ? "" : textSuffix;
        }
        return title;

    }

    /**
     * Face down cards and their copy tokens don't have names and that's "empty"
     * names is not equals
     */
    public static boolean haveSameNames(String name1, String name2, Boolean ignoreMtgRuleForEmptyNames) {
        if (ignoreMtgRuleForEmptyNames) {
            // simple compare for tests and engine
            return name1 != null && name1.equals(name2);
        } else {
            // mtg logic compare for game (empty names can't be same)
            return !haveEmptyName(name1) && !haveEmptyName(name2) && name1.equals(name2);
        }
    }

    public static boolean haveSameNames(String name1, String name2) {
        return haveSameNames(name1, name2, false);
    }

    public static boolean haveSameNames(MageObject object1, MageObject object2) {
        return object1 != null && object2 != null && haveSameNames(object1.getName(), object2.getName());
    }

    public static boolean haveSameNames(MageObject object, String needName, Game game) {
        return containsName(object, needName, game);
    }

    public static boolean containsName(MageObject object, String name, Game game) {
        return new NamePredicate(name).apply(object, game);
    }

    public static boolean haveEmptyName(String name) {
        return name == null || name.isEmpty() || name.equals(EmptyNames.FACE_DOWN_CREATURE.toString()) || name.equals(EmptyNames.FACE_DOWN_TOKEN.toString());
    }

    public static boolean haveEmptyName(MageObject object) {
        return object == null || haveEmptyName(object.getName());
    }

    public static UUID getMainCardId(Game game, UUID objectId) {
        Card card = game.getCard(objectId);
        return card != null ? card.getMainCard().getId() : objectId;
    }

    public static String urlEncode(String data) {
        if (data.isEmpty()) {
            return "";
        }

        try {
            return URLEncoder.encode(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public static String urlDecode(String encodedData) {
        if (encodedData.isEmpty()) {
            return "";
        }

        try {
            return URLDecoder.decode(encodedData, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    /**
     * Checks if a card had a given ability depending their historic cardState
     *
     * @param ability   the ability that is checked
     * @param cardState the historic cardState (from LKI)
     * @param cardId    the id of the card
     * @param game
     * @return
     */
    public static boolean cardHadAbility(Ability ability, CardState cardState, UUID cardId, Game game) {
        Card card = game.getCard(cardId);
        if (card != null) {
            if (cardState != null) {
                if (cardState.getAbilities().contains(ability)) { // Check other abilities (possibly given after lost of abilities)
                    return true;
                }
                if (cardState.hasLostAllAbilities()) {
                    return false; // Not allowed to check abilities of original card
                }
            }
            return card.getAbilities().contains(ability); // check if the original card has the ability
        }
        return false;
    }

    public static List<String> concatManaSymbols(String delimeter, List<String> mana1, List<String> mana2) {
        List<String> res = new ArrayList<>(mana1);
        if (res.size() > 0 && mana2.size() > 0 && delimeter != null && !delimeter.isEmpty()) {
            res.add(delimeter);
        }
        res.addAll(mana2);
        return res;
    }

    public static ColoredManaSymbol manaTypeToColoredManaSymbol(ManaType manaType) {
        switch (manaType) {
            case BLACK:
                return ColoredManaSymbol.B;
            case BLUE:
                return ColoredManaSymbol.U;
            case GREEN:
                return ColoredManaSymbol.G;
            case RED:
                return ColoredManaSymbol.R;
            case WHITE:
                return ColoredManaSymbol.W;
            case GENERIC:
            case COLORLESS:
            default:
                throw new IllegalArgumentException("Wrong mana type " + manaType);
        }
    }

    public static String getBoostCountAsStr(int power, int toughness) {
        // sign fix for zero values
        // -1/+0 must be -1/-0
        // +0/-1 must be -0/-1
        String signedP = String.format("%1$+d", power);
        String signedT = String.format("%1$+d", toughness);
        if (signedP.equals("+0") && signedT.startsWith("-")) {
            signedP = "-0";
        }
        if (signedT.equals("+0") && signedP.startsWith("-")) {
            signedT = "-0";
        }

        return signedP + "/" + signedT;
    }

    public static boolean isSpliceAbility(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            return ((SpellAbility) ability).getSpellAbilityType() == SpellAbilityType.SPLICE;
        }
        return false;
    }

    public static boolean isFusedPartAbility(Ability ability, Game game) {
        // TODO: is works fine with copies of spells on stack?
        if (ability instanceof SpellAbility) {
            Spell mainSpell = game.getSpell(ability.getId());
            if (mainSpell == null) {
                return true;
            } else {
                SpellAbility mainSpellAbility = mainSpell.getSpellAbility();
                return mainSpellAbility.getSpellAbilityType() == SpellAbilityType.SPLIT_FUSED
                        && !ability.equals(mainSpellAbility);
            }
        }
        return false;
    }

    public static Abilities<Ability> getAbilities(MageObject object, Game game) {
        if (object instanceof Card) {
            return ((Card) object).getAbilities(game);
        } else {
            return object.getAbilities();
        }
    }

    public static String getTextWithFirstCharUpperCase(String text) {
        if (text != null && text.length() >= 1) {
            return Character.toUpperCase(text.charAt(0)) + text.substring(1);
        } else {
            return text;
        }
    }

    public static Set<UUID> getAllSelectedTargets(Ability ability, Game game) {
        return ability.getModes().getSelectedModes()
                .stream()
                .map(ability.getModes()::get)
                .map(Mode::getTargets)
                .flatMap(Collection::stream)
                .map(Target::getTargets)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    public static Set<UUID> getAllPossibleTargets(Ability ability, Game game) {
        return ability.getModes().values()
                .stream()
                .map(Mode::getTargets)
                .flatMap(Collection::stream)
                .map(t -> t.possibleTargets(ability.getSourceId(), ability.getControllerId(), game))
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    /**
     * Put card to battlefield without resolve
     *
     * @param game
     * @param card
     * @param player
     */
    public static void putCardOntoBattlefieldWithEffects(Game game, Card card, Player player) {
        // same logic as ZonesHandler->maybeRemoveFromSourceZone

        // prepare card and permanent
        card.setZone(Zone.BATTLEFIELD, game);
        card.setOwnerId(player.getId());
        PermanentCard permanent;
        if (card instanceof MeldCard) {
            permanent = new PermanentMeld(card, player.getId(), game);
        } else {
            permanent = new PermanentCard(card, player.getId(), game);
        }

        // put onto battlefield with possible counters
        game.getPermanentsEntering().put(permanent.getId(), permanent);
        card.checkForCountersToAdd(permanent, game);
        permanent.entersBattlefield(permanent.getId(), game, Zone.OUTSIDE, false);
        game.addPermanent(permanent, game.getState().getNextPermanentOrderNumber());
        game.getPermanentsEntering().remove(permanent.getId());

        // workaround for special tapped status from test framework's command (addCard)
        if (card instanceof PermanentCard && ((PermanentCard) card).isTapped()) {
            permanent.setTapped(true);
        }

        // remove sickness
        permanent.removeSummoningSickness();

        // init effects on static abilities (init continuous effects; warning, game state contains copy)
        for (ContinuousEffect effect : game.getState().getContinuousEffects().getLayeredEffects(game)) {
            Optional<Ability> ability = game.getState().getContinuousEffects().getLayeredEffectAbilities(effect).stream().findFirst();
            if (ability.isPresent() && permanent.getId().equals(ability.get().getSourceId())) {
                effect.init(ability.get(), game, player.getId());
            }
        }
    }
}
