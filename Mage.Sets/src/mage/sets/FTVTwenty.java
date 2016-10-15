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

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author fireshoes
 */
public class FTVTwenty extends ExpansionSet {

    private static final FTVTwenty fINSTANCE = new FTVTwenty();

    public static FTVTwenty getInstance() {
        return fINSTANCE;
    }

    private FTVTwenty() {
        super("From the Vault: Twenty", "V13", ExpansionSet.buildDate(2013, 8, 23), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;
        cards.add(new SetCardInfo("Akroma's Vengeance", 11, Rarity.MYTHIC, mage.cards.a.AkromasVengeance.class));
        cards.add(new SetCardInfo("Chainer's Edict", 10, Rarity.MYTHIC, mage.cards.c.ChainersEdict.class));
        cards.add(new SetCardInfo("Chameleon Colossus", 16, Rarity.MYTHIC, mage.cards.c.ChameleonColossus.class));
        cards.add(new SetCardInfo("Char", 14, Rarity.MYTHIC, mage.cards.c.Char.class));
        cards.add(new SetCardInfo("Cruel Ultimatum", 17, Rarity.MYTHIC, mage.cards.c.CruelUltimatum.class));
        cards.add(new SetCardInfo("Dark Ritual", 1, Rarity.MYTHIC, mage.cards.d.DarkRitual.class));
        cards.add(new SetCardInfo("Fact or Fiction", 9, Rarity.MYTHIC, mage.cards.f.FactOrFiction.class));
        cards.add(new SetCardInfo("Fyndhorn Elves", 4, Rarity.MYTHIC, mage.cards.f.FyndhornElves.class));
        cards.add(new SetCardInfo("Gilded Lotus", 12, Rarity.MYTHIC, mage.cards.g.GildedLotus.class));
        cards.add(new SetCardInfo("Green Sun's Zenith", 19, Rarity.MYTHIC, mage.cards.g.GreenSunsZenith.class));
        cards.add(new SetCardInfo("Hymn to Tourach", 3, Rarity.SPECIAL, mage.cards.h.HymnToTourach1.class));
        cards.add(new SetCardInfo("Impulse", 5, Rarity.MYTHIC, mage.cards.i.Impulse.class));
        cards.add(new SetCardInfo("Ink-Eyes, Servant of Oni", 13, Rarity.MYTHIC, mage.cards.i.InkEyesServantOfOni.class));
        cards.add(new SetCardInfo("Jace, the Mind Sculptor", 18, Rarity.MYTHIC, mage.cards.j.JaceTheMindSculptor.class));
        cards.add(new SetCardInfo("Kessig Wolf Run", 20, Rarity.MYTHIC, mage.cards.k.KessigWolfRun.class));
        cards.add(new SetCardInfo("Swords to Plowshares", 2, Rarity.MYTHIC, mage.cards.s.SwordsToPlowshares.class));
        cards.add(new SetCardInfo("Tangle Wire", 8, Rarity.MYTHIC, mage.cards.t.TangleWire.class));
        cards.add(new SetCardInfo("Thran Dynamo", 7, Rarity.MYTHIC, mage.cards.t.ThranDynamo.class));
        cards.add(new SetCardInfo("Venser, Shaper Savant", 15, Rarity.MYTHIC, mage.cards.v.VenserShaperSavant.class));
        cards.add(new SetCardInfo("Wall of Blossoms", 6, Rarity.MYTHIC, mage.cards.w.WallOfBlossoms.class));
    }
}
