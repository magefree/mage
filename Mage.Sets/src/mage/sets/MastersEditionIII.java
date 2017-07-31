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

import mage.cards.CardGraphicInfo;
import mage.cards.ExpansionSet;
import mage.cards.f.FeveredStrength;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author LevelX2
 */
public class MastersEditionIII extends ExpansionSet {

    private static final MastersEditionIII instance = new MastersEditionIII();

    public static MastersEditionIII getInstance() {
        return instance;
    }

    private MastersEditionIII() {
        super("Masters Edition III", "ME3", ExpansionSet.buildDate(2009, 9, 7), SetType.MAGIC_ONLINE);
        this.hasBasicLands = true;
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 0;
        cards.add(new SetCardInfo("Active Volcano", 85, Rarity.UNCOMMON, mage.cards.a.ActiveVolcano.class));
        cards.add(new SetCardInfo("Akron Legionnaire", 1, Rarity.RARE, mage.cards.a.AkronLegionnaire.class));
        cards.add(new SetCardInfo("All Hallow's Eve", 57, Rarity.RARE, mage.cards.a.AllHallowsEve.class));
        cards.add(new SetCardInfo("Amrou Kithkin", 3, Rarity.COMMON, mage.cards.a.AmrouKithkin.class));
        cards.add(new SetCardInfo("Anaba Ancestor", 86, Rarity.COMMON, mage.cards.a.AnabaAncestor.class));
        cards.add(new SetCardInfo("Anaba Spirit Crafter", 87, Rarity.COMMON, mage.cards.a.AnabaSpiritCrafter.class));
        cards.add(new SetCardInfo("Angus Mackenzie", 141, Rarity.RARE, mage.cards.a.AngusMackenzie.class));
        cards.add(new SetCardInfo("Arcades Sabboth", 142, Rarity.RARE, mage.cards.a.ArcadesSabboth.class));
        cards.add(new SetCardInfo("Arena of the Ancients", 188, Rarity.RARE, mage.cards.a.ArenaOfTheAncients.class));
        cards.add(new SetCardInfo("Ashes to Ashes", 58, Rarity.UNCOMMON, mage.cards.a.AshesToAshes.class));
        cards.add(new SetCardInfo("Astrolabe", 189, Rarity.COMMON, mage.cards.a.Astrolabe.class));
        cards.add(new SetCardInfo("Barktooth Warbeard", 144, Rarity.COMMON, mage.cards.b.BarktoothWarbeard.class));
        cards.add(new SetCardInfo("Barl's Cage", 190, Rarity.RARE, mage.cards.b.BarlsCage.class));
        cards.add(new SetCardInfo("Bartel Runeaxe", 145, Rarity.UNCOMMON, mage.cards.b.BartelRuneaxe.class));
        cards.add(new SetCardInfo("Bayou", 204, Rarity.RARE, mage.cards.b.Bayou.class));
        cards.add(new SetCardInfo("Bazaar of Baghdad", 205, Rarity.RARE, mage.cards.b.BazaarOfBaghdad.class));
        cards.add(new SetCardInfo("Black Vise", 191, Rarity.RARE, mage.cards.b.BlackVise.class));
        cards.add(new SetCardInfo("Blood Lust", 88, Rarity.COMMON, mage.cards.b.BloodLust.class));
        cards.add(new SetCardInfo("Bone Flute", 192, Rarity.COMMON, mage.cards.b.BoneFlute.class));
        cards.add(new SetCardInfo("Boomerang", 30, Rarity.COMMON, mage.cards.b.Boomerang.class));
        cards.add(new SetCardInfo("Boris Devilboon", 146, Rarity.UNCOMMON, mage.cards.b.BorisDevilboon.class));
        cards.add(new SetCardInfo("Borrowing 100,000 Arrows", 31, Rarity.UNCOMMON, mage.cards.b.Borrowing100000Arrows.class));
        cards.add(new SetCardInfo("Brilliant Plan", 32, Rarity.COMMON, mage.cards.b.BrilliantPlan.class));
        cards.add(new SetCardInfo("Burning of Xinye", 89, Rarity.RARE, mage.cards.b.BurningOfXinye.class));
        cards.add(new SetCardInfo("Capture of Jingzhou", 33, Rarity.RARE, mage.cards.c.CaptureOfJingzhou.class));
        cards.add(new SetCardInfo("Carrion Ants", 60, Rarity.UNCOMMON, mage.cards.c.CarrionAnts.class));
        cards.add(new SetCardInfo("Chain Lightning", 90, Rarity.COMMON, mage.cards.c.ChainLightning.class));
        cards.add(new SetCardInfo("Chromium", 147, Rarity.RARE, mage.cards.c.Chromium.class));
        cards.add(new SetCardInfo("Cinder Storm", 91, Rarity.UNCOMMON, mage.cards.c.CinderStorm.class));
        cards.add(new SetCardInfo("City of Shadows", 206, Rarity.RARE, mage.cards.c.CityOfShadows.class));
        cards.add(new SetCardInfo("Cleanse", 5, Rarity.RARE, mage.cards.c.Cleanse.class));
        cards.add(new SetCardInfo("Coal Golem", 193, Rarity.COMMON, mage.cards.c.CoalGolem.class));
        cards.add(new SetCardInfo("Concordant Crossroads", 114, Rarity.RARE, mage.cards.c.ConcordantCrossroads.class));
        cards.add(new SetCardInfo("Corrupt Eunuchs", 92, Rarity.UNCOMMON, mage.cards.c.CorruptEunuchs.class));
        cards.add(new SetCardInfo("Cosmic Horror", 61, Rarity.RARE, mage.cards.c.CosmicHorror.class));
        cards.add(new SetCardInfo("Crimson Kobolds", 93, Rarity.COMMON, mage.cards.c.CrimsonKobolds.class));
        cards.add(new SetCardInfo("Crimson Manticore", 94, Rarity.UNCOMMON, mage.cards.c.CrimsonManticore.class));
        cards.add(new SetCardInfo("Dance of Many", 34, Rarity.UNCOMMON, mage.cards.d.DanceOfMany.class));
        cards.add(new SetCardInfo("D'Avenant Archer", 6, Rarity.COMMON, mage.cards.d.DAvenantArcher.class));
        cards.add(new SetCardInfo("Demonic Torment", 62, Rarity.COMMON, mage.cards.d.DemonicTorment.class));
        cards.add(new SetCardInfo("Desert Twister", 115, Rarity.UNCOMMON, mage.cards.d.DesertTwister.class));
        cards.add(new SetCardInfo("Desperate Charge", 63, Rarity.COMMON, mage.cards.d.DesperateCharge.class));
        cards.add(new SetCardInfo("Didgeridoo", 194, Rarity.UNCOMMON, mage.cards.d.Didgeridoo.class));
        cards.add(new SetCardInfo("Disenchant", 7, Rarity.COMMON, mage.cards.d.Disenchant.class));
        cards.add(new SetCardInfo("Divine Intervention", 8, Rarity.RARE, mage.cards.d.DivineIntervention.class));
        cards.add(new SetCardInfo("Dong Zhou, the Tyrant", 96, Rarity.RARE, mage.cards.d.DongZhouTheTyrant.class));
        cards.add(new SetCardInfo("Elves of Deep Shadow", 116, Rarity.COMMON, mage.cards.e.ElvesOfDeepShadow.class));
        cards.add(new SetCardInfo("Evil Presence", 64, Rarity.COMMON, mage.cards.e.EvilPresence.class));
        cards.add(new SetCardInfo("Exorcist", 10, Rarity.UNCOMMON, mage.cards.e.Exorcist.class));
        cards.add(new SetCardInfo("Faerie Noble", 117, Rarity.UNCOMMON, mage.cards.f.FaerieNoble.class));
        cards.add(new SetCardInfo("False Defeat", 11, Rarity.UNCOMMON, mage.cards.f.FalseDefeat.class));
        cards.add(new SetCardInfo("Famine", 65, Rarity.UNCOMMON, mage.cards.f.Famine.class));
        cards.add(new SetCardInfo("Fellwar Stone", 195, Rarity.COMMON, mage.cards.f.FellwarStone.class));
        cards.add(new SetCardInfo("Fevered Strength", 66, Rarity.COMMON, FeveredStrength.class));
        cards.add(new SetCardInfo("Fire Ambush", 97, Rarity.COMMON, mage.cards.f.FireAmbush.class));
        cards.add(new SetCardInfo("Fire Drake", 98, Rarity.COMMON, mage.cards.f.FireDrake.class));
        cards.add(new SetCardInfo("Fire Sprites", 118, Rarity.COMMON, mage.cards.f.FireSprites.class));
        cards.add(new SetCardInfo("Flash Flood", 35, Rarity.UNCOMMON, mage.cards.f.FlashFlood.class));
        cards.add(new SetCardInfo("Forced Retreat", 37, Rarity.COMMON, mage.cards.f.ForcedRetreat.class));
        cards.add(new SetCardInfo("Force Spike", 36, Rarity.COMMON, mage.cards.f.ForceSpike.class));
        cards.add(new SetCardInfo("Forest", 228, Rarity.LAND, mage.cards.basiclands.Forest.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Forest", 229, Rarity.LAND, mage.cards.basiclands.Forest.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Forest", 230, Rarity.LAND, mage.cards.basiclands.Forest.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Frost Giant", 101, Rarity.UNCOMMON, mage.cards.f.FrostGiant.class));
        cards.add(new SetCardInfo("Gaea's Touch", 120, Rarity.UNCOMMON, mage.cards.g.GaeasTouch.class));
        cards.add(new SetCardInfo("Ghostly Visit", 67, Rarity.COMMON, mage.cards.g.GhostlyVisit.class));
        cards.add(new SetCardInfo("Ghosts of the Damned", 68, Rarity.COMMON, mage.cards.g.GhostsOfTheDamned.class));
        cards.add(new SetCardInfo("Giant Growth", 121, Rarity.COMMON, mage.cards.g.GiantGrowth.class));
        cards.add(new SetCardInfo("Grim Tutor", 69, Rarity.RARE, mage.cards.g.GrimTutor.class));
        cards.add(new SetCardInfo("Guan Yu's 1,000-Li March", 13, Rarity.RARE, mage.cards.g.GuanYus1000LiMarch.class));
        cards.add(new SetCardInfo("Guan Yu, Sainted Warrior", 12, Rarity.UNCOMMON, mage.cards.g.GuanYuSaintedWarrior.class));
        cards.add(new SetCardInfo("Gwendlyn Di Corci", 149, Rarity.RARE, mage.cards.g.GwendlynDiCorci.class));
        cards.add(new SetCardInfo("Hazezon Tamar", 151, Rarity.RARE, mage.cards.h.HazezonTamar.class));
        cards.add(new SetCardInfo("Heal", 14, Rarity.COMMON, mage.cards.h.Heal.class));
        cards.add(new SetCardInfo("Hellfire", 70, Rarity.RARE, mage.cards.h.Hellfire.class));
        cards.add(new SetCardInfo("Hua Tuo, Honored Physician", 123, Rarity.RARE, mage.cards.h.HuaTuoHonoredPhysician.class));
        cards.add(new SetCardInfo("Hunding Gjornersen", 152, Rarity.UNCOMMON, mage.cards.h.HundingGjornersen.class));
        cards.add(new SetCardInfo("Hunting Cheetah", 124, Rarity.COMMON, mage.cards.h.HuntingCheetah.class));
        cards.add(new SetCardInfo("Hurloon Minotaur", 102, Rarity.COMMON, mage.cards.h.HurloonMinotaur.class));
        cards.add(new SetCardInfo("Immolation", 103, Rarity.COMMON, mage.cards.i.Immolation.class));
        cards.add(new SetCardInfo("Infuse", 38, Rarity.COMMON, mage.cards.i.Infuse.class));
        cards.add(new SetCardInfo("Island", 219, Rarity.LAND, mage.cards.basiclands.Island.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Island", 220, Rarity.LAND, mage.cards.basiclands.Island.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Island", 221, Rarity.LAND, mage.cards.basiclands.Island.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Ivory Guardians", 15, Rarity.UNCOMMON, mage.cards.i.IvoryGuardians.class));
        cards.add(new SetCardInfo("Jedit Ojanen", 153, Rarity.COMMON, mage.cards.j.JeditOjanen.class));
        cards.add(new SetCardInfo("Jerrard of the Closed Fist", 154, Rarity.COMMON, mage.cards.j.JerrardOfTheClosedFist.class));
        cards.add(new SetCardInfo("Jungle Lion", 125, Rarity.COMMON, mage.cards.j.JungleLion.class));
        cards.add(new SetCardInfo("Karakas", 208, Rarity.RARE, mage.cards.k.Karakas.class));
        cards.add(new SetCardInfo("Kei Takahashi", 155, Rarity.UNCOMMON, mage.cards.k.KeiTakahashi.class));
        cards.add(new SetCardInfo("Killer Bees", 126, Rarity.UNCOMMON, mage.cards.k.KillerBees.class));
        cards.add(new SetCardInfo("Kobold Drill Sergeant", 104, Rarity.UNCOMMON, mage.cards.k.KoboldDrillSergeant.class));
        cards.add(new SetCardInfo("Kobold Overlord", 105, Rarity.UNCOMMON, mage.cards.k.KoboldOverlord.class));
        cards.add(new SetCardInfo("Kobolds of Kher Keep", 107, Rarity.COMMON, mage.cards.k.KoboldsOfKherKeep.class));
        cards.add(new SetCardInfo("Kobold Taskmaster", 106, Rarity.COMMON, mage.cards.k.KoboldTaskmaster.class));
        cards.add(new SetCardInfo("Kongming, 'Sleeping Dragon'", 16, Rarity.RARE, mage.cards.k.KongmingSleepingDragon.class));
        cards.add(new SetCardInfo("Lady Caleria", 157, Rarity.UNCOMMON, mage.cards.l.LadyCaleria.class));
        cards.add(new SetCardInfo("Lady Evangela", 158, Rarity.UNCOMMON, mage.cards.l.LadyEvangela.class));
        cards.add(new SetCardInfo("Lady Orca", 159, Rarity.COMMON, mage.cards.l.LadyOrca.class));
        cards.add(new SetCardInfo("Land Equilibrium", 40, Rarity.RARE, mage.cards.l.LandEquilibrium.class));
        cards.add(new SetCardInfo("Land Tax", 17, Rarity.RARE, mage.cards.l.LandTax.class));
        cards.add(new SetCardInfo("Lightning Blow", 18, Rarity.COMMON, mage.cards.l.LightningBlow.class));
        cards.add(new SetCardInfo("Liu Bei, Lord of Shu", 19, Rarity.RARE, mage.cards.l.LiuBeiLordOfShu.class));
        cards.add(new SetCardInfo("Living Plane", 127, Rarity.RARE, mage.cards.l.LivingPlane.class));
        cards.add(new SetCardInfo("Livonya Silone", 160, Rarity.UNCOMMON, mage.cards.l.LivonyaSilone.class));
        cards.add(new SetCardInfo("Loyal Retainers", 20, Rarity.UNCOMMON, mage.cards.l.LoyalRetainers.class));
        cards.add(new SetCardInfo("Lu Bu, Master-at-Arms", 108, Rarity.RARE, mage.cards.l.LuBuMasterAtArms.class));
        cards.add(new SetCardInfo("Lu Meng, Wu General", 41, Rarity.UNCOMMON, mage.cards.l.LuMengWuGeneral.class));
        cards.add(new SetCardInfo("Lu Xun, Scholar General", 42, Rarity.UNCOMMON, mage.cards.l.LuXunScholarGeneral.class));
        cards.add(new SetCardInfo("Mana Drain", 43, Rarity.RARE, mage.cards.m.ManaDrain.class));
        cards.add(new SetCardInfo("Mana Vortex", 44, Rarity.RARE, mage.cards.m.ManaVortex.class));
        cards.add(new SetCardInfo("Marhault Elsdragon", 161, Rarity.UNCOMMON, mage.cards.m.MarhaultElsdragon.class));
        cards.add(new SetCardInfo("Meng Huo, Barbarian King", 128, Rarity.RARE, mage.cards.m.MengHuoBarbarianKing.class));
        cards.add(new SetCardInfo("Meng Huo's Horde", 129, Rarity.COMMON, mage.cards.m.MengHuosHorde.class));
        cards.add(new SetCardInfo("Mind Twist", 72, Rarity.RARE, mage.cards.m.MindTwist.class));
        cards.add(new SetCardInfo("Misfortune's Gain", 21, Rarity.COMMON, mage.cards.m.MisfortunesGain.class));
        cards.add(new SetCardInfo("Mountain", 225, Rarity.LAND, mage.cards.basiclands.Mountain.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Mountain", 226, Rarity.LAND, mage.cards.basiclands.Mountain.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Mountain", 227, Rarity.LAND, mage.cards.basiclands.Mountain.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Nether Void", 73, Rarity.RARE, mage.cards.n.NetherVoid.class));
        cards.add(new SetCardInfo("Nicol Bolas", 163, Rarity.RARE, mage.cards.n.NicolBolas.class));
        cards.add(new SetCardInfo("Old Man of the Sea", 45, Rarity.RARE, mage.cards.o.OldManOfTheSea.class));
        cards.add(new SetCardInfo("Palladia-Mors", 164, Rarity.RARE, mage.cards.p.PalladiaMors.class));
        cards.add(new SetCardInfo("Pavel Maliki", 165, Rarity.UNCOMMON, mage.cards.p.PavelMaliki.class));
        cards.add(new SetCardInfo("Peach Garden Oath", 22, Rarity.COMMON, mage.cards.p.PeachGardenOath.class));
        cards.add(new SetCardInfo("Plains", 216, Rarity.LAND, mage.cards.basiclands.Plains.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Plains", 217, Rarity.LAND, mage.cards.basiclands.Plains.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Plains", 218, Rarity.LAND, mage.cards.basiclands.Plains.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Plateau", 209, Rarity.RARE, mage.cards.p.Plateau.class));
        cards.add(new SetCardInfo("Princess Lucrezia", 166, Rarity.UNCOMMON, mage.cards.p.PrincessLucrezia.class));
        cards.add(new SetCardInfo("Raging Minotaur", 109, Rarity.COMMON, mage.cards.r.RagingMinotaur.class));
        cards.add(new SetCardInfo("Ragnar", 167, Rarity.UNCOMMON, mage.cards.r.Ragnar.class));
        cards.add(new SetCardInfo("Ramirez DePietro", 168, Rarity.COMMON, mage.cards.r.RamirezDePietro.class));
        cards.add(new SetCardInfo("Ramses Overdark", 169, Rarity.UNCOMMON, mage.cards.r.RamsesOverdark.class));
        cards.add(new SetCardInfo("Rasputin Dreamweaver", 170, Rarity.RARE, mage.cards.r.RasputinDreamweaver.class));
        cards.add(new SetCardInfo("Recall", 46, Rarity.UNCOMMON, mage.cards.r.Recall.class));
        cards.add(new SetCardInfo("Reincarnation", 130, Rarity.UNCOMMON, mage.cards.r.Reincarnation.class));
        cards.add(new SetCardInfo("Remove Soul", 47, Rarity.COMMON, mage.cards.r.RemoveSoul.class));
        cards.add(new SetCardInfo("Reset", 48, Rarity.RARE, mage.cards.r.Reset.class));
        cards.add(new SetCardInfo("Reveka, Wizard Savant", 49, Rarity.UNCOMMON, mage.cards.r.RevekaWizardSavant.class));
        cards.add(new SetCardInfo("Riding the Dilu Horse", 131, Rarity.UNCOMMON, mage.cards.r.RidingTheDiluHorse.class));
        cards.add(new SetCardInfo("Riven Turnbull", 171, Rarity.UNCOMMON, mage.cards.r.RivenTurnbull.class));
        cards.add(new SetCardInfo("Rolling Earthquake", 110, Rarity.RARE, mage.cards.r.RollingEarthquake.class));
        cards.add(new SetCardInfo("Rubinia Soulsinger", 173, Rarity.RARE, mage.cards.r.RubiniaSoulsinger.class));
        cards.add(new SetCardInfo("Scrubland", 210, Rarity.RARE, mage.cards.s.Scrubland.class));
        cards.add(new SetCardInfo("Scryb Sprites", 132, Rarity.COMMON, mage.cards.s.ScrybSprites.class));
        cards.add(new SetCardInfo("Shu Cavalry", 23, Rarity.COMMON, mage.cards.s.ShuCavalry.class));
        cards.add(new SetCardInfo("Shu Elite Companions", 24, Rarity.COMMON, mage.cards.s.ShuEliteCompanions.class));
        cards.add(new SetCardInfo("Shu General", 25, Rarity.COMMON, mage.cards.s.ShuGeneral.class));
        cards.add(new SetCardInfo("Shu Soldier-Farmers", 26, Rarity.COMMON, mage.cards.s.ShuSoldierFarmers.class));
        cards.add(new SetCardInfo("Sir Shandlar of Eberyn", 174, Rarity.COMMON, mage.cards.s.SirShandlarOfEberyn.class));
        cards.add(new SetCardInfo("Sivitri Scarzam", 175, Rarity.COMMON, mage.cards.s.SivitriScarzam.class));
        cards.add(new SetCardInfo("Slashing Tiger", 133, Rarity.COMMON, mage.cards.s.SlashingTiger.class));
        cards.add(new SetCardInfo("Sol Grail", 201, Rarity.COMMON, mage.cards.s.SolGrail.class));
        cards.add(new SetCardInfo("Spirit Shackle", 74, Rarity.COMMON, mage.cards.s.SpiritShackle.class));
        cards.add(new SetCardInfo("Spoils of Victory", 134, Rarity.COMMON, mage.cards.s.SpoilsOfVictory.class));
        cards.add(new SetCardInfo("Stolen Grain", 75, Rarity.UNCOMMON, mage.cards.s.StolenGrain.class));
        cards.add(new SetCardInfo("Storm World", 111, Rarity.RARE, mage.cards.s.StormWorld.class));
        cards.add(new SetCardInfo("Strategic Planning", 51, Rarity.COMMON, mage.cards.s.StrategicPlanning.class));
        cards.add(new SetCardInfo("Sunastian Falconer", 178, Rarity.UNCOMMON, mage.cards.s.SunastianFalconer.class));
        cards.add(new SetCardInfo("Sun Ce, Young Conquerer", 52, Rarity.UNCOMMON, mage.cards.s.SunCeYoungConquerer.class));
        cards.add(new SetCardInfo("Sun Quan, Lord of Wu", 53, Rarity.RARE, mage.cards.s.SunQuanLordOfWu.class));
        cards.add(new SetCardInfo("Swamp", 222, Rarity.LAND, mage.cards.basiclands.Swamp.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Swamp", 223, Rarity.LAND, mage.cards.basiclands.Swamp.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Swamp", 224, Rarity.LAND, mage.cards.basiclands.Swamp.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Tetsuo Umezawa", 179, Rarity.RARE, mage.cards.t.TetsuoUmezawa.class));
        cards.add(new SetCardInfo("The Abyss", 77, Rarity.RARE, mage.cards.t.TheAbyss.class));
        cards.add(new SetCardInfo("The Lady of the Mountain", 180, Rarity.COMMON, mage.cards.t.TheLadyOfTheMountain.class));
        cards.add(new SetCardInfo("The Tabernacle at Pendrell Vale", 212, Rarity.RARE, mage.cards.t.TheTabernacleAtPendrellVale.class));
        cards.add(new SetCardInfo("The Wretched", 78, Rarity.UNCOMMON, mage.cards.t.TheWretched.class));
        cards.add(new SetCardInfo("Three Visits", 135, Rarity.COMMON, mage.cards.t.ThreeVisits.class));
        cards.add(new SetCardInfo("Tobias Andrion", 181, Rarity.COMMON, mage.cards.t.TobiasAndrion.class));
        cards.add(new SetCardInfo("Torsten Von Ursus", 183, Rarity.COMMON, mage.cards.t.TorstenVonUrsus.class));
        cards.add(new SetCardInfo("Tor Wauki", 182, Rarity.UNCOMMON, mage.cards.t.TorWauki.class));
        cards.add(new SetCardInfo("Tracker", 136, Rarity.UNCOMMON, mage.cards.t.Tracker.class));
        cards.add(new SetCardInfo("Trip Wire", 137, Rarity.COMMON, mage.cards.t.TripWire.class));
        cards.add(new SetCardInfo("Tropical Island", 213, Rarity.RARE, mage.cards.t.TropicalIsland.class));
        cards.add(new SetCardInfo("Tuknir Deathlock", 184, Rarity.UNCOMMON, mage.cards.t.TuknirDeathlock.class));
        cards.add(new SetCardInfo("Urborg", 214, Rarity.UNCOMMON, mage.cards.u.Urborg.class));
        cards.add(new SetCardInfo("Vaevictis Asmadi", 185, Rarity.RARE, mage.cards.v.VaevictisAsmadi.class));
        cards.add(new SetCardInfo("Volcanic Island", 215, Rarity.RARE, mage.cards.v.VolcanicIsland.class));
        cards.add(new SetCardInfo("Wall of Light", 27, Rarity.COMMON, mage.cards.w.WallOfLight.class));
        cards.add(new SetCardInfo("Wandering Mage", 186, Rarity.RARE, mage.cards.w.WanderingMage.class));
        cards.add(new SetCardInfo("Wei Elite Companions", 79, Rarity.COMMON, mage.cards.w.WeiEliteCompanions.class));
        cards.add(new SetCardInfo("Wei Infantry", 80, Rarity.COMMON, mage.cards.w.WeiInfantry.class));
        cards.add(new SetCardInfo("Wei Night Raiders", 81, Rarity.UNCOMMON, mage.cards.w.WeiNightRaiders.class));
        cards.add(new SetCardInfo("Wei Strike Force", 82, Rarity.COMMON, mage.cards.w.WeiStrikeForce.class));
        cards.add(new SetCardInfo("Willow Priestess", 138, Rarity.UNCOMMON, mage.cards.w.WillowPriestess.class));
        cards.add(new SetCardInfo("Willow Satyr", 139, Rarity.RARE, mage.cards.w.WillowSatyr.class));
        cards.add(new SetCardInfo("Wormwood Treefolk", 140, Rarity.UNCOMMON, mage.cards.w.WormwoodTreefolk.class));
        cards.add(new SetCardInfo("Wu Elite Cavalry", 54, Rarity.COMMON, mage.cards.w.WuEliteCavalry.class));
        cards.add(new SetCardInfo("Wu Longbowman", 55, Rarity.COMMON, mage.cards.w.WuLongbowman.class));
        cards.add(new SetCardInfo("Wu Warship", 56, Rarity.COMMON, mage.cards.w.WuWarship.class));
        cards.add(new SetCardInfo("Xiahou Dun, the One-Eyed", 83, Rarity.UNCOMMON, mage.cards.x.XiahouDunTheOneEyed.class));
        cards.add(new SetCardInfo("Xira Arien", 187, Rarity.RARE, mage.cards.x.XiraArien.class));
        cards.add(new SetCardInfo("Young Wei Recruits", 84, Rarity.COMMON, mage.cards.y.YoungWeiRecruits.class));
        cards.add(new SetCardInfo("Zhang Fei, Fierce Warrior", 28, Rarity.UNCOMMON, mage.cards.z.ZhangFeiFierceWarrior.class));
        cards.add(new SetCardInfo("Zodiac Dragon", 112, Rarity.RARE, mage.cards.z.ZodiacDragon.class));
    }

}