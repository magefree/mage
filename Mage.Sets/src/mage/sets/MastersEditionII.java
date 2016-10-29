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
import mage.cards.a.ArmorThrull;
import mage.cards.b.BrassclawOrcs;
import mage.cards.c.CombatMedic;
import mage.cards.e.ElvishHunter;
import mage.cards.f.FarrelsZealot;
import mage.cards.g.GorillaShaman;
import mage.cards.i.IcatianJavelineers;
import mage.cards.i.IcatianScout;
import mage.cards.l.LimDulsHighGuard;
import mage.cards.n.Necrite;
import mage.cards.n.NightSoil;
import mage.cards.o.OrcishVeteran;
import mage.constants.SetType;
import mage.constants.Rarity;

/**
 *
 * @author LevelX2
 */
public class MastersEditionII extends ExpansionSet {

    private static final MastersEditionII fINSTANCE = new MastersEditionII();

    public static MastersEditionII getInstance() {
        return fINSTANCE;
    }

    private MastersEditionII() {
        super("Masters Edition II", "ME2", ExpansionSet.buildDate(2008, 9, 22), SetType.MAGIC_ONLINE);
        this.hasBasicLands = false;
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 0;
        cards.add(new SetCardInfo("Abbey Gargoyles", 1, Rarity.UNCOMMON, mage.cards.a.AbbeyGargoyles.class));
        cards.add(new SetCardInfo("Adarkar Sentinel", 201, Rarity.COMMON, mage.cards.a.AdarkarSentinel.class));
        cards.add(new SetCardInfo("Aeolipile", 202, Rarity.COMMON, mage.cards.a.Aeolipile.class));
        cards.add(new SetCardInfo("Ambush Party", 115, Rarity.COMMON, mage.cards.a.AmbushParty.class));
        cards.add(new SetCardInfo("Anarchy", 116, Rarity.RARE, mage.cards.a.Anarchy.class));
        cards.add(new SetCardInfo("Angel of Fury", 2, Rarity.RARE, mage.cards.a.AngelOfFury.class));
        cards.add(new SetCardInfo("Angel of Light", 3, Rarity.UNCOMMON, mage.cards.a.AngelOfLight.class));
        cards.add(new SetCardInfo("Armored Griffin", 5, Rarity.COMMON, mage.cards.a.ArmoredGriffin.class));
        cards.add(new SetCardInfo("Armor of Faith", 4, Rarity.COMMON, mage.cards.a.ArmorOfFaith.class));
        cards.add(new SetCardInfo("Armor Thrull", 77, Rarity.COMMON, ArmorThrull.class));
        cards.add(new SetCardInfo("Aurochs", 153, Rarity.COMMON, mage.cards.a.Aurochs.class));
        cards.add(new SetCardInfo("Aysen Bureaucrats", 6, Rarity.COMMON, mage.cards.a.AysenBureaucrats.class));
        cards.add(new SetCardInfo("Badlands", 225, Rarity.RARE, mage.cards.b.Badlands.class));
        cards.add(new SetCardInfo("Balduvian Dead", 79, Rarity.UNCOMMON, mage.cards.b.BalduvianDead.class));
        cards.add(new SetCardInfo("Balduvian Trading Post", 226, Rarity.RARE, mage.cards.b.BalduvianTradingPost.class));
        cards.add(new SetCardInfo("Barbed Sextant", 204, Rarity.COMMON, mage.cards.b.BarbedSextant.class));
        cards.add(new SetCardInfo("Binding Grasp", 41, Rarity.RARE, mage.cards.b.BindingGrasp.class));
        cards.add(new SetCardInfo("Bounty of the Hunt", 154, Rarity.RARE, mage.cards.b.BountyOfTheHunt.class));
        cards.add(new SetCardInfo("Brainstorm", 42, Rarity.COMMON, mage.cards.b.Brainstorm.class));
        cards.add(new SetCardInfo("Brassclaw Orcs", 119, Rarity.COMMON, BrassclawOrcs.class));
        cards.add(new SetCardInfo("Brimstone Dragon", 120, Rarity.RARE, mage.cards.b.BrimstoneDragon.class));
        cards.add(new SetCardInfo("Browse", 43, Rarity.UNCOMMON, mage.cards.b.Browse.class));
        cards.add(new SetCardInfo("Burnout", 121, Rarity.UNCOMMON, mage.cards.b.Burnout.class));
        cards.add(new SetCardInfo("Caribou Range", 8, Rarity.RARE, mage.cards.c.CaribouRange.class));
        cards.add(new SetCardInfo("Combat Medic", 9, Rarity.COMMON, CombatMedic.class));
        cards.add(new SetCardInfo("Conquer", 122, Rarity.UNCOMMON, mage.cards.c.Conquer.class));
        cards.add(new SetCardInfo("Counterspell", 44, Rarity.UNCOMMON, mage.cards.c.Counterspell.class));
        cards.add(new SetCardInfo("Dance of the Dead", 83, Rarity.UNCOMMON, mage.cards.d.DanceOfTheDead.class));
        cards.add(new SetCardInfo("Dark Banishing", 84, Rarity.COMMON, mage.cards.d.DarkBanishing.class));
        cards.add(new SetCardInfo("Deep Spawn", 45, Rarity.RARE, mage.cards.d.DeepSpawn.class));
        cards.add(new SetCardInfo("Demonic Consultation", 85, Rarity.UNCOMMON, mage.cards.d.DemonicConsultation.class));
        cards.add(new SetCardInfo("Despotic Scepter", 206, Rarity.RARE, mage.cards.d.DespoticScepter.class));
        cards.add(new SetCardInfo("Disenchant", 10, Rarity.COMMON, mage.cards.d.Disenchant.class));
        cards.add(new SetCardInfo("Dwarven Ruins", 227, Rarity.UNCOMMON, mage.cards.d.DwarvenRuins.class));
        cards.add(new SetCardInfo("Dystopia", 88, Rarity.RARE, mage.cards.d.Dystopia.class));
        cards.add(new SetCardInfo("Ebon Praetor", 89, Rarity.RARE, mage.cards.e.EbonPraetor.class));
        cards.add(new SetCardInfo("Ebon Stronghold", 228, Rarity.UNCOMMON, mage.cards.e.EbonStronghold.class));
        cards.add(new SetCardInfo("Elemental Augury", 193, Rarity.RARE, mage.cards.e.ElementalAugury.class));
        cards.add(new SetCardInfo("Elven Lyre", 208, Rarity.COMMON, mage.cards.e.ElvenLyre.class));
        cards.add(new SetCardInfo("Elvish Farmer", 156, Rarity.RARE, mage.cards.e.ElvishFarmer.class));
        cards.add(new SetCardInfo("Elvish Hunter", 157, Rarity.COMMON, ElvishHunter.class));
        cards.add(new SetCardInfo("Elvish Ranger", 158, Rarity.COMMON, mage.cards.e.ElvishRanger.class));
        cards.add(new SetCardInfo("Elvish Spirit Guide", 159, Rarity.UNCOMMON, mage.cards.e.ElvishSpiritGuide.class));
        cards.add(new SetCardInfo("Enervate", 47, Rarity.COMMON, mage.cards.e.Enervate.class));
        cards.add(new SetCardInfo("Errantry", 124, Rarity.COMMON, mage.cards.e.Errantry.class));
        cards.add(new SetCardInfo("Farrel's Mantle", 13, Rarity.UNCOMMON, mage.cards.f.FarrelsMantle.class));
        cards.add(new SetCardInfo("Farrel's Zealot", 14, Rarity.UNCOMMON, FarrelsZealot.class));
        cards.add(new SetCardInfo("Feral Thallid", 161, Rarity.COMMON, mage.cards.f.FeralThallid.class));
        cards.add(new SetCardInfo("Fire Dragon", 125, Rarity.RARE, mage.cards.f.FireDragon.class));
        cards.add(new SetCardInfo("Flame Spirit", 126, Rarity.UNCOMMON, mage.cards.f.FlameSpirit.class));
        cards.add(new SetCardInfo("Folk of the Pines", 162, Rarity.COMMON, mage.cards.f.FolkOfThePines.class));
        cards.add(new SetCardInfo("Forbidden Lore", 163, Rarity.UNCOMMON, mage.cards.f.ForbiddenLore.class));
        cards.add(new SetCardInfo("Forgotten Lore", 164, Rarity.UNCOMMON, mage.cards.f.ForgottenLore.class));
        cards.add(new SetCardInfo("Foul Familiar", 90, Rarity.COMMON, mage.cards.f.FoulFamiliar.class));
        cards.add(new SetCardInfo("Fumarole", 194, Rarity.UNCOMMON, mage.cards.f.Fumarole.class));
        cards.add(new SetCardInfo("Fungal Bloom", 165, Rarity.RARE, mage.cards.f.FungalBloom.class));
        cards.add(new SetCardInfo("Giant Growth", 167, Rarity.COMMON, mage.cards.g.GiantGrowth.class));
        cards.add(new SetCardInfo("Glacial Chasm", 229, Rarity.RARE, mage.cards.g.GlacialChasm.class));
        cards.add(new SetCardInfo("Glacial Crevasses", 127, Rarity.RARE, mage.cards.g.GlacialCrevasses.class));
        cards.add(new SetCardInfo("Gorilla Shaman", 129, Rarity.UNCOMMON, GorillaShaman.class));
        cards.add(new SetCardInfo("Grandmother Sengir", 93, Rarity.RARE, mage.cards.g.GrandmotherSengir.class));
        cards.add(new SetCardInfo("Havenwood Battleground", 230, Rarity.UNCOMMON, mage.cards.h.HavenwoodBattleground.class));
        cards.add(new SetCardInfo("Heart of Yavimaya", 231, Rarity.RARE, mage.cards.h.HeartOfYavimaya.class));
        cards.add(new SetCardInfo("Helm of Obedience", 210, Rarity.RARE, mage.cards.h.HelmOfObedience.class));
        cards.add(new SetCardInfo("Icatian Javelineers", 15, Rarity.COMMON, IcatianJavelineers.class));
        cards.add(new SetCardInfo("Icatian Scout", 17, Rarity.COMMON, IcatianScout.class));
        cards.add(new SetCardInfo("Icequake", 94, Rarity.COMMON, mage.cards.i.Icequake.class));
        cards.add(new SetCardInfo("Icy Prison", 50, Rarity.COMMON, mage.cards.i.IcyPrison.class));
        cards.add(new SetCardInfo("Ihsan's Shade", 95, Rarity.RARE, mage.cards.i.IhsansShade.class));
        cards.add(new SetCardInfo("Imperial Recruiter", 130, Rarity.RARE, mage.cards.i.ImperialRecruiter.class));
        cards.add(new SetCardInfo("Imperial Seal", 96, Rarity.RARE, mage.cards.i.ImperialSeal.class));
        cards.add(new SetCardInfo("Incinerate", 131, Rarity.COMMON, mage.cards.i.Incinerate.class));
        cards.add(new SetCardInfo("Inheritance", 18, Rarity.UNCOMMON, mage.cards.i.Inheritance.class));
        cards.add(new SetCardInfo("Ironclaw Orcs", 132, Rarity.COMMON, mage.cards.i.IronclawOrcs.class));
        cards.add(new SetCardInfo("Jester's Mask", 211, Rarity.RARE, mage.cards.j.JestersMask.class));
        cards.add(new SetCardInfo("Johtull Wurm", 168, Rarity.UNCOMMON, mage.cards.j.JohtullWurm.class));
        cards.add(new SetCardInfo("Juniper Order Advocate", 20, Rarity.UNCOMMON, mage.cards.j.JuniperOrderAdvocate.class));
        cards.add(new SetCardInfo("Kaysa", 170, Rarity.RARE, mage.cards.k.Kaysa.class));
        cards.add(new SetCardInfo("Kjeldoran Dead", 98, Rarity.COMMON, mage.cards.k.KjeldoranDead.class));
        cards.add(new SetCardInfo("Kjeldoran Outpost", 233, Rarity.RARE, mage.cards.k.KjeldoranOutpost.class));
        cards.add(new SetCardInfo("Knight of Stromgald", 99, Rarity.UNCOMMON, mage.cards.k.KnightOfStromgald.class));
        cards.add(new SetCardInfo("Krovikan Fetish", 100, Rarity.COMMON, mage.cards.k.KrovikanFetish.class));
        cards.add(new SetCardInfo("Krovikan Sorcerer", 51, Rarity.COMMON, mage.cards.k.KrovikanSorcerer.class));
        cards.add(new SetCardInfo("Leaping Lizard", 171, Rarity.COMMON, mage.cards.l.LeapingLizard.class));
        cards.add(new SetCardInfo("Lim-Dul's High Guard", 103, Rarity.UNCOMMON, LimDulsHighGuard.class));
        cards.add(new SetCardInfo("Magus of the Unseen", 53, Rarity.RARE, mage.cards.m.MagusOfTheUnseen.class));
        cards.add(new SetCardInfo("Mana Crypt", 214, Rarity.RARE, mage.cards.m.ManaCrypt.class));
        cards.add(new SetCardInfo("Marjhan", 54, Rarity.RARE, mage.cards.m.Marjhan.class));
        cards.add(new SetCardInfo("Mesmeric Trance", 55, Rarity.RARE, mage.cards.m.MesmericTrance.class));
        cards.add(new SetCardInfo("Narwhal", 57, Rarity.UNCOMMON, mage.cards.n.Narwhal.class));
        cards.add(new SetCardInfo("Necrite", 106, Rarity.COMMON, Necrite.class));
        cards.add(new SetCardInfo("Necropotence", 107, Rarity.RARE, mage.cards.n.Necropotence.class));
        cards.add(new SetCardInfo("Night Soil", 173, Rarity.UNCOMMON, NightSoil.class));
        cards.add(new SetCardInfo("Orc General", 137, Rarity.UNCOMMON, mage.cards.o.OrcGeneral.class));
        cards.add(new SetCardInfo("Orcish Cannoneers", 138, Rarity.UNCOMMON, mage.cards.o.OrcishCannoneers.class));
        cards.add(new SetCardInfo("Orcish Captain", 139, Rarity.UNCOMMON, mage.cards.o.OrcishCaptain.class));
        cards.add(new SetCardInfo("Orcish Lumberjack", 142, Rarity.COMMON, mage.cards.o.OrcishLumberjack.class));
        cards.add(new SetCardInfo("Orcish Veteran", 144, Rarity.COMMON, OrcishVeteran.class));
        cards.add(new SetCardInfo("Order of the Sacred Torch", 25, Rarity.RARE, mage.cards.o.OrderOfTheSacredTorch.class));
        cards.add(new SetCardInfo("Order of the White Shield", 26, Rarity.UNCOMMON, mage.cards.o.OrderOfTheWhiteShield.class));
        cards.add(new SetCardInfo("Panic", 145, Rarity.COMMON, mage.cards.p.Panic.class));
        cards.add(new SetCardInfo("Personal Tutor", 58, Rarity.UNCOMMON, mage.cards.p.PersonalTutor.class));
        cards.add(new SetCardInfo("Phyrexian Devourer", 216, Rarity.UNCOMMON, mage.cards.p.PhyrexianDevourer.class));
        cards.add(new SetCardInfo("Pillage", 146, Rarity.UNCOMMON, mage.cards.p.Pillage.class));
        cards.add(new SetCardInfo("Portent", 60, Rarity.COMMON, mage.cards.p.Portent.class));
        cards.add(new SetCardInfo("Pyrokinesis", 147, Rarity.RARE, mage.cards.p.Pyrokinesis.class));
        cards.add(new SetCardInfo("Ravages of War", 27, Rarity.RARE, mage.cards.r.RavagesOfWar.class));
        cards.add(new SetCardInfo("Ray of Command", 61, Rarity.UNCOMMON, mage.cards.r.RayOfCommand.class));
        cards.add(new SetCardInfo("Red Cliffs Armada", 62, Rarity.COMMON, mage.cards.r.RedCliffsArmada.class));
        cards.add(new SetCardInfo("Reinforcements", 28, Rarity.COMMON, mage.cards.r.Reinforcements.class));
        cards.add(new SetCardInfo("Reprisal", 29, Rarity.COMMON, mage.cards.r.Reprisal.class));
        cards.add(new SetCardInfo("Righteous Fury", 30, Rarity.RARE, mage.cards.r.RighteousFury.class));
        cards.add(new SetCardInfo("Ritual of the Machine", 109, Rarity.RARE, mage.cards.r.RitualOfTheMachine.class));
        cards.add(new SetCardInfo("Roterothopter", 218, Rarity.COMMON, mage.cards.r.Roterothopter.class));
        cards.add(new SetCardInfo("Royal Trooper", 32, Rarity.COMMON, mage.cards.r.RoyalTrooper.class));
        cards.add(new SetCardInfo("Ruins of Trokair", 234, Rarity.UNCOMMON, mage.cards.r.RuinsOfTrokair.class));
        cards.add(new SetCardInfo("Savannah", 235, Rarity.RARE, mage.cards.s.Savannah.class));
        cards.add(new SetCardInfo("Screeching Drake", 63, Rarity.COMMON, mage.cards.s.ScreechingDrake.class));
        cards.add(new SetCardInfo("Sea Drake", 64, Rarity.RARE, mage.cards.s.SeaDrake.class));
        cards.add(new SetCardInfo("Sea Spirit", 65, Rarity.UNCOMMON, mage.cards.s.SeaSpirit.class));
        cards.add(new SetCardInfo("Shrink", 175, Rarity.COMMON, mage.cards.s.Shrink.class));
        cards.add(new SetCardInfo("Sibilant Spirit", 67, Rarity.RARE, mage.cards.s.SibilantSpirit.class));
        cards.add(new SetCardInfo("Skeleton Ship", 197, Rarity.RARE, mage.cards.s.SkeletonShip.class));
        cards.add(new SetCardInfo("Skull Catapult", 219, Rarity.UNCOMMON, mage.cards.s.SkullCatapult.class));
        cards.add(new SetCardInfo("Snow-Covered Forest", 245, Rarity.LAND, mage.cards.s.SnowCoveredForest.class));
        cards.add(new SetCardInfo("Snow-Covered Island", 242, Rarity.LAND, mage.cards.s.SnowCoveredIsland.class));
        cards.add(new SetCardInfo("Snow-Covered Mountain", 244, Rarity.LAND, mage.cards.s.SnowCoveredMountain.class));
        cards.add(new SetCardInfo("Snow-Covered Plains", 241, Rarity.LAND, mage.cards.s.SnowCoveredPlains.class));
        cards.add(new SetCardInfo("Snow-Covered Swamp", 243, Rarity.LAND, mage.cards.s.SnowCoveredSwamp.class));
        cards.add(new SetCardInfo("Soldevi Digger", 221, Rarity.UNCOMMON, mage.cards.s.SoldeviDigger.class));
        cards.add(new SetCardInfo("Soldevi Excavations", 236, Rarity.RARE, mage.cards.s.SoldeviExcavations.class));
        cards.add(new SetCardInfo("Soldevi Simulacrum", 222, Rarity.UNCOMMON, mage.cards.s.SoldeviSimulacrum.class));
        cards.add(new SetCardInfo("Songs of the Damned", 110, Rarity.COMMON, mage.cards.s.SongsOfTheDamned.class));
        cards.add(new SetCardInfo("Soul Exchange", 111, Rarity.UNCOMMON, mage.cards.s.SoulExchange.class));
        cards.add(new SetCardInfo("Soul Kiss", 112, Rarity.UNCOMMON, mage.cards.s.SoulKiss.class));
        cards.add(new SetCardInfo("Spore Flower", 177, Rarity.UNCOMMON, mage.cards.s.SporeFlower.class));
        cards.add(new SetCardInfo("Stampede", 178, Rarity.UNCOMMON, mage.cards.s.Stampede.class));
        cards.add(new SetCardInfo("Stonehands", 151, Rarity.COMMON, mage.cards.s.Stonehands.class));
        cards.add(new SetCardInfo("Stone Spirit", 150, Rarity.UNCOMMON, mage.cards.s.StoneSpirit.class));
        cards.add(new SetCardInfo("Storm Spirit", 198, Rarity.RARE, mage.cards.s.StormSpirit.class));
        cards.add(new SetCardInfo("Stromgald Cabal", 113, Rarity.RARE, mage.cards.s.StromgaldCabal.class));
        cards.add(new SetCardInfo("Stunted Growth", 179, Rarity.RARE, mage.cards.s.StuntedGrowth.class));
        cards.add(new SetCardInfo("Sustaining Spirit", 36, Rarity.RARE, mage.cards.s.SustainingSpirit.class));
        cards.add(new SetCardInfo("Svyelunite Temple", 237, Rarity.UNCOMMON, mage.cards.s.SvyeluniteTemple.class));
        cards.add(new SetCardInfo("Swords to Plowshares", 37, Rarity.UNCOMMON, mage.cards.s.SwordsToPlowshares.class));
        cards.add(new SetCardInfo("Taiga", 238, Rarity.RARE, mage.cards.t.Taiga.class));
        cards.add(new SetCardInfo("Temporal Manipulation", 69, Rarity.RARE, mage.cards.t.TemporalManipulation.class));
        cards.add(new SetCardInfo("Thallid", 180, Rarity.COMMON, mage.cards.t.Thallid.class));
        cards.add(new SetCardInfo("Thallid Devourer", 181, Rarity.COMMON, mage.cards.t.ThallidDevourer.class));
        cards.add(new SetCardInfo("Thelonite Druid", 182, Rarity.RARE, mage.cards.t.TheloniteDruid.class));
        cards.add(new SetCardInfo("Thermokarst", 183, Rarity.COMMON, mage.cards.t.Thermokarst.class));
        cards.add(new SetCardInfo("Thought Lash", 70, Rarity.RARE, mage.cards.t.ThoughtLash.class));
        cards.add(new SetCardInfo("Thunder Wall", 71, Rarity.UNCOMMON, mage.cards.t.ThunderWall.class));
        cards.add(new SetCardInfo("Time Bomb", 223, Rarity.RARE, mage.cards.t.TimeBomb.class));
        cards.add(new SetCardInfo("Tinder Wall", 184, Rarity.COMMON, mage.cards.t.TinderWall.class));
        cards.add(new SetCardInfo("Tundra", 239, Rarity.RARE, mage.cards.t.Tundra.class));
        cards.add(new SetCardInfo("Underground Sea", 240, Rarity.RARE, mage.cards.u.UndergroundSea.class));
        cards.add(new SetCardInfo("Viscerid Armor", 72, Rarity.COMMON, mage.cards.v.VisceridArmor.class));
        cards.add(new SetCardInfo("Wall of Kelp", 74, Rarity.COMMON, mage.cards.w.WallOfKelp.class));
        cards.add(new SetCardInfo("Warning", 38, Rarity.COMMON, mage.cards.w.Warning.class));
        cards.add(new SetCardInfo("Whiteout", 185, Rarity.COMMON, mage.cards.w.Whiteout.class));
        cards.add(new SetCardInfo("Wind Spirit", 75, Rarity.UNCOMMON, mage.cards.w.WindSpirit.class));
        cards.add(new SetCardInfo("Wings of Aesthir", 199, Rarity.UNCOMMON, mage.cards.w.WingsOfAesthir.class));
        cards.add(new SetCardInfo("Wolf Pack", 187, Rarity.RARE, mage.cards.w.WolfPack.class));
        cards.add(new SetCardInfo("Yavimaya Ancients", 190, Rarity.UNCOMMON, mage.cards.y.YavimayaAncients.class));
        cards.add(new SetCardInfo("Zuran Spellcaster", 76, Rarity.COMMON, mage.cards.z.ZuranSpellcaster.class));
    }

}