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
public class PDSFireAndLightning extends ExpansionSet {

    private static final PDSFireAndLightning fINSTANCE = new PDSFireAndLightning();

    public static PDSFireAndLightning getInstance() {
        return fINSTANCE;
    }

    private PDSFireAndLightning() {
        super("Premium Deck Series: Fire and Lightning", "PD2", ExpansionSet.buildDate(2010, 11, 1),
                SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;
        cards.add(new SetCardInfo("Ball Lightning", 12, Rarity.RARE, mage.cards.b.BallLightning.class));
        cards.add(new SetCardInfo("Barbarian Ring", 28, Rarity.UNCOMMON, mage.cards.b.BarbarianRing.class));
        cards.add(new SetCardInfo("Boggart Ram-Gang", 13, Rarity.UNCOMMON, mage.cards.b.BoggartRamGang.class));
        cards.add(new SetCardInfo("Browbeat", 21, Rarity.UNCOMMON, mage.cards.b.Browbeat.class));
        cards.add(new SetCardInfo("Chain Lightning", 16, Rarity.COMMON, mage.cards.c.ChainLightning.class));
        cards.add(new SetCardInfo("Cinder Pyromancer", 9, Rarity.COMMON, mage.cards.c.CinderPyromancer.class));
        cards.add(new SetCardInfo("Figure of Destiny", 5, Rarity.RARE, mage.cards.f.FigureOfDestiny.class));
        cards.add(new SetCardInfo("Fireball", 27, Rarity.UNCOMMON, mage.cards.f.Fireball.class));
        cards.add(new SetCardInfo("Fireblast", 26, Rarity.COMMON, mage.cards.f.Fireblast.class));
        cards.add(new SetCardInfo("Fire Servant", 15, Rarity.UNCOMMON, mage.cards.f.FireServant.class));
        cards.add(new SetCardInfo("Flames of the Blood Hand", 22, Rarity.UNCOMMON, mage.cards.f.FlamesOfTheBloodHand.class));
        cards.add(new SetCardInfo("Ghitu Encampment", 29, Rarity.UNCOMMON, mage.cards.g.GhituEncampment.class));
        cards.add(new SetCardInfo("Grim Lavamancer", 1, Rarity.RARE, mage.cards.g.GrimLavamancer.class));
        cards.add(new SetCardInfo("Hammer of Bogardan", 23, Rarity.RARE, mage.cards.h.HammerOfBogardan.class));
        cards.add(new SetCardInfo("Hellspark Elemental", 6, Rarity.UNCOMMON, mage.cards.h.HellsparkElemental.class));
        cards.add(new SetCardInfo("Jackal Pup", 2, Rarity.UNCOMMON, mage.cards.j.JackalPup.class));
        cards.add(new SetCardInfo("Jaya Ballard, Task Mage", 10, Rarity.RARE, mage.cards.j.JayaBallardTaskMage.class));
        cards.add(new SetCardInfo("Keldon Champion", 14, Rarity.UNCOMMON, mage.cards.k.KeldonChampion.class));
        cards.add(new SetCardInfo("Keldon Marauders", 7, Rarity.COMMON, mage.cards.k.KeldonMarauders.class));
        cards.add(new SetCardInfo("Lightning Bolt", 17, Rarity.COMMON, mage.cards.l.LightningBolt.class));
        cards.add(new SetCardInfo("Mogg Fanatic", 3, Rarity.UNCOMMON, mage.cards.m.MoggFanatic.class));
        cards.add(new SetCardInfo("Mogg Flunkies", 8, Rarity.COMMON, mage.cards.m.MoggFlunkies.class));
        cards.add(new SetCardInfo("Mountain", 31, Rarity.LAND, mage.cards.basiclands.Mountain.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Mountain", 32, Rarity.LAND, mage.cards.basiclands.Mountain.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Mountain", 33, Rarity.LAND, mage.cards.basiclands.Mountain.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Mountain", 34, Rarity.LAND, mage.cards.basiclands.Mountain.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Pillage", 24, Rarity.UNCOMMON, mage.cards.p.Pillage.class));
        cards.add(new SetCardInfo("Price of Progress", 18, Rarity.UNCOMMON, mage.cards.p.PriceOfProgress.class));
        cards.add(new SetCardInfo("Reverberate", 20, Rarity.RARE, mage.cards.r.Reverberate.class));
        cards.add(new SetCardInfo("Spark Elemental", 4, Rarity.UNCOMMON, mage.cards.s.SparkElemental.class));
        cards.add(new SetCardInfo("Sudden Impact", 25, Rarity.UNCOMMON, mage.cards.s.SuddenImpact.class));
        cards.add(new SetCardInfo("Teetering Peaks", 30, Rarity.COMMON, mage.cards.t.TeeteringPeaks.class));
        cards.add(new SetCardInfo("Thunderbolt", 19, Rarity.COMMON, mage.cards.t.Thunderbolt.class));
        cards.add(new SetCardInfo("Vulshok Sorcerer", 11, Rarity.COMMON, mage.cards.v.VulshokSorcerer.class));
    }
}
