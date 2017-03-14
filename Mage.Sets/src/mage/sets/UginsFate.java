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

import mage.cards.ExpansionSet;
import mage.constants.SetType;
import mage.constants.Rarity;

/**
 *
 * @author fireshoes
 */

public class UginsFate extends ExpansionSet {

    private static final UginsFate instance = new UginsFate();

    public static UginsFate getInstance() {
        return instance;
    }

    private UginsFate() {
        super("Ugin's Fate", "UGIN", ExpansionSet.buildDate(2015, 1, 16), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        cards.add(new SetCardInfo("Ainok Tracker", 96, Rarity.COMMON, mage.cards.a.AinokTracker.class));
        cards.add(new SetCardInfo("Altar of the Brood", 216, Rarity.RARE, mage.cards.a.AltarOfTheBrood.class));
        cards.add(new SetCardInfo("Arashin War Beast", 123, Rarity.UNCOMMON, mage.cards.a.ArashinWarBeast.class));
        cards.add(new SetCardInfo("Arc Lightning", 97, Rarity.UNCOMMON, mage.cards.a.ArcLightning.class));
        cards.add(new SetCardInfo("Briber's Purse", 217, Rarity.UNCOMMON, mage.cards.b.BribersPurse.class));
        cards.add(new SetCardInfo("Debilitating Injury", 68, Rarity.COMMON, mage.cards.d.DebilitatingInjury.class));
        cards.add(new SetCardInfo("Dragonscale Boon", 131, Rarity.COMMON, mage.cards.d.DragonscaleBoon.class));
        cards.add(new SetCardInfo("Fierce Invocation", 98, Rarity.COMMON, mage.cards.f.FierceInvocation.class));
        cards.add(new SetCardInfo("Formless Nurturing", 129, Rarity.COMMON, mage.cards.f.FormlessNurturing.class));
        cards.add(new SetCardInfo("Ghostfire Blade", 220, Rarity.RARE, mage.cards.g.GhostfireBlade.class));
        cards.add(new SetCardInfo("Grim Haruspex", 73, Rarity.RARE, mage.cards.g.GrimHaruspex.class));
        cards.add(new SetCardInfo("Hewed Stone Retainers", 161, Rarity.UNCOMMON, mage.cards.h.HewedStoneRetainers.class));
        cards.add(new SetCardInfo("Jeering Instigator", 113, Rarity.RARE, mage.cards.j.JeeringInstigator.class));
        cards.add(new SetCardInfo("Jeskai Infiltrator", 36, Rarity.RARE, mage.cards.j.JeskaiInfiltrator.class));
        cards.add(new SetCardInfo("Mastery of the Unseen", 19, Rarity.RARE, mage.cards.m.MasteryOfTheUnseen.class));
        cards.add(new SetCardInfo("Mystic of the Hidden Way", 48, Rarity.COMMON, mage.cards.m.MysticOfTheHiddenWay.class));
        cards.add(new SetCardInfo("Reality Shift", 46, Rarity.UNCOMMON, mage.cards.r.RealityShift.class));
        cards.add(new SetCardInfo("Ruthless Ripper", 88, Rarity.UNCOMMON, mage.cards.r.RuthlessRipper.class));
        cards.add(new SetCardInfo("Smite the Monstrous", 24, Rarity.COMMON, mage.cards.s.SmiteTheMonstrous.class));
        cards.add(new SetCardInfo("Soul Summons", 26, Rarity.COMMON, mage.cards.s.SoulSummons.class));
        cards.add(new SetCardInfo("Sultai Emissary", 85, Rarity.COMMON, mage.cards.s.SultaiEmissary.class));
        cards.add(new SetCardInfo("Ugin's Construct", 164, Rarity.UNCOMMON, mage.cards.u.UginsConstruct.class));
        cards.add(new SetCardInfo("Ugin, the Spirit Dragon", 1, Rarity.MYTHIC, mage.cards.u.UginTheSpiritDragon.class));
        cards.add(new SetCardInfo("Watcher of the Roost", 30, Rarity.UNCOMMON, mage.cards.w.WatcherOfTheRoost.class));
        cards.add(new SetCardInfo("Wildcall", 146, Rarity.RARE, mage.cards.w.Wildcall.class));
        cards.add(new SetCardInfo("Write into Being", 59, Rarity.COMMON, mage.cards.w.WriteIntoBeing.class));
    }
}
