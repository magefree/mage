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
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author North
 */
public class Legends extends ExpansionSet {

    private static final Legends instance = new Legends();

    public static Legends getInstance() {
        return instance;
    }

    private Legends() {
        super("Legends", "LEG", ExpansionSet.buildDate(1994, 6, 1), SetType.EXPANSION);
        this.hasBasicLands = false;
        this.hasBoosters = true;
        this.numBoosterLands = 0;
        this.numBoosterCommon = 11;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 0;
        cards.add(new SetCardInfo("Abomination", 1, Rarity.UNCOMMON, mage.cards.a.Abomination.class));
        cards.add(new SetCardInfo("Acid Rain", 44, Rarity.RARE, mage.cards.a.AcidRain.class));
        cards.add(new SetCardInfo("Active Volcano", 130, Rarity.COMMON, mage.cards.a.ActiveVolcano.class));
        cards.add(new SetCardInfo("Adun Oakenshield", 256, Rarity.RARE, mage.cards.a.AdunOakenshield.class));
        cards.add(new SetCardInfo("Aerathi Berserker", 131, Rarity.UNCOMMON, mage.cards.a.AerathiBerserker.class));
        cards.add(new SetCardInfo("Aisling Leprechaun", 87, Rarity.COMMON, mage.cards.a.AislingLeprechaun.class));
        cards.add(new SetCardInfo("Akron Legionnaire", 170, Rarity.RARE, mage.cards.a.AkronLegionnaire.class));
        cards.add(new SetCardInfo("Alchor's Tomb", 214, Rarity.RARE, mage.cards.a.AlchorsTomb.class));
        cards.add(new SetCardInfo("All Hallow's Eve", 2, Rarity.RARE, mage.cards.a.AllHallowsEve.class));
        cards.add(new SetCardInfo("Amrou Kithkin", 172, Rarity.COMMON, mage.cards.a.AmrouKithkin.class));
        cards.add(new SetCardInfo("Angus Mackenzie", 257, Rarity.RARE, mage.cards.a.AngusMackenzie.class));
        cards.add(new SetCardInfo("Arcades Sabboth", 258, Rarity.RARE, mage.cards.a.ArcadesSabboth.class));
        cards.add(new SetCardInfo("Arena of the Ancients", 215, Rarity.RARE, mage.cards.a.ArenaOfTheAncients.class));
        cards.add(new SetCardInfo("Avoid Fate", 89, Rarity.COMMON, mage.cards.a.AvoidFate.class));
        cards.add(new SetCardInfo("Azure Drake", 46, Rarity.UNCOMMON, mage.cards.a.AzureDrake.class));
        cards.add(new SetCardInfo("Backfire", 47, Rarity.UNCOMMON, mage.cards.b.Backfire.class));
        cards.add(new SetCardInfo("Barbary Apes", 90, Rarity.COMMON, mage.cards.b.BarbaryApes.class));
        cards.add(new SetCardInfo("Barktooth Warbeard", 261, Rarity.UNCOMMON, mage.cards.b.BarktoothWarbeard.class));
        cards.add(new SetCardInfo("Bartel Runeaxe", 262, Rarity.RARE, mage.cards.b.BartelRuneaxe.class));
        cards.add(new SetCardInfo("Beasts of Bogardan", 133, Rarity.UNCOMMON, mage.cards.b.BeastsOfBogardan.class));
        cards.add(new SetCardInfo("Blight", 3, Rarity.UNCOMMON, mage.cards.b.Blight.class));
        cards.add(new SetCardInfo("Blood Lust", 135, Rarity.UNCOMMON, mage.cards.b.BloodLust.class));
        cards.add(new SetCardInfo("Boomerang", 48, Rarity.COMMON, mage.cards.b.Boomerang.class));
        cards.add(new SetCardInfo("Boris Devilboon", 263, Rarity.RARE, mage.cards.b.BorisDevilboon.class));
        cards.add(new SetCardInfo("Carrion Ants", 4, Rarity.RARE, mage.cards.c.CarrionAnts.class));
        cards.add(new SetCardInfo("Cat Warriors", 91, Rarity.COMMON, mage.cards.c.CatWarriors.class));
        cards.add(new SetCardInfo("Chain Lightning", 137, Rarity.COMMON, mage.cards.c.ChainLightning.class));
        cards.add(new SetCardInfo("Chains of Mephistopheles", 5, Rarity.RARE, mage.cards.c.ChainsOfMephistopheles.class));
        cards.add(new SetCardInfo("Chromium", 264, Rarity.RARE, mage.cards.c.Chromium.class));
        cards.add(new SetCardInfo("Cleanse", 174, Rarity.RARE, mage.cards.c.Cleanse.class));
        cards.add(new SetCardInfo("Clergy of the Holy Nimbus", 175, Rarity.COMMON, mage.cards.c.ClergyOfTheHolyNimbus.class));
        cards.add(new SetCardInfo("Concordant Crossroads", 93, Rarity.RARE, mage.cards.c.ConcordantCrossroads.class));
        cards.add(new SetCardInfo("Cosmic Horror", 6, Rarity.RARE, mage.cards.c.CosmicHorror.class));
        cards.add(new SetCardInfo("Craw Giant", 94, Rarity.UNCOMMON, mage.cards.c.CrawGiant.class));
        cards.add(new SetCardInfo("Crimson Kobolds", 219, Rarity.COMMON, mage.cards.c.CrimsonKobolds.class));
        cards.add(new SetCardInfo("Crimson Manticore", 139, Rarity.RARE, mage.cards.c.CrimsonManticore.class));
        cards.add(new SetCardInfo("Crookshank Kobolds", 220, Rarity.COMMON, mage.cards.c.CrookshankKobolds.class));
        cards.add(new SetCardInfo("Cyclopean Mummy", 7, Rarity.COMMON, mage.cards.c.CyclopeanMummy.class));
        cards.add(new SetCardInfo("Dakkon Blackblade", 265, Rarity.RARE, mage.cards.d.DakkonBlackblade.class));
        cards.add(new SetCardInfo("Darkness", 8, Rarity.COMMON, mage.cards.d.Darkness.class));
        cards.add(new SetCardInfo("D'Avenant Archer", 176, Rarity.COMMON, mage.cards.d.DAvenantArcher.class));
        cards.add(new SetCardInfo("Demonic Torment", 9, Rarity.UNCOMMON, mage.cards.d.DemonicTorment.class));
        cards.add(new SetCardInfo("Devouring Deep", 50, Rarity.COMMON, mage.cards.d.DevouringDeep.class));
        cards.add(new SetCardInfo("Divine Intervention", 177, Rarity.RARE, mage.cards.d.DivineIntervention.class));
        cards.add(new SetCardInfo("Divine Offering", 178, Rarity.COMMON, mage.cards.d.DivineOffering.class));
        cards.add(new SetCardInfo("Divine Transformation", 179, Rarity.RARE, mage.cards.d.DivineTransformation.class));
        cards.add(new SetCardInfo("Durkwood Boars", 96, Rarity.COMMON, mage.cards.d.DurkwoodBoars.class));
        cards.add(new SetCardInfo("Dwarven Song", 141, Rarity.UNCOMMON, mage.cards.d.DwarvenSong.class));
        cards.add(new SetCardInfo("Elven Riders", 97, Rarity.RARE, mage.cards.e.ElvenRiders.class));
        cards.add(new SetCardInfo("Emerald Dragonfly", 98, Rarity.COMMON, mage.cards.e.EmeraldDragonfly.class));
        cards.add(new SetCardInfo("Energy Tap", 54, Rarity.COMMON, mage.cards.e.EnergyTap.class));
        cards.add(new SetCardInfo("Eternal Warrior", 142, Rarity.UNCOMMON, mage.cards.e.EternalWarrior.class));
        cards.add(new SetCardInfo("Eureka", 99, Rarity.RARE, mage.cards.e.Eureka.class));
        cards.add(new SetCardInfo("Evil Eye of Orms-by-Gore", 10, Rarity.UNCOMMON, mage.cards.e.EvilEyeOfOrmsByGore.class));
        cards.add(new SetCardInfo("Fallen Angel", 11, Rarity.UNCOMMON, mage.cards.f.FallenAngel.class));
        cards.add(new SetCardInfo("Field of Dreams", 55, Rarity.RARE, mage.cards.f.FieldOfDreams.class));
        cards.add(new SetCardInfo("Fire Sprites", 100, Rarity.COMMON, mage.cards.f.FireSprites.class));
        cards.add(new SetCardInfo("Flash Counter", 56, Rarity.COMMON, mage.cards.f.FlashCounter.class));
        cards.add(new SetCardInfo("Flash Flood", 57, Rarity.COMMON, mage.cards.f.FlashFlood.class));
        cards.add(new SetCardInfo("Force Spike", 58, Rarity.COMMON, mage.cards.f.ForceSpike.class));
        cards.add(new SetCardInfo("Frost Giant", 146, Rarity.UNCOMMON, mage.cards.f.FrostGiant.class));
        cards.add(new SetCardInfo("Gaseous Form", 59, Rarity.COMMON, mage.cards.g.GaseousForm.class));
        cards.add(new SetCardInfo("Ghosts of the Damned", 12, Rarity.COMMON, mage.cards.g.GhostsOfTheDamned.class));
        cards.add(new SetCardInfo("Giant Strength", 147, Rarity.COMMON, mage.cards.g.GiantStrength.class));
        cards.add(new SetCardInfo("Gravity Sphere", 149, Rarity.RARE, mage.cards.g.GravitySphere.class));
        cards.add(new SetCardInfo("Great Defender", 185, Rarity.UNCOMMON, mage.cards.g.GreatDefender.class));
        cards.add(new SetCardInfo("Greed", 15, Rarity.RARE, mage.cards.g.Greed.class));
        cards.add(new SetCardInfo("Gwendlyn Di Corci", 268, Rarity.RARE, mage.cards.g.GwendlynDiCorci.class));
        cards.add(new SetCardInfo("Hazezon Tamar", 270, Rarity.RARE, mage.cards.h.HazezonTamar.class));
        cards.add(new SetCardInfo("Headless Horseman", 16, Rarity.COMMON, mage.cards.h.HeadlessHorseman.class));
        cards.add(new SetCardInfo("Heaven's Gate", 188, Rarity.UNCOMMON, mage.cards.h.HeavensGate.class));
        cards.add(new SetCardInfo("Hellfire", 18, Rarity.RARE, mage.cards.h.Hellfire.class));
        cards.add(new SetCardInfo("Hell's Caretaker", 19, Rarity.RARE, mage.cards.h.HellsCaretaker.class));
        cards.add(new SetCardInfo("Holy Day", 189, Rarity.COMMON, mage.cards.h.HolyDay.class));
        cards.add(new SetCardInfo("Hornet Cobra", 104, Rarity.COMMON, mage.cards.h.HornetCobra.class));
        cards.add(new SetCardInfo("Horn of Deafening", 224, Rarity.RARE, mage.cards.h.HornOfDeafening.class));
        cards.add(new SetCardInfo("Horror of Horrors", 20, Rarity.UNCOMMON, mage.cards.h.HorrorOfHorrors.class));
        cards.add(new SetCardInfo("Hunding Gjornersen", 271, Rarity.UNCOMMON, mage.cards.h.HundingGjornersen.class));
        cards.add(new SetCardInfo("Hyperion Blacksmith", 150, Rarity.UNCOMMON, mage.cards.h.HyperionBlacksmith.class));
        cards.add(new SetCardInfo("Immolation", 151, Rarity.COMMON, mage.cards.i.Immolation.class));
        cards.add(new SetCardInfo("In the Eye of Chaos", 61, Rarity.RARE, mage.cards.i.InTheEyeOfChaos.class));
        cards.add(new SetCardInfo("Invoke Prejudice", 62, Rarity.RARE, mage.cards.i.InvokePrejudice.class));
        cards.add(new SetCardInfo("Ivory Guardians", 192, Rarity.UNCOMMON, mage.cards.i.IvoryGuardians.class));
        cards.add(new SetCardInfo("Jacques le Vert", 272, Rarity.RARE, mage.cards.j.JacquesLeVert.class));
        cards.add(new SetCardInfo("Jasmine Boreal", 273, Rarity.UNCOMMON, mage.cards.j.JasmineBoreal.class));
        cards.add(new SetCardInfo("Jedit Ojanen", 274, Rarity.UNCOMMON, mage.cards.j.JeditOjanen.class));
        cards.add(new SetCardInfo("Jerrard of the Closed Fist", 275, Rarity.UNCOMMON, mage.cards.j.JerrardOfTheClosedFist.class));
        cards.add(new SetCardInfo("Juxtapose", 63, Rarity.RARE, mage.cards.j.Juxtapose.class));
        cards.add(new SetCardInfo("Karakas", 248, Rarity.UNCOMMON, mage.cards.k.Karakas.class));
        cards.add(new SetCardInfo("Kasimir the Lone Wolf", 277, Rarity.UNCOMMON, mage.cards.k.KasimirTheLoneWolf.class));
        cards.add(new SetCardInfo("Keepers of the Faith", 193, Rarity.COMMON, mage.cards.k.KeepersOfTheFaith.class));
        cards.add(new SetCardInfo("Kei Takahashi", 278, Rarity.RARE, mage.cards.k.KeiTakahashi.class));
        cards.add(new SetCardInfo("Killer Bees", 106, Rarity.RARE, mage.cards.k.KillerBees.class));
        cards.add(new SetCardInfo("Kismet", 194, Rarity.UNCOMMON, mage.cards.k.Kismet.class));
        cards.add(new SetCardInfo("Kobold Drill Sergeant", 152, Rarity.UNCOMMON, mage.cards.k.KoboldDrillSergeant.class));
        cards.add(new SetCardInfo("Kobold Overlord", 153, Rarity.RARE, mage.cards.k.KoboldOverlord.class));
        cards.add(new SetCardInfo("Kobolds of Kher Keep", 226, Rarity.COMMON, mage.cards.k.KoboldsOfKherKeep.class));
        cards.add(new SetCardInfo("Kobold Taskmaster", 154, Rarity.UNCOMMON, mage.cards.k.KoboldTaskmaster.class));
        cards.add(new SetCardInfo("Lady Caleria", 279, Rarity.RARE, mage.cards.l.LadyCaleria.class));
        cards.add(new SetCardInfo("Lady Evangela", 280, Rarity.RARE, mage.cards.l.LadyEvangela.class));
        cards.add(new SetCardInfo("Lady Orca", 281, Rarity.UNCOMMON, mage.cards.l.LadyOrca.class));
        cards.add(new SetCardInfo("Land Equilibrium", 64, Rarity.RARE, mage.cards.l.LandEquilibrium.class));
        cards.add(new SetCardInfo("Land Tax", 195, Rarity.UNCOMMON, mage.cards.l.LandTax.class));
        cards.add(new SetCardInfo("Lifeblood", 196, Rarity.RARE, mage.cards.l.Lifeblood.class));
        cards.add(new SetCardInfo("Living Plane", 107, Rarity.RARE, mage.cards.l.LivingPlane.class));
        cards.add(new SetCardInfo("Livonya Silone", 282, Rarity.RARE, mage.cards.l.LivonyaSilone.class));
        cards.add(new SetCardInfo("Lost Soul", 25, Rarity.COMMON, mage.cards.l.LostSoul.class));
        cards.add(new SetCardInfo("Mana Drain", 65, Rarity.UNCOMMON, mage.cards.m.ManaDrain.class));
        cards.add(new SetCardInfo("Mana Matrix", 230, Rarity.RARE, mage.cards.m.ManaMatrix.class));
        cards.add(new SetCardInfo("Marhault Elsdragon", 284, Rarity.UNCOMMON, mage.cards.m.MarhaultElsdragon.class));
        cards.add(new SetCardInfo("Mirror Universe", 232, Rarity.RARE, mage.cards.m.MirrorUniverse.class));
        cards.add(new SetCardInfo("Moat", 197, Rarity.RARE, mage.cards.m.Moat.class));
        cards.add(new SetCardInfo("Mold Demon", 26, Rarity.RARE, mage.cards.m.MoldDemon.class));
        cards.add(new SetCardInfo("Moss Monster", 109, Rarity.COMMON, mage.cards.m.MossMonster.class));
        cards.add(new SetCardInfo("Mountain Yeti", 156, Rarity.UNCOMMON, mage.cards.m.MountainYeti.class));
        cards.add(new SetCardInfo("Nether Void", 27, Rarity.RARE, mage.cards.n.NetherVoid.class));
        cards.add(new SetCardInfo("Nicol Bolas", 286, Rarity.RARE, mage.cards.n.NicolBolas.class));
        cards.add(new SetCardInfo("Osai Vultures", 198, Rarity.COMMON, mage.cards.o.OsaiVultures.class));
        cards.add(new SetCardInfo("Palladia-Mors", 287, Rarity.RARE, mage.cards.p.PalladiaMors.class));
        cards.add(new SetCardInfo("Part Water", 66, Rarity.UNCOMMON, mage.cards.p.PartWater.class));
        cards.add(new SetCardInfo("Pavel Maliki", 288, Rarity.UNCOMMON, mage.cards.p.PavelMaliki.class));
        cards.add(new SetCardInfo("Pendelhaven", 250, Rarity.UNCOMMON, mage.cards.p.Pendelhaven.class));
        cards.add(new SetCardInfo("Pit Scorpion", 28, Rarity.COMMON, mage.cards.p.PitScorpion.class));
        cards.add(new SetCardInfo("Pixie Queen", 110, Rarity.RARE, mage.cards.p.PixieQueen.class));
        cards.add(new SetCardInfo("Planar Gate", 235, Rarity.RARE, mage.cards.p.PlanarGate.class));
        cards.add(new SetCardInfo("Pradesh Gypsies", 111, Rarity.UNCOMMON, mage.cards.p.PradeshGypsies.class));
        cards.add(new SetCardInfo("Presence of the Master", 200, Rarity.UNCOMMON, mage.cards.p.PresenceOfTheMaster.class));
        cards.add(new SetCardInfo("Princess Lucrezia", 289, Rarity.UNCOMMON, mage.cards.p.PrincessLucrezia.class));
        cards.add(new SetCardInfo("Pyrotechnics", 158, Rarity.COMMON, mage.cards.p.Pyrotechnics.class));
        cards.add(new SetCardInfo("Rabid Wombat", 112, Rarity.UNCOMMON, mage.cards.r.RabidWombat.class));
        cards.add(new SetCardInfo("Radjan Spirit", 113, Rarity.UNCOMMON, mage.cards.r.RadjanSpirit.class));
        cards.add(new SetCardInfo("Raging Bull", 160, Rarity.COMMON, mage.cards.r.RagingBull.class));
        cards.add(new SetCardInfo("Ragnar", 290, Rarity.RARE, mage.cards.r.Ragnar.class));
        cards.add(new SetCardInfo("Ramirez DePietro", 291, Rarity.UNCOMMON, mage.cards.r.RamirezDePietro.class));
        cards.add(new SetCardInfo("Ramses Overdark", 292, Rarity.RARE, mage.cards.r.RamsesOverdark.class));
        cards.add(new SetCardInfo("Rasputin Dreamweaver", 293, Rarity.RARE, mage.cards.r.RasputinDreamweaver.class));
        cards.add(new SetCardInfo("Recall", 70, Rarity.RARE, mage.cards.r.Recall.class));
        cards.add(new SetCardInfo("Reincarnation", 115, Rarity.UNCOMMON, mage.cards.r.Reincarnation.class));
        cards.add(new SetCardInfo("Relic Barrier", 237, Rarity.UNCOMMON, mage.cards.r.RelicBarrier.class));
        cards.add(new SetCardInfo("Remove Soul", 72, Rarity.COMMON, mage.cards.r.RemoveSoul.class));
        cards.add(new SetCardInfo("Reset", 73, Rarity.UNCOMMON, mage.cards.r.Reset.class));
        cards.add(new SetCardInfo("Revelation", 116, Rarity.RARE, mage.cards.r.Revelation.class));
        cards.add(new SetCardInfo("Righteous Avengers", 203, Rarity.UNCOMMON, mage.cards.r.RighteousAvengers.class));
        cards.add(new SetCardInfo("Ring of Immortals", 238, Rarity.RARE, mage.cards.r.RingOfImmortals.class));
        cards.add(new SetCardInfo("Riven Turnbull", 294, Rarity.UNCOMMON, mage.cards.r.RivenTurnbull.class));
        cards.add(new SetCardInfo("Rubinia Soulsinger", 296, Rarity.RARE, mage.cards.r.RubiniaSoulsinger.class));
        cards.add(new SetCardInfo("Sea Kings' Blessing", 75, Rarity.UNCOMMON, mage.cards.s.SeaKingsBlessing.class));
        cards.add(new SetCardInfo("Segovian Leviathan", 76, Rarity.UNCOMMON, mage.cards.s.SegovianLeviathan.class));
        cards.add(new SetCardInfo("Sentinel", 239, Rarity.RARE, mage.cards.s.Sentinel.class));
        cards.add(new SetCardInfo("Serpent Generator", 240, Rarity.RARE, mage.cards.s.SerpentGenerator.class));
        cards.add(new SetCardInfo("Shield Wall", 205, Rarity.UNCOMMON, mage.cards.s.ShieldWall.class));
        cards.add(new SetCardInfo("Sir Shandlar of Eberyn", 297, Rarity.UNCOMMON, mage.cards.s.SirShandlarOfEberyn.class));
        cards.add(new SetCardInfo("Sivitri Scarzam", 298, Rarity.UNCOMMON, mage.cards.s.SivitriScarzam.class));
        cards.add(new SetCardInfo("Sol'kanar the Swamp King", 299, Rarity.RARE, mage.cards.s.SolkanarTheSwampKing.class));
        cards.add(new SetCardInfo("Spinal Villain", 161, Rarity.RARE, mage.cards.s.SpinalVillain.class));
        cards.add(new SetCardInfo("Spirit Link", 206, Rarity.UNCOMMON, mage.cards.s.SpiritLink.class));
        cards.add(new SetCardInfo("Spirit Shackle", 31, Rarity.COMMON, mage.cards.s.SpiritShackle.class));
        cards.add(new SetCardInfo("Storm Seeker", 119, Rarity.UNCOMMON, mage.cards.s.StormSeeker.class));
        cards.add(new SetCardInfo("Storm World", 162, Rarity.RARE, mage.cards.s.StormWorld.class));
        cards.add(new SetCardInfo("Sunastian Falconer", 301, Rarity.UNCOMMON, mage.cards.s.SunastianFalconer.class));
        cards.add(new SetCardInfo("Sylvan Library", 121, Rarity.UNCOMMON, mage.cards.s.SylvanLibrary.class));
        cards.add(new SetCardInfo("Sylvan Paradise", 122, Rarity.UNCOMMON, mage.cards.s.SylvanParadise.class));
        cards.add(new SetCardInfo("Syphon Soul", 32, Rarity.COMMON, mage.cards.s.SyphonSoul.class));
        cards.add(new SetCardInfo("Tetsuo Umezawa", 302, Rarity.RARE, mage.cards.t.TetsuoUmezawa.class));
        cards.add(new SetCardInfo("The Abyss", 34, Rarity.RARE, mage.cards.t.TheAbyss.class));
        cards.add(new SetCardInfo("The Brute", 164, Rarity.COMMON, mage.cards.t.TheBrute.class));
        cards.add(new SetCardInfo("The Lady of the Mountain", 303, Rarity.UNCOMMON, mage.cards.t.TheLadyOfTheMountain.class));
        cards.add(new SetCardInfo("The Tabernacle at Pendrell Vale", 252, Rarity.RARE, mage.cards.t.TheTabernacleAtPendrellVale.class));
        cards.add(new SetCardInfo("The Wretched", 35, Rarity.RARE, mage.cards.t.TheWretched.class));
        cards.add(new SetCardInfo("Thunder Spirit", 208, Rarity.RARE, mage.cards.t.ThunderSpirit.class));
        cards.add(new SetCardInfo("Tobias Andrion", 304, Rarity.UNCOMMON, mage.cards.t.TobiasAndrion.class));
        cards.add(new SetCardInfo("Torsten Von Ursus", 306, Rarity.UNCOMMON, mage.cards.t.TorstenVonUrsus.class));
        cards.add(new SetCardInfo("Tor Wauki", 305, Rarity.UNCOMMON, mage.cards.t.TorWauki.class));
        cards.add(new SetCardInfo("Touch of Darkness", 36, Rarity.UNCOMMON, mage.cards.t.TouchOfDarkness.class));
        cards.add(new SetCardInfo("Transmutation", 37, Rarity.COMMON, mage.cards.t.Transmutation.class));
        cards.add(new SetCardInfo("Triassic Egg", 242, Rarity.RARE, mage.cards.t.TriassicEgg.class));
        cards.add(new SetCardInfo("Tuknir Deathlock", 307, Rarity.RARE, mage.cards.t.TuknirDeathlock.class));
        cards.add(new SetCardInfo("Tundra Wolves", 209, Rarity.COMMON, mage.cards.t.TundraWolves.class));
        cards.add(new SetCardInfo("Underworld Dreams", 38, Rarity.UNCOMMON, mage.cards.u.UnderworldDreams.class));
        cards.add(new SetCardInfo("Untamed Wilds", 124, Rarity.UNCOMMON, mage.cards.u.UntamedWilds.class));
        cards.add(new SetCardInfo("Urborg", 255, Rarity.UNCOMMON, mage.cards.u.Urborg.class));
        cards.add(new SetCardInfo("Vaevictis Asmadi", 309, Rarity.RARE, mage.cards.v.VaevictisAsmadi.class));
        cards.add(new SetCardInfo("Vampire Bats", 39, Rarity.COMMON, mage.cards.v.VampireBats.class));
        cards.add(new SetCardInfo("Walking Dead", 40, Rarity.COMMON, mage.cards.w.WalkingDead.class));
        cards.add(new SetCardInfo("Wall of Earth", 166, Rarity.COMMON, mage.cards.w.WallOfEarth.class));
        cards.add(new SetCardInfo("Wall of Heat", 167, Rarity.COMMON, mage.cards.w.WallOfHeat.class));
        cards.add(new SetCardInfo("Wall of Light", 212, Rarity.UNCOMMON, mage.cards.w.WallOfLight.class));
        cards.add(new SetCardInfo("Wall of Opposition", 168, Rarity.RARE, mage.cards.w.WallOfOpposition.class));
        cards.add(new SetCardInfo("Wall of Putrid Flesh", 41, Rarity.UNCOMMON, mage.cards.w.WallOfPutridFlesh.class));
        cards.add(new SetCardInfo("Wall of Wonder", 85, Rarity.UNCOMMON, mage.cards.w.WallOfWonder.class));
        cards.add(new SetCardInfo("Whirling Dervish", 125, Rarity.UNCOMMON, mage.cards.w.WhirlingDervish.class));
        cards.add(new SetCardInfo("Willow Satyr", 126, Rarity.RARE, mage.cards.w.WillowSatyr.class));
        cards.add(new SetCardInfo("Winds of Change", 169, Rarity.UNCOMMON, mage.cards.w.WindsOfChange.class));
        cards.add(new SetCardInfo("Wolverine Pack", 128, Rarity.COMMON, mage.cards.w.WolverinePack.class));
        cards.add(new SetCardInfo("Xira Arien", 310, Rarity.RARE, mage.cards.x.XiraArien.class));
        cards.add(new SetCardInfo("Zephyr Falcon", 86, Rarity.COMMON, mage.cards.z.ZephyrFalcon.class));
    }
}
