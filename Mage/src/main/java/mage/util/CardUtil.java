/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
package mage.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import mage.MageObject;
import mage.Mana;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.SpellAbility;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.mana.*;
import mage.cards.Card;
import mage.filter.FilterMana;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.game.stack.Spell;
import mage.util.functions.CopyTokenFunction;

/**
 * @author nantuko
 */
public final class CardUtil {


    private static final String SOURCE_EXILE_ZONE_TEXT = "SourceExileZone";

    static final String[] numberStrings = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine",
        "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen", "twenty"};

    private static final String[] NON_CHANGELING_SUBTYPES_VALUES = new String[]{
        // basic lands subtypes
        "Mountain", "Forest", "Plains", "Swamp", "Island",
        // Enchantment subtypes
        "Aura", "Cartouche", "Curse", "Shrine",
        // Artifact subtypes
        "Clue", "Equipment", "Fortification", "Contraption", "Vehicle",
        // Land subtypes
        "Desert", "Gate", "Lair", "Locus", "Urza's", "Mine", "Power-Plant", "Tower",
        // Planeswalker subtypes
        "Ajani", "Arlinn", "Ashiok", "Bolas", "Chandra", "Dack", "Daretti", "Domri", "Dovin", "Elspeth", "Freyalise", "Garruk", "Gideon", "Jace",
        "Karn", "Kiora", "Koth", "Liliana", "Nahiri", "Nissa", "Narset", "Nixilis", "Ral", "Saheeli", "Sarkhan", "Sorin", "Tamiyo", "Teferi",
        "Tezzeret", "Tibalt", "Ugin", "Venser", "Vraska", "Xenagos",
        // Instant sorcery subtypes
        "Trap", "Arcane"};
    private static final Set<String> NON_CREATURE_SUBTYPES = new HashSet<>(Arrays.asList(NON_CHANGELING_SUBTYPES_VALUES));

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
        CardUtil.adjustAbilityCost((Ability) spellAbility, reduceCount);
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
        int restToReduce = reduceCount;
        ManaCosts<ManaCost> adjustedCost = new ManaCostsImpl<>();
        boolean updated = false;
        for (ManaCost manaCost : manaCosts) {
            Mana mana = manaCost.getOptions().get(0);
            int colorless = mana != null ? mana.getGeneric() : 0;
            if (restToReduce != 0 && colorless > 0) {
                if ((colorless - restToReduce) > 0) {
                    int newColorless = colorless - restToReduce;
                    adjustedCost.add(new GenericManaCost(newColorless));
                    restToReduce = 0;
                } else {
                    restToReduce -= colorless;
                }
                updated = true;
            } else {
                adjustedCost.add(manaCost);
            }
        }

        // for increasing spell cost effects
        if (!updated && reduceCount < 0) {
            adjustedCost.add(new GenericManaCost(-reduceCount));
        }
        adjustedCost.setSourceFilter(manaCosts.getSourceFilter());
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
     *                          appropriate colored mana is in the costs included
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

    public static String replaceSourceName(String message, String sourceName) {
        message = message.replace("{this}", sourceName);
        message = message.replace("{source}", sourceName);
        return message;
    }

    public static boolean checkNumeric(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) {
                return false;
            }
        }
        return true;
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
            zoneChangeCounter = ((Permanent) mageObject).getZoneChangeCounter(game);
        } else if (mageObject instanceof Card) {
            zoneChangeCounter = ((Card) mageObject).getZoneChangeCounter(game);
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
     * Returns if the ability is used to check which cards are playable on hand.
     * (Issue #457)
     *
     * @param ability - ability to check
     * @return
     */
    public static boolean isCheckPlayableMode(Ability ability) {
        if (ability instanceof ActivatedAbility) {
            return ((ActivatedAbility) ability).isCheckPlayableMode();
        }
        return false;
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



    public static boolean isNonCreatureSubtype(String subtype) {
        return NON_CREATURE_SUBTYPES.contains(subtype);
    }

    public static boolean cardCanBePlayedNow(Card card, UUID playerId, Game game) {
        if (card.isLand()) {
            return game.canPlaySorcery(playerId) && game.getPlayer(playerId).canPlayLand();
        } else {
            return card.getSpellAbility() != null && card.getSpellAbility().spellCanBeActivatedRegularlyNow(playerId, game);
        }
    }

}
