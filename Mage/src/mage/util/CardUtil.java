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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.SpellAbility;
import mage.abilities.costs.AlternativeCost;
import mage.abilities.costs.AlternativeCostImpl;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.HybridManaCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.keyword.ChangelingAbility;
import mage.cards.Card;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.filter.FilterMana;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.game.stack.Spell;
import mage.util.functions.CopyTokenFunction;


/**
 * @author nantuko
 */
public class CardUtil {

    private static final String regexBlack = ".*\\x7b.{0,2}B.{0,2}\\x7d.*";
    private static final String regexBlue = ".*\\x7b.{0,2}U.{0,2}\\x7d.*";
    private static final String regexRed = ".*\\x7b.{0,2}R.{0,2}\\x7d.*";
    private static final String regexGreen = ".*\\x7b.{0,2}G.{0,2}\\x7d.*";
    private static final String regexWhite = ".*\\x7b.{0,2}W.{0,2}\\x7d.*";

    private static final String SOURCE_EXILE_ZONE_TEXT = "SourceExileZone";
    
    static String numberStrings[] = { "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine",
                                      "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "ninteen", "twenty"};

    /**
     * Checks whether two cards share card types.
     *
     * @param card1
     * @param card2
     * @return
     */
    public static boolean shareTypes(Card card1, Card card2) {

        if (card1 == null || card2 == null) {
            throw new IllegalArgumentException("Params can't be null");
        }

        for (CardType type : card1.getCardType()) {
            if (card2.getCardType().contains(type)) {
                return true;
            }
        }

        return false;
    }
    /**
     * Checks whether two cards share card subtypes.
     *
     * @param card1
     * @param card2
     * @return
     */
    public static boolean shareSubtypes(Card card1, Card card2) {

        if (card1 == null || card2 == null) {
            throw new IllegalArgumentException("Params can't be null");
        }

        if (card1.getCardType().contains(CardType.CREATURE) && card2.getCardType().contains(CardType.CREATURE)) {
            if (card1.getAbilities().contains(ChangelingAbility.getInstance()) ||
                    card1.getSubtype().contains(ChangelingAbility.ALL_CREATURE_TYPE) ||
                    card2.getAbilities().contains(ChangelingAbility.getInstance()) ||
                    card2.getSubtype().contains(ChangelingAbility.ALL_CREATURE_TYPE)) {
                return true;
            }
        }
        for (String subtype : card1.getSubtype()) {
            if (card2.getSubtype().contains(subtype)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Increase spell or ability cost to be paid.
     *
     * @param ability
     * @param increaseCount
     */
    public static void increaseCost(Ability ability, int increaseCount) {
        adjustCost(ability, -increaseCount);
        adjustAlternativeCosts(ability, -increaseCount);
    }

    /**
     * Reduces ability cost to be paid.
     *
     * @param ability
     * @param reduceCount
     */
    public static void reduceCost(Ability ability, int reduceCount) {
        adjustCost(ability, reduceCount);
        adjustAlternativeCosts(ability, reduceCount);
    }

    /**
     * Adjusts spell or ability cost to be paid.
     *
     * @param spellAbility
     * @param reduceCount
     */
    public static void adjustCost(SpellAbility spellAbility, int reduceCount) {
        CardUtil.adjustCost((Ability) spellAbility, reduceCount);
        adjustAlternativeCosts(spellAbility, reduceCount);
    }
    
    public static ManaCosts<ManaCost> increaseCost(ManaCosts<ManaCost> manaCosts, int increaseCount) {
        return adjustCost(manaCosts, -increaseCount);
    }

    public static ManaCosts<ManaCost> reduceCost(ManaCosts<ManaCost> manaCosts, int reduceCount) {
        return adjustCost(manaCosts, reduceCount);
    }

    private static void adjustAlternativeCosts(Ability ability, int reduceCount) {
        for (AlternativeCost alternativeCost : ability.getAlternativeCosts()) {
            if (alternativeCost instanceof AlternativeCostImpl) {
                AlternativeCostImpl impl = (AlternativeCostImpl) alternativeCost;
                ManaCosts<ManaCost> adjustedCost = new ManaCostsImpl<>();
                boolean updated = false;
                Iterator it = impl.iterator();
                while (it.hasNext()) {
                    Object cost = it.next();
                    if (cost instanceof ManaCosts) {
                        for (Object object : ((ManaCosts) cost)) {
                            if (object instanceof ManaCost) {
                                ManaCost manaCost = (ManaCost) object;
                                Mana mana = manaCost.getOptions().get(0);
                                int colorless = mana != null ? mana.getColorless() : 0;
                                if (!updated && colorless > 0) {
                                    if ((colorless - reduceCount) > 0) {
                                        int newColorless = colorless - reduceCount;
                                        it.remove();
                                        adjustedCost.add(new GenericManaCost(newColorless));
                                    }
                                    updated = true;
                                } else {
                                    adjustedCost.add(manaCost);
                                }
                            }
                        }
                    }
                }
                impl.add(adjustedCost);
            }
        }
    }



    /**
     * Adjusts ability cost to be paid.
     *
     * @param ability
     * @param reduceCount
     */
    private static void adjustCost(Ability ability, int reduceCount) {
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
            int colorless = mana != null ? mana.getColorless() : 0;
            if (colorless > 0) {
                if ((colorless - restToReduce) > 0) {
                    int newColorless = colorless - restToReduce;
                    adjustedCost.add(new GenericManaCost(newColorless));
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

    public static ManaCosts<ManaCost> removeVariableManaCost(ManaCosts<ManaCost> manaCosts) {
        ManaCosts<ManaCost> adjustedCost = new ManaCostsImpl<>();
        for (ManaCost manaCost: manaCosts) {
            if (!(manaCost instanceof VariableManaCost)) {
                adjustedCost.add(manaCost);
            }
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
     * @param convertToGeneric colored mana does reduce generic mana if no appropriate colored mana is in the costs included
     */
    public static void adjustCost(SpellAbility spellAbility, ManaCosts<ManaCost> manaCostsToReduce, boolean convertToGeneric) {
        ManaCosts<ManaCost> previousCost = spellAbility.getManaCostsToPay();
        ManaCosts<ManaCost> adjustedCost = new ManaCostsImpl<>();        
        // save X value (e.g. convoke ability)
        for (VariableCost vCost: previousCost.getVariableCosts()) {
            if (vCost instanceof VariableManaCost) {
                adjustedCost.add((VariableManaCost) vCost);
            }
        }


        Mana reduceMana = new Mana();
        for (ManaCost manaCost : manaCostsToReduce) {
            reduceMana.add(manaCost.getMana());
        }
        // subtract colored mana
        for (ManaCost newManaCost : previousCost) {
            Mana mana = newManaCost.getMana();
            if (mana.getColorless() > 0) {
                continue;
            }
            if (mana.getBlack() > 0 && reduceMana.getBlack() > 0) {
                if (reduceMana.getBlack() > mana.getBlack()) {
                    reduceMana.setBlack(reduceMana.getBlack()-mana.getBlack());
                    mana.setBlack(0);
                } else {
                    mana.setBlack(mana.getBlack()-reduceMana.getBlack());
                    reduceMana.setBlack(0);
                }
            }
            if (mana.getRed() > 0 && reduceMana.getRed() > 0) {
                if (reduceMana.getRed() > mana.getRed()) {
                    reduceMana.setRed(reduceMana.getRed()-mana.getRed());
                    mana.setRed(0);
                } else {
                    mana.setRed(mana.getRed()-reduceMana.getRed());
                    reduceMana.setRed(0);
                }
            }
            if (mana.getBlue() > 0 && reduceMana.getBlue() > 0) {
                if (reduceMana.getBlue() > mana.getBlue()) {
                    reduceMana.setBlue(reduceMana.getBlue()-mana.getBlue());
                    mana.setBlue(0);
                } else {
                    mana.setBlue(mana.getBlue()-reduceMana.getBlue());
                    reduceMana.setBlue(0);
                }
            }
            if (mana.getGreen() > 0 && reduceMana.getGreen() > 0) {
                if (reduceMana.getGreen() > mana.getGreen()) {
                    reduceMana.setGreen(reduceMana.getGreen()-mana.getGreen());
                    mana.setGreen(0);
                } else {
                    mana.setGreen(mana.getGreen()-reduceMana.getGreen());
                    reduceMana.setGreen(0);
                }
            }
            if (mana.getWhite() > 0 && reduceMana.getWhite() > 0) {
                if (reduceMana.getWhite() > mana.getWhite()) {
                    reduceMana.setWhite(reduceMana.getWhite()-mana.getWhite());
                    mana.setWhite(0);
                } else {
                    mana.setWhite(mana.getWhite()-reduceMana.getWhite());
                    reduceMana.setWhite(0);
                }
            }
            if (newManaCost instanceof HybridManaCost) {
                if (mana.count() > 1) {
                    adjustedCost.add(newManaCost);
                }
            } else if (mana.count() > 0) {
                adjustedCost.add(newManaCost);
            }
        }
        // subtract colorless mana, use all mana that is left
        int reduceAmount;
        if (convertToGeneric) {
            reduceAmount = reduceMana.count();
        } else {
            reduceAmount = reduceMana.getColorless();
        }
        for (ManaCost newManaCost : previousCost) {
            Mana mana = newManaCost.getMana();
            if (mana.getColorless() == 0) {
                continue;
            }
            if (mana.getColorless() > 0 && reduceAmount > 0) {
                if (reduceAmount > mana.getColorless()) {
                    reduceAmount -= mana.getColorless();
                    mana.setColorless(0);
                } else {
                    mana.setColorless(mana.getColorless() - reduceAmount);
                    reduceAmount = 0;
                }
            }
            if (mana.count() > 0) {
                adjustedCost.add(0, new GenericManaCost(mana.count()));
            }
        }
        adjustedCost.setSourceFilter(previousCost.getSourceFilter());  // keep mana source restrictions
        spellAbility.getManaCostsToPay().clear();
        spellAbility.getManaCostsToPay().addAll(adjustedCost);
    }
    

    /**
     * Returns function that copies params\abilities from one card to {@link Token}.
     *
     * @param target
     * @return
     */
    public static CopyTokenFunction copyTo(Token target) {
        return new CopyTokenFunction(target);
    }

    public static boolean isPermanentCard ( Card card )  {
        boolean permanent = false;

        permanent |= card.getCardType().contains(CardType.ARTIFACT);
        permanent |= card.getCardType().contains(CardType.CREATURE);
        permanent |= card.getCardType().contains(CardType.ENCHANTMENT);
        permanent |= card.getCardType().contains(CardType.LAND);
        permanent |= card.getCardType().contains(CardType.PLANESWALKER);

        return permanent;
    }

    /**
     * Converts an integer number to string
     * Numbers > 20 will be returned as digits
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
     * @param forOne if the number is 1, this string will be returnedinstead of "one".
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
        for(int i = 0; i < s.length(); i++) {
            if(!Character.isDigit(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Creates and saves a (card + zoneChangeCounter) specific exileId.
     *
     * @param game the current game
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
    
    public static UUID getObjectExileZoneId(Game game, MageObject mageObject) {
        return getObjectExileZoneId(game, mageObject, false);
    }

    public static UUID getObjectExileZoneId(Game game, MageObject mageObject, boolean previous) {
        int zoneChangeCounter = 0;
        if (mageObject instanceof Permanent) {
            zoneChangeCounter = ((Permanent) mageObject).getZoneChangeCounter();
        } else if (mageObject instanceof Card) {
            zoneChangeCounter = ((Card) mageObject).getZoneChangeCounter();
        }
        if (zoneChangeCounter > 0 && previous) {
            zoneChangeCounter--;
        }
        return getExileZoneId(getObjectZoneString(SOURCE_EXILE_ZONE_TEXT,mageObject.getId(), game, zoneChangeCounter, false), game);
    }
    
    public static UUID getExileZoneId(Game game, UUID objectId, int zoneChangeCounter) {
        return getExileZoneId(getObjectZoneString(SOURCE_EXILE_ZONE_TEXT,objectId, game, zoneChangeCounter, false), game);
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
     * Creates a string from text + cardId and the zoneChangeCounter of the card (from cardId).
     * This string can be used to save and get values that must be specific to a permanent instance.
     * So they won't match, if a permanent was e.g. exiled and came back immediately.
     *
     * @param text short value to describe the value
     * @param cardId id of the card
     * @param game the game
     * @return
     */
    public static String getCardZoneString(String text, UUID cardId, Game game) {
        return getCardZoneString(text, cardId, game, false);
    }

    public static String getCardZoneString(String text, UUID cardId, Game game, boolean previous) {        
        int zoneChangeCounter= 0;
        Card card = game.getCard(cardId); // if called for a token, the id is enough
        if (card != null) {
            zoneChangeCounter = card.getZoneChangeCounter();
        }
        return getObjectZoneString(text,cardId, game, zoneChangeCounter, previous);
    }
    
    public static String getObjectZoneString(String text, MageObject mageObject, Game game) {
        int zoneChangeCounter = 0;
        if (mageObject instanceof Permanent) {
            zoneChangeCounter = ((Permanent) mageObject).getZoneChangeCounter();
        } else if (mageObject instanceof Card) {
            zoneChangeCounter = ((Card) mageObject).getZoneChangeCounter();
        }
        return getObjectZoneString(text, mageObject.getId(), game, zoneChangeCounter, false);
    }
    
    public static String getObjectZoneString(String text, UUID objectId, Game game, int zoneChangeCounter, boolean previous) {
        StringBuilder uniqueString = new StringBuilder();
        if (text != null) {
            uniqueString.append(text);
        }
        uniqueString.append(objectId);
        uniqueString.append(previous ? zoneChangeCounter - 1: zoneChangeCounter);
        return uniqueString.toString();        
    }
    
    /**
     * Returns if the ability is used to check which cards
     * are playable on hand. (Issue #457)
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
     * Adds tags to mark the additional info of a card
     * (e.g. blue font color)
     * @param text text body
     * @return
     */
    public static String addToolTipMarkTags(String text) {
        return "<font color = 'blue'>" + text + "</font>";
    }

    public static boolean convertedManaCostsIsEqual(MageObject object1, MageObject object2) {
        Set<Integer> cmcObject1 = getCMC(object1);
        Set<Integer> cmcObject2 = getCMC(object2);
        for (Integer integer :cmcObject1) {
            if (cmcObject2.contains(integer)) {
                return true;
            }
        }
        return false;
    }

    public static Set<Integer> getCMC(MageObject object) {
        Set<Integer> cmcObject = new HashSet<>();
        if (object instanceof Spell) {
            cmcObject.add(((Spell)object).getConvertedManaCost());
        } else if (object instanceof Card) {
            Card card = (Card) object;
            if (card instanceof SplitCard) {
                SplitCard splitCard = (SplitCard) card;
                cmcObject.add(splitCard.getLeftHalfCard().getManaCost().convertedManaCost());
                cmcObject.add(splitCard.getRightHalfCard().getManaCost().convertedManaCost());
            } else {
                cmcObject.add(card.getManaCost().convertedManaCost());
            }
        } 
        return cmcObject;
    }
    
    /**
     * Gets the colors that are in the casting cost but also in the rules text 
     * as far as not included in reminder text.
     * 
     * @param card
     * @return 
     */
    public static FilterMana getColorIdentity(Card card) {
        FilterMana mana = new FilterMana();
        mana.setBlack(card.getManaCost().getText().matches(regexBlack));
        mana.setBlue(card.getManaCost().getText().matches(regexBlue));
        mana.setGreen(card.getManaCost().getText().matches(regexGreen));
        mana.setRed(card.getManaCost().getText().matches(regexRed));
        mana.setWhite(card.getManaCost().getText().matches(regexWhite));

        for (String rule : card.getRules()) {
            rule = rule.replaceAll("(?i)<i.*?</i>", ""); // Ignoring reminder text in italic
            if (rule.matches(regexBlack)) {
                mana.setBlack(true);
            }
            if (rule.matches(regexBlue)) {
                mana.setBlue(true);
            }
            if (rule.matches(regexGreen)) {
                mana.setGreen(true);
            }
            if (rule.matches(regexRed)) {
                mana.setRed(true);
            }
            if (rule.matches(regexWhite)) {
                mana.setWhite(true);
            }
        }
        return mana;
    }

}
