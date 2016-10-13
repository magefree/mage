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
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author fireshoes
 */
public class FTVDragons extends ExpansionSet {

    private static final FTVDragons fINSTANCE = new FTVDragons();

    public static FTVDragons getInstance() {
        return fINSTANCE;
    }

    private FTVDragons() {
        super("From the Vault: Dragons", "DRB", "mage.sets.ftvdragons", ExpansionSet.buildDate(2008, 8, 29), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;
        cards.add(new SetCardInfo("Bladewing the Risen", 1, Rarity.RARE, mage.cards.b.BladewingTheRisen.class));
        cards.add(new SetCardInfo("Bogardan Hellkite", 2, Rarity.RARE, mage.cards.b.BogardanHellkite.class));
        cards.add(new SetCardInfo("Draco", 3, Rarity.RARE, mage.cards.d.Draco.class));
        cards.add(new SetCardInfo("Dragonstorm", 5, Rarity.RARE, mage.cards.d.Dragonstorm.class));
        cards.add(new SetCardInfo("Dragon Whelp", 4, Rarity.RARE, mage.cards.d.DragonWhelp.class));
        cards.add(new SetCardInfo("Ebon Dragon", 6, Rarity.RARE, mage.cards.e.EbonDragon.class));
        cards.add(new SetCardInfo("Form of the Dragon", 7, Rarity.RARE, mage.cards.f.FormOfTheDragon.class));
        cards.add(new SetCardInfo("Hellkite Overlord", 8, Rarity.RARE, mage.cards.h.HellkiteOverlord.class));
        cards.add(new SetCardInfo("Kokusho, the Evening Star", 9, Rarity.RARE, mage.cards.k.KokushoTheEveningStar.class));
        cards.add(new SetCardInfo("Nicol Bolas", 10, Rarity.RARE, mage.cards.n.NicolBolas.class));
        cards.add(new SetCardInfo("Niv-Mizzet, the Firemind", 11, Rarity.RARE, mage.cards.n.NivMizzetTheFiremind.class));
        cards.add(new SetCardInfo("Rith, the Awakener", 12, Rarity.RARE, mage.cards.r.RithTheAwakener.class));
        cards.add(new SetCardInfo("Shivan Dragon", 13, Rarity.RARE, mage.cards.s.ShivanDragon.class));
        cards.add(new SetCardInfo("Thunder Dragon", 14, Rarity.RARE, mage.cards.t.ThunderDragon.class));
        cards.add(new SetCardInfo("Two-Headed Dragon", 15, Rarity.RARE, mage.cards.t.TwoHeadedDragon.class));
    }
}
