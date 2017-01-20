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
import mage.cards.FrameStyle;
import mage.constants.Rarity;
import mage.constants.SetType;

public class MagicPlayerRewards extends ExpansionSet {

    private static final MagicPlayerRewards fINSTANCE = new MagicPlayerRewards();

    public static MagicPlayerRewards getInstance() {
        return fINSTANCE;
    }

    private MagicPlayerRewards() {
        super("Magic Player Rewards", "MPRP", ExpansionSet.buildDate(1990, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;
        CardGraphicInfo graphicInfo = new CardGraphicInfo(FrameStyle.MPRP_FULL_ART_BASIC, false);
        cards.add(new SetCardInfo("Bituminous Blast", 46, Rarity.SPECIAL, mage.cards.b.BituminousBlast.class, graphicInfo));
        cards.add(new SetCardInfo("Blightning", 36, Rarity.SPECIAL, mage.cards.b.Blightning.class, graphicInfo));
        cards.add(new SetCardInfo("Brave the Elements", 50, Rarity.SPECIAL, mage.cards.b.BraveTheElements.class, graphicInfo));
        cards.add(new SetCardInfo("Burst Lightning", 47, Rarity.SPECIAL, mage.cards.b.BurstLightning.class, graphicInfo));
        cards.add(new SetCardInfo("Cancel", 41, Rarity.SPECIAL, mage.cards.c.Cancel.class, graphicInfo));
        cards.add(new SetCardInfo("Celestial Purge", 45, Rarity.SPECIAL, mage.cards.c.CelestialPurge.class, graphicInfo));
        cards.add(new SetCardInfo("Condemn", 18, Rarity.SPECIAL, mage.cards.c.Condemn.class, graphicInfo));
        cards.add(new SetCardInfo("Corrupt", 30, Rarity.SPECIAL, mage.cards.c.Corrupt.class, graphicInfo));
        cards.add(new SetCardInfo("Cruel Edict", 21, Rarity.SPECIAL, mage.cards.c.CruelEdict.class, graphicInfo));
        cards.add(new SetCardInfo("Cryptic Command", 31, Rarity.SPECIAL, mage.cards.c.CrypticCommand.class, graphicInfo));
        cards.add(new SetCardInfo("Damnation", 24, Rarity.SPECIAL, mage.cards.d.Damnation.class, graphicInfo));
        cards.add(new SetCardInfo("Day of Judgment", 49, Rarity.SPECIAL, mage.cards.d.DayOfJudgment.class, graphicInfo));
        cards.add(new SetCardInfo("Disenchant", 22, Rarity.SPECIAL, mage.cards.d.Disenchant.class, graphicInfo));
        cards.add(new SetCardInfo("Doom Blade", 51, Rarity.SPECIAL, mage.cards.d.DoomBlade.class, graphicInfo));
        cards.add(new SetCardInfo("Fireball", 6, Rarity.SPECIAL, mage.cards.f.Fireball.class, graphicInfo));
        cards.add(new SetCardInfo("Flame Javelin", 32, Rarity.SPECIAL, mage.cards.f.FlameJavelin.class, graphicInfo));
        cards.add(new SetCardInfo("Giant Growth", 13, Rarity.SPECIAL, mage.cards.g.GiantGrowth.class, graphicInfo));
        cards.add(new SetCardInfo("Harmonize", 28, Rarity.SPECIAL, mage.cards.h.Harmonize.class, graphicInfo));
        cards.add(new SetCardInfo("Harrow", 48, Rarity.SPECIAL, mage.cards.h.Harrow.class, graphicInfo));
        cards.add(new SetCardInfo("Hinder", 11, Rarity.SPECIAL, mage.cards.h.Hinder.class, graphicInfo));
        cards.add(new SetCardInfo("Hypnotic Specter", 10, Rarity.SPECIAL, mage.cards.h.HypnoticSpecter.class));
        cards.add(new SetCardInfo("Incinerate", 26, Rarity.SPECIAL, mage.cards.i.Incinerate.class, graphicInfo));
        cards.add(new SetCardInfo("Infest", 43, Rarity.SPECIAL, mage.cards.i.Infest.class, graphicInfo));
        cards.add(new SetCardInfo("Lightning Bolt", 40, Rarity.SPECIAL, mage.cards.l.LightningBolt.class, graphicInfo));
        cards.add(new SetCardInfo("Lightning Helix", 16, Rarity.SPECIAL, mage.cards.l.LightningHelix.class, graphicInfo));
        cards.add(new SetCardInfo("Mana Leak", 8, Rarity.SPECIAL, mage.cards.m.ManaLeak.class, graphicInfo));
        cards.add(new SetCardInfo("Mana Tithe", 27, Rarity.SPECIAL, mage.cards.m.ManaTithe.class, graphicInfo));
        cards.add(new SetCardInfo("Mortify", 19, Rarity.SPECIAL, mage.cards.m.Mortify.class, graphicInfo));
        cards.add(new SetCardInfo("Nameless Inversion", 34, Rarity.SPECIAL, mage.cards.n.NamelessInversion.class, graphicInfo));
        cards.add(new SetCardInfo("Negate", 38, Rarity.SPECIAL, mage.cards.n.Negate.class, graphicInfo));
        cards.add(new SetCardInfo("Oxidize", 7, Rarity.SPECIAL, mage.cards.o.Oxidize.class, graphicInfo));
        cards.add(new SetCardInfo("Ponder", 29, Rarity.SPECIAL, mage.cards.p.Ponder.class, graphicInfo));
        cards.add(new SetCardInfo("Powder Keg", 3, Rarity.SPECIAL, mage.cards.p.PowderKeg.class));
        cards.add(new SetCardInfo("Psionic Blast", 20, Rarity.SPECIAL, mage.cards.p.PsionicBlast.class, graphicInfo));
        cards.add(new SetCardInfo("Psychatog", 4, Rarity.SPECIAL, mage.cards.p.Psychatog.class));
        cards.add(new SetCardInfo("Putrefy", 14, Rarity.SPECIAL, mage.cards.p.Putrefy.class, graphicInfo));
        cards.add(new SetCardInfo("Pyroclasm", 12, Rarity.SPECIAL, mage.cards.p.Pyroclasm.class, graphicInfo));
        cards.add(new SetCardInfo("Rampant Growth", 37, Rarity.SPECIAL, mage.cards.r.RampantGrowth.class, graphicInfo));
        cards.add(new SetCardInfo("Reciprocate", 9, Rarity.SPECIAL, mage.cards.r.Reciprocate.class, graphicInfo));
        cards.add(new SetCardInfo("Recollect", 23, Rarity.SPECIAL, mage.cards.r.Recollect.class, graphicInfo));
        cards.add(new SetCardInfo("Remove Soul", 35, Rarity.SPECIAL, mage.cards.r.RemoveSoul.class, graphicInfo));
        cards.add(new SetCardInfo("Searing Blaze", 53, Rarity.SPECIAL, mage.cards.s.SearingBlaze.class, graphicInfo));
        cards.add(new SetCardInfo("Sign in Blood", 42, Rarity.SPECIAL, mage.cards.s.SignInBlood.class, graphicInfo));
        cards.add(new SetCardInfo("Terminate", 39, Rarity.SPECIAL, mage.cards.t.Terminate.class, graphicInfo));
        cards.add(new SetCardInfo("Terror", 5, Rarity.SPECIAL, mage.cards.t.Terror.class, graphicInfo));
        cards.add(new SetCardInfo("Tidings", 25, Rarity.SPECIAL, mage.cards.t.Tidings.class, graphicInfo));
        cards.add(new SetCardInfo("Treasure Hunt", 52, Rarity.SPECIAL, mage.cards.t.TreasureHunt.class, graphicInfo));
        cards.add(new SetCardInfo("Unmake", 33, Rarity.SPECIAL, mage.cards.u.Unmake.class, graphicInfo));
        cards.add(new SetCardInfo("Voidmage Prodigy", 2, Rarity.SPECIAL, mage.cards.v.VoidmageProdigy.class));
        cards.add(new SetCardInfo("Volcanic Fallout", 44, Rarity.SPECIAL, mage.cards.v.VolcanicFallout.class, graphicInfo));
        cards.add(new SetCardInfo("Wasteland", 1, Rarity.SPECIAL, mage.cards.w.Wasteland.class, graphicInfo));
        cards.add(new SetCardInfo("Wrath of God", 17, Rarity.SPECIAL, mage.cards.w.WrathOfGod.class, graphicInfo));
        cards.add(new SetCardInfo("Zombify", 15, Rarity.SPECIAL, mage.cards.z.Zombify.class, graphicInfo));
    }
}
