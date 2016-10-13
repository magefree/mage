/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets;

import java.util.GregorianCalendar;
import mage.cards.ExpansionSet;
import mage.constants.SetType;
import mage.constants.Rarity;
import java.util.List;
import mage.ObjectColor;
import mage.cards.CardGraphicInfo;
import mage.cards.FrameStyle;

/**
 *
 * @author fireshoes
 */

public class SuperSeries extends ExpansionSet {

    private static final SuperSeries fINSTANCE = new SuperSeries();

    public static SuperSeries getInstance() {
        return fINSTANCE;
    }

    private SuperSeries() {
        super("Super Series", "SUS", "mage.sets.superseries", ExpansionSet.buildDate(1996, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        cards.add(new SetCardInfo("City of Brass", 6, Rarity.SPECIAL, mage.cards.c.CityOfBrass.class));
        cards.add(new SetCardInfo("Crusade", 4, Rarity.SPECIAL, mage.cards.c.Crusade.class));
        cards.add(new SetCardInfo("Elvish Champion", 17, Rarity.SPECIAL, mage.cards.e.ElvishChampion.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Elvish Champion", 26, Rarity.SPECIAL, mage.cards.e.ElvishChampion.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Elvish Champion", 32, Rarity.SPECIAL, mage.cards.e.ElvishChampion.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Elvish Lyrist", 5, Rarity.COMMON, mage.cards.e.ElvishLyrist.class));
        cards.add(new SetCardInfo("Giant Growth", 8, Rarity.COMMON, mage.cards.g.GiantGrowth.class));
        cards.add(new SetCardInfo("Glorious Anthem", 16, Rarity.SPECIAL, mage.cards.g.GloriousAnthem.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Glorious Anthem", 25, Rarity.SPECIAL, mage.cards.g.GloriousAnthem.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Glorious Anthem", 31, Rarity.SPECIAL, mage.cards.g.GloriousAnthem.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Lord of Atlantis", 3, Rarity.SPECIAL, mage.cards.l.LordOfAtlantis.class));
        cards.add(new SetCardInfo("Mad Auntie", 18, Rarity.SPECIAL, mage.cards.m.MadAuntie.class));
        cards.add(new SetCardInfo("Royal Assassin", 20, Rarity.SPECIAL, mage.cards.r.RoyalAssassin.class));
        cards.add(new SetCardInfo("Sakura-Tribe Elder", 12, Rarity.COMMON, mage.cards.s.SakuraTribeElder.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Sakura-Tribe Elder", 21, Rarity.COMMON, mage.cards.s.SakuraTribeElder.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Sakura-Tribe Elder", 27, Rarity.COMMON, mage.cards.s.SakuraTribeElder.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Serra Avatar", 2, Rarity.SPECIAL, mage.cards.s.SerraAvatar.class));
        cards.add(new SetCardInfo("Shard Phoenix", 13, Rarity.SPECIAL, mage.cards.s.ShardPhoenix.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Shard Phoenix", 22, Rarity.SPECIAL, mage.cards.s.ShardPhoenix.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Shard Phoenix", 28, Rarity.SPECIAL, mage.cards.s.ShardPhoenix.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Slith Firewalker", 19, Rarity.SPECIAL, mage.cards.s.SlithFirewalker.class));
        cards.add(new SetCardInfo("Soltari Priest", 14, Rarity.SPECIAL, mage.cards.s.SoltariPriest.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Soltari Priest", 23, Rarity.SPECIAL, mage.cards.s.SoltariPriest.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Soltari Priest", 29, Rarity.SPECIAL, mage.cards.s.SoltariPriest.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Thran Quarry", 1, Rarity.SPECIAL, mage.cards.t.ThranQuarry.class));
        cards.add(new SetCardInfo("Two-Headed Dragon", 9, Rarity.SPECIAL, mage.cards.t.TwoHeadedDragon.class));
        cards.add(new SetCardInfo("Volcanic Hammer", 7, Rarity.COMMON, mage.cards.v.VolcanicHammer.class));
        cards.add(new SetCardInfo("Whirling Dervish", 15, Rarity.COMMON, mage.cards.w.WhirlingDervish.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Whirling Dervish", 24, Rarity.COMMON, mage.cards.w.WhirlingDervish.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Whirling Dervish", 30, Rarity.COMMON, mage.cards.w.WhirlingDervish.class, new CardGraphicInfo(null, true)));
    }
}
