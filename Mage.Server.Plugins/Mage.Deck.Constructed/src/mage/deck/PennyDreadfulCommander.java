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

import java.util.*;
import java.util.Map.Entry;
import mage.abilities.common.CanBeYourCommanderAbility;
import mage.abilities.keyword.PartnerAbility;
import mage.cards.Card;
import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.cards.decks.Constructed;
import mage.cards.decks.Deck;
import mage.constants.SetType;
import mage.filter.FilterMana;

/**
 *
 * @author spjspj
 */
public class PennyDreadfulCommander extends Constructed {

    protected List<String> bannedCommander = new ArrayList<>();
    private static final Map<String, Integer> pdAllowed = new HashMap<>();
    private static boolean setupAllowed = false;

    public PennyDreadfulCommander() {
        this("Penny Dreadful Commander");
        for (ExpansionSet set : Sets.getInstance().values()) {
            if (set.getSetType() != SetType.CUSTOM_SET) {
                setCodes.add(set.getCode());
            }
        }
    }

    public PennyDreadfulCommander(String name) {
        super(name);
    }

    @Override
    public boolean validate(Deck deck) {
        boolean valid = true;
        FilterMana colorIdentity = new FilterMana();

        if (deck.getCards().size() + deck.getSideboard().size() != 100) {
            invalid.put("Deck", "Must contain 100 cards: has " + (deck.getCards().size() + deck.getSideboard().size()) + " cards");
            valid = false;
        }

        List<String> basicLandNames = new ArrayList<>(Arrays.asList("Forest", "Island", "Mountain", "Swamp", "Plains", "Wastes"));
        Map<String, Integer> counts = new HashMap<>();
        countCards(counts, deck.getCards());
        countCards(counts, deck.getSideboard());

        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            if (entry.getValue() > 1) {
                if (!basicLandNames.contains(entry.getKey())) {
                    invalid.put(entry.getKey(), "Too many: " + entry.getValue());
                    valid = false;
                }
            }
        }

        generatePennyDreadfulHash();
        for (String wantedCard : counts.keySet()) {
            if (!(pdAllowed.containsKey(wantedCard))) {
                invalid.put(wantedCard, "Banned");
                valid = false;
            }
        }

        if (deck.getSideboard().size() < 1 || deck.getSideboard().size() > 2) {
            invalid.put("Commander", "Sideboard must contain only the commander(s)");
            valid = false;
        } else {
            for (Card commander : deck.getSideboard()) {
                if ((!commander.isCreature() || !commander.isLegendary())
                        && (!commander.isPlaneswalker() || !commander.getAbilities().contains(CanBeYourCommanderAbility.getInstance()))) {
                    invalid.put("Commander", "Commander invalid (" + commander.getName() + ')');
                    valid = false;
                }
                if (deck.getSideboard().size() == 2 && !commander.getAbilities().contains(PartnerAbility.getInstance())) {
                    invalid.put("Commander", "Commander without Partner (" + commander.getName() + ')');
                    valid = false;
                }
                FilterMana commanderColor = commander.getColorIdentity();
                if (commanderColor.isWhite()) {
                    colorIdentity.setWhite(true);
                }
                if (commanderColor.isBlue()) {
                    colorIdentity.setBlue(true);
                }
                if (commanderColor.isBlack()) {
                    colorIdentity.setBlack(true);
                }
                if (commanderColor.isRed()) {
                    colorIdentity.setRed(true);
                }
                if (commanderColor.isGreen()) {
                    colorIdentity.setGreen(true);
                }
            }
        }
        for (Card card : deck.getCards()) {
            if (!cardHasValidColor(colorIdentity, card)) {
                invalid.put(card.getName(), "Invalid color (" + colorIdentity.toString() + ')');
                valid = false;
            }
        }
        for (Card card : deck.getCards()) {
            if (!isSetAllowed(card.getExpansionSetCode())) {
                if (!legalSets(card)) {
                    invalid.put(card.getName(), "Not allowed Set: " + card.getExpansionSetCode());
                    valid = false;
                }
            }
        }
        for (Card card : deck.getSideboard()) {
            if (!isSetAllowed(card.getExpansionSetCode())) {
                if (!legalSets(card)) {
                    invalid.put(card.getName(), "Not allowed Set: " + card.getExpansionSetCode());
                    valid = false;
                }
            }
        }
        return valid;
    }

    public boolean cardHasValidColor(FilterMana commander, Card card) {
        FilterMana cardColor = card.getColorIdentity();
        return !(cardColor.isBlack() && !commander.isBlack()
                || cardColor.isBlue() && !commander.isBlue()
                || cardColor.isGreen() && !commander.isGreen()
                || cardColor.isRed() && !commander.isRed()
                || cardColor.isWhite() && !commander.isWhite());
    }

    public void generatePennyDreadfulHash() {
        if (setupAllowed == false) {
            setupAllowed = true;
        } else {
            return;
        }

        Properties properties = new Properties();
        try {
            properties.load(PennyDreadfulCommander.class.getResourceAsStream("pennydreadful.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (final Entry<Object, Object> entry : properties.entrySet()) {
            pdAllowed.put((String) entry.getKey(), 1);
        }
    }
}
