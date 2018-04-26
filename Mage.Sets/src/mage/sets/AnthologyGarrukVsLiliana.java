/*
 * Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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
public class AnthologyGarrukVsLiliana extends ExpansionSet {

    private static final AnthologyGarrukVsLiliana instance = new AnthologyGarrukVsLiliana();

    public static AnthologyGarrukVsLiliana getInstance() {
        return instance;
    }

    private AnthologyGarrukVsLiliana() {
        super("Duel Decks: Anthology, Garruk vs. Liliana", "DD3GVL", ExpansionSet.buildDate(2014, 12, 5),
                SetType.SUPPLEMENTAL);
        this.blockName = "Duel Decks: Anthology";
        this.hasBasicLands = false;
        cards.add(new SetCardInfo("Albino Troll", 3, Rarity.UNCOMMON, mage.cards.a.AlbinoTroll.class));
        cards.add(new SetCardInfo("Bad Moon", 48, Rarity.RARE, mage.cards.b.BadMoon.class));
        cards.add(new SetCardInfo("Basking Rootwalla", 2, Rarity.COMMON, mage.cards.b.BaskingRootwalla.class));
        cards.add(new SetCardInfo("Beast Attack", 23, Rarity.UNCOMMON, mage.cards.b.BeastAttack.class));
        cards.add(new SetCardInfo("Blastoderm", 7, Rarity.COMMON, mage.cards.b.Blastoderm.class));
        cards.add(new SetCardInfo("Corrupt", 57, Rarity.UNCOMMON, mage.cards.c.Corrupt.class));
        cards.add(new SetCardInfo("Deathgreeter", 33, Rarity.COMMON, mage.cards.d.Deathgreeter.class));
        cards.add(new SetCardInfo("Drudge Skeletons", 36, Rarity.COMMON, mage.cards.d.DrudgeSkeletons.class));
        cards.add(new SetCardInfo("Elephant Guide", 18, Rarity.UNCOMMON, mage.cards.e.ElephantGuide.class));
        cards.add(new SetCardInfo("Enslave", 58, Rarity.UNCOMMON, mage.cards.e.Enslave.class));
        cards.add(new SetCardInfo("Faerie Macabre", 42, Rarity.COMMON, mage.cards.f.FaerieMacabre.class));
        cards.add(new SetCardInfo("Fleshbag Marauder", 38, Rarity.UNCOMMON, mage.cards.f.FleshbagMarauder.class));
        cards.add(new SetCardInfo("Forest", 28, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 29, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 30, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 31, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Garruk Wildspeaker", 1, Rarity.MYTHIC, mage.cards.g.GarrukWildspeaker.class));
        cards.add(new SetCardInfo("Genju of the Cedars", 13, Rarity.UNCOMMON, mage.cards.g.GenjuOfTheCedars.class));
        cards.add(new SetCardInfo("Genju of the Fens", 47, Rarity.UNCOMMON, mage.cards.g.GenjuOfTheFens.class));
        cards.add(new SetCardInfo("Ghost-Lit Stalker", 34, Rarity.UNCOMMON, mage.cards.g.GhostLitStalker.class));
        cards.add(new SetCardInfo("Giant Growth", 14, Rarity.COMMON, mage.cards.g.GiantGrowth.class));
        cards.add(new SetCardInfo("Harmonize", 21, Rarity.UNCOMMON, mage.cards.h.Harmonize.class));
        cards.add(new SetCardInfo("Hideous End", 52, Rarity.COMMON, mage.cards.h.HideousEnd.class));
        cards.add(new SetCardInfo("Howling Banshee", 43, Rarity.UNCOMMON, mage.cards.h.HowlingBanshee.class));
        cards.add(new SetCardInfo("Ichor Slick", 51, Rarity.COMMON, mage.cards.i.IchorSlick.class));
        cards.add(new SetCardInfo("Indrik Stomphowler", 10, Rarity.UNCOMMON, mage.cards.i.IndrikStomphowler.class));
        cards.add(new SetCardInfo("Invigorate", 19, Rarity.COMMON, mage.cards.i.Invigorate.class));
        cards.add(new SetCardInfo("Keening Banshee", 44, Rarity.UNCOMMON, mage.cards.k.KeeningBanshee.class));
        cards.add(new SetCardInfo("Krosan Tusker", 11, Rarity.COMMON, mage.cards.k.KrosanTusker.class));
        cards.add(new SetCardInfo("Lignify", 16, Rarity.COMMON, mage.cards.l.Lignify.class));
        cards.add(new SetCardInfo("Liliana Vess", 32, Rarity.MYTHIC, mage.cards.l.LilianaVess.class));
        cards.add(new SetCardInfo("Mutilate", 55, Rarity.RARE, mage.cards.m.Mutilate.class));
        cards.add(new SetCardInfo("Nature's Lore", 17, Rarity.COMMON, mage.cards.n.NaturesLore.class));
        cards.add(new SetCardInfo("Overrun", 24, Rarity.UNCOMMON, mage.cards.o.Overrun.class));
        cards.add(new SetCardInfo("Phyrexian Rager", 39, Rarity.COMMON, mage.cards.p.PhyrexianRager.class));
        cards.add(new SetCardInfo("Plated Slagwurm", 12, Rarity.RARE, mage.cards.p.PlatedSlagwurm.class));
        cards.add(new SetCardInfo("Polluted Mire", 59, Rarity.COMMON, mage.cards.p.PollutedMire.class));
        cards.add(new SetCardInfo("Rancor", 15, Rarity.COMMON, mage.cards.r.Rancor.class));
        cards.add(new SetCardInfo("Ravenous Baloth", 8, Rarity.RARE, mage.cards.r.RavenousBaloth.class));
        cards.add(new SetCardInfo("Ravenous Rats", 37, Rarity.COMMON, mage.cards.r.RavenousRats.class));
        cards.add(new SetCardInfo("Rise from the Grave", 56, Rarity.UNCOMMON, mage.cards.r.RiseFromTheGrave.class));
        cards.add(new SetCardInfo("Rude Awakening", 22, Rarity.RARE, mage.cards.r.RudeAwakening.class));
        cards.add(new SetCardInfo("Serrated Arrows", 20, Rarity.COMMON, mage.cards.s.SerratedArrows.class));
        cards.add(new SetCardInfo("Sign in Blood", 49, Rarity.COMMON, mage.cards.s.SignInBlood.class));
        cards.add(new SetCardInfo("Skeletal Vampire", 46, Rarity.RARE, mage.cards.s.SkeletalVampire.class));
        cards.add(new SetCardInfo("Slippery Karst", 26, Rarity.COMMON, mage.cards.s.SlipperyKarst.class));
        cards.add(new SetCardInfo("Snuff Out", 53, Rarity.COMMON, mage.cards.s.SnuffOut.class));
        cards.add(new SetCardInfo("Stampeding Wildebeests", 9, Rarity.UNCOMMON, mage.cards.s.StampedingWildebeests.class));
        cards.add(new SetCardInfo("Swamp", 60, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 61, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 62, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 63, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tendrils of Corruption", 54, Rarity.COMMON, mage.cards.t.TendrilsOfCorruption.class));
        cards.add(new SetCardInfo("Treetop Village", 27, Rarity.UNCOMMON, mage.cards.t.TreetopVillage.class));
        cards.add(new SetCardInfo("Twisted Abomination", 45, Rarity.COMMON, mage.cards.t.TwistedAbomination.class));
        cards.add(new SetCardInfo("Urborg Syphon-Mage", 40, Rarity.COMMON, mage.cards.u.UrborgSyphonMage.class));
        cards.add(new SetCardInfo("Vampire Bats", 35, Rarity.COMMON, mage.cards.v.VampireBats.class));
        cards.add(new SetCardInfo("Vicious Hunger", 50, Rarity.COMMON, mage.cards.v.ViciousHunger.class));
        cards.add(new SetCardInfo("Vine Trellis", 4, Rarity.COMMON, mage.cards.v.VineTrellis.class));
        cards.add(new SetCardInfo("Wall of Bone", 41, Rarity.UNCOMMON, mage.cards.w.WallOfBone.class));
        cards.add(new SetCardInfo("Wild Mongrel", 5, Rarity.COMMON, mage.cards.w.WildMongrel.class));
        cards.add(new SetCardInfo("Windstorm", 25, Rarity.UNCOMMON, mage.cards.w.Windstorm.class));
        cards.add(new SetCardInfo("Wirewood Savage", 6, Rarity.COMMON, mage.cards.w.WirewoodSavage.class));
    }
}
