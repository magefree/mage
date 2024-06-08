package mage.util;

import com.google.common.collect.ImmutableList;
import mage.*;
import mage.abilities.*;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.mana.*;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.dynamicvalue.common.SavedGainedLifeValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.asthought.CanPlayCardControllerEffect;
import mage.abilities.effects.common.asthought.YouMaySpendManaAsAnyColorToCastTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.HintUtils;
import mage.cards.*;
import mage.constants.*;
import mage.counters.Counter;
import mage.filter.Filter;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.CardState;
import mage.game.Game;
import mage.game.GameState;
import mage.game.command.Commander;
import mage.game.events.BatchEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.game.permanent.PermanentMeld;
import mage.game.permanent.PermanentToken;
import mage.game.permanent.token.Token;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.players.PlayerList;
import mage.target.Target;
import mage.target.TargetCard;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.Watcher;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author nantuko
 */
public final class CardUtil {

    private static final Logger logger = Logger.getLogger(CardUtil.class);

    public static final List<String> RULES_ERROR_INFO = ImmutableList.of("Exception occurred in rules generation");

    public static final String SOURCE_EXILE_ZONE_TEXT = "SourceExileZone";

    static final String[] numberStrings = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine",
            "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen", "twenty"};

    static final String[] ordinalStrings = {"first", "second", "third", "fourth", "fifth", "sixth", "seventh", "eightth", "ninth",
            "tenth", "eleventh", "twelfth", "thirteenth", "fourteenth", "fifteenth", "sixteenth", "seventeenth", "eighteenth", "nineteenth", "twentieth"};

    public static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");

    private static final List<String> costWords = Arrays.asList(
            "put", "return", "exile", "discard", "sacrifice", "remove", "tap", "reveal", "pay", "collect"
    );

    // search set code in commands like "set_code-card_name"
    public static final int TESTS_SET_CODE_MIN_LOOKUP_LENGTH = 3;
    public static final int TESTS_SET_CODE_MAX_LOOKUP_LENGTH = 6;
    public static final String TESTS_SET_CODE_DELIMETER = "-"; // delimeter for cheats and tests command "set_code-card_name"

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
     * calculates the maximal possible generic mana reduction for a given mana cost
     *
     * @param mana                 mana costs that should be reduced
     * @param maxPossibleReduction max possible generic mana reduction
     * @param notLessThan          the complete costs may not be reduced more than this CMC mana costs
     */
    public static int calculateActualPossibleGenericManaReduction(Mana mana, int maxPossibleReduction, int notLessThan) {
        int nonGeneric = mana.count() - mana.getGeneric();
        int notPossibleGenericReduction = Math.max(0, notLessThan - nonGeneric);
        int actualPossibleGenericManaReduction = Math.max(0, mana.getGeneric() - notPossibleGenericReduction);
        if (actualPossibleGenericManaReduction > maxPossibleReduction) {
            actualPossibleGenericManaReduction = maxPossibleReduction;
        }
        return actualPossibleGenericManaReduction;
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
        ability.clearManaCostsToPay();
        ability.addManaCostsToPay(adjustedCost);
    }

    public static ManaCosts<ManaCost> adjustCost(ManaCosts<ManaCost> manaCosts, int reduceCount) {
        ManaCosts<ManaCost> newCost = new ManaCostsImpl<>();

        // nothing to change
        if (reduceCount == 0) {
            for (ManaCost manaCost : manaCosts) {
                newCost.add(manaCost.copy());
            }
            return newCost;
        }

        // keep same order for costs
        Map<ManaCost, ManaCost> changedCost = new LinkedHashMap<>(); // must be ordered
        List<ManaCost> addedCost = new ArrayList<>();
        manaCosts.forEach(manaCost -> {
            changedCost.put(manaCost, manaCost);
        });

        // remove or save cost
        if (reduceCount > 0) {
            int restToReduce = reduceCount;

            // first run - priority for single option costs (generic)
            for (ManaCost manaCost : manaCosts) {

                // ignore snow mana
                if (manaCost instanceof SnowManaCost) {
                    continue;
                }

                // ignore unknown mana
                if (manaCost.getOptions().isEmpty()) {
                    continue;
                }

                // ignore monohybrid and other multi-option mana (for potential support)
                if (manaCost.getOptions().size() > 1) {
                    continue;
                }

                // generic mana reduce
                Mana mana = manaCost.getOptions().getAtIndex(0);
                int colorless = mana != null ? mana.getGeneric() : 0;
                if (restToReduce != 0 && colorless > 0) {
                    if ((colorless - restToReduce) > 0) {
                        // partly reduce
                        int newColorless = colorless - restToReduce;
                        changedCost.put(manaCost, new GenericManaCost(newColorless));
                        restToReduce = 0;
                    } else {
                        // full reduce - ignore cost
                        changedCost.put(manaCost, null);
                        restToReduce -= colorless;
                    }
                } else {
                    // nothing to reduce
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
                    int colorless = mono.getOptions().getAtIndex(1).getGeneric();
                    if (restToReduce != 0 && colorless > 0) {
                        if ((colorless - restToReduce) > 0) {
                            // partly reduce
                            int newColorless = colorless - restToReduce;
                            changedCost.put(manaCost, new MonoHybridManaCost(mono.getManaColor(), newColorless));
                            restToReduce = 0;
                        } else {
                            // full reduce
                            changedCost.put(manaCost, new MonoHybridManaCost(mono.getManaColor(), 0));
                            restToReduce -= colorless;
                        }
                    } else {
                        // nothing to reduce
                    }
                    continue;
                }

                // unsupported multi-option mana types for reduce (like HybridManaCost)
                // nothing to do
            }
        }

        // increase cost (add to first generic or add new)
        if (reduceCount < 0) {
            boolean added = false;
            for (ManaCost manaCost : manaCosts) {
                // ignore already reduced cost (add new cost to the start)
                if (changedCost.get(manaCost) == null) {
                    continue;
                }

                // add to existing cost
                if (reduceCount != 0 && manaCost instanceof GenericManaCost) {
                    GenericManaCost gen = (GenericManaCost) manaCost;
                    changedCost.put(manaCost, new GenericManaCost(gen.getOptions().getAtIndex(0).getGeneric() + -reduceCount));
                    reduceCount = 0;
                    added = true;
                } else {
                    // non-generic mana
                }
            }

            // add as new cost
            if (!added) {
                addedCost.add(new GenericManaCost(-reduceCount));
            }
        }

        // collect final result
        addedCost.forEach(cost -> {
            newCost.add(cost.copy());
        });
        changedCost.forEach((key, value) -> {
            // ignore fully reduced and add changed
            if (value != null) {
                newCost.add(value.copy());
            }
        });

        // cost modifying effects requiring snow mana unnecessarily (fixes #6000)
        Filter filter = manaCosts.stream()
                .filter(manaCost -> !(manaCost instanceof SnowManaCost))
                .map(ManaCost::getSourceFilter)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
        if (filter != null) {
            newCost.setSourceFilter(filter);
        }

        return newCost;
    }

    public static void reduceCost(SpellAbility spellAbility, ManaCosts<ManaCost> manaCostsToReduce) {
        adjustCost(spellAbility, manaCostsToReduce, true);
    }

    public static void increaseCost(SpellAbility spellAbility, ManaCosts<ManaCost> manaCostsToIncrease) {
        ManaCosts<ManaCost> increasedCost = spellAbility.getManaCostsToPay().copy();

        for (ManaCost manaCost : manaCostsToIncrease) {
            increasedCost.add(manaCost.copy());
        }

        spellAbility.clearManaCostsToPay();
        spellAbility.addManaCostsToPay(increasedCost);
    }

    /**
     * Adjusts spell or ability cost to be paid by colored and generic mana.
     *
     * @param ability           spell or ability to reduce the cost of
     * @param manaCostsToReduce reduces the spell or ability cost by that much
     * @param convertToGeneric  colored mana does reduce generic mana if no
     *                          appropriate colored mana is in the costs
     *                          included
     */
    public static void adjustCost(Ability ability, ManaCosts<ManaCost> manaCostsToReduce, boolean convertToGeneric) {
        ManaCosts<ManaCost> previousCost = ability.getManaCostsToPay();
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
        ability.clearManaCostsToPay();
        ability.addManaCostsToPay(adjustedCost);
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
        return message != null ? message.replace("{this}", sourceName) : null;
    }

    public static String booleanToFlipName(boolean flip) {
        if (flip) {
            return "Heads";
        }
        return "Tails";
    }

    public static boolean checkNumeric(String s) {
        return !s.isEmpty() && s.chars().allMatch(Character::isDigit);
    }

    /**
     * Parse card number as int (support base [123] and alternative numbers
     * [123b], [U123]).
     *
     * @param cardNumber origin card number
     * @return int
     */
    public static int parseCardNumberAsInt(String cardNumber) {

        if (cardNumber == null || cardNumber.isEmpty()) {
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

    public static UUID getExileZoneId(Game game, Ability source) {
        return getExileZoneId(game, source, 0);
    }

    public static UUID getExileZoneId(Game game, Ability source, int offset) {
        return getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter() + offset);
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

    /**
     * Integer operation with overflow protection
     *
     * @param base
     * @param increment
     * @return
     */
    public static int overflowInc(int base, int increment) {
        return overflowResult((long) base + increment);
    }

    /**
     * Integer operation with overflow protection
     *
     * @param base
     * @param decrement
     * @return
     */
    public static int overflowDec(int base, int decrement) {
        return overflowResult((long) base - decrement);
    }

    /**
     * Integer operation with overflow protection
     *
     * @param base
     * @param multiply
     * @return
     */
    public static int overflowMultiply(int base, int multiply) {
        return overflowResult((long) base * multiply);
    }

    private static int overflowResult(long value) {
        if (value >= Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        } else if (value <= Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        } else {
            return (int) value;
        }
    }

    public static String createObjectRealtedWindowTitle(Ability source, Game game, String textSuffix) {
        String title;
        if (source != null) {
            MageObject sourceObject = game.getObject(source);
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
        return name == null
                || name.isEmpty()
                || EmptyNames.isEmptyName(name);
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

    public static String concatManaSymbols(String delimeter, String mana1, String mana2) {
        String res = mana1;
        if (!res.isEmpty() && !mana2.isEmpty() && delimeter != null && !delimeter.isEmpty()) {
            res += delimeter;
        }
        res += mana2;
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

    public static String getBoostCountAsStr(DynamicValue power, DynamicValue toughness) {
        // sign fix for zero values
        // -1/+0 must be -1/-0
        // +0/-1 must be -0/-1
        String p = power.toString();
        String t = toughness.toString();
        if (!p.startsWith("-")) {
            p = t.startsWith("-") && p.equals("0") ? "-0" : "+" + p;
        }
        if (!t.startsWith("-")) {
            t = p.startsWith("-") && t.equals("0") ? "-0" : "+" + t;
        }
        return p + "/" + t;
    }

    public static String getBoostCountAsStr(int power, int toughness) {
        return getBoostCountAsStr(StaticValue.get(power), StaticValue.get(toughness));
    }

    public static String getBoostText(DynamicValue power, DynamicValue toughness, Duration duration) {
        String boostCount = getBoostCountAsStr(power, toughness);
        StringBuilder sb = new StringBuilder(boostCount);
        // don't include "for the rest of the game" for emblems, etc.
        if (duration != Duration.EndOfGame) {
            String d = duration.toString();
            if (!d.isEmpty()) {
                sb.append(' ').append(d);
            }
        }
        String message = power.getMessage();
        if (message.isEmpty()) {
            message = toughness.getMessage();
        }
        if (!message.isEmpty()) {
            sb.append(boostCount.contains("X") ? ", where X is " : " for each ").append(message);
        }
        return sb.toString();
    }

    public static Outcome getBoostOutcome(DynamicValue power, DynamicValue toughness) {
        if (toughness.getSign() < 0) {
            return Outcome.Removal;
        }
        if (power.getSign() < 0) {
            return Outcome.UnboostCreature;
        }
        return Outcome.BoostCreature;
    }

    public static String getSimpleCountersText(int amount, String forOne, String counterType) {
        return numberToText(amount, forOne) + " " + counterType + " counter" + (amount == 1 ? "" : "s");
    }

    public static String getOneOneCountersText(int amount) {
        return getSimpleCountersText(amount, "a", "+1/+1");
    }

    public static String getAddRemoveCountersText(DynamicValue amount, Counter counter, String description, boolean add) {
        boolean targetPlayerGets = add && (description.endsWith("player") || description.endsWith("opponent"));
        StringBuilder sb = new StringBuilder();
        if (targetPlayerGets) {
            sb.append(description);
            sb.append(" gets ");
        } else {
            sb.append(add ? "put " : "remove ");
        }
        boolean xValue = amount.toString().equals("X");
        if (xValue) {
            sb.append("X ").append(counter.getName()).append(" counters");
        } else if (amount == SavedDamageValue.MANY || amount == SavedGainedLifeValue.MANY) {
            sb.append("that many ").append(counter.getName()).append(" counters");
        } else {
            sb.append(counter.getDescription());
        }
        if (!targetPlayerGets) {
            sb.append(add ? " on " : " from ").append(description);
        }
        if (!amount.getMessage().isEmpty()) {
            sb.append(xValue ? ", where X is " : " for each ").append(amount.getMessage());
        }
        return sb.toString();
    }

    public static boolean isFusedPartAbility(Ability ability, Game game) {
        // TODO: does it work fine with copies of spells on stack?
        if (!(ability instanceof SpellAbility)) {
            return false;
        }
        if (((SpellAbility) ability).getSpellAbilityType() == SpellAbilityType.SPLICE) {
            return true;
        }
        Spell mainSpell = game.getSpell(ability.getId());
        if (mainSpell == null) {
            return true;
        }
        SpellAbility mainSpellAbility = mainSpell.getSpellAbility();
        return mainSpellAbility.getSpellAbilityType() == SpellAbilityType.SPLIT_FUSED
                && !ability.equals(mainSpellAbility);
    }

    public static Abilities<Ability> getAbilities(MageObject object, Game game) {
        if (object instanceof Card) {
            return ((Card) object).getAbilities(game);
        } else if (object instanceof Commander && Zone.COMMAND.equals(game.getState().getZone(object.getId()))) {
            // Commanders in command zone must gain cost related abilities for playable
            // calculation (affinity, convoke; example: Chief Engineer). So you must use card object here
            Card card = game.getCard(object.getId());
            if (card != null) {
                return card.getAbilities(game);
            } else {
                return object.getAbilities();
            }
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

    public static String getTextWithFirstCharLowerCase(String text) {
        if (text != null && text.length() >= 1) {
            return Character.toLowerCase(text.charAt(0)) + text.substring(1);
        } else {
            return text;
        }
    }

    private static final String vowels = "aeiouAEIOU8";

    public static String addArticle(String text) {
        if (text.startsWith("a ")
                || text.startsWith("an ")
                || text.startsWith("another ")
                || text.startsWith("any ")
                || text.startsWith("{this} ")
                || text.startsWith("your ")
                || text.startsWith("one ")) {
            return text;
        }
        return (!text.isEmpty() && vowels.contains(text.substring(0, 1))) ? "an " + text : "a " + text;
    }

    public static String italicizeWithEmDash(String text) {
        return "<i>" + text + "</i> &mdash; ";
    }

    public static String stripReminderText(String text) {
        return text.endsWith(")</i>") ? text.substring(0, text.indexOf(" <i>(")) : text;
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
                .map(t -> t.possibleTargets(ability.getControllerId(), ability, game))
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    /**
     * For finding the spell or ability on the stack for "becomes the target" triggers.
     *
     * @param event the GameEvent.EventType.TARGETED from checkTrigger() or watch()
     * @param game  the Game from checkTrigger() or watch()
     * @return the StackObject which targeted the source, or null if not found
     */
    public static StackObject getTargetingStackObject(GameEvent event, Game game) {
        // In case of multiple simultaneous triggered abilities from the same source,
        // need to get the actual one that targeted, see #8026, #8378
        // Also avoids triggering on cancelled selections, see #8802
        for (StackObject stackObject : game.getStack()) {
            Ability stackAbility = stackObject.getStackAbility();
            if (stackAbility == null || !stackAbility.getSourceId().equals(event.getSourceId())) {
                continue;
            }
            for (Target target : stackAbility.getTargets()) {
                if (target.getTargets().contains(event.getTargetId())) {
                    return stackObject;
                }
            }
        }
        return null;
    }

    /**
     * For ensuring that spells/abilities that target the same object twice only trigger each "becomes the target" ability once.
     * If this is the first attempt at triggering for a given ability targeting a given object,
     * this method records that in the game state for later checks by this same method.
     *
     * @param checkingReference must be unique for each usage (this.id.toString() of the TriggeredAbility, or this.getKey() of the watcher)
     * @param targetingObject   from getTargetingStackObject
     * @param event             the GameEvent.EventType.TARGETED from checkTrigger() or watch()
     * @param game              the Game from checkTrigger() or watch()
     * @return true if already triggered/watched, false if this is the first/only trigger/watch
     */
    public static boolean checkTargetedEventAlreadyUsed(String checkingReference, StackObject targetingObject, GameEvent event, Game game) {
        String stateKey = "targetedMap" + checkingReference;
        // If a spell or ability an opponent controls targets a single permanent you control more than once,
        // Battle Mammoth's triggered ability will trigger only once.
        // However, if a spell or ability an opponent controls targets multiple permanents you control,
        // Battle Mammoth's triggered ability will trigger once for each of those permanents. (2021-02-05)
        Map<UUID, Set<UUID>> targetMap = (Map<UUID, Set<UUID>>) game.getState().getValue(stateKey);
        // targetMap: key - targetId; value - Set of stackObject Ids
        if (targetMap == null) {
            targetMap = new HashMap<>();
        } else {
            targetMap = new HashMap<>(targetMap); // must have new object reference if saved back to game state
        }
        Set<UUID> targetingObjects = targetMap.computeIfAbsent(event.getTargetId(), k -> new HashSet<>());
        if (!targetingObjects.add(targetingObject.getId())) {
            return true; // The trigger/watcher already recorded that target of the stack object
        }
        // Otherwise, store this combination of trigger/watcher + target + stack object
        targetMap.put(event.getTargetId(), targetingObjects);
        game.getState().setValue(stateKey, targetMap);
        return false;
    }

    /**
     * Put card to battlefield without resolve/ETB (for cheats and tests only)
     *
     * @param source  must be non-null (if you need it empty then use fakeSourceAbility)
     * @param game
     * @param newCard
     * @param player
     */
    public static void putCardOntoBattlefieldWithEffects(Ability source, Game game, Card newCard, Player player, boolean tapped) {
        // same logic as ZonesHandler->maybeRemoveFromSourceZone

        // runtime check: must have source
        if (source == null) {
            throw new IllegalArgumentException("Wrong code usage: must use source ability or fakeSourceAbility");
        }

        // runtime check: must use only real cards
        if (newCard instanceof PermanentCard) {
            throw new IllegalArgumentException("Wrong code usage: must put to battlefield only real cards, not PermanentCard");
        }

        // workaround to put real permanent from one side (example: you call mdf card by cheats)
        Card permCard = getDefaultCardSideForBattlefield(game, newCard);

        // prepare card and permanent
        permCard.setZone(Zone.BATTLEFIELD, game);
        permCard.setOwnerId(player.getId());
        PermanentCard permanent;
        if (permCard instanceof MeldCard) {
            permanent = new PermanentMeld(permCard, player.getId(), game);
        } else {
            permanent = new PermanentCard(permCard, player.getId(), game);
        }

        // put onto battlefield with possible counters without ETB
        game.getPermanentsEntering().put(permanent.getId(), permanent);
        permCard.applyEnterWithCounters(permanent, source, game);
        permanent.entersBattlefield(source, game, Zone.OUTSIDE, false);
        game.addPermanent(permanent, game.getState().getNextPermanentOrderNumber());
        game.getPermanentsEntering().remove(permanent.getId());

        // tapped status
        // warning, "enters the battlefield tapped" abilities will be executed before, so don't set to false here
        if (tapped) {
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

    /**
     * Choose default card's part to put on battlefield (for cheats and tests only)
     * or to find a default card side (for copy effect)
     *
     * @param card
     * @return
     */
    public static Card getDefaultCardSideForBattlefield(Game game, Card card) {
        if (card instanceof PermanentCard) {
            return card;
        }

        // must choose left side all time
        Card permCard;
        if (card instanceof SplitCard) {
            permCard = card;
        } else if (card instanceof AdventureCard) {
            permCard = card;
        } else if (card instanceof ModalDoubleFacedCard) {
            permCard = ((ModalDoubleFacedCard) card).getLeftHalfCard();
        } else {
            permCard = card;
        }

        // must be creature/planeswalker (if you catch this error then check targeting/copying code)
        if (permCard.isInstantOrSorcery(game)) {
            throw new IllegalArgumentException("Card side can't be put to battlefield: " + permCard.getName());
        }

        return permCard;
    }

    /**
     * Return card name for same name searching
     *
     * @param card
     * @return
     */
    public static String getCardNameForSameNameSearch(Card card) {
        // it's ok to return one name only cause NamePredicate can find same card by first name
        if (card instanceof SplitCard) {
            return ((SplitCard) card).getLeftHalfCard().getName();
        } else if (card instanceof ModalDoubleFacedCard) {
            return ((ModalDoubleFacedCard) card).getLeftHalfCard().getName();
        } else {
            return card.getName();
        }
    }

    public static List<String> getCardRulesWithAdditionalInfo(UUID cardId, String cardName,
                                                              Abilities<Ability> rulesSource, Abilities<Ability> hintAbilities) {
        return getCardRulesWithAdditionalInfo(null, cardId, cardName, rulesSource, hintAbilities);
    }

    /**
     * Prepare rules list from abilities
     *
     * @param rulesSource abilities list to show as rules
     * @param hintsSource abilities list to show as card hints only (you can add additional hints here; example: from second or transformed side)
     */
    public static List<String> getCardRulesWithAdditionalInfo(Game game, UUID cardId, String cardName,
                                                              Abilities<Ability> rulesSource, Abilities<Ability> hintsSource) {
        try {
            List<String> rules = rulesSource.getRules(cardName);

            if (game == null || game.getPhase() == null) {
                // dynamic hints for started game only
                return rules;
            }

            // additional effect's info from card.addInfo methods
            rules.addAll(game.getState().getCardState(cardId).getInfo().values());

            // ability hints
            List<String> abilityHints = new ArrayList<>();
            if (HintUtils.ABILITY_HINTS_ENABLE) {
                for (Ability ability : hintsSource) {
                    for (Hint hint : ability.getHints()) {
                        String s = hint.getText(game, ability);
                        if (s != null && !s.isEmpty()) {
                            abilityHints.add(s);
                        }
                    }
                }
            }

            // restrict hints only for permanents, not cards
            // total hints
            if (!abilityHints.isEmpty()) {
                rules.add(HintUtils.HINT_START_MARK);
                HintUtils.appendHints(rules, abilityHints);
            }

            return rules;
        } catch (Exception e) {
            logger.error("Exception in rules generation for card: " + cardName, e);
        }
        return RULES_ERROR_INFO;
    }

    /**
     * Take control under another player, use it in inner effects like Word of Commands. Don't forget to end it in same code.
     *
     * @param game
     * @param controller
     * @param playerUnderControl
     * @param givePauseForResponse if you want to give controller time to watch opponent's hand (if you remove control effect in the end of code)
     */
    public static void takeControlUnderPlayerStart(Game game, Ability source, Player controller, Player playerUnderControl, boolean givePauseForResponse) {
        // game logs added in child's call
        controller.controlPlayersTurn(game, playerUnderControl.getId(), CardUtil.getSourceLogName(game, source));
        if (givePauseForResponse) {
            while (controller.canRespond()) {
                if (controller.chooseUse(Outcome.Benefit, "You got control of " + playerUnderControl.getLogName()
                                + ". Use switch hands button to view opponent's hand.", null,
                        "Continue", "Wait", null, game)) {
                    break;
                }
            }
        }
    }

    /**
     * Return control under another player, use it in inner effects like Word of Commands.
     *
     * @param game
     * @param controller
     * @param playerUnderControl
     */
    public static void takeControlUnderPlayerEnd(Game game, Ability source, Player controller, Player playerUnderControl) {
        playerUnderControl.setGameUnderYourControl(true, false);
        if (!playerUnderControl.getTurnControlledBy().equals(controller.getId())) {
            game.informPlayers(controller + " return control of the turn to " + playerUnderControl.getLogName() + CardUtil.getSourceLogName(game, source));
            controller.getPlayersUnderYourControl().remove(playerUnderControl.getId());
        }
    }

    // TODO: use CastManaAdjustment instead of boolean anyColor
    public static void makeCardPlayable(Game game, Ability source, Card card, boolean useCastSpellOnly, Duration duration, boolean anyColor) {
        makeCardPlayable(game, source, card, useCastSpellOnly, duration, anyColor, null, null);
    }

    /**
     * Add effects to game that allows to play/cast card from current zone and spend mana as any type for it.
     * Effects will be discarded/ignored on any card movements or blinks (after ZCC change)
     * <p>
     * Affected to all card's parts
     *
     * @param game
     * @param card
     * @param duration
     * @param anyColor
     * @param condition can be null
     */
    // TODO: use CastManaAdjustment instead of boolean anyColor
    public static void makeCardPlayable(Game game, Ability source, Card card, boolean useCastSpellOnly, Duration duration, boolean anyColor, UUID playerId, Condition condition) {
        // Effect can be used for cards in zones and permanents on battlefield
        // PermanentCard's ZCC is static, but we need updated ZCC from the card (after moved to another zone)
        // So there is a workaround to get actual card's ZCC
        // Example: Hostage Taker
        UUID objectId = card.getMainCard().getId();
        int zcc = game.getState().getZoneChangeCounter(objectId);
        game.addEffect(new CanPlayCardControllerEffect(game, objectId, zcc, useCastSpellOnly, duration, playerId, condition), source);
        if (anyColor) {
            game.addEffect(new YouMaySpendManaAsAnyColorToCastTargetEffect(duration, playerId, condition).setTargetPointer(new FixedTarget(objectId, zcc)), source);
        }
    }

    public interface SpellCastTracker {

        boolean checkCard(Card card, Game game);

        void addCard(Card card, Ability source, Game game);
    }

    /**
     * Retrieves a list of all castable components from a given card based on certain conditions.
     * <p>
     * Castable components are parts of a card that can be played or cast,
     * such as the adventure and main side of adventure spells or both sides of a fuse card.
     *
     * @param cardToCast
     * @param filter           An optional filter to determine if a card is eligible for casting.
     * @param source           The ability or source responsible for the casting.
     * @param player
     * @param game
     * @param spellCastTracker An optional tracker for spell casting.
     * @param playLand         A boolean flag indicating whether playing lands is allowed.
     * @return A list of castable components from the input card, considering the provided conditions.
     */
    public static List<Card> getCastableComponents(Card cardToCast, FilterCard filter, Ability source, Player player, Game game, SpellCastTracker spellCastTracker, boolean playLand) {
        UUID playerId = player.getId();
        List<Card> cards = new ArrayList<>();
        if (cardToCast instanceof CardWithHalves) {
            cards.add(((CardWithHalves) cardToCast).getLeftHalfCard());
            cards.add(((CardWithHalves) cardToCast).getRightHalfCard());
        } else if (cardToCast instanceof AdventureCard) {
            cards.add(cardToCast);
            cards.add(((AdventureCard) cardToCast).getSpellCard());
        } else {
            cards.add(cardToCast);
        }
        cards.removeIf(Objects::isNull);
        if (!playLand || !player.canPlayLand() || !game.isActivePlayer(playerId)) {
            cards.removeIf(card -> card.isLand(game));
        }
        if (filter != null) {
            cards.removeIf(card -> !filter.match(card, playerId, source, game));
        }
        if (spellCastTracker != null) {
            cards.removeIf(card -> !spellCastTracker.checkCard(card, game));
        }
        return cards;
    }

    private static final FilterCard defaultFilter = new FilterCard("card to cast");

    public static boolean castSpellWithAttributesForFree(Player player, Ability source, Game game, Card card) {
        return castSpellWithAttributesForFree(player, source, game, card, StaticFilters.FILTER_CARD);
    }

    public static boolean castSpellWithAttributesForFree(Player player, Ability source, Game game, Card card, FilterCard filter) {
        return castSpellWithAttributesForFree(player, source, game, new CardsImpl(card), filter);
    }

    public static boolean castSpellWithAttributesForFree(Player player, Ability source, Game game, Cards cards, FilterCard filter) {
        return castSpellWithAttributesForFree(player, source, game, cards, filter, null);
    }

    public static boolean castSpellWithAttributesForFree(Player player, Ability source, Game game, Cards cards, FilterCard filter, SpellCastTracker spellCastTracker) {
        return castSpellWithAttributesForFree(player, source, game, cards, filter, spellCastTracker, false);
    }

    public static boolean castSpellWithAttributesForFree(Player player, Ability source, Game game, Cards cards, FilterCard filter, SpellCastTracker spellCastTracker, boolean playLand) {
        Map<UUID, List<Card>> cardMap = new HashMap<>();
        for (Card card : cards.getCards(game)) {
            List<Card> castableComponents = getCastableComponents(card, filter, source, player, game, spellCastTracker, playLand);
            if (!castableComponents.isEmpty()) {
                cardMap.put(card.getId(), castableComponents);
            }
        }
        Card cardToCast;
        switch (cardMap.size()) {
            case 0:
                return false;
            case 1:
                cardToCast = cards.get(cardMap.keySet().stream().findFirst().orElse(null), game);
                break;
            default:
                Cards castableCards = new CardsImpl(cardMap.keySet());
                TargetCard target = new TargetCard(0, 1, Zone.ALL, defaultFilter);
                target.withNotTarget(true);
                player.choose(Outcome.PlayForFree, castableCards, target, source, game);
                cardToCast = castableCards.get(target.getFirstTarget(), game);
        }
        if (cardToCast == null) {
            return false;
        }
        List<Card> partsToCast = cardMap.get(cardToCast.getId());
        String partsInfo = partsToCast
                .stream()
                .map(MageObject::getLogName)
                .collect(Collectors.joining(" or "));
        if (partsToCast.size() < 1
                || !player.chooseUse(
                Outcome.PlayForFree, "Cast spell without paying its mana cost (" + partsInfo + ")?", source, game
        )) {
            return false;
        }
        partsToCast.forEach(card -> game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE));
        ActivatedAbility chosenAbility;
        if (playLand) {
            chosenAbility = player.chooseLandOrSpellAbility(cardToCast, game, true);
        } else {
            chosenAbility = player.chooseAbilityForCast(cardToCast, game, true);
        }
        boolean result = false;
        if (chosenAbility instanceof SpellAbility) {
            result = player.cast(
                    (SpellAbility) chosenAbility,
                    game, true, new ApprovingObject(source, game)
            );
        } else if (playLand && chosenAbility instanceof PlayLandAbility) {
            Card land = game.getCard(chosenAbility.getSourceId());
            result = player.playLand(land, game, true);
        }
        partsToCast.forEach(card -> game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null));
        if (result && spellCastTracker != null) {
            spellCastTracker.addCard(cardToCast, source, game);
        }
        if (player.isComputer() && !result) {
            cards.remove(cardToCast);
        }
        return result;
    }

    private static boolean checkForPlayable(Cards cards, FilterCard filter, Ability source, Player player, Game game, SpellCastTracker spellCastTracker, boolean playLand) {
        return cards
                .getCards(game)
                .stream()
                .anyMatch(card -> !getCastableComponents(card, filter, source, player, game, spellCastTracker, playLand).isEmpty());
    }

    public static void castMultipleWithAttributeForFree(Player player, Ability source, Game game, Cards cards, FilterCard filter) {
        castMultipleWithAttributeForFree(player, source, game, cards, filter, Integer.MAX_VALUE);
    }

    public static void castMultipleWithAttributeForFree(Player player, Ability source, Game game, Cards cards, FilterCard filter, int maxSpells) {
        castMultipleWithAttributeForFree(player, source, game, cards, filter, maxSpells, null);
    }

    public static void castMultipleWithAttributeForFree(Player player, Ability source, Game game, Cards cards, FilterCard filter, int maxSpells, SpellCastTracker spellCastTracker) {
        castMultipleWithAttributeForFree(player, source, game, cards, filter, maxSpells, spellCastTracker, false);
    }

    public static void castMultipleWithAttributeForFree(Player player, Ability source, Game game, Cards cards, FilterCard filter, int maxSpells, SpellCastTracker spellCastTracker, boolean playLand) {
        if (maxSpells == 1) {
            CardUtil.castSpellWithAttributesForFree(player, source, game, cards, filter);
            return;
        }
        int spellsCast = 0;
        cards.removeZone(Zone.STACK, game);
        while (player.canRespond() && spellsCast < maxSpells && !cards.isEmpty()) {
            if (CardUtil.castSpellWithAttributesForFree(player, source, game, cards, filter, spellCastTracker, playLand)) {
                spellsCast++;
                cards.removeZone(Zone.STACK, game);
            } else if (!checkForPlayable(
                    cards, filter, source, player, game, spellCastTracker, playLand
            ) || !player.chooseUse(
                    Outcome.PlayForFree, "Continue casting spells?", source, game
            )) {
                break;
            }
        }
    }

    public static void castSingle(Player player, Ability source, Game game, Card card) {
        castSingle(player, source, game, card, null);
    }

    public static void castSingle(Player player, Ability source, Game game, Card card, ManaCostsImpl<ManaCost> manaCost) {
        castSingle(player, source, game, card, false, manaCost);
    }

    public static void castSingle(Player player, Ability source, Game game, Card card, boolean noMana, ManaCostsImpl<ManaCost> manaCost) {
        // handle split-cards
        if (card instanceof SplitCard) {
            SplitCardHalf leftHalfCard = ((SplitCard) card).getLeftHalfCard();
            SplitCardHalf rightHalfCard = ((SplitCard) card).getRightHalfCard();
            if (manaCost != null) {
                // get additional cost if any
                Costs<Cost> additionalCostsLeft = leftHalfCard.getSpellAbility().getCosts();
                Costs<Cost> additionalCostsRight = rightHalfCard.getSpellAbility().getCosts();
                // set alternative cost and any additional cost
                player.setCastSourceIdWithAlternateMana(leftHalfCard.getId(), manaCost, additionalCostsLeft, MageIdentifier.Default);
                player.setCastSourceIdWithAlternateMana(rightHalfCard.getId(), manaCost, additionalCostsRight, MageIdentifier.Default);
            }
            // allow the card to be cast
            game.getState().setValue("PlayFromNotOwnHandZone" + leftHalfCard.getId(), Boolean.TRUE);
            game.getState().setValue("PlayFromNotOwnHandZone" + rightHalfCard.getId(), Boolean.TRUE);
        }

        // handle MDFC
        if (card instanceof ModalDoubleFacedCard) {
            ModalDoubleFacedCardHalf leftHalfCard = ((ModalDoubleFacedCard) card).getLeftHalfCard();
            ModalDoubleFacedCardHalf rightHalfCard = ((ModalDoubleFacedCard) card).getRightHalfCard();
            if (manaCost != null) {
                // some MDFC cards are lands.  IE: sea gate restoration
                if (!leftHalfCard.isLand(game)) {
                    // get additional cost if any
                    Costs<Cost> additionalCostsMDFCLeft = leftHalfCard.getSpellAbility().getCosts();
                    // set alternative cost and any additional cost
                    player.setCastSourceIdWithAlternateMana(leftHalfCard.getId(), manaCost, additionalCostsMDFCLeft, MageIdentifier.Default);
                }
                if (!rightHalfCard.isLand(game)) {
                    // get additional cost if any
                    Costs<Cost> additionalCostsMDFCRight = rightHalfCard.getSpellAbility().getCosts();
                    // set alternative cost and any additional cost
                    player.setCastSourceIdWithAlternateMana(rightHalfCard.getId(), manaCost, additionalCostsMDFCRight, MageIdentifier.Default);
                }
            }
            // allow the card to be cast
            game.getState().setValue("PlayFromNotOwnHandZone" + leftHalfCard.getId(), Boolean.TRUE);
            game.getState().setValue("PlayFromNotOwnHandZone" + rightHalfCard.getId(), Boolean.TRUE);
        }

        // handle adventure cards
        if (card instanceof AdventureCard) {
            Card creatureCard = card.getMainCard();
            Card spellCard = ((AdventureCard) card).getSpellCard();
            if (manaCost != null) {
                // get additional cost if any
                Costs<Cost> additionalCostsCreature = creatureCard.getSpellAbility().getCosts();
                Costs<Cost> additionalCostsSpellCard = spellCard.getSpellAbility().getCosts();
                // set alternative cost and any additional cost
                player.setCastSourceIdWithAlternateMana(creatureCard.getId(), manaCost, additionalCostsCreature, MageIdentifier.Default);
                player.setCastSourceIdWithAlternateMana(spellCard.getId(), manaCost, additionalCostsSpellCard, MageIdentifier.Default);
            }
            // allow the card to be cast
            game.getState().setValue("PlayFromNotOwnHandZone" + creatureCard.getId(), Boolean.TRUE);
            game.getState().setValue("PlayFromNotOwnHandZone" + spellCard.getId(), Boolean.TRUE);
        }

        // normal card
        if (manaCost != null) {
            // get additional cost if any
            Costs<Cost> additionalCostsNormalCard = card.getSpellAbility().getCosts();
            player.setCastSourceIdWithAlternateMana(card.getMainCard().getId(), manaCost, additionalCostsNormalCard, MageIdentifier.Default);
        }

        // cast it
        player.cast(player.chooseAbilityForCast(card.getMainCard(), game, noMana),
                game, noMana, new ApprovingObject(source, game));

        // turn off effect after cast on every possible card-face
        if (card instanceof SplitCard) {
            SplitCardHalf leftHalfCard = ((SplitCard) card).getLeftHalfCard();
            SplitCardHalf rightHalfCard = ((SplitCard) card).getRightHalfCard();
            game.getState().setValue("PlayFromNotOwnHandZone" + leftHalfCard.getId(), null);
            game.getState().setValue("PlayFromNotOwnHandZone" + rightHalfCard.getId(), null);
        }
        if (card instanceof ModalDoubleFacedCard) {
            ModalDoubleFacedCardHalf leftHalfCard = ((ModalDoubleFacedCard) card).getLeftHalfCard();
            ModalDoubleFacedCardHalf rightHalfCard = ((ModalDoubleFacedCard) card).getRightHalfCard();
            game.getState().setValue("PlayFromNotOwnHandZone" + leftHalfCard.getId(), null);
            game.getState().setValue("PlayFromNotOwnHandZone" + rightHalfCard.getId(), null);
        }
        if (card instanceof AdventureCard) {
            Card creatureCard = card.getMainCard();
            Card spellCard = ((AdventureCard) card).getSpellCard();
            game.getState().setValue("PlayFromNotOwnHandZone" + creatureCard.getId(), null);
            game.getState().setValue("PlayFromNotOwnHandZone" + spellCard.getId(), null);
        }
        // turn off effect on a normal card
        game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
    }

    /**
     * Pay life in effects
     *
     * @param lifeToPay
     * @param player
     * @param source
     * @param game
     * @return true on good pay
     */
    public static boolean tryPayLife(int lifeToPay, Player player, Ability source, Game game) {
        // rules:
        // 119.4. If a cost or effect allows a player to pay an amount of life greater than 0, the player may do so
        // only if their life total is greater than or equal to the amount of the payment. If a player pays life,
        // the payment is subtracted from their life total; in other words, the player loses that much life.
        // (Players can always pay 0 life.)
        if (lifeToPay == 0) {
            return true;
        } else if (lifeToPay < 0) {
            return false;
        }

        if (lifeToPay > player.getLife()) {
            return false;
        }

        if (game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.PAY_LIFE, player.getId(), source, player.getId(), lifeToPay))) {
            // 2023-08-20: For now, Cost being replaced are paid.
            // Waiting on actual ruling of Ashiok, Wicked Manipulator.
            return true;
        }
        if (player.loseLife(lifeToPay, game, source, false) >= lifeToPay) {
            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.LIFE_PAID, player.getId(), source, player.getId(), lifeToPay));
            return true;
        }

        return false;
    }

    public static String getSourceName(Game game, Ability source) {
        MageObject sourceObject = source.getSourceObject(game);
        return sourceObject != null ? sourceObject.getName() : "";
    }

    public static String getSourceIdName(Game game, Ability source) {
        MageObject sourceObject = source.getSourceObject(game);
        return sourceObject != null ? sourceObject.getIdName() : "";
    }

    /**
     * Generates source log name to insert into log messages
     *
     * @param game
     * @param sourceId
     * @param namePrefix   if source object exists then that will be added before name
     * @param namePostfix  if source object exists then that will be added after name
     * @param nonFoundText if source object not exists then it will be used
     * @return
     */
    public static String getSourceLogName(Game game, String namePrefix, UUID sourceId, String namePostfix, String nonFoundText) {
        MageObject sourceObject = game.getObject(sourceId);
        return (sourceObject == null ? nonFoundText : namePrefix + sourceObject.getLogName() + namePostfix);
    }

    public static String getSourceLogName(Game game, String namePrefix, Ability source, String namePostfix, String nonFoundText) {
        return getSourceLogName(game, namePrefix, source == null ? null : source.getSourceId(), namePostfix, nonFoundText);
    }

    public static String getSourceLogName(Game game, Ability source) {
        return CardUtil.getSourceLogName(game, " (source: ", source, ")", "");
    }

    public static String getSourceLogName(Game game, Ability source, UUID ignoreSourceId) {
        if (ignoreSourceId != null && source != null && ignoreSourceId.equals(source.getSourceId())) {
            return "";
        }
        return CardUtil.getSourceLogName(game, " (source: ", source, ")", "");
    }

    /**
     * Find actual ZCC of source object, works in any moment (even before source ability activated)
     * <p>
     * Use case for usage: if you want to get actual object ZCC before ability resolve
     * (ability gets zcc after resolve/activate/trigger only -- ?wtf workaround to targets setup I think?)
     *
     * @param game
     * @param source
     * @return
     */
    public static int getActualSourceObjectZoneChangeCounter(Game game, Ability source) {
        // current object zcc, find from source object (it can be permanent or spell on stack)
        int zcc = source.getSourceObjectZoneChangeCounter();
        if (zcc == 0) {
            // if ability is not activated yet then use current object's zcc (example: triggered etb ability checking the kicker conditional)
            zcc = game.getState().getZoneChangeCounter(source.getSourceId());
        }
        return zcc;
    }

    /**
     * Create a MageObjectReference of the ability's source
     * Subtract 1 zcc if not on the stack, referencing when it was on the stack if it's a resolved permanent.
     * works in any moment (even before source ability activated)
     *
     * @param game
     * @param ability
     * @return MageObjectReference to the ability's source stack moment
     */
    public static MageObjectReference getSourceStackMomentReference(Game game, Ability ability) {
        // Squad/Kicker activates in STACK zone so all zcc must be from "stack moment"
        // Use cases:
        // * resolving spell have same zcc (example: check kicker status in sorcery/instant);
        // * copied spell have same zcc as source spell (see Spell.copySpell and zcc sync);
        // * creature/token from resolved spell have +1 zcc after moved to battlefield (example: check kicker status in ETB triggers/effects);

        // find object info from the source ability (it can be a permanent or a spell on stack, on the moment of trigger/resolve)
        MageObject sourceObject = ability.getSourceObject(game);
        Zone sourceObjectZone = game.getState().getZone(sourceObject.getId());
        int zcc = CardUtil.getActualSourceObjectZoneChangeCounter(game, ability);
        // find "stack moment" zcc:
        // * permanent cards enters from STACK to BATTLEFIELD (+1 zcc)
        // * permanent tokens enters from OUTSIDE to BATTLEFIELD (+1 zcc, see prepare code in TokenImpl.putOntoBattlefieldHelper)
        // * spells and copied spells resolves on STACK (zcc not changes)
        if (sourceObjectZone != Zone.STACK) {
            --zcc;
        }
        return new MageObjectReference(ability.getSourceId(), zcc, game);
    }

    /**
     * Returns the entire cost tags map of either the source ability, or the permanent source of the ability. May be null.
     * Works in any moment (even before source ability activated)
     * Usually you should use one of the single tag functions instead: getSourceCostsTag() or checkSourceCostsTagExists()
     * Use this function with caution, as it directly exposes the backing data structure.
     *
     * @param game
     * @param source
     * @return the tag map (or null)
     */
    public static Map<String, Object> getSourceCostsTagsMap(Game game, Ability source) {
        Map<String, Object> costTags;
        costTags = source.getCostsTagMap();
        if (costTags == null && source.getSourcePermanentOrLKI(game) != null) {
            costTags = game.getPermanentCostsTags().get(CardUtil.getSourceStackMomentReference(game, source));
        }
        return costTags;
    }

    /**
     * Check if a specific tag exists in the cost tags of either the source ability, or the permanent source of the ability.
     * Works in any moment (even before source ability activated)
     *
     * @param game
     * @param source
     * @param tag    The tag's string identifier to look up
     * @return if the tag was found
     */
    public static boolean checkSourceCostsTagExists(Game game, Ability source, String tag) {
        Map<String, Object> costTags = getSourceCostsTagsMap(game, source);
        return costTags != null && costTags.containsKey(tag);
    }

    /**
     * Find a specific tag in the cost tags of either the source ability, or the permanent source of the ability.
     * Works in any moment (even before source ability activated)
     * Do not use with null values, use checkSourceCostsTagExists instead
     *
     * @param game
     * @param source
     * @param tag          The tag's string identifier to look up
     * @param defaultValue A default value to return if the tag is not found
     * @return The object stored by the tag if found, the default if not
     */
    public static <T> T getSourceCostsTag(Game game, Ability source, String tag, T defaultValue) {
        Map<String, Object> costTags = getSourceCostsTagsMap(game, source);
        if (costTags != null) {
            Object value = costTags.getOrDefault(tag, defaultValue);
            if (value == null) {
                throw new IllegalStateException("Wrong code usage: Costs tag " + tag + " has value stored of type null but is trying to be read. Use checkSourceCostsTagExists");
            }
            if (value.getClass() != defaultValue.getClass()) {
                throw new IllegalStateException("Wrong code usage: Costs tag " + tag + " has value stored of type " + value.getClass().getName() + " different from default of type " + defaultValue.getClass().getName());
            }
            return (T) value;
        }
        return defaultValue;
    }

    public static String addCostVerb(String text) {
        if (costWords.stream().anyMatch(text.toLowerCase(Locale.ENGLISH)::startsWith)) {
            return text;
        }
        return "pay " + text;
    }

    private static boolean isImmutableObject(Object o) {
        return o == null
                || o instanceof Number || o instanceof Boolean || o instanceof String
                || o instanceof MageObjectReference || o instanceof UUID
                || o instanceof Enum;
    }

    /**
     * Make deep copy of any object (supported by xmage)
     * <p>
     * Warning, don't use self reference objects because it will raise StackOverflowError
     *
     * @param value
     * @param <T>
     * @return
     */
    public static <T> T deepCopyObject(T value) {
        if (isImmutableObject(value)) {
            return value;
        } else if (value instanceof Copyable) {
            return (T) ((Copyable<T>) value).copy();
        } else if (value instanceof Watcher) {
            return (T) ((Watcher) value).copy();
        } else if (value instanceof Ability) {
            return (T) ((Ability) value).copy();
        } else if (value instanceof PlayerList) {
            return (T) ((PlayerList) value).copy();
        } else if (value instanceof EnumSet) {
            return (T) ((EnumSet) value).clone();
        } else if (value instanceof EnumMap) {
            return (T) deepCopyEnumMap((EnumMap) value);
        } else if (value instanceof LinkedHashSet) {
            return (T) deepCopyLinkedHashSet((LinkedHashSet) value);
        } else if (value instanceof LinkedHashMap) {
            return (T) deepCopyLinkedHashMap((LinkedHashMap) value);
        } else if (value instanceof TreeSet) {
            return (T) deepCopyTreeSet((TreeSet) value);
        } else if (value instanceof HashSet) {
            return (T) deepCopyHashSet((HashSet) value);
        } else if (value instanceof HashMap) {
            return (T) deepCopyHashMap((HashMap) value);
        } else if (value instanceof List) {
            return (T) deepCopyList((List) value);
        } else if (value instanceof AbstractMap.SimpleImmutableEntry) { //Used by Leonin Arbiter, Vessel Of The All Consuming Wanderer as a generic Pair class
            AbstractMap.SimpleImmutableEntry entryValue = (AbstractMap.SimpleImmutableEntry) value;
            return (T) new AbstractMap.SimpleImmutableEntry(deepCopyObject(entryValue.getKey()), deepCopyObject(entryValue.getValue()));
        } else {
            // warning, do not add unnecessarily new data types and structures to game engine, try to use only standard types (see above)
            throw new IllegalStateException("Unhandled object " + value.getClass().getSimpleName() + " during deep copy, must add explicit handling of all Object types");
        }
    }

    private static <T extends Comparable<T>> TreeSet<T> deepCopyTreeSet(TreeSet<T> original) {
        if (original.getClass() != TreeSet.class) {
            throw new IllegalStateException("Unhandled TreeSet type " + original.getClass().getSimpleName() + " in deep copy");
        }
        TreeSet<T> newSet = new TreeSet<>();
        for (T value : original) {
            newSet.add((T) deepCopyObject(value));
        }
        return newSet;
    }

    private static <T> HashSet<T> deepCopyHashSet(Set<T> original) {
        if (original.getClass() != HashSet.class) {
            throw new IllegalStateException("Unhandled HashSet type " + original.getClass().getSimpleName() + " in deep copy");
        }
        HashSet<T> newSet = new HashSet<>(original.size());
        for (T value : original) {
            newSet.add((T) deepCopyObject(value));
        }
        return newSet;
    }

    private static <T> LinkedHashSet<T> deepCopyLinkedHashSet(LinkedHashSet<T> original) {
        if (original.getClass() != LinkedHashSet.class) {
            throw new IllegalStateException("Unhandled LinkedHashSet type " + original.getClass().getSimpleName() + " in deep copy");
        }
        LinkedHashSet<T> newSet = new LinkedHashSet<>(original.size());
        for (T value : original) {
            newSet.add((T) deepCopyObject(value));
        }
        return newSet;
    }

    private static <T> List<T> deepCopyList(List<T> original) { //always returns an ArrayList
        if (original.getClass() != ArrayList.class) {
            throw new IllegalStateException("Unhandled List type " + original.getClass().getSimpleName() + " in deep copy");
        }
        ArrayList<T> newList = new ArrayList<>(original.size());
        for (T value : original) {
            newList.add((T) deepCopyObject(value));
        }
        return newList;
    }

    private static <K, V> HashMap<K, V> deepCopyHashMap(Map<K, V> original) {
        if (original.getClass() != HashMap.class) {
            throw new IllegalStateException("Unhandled HashMap type " + original.getClass().getSimpleName() + " in deep copy");
        }
        HashMap<K, V> newMap = new HashMap<>(original.size());
        for (Map.Entry<K, V> entry : original.entrySet()) {
            newMap.put((K) deepCopyObject(entry.getKey()), (V) deepCopyObject(entry.getValue()));
        }
        return newMap;
    }

    private static <K, V> LinkedHashMap<K, V> deepCopyLinkedHashMap(Map<K, V> original) {
        if (original.getClass() != LinkedHashMap.class) {
            throw new IllegalStateException("Unhandled LinkedHashMap type " + original.getClass().getSimpleName() + " in deep copy");
        }
        LinkedHashMap<K, V> newMap = new LinkedHashMap<>(original.size());
        for (Map.Entry<K, V> entry : original.entrySet()) {
            newMap.put((K) deepCopyObject(entry.getKey()), (V) deepCopyObject(entry.getValue()));
        }
        return newMap;
    }

    private static <K extends Enum<K>, V> EnumMap<K, V> deepCopyEnumMap(Map<K, V> original) {
        if (original.getClass() != EnumMap.class) {
            throw new IllegalStateException("Unhandled EnumMap type " + original.getClass().getSimpleName() + " in deep copy");
        }
        EnumMap<K, V> newMap = new EnumMap<>(original);
        for (Map.Entry<K, V> entry : newMap.entrySet()) {
            entry.setValue((V) deepCopyObject(entry.getValue()));
        }
        return newMap;
    }

    /**
     * Collect all possible object's parts (example: all sides in mdf/split cards)
     * <p>
     * Works with any objects, so commander object can return four ids: commander + main card + left card + right card
     * If you pass Card object then it return main card + all parts
     *
     * @param object
     * @return
     */
    public static Set<UUID> getObjectParts(MageObject object) {
        Set<UUID> res = new LinkedHashSet<>(); // set must be ordered
        List<MageObject> allParts = getObjectPartsAsObjects(object);
        allParts.forEach(part -> {
            res.add(part.getId());
        });
        return res;
    }

    public static List<MageObject> getObjectPartsAsObjects(MageObject object) {
        List<MageObject> res = new ArrayList<>();
        if (object == null) {
            return res;
        }

        if (object instanceof SplitCard || object instanceof SplitCardHalf) {
            SplitCard mainCard = (SplitCard) ((Card) object).getMainCard();
            res.add(mainCard);
            res.add(mainCard.getLeftHalfCard());
            res.add(mainCard.getRightHalfCard());
        } else if (object instanceof ModalDoubleFacedCard || object instanceof ModalDoubleFacedCardHalf) {
            ModalDoubleFacedCard mainCard = (ModalDoubleFacedCard) ((Card) object).getMainCard();
            res.add(mainCard);
            res.add(mainCard.getLeftHalfCard());
            res.add(mainCard.getRightHalfCard());
        } else if (object instanceof AdventureCard || object instanceof AdventureCardSpell) {
            AdventureCard mainCard = (AdventureCard) ((Card) object).getMainCard();
            res.add(mainCard);
            res.add(mainCard.getSpellCard());
        } else if (object instanceof Spell) {
            // example: activate Lightning Storm's ability from the spell on the stack
            res.add(object);
            res.addAll(getObjectPartsAsObjects(((Spell) object).getCard()));
        } else if (object instanceof Commander) {
            // commander can contains double sides
            res.add(object);
            res.addAll(getObjectPartsAsObjects(((Commander) object).getSourceObject()));
        } else {
            res.add(object);
        }
        return res;
    }

    public static int parseIntWithDefault(String value, int defaultValue) {
        int res;
        try {
            res = Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            res = defaultValue;
        }
        return res;
    }

    /**
     * Find mapping from original to copied card (e.g. map original left side with copied left side)
     *
     * @param originalCard
     * @param copiedCard
     * @return
     */
    public static Map<UUID, MageObject> getOriginalToCopiedPartsMap(Card originalCard, Card copiedCard) {
        List<UUID> oldIds = new ArrayList<>(CardUtil.getObjectParts(originalCard));
        List<MageObject> newObjects = new ArrayList<>(CardUtil.getObjectPartsAsObjects(copiedCard));
        if (oldIds.size() != newObjects.size()) {
            throw new IllegalStateException("Found wrong card parts after copy: " + originalCard.getName() + " -> " + copiedCard.getName());
        }

        Map<UUID, MageObject> mapOldToNew = new HashMap<>();
        for (int i = 0; i < oldIds.size(); i++) {
            mapOldToNew.put(oldIds.get(i), newObjects.get(i));
        }
        return mapOldToNew;
    }

    /**
     * Return turn info for game. Uses in game logs and debug.
     *
     * @param game
     * @return
     */
    public static String getTurnInfo(Game game) {
        return getTurnInfo(game == null ? null : game.getState());
    }

    public static String getTurnInfo(GameState gameState) {
        // no turn info
        if (gameState == null) {
            return null;
        }

        // not started game
        if (gameState.getTurn().getStep() == null) {
            return "T0";
        }

        // normal game
        return "T" + gameState.getTurnNum() + "." + gameState.getTurn().getStep().getType().getStepShortText();
    }

    public static String concatWithOr(List<String> strings) {
        return concatWith(strings, "or");
    }

    public static String concatWithAnd(List<String> strings) {
        return concatWith(strings, "and");
    }

    private static String concatWith(List<String> strings, String last) {
        switch (strings.size()) {
            case 0:
                return "";
            case 1:
                return strings.get(0);
            case 2:
                return strings.get(0) + " " + last + " " + strings.get(1);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strings.size(); i++) {
            sb.append(strings.get(i));
            if (i == strings.size() - 1) {
                break;
            }
            sb.append(", ");
            if (i == strings.size() - 2) {
                sb.append(last);
                sb.append(' ');
            }
        }
        return sb.toString();
    }

    public static <T> Stream<T> castStream(Collection<?> collection, Class<T> clazz) {
        return castStream(collection.stream(), clazz);
    }

    public static <T> Stream<T> castStream(Stream<?> stream, Class<T> clazz) {
        return stream.filter(clazz::isInstance).map(clazz::cast).filter(Objects::nonNull);
    }

    /**
     * Move card or permanent to dest zone and add counter to it
     *
     * @param game
     * @param source
     * @param controller
     * @param card       can be card or permanent
     * @param toZone
     * @param counter
     */
    public static boolean moveCardWithCounter(Game game, Ability source, Player controller, Card card, Zone toZone, Counter counter) {
        if (toZone == Zone.BATTLEFIELD) {
            throw new IllegalArgumentException("Wrong code usage - method doesn't support moving to battlefield zone");
        }

        // workaround:
        // in ZONE_CHANGE replace events you must set new zone by event's setToZone,
        // BUT for counter effect you need to complete zone change event first (so moveCards calls here)
        // TODO: must be fixed someday by:
        //  * or by new event ZONE_CHANGED to apply counter effect on it
        //  * or by counter effects applier in ZONE_CHANGE event (see copy or token as example)

        // move to zone
        if (!controller.moveCards(card, toZone, source, game)) {
            return false;
        }

        // add counter
        // after move it's a new object (not a permanent), so must work with main card
        Effect effect = new AddCountersTargetEffect(counter);
        effect.setTargetPointer(new FixedTarget(card.getMainCard(), game));
        effect.apply(game, source);
        return true;
    }

    public static <T> int setOrIncrementValue(T u, Integer i) {
        return i == null ? 1 : Integer.sum(i, 1);
    }

    public static String convertLoyaltyOrDefense(int value) {
        switch (value) {
            case -2:
                return "X";
            case -1:
                return "";
            default:
                return "" + value;
        }
    }

    public static int convertLoyaltyOrDefense(String value) {
        switch (value) {
            case "X":
                return -2;
            case "":
                return -1;
            default:
                return Integer.parseInt(value);
        }
    }

    public static void checkSetParamForSerializationCompatibility(Set<String> data) {
        // HashMap uses inner class for Keys without serialization support,
        // so you can't use it for client-server data
        if (data != null && data.getClass().getName().endsWith("$KeySet")) {
            throw new IllegalArgumentException("Can't use KeySet as param, use new LinkedHashSet<>(data.keySet()) instead");
        }
    }

    public static String substring(String str, int maxLength) {
        return substring(str, maxLength, "");
    }

    /**
     * Don't raise exception, so must be used instead standard substring calls all the time
     *
     * @param str
     * @param maxLength
     * @param overflowEnding can add ... at the end
     * @return
     */
    public static String substring(String str, int maxLength, String overflowEnding) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        // full
        if (str.length() <= maxLength) {
            return str;
        }

        // short
        if (maxLength <= overflowEnding.length()) {
            return overflowEnding.substring(0, maxLength);
        } else {
            return (str + overflowEnding).substring(0, maxLength - overflowEnding.length()) + overflowEnding;
        }
    }


    /**
     * Copy image related data from one object to another (set code, card number, image number, file name)
     * Use it in copy/transform effects
     */
    public static void copySetAndCardNumber(MageObject targetObject, MageObject copyFromObject) {
        String needSetCode;
        String needCardNumber;
        String needImageFileName;
        int needImageNumber;
        boolean needUsesVariousArt = false;

        needSetCode = copyFromObject.getExpansionSetCode();
        needCardNumber = copyFromObject.getCardNumber();
        needImageFileName = copyFromObject.getImageFileName();
        needImageNumber = copyFromObject.getImageNumber();
        needUsesVariousArt = copyFromObject.getUsesVariousArt();

        if (targetObject instanceof Permanent) {
            copySetAndCardNumber((Permanent) targetObject, needSetCode, needCardNumber, needImageFileName, needImageNumber, needUsesVariousArt);
        } else if (targetObject instanceof Token) {
            copySetAndCardNumber((Token) targetObject, needSetCode, needCardNumber, needImageFileName, needImageNumber, needUsesVariousArt);
        } else if (targetObject instanceof Card) {
            copySetAndCardNumber((Card) targetObject, needSetCode, needCardNumber, needImageFileName, needImageNumber, needUsesVariousArt);
        } else {
            throw new IllegalStateException("Unsupported target object class: " + targetObject.getClass().getSimpleName());
        }
    }

    private static void copySetAndCardNumber(Permanent targetPermanent, String newSetCode, String newCardNumber, String newImageFileName, Integer newImageNumber, boolean usesVariousArt) {
        if (targetPermanent instanceof PermanentCard
                || targetPermanent instanceof PermanentToken) {
            targetPermanent.setExpansionSetCode(newSetCode);
            targetPermanent.setUsesVariousArt(usesVariousArt);
            targetPermanent.setCardNumber(newCardNumber);
            targetPermanent.setImageFileName(newImageFileName);
            targetPermanent.setImageNumber(newImageNumber);
        } else {
            throw new IllegalArgumentException("Wrong code usage: un-supported target permanent type: " + targetPermanent.getClass().getSimpleName());
        }
    }

    private static void copySetAndCardNumber(Token targetToken, String newSetCode, String newCardNumber, String newImageFileName, Integer newImageNumber, boolean newUsesVariousArt) {
        targetToken.setExpansionSetCode(newSetCode);
        targetToken.setCardNumber(newCardNumber);
        targetToken.setImageFileName(newImageFileName);
        targetToken.setImageNumber(newImageNumber);

        // runtime check
        if (newUsesVariousArt && newCardNumber.isEmpty()) {
            throw new IllegalArgumentException("Wrong code usage: usesVariousArt can be used for token from card only");
        }
        targetToken.setUsesVariousArt(newUsesVariousArt);
    }

    private static void copySetAndCardNumber(Card targetCard, String newSetCode, String newCardNumber, String newImageFileName, Integer newImageNumber, boolean usesVariousArt) {
        targetCard.setExpansionSetCode(newSetCode);
        targetCard.setUsesVariousArt(usesVariousArt);
        targetCard.setCardNumber(newCardNumber);
        targetCard.setImageFileName(newImageFileName);
        targetCard.setImageNumber(newImageNumber);
    }

    /**
     * One single event can be a batch (contain multiple events)
     */
    public static Set<UUID> getEventTargets(GameEvent event) {
        Set<UUID> res = new HashSet<>();
        if (event instanceof BatchEvent) {
            res.addAll(((BatchEvent<?>) event).getTargetIds());
        } else if (event != null && event.getTargetId() != null) {
            res.add(event.getTargetId());
        }
        return res;
    }

    /**
     * Prepare card name for render in card panels, popups, etc. Can show face down status and real card name instead empty string
     *
     * @param imageFileName face down status or another inner image name like Morph, Copy, etc
     */
    public static String getCardNameForGUI(String name, String imageFileName) {
        if (imageFileName.isEmpty()) {
            // normal name
            return name;
        } else {
            // face down or inner name
            return imageFileName + (name.isEmpty() ? "" : ": " + name);
        }
    }

    /**
     * GUI related: show real name and day/night button for face down card
     */
    public static boolean canShowAsControlled(Card card, UUID createdForPlayer) {
        return card.getControllerOrOwnerId().equals(createdForPlayer);
    }

    /**
     * Ability used for information only, e.g. adds additional rule texts
     */
    public static boolean isInformationAbility(Ability ability) {
        return !ability.getEffects().isEmpty()
                && ability.getEffects().stream().allMatch(e -> e instanceof InfoEffect);
    }
}
