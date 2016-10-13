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

package mage.sets;

import java.util.GregorianCalendar;
import mage.cards.ExpansionSet;
import mage.constants.SetType;
import mage.constants.Rarity;
import java.util.List;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class WelcomeDeck2016 extends ExpansionSet {
    private static final WelcomeDeck2016 fINSTANCE = new WelcomeDeck2016();

    public static WelcomeDeck2016 getInstance() {
        return fINSTANCE;
    }

    private WelcomeDeck2016() {
        super("Welcome Deck 2016", "W16", "mage.sets.welcomedeck2016", ExpansionSet.buildDate(2016, 3, 8), SetType.SUPPLEMENTAL_STANDARD_LEGAL);
        this.hasBasicLands = false;
        this.hasBoosters = false;
        cards.add(new SetCardInfo("Aegis Angel", 1, Rarity.RARE, mage.cards.a.AegisAngel.class));
        cards.add(new SetCardInfo("Air Servant", 4, Rarity.UNCOMMON, mage.cards.a.AirServant.class));
        cards.add(new SetCardInfo("Borderland Marauder", 11, Rarity.COMMON, mage.cards.b.BorderlandMarauder.class));
        cards.add(new SetCardInfo("Cone of Flame", 12, Rarity.UNCOMMON, mage.cards.c.ConeOfFlame.class));
        cards.add(new SetCardInfo("Disperse", 5, Rarity.COMMON, mage.cards.d.Disperse.class));
        cards.add(new SetCardInfo("Incremental Growth", 14, Rarity.UNCOMMON, mage.cards.i.IncrementalGrowth.class));
        cards.add(new SetCardInfo("Marked by Honor", 2, Rarity.COMMON, mage.cards.m.MarkedByHonor.class));
        cards.add(new SetCardInfo("Mind Rot", 7, Rarity.COMMON, mage.cards.m.MindRot.class));
        cards.add(new SetCardInfo("Nightmare", 8, Rarity.RARE, mage.cards.n.Nightmare.class));
        cards.add(new SetCardInfo("Oakenform", 15, Rarity.COMMON, mage.cards.o.Oakenform.class));
        cards.add(new SetCardInfo("Sengir Vampire", 9, Rarity.UNCOMMON, mage.cards.s.SengirVampire.class));
        cards.add(new SetCardInfo("Serra Angel", 3, Rarity.RARE, mage.cards.s.SerraAngel.class));
        cards.add(new SetCardInfo("Shivan Dragon", 13, Rarity.RARE, mage.cards.s.ShivanDragon.class));
        cards.add(new SetCardInfo("Soul of the Harvest", 16, Rarity.RARE, mage.cards.s.SoulOfTheHarvest.class));
        cards.add(new SetCardInfo("Sphinx of Magosi", 6, Rarity.RARE, mage.cards.s.SphinxOfMagosi.class));
        cards.add(new SetCardInfo("Walking Corpse", 10, Rarity.COMMON, mage.cards.w.WalkingCorpse.class));
    }
}