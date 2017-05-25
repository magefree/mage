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
public class AusHighlander extends Constructed {

    public AusHighlander() {
        this("Australian Highlander");
        for (ExpansionSet set : Sets.getInstance().values()) {
            if (set.getSetType() != SetType.CUSTOM_SET) {
                setCodes.add(set.getCode());
            }
        }
    }

    public AusHighlander(String name) {
        super(name);
    }

    @Override
    public boolean validate(Deck deck) {
        boolean valid = true;

        if (deck.getCards().size() != 60) {
            invalid.put("Deck", "Must contain 60 singleton cards: has " + (deck.getCards().size()) + " cards");
            valid = false;
        }
        if (deck.getSideboard().size() > 15) {
            invalid.put("Sideboard", "Must contain at most 15 singleton cards: has " + (deck.getSideboard().size()) + " cards");
            valid = false;
        }

        banned.add("Advantageous Proclamation");
        banned.add("Amulet of Quoz");
        banned.add("Backup Plan");
        banned.add("Brago's Favor");
        banned.add("Bronze Tablet");
        banned.add("Chaos Orb");
        banned.add("Contract from Below");
        banned.add("Darkpact");
        banned.add("Demonic Attorney");
        banned.add("Double Stroke");
        banned.add("Falling Star");
        banned.add("Immediate Action");
        banned.add("Iterative Analysis");
        banned.add("Jeweled Bird");
        banned.add("Muzzio's Preparations");
        banned.add("Power Play");
        banned.add("Rebirth");
        banned.add("Secret Summoning");
        banned.add("Secrets of Paradise");
        banned.add("Sentinel Dispatch");
        banned.add("Shahrazad");
        banned.add("Tempest Efreet");
        banned.add("Timmerian Fiends");
        banned.add("Unexpected Potential");
        banned.add("Worldknit");

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

        int totalPoints = 0;
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            String cn = entry.getKey();

            if (cn.equals("Back to Basics")
                    || cn.equals("Balance")
                    || cn.equals("Birthing Pod")
                    || cn.equals("Crop Rotation")
                    || cn.equals("Dark Petition")
                    || cn.equals("Enlightened Tutor")
                    || cn.equals("Fastbond")
                    || cn.equals("Force of Will")
                    || cn.equals("Gifts Ungiven")
                    || cn.equals("Green Sun's Zenith")
                    || cn.equals("Hermit Druid")
                    || cn.equals("Intuition")
                    || cn.equals("Jace, the Mind Sculptor")
                    || cn.equals("Karakas")
                    || cn.equals("Lim-Dul's Vault")
                    || cn.equals("Mana Drain")
                    || cn.equals("Mana Vault")
                    || cn.equals("Memory Jar")
                    || cn.equals("Merchant Scroll")
                    || cn.equals("Mind Twist")
                    || cn.equals("Mishra's Workshop")
                    || cn.equals("Natural Order")
                    || cn.equals("Oath of Druids")
                    || cn.equals("Personal Tutor")
                    || cn.equals("Snapcaster Mage")
                    || cn.equals("Steelshaper's Gift")
                    || cn.equals("Stoneforge Mystic")
                    || cn.equals("Survival of the Fittest")
                    || cn.equals("Tainted Pact")
                    || cn.equals("Time Spiral")
                    || cn.equals("Timetwister")
                    || cn.equals("Treasure Cruise")
                    || cn.equals("True-Name Nemesis")
                    || cn.equals("Umezawa's Jitte")
                    || cn.equals("Wasteland")
                    || cn.equals("Wheel of Fortune")
                    || cn.equals("Worldly Tutor")
                    || cn.equals("Yawgmoth's Bargain")) {
                totalPoints += 1;
                invalid.put(cn, "1 point");
            }

            if (cn.equals("Channel")
                    || cn.equals("Dig Through Time")
                    || cn.equals("Library of Alexandria")
                    || cn.equals("Mana Crypt")
                    || cn.equals("Mox Emerald")
                    || cn.equals("Mox Jet")
                    || cn.equals("Mox Pearl")
                    || cn.equals("Mox Ruby")
                    || cn.equals("Mox Sapphire")
                    || cn.equals("Mystical Tutor")
                    || cn.equals("Protean Hulk")
                    || cn.equals("Skullclamp")
                    || cn.equals("Strip Mine")
                    || cn.equals("Tolarian Academy")) {
                totalPoints += 2;
                invalid.put(cn, "2 points");
            }

            if (cn.equals("Demonic Tutor")
                    || cn.equals("Imperial Seal")
                    || cn.equals("Sol Ring")
                    || cn.equals("Time Walk")
                    || cn.equals("Tinker")
                    || cn.equals("Vampiric Tutor")
                    || cn.equals("Yawgmoth's Will")) {
                totalPoints += 3;
                invalid.put(cn, "3 points");
            }

            if (cn.equals("Ancestral Recall")
                    || cn.equals("Black Lotus")
                    || cn.equals("Time Vault")) {
                totalPoints += 4;
                invalid.put(cn, "4 points");
            }
        }
        if (totalPoints > 7) {
            invalid.put("Total points too high", "Your calculated point total was " + totalPoints);
            valid = false;
        }
        return valid;
    }
}
