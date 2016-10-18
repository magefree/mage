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
public class FTVRealms extends ExpansionSet {

    private static final FTVRealms fINSTANCE = new FTVRealms();

    public static FTVRealms getInstance() {
        return fINSTANCE;
    }

    private FTVRealms() {
        super("From the Vault: Realms", "V12", ExpansionSet.buildDate(2012, 8, 31), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;
        cards.add(new SetCardInfo("Ancient Tomb", 1, Rarity.MYTHIC, mage.cards.a.AncientTomb.class));
        cards.add(new SetCardInfo("Boseiju, Who Shelters All", 2, Rarity.MYTHIC, mage.cards.b.BoseijuWhoSheltersAll.class));
        cards.add(new SetCardInfo("Cephalid Coliseum", 3, Rarity.MYTHIC, mage.cards.c.CephalidColiseum.class));
        cards.add(new SetCardInfo("Desert", 4, Rarity.MYTHIC, mage.cards.d.Desert.class));
        cards.add(new SetCardInfo("Dryad Arbor", 5, Rarity.MYTHIC, mage.cards.d.DryadArbor.class));
        cards.add(new SetCardInfo("Forbidden Orchard", 6, Rarity.MYTHIC, mage.cards.f.ForbiddenOrchard.class));
        cards.add(new SetCardInfo("Glacial Chasm", 7, Rarity.MYTHIC, mage.cards.g.GlacialChasm.class));
        cards.add(new SetCardInfo("Grove of the Burnwillows", 8, Rarity.MYTHIC, mage.cards.g.GroveOfTheBurnwillows.class));
        cards.add(new SetCardInfo("High Market", 9, Rarity.MYTHIC, mage.cards.h.HighMarket.class));
        cards.add(new SetCardInfo("Maze of Ith", 10, Rarity.MYTHIC, mage.cards.m.MazeOfIth.class));
        cards.add(new SetCardInfo("Murmuring Bosk", 11, Rarity.MYTHIC, mage.cards.m.MurmuringBosk.class));
        cards.add(new SetCardInfo("Shivan Gorge", 12, Rarity.MYTHIC, mage.cards.s.ShivanGorge.class));
        cards.add(new SetCardInfo("Urborg, Tomb of Yawgmoth", 13, Rarity.MYTHIC, mage.cards.u.UrborgTombOfYawgmoth.class));
        cards.add(new SetCardInfo("Vesuva", 14, Rarity.MYTHIC, mage.cards.v.Vesuva.class));
        cards.add(new SetCardInfo("Windbrisk Heights", 15, Rarity.MYTHIC, mage.cards.w.WindbriskHeights.class));
    }
}
