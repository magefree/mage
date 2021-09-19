package mage.util;

import com.google.common.collect.ImmutableList;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.SpellAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.mana.*;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.asthought.CanPlayCardControllerEffect;
import mage.abilities.effects.common.asthought.YouMaySpendManaAsAnyColorToCastTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.HintUtils;
import mage.cards.*;
import mage.constants.*;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.filter.Filter;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.CardState;
import mage.game.Game;
import mage.game.GameState;
import mage.game.command.Commander;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.game.permanent.PermanentMeld;
import mage.game.permanent.token.Token;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.Target;
import mage.target.targetpointer.FixedTarget;
import mage.util.functions.CopyTokenFunction;
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

    private static final String SOURCE_EXILE_ZONE_TEXT = "SourceExileZone";

    static final String[] numberStrings = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine",
            "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen", "twenty"};

    static final String[] ordinalStrings = {"first", "second", "third", "fourth", "fifth", "sixth", "seventh", "eightth", "ninth",
            "tenth", "eleventh", "twelfth", "thirteenth", "fourteenth", "fifteenth", "sixteenth", "seventeenth", "eighteenth", "nineteenth", "twentieth"};

    public static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");

    private static final List<String> costWords = Arrays.asList(
            "put", "return", "exile", "discard", "sacrifice", "remove", "tap", "reveal", "pay"
    );

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
        ability.getManaCostsToPay().clear();
        ability.getManaCostsToPay().addAll(adjustedCost);
    }

    private static ManaCosts<ManaCost> adjustCost(ManaCosts<ManaCost> manaCosts, int reduceCount) {
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
                if (manaCost.getOptions().size() == 0) {
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
                    int colorless = mono.getOptions().get(1).getGeneric();
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
                    changedCost.put(manaCost, new GenericManaCost(gen.getOptions().get(0).getGeneric() + -reduceCount));
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

    public static UUID getExileZoneId(Game game, Ability source) {
        return getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
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
        if (value > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        } else if (value < Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        } else {
            return (int) value;
        }
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
        // TODO: does it work fine with copies of spells on stack?
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

    private static final String vowels = "aeiouAEIOU";

    public static String addArticle(String text) {
        if (text.startsWith("a ")
                || text.startsWith("an ")
                || text.startsWith("another ")
                || text.startsWith("any ")) {
            return text;
        }
        return vowels.contains(text.substring(0, 1)) ? "an " + text : "a " + text;
    }

    public static String italicizeWithEmDash(String text) {
        return "<i>" + text + "</i> &mdash; ";
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
     * Put card to battlefield without resolve (for cheats and tests only)
     *
     * @param source  must be non null (if you need it empty then use fakeSourceAbility)
     * @param game
     * @param newCard
     * @param player
     */
    public static void putCardOntoBattlefieldWithEffects(Ability source, Game game, Card newCard, Player player) {
        // same logic as ZonesHandler->maybeRemoveFromSourceZone

        // workaround to put real permanent from one side (example: you call mdf card by cheats)
        Card permCard = getDefaultCardSideForBattlefield(newCard);

        // prepare card and permanent
        permCard.setZone(Zone.BATTLEFIELD, game);
        permCard.setOwnerId(player.getId());
        PermanentCard permanent;
        if (permCard instanceof MeldCard) {
            permanent = new PermanentMeld(permCard, player.getId(), game);
        } else {
            permanent = new PermanentCard(permCard, player.getId(), game);
        }

        // put onto battlefield with possible counters
        game.getPermanentsEntering().put(permanent.getId(), permanent);
        permCard.checkForCountersToAdd(permanent, source, game);
        permanent.entersBattlefield(source, game, Zone.OUTSIDE, false);
        game.addPermanent(permanent, game.getState().getNextPermanentOrderNumber());
        game.getPermanentsEntering().remove(permanent.getId());

        // workaround for special tapped status from test framework's command (addCard)
        if (permCard instanceof PermanentCard && ((PermanentCard) permCard).isTapped()) {
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
     *
     * @param card
     * @return
     */
    public static Card getDefaultCardSideForBattlefield(Card card) {
        // chose left side all time
        Card permCard;
        if (card instanceof SplitCard) {
            permCard = card;
        } else if (card instanceof AdventureCard) {
            permCard = card;
        } else if (card instanceof ModalDoubleFacesCard) {
            permCard = ((ModalDoubleFacesCard) card).getLeftHalfCard();
        } else {
            permCard = card;
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
        } else if (card instanceof ModalDoubleFacesCard) {
            return ((ModalDoubleFacesCard) card).getLeftHalfCard().getName();
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
     * @param hintsSource abilities list to show as card hints only (you can add additional hints here; exameple: from second or transformed side)
     */
    public static List<String> getCardRulesWithAdditionalInfo(Game game, UUID cardId, String cardName,
                                                              Abilities<Ability> rulesSource, Abilities<Ability> hintsSource) {
        try {
            List<String> rules = rulesSource.getRules(cardName);

            if (game != null) {

                // debug state
                for (String data : game.getState().getCardState(cardId).getInfo().values()) {
                    rules.add(data);
                }

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
     * @param targetPlayer
     * @param givePauseForResponse if you want to give controller time to watch opponent's hand (if you remove control effect in the end of code)
     */
    public static void takeControlUnderPlayerStart(Game game, Player controller, Player targetPlayer, boolean givePauseForResponse) {
        controller.controlPlayersTurn(game, targetPlayer.getId());
        if (givePauseForResponse) {
            while (controller.canRespond()) {
                if (controller.chooseUse(Outcome.Benefit, "You got control of " + targetPlayer.getLogName()
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
     * @param targetPlayer
     */
    public static void takeControlUnderPlayerEnd(Game game, Player controller, Player targetPlayer) {
        targetPlayer.setGameUnderYourControl(true, false);
        if (!targetPlayer.getTurnControlledBy().equals(controller.getId())) {
            controller.getPlayersUnderYourControl().remove(targetPlayer.getId());
        }
    }

    public static void makeCardPlayable(Game game, Ability source, Card card, Duration duration, boolean anyColor) {
        makeCardPlayable(game, source, card, duration, anyColor, null);
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
    public static void makeCardPlayable(Game game, Ability source, Card card, Duration duration, boolean anyColor, Condition condition) {
        // Effect can be used for cards in zones and permanents on battlefield
        // PermanentCard's ZCC is static, but we need updated ZCC from the card (after moved to another zone)
        // So there is a workaround to get actual card's ZCC
        // Example: Hostage Taker
        UUID objectId = card.getMainCard().getId();
        int zcc = game.getState().getZoneChangeCounter(objectId);
        game.addEffect(new CanPlayCardControllerEffect(game, objectId, zcc, duration, condition), source);
        if (anyColor) {
            game.addEffect(new YouMaySpendManaAsAnyColorToCastTargetEffect(duration, condition).setTargetPointer(new FixedTarget(objectId, zcc)), source);
        }
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

        if (player.loseLife(lifeToPay, game, source, false) >= lifeToPay) {
            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.LIFE_PAID, player.getId(), source, player.getId(), lifeToPay));
            return true;
        }

        return false;
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

    public static boolean checkCostWords(String text) {
        return text != null && costWords.stream().anyMatch(text.toLowerCase(Locale.ENGLISH)::startsWith);
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
        } else if (object instanceof ModalDoubleFacesCard || object instanceof ModalDoubleFacesCardHalf) {
            ModalDoubleFacesCard mainCard = (ModalDoubleFacesCard) ((Card) object).getMainCard();
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

    public static String concatWithAnd(List<String> strings) {
        switch (strings.size()) {
            case 0:
                return "";
            case 1:
                return strings.get(0);
            case 2:
                return strings.get(0) + " and " + strings.get(1);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strings.size(); i++) {
            sb.append(strings.get(i));
            if (i == strings.size() - 1) {
                break;
            }
            sb.append(", ");
            if (i == strings.size() - 2) {
                sb.append("and ");
            }
        }
        return sb.toString();
    }

    public static <T> Stream<T> castStream(Stream<?> stream, Class<T> clazz) {
        return stream.filter(clazz::isInstance).map(clazz::cast);
    }

    /**
     * Move card or permanent to dest zone and add counter to it
     *
     * @param game
     * @param source
     * @param controller
     * @param card can be card or permanent
     * @param toZone
     * @param counter
     */
    public static boolean moveCardWithCounter(Game game, Ability source, Player controller, Card card, Zone toZone, Counter counter) {
        if (toZone == Zone.BATTLEFIELD) {
            throw new IllegalArgumentException("Wrong code usage - method doesn't support moving to battlefield zone");
        }

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

}
