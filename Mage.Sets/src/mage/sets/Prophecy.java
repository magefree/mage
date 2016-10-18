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
 * @author North
 */
public class Prophecy extends ExpansionSet {

    private static final Prophecy fINSTANCE = new Prophecy();

    public static Prophecy getInstance() {
        return fINSTANCE;
    }

    private Prophecy() {
        super("Prophecy", "PCY", ExpansionSet.buildDate(2000, 4, 27), SetType.EXPANSION);
        this.blockName = "Masques";
        this.parentSet = MercadianMasques.getInstance();
        this.hasBasicLands = false;
        this.hasBoosters = true;
        this.numBoosterLands = 0;
        this.numBoosterCommon = 11;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 0;
        cards.add(new SetCardInfo("Abolish", 1, Rarity.UNCOMMON, mage.cards.a.Abolish.class));
        cards.add(new SetCardInfo("Agent of Shauku", 55, Rarity.COMMON, mage.cards.a.AgentOfShauku.class));
        cards.add(new SetCardInfo("Alexi's Cloak", 29, Rarity.COMMON, mage.cards.a.AlexisCloak.class));
        cards.add(new SetCardInfo("Alexi, Zephyr Mage", 28, Rarity.RARE, mage.cards.a.AlexiZephyrMage.class));
        cards.add(new SetCardInfo("Aura Fracture", 2, Rarity.COMMON, mage.cards.a.AuraFracture.class));
        cards.add(new SetCardInfo("Avatar of Fury", 82, Rarity.RARE, mage.cards.a.AvatarOfFury.class));
        cards.add(new SetCardInfo("Avatar of Hope", 3, Rarity.RARE, mage.cards.a.AvatarOfHope.class));
        cards.add(new SetCardInfo("Avatar of Might", 109, Rarity.RARE, mage.cards.a.AvatarOfMight.class));
        cards.add(new SetCardInfo("Avatar of Will", 30, Rarity.RARE, mage.cards.a.AvatarOfWill.class));
        cards.add(new SetCardInfo("Avatar of Woe", 56, Rarity.RARE, mage.cards.a.AvatarOfWoe.class));
        cards.add(new SetCardInfo("Barbed Field", 83, Rarity.UNCOMMON, mage.cards.b.BarbedField.class));
        cards.add(new SetCardInfo("Blessed Wind", 4, Rarity.RARE, mage.cards.b.BlessedWind.class));
        cards.add(new SetCardInfo("Bog Elemental", 57, Rarity.RARE, mage.cards.b.BogElemental.class));
        cards.add(new SetCardInfo("Bog Glider", 58, Rarity.COMMON, mage.cards.b.BogGlider.class));
        cards.add(new SetCardInfo("Calming Verse", 110, Rarity.COMMON, mage.cards.c.CalmingVerse.class));
        cards.add(new SetCardInfo("Chilling Apparition", 59, Rarity.UNCOMMON, mage.cards.c.ChillingApparition.class));
        cards.add(new SetCardInfo("Chimeric Idol", 136, Rarity.UNCOMMON, mage.cards.c.ChimericIdol.class));
        cards.add(new SetCardInfo("Coastal Hornclaw", 31, Rarity.COMMON, mage.cards.c.CoastalHornclaw.class));
        cards.add(new SetCardInfo("Darba", 111, Rarity.UNCOMMON, mage.cards.d.Darba.class));
        cards.add(new SetCardInfo("Denying Wind", 32, Rarity.RARE, mage.cards.d.DenyingWind.class));
        cards.add(new SetCardInfo("Despoil", 62, Rarity.COMMON, mage.cards.d.Despoil.class));
        cards.add(new SetCardInfo("Devastate", 87, Rarity.COMMON, mage.cards.d.Devastate.class));
        cards.add(new SetCardInfo("Diving Griffin", 6, Rarity.COMMON, mage.cards.d.DivingGriffin.class));
        cards.add(new SetCardInfo("Excavation", 33, Rarity.UNCOMMON, mage.cards.e.Excavation.class));
        cards.add(new SetCardInfo("Fault Riders", 88, Rarity.COMMON, mage.cards.f.FaultRiders.class));
        cards.add(new SetCardInfo("Fen Stalker", 64, Rarity.COMMON, mage.cards.f.FenStalker.class));
        cards.add(new SetCardInfo("Flameshot", 90, Rarity.UNCOMMON, mage.cards.f.Flameshot.class));
        cards.add(new SetCardInfo("Flowering Field", 9, Rarity.UNCOMMON, mage.cards.f.FloweringField.class));
        cards.add(new SetCardInfo("Foil", 34, Rarity.UNCOMMON, mage.cards.f.Foil.class));
        cards.add(new SetCardInfo("Greel's Caress", 67, Rarity.COMMON, mage.cards.g.GreelsCaress.class));
        cards.add(new SetCardInfo("Gulf Squid", 35, Rarity.COMMON, mage.cards.g.GulfSquid.class));
        cards.add(new SetCardInfo("Hazy Homunculus", 36, Rarity.COMMON, mage.cards.h.HazyHomunculus.class));
        cards.add(new SetCardInfo("Heightened Awareness", 37, Rarity.RARE, mage.cards.h.HeightenedAwareness.class));
        cards.add(new SetCardInfo("Infernal Genesis", 68, Rarity.RARE, mage.cards.i.InfernalGenesis.class));
        cards.add(new SetCardInfo("Inflame", 91, Rarity.COMMON, mage.cards.i.Inflame.class));
        cards.add(new SetCardInfo("Jeweled Spirit", 12, Rarity.RARE, mage.cards.j.JeweledSpirit.class));
        cards.add(new SetCardInfo("Jolrael, Empress of Beasts", 115, Rarity.RARE, mage.cards.j.JolraelEmpressOfBeasts.class));
        cards.add(new SetCardInfo("Jolrael's Favor", 116, Rarity.COMMON, mage.cards.j.JolraelsFavor.class));
        cards.add(new SetCardInfo("Keldon Arsonist", 92, Rarity.UNCOMMON, mage.cards.k.KeldonArsonist.class));
        cards.add(new SetCardInfo("Latulla, Keldon Overseer", 95, Rarity.RARE, mage.cards.l.LatullaKeldonOverseer.class));
        cards.add(new SetCardInfo("Lesser Gargadon", 97, Rarity.UNCOMMON, mage.cards.l.LesserGargadon.class));
        cards.add(new SetCardInfo("Living Terrain", 117, Rarity.UNCOMMON, mage.cards.l.LivingTerrain.class));
        cards.add(new SetCardInfo("Mageta's Boon", 14, Rarity.COMMON, mage.cards.m.MagetasBoon.class));
        cards.add(new SetCardInfo("Mageta the Lion", 13, Rarity.RARE, mage.cards.m.MagetaTheLion.class));
        cards.add(new SetCardInfo("Marsh Boa", 118, Rarity.COMMON, mage.cards.m.MarshBoa.class));
        cards.add(new SetCardInfo("Mercenary Informer", 15, Rarity.RARE, mage.cards.m.MercenaryInformer.class));
        cards.add(new SetCardInfo("Mine Bearer", 16, Rarity.COMMON, mage.cards.m.MineBearer.class));
        cards.add(new SetCardInfo("Mungha Wurm", 119, Rarity.RARE, mage.cards.m.MunghaWurm.class));
        cards.add(new SetCardInfo("Nakaya Shade", 69, Rarity.UNCOMMON, mage.cards.n.NakayaShade.class));
        cards.add(new SetCardInfo("Noxious Field", 70, Rarity.UNCOMMON, mage.cards.n.NoxiousField.class));
        cards.add(new SetCardInfo("Outbreak", 71, Rarity.UNCOMMON, mage.cards.o.Outbreak.class));
        cards.add(new SetCardInfo("Overburden", 39, Rarity.RARE, mage.cards.o.Overburden.class));
        cards.add(new SetCardInfo("Panic Attack", 98, Rarity.COMMON, mage.cards.p.PanicAttack.class));
        cards.add(new SetCardInfo("Pit Raptor", 72, Rarity.UNCOMMON, mage.cards.p.PitRaptor.class));
        cards.add(new SetCardInfo("Plague Fiend", 73, Rarity.COMMON, mage.cards.p.PlagueFiend.class));
        cards.add(new SetCardInfo("Plague Wind", 74, Rarity.RARE, mage.cards.p.PlagueWind.class));
        cards.add(new SetCardInfo("Pygmy Razorback", 120, Rarity.COMMON, mage.cards.p.PygmyRazorback.class));
        cards.add(new SetCardInfo("Quicksilver Wall", 41, Rarity.UNCOMMON, mage.cards.q.QuicksilverWall.class));
        cards.add(new SetCardInfo("Rebel Informer", 75, Rarity.RARE, mage.cards.r.RebelInformer.class));
        cards.add(new SetCardInfo("Rethink", 42, Rarity.COMMON, mage.cards.r.Rethink.class));
        cards.add(new SetCardInfo("Rhystic Circle", 19, Rarity.COMMON, mage.cards.r.RhysticCircle.class));
        cards.add(new SetCardInfo("Rhystic Study", 45, Rarity.COMMON, mage.cards.r.RhysticStudy.class));
        cards.add(new SetCardInfo("Rhystic Tutor", 77, Rarity.RARE, mage.cards.r.RhysticTutor.class));
        cards.add(new SetCardInfo("Ribbon Snake", 46, Rarity.COMMON, mage.cards.r.RibbonSnake.class));
        cards.add(new SetCardInfo("Rib Cage Spider", 121, Rarity.COMMON, mage.cards.r.RibCageSpider.class));
        cards.add(new SetCardInfo("Ridgeline Rager", 100, Rarity.COMMON, mage.cards.r.RidgelineRager.class));
        cards.add(new SetCardInfo("Root Cage", 122, Rarity.UNCOMMON, mage.cards.r.RootCage.class));
        cards.add(new SetCardInfo("Scoria Cat", 101, Rarity.UNCOMMON, mage.cards.s.ScoriaCat.class));
        cards.add(new SetCardInfo("Searing Wind", 103, Rarity.RARE, mage.cards.s.SearingWind.class));
        cards.add(new SetCardInfo("Shield Dancer", 23, Rarity.UNCOMMON, mage.cards.s.ShieldDancer.class));
        cards.add(new SetCardInfo("Silt Crawler", 123, Rarity.COMMON, mage.cards.s.SiltCrawler.class));
        cards.add(new SetCardInfo("Spiketail Drake", 48, Rarity.UNCOMMON, mage.cards.s.SpiketailDrake.class));
        cards.add(new SetCardInfo("Spiketail Hatchling", 49, Rarity.COMMON, mage.cards.s.SpiketailHatchling.class));
        cards.add(new SetCardInfo("Spitting Spider", 125, Rarity.UNCOMMON, mage.cards.s.SpittingSpider.class));
        cards.add(new SetCardInfo("Spore Frog", 126, Rarity.COMMON, mage.cards.s.SporeFrog.class));
        cards.add(new SetCardInfo("Spur Grappler", 104, Rarity.COMMON, mage.cards.s.SpurGrappler.class));
        cards.add(new SetCardInfo("Squirrel Wrangler", 127, Rarity.RARE, mage.cards.s.SquirrelWrangler.class));
        cards.add(new SetCardInfo("Steal Strength", 79, Rarity.COMMON, mage.cards.s.StealStrength.class));
        cards.add(new SetCardInfo("Stormwatch Eagle", 50, Rarity.COMMON, mage.cards.s.StormwatchEagle.class));
        cards.add(new SetCardInfo("Sunken Field", 51, Rarity.UNCOMMON, mage.cards.s.SunkenField.class));
        cards.add(new SetCardInfo("Sword Dancer", 25, Rarity.UNCOMMON, mage.cards.s.SwordDancer.class));
        cards.add(new SetCardInfo("Thrive", 129, Rarity.COMMON, mage.cards.t.Thrive.class));
        cards.add(new SetCardInfo("Trenching Steed", 26, Rarity.COMMON, mage.cards.t.TrenchingSteed.class));
        cards.add(new SetCardInfo("Troubled Healer", 27, Rarity.COMMON, mage.cards.t.TroubledHealer.class));
        cards.add(new SetCardInfo("Troublesome Spirit", 52, Rarity.RARE, mage.cards.t.TroublesomeSpirit.class));
        cards.add(new SetCardInfo("Verdant Field", 130, Rarity.UNCOMMON, mage.cards.v.VerdantField.class));
        cards.add(new SetCardInfo("Vintara Elephant", 131, Rarity.COMMON, mage.cards.v.VintaraElephant.class));
        cards.add(new SetCardInfo("Vintara Snapper", 132, Rarity.UNCOMMON, mage.cards.v.VintaraSnapper.class));
        cards.add(new SetCardInfo("Well of Discovery", 140, Rarity.RARE, mage.cards.w.WellOfDiscovery.class));
        cards.add(new SetCardInfo("Well of Life", 141, Rarity.UNCOMMON, mage.cards.w.WellOfLife.class));
        cards.add(new SetCardInfo("Whip Sergeant", 107, Rarity.UNCOMMON, mage.cards.w.WhipSergeant.class));
        cards.add(new SetCardInfo("Whipstitched Zombie", 81, Rarity.COMMON, mage.cards.w.WhipstitchedZombie.class));
        cards.add(new SetCardInfo("Wild Might", 134, Rarity.COMMON, mage.cards.w.WildMight.class));
        cards.add(new SetCardInfo("Wintermoon Mesa", 143, Rarity.RARE, mage.cards.w.WintermoonMesa.class));
        cards.add(new SetCardInfo("Zerapa Minotaur", 108, Rarity.COMMON, mage.cards.z.ZerapaMinotaur.class));
    }
}
