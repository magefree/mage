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
 * Created by IntelliJ IDEA. User: Loki Date: 20.12.10 Time: 21:01
 */
public class Guildpact extends ExpansionSet {
    private static final Guildpact instance = new Guildpact();

    public static Guildpact getInstance() {
        return instance;
    }

    private Guildpact() {
        super("Guildpact", "GPT", ExpansionSet.buildDate(2006, 1, 3), SetType.EXPANSION);
        this.blockName = "Ravnica";
        this.parentSet = RavnicaCityOfGuilds.getInstance();
        this.hasBasicLands = false;
        this.hasBoosters = true;
        this.numBoosterLands = 0;
        this.numBoosterCommon = 11;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 0;
        cards.add(new SetCardInfo("Absolver Thrull", 1, Rarity.COMMON, mage.cards.a.AbsolverThrull.class));
        cards.add(new SetCardInfo("Abyssal Nocturnus", 43, Rarity.RARE, mage.cards.a.AbyssalNocturnus.class));
        cards.add(new SetCardInfo("Agent of Masks", 100, Rarity.UNCOMMON, mage.cards.a.AgentOfMasks.class));
        cards.add(new SetCardInfo("Angel of Despair", 101, Rarity.RARE, mage.cards.a.AngelOfDespair.class));
        cards.add(new SetCardInfo("Battering Wurm", 79, Rarity.UNCOMMON, mage.cards.b.BatteringWurm.class));
        cards.add(new SetCardInfo("Beastmaster's Magemark", 80, Rarity.COMMON, mage.cards.b.BeastmastersMagemark.class));
        cards.add(new SetCardInfo("Belfry Spirit", 2, Rarity.UNCOMMON, mage.cards.b.BelfrySpirit.class));
        cards.add(new SetCardInfo("Benediction of Moons", 3, Rarity.COMMON, mage.cards.b.BenedictionOfMoons.class));
        cards.add(new SetCardInfo("Blind Hunter", 102, Rarity.COMMON, mage.cards.b.BlindHunter.class));
        cards.add(new SetCardInfo("Bloodscale Prowler", 64, Rarity.COMMON, mage.cards.b.BloodscaleProwler.class));
        cards.add(new SetCardInfo("Borborygmos", 103, Rarity.RARE, mage.cards.b.Borborygmos.class));
        cards.add(new SetCardInfo("Burning-Tree Bloodscale", 104, Rarity.COMMON, mage.cards.b.BurningTreeBloodscale.class));
        cards.add(new SetCardInfo("Burning-Tree Shaman", 105, Rarity.RARE, mage.cards.b.BurningTreeShaman.class));
        cards.add(new SetCardInfo("Castigate", 106, Rarity.COMMON, mage.cards.c.Castigate.class));
        cards.add(new SetCardInfo("Caustic Rain", 44, Rarity.UNCOMMON, mage.cards.c.CausticRain.class));
        cards.add(new SetCardInfo("Cerebral Vortex", 107, Rarity.RARE, mage.cards.c.CerebralVortex.class));
        cards.add(new SetCardInfo("Cremate", 45, Rarity.COMMON, mage.cards.c.Cremate.class));
        cards.add(new SetCardInfo("Cry of Contrition", 46, Rarity.COMMON, mage.cards.c.CryOfContrition.class));
        cards.add(new SetCardInfo("Crystal Seer", 23, Rarity.UNCOMMON, mage.cards.c.CrystalSeer.class));
        cards.add(new SetCardInfo("Culling Sun", 109, Rarity.RARE, mage.cards.c.CullingSun.class));
        cards.add(new SetCardInfo("Daggerclaw Imp", 48, Rarity.UNCOMMON, mage.cards.d.DaggerclawImp.class));
        cards.add(new SetCardInfo("Debtors' Knell", 141, Rarity.RARE, mage.cards.d.DebtorsKnell.class));
        cards.add(new SetCardInfo("Djinn Illuminatus", 142, Rarity.RARE, mage.cards.d.DjinnIlluminatus.class));
        cards.add(new SetCardInfo("Douse in Gloom", 49, Rarity.COMMON, mage.cards.d.DouseInGloom.class));
        cards.add(new SetCardInfo("Drowned Rusalka", 24, Rarity.UNCOMMON, mage.cards.d.DrownedRusalka.class));
        cards.add(new SetCardInfo("Dryad Sophisticate", 83, Rarity.UNCOMMON, mage.cards.d.DryadSophisticate.class));
        cards.add(new SetCardInfo("Dune-Brood Nephilim", 110, Rarity.RARE, mage.cards.d.DuneBroodNephilim.class));
        cards.add(new SetCardInfo("Earth Surge", 84, Rarity.RARE, mage.cards.e.EarthSurge.class));
        cards.add(new SetCardInfo("Electrolyze", 111, Rarity.UNCOMMON, mage.cards.e.Electrolyze.class));
        cards.add(new SetCardInfo("Exhumer Thrull", 50, Rarity.UNCOMMON, mage.cards.e.ExhumerThrull.class));
        cards.add(new SetCardInfo("Fencer's Magemark", 65, Rarity.COMMON, mage.cards.f.FencersMagemark.class));
        cards.add(new SetCardInfo("Feral Animist", 112, Rarity.UNCOMMON, mage.cards.f.FeralAnimist.class));
        cards.add(new SetCardInfo("Frazzle", 25, Rarity.UNCOMMON, mage.cards.f.Frazzle.class));
        cards.add(new SetCardInfo("Gatherer of Graces", 85, Rarity.UNCOMMON, mage.cards.g.GathererOfGraces.class));
        cards.add(new SetCardInfo("Gelectrode", 113, Rarity.UNCOMMON, mage.cards.g.Gelectrode.class));
        cards.add(new SetCardInfo("Ghor-Clan Bloodscale", 66, Rarity.UNCOMMON, mage.cards.g.GhorClanBloodscale.class));
        cards.add(new SetCardInfo("Ghor-Clan Savage", 86, Rarity.COMMON, mage.cards.g.GhorClanSavage.class));
        cards.add(new SetCardInfo("Ghost Council of Orzhova", 114, Rarity.RARE, mage.cards.g.GhostCouncilOfOrzhova.class));
        cards.add(new SetCardInfo("Ghost Warden", 5, Rarity.COMMON, mage.cards.g.GhostWarden.class));
        cards.add(new SetCardInfo("Ghostway", 6, Rarity.RARE, mage.cards.g.Ghostway.class));
        cards.add(new SetCardInfo("Giant Solifuge", 143, Rarity.RARE, mage.cards.g.GiantSolifuge.class));
        cards.add(new SetCardInfo("Gigadrowse", 26, Rarity.COMMON, mage.cards.g.Gigadrowse.class));
        cards.add(new SetCardInfo("Glint-Eye Nephilim", 115, Rarity.RARE, mage.cards.g.GlintEyeNephilim.class));
        cards.add(new SetCardInfo("Goblin Flectomancer", 116, Rarity.UNCOMMON, mage.cards.g.GoblinFlectomancer.class));
        cards.add(new SetCardInfo("Godless Shrine", 157, Rarity.RARE, mage.cards.g.GodlessShrine.class));
        cards.add(new SetCardInfo("Graven Dominator", 7, Rarity.RARE, mage.cards.g.GravenDominator.class));
        cards.add(new SetCardInfo("Gristleback", 87, Rarity.UNCOMMON, mage.cards.g.Gristleback.class));
        cards.add(new SetCardInfo("Gruul Guildmage", 144, Rarity.UNCOMMON, mage.cards.g.GruulGuildmage.class));
        cards.add(new SetCardInfo("Gruul Nodorog", 88, Rarity.COMMON, mage.cards.g.GruulNodorog.class));
        cards.add(new SetCardInfo("Gruul Scrapper", 89, Rarity.COMMON, mage.cards.g.GruulScrapper.class));
        cards.add(new SetCardInfo("Gruul Signet", 150, Rarity.COMMON, mage.cards.g.GruulSignet.class));
        cards.add(new SetCardInfo("Gruul Turf", 158, Rarity.COMMON, mage.cards.g.GruulTurf.class));
        cards.add(new SetCardInfo("Gruul War Plow", 151, Rarity.RARE, mage.cards.g.GruulWarPlow.class));
        cards.add(new SetCardInfo("Guardian's Magemark", 8, Rarity.COMMON, mage.cards.g.GuardiansMagemark.class));
        cards.add(new SetCardInfo("Harrier Griffin", 9, Rarity.UNCOMMON, mage.cards.h.HarrierGriffin.class));
        cards.add(new SetCardInfo("Hatching Plans", 27, Rarity.RARE, mage.cards.h.HatchingPlans.class));
        cards.add(new SetCardInfo("Hissing Miasma", 51, Rarity.UNCOMMON, mage.cards.h.HissingMiasma.class));
        cards.add(new SetCardInfo("Hypervolt Grasp", 67, Rarity.UNCOMMON, mage.cards.h.HypervoltGrasp.class));
        cards.add(new SetCardInfo("Infiltrator's Magemark", 28, Rarity.COMMON, mage.cards.i.InfiltratorsMagemark.class));
        cards.add(new SetCardInfo("Ink-Treader Nephilim", 117, Rarity.RARE, mage.cards.i.InkTreaderNephilim.class));
        cards.add(new SetCardInfo("Invoke the Firemind", 118, Rarity.RARE, mage.cards.i.InvokeTheFiremind.class));
        cards.add(new SetCardInfo("Izzet Boilerworks", 159, Rarity.COMMON, mage.cards.i.IzzetBoilerworks.class));
        cards.add(new SetCardInfo("Izzet Chronarch", 119, Rarity.COMMON, mage.cards.i.IzzetChronarch.class));
        cards.add(new SetCardInfo("Izzet Guildmage", 145, Rarity.UNCOMMON, mage.cards.i.IzzetGuildmage.class));
        cards.add(new SetCardInfo("Izzet Signet", 152, Rarity.COMMON, mage.cards.i.IzzetSignet.class));
        cards.add(new SetCardInfo("Leap of Flame", 121, Rarity.COMMON, mage.cards.l.LeapOfFlame.class));
        cards.add(new SetCardInfo("Leyline of Lifeforce", 90, Rarity.RARE, mage.cards.l.LeylineOfLifeforce.class));
        cards.add(new SetCardInfo("Leyline of Lightning", 68, Rarity.RARE, mage.cards.l.LeylineOfLightning.class));
        cards.add(new SetCardInfo("Leyline of Singularity", 29, Rarity.RARE, mage.cards.l.LeylineOfSingularity.class));
        cards.add(new SetCardInfo("Leyline of the Meek", 10, Rarity.RARE, mage.cards.l.LeylineOfTheMeek.class));
        cards.add(new SetCardInfo("Leyline of the Void", 52, Rarity.RARE, mage.cards.l.LeylineOfTheVoid.class));
        cards.add(new SetCardInfo("Lionheart Maverick", 11, Rarity.COMMON, mage.cards.l.LionheartMaverick.class));
        cards.add(new SetCardInfo("Martyred Rusalka", 12, Rarity.UNCOMMON, mage.cards.m.MartyredRusalka.class));
        cards.add(new SetCardInfo("Mizzium Transreliquat", 153, Rarity.RARE, mage.cards.m.MizziumTransreliquat.class));
        cards.add(new SetCardInfo("Mortify", 122, Rarity.UNCOMMON, mage.cards.m.Mortify.class));
        cards.add(new SetCardInfo("Mourning Thrull", 146, Rarity.COMMON, mage.cards.m.MourningThrull.class));
        cards.add(new SetCardInfo("Necromancer's Magemark", 53, Rarity.COMMON, mage.cards.n.NecromancersMagemark.class));
        cards.add(new SetCardInfo("Nivix, Aerie of the Firemind", 160, Rarity.UNCOMMON, mage.cards.n.NivixAerieOfTheFiremind.class));
        cards.add(new SetCardInfo("Niv-Mizzet, the Firemind", 123, Rarity.RARE, mage.cards.n.NivMizzetTheFiremind.class));
        cards.add(new SetCardInfo("Ogre Savant", 70, Rarity.COMMON, mage.cards.o.OgreSavant.class));
        cards.add(new SetCardInfo("Order of the Stars", 13, Rarity.UNCOMMON, mage.cards.o.OrderOfTheStars.class));
        cards.add(new SetCardInfo("Orzhova, the Church of Deals", 162, Rarity.UNCOMMON, mage.cards.o.OrzhovaTheChurchOfDeals.class));
        cards.add(new SetCardInfo("Orzhov Basilica", 161, Rarity.COMMON, mage.cards.o.OrzhovBasilica.class));
        cards.add(new SetCardInfo("Orzhov Euthanist", 54, Rarity.COMMON, mage.cards.o.OrzhovEuthanist.class));
        cards.add(new SetCardInfo("Orzhov Guildmage", 147, Rarity.UNCOMMON, mage.cards.o.OrzhovGuildmage.class));
        cards.add(new SetCardInfo("Orzhov Pontiff", 124, Rarity.RARE, mage.cards.o.OrzhovPontiff.class));
        cards.add(new SetCardInfo("Orzhov Signet", 155, Rarity.COMMON, mage.cards.o.OrzhovSignet.class));
        cards.add(new SetCardInfo("Ostiary Thrull", 55, Rarity.COMMON, mage.cards.o.OstiaryThrull.class));
        cards.add(new SetCardInfo("Parallectric Feedback", 71, Rarity.RARE, mage.cards.p.ParallectricFeedback.class));
        cards.add(new SetCardInfo("Petrahydrox", 148, Rarity.COMMON, mage.cards.p.Petrahydrox.class));
        cards.add(new SetCardInfo("Pillory of the Sleepless", 125, Rarity.COMMON, mage.cards.p.PilloryOfTheSleepless.class));
        cards.add(new SetCardInfo("Plagued Rusalka", 56, Rarity.UNCOMMON, mage.cards.p.PlaguedRusalka.class));
        cards.add(new SetCardInfo("Poisonbelly Ogre", 57, Rarity.COMMON, mage.cards.p.PoisonbellyOgre.class));
        cards.add(new SetCardInfo("Pyromatics", 72, Rarity.COMMON, mage.cards.p.Pyromatics.class));
        cards.add(new SetCardInfo("Quicken", 31, Rarity.RARE, mage.cards.q.Quicken.class));
        cards.add(new SetCardInfo("Rabble-Rouser", 73, Rarity.UNCOMMON, mage.cards.r.RabbleRouser.class));
        cards.add(new SetCardInfo("Repeal", 32, Rarity.COMMON, mage.cards.r.Repeal.class));
        cards.add(new SetCardInfo("Restless Bones", 58, Rarity.COMMON, mage.cards.r.RestlessBones.class));
        cards.add(new SetCardInfo("Revenant Patriarch", 59, Rarity.UNCOMMON, mage.cards.r.RevenantPatriarch.class));
        cards.add(new SetCardInfo("Rumbling Slum", 126, Rarity.RARE, mage.cards.r.RumblingSlum.class));
        cards.add(new SetCardInfo("Runeboggle", 33, Rarity.COMMON, mage.cards.r.Runeboggle.class));
        cards.add(new SetCardInfo("Sanguine Praetor", 60, Rarity.RARE, mage.cards.s.SanguinePraetor.class));
        cards.add(new SetCardInfo("Savage Twister", 127, Rarity.UNCOMMON, mage.cards.s.SavageTwister.class));
        cards.add(new SetCardInfo("Scab-Clan Mauler", 128, Rarity.COMMON, mage.cards.s.ScabClanMauler.class));
        cards.add(new SetCardInfo("Schismotivate", 129, Rarity.UNCOMMON, mage.cards.s.Schismotivate.class));
        cards.add(new SetCardInfo("Scorched Rusalka", 74, Rarity.UNCOMMON, mage.cards.s.ScorchedRusalka.class));
        cards.add(new SetCardInfo("Shadow Lance", 14, Rarity.UNCOMMON, mage.cards.s.ShadowLance.class));
        cards.add(new SetCardInfo("Shattering Spree", 75, Rarity.UNCOMMON, mage.cards.s.ShatteringSpree.class));
        cards.add(new SetCardInfo("Shrieking Grotesque", 15, Rarity.COMMON, mage.cards.s.ShriekingGrotesque.class));
        cards.add(new SetCardInfo("Siege of Towers", 76, Rarity.RARE, mage.cards.s.SiegeOfTowers.class));
        cards.add(new SetCardInfo("Silhana Ledgewalker", 94, Rarity.COMMON, mage.cards.s.SilhanaLedgewalker.class));
        cards.add(new SetCardInfo("Silhana Starfletcher", 95, Rarity.COMMON, mage.cards.s.SilhanaStarfletcher.class));
        cards.add(new SetCardInfo("Skarrgan Firebird", 77, Rarity.RARE, mage.cards.s.SkarrganFirebird.class));
        cards.add(new SetCardInfo("Skarrgan Pit-Skulk", 96, Rarity.COMMON, mage.cards.s.SkarrganPitSkulk.class));
        cards.add(new SetCardInfo("Skarrgan Skybreaker", 130, Rarity.UNCOMMON, mage.cards.s.SkarrganSkybreaker.class));
        cards.add(new SetCardInfo("Skarrg, the Rage Pits", 163, Rarity.UNCOMMON, mage.cards.s.SkarrgTheRagePits.class));
        cards.add(new SetCardInfo("Skeletal Vampire", 62, Rarity.RARE, mage.cards.s.SkeletalVampire.class));
        cards.add(new SetCardInfo("Sky Swallower", 34, Rarity.RARE, mage.cards.s.SkySwallower.class));
        cards.add(new SetCardInfo("Skyrider Trainee", 17, Rarity.COMMON, mage.cards.s.SkyriderTrainee.class));
        cards.add(new SetCardInfo("Smogsteed Rider", 63, Rarity.UNCOMMON, mage.cards.s.SmogsteedRider.class));
        cards.add(new SetCardInfo("Souls of the Faultless", 131, Rarity.UNCOMMON, mage.cards.s.SoulsOfTheFaultless.class));
        cards.add(new SetCardInfo("Spelltithe Enforcer", 18, Rarity.RARE, mage.cards.s.SpelltitheEnforcer.class));
        cards.add(new SetCardInfo("Starved Rusalka", 97, Rarity.UNCOMMON, mage.cards.s.StarvedRusalka.class));
        cards.add(new SetCardInfo("Steamcore Weird", 35, Rarity.COMMON, mage.cards.s.SteamcoreWeird.class));
        cards.add(new SetCardInfo("Steam Vents", 164, Rarity.RARE, mage.cards.s.SteamVents.class));
        cards.add(new SetCardInfo("Stitch in Time", 132, Rarity.RARE, mage.cards.s.StitchInTime.class));
        cards.add(new SetCardInfo("Stomping Ground", 165, Rarity.RARE, mage.cards.s.StompingGround.class));
        cards.add(new SetCardInfo("Storm Herd", 19, Rarity.RARE, mage.cards.s.StormHerd.class));
        cards.add(new SetCardInfo("Stratozeppelid", 36, Rarity.UNCOMMON, mage.cards.s.Stratozeppelid.class));
        cards.add(new SetCardInfo("Streetbreaker Wurm", 133, Rarity.COMMON, mage.cards.s.StreetbreakerWurm.class));
        cards.add(new SetCardInfo("Sword of the Paruns", 156, Rarity.RARE, mage.cards.s.SwordOfTheParuns.class));
        cards.add(new SetCardInfo("Teysa, Orzhov Scion", 134, Rarity.RARE, mage.cards.t.TeysaOrzhovScion.class));
        cards.add(new SetCardInfo("Thunderheads", 37, Rarity.UNCOMMON, mage.cards.t.Thunderheads.class));
        cards.add(new SetCardInfo("Tibor and Lumia", 135, Rarity.RARE, mage.cards.t.TiborAndLumia.class));
        cards.add(new SetCardInfo("Tin Street Hooligan", 78, Rarity.COMMON, mage.cards.t.TinStreetHooligan.class));
        cards.add(new SetCardInfo("To Arms!", 20, Rarity.UNCOMMON, mage.cards.t.ToArms.class));
        cards.add(new SetCardInfo("Torch Drake", 38, Rarity.COMMON, mage.cards.t.TorchDrake.class));
        cards.add(new SetCardInfo("Train of Thought", 39, Rarity.COMMON, mage.cards.t.TrainOfThought.class));
        cards.add(new SetCardInfo("Ulasht, the Hate Seed", 136, Rarity.RARE, mage.cards.u.UlashtTheHateSeed.class));
        cards.add(new SetCardInfo("Vacuumelt", 40, Rarity.UNCOMMON, mage.cards.v.Vacuumelt.class));
        cards.add(new SetCardInfo("Vedalken Plotter", 41, Rarity.UNCOMMON, mage.cards.v.VedalkenPlotter.class));
        cards.add(new SetCardInfo("Vertigo Spawn", 42, Rarity.UNCOMMON, mage.cards.v.VertigoSpawn.class));
        cards.add(new SetCardInfo("Wee Dragonauts", 137, Rarity.COMMON, mage.cards.w.WeeDragonauts.class));
        cards.add(new SetCardInfo("Wild Cantor", 149, Rarity.COMMON, mage.cards.w.WildCantor.class));
        cards.add(new SetCardInfo("Wildsize", 98, Rarity.COMMON, mage.cards.w.Wildsize.class));
        cards.add(new SetCardInfo("Witch-Maw Nephilim", 138, Rarity.RARE, mage.cards.w.WitchMawNephilim.class));
        cards.add(new SetCardInfo("Withstand", 21, Rarity.COMMON, mage.cards.w.Withstand.class));
        cards.add(new SetCardInfo("Wreak Havoc", 139, Rarity.UNCOMMON, mage.cards.w.WreakHavoc.class));
        cards.add(new SetCardInfo("Wurmweaver Coil", 99, Rarity.RARE, mage.cards.w.WurmweaverCoil.class));
        cards.add(new SetCardInfo("Yore-Tiller Nephilim", 140, Rarity.RARE, mage.cards.y.YoreTillerNephilim.class));
    }
}
