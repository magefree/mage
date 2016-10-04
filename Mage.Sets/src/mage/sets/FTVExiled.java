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
public class FTVExiled extends ExpansionSet {
    private static final FTVExiled fINSTANCE = new FTVExiled();

    public static FTVExiled getInstance() {
        return fINSTANCE;
    }

    private FTVExiled() {
        super("From the Vault: Exiled", "V09", "mage.sets.ftvexiled", new GregorianCalendar(2009, 8, 28).getTime(), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;
        cards.add(new SetCardInfo("Balance", 1, Rarity.MYTHIC, mage.cards.b.Balance.class));
        cards.add(new SetCardInfo("Berserk", 2, Rarity.MYTHIC, mage.cards.b.Berserk.class));
        cards.add(new SetCardInfo("Channel", 3, Rarity.MYTHIC, mage.cards.c.Channel.class));
        cards.add(new SetCardInfo("Gifts Ungiven", 4, Rarity.MYTHIC, mage.cards.g.GiftsUngiven.class));
        cards.add(new SetCardInfo("Goblin Lackey", 5, Rarity.MYTHIC, mage.cards.g.GoblinLackey.class));
        cards.add(new SetCardInfo("Kird Ape", 6, Rarity.MYTHIC, mage.cards.k.KirdApe.class));
        cards.add(new SetCardInfo("Lotus Petal", 7, Rarity.MYTHIC, mage.cards.l.LotusPetal.class));
        cards.add(new SetCardInfo("Mystical Tutor", 8, Rarity.MYTHIC, mage.cards.m.MysticalTutor.class));
        cards.add(new SetCardInfo("Necropotence", 9, Rarity.MYTHIC, mage.cards.n.Necropotence.class));
        cards.add(new SetCardInfo("Sensei's Divining Top", 10, Rarity.MYTHIC, mage.cards.s.SenseisDiviningTop.class));
        cards.add(new SetCardInfo("Serendib Efreet", 11, Rarity.MYTHIC, mage.cards.s.SerendibEfreet.class));
        cards.add(new SetCardInfo("Skullclamp", 12, Rarity.MYTHIC, mage.cards.s.Skullclamp.class));
        cards.add(new SetCardInfo("Strip Mine", 13, Rarity.MYTHIC, mage.cards.s.StripMine.class));
        cards.add(new SetCardInfo("Tinker", 14, Rarity.MYTHIC, mage.cards.t.Tinker.class));
        cards.add(new SetCardInfo("Trinisphere", 15, Rarity.MYTHIC, mage.cards.t.Trinisphere.class));
    }
}