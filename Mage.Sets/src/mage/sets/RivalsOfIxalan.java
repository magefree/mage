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
public class RivalsOfIxalan extends ExpansionSet {

    private static final RivalsOfIxalan instance = new RivalsOfIxalan();

    public static RivalsOfIxalan getInstance() {
        return instance;
    }

    private RivalsOfIxalan() {
        super("Rivals of Ixalan", "RIX", ExpansionSet.buildDate(2018, 1, 19), SetType.EXPANSION);
        this.blockName = "Ixalan";
        this.parentSet = Ixalan.getInstance();
        this.hasBoosters = true;
        this.hasBasicLands = false;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 11;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;

        cards.add(new SetCardInfo("Angrath's Ambusher", 202, Rarity.UNCOMMON, mage.cards.a.AngrathsAmbusher.class));
        cards.add(new SetCardInfo("Angrath's Fury", 204, Rarity.RARE, mage.cards.a.AngrathsFury.class));
        cards.add(new SetCardInfo("Angrath, Minotaur Pirate", 201, Rarity.MYTHIC, mage.cards.a.AngrathMinotaurPirate.class));
        cards.add(new SetCardInfo("Brass's Bounty", 94, Rarity.RARE, mage.cards.b.BrasssBounty.class));
        cards.add(new SetCardInfo("Captain's Hook", 177, Rarity.RARE, mage.cards.c.CaptainsHook.class));
        cards.add(new SetCardInfo("Cinder Barrens", 205, Rarity.RARE, mage.cards.c.CinderBarrens.class));
        cards.add(new SetCardInfo("Evolving Wilds", 186, Rarity.RARE, mage.cards.e.EvolvingWilds.class));
        cards.add(new SetCardInfo("Ghalta, Primal Hunger", 130, Rarity.RARE, mage.cards.g.GhaltaPrimalHunger.class));
        cards.add(new SetCardInfo("Silvergill Adept", 53, Rarity.UNCOMMON, mage.cards.s.SilvergillAdept.class));
        cards.add(new SetCardInfo("Storm the Vault", 173, Rarity.RARE, mage.cards.s.StormTheVault.class));
        cards.add(new SetCardInfo("Swab Goblin", 203, Rarity.COMMON, mage.cards.s.SwabGoblin.class));
        cards.add(new SetCardInfo("Tetzimoc, Primal Death", 86, Rarity.RARE, mage.cards.t.TetzimocPrimalDeath.class));
        cards.add(new SetCardInfo("The Immortal Sun", 180, Rarity.MYTHIC, mage.cards.t.TheImmortalSun.class));
        cards.add(new SetCardInfo("Vampire Champion", 198, Rarity.COMMON, mage.cards.v.VampireChampion.class));
        cards.add(new SetCardInfo("Vault of Catlacan", 173, Rarity.RARE, mage.cards.v.VaultOfCatlacan.class));
        cards.add(new SetCardInfo("Vona's Hunger", 90, Rarity.RARE, mage.cards.v.VonasHunger.class));
        cards.add(new SetCardInfo("Vraska's Conquistador", 199, Rarity.UNCOMMON, mage.cards.v.VraskasConquistador.class));
        cards.add(new SetCardInfo("Vraska's Scorn", 200, Rarity.RARE, mage.cards.v.VraskasScorn.class));
        cards.add(new SetCardInfo("Vraska, Scheming Gorgon", 197, Rarity.MYTHIC, mage.cards.v.VraskaSchemingGorgon.class));
    }
}
