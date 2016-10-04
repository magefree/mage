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
 * @author fireshoes
 */
public class FTVLore extends ExpansionSet {
    private static final FTVLore fINSTANCE = new FTVLore();

    public static FTVLore getInstance() {
        return fINSTANCE;
    }

    private FTVLore() {
        super("From the Vault: Lore", "V16", "mage.sets.ftvlore", new GregorianCalendar(2016, 8, 19).getTime(), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;
        cards.add(new SetCardInfo("Beseech the Queen", 1, Rarity.MYTHIC, mage.cards.b.BeseechTheQueen.class));
        cards.add(new SetCardInfo("Cabal Ritual", 2, Rarity.MYTHIC, mage.cards.c.CabalRitual.class));
        cards.add(new SetCardInfo("Conflux", 3, Rarity.MYTHIC, mage.cards.c.Conflux.class));
        cards.add(new SetCardInfo("Dark Depths", 4, Rarity.MYTHIC, mage.cards.d.DarkDepths.class));
        cards.add(new SetCardInfo("Glissa, the Traitor", 5, Rarity.MYTHIC, mage.cards.g.GlissaTheTraitor.class));
        cards.add(new SetCardInfo("Helvault", 6, Rarity.MYTHIC, mage.cards.h.Helvault.class));
        cards.add(new SetCardInfo("Memnarch", 7, Rarity.MYTHIC, mage.cards.m.Memnarch.class));
        cards.add(new SetCardInfo("Mind's Desire", 8, Rarity.MYTHIC, mage.cards.m.MindsDesire.class));
        cards.add(new SetCardInfo("Momir Vig, Simic Visionary", 9, Rarity.MYTHIC, mage.cards.m.MomirVigSimicVisionary.class));
        cards.add(new SetCardInfo("Near-Death Experience", 10, Rarity.MYTHIC, mage.cards.n.NearDeathExperience.class));
        cards.add(new SetCardInfo("Obliterate", 11, Rarity.MYTHIC, mage.cards.o.Obliterate.class));
        cards.add(new SetCardInfo("Phyrexian Processor", 12, Rarity.MYTHIC, mage.cards.p.PhyrexianProcessor.class));
        cards.add(new SetCardInfo("Tolaria West", 13, Rarity.MYTHIC, mage.cards.t.TolariaWest.class));
        cards.add(new SetCardInfo("Umezawa's Jitte", 14, Rarity.MYTHIC, mage.cards.u.UmezawasJitte.class));
        cards.add(new SetCardInfo("Unmask", 15, Rarity.MYTHIC, mage.cards.u.Unmask.class));
    }
}
