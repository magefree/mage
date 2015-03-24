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
import mage.util.CardUtil;

/**
 *
 * @author Plopman
 */
public class Commander extends DeckValidator {

    protected  List<String> banned = new ArrayList<>();
    protected  List<String> bannedCommander = new ArrayList<>();

    public Commander() {
        this("Commander");
        banned.add("Ancestral Recall");
        banned.add("Balance");
        banned.add("Biorhythm");
        banned.add("Black Lotus");
        banned.add("Braids, Cabal Minion");
        banned.add("Channel");
        banned.add("Coalition Victory");
        banned.add("Emrakul, the Aeons Torn");
        banned.add("Erayo, Soratami Ascendant");
        banned.add("Fastbond");
        banned.add("Gifts Ungiven");
        banned.add("Griselbrand");
        banned.add("Karakas");
        banned.add("Library of Alexandria");
        banned.add("Limited Resources");
        banned.add("Mox Emerald");
        banned.add("Mox Jet");
        banned.add("Mox Pearl");
        banned.add("Mox Ruby");
        banned.add("Mox Sapphire");
        banned.add("Painter's Servant");
        banned.add("Panoptic Mirror");
        banned.add("Primeval Titan");
        banned.add("Protean Hulk");
        banned.add("Recurring Nightmare");
        banned.add("Rofellos, Llanowar Emissary");
        banned.add("Sundering Titan");
        banned.add("Sway of the Stars");
        banned.add("Sylvan Primordial");
        banned.add("Time Vault");
        banned.add("Time Walk");
        banned.add("Tinker");
        banned.add("Tolarian Academy");
        banned.add("Trade Secrets");
        banned.add("Upheaval");
        banned.add("Worldfire");
        banned.add("Yawgmoth's Bargain");
    }

    public Commander(String name) {
        super(name);
    }

    @Override
    public boolean validate(Deck deck) {
        boolean valid = true;

        if (deck.getCards().size() != 99) {
            invalid.put("Deck", "Must contain 99 cards: has " + deck.getCards().size() + " cards");
            valid = false;
        }

        List<String> basicLandNames = new ArrayList<>(Arrays.asList("Forest", "Island", "Mountain", "Swamp", "Plains",
                "Snow-Covered Forest", "Snow-Covered Island", "Snow-Covered Mountain", "Snow-Covered Swamp", "Snow-Covered Plains"));
        Map<String, Integer> counts = new HashMap<>();
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

        if (deck.getSideboard().size() == 1) {
            Card commander = (Card) deck.getSideboard().toArray()[0];
            if (commander == null) {
                invalid.put("Commander", "Commander invalid ");
                return false;
            }
            if ((commander.getCardType().contains(CardType.CREATURE) && commander.getSupertype().contains("Legendary")) ||
                    (commander.getCardType().contains(CardType.PLANESWALKER) && commander.getAbilities().contains(CanBeYourCommanderAbility.getInstance()))) {
                if (!bannedCommander.contains(commander.getName())) {
                    FilterMana color = CardUtil.getColorIdentity(commander);
                    for (Card card : deck.getCards()) {
                        if (!cardHasValidColor(color, card)) {
                            invalid.put(card.getName(), "Invalid color (" + commander.getName() +")");
                            valid = false;
                        }
                    }
                } else {
                    invalid.put("Commander", "Commander banned (" + commander.getName() +")");
                    valid = false;
                }
            } else {
                invalid.put("Commander", "Commander invalid (" + commander.getName() +")");
                valid = false;
            }
        } else {
            invalid.put("Commander", "Sideboard must contain only the commander");
        }

        return valid;
    }

    public boolean cardHasValidColor(FilterMana commander, Card card) {
        FilterMana cardColor = CardUtil.getColorIdentity(card);
        if (cardColor.isBlack() && !commander.isBlack()
                || cardColor.isBlue() && !commander.isBlue()
                || cardColor.isGreen() && !commander.isGreen()
                || cardColor.isRed() && !commander.isRed()
                || cardColor.isWhite() && !commander.isWhite()) {
            return false;
        }
        return true;
    }

}
