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

import mage.cards.CardGraphicInfo;
import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author fireshoes
 */
public class PDSGraveborn extends ExpansionSet {
    private static final PDSGraveborn instance = new PDSGraveborn();

    public static PDSGraveborn getInstance() {
        return instance;
    }

    private PDSGraveborn() {
        super("Premium Deck Series: Graveborn", "PD3", ExpansionSet.buildDate(2011, 11, 1), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;
        cards.add(new SetCardInfo("Animate Dead", 16, Rarity.UNCOMMON, mage.cards.a.AnimateDead.class));
        cards.add(new SetCardInfo("Avatar of Woe", 6, Rarity.RARE, mage.cards.a.AvatarOfWoe.class));
        cards.add(new SetCardInfo("Blazing Archon", 11, Rarity.RARE, mage.cards.b.BlazingArchon.class));
        cards.add(new SetCardInfo("Buried Alive", 20, Rarity.UNCOMMON, mage.cards.b.BuriedAlive.class));
        cards.add(new SetCardInfo("Cabal Therapy", 12, Rarity.UNCOMMON, mage.cards.c.CabalTherapy.class));
        cards.add(new SetCardInfo("Crosis, the Purger", 5, Rarity.RARE, mage.cards.c.CrosisThePurger.class));
        cards.add(new SetCardInfo("Crystal Vein", 24, Rarity.UNCOMMON, mage.cards.c.CrystalVein.class));
        cards.add(new SetCardInfo("Diabolic Servitude", 22, Rarity.UNCOMMON, mage.cards.d.DiabolicServitude.class));
        cards.add(new SetCardInfo("Dread Return", 23, Rarity.UNCOMMON, mage.cards.d.DreadReturn.class));
        cards.add(new SetCardInfo("Duress", 13, Rarity.COMMON, mage.cards.d.Duress.class));
        cards.add(new SetCardInfo("Ebon Stronghold", 25, Rarity.UNCOMMON, mage.cards.e.EbonStronghold.class));
        cards.add(new SetCardInfo("Entomb", 14, Rarity.RARE, mage.cards.e.Entomb.class));
        cards.add(new SetCardInfo("Exhume", 17, Rarity.COMMON, mage.cards.e.Exhume.class));
        cards.add(new SetCardInfo("Faceless Butcher", 3, Rarity.COMMON, mage.cards.f.FacelessButcher.class));
        cards.add(new SetCardInfo("Hidden Horror", 2, Rarity.UNCOMMON, mage.cards.h.HiddenHorror.class));
        cards.add(new SetCardInfo("Inkwell Leviathan", 10, Rarity.RARE, mage.cards.i.InkwellLeviathan.class));
        cards.add(new SetCardInfo("Polluted Mire", 26, Rarity.COMMON, mage.cards.p.PollutedMire.class));
        cards.add(new SetCardInfo("Putrid Imp", 1, Rarity.COMMON, mage.cards.p.PutridImp.class));
        cards.add(new SetCardInfo("Reanimate", 15, Rarity.UNCOMMON, mage.cards.r.Reanimate.class));
        cards.add(new SetCardInfo("Sickening Dreams", 18, Rarity.UNCOMMON, mage.cards.s.SickeningDreams.class));
        cards.add(new SetCardInfo("Sphinx of the Steel Wind", 9, Rarity.MYTHIC, mage.cards.s.SphinxOfTheSteelWind.class));
        cards.add(new SetCardInfo("Swamp", 27, Rarity.LAND, mage.cards.basiclands.Swamp.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Swamp", 28, Rarity.LAND, mage.cards.basiclands.Swamp.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Swamp", 29, Rarity.LAND, mage.cards.basiclands.Swamp.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Swamp", 30, Rarity.LAND, mage.cards.basiclands.Swamp.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Terastodon", 7, Rarity.RARE, mage.cards.t.Terastodon.class));
        cards.add(new SetCardInfo("Twisted Abomination", 4, Rarity.COMMON, mage.cards.t.TwistedAbomination.class));
        cards.add(new SetCardInfo("Verdant Force", 8, Rarity.RARE, mage.cards.v.VerdantForce.class));
        cards.add(new SetCardInfo("Zombie Infestation", 19, Rarity.UNCOMMON, mage.cards.z.ZombieInfestation.class));
    }
}