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
 * @author LevelX2
 */
public class ClashPack extends ExpansionSet {
    private static final ClashPack instance = new ClashPack();

    public static ClashPack getInstance() {
        return instance;
    }

    private ClashPack() {
        super("Clash Pack", "CLASH", ExpansionSet.buildDate(2014, 7, 18), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;
        cards.add(new SetCardInfo("Courser of Kruphix", 12, Rarity.SPECIAL, mage.cards.c.CourserOfKruphix.class));
        cards.add(new SetCardInfo("Fated Intervention", 2, Rarity.SPECIAL, mage.cards.f.FatedIntervention.class));
        cards.add(new SetCardInfo("Font of Fertility", 3, Rarity.SPECIAL, mage.cards.f.FontOfFertility.class));
        cards.add(new SetCardInfo("Hero's Downfall", 8, Rarity.SPECIAL, mage.cards.h.HerosDownfall.class));
        cards.add(new SetCardInfo("Hydra Broodmaster", 4, Rarity.SPECIAL, mage.cards.h.HydraBroodmaster.class));
        cards.add(new SetCardInfo("Necropolis Fiend", 7, Rarity.SPECIAL, mage.cards.n.NecropolisFiend.class));
        cards.add(new SetCardInfo("Prognostic Sphinx", 1, Rarity.SPECIAL, mage.cards.p.PrognosticSphinx.class));
        cards.add(new SetCardInfo("Prophet of Kruphix", 5, Rarity.SPECIAL, mage.cards.p.ProphetOfKruphix.class));
        cards.add(new SetCardInfo("Reaper of the Wilds", 10, Rarity.SPECIAL, mage.cards.r.ReaperOfTheWilds.class));
        cards.add(new SetCardInfo("Sultai Ascendancy", 9, Rarity.SPECIAL, mage.cards.s.SultaiAscendancy.class));
        cards.add(new SetCardInfo("Temple of Mystery", 6, Rarity.SPECIAL, mage.cards.t.TempleOfMystery.class));
        cards.add(new SetCardInfo("Whip of Erebos", 11, Rarity.SPECIAL, mage.cards.w.WhipOfErebos.class));
    }
}