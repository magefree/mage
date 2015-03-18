/*
 * Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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
package mage.deck;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mage.abilities.common.CanBeYourCommanderAbility;
import mage.cards.Card;
import mage.cards.decks.Deck;
import mage.cards.decks.DeckValidator;
import mage.constants.CardType;
import mage.filter.FilterMana;
import mage.game.GameTinyLeadersImpl;
import mage.util.CardUtil;

/**
 *
 * @author JRHerlehy
 */
public class TinyLeaders extends DeckValidator {

    protected List<String> banned = new ArrayList<>();
    protected List<String> bannedCommander = new ArrayList<>();

    public TinyLeaders() {
        this("Tiny Leaders");
        //Banned list from tinyleaders.blodspot.ca/p/ban-list.html
        //Ban list updated as of 11/08/14
        banned.add("Ancestral Recall");
        banned.add("Balance");
        banned.add("Black Lotus");
        banned.add("Channel");
        banned.add("Counterbalance");
        banned.add("Demonic Tutor");
        banned.add("Earthcraft");
        banned.add("Edric, Spymaster of Trest");
        banned.add("Fastbond");
        banned.add("Goblin Recruiter");
        banned.add("Hermit Druid");
        banned.add("Imperial Seal");
        banned.add("Library of Alexandria");
        banned.add("Karakas");
        banned.add("Mana Crypt");
        banned.add("Mana Drain");
        banned.add("Mana Vault");
        banned.add("metalworker");
        banned.add("Mind Twist");
        banned.add("Mishra's Workshop");
        banned.add("Mox Emerald");
        banned.add("Mox Jet");
        banned.add("Mox Pearl");
        banned.add("Mox Ruby");
        banned.add("Mox Sapphire");
        banned.add("Necropotence");
        banned.add("Painter's Servant");
        banned.add("Shahrazad");
        banned.add("Skullclamp");
        banned.add("Sol Ring");
        banned.add("Strip Mine");
        banned.add("Survival of the Fittest");
        banned.add("Sword of Body and Mind");
        banned.add("Time Vault");
        banned.add("Time Walk");
        banned.add("Timetwister");
        banned.add("Tolarian Academy");
        banned.add("Umezawa's Jitte");
        banned.add("Vampiric Tutor");
        banned.add("Yawgmoth's Will");

        //Additionally, these Legendary creatures cannot be used as Commanders
        bannedCommander.add("Erayo, Soratami Ascendant");
        bannedCommander.add("Rofellos, Llanowar Emissary");
        bannedCommander.add("Derevi, Empyrical Tactician");
    }

    public TinyLeaders(String name) {
        super(name);
    }

    /**
     *
     * @param deck
     * @return - True if deck is valid
     */
    @Override
    public boolean validate(Deck deck) {
        boolean valid = true;

        if (deck.getCards().size() != 49) {
            invalid.put("Deck", "Must contain 49 cards: has " + deck.getCards().size() + " cards");
            valid = false;
        }

        List<String> basicLandNames = new ArrayList<>(Arrays.asList("Forest", "Island", "Mountain", "Swamp", "Plains",
                "Snow-Covered Forest", "Snow-Covered Island", "Snow-Covered Mountain", "Snow-Covered Swamp", "Snow-Covered Plains"));
        Map<String, Integer> counts = new HashMap<>();
        counts.put(deck.getName(), 1); // add the commander to the counts, so it can't be in the deck or sideboard again
        countCards(counts, deck.getCards());
        countCards(counts, deck.getSideboard());
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            if (entry.getValue() > 1) {
                if (!basicLandNames.contains(entry.getKey()) && !entry.getKey().equals("Relentless Rats") && !entry.getKey().equals("Shadowborn Apostle")) {
                    invalid.put(entry.getKey(), "Too many: " + entry.getValue());
                    valid = false;
                }
            }
        }

        for (String bannedCard : banned) {
            if (counts.containsKey(bannedCard)) {
                invalid.put(bannedCard, "Banned");
                valid = false;
            }
        }

        if (deck.getSideboard().size() <= 10) {
            Card commander = GameTinyLeadersImpl.getCommanderCard(deck.getName(), null);
            /**
             * 905.5b - Each card must have a converted mana cost of three of less.
             *          Cards with {X} in their mana cost count X as zero.
             *          Split and double-face cards are legal only if both of their halves would be legal independently.
             */
            
            if (commander == null  || commander.getManaCost().convertedManaCost() > 3) {
                if (commander == null) {
                    invalid.put("Leader", "Please be sure to set your leader name in the NAME field in the DECK EDITOR (use the Sultai for a UBG (2/2) default Commander)");
                }
                if (commander != null && commander.getManaCost().convertedManaCost() > 3) {
                    invalid.put("Leader", "Commanders converted mana cost is greater than 3");
                }
                return false;
            }
            if ((commander.getCardType().contains(CardType.CREATURE) && commander.getSupertype().contains("Legendary"))
                    || (commander.getCardType().contains(CardType.PLANESWALKER) && commander.getAbilities().contains(CanBeYourCommanderAbility.getInstance()))) {
                if (!bannedCommander.contains(commander.getName())) {
                    FilterMana color = CardUtil.getColorIdentity(commander);
                    for (Card card : deck.getCards()) {
                        if (!cardHasValideColor(color, card)) {
                            invalid.put(card.getName(), "Invalid color (" + commander.getName() + ")");
                            valid = false;
                        }
                        
                        //905.5b - Converted mana cost must be 3 or less
                        if (card.getManaCost().convertedManaCost() > 3) {
                            invalid.put(card.getName(), "Invalid cost (" + card.getManaCost().convertedManaCost() + ")");
                            valid = false;
                        }
                    }
                } else {
                    invalid.put("Commander", "Commander banned (" + commander.getName() + ")");
                    valid = false;
                }
            } else {
                invalid.put("Commander", "Commander invalide (" + commander.getName() + ")");
                valid = false;
            }
        } else {
            invalid.put("Commander", "Sideboard must contain only a maximum of 10 sideboard cards (the Tiny Leader name must be written to the deck name)");
            valid = false;
        }

        return valid;
    }

    /**
     *
     * @param commander FilterMana object with Color Identity of Commander set
     * @param card Card to validate
     * @return True if card has a valid color identity
     */
    public boolean cardHasValideColor(FilterMana commander, Card card) {
        FilterMana cardColor = CardUtil.getColorIdentity(card);
        return !(cardColor.isBlack() && !commander.isBlack()
                || cardColor.isBlue() && !commander.isBlue()
                || cardColor.isGreen() && !commander.isGreen()
                || cardColor.isRed() && !commander.isRed()
                || cardColor.isWhite() && !commander.isWhite());
    }

}
