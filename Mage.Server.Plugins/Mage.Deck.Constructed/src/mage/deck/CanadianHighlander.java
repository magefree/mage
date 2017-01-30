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

import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.cards.decks.Constructed;
import mage.cards.decks.Deck;
import mage.constants.SetType;

/**
 *
 * @author spjspj
 */
public class CanadianHighlander extends Constructed {

    public CanadianHighlander() {
        this("Canadian Highlander");
        for (ExpansionSet set : Sets.getInstance().values()) {
            if (set.getSetType() != SetType.CUSTOM_SET) {
                setCodes.add(set.getCode());
            }
        }
    }

    public CanadianHighlander(String name) {
        super(name);
    }

    @Override
    public boolean validate(Deck deck) {
        boolean valid = true;

        if (deck.getCards().size() < 100) {
            invalid.put("Deck", "Must contain 100 or more singleton cards: has " + (deck.getCards().size()) + " cards");
            valid = false;
        }
        
        if (deck.getSideboard().size() > 0) {
            invalid.put("Deck", "Sideboard can't contain any cards: has " + (deck.getSideboard().size()) + " cards");
            valid = false;
        }

        List<String> basicLandNames = new ArrayList<>(Arrays.asList("Forest", "Island", "Mountain", "Swamp", "Plains", "Wastes",
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

        int allowedPoints = 10 * (int) Math.floor(deck.getCards().size()/100.0);
        int totalPoints = 0;
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            String cn = entry.getKey();
            if(cn.equals("Balance")
                    || cn.equals("Dig Through Time")
                    || cn.equals("Fastbond")
                    || cn.equals("Gifts Ungiven")
                    || cn.equals("Intuition")
                    || cn.equals("Library of Alexandria")
                    || cn.equals("Lim-Dul's Vault")
                    || cn.equals("Mana Vault")
                    || cn.equals("Mind Twist")
                    || cn.equals("Oath of Druids")
                    || cn.equals("Personal Tutor")
                    || cn.equals("Stoneforge Mystic")
                    || cn.equals("Tainted Pact")
                    || cn.equals("Tolarian Academy")
                    || cn.equals("Transmute Artifact")
                    || cn.equals("Treasure Cruise")
                    || cn.equals("True-Name Nemesis")) {
                totalPoints += 1;
            }
            if(cn.equals("Doomsday")
                    || cn.equals("Enlightened Tutor")
                    || cn.equals("Imperial Seal")
                    || cn.equals("Mana Crypt")
                    || cn.equals("Mystical Tutor")
                    || cn.equals("Strip Mine")
                    || cn.equals("Summoner's Pact")
                    || cn.equals("Survival of the Fittest")
                    || cn.equals("Umezawa's Jitte")) {
                totalPoints += 2;
            }
            if(cn.equals("Birthing Pod")
                    || cn.equals("Mox Emerald")
                    || cn.equals("Mox Jet")
                    || cn.equals("Mox Pearl")
                    || cn.equals("Mox Ruby")
                    || cn.equals("Mox Sapphire")
                    || cn.equals("Protean Hulk")
                    || cn.equals("Vampiric Tutor")) {
                totalPoints += 3;
            }
            if(cn.equals("Demonic Tutor")
                    || cn.equals("Hermit Druid")
                    || cn.equals("Sol Ring")) {
                totalPoints += 4;
            }
            if(cn.equals("Ancestral Recall")
                    || cn.equals("Natural Order")
                    || cn.equals("Time Walk")
                    || cn.equals("Tinker")) {
                totalPoints += 5;
            }
            if(cn.equals("Flash")) {
                totalPoints += 6;
            }
            if(cn.equals("Black Lotus")
                    || cn.equals("Time Vault")) {
                totalPoints += 7;
            }
        }
        if(totalPoints > allowedPoints) {
            invalid.put("Total points too high", "Your calculated point total was " + totalPoints);
            valid = false;
        }
        return valid;
    }
}
