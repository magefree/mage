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
 * @author JayDi85
 */
public class ExplorersOfIxalan extends ExpansionSet {

    private static final ExplorersOfIxalan instance = new ExplorersOfIxalan();

    public static ExplorersOfIxalan getInstance() {
        return instance;
    }

    private ExplorersOfIxalan() {
        super("Explorers of Ixalan", "E02", ExpansionSet.buildDate(2017, 11, 24), SetType.SUPPLEMENTAL);
        this.blockName = "Explorers of Ixalan";

        cards.add(new SetCardInfo("Adaptive Automaton", 42, Rarity.RARE, mage.cards.a.AdaptiveAutomaton.class));
        cards.add(new SetCardInfo("Aether Gale", 7, Rarity.RARE, mage.cards.a.AetherGale.class));
        cards.add(new SetCardInfo("Aggravated Assault", 25, Rarity.RARE, mage.cards.a.AggravatedAssault.class));
        cards.add(new SetCardInfo("Beacon of Immortality", 1, Rarity.RARE, mage.cards.b.BeaconOfImmortality.class));
        cards.add(new SetCardInfo("Blatant Thievery", 8, Rarity.RARE, mage.cards.b.BlatantThievery.class));
        cards.add(new SetCardInfo("Bloodbond Vampire", 15, Rarity.UNCOMMON, mage.cards.b.BloodbondVampire.class));
        cards.add(new SetCardInfo("Borderland Ranger", 31, Rarity.COMMON, mage.cards.b.BorderlandRanger.class));
        cards.add(new SetCardInfo("Child of Night", 16, Rarity.COMMON, mage.cards.c.ChildOfNight.class));
        cards.add(new SetCardInfo("Coat with Venom", 17, Rarity.COMMON, mage.cards.c.CoatWithVenom.class));
        cards.add(new SetCardInfo("Concentrate", 9, Rarity.UNCOMMON, mage.cards.c.Concentrate.class));
        cards.add(new SetCardInfo("Crumbling Necropolis", 45, Rarity.UNCOMMON, mage.cards.c.CrumblingNecropolis.class));
        cards.add(new SetCardInfo("Day of Judgment", 2, Rarity.RARE, mage.cards.d.DayOfJudgment.class));
        cards.add(new SetCardInfo("Disaster Radius", 26, Rarity.RARE, mage.cards.d.DisasterRadius.class));
        cards.add(new SetCardInfo("Doom Blade", 18, Rarity.UNCOMMON, mage.cards.d.DoomBlade.class));
        cards.add(new SetCardInfo("Giant Growth", 32, Rarity.COMMON, mage.cards.g.GiantGrowth.class));
        cards.add(new SetCardInfo("Hunter's Prowess", 33, Rarity.RARE, mage.cards.h.HuntersProwess.class));
        cards.add(new SetCardInfo("Innocent Blood", 19, Rarity.COMMON, mage.cards.i.InnocentBlood.class));
        cards.add(new SetCardInfo("Jungle Barrier", 38, Rarity.UNCOMMON, mage.cards.j.JungleBarrier.class));
        cards.add(new SetCardInfo("Jungle Shrine", 46, Rarity.UNCOMMON, mage.cards.j.JungleShrine.class));
        cards.add(new SetCardInfo("Lightning Helix", 39, Rarity.UNCOMMON, mage.cards.l.LightningHelix.class));
        cards.add(new SetCardInfo("Mass Mutiny", 27, Rarity.RARE, mage.cards.m.MassMutiny.class));
        cards.add(new SetCardInfo("Merfolk Sovereign", 10, Rarity.RARE, mage.cards.m.MerfolkSovereign.class));
        cards.add(new SetCardInfo("Mortify", 40, Rarity.UNCOMMON, mage.cards.m.Mortify.class));
        cards.add(new SetCardInfo("Necropolis Regent", 20, Rarity.MYTHIC, mage.cards.n.NecropolisRegent.class));
        cards.add(new SetCardInfo("Path to Exile", 3, Rarity.UNCOMMON, mage.cards.p.PathToExile.class));
        cards.add(new SetCardInfo("Prey Upon", 34, Rarity.COMMON, mage.cards.p.PreyUpon.class));
        cards.add(new SetCardInfo("Prismatic Lens", 43, Rarity.UNCOMMON, mage.cards.p.PrismaticLens.class));
        cards.add(new SetCardInfo("Quicksilver Amulet", 44, Rarity.RARE, mage.cards.q.QuicksilverAmulet.class));
        cards.add(new SetCardInfo("Rancor", 35, Rarity.UNCOMMON, mage.cards.r.Rancor.class));
        cards.add(new SetCardInfo("Rush of Adrenaline", 28, Rarity.COMMON, mage.cards.r.RushOfAdrenaline.class));
        cards.add(new SetCardInfo("Shared Animosity", 29, Rarity.RARE, mage.cards.s.SharedAnimosity.class));
        cards.add(new SetCardInfo("Shielded by Faith", 4, Rarity.RARE, mage.cards.s.ShieldedByFaith.class));
        cards.add(new SetCardInfo("Soul of the Harvest", 36, Rarity.RARE, mage.cards.s.SoulOfTheHarvest.class));
        cards.add(new SetCardInfo("Tainted Field", 47, Rarity.UNCOMMON, mage.cards.t.TaintedField.class));
        cards.add(new SetCardInfo("Threads of Disloyalty", 11, Rarity.RARE, mage.cards.t.ThreadsOfDisloyalty.class));
        cards.add(new SetCardInfo("Time Warp", 12, Rarity.MYTHIC, mage.cards.t.TimeWarp.class));
        cards.add(new SetCardInfo("Unsummon", 13, Rarity.COMMON, mage.cards.u.Unsummon.class));
        cards.add(new SetCardInfo("Urge to Feed", 21, Rarity.UNCOMMON, mage.cards.u.UrgeToFeed.class));
        cards.add(new SetCardInfo("Vampire Interloper", 22, Rarity.COMMON, mage.cards.v.VampireInterloper.class));
        cards.add(new SetCardInfo("Vampire Nighthawk", 23, Rarity.UNCOMMON, mage.cards.v.VampireNighthawk.class));
        cards.add(new SetCardInfo("Vampire Noble", 24, Rarity.COMMON, mage.cards.v.VampireNoble.class));
        cards.add(new SetCardInfo("Veteran's Reflexes", 5, Rarity.COMMON, mage.cards.v.VeteransReflexes.class));
        cards.add(new SetCardInfo("Vow of Duty", 6, Rarity.UNCOMMON, mage.cards.v.VowOfDuty.class));
        cards.add(new SetCardInfo("Vow of Flight", 14, Rarity.UNCOMMON, mage.cards.v.VowOfFlight.class));
        cards.add(new SetCardInfo("Vow of Lightning", 30, Rarity.UNCOMMON, mage.cards.v.VowOfLightning.class));
        cards.add(new SetCardInfo("Vow of Wildness", 37, Rarity.UNCOMMON, mage.cards.v.VowOfWildness.class));
        cards.add(new SetCardInfo("Zealous Persecution", 41, Rarity.UNCOMMON, mage.cards.z.ZealousPersecution.class));
    }
}
