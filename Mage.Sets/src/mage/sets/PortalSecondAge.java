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
 * @author Plopman
 */

public class PortalSecondAge extends ExpansionSet {

    private static final PortalSecondAge instance = new PortalSecondAge();

    /**
     *
     * @return
     */
    public static PortalSecondAge getInstance() {
        return instance;
    }

    private PortalSecondAge() {
        super("Portal Second Age", "P02", ExpansionSet.buildDate(1998, 6, 24), SetType.SUPPLEMENTAL);
        this.blockName = "Beginner";
        this.hasBasicLands = true;
        this.hasBoosters = true;
        this.numBoosterLands = 0;
        this.numBoosterCommon = 11;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 0;
        cards.add(new SetCardInfo("Abyssal Nightstalker", 1, Rarity.UNCOMMON, mage.cards.a.AbyssalNightstalker.class));
        cards.add(new SetCardInfo("Air Elemental", 31, Rarity.UNCOMMON, mage.cards.a.AirElemental.class));
        cards.add(new SetCardInfo("Alaborn Cavalier", 121, Rarity.UNCOMMON, mage.cards.a.AlabornCavalier.class));
        cards.add(new SetCardInfo("Alaborn Grenadier", 122, Rarity.COMMON, mage.cards.a.AlabornGrenadier.class));
        cards.add(new SetCardInfo("Alaborn Musketeer", 123, Rarity.COMMON, mage.cards.a.AlabornMusketeer.class));
        cards.add(new SetCardInfo("Alaborn Trooper", 124, Rarity.COMMON, mage.cards.a.AlabornTrooper.class));
        cards.add(new SetCardInfo("Alaborn Veteran", 125, Rarity.RARE, mage.cards.a.AlabornVeteran.class));
        cards.add(new SetCardInfo("Alluring Scent", 61, Rarity.RARE, mage.cards.a.AlluringScent.class));
        cards.add(new SetCardInfo("Ancient Craving", 2, Rarity.RARE, mage.cards.a.AncientCraving.class));
        cards.add(new SetCardInfo("Angelic Blessing", 129, Rarity.COMMON, mage.cards.a.AngelicBlessing.class));
        cards.add(new SetCardInfo("Angelic Wall", 130, Rarity.COMMON, mage.cards.a.AngelicWall.class));
        cards.add(new SetCardInfo("Angel of Fury", 127, Rarity.RARE, mage.cards.a.AngelOfFury.class));
        cards.add(new SetCardInfo("Angel of Mercy", 128, Rarity.UNCOMMON, mage.cards.a.AngelOfMercy.class));
        cards.add(new SetCardInfo("Apprentice Sorcerer", 32, Rarity.UNCOMMON, mage.cards.a.ApprenticeSorcerer.class));
        cards.add(new SetCardInfo("Archangel", 131, Rarity.RARE, mage.cards.a.Archangel.class));
        cards.add(new SetCardInfo("Armageddon", 132, Rarity.RARE, mage.cards.a.Armageddon.class));
        cards.add(new SetCardInfo("Armored Galleon", 33, Rarity.UNCOMMON, mage.cards.a.ArmoredGalleon.class));
        cards.add(new SetCardInfo("Armored Griffin", 133, Rarity.UNCOMMON, mage.cards.a.ArmoredGriffin.class));
        cards.add(new SetCardInfo("Barbtooth Wurm", 62, Rarity.COMMON, mage.cards.b.BarbtoothWurm.class));
        cards.add(new SetCardInfo("Bargain", 134, Rarity.UNCOMMON, mage.cards.b.Bargain.class));
        cards.add(new SetCardInfo("Bear Cub", 63, Rarity.COMMON, mage.cards.b.BearCub.class));
        cards.add(new SetCardInfo("Bee Sting", 64, Rarity.UNCOMMON, mage.cards.b.BeeSting.class));
        cards.add(new SetCardInfo("Blaze", 91, Rarity.UNCOMMON, mage.cards.b.Blaze.class));
        cards.add(new SetCardInfo("Bloodcurdling Scream", 3, Rarity.UNCOMMON, mage.cards.b.BloodcurdlingScream.class));
        cards.add(new SetCardInfo("Breath of Life", 135, Rarity.COMMON, mage.cards.b.BreathOfLife.class));
        cards.add(new SetCardInfo("Brimstone Dragon", 92, Rarity.RARE, mage.cards.b.BrimstoneDragon.class));
        cards.add(new SetCardInfo("Brutal Nightstalker", 4, Rarity.UNCOMMON, mage.cards.b.BrutalNightstalker.class));
        cards.add(new SetCardInfo("Chorus of Woe", 5, Rarity.COMMON, mage.cards.c.ChorusOfWoe.class));
        cards.add(new SetCardInfo("Coastal Wizard", 34, Rarity.RARE, mage.cards.c.CoastalWizard.class));
        cards.add(new SetCardInfo("Coercion", 6, Rarity.UNCOMMON, mage.cards.c.Coercion.class));
        cards.add(new SetCardInfo("Cruel Edict", 7, Rarity.COMMON, mage.cards.c.CruelEdict.class));
        cards.add(new SetCardInfo("Dakmor Bat", 8, Rarity.COMMON, mage.cards.d.DakmorBat.class));
        cards.add(new SetCardInfo("Dakmor Plague", 9, Rarity.UNCOMMON, mage.cards.d.DakmorPlague.class));
        cards.add(new SetCardInfo("Dakmor Scorpion", 10, Rarity.COMMON, mage.cards.d.DakmorScorpion.class));
        cards.add(new SetCardInfo("Dakmor Sorceress", 11, Rarity.RARE, mage.cards.d.DakmorSorceress.class));
        cards.add(new SetCardInfo("Dark Offering", 12, Rarity.UNCOMMON, mage.cards.d.DarkOffering.class));
        cards.add(new SetCardInfo("Deathcoil Wurm", 65, Rarity.RARE, mage.cards.d.DeathcoilWurm.class));
        cards.add(new SetCardInfo("Deja Vu", 35, Rarity.COMMON, mage.cards.d.DejaVu.class));
        cards.add(new SetCardInfo("Denizen of the Deep", 36, Rarity.RARE, mage.cards.d.DenizenOfTheDeep.class));
        cards.add(new SetCardInfo("Earthquake", 94, Rarity.RARE, mage.cards.e.Earthquake.class));
        cards.add(new SetCardInfo("Exhaustion", 37, Rarity.RARE, mage.cards.e.Exhaustion.class));
        cards.add(new SetCardInfo("Extinguish", 38, Rarity.COMMON, mage.cards.e.Extinguish.class));
        cards.add(new SetCardInfo("Eye Spy", 39, Rarity.UNCOMMON, mage.cards.e.EyeSpy.class));
        cards.add(new SetCardInfo("False Summoning", 40, Rarity.COMMON, mage.cards.f.FalseSummoning.class));
        cards.add(new SetCardInfo("Festival of Trokin", 136, Rarity.COMMON, mage.cards.f.FestivalOfTrokin.class));
        cards.add(new SetCardInfo("Forest", 151, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 152, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 153, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Foul Spirit", 13, Rarity.UNCOMMON, mage.cards.f.FoulSpirit.class));
        cards.add(new SetCardInfo("Goblin Cavaliers", 95, Rarity.COMMON, mage.cards.g.GoblinCavaliers.class));
        cards.add(new SetCardInfo("Goblin Firestarter", 96, Rarity.UNCOMMON, mage.cards.g.GoblinFirestarter.class));
        cards.add(new SetCardInfo("Goblin General", 97, Rarity.RARE, mage.cards.g.GoblinGeneral.class));
        cards.add(new SetCardInfo("Goblin Glider", 98, Rarity.COMMON, mage.cards.g.GoblinGlider.class));
        cards.add(new SetCardInfo("Goblin Lore", 99, Rarity.UNCOMMON, mage.cards.g.GoblinLore.class));
        cards.add(new SetCardInfo("Goblin Matron", 100, Rarity.UNCOMMON, mage.cards.g.GoblinMatron.class));
        cards.add(new SetCardInfo("Goblin Mountaineer", 101, Rarity.COMMON, mage.cards.g.GoblinMountaineer.class));
        cards.add(new SetCardInfo("Goblin Piker", 102, Rarity.COMMON, mage.cards.g.GoblinPiker.class));
        cards.add(new SetCardInfo("Goblin Raider", 103, Rarity.COMMON, mage.cards.g.GoblinRaider.class));
        cards.add(new SetCardInfo("Goblin War Strike", 105, Rarity.COMMON, mage.cards.g.GoblinWarStrike.class));
        cards.add(new SetCardInfo("Golden Bear", 67, Rarity.COMMON, mage.cards.g.GoldenBear.class));
        cards.add(new SetCardInfo("Hand of Death", 14, Rarity.COMMON, mage.cards.h.HandOfDeath.class));
        cards.add(new SetCardInfo("Harmony of Nature", 68, Rarity.UNCOMMON, mage.cards.h.HarmonyOfNature.class));
        cards.add(new SetCardInfo("Hidden Horror", 15, Rarity.RARE, mage.cards.h.HiddenHorror.class));
        cards.add(new SetCardInfo("Hurricane", 69, Rarity.RARE, mage.cards.h.Hurricane.class));
        cards.add(new SetCardInfo("Ironhoof Ox", 70, Rarity.UNCOMMON, mage.cards.i.IronhoofOx.class));
        cards.add(new SetCardInfo("Island", 154, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 155, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 156, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jagged Lightning", 106, Rarity.UNCOMMON, mage.cards.j.JaggedLightning.class));
        cards.add(new SetCardInfo("Kiss of Death", 16, Rarity.UNCOMMON, mage.cards.k.KissOfDeath.class));
        cards.add(new SetCardInfo("Lava Axe", 107, Rarity.COMMON, mage.cards.l.LavaAxe.class));
        cards.add(new SetCardInfo("Lone Wolf", 71, Rarity.UNCOMMON, mage.cards.l.LoneWolf.class));
        cards.add(new SetCardInfo("Lurking Nightstalker", 17, Rarity.COMMON, mage.cards.l.LurkingNightstalker.class));
        cards.add(new SetCardInfo("Lynx", 72, Rarity.COMMON, mage.cards.l.Lynx.class));
        cards.add(new SetCardInfo("Magma Giant", 108, Rarity.RARE, mage.cards.m.MagmaGiant.class));
        cards.add(new SetCardInfo("Mind Rot", 18, Rarity.COMMON, mage.cards.m.MindRot.class));
        cards.add(new SetCardInfo("Moaning Spirit", 19, Rarity.COMMON, mage.cards.m.MoaningSpirit.class));
        cards.add(new SetCardInfo("Monstrous Growth", 73, Rarity.COMMON, mage.cards.m.MonstrousGrowth.class));
        cards.add(new SetCardInfo("Mountain", 157, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 158, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 159, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Muck Rats", 20, Rarity.COMMON, mage.cards.m.MuckRats.class));
        cards.add(new SetCardInfo("Mystic Denial", 41, Rarity.UNCOMMON, mage.cards.m.MysticDenial.class));
        cards.add(new SetCardInfo("Natural Spring", 74, Rarity.COMMON, mage.cards.n.NaturalSpring.class));
        cards.add(new SetCardInfo("Nature's Lore", 75, Rarity.COMMON, mage.cards.n.NaturesLore.class));
        cards.add(new SetCardInfo("Nightstalker Engine", 21, Rarity.RARE, mage.cards.n.NightstalkerEngine.class));
        cards.add(new SetCardInfo("Norwood Archers", 76, Rarity.COMMON, mage.cards.n.NorwoodArchers.class));
        cards.add(new SetCardInfo("Norwood Priestess", 77, Rarity.RARE, mage.cards.n.NorwoodPriestess.class));
        cards.add(new SetCardInfo("Norwood Ranger", 78, Rarity.COMMON, mage.cards.n.NorwoodRanger.class));
        cards.add(new SetCardInfo("Norwood Riders", 79, Rarity.COMMON, mage.cards.n.NorwoodRiders.class));
        cards.add(new SetCardInfo("Norwood Warrior", 80, Rarity.COMMON, mage.cards.n.NorwoodWarrior.class));
        cards.add(new SetCardInfo("Obsidian Giant", 109, Rarity.UNCOMMON, mage.cards.o.ObsidianGiant.class));
        cards.add(new SetCardInfo("Ogre Arsonist", 110, Rarity.UNCOMMON, mage.cards.o.OgreArsonist.class));
        cards.add(new SetCardInfo("Ogre Berserker", 111, Rarity.COMMON, mage.cards.o.OgreBerserker.class));
        cards.add(new SetCardInfo("Ogre Taskmaster", 112, Rarity.UNCOMMON, mage.cards.o.OgreTaskmaster.class));
        cards.add(new SetCardInfo("Ogre Warrior", 113, Rarity.COMMON, mage.cards.o.OgreWarrior.class));
        cards.add(new SetCardInfo("Path of Peace", 138, Rarity.COMMON, mage.cards.p.PathOfPeace.class));
        cards.add(new SetCardInfo("Plains", 160, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 161, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 162, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plated Wurm", 81, Rarity.COMMON, mage.cards.p.PlatedWurm.class));
        cards.add(new SetCardInfo("Predatory Nightstalker", 22, Rarity.UNCOMMON, mage.cards.p.PredatoryNightstalker.class));
        cards.add(new SetCardInfo("Prowling Nightstalker", 23, Rarity.COMMON, mage.cards.p.ProwlingNightstalker.class));
        cards.add(new SetCardInfo("Raging Goblin", 114, Rarity.COMMON, mage.cards.r.RagingGoblin.class));
        cards.add(new SetCardInfo("Raiding Nightstalker", 24, Rarity.COMMON, mage.cards.r.RaidingNightstalker.class));
        cards.add(new SetCardInfo("Rain of Daggers", 25, Rarity.RARE, mage.cards.r.RainOfDaggers.class));
        cards.add(new SetCardInfo("Raise Dead", 26, Rarity.COMMON, mage.cards.r.RaiseDead.class));
        cards.add(new SetCardInfo("Ravenous Rats", 27, Rarity.COMMON, mage.cards.r.RavenousRats.class));
        cards.add(new SetCardInfo("Razorclaw Bear", 82, Rarity.RARE, mage.cards.r.RazorclawBear.class));
        cards.add(new SetCardInfo("Righteous Charge", 140, Rarity.COMMON, mage.cards.r.RighteousCharge.class));
        cards.add(new SetCardInfo("Righteous Fury", 141, Rarity.RARE, mage.cards.r.RighteousFury.class));
        cards.add(new SetCardInfo("River Bear", 84, Rarity.UNCOMMON, mage.cards.r.RiverBear.class));
        cards.add(new SetCardInfo("Salvage", 85, Rarity.COMMON, mage.cards.s.Salvage.class));
        cards.add(new SetCardInfo("Screeching Drake", 44, Rarity.COMMON, mage.cards.s.ScreechingDrake.class));
        cards.add(new SetCardInfo("Sea Drake", 45, Rarity.UNCOMMON, mage.cards.s.SeaDrake.class));
        cards.add(new SetCardInfo("Sleight of Hand", 46, Rarity.COMMON, mage.cards.s.SleightOfHand.class));
        cards.add(new SetCardInfo("Spitting Earth", 116, Rarity.COMMON, mage.cards.s.SpittingEarth.class));
        cards.add(new SetCardInfo("Steam Catapult", 142, Rarity.RARE, mage.cards.s.SteamCatapult.class));
        cards.add(new SetCardInfo("Steam Frigate", 47, Rarity.COMMON, mage.cards.s.SteamFrigate.class));
        cards.add(new SetCardInfo("Stone Rain", 117, Rarity.COMMON, mage.cards.s.StoneRain.class));
        cards.add(new SetCardInfo("Swamp", 163, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 164, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 165, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swarm of Rats", 29, Rarity.COMMON, mage.cards.s.SwarmOfRats.class));
        cards.add(new SetCardInfo("Sylvan Basilisk", 86, Rarity.RARE, mage.cards.s.SylvanBasilisk.class));
        cards.add(new SetCardInfo("Sylvan Yeti", 87, Rarity.RARE, mage.cards.s.SylvanYeti.class));
        cards.add(new SetCardInfo("Talas Air Ship", 48, Rarity.COMMON, mage.cards.t.TalasAirShip.class));
        cards.add(new SetCardInfo("Talas Explorer", 49, Rarity.COMMON, mage.cards.t.TalasExplorer.class));
        cards.add(new SetCardInfo("Talas Merchant", 50, Rarity.COMMON, mage.cards.t.TalasMerchant.class));
        cards.add(new SetCardInfo("Talas Researcher", 51, Rarity.RARE, mage.cards.t.TalasResearcher.class));
        cards.add(new SetCardInfo("Talas Scout", 52, Rarity.COMMON, mage.cards.t.TalasScout.class));
        cards.add(new SetCardInfo("Talas Warrior", 53, Rarity.RARE, mage.cards.t.TalasWarrior.class));
        cards.add(new SetCardInfo("Temple Acolyte", 143, Rarity.COMMON, mage.cards.t.TempleAcolyte.class));
        cards.add(new SetCardInfo("Temple Elder", 144, Rarity.UNCOMMON, mage.cards.t.TempleElder.class));
        cards.add(new SetCardInfo("Temporal Manipulation", 54, Rarity.RARE, mage.cards.t.TemporalManipulation.class));
        cards.add(new SetCardInfo("Theft of Dreams", 55, Rarity.UNCOMMON, mage.cards.t.TheftOfDreams.class));
        cards.add(new SetCardInfo("Tidal Surge", 56, Rarity.COMMON, mage.cards.t.TidalSurge.class));
        cards.add(new SetCardInfo("Time Ebb", 57, Rarity.COMMON, mage.cards.t.TimeEbb.class));
        cards.add(new SetCardInfo("Touch of Brilliance", 58, Rarity.COMMON, mage.cards.t.TouchOfBrilliance.class));
        cards.add(new SetCardInfo("Town Sentry", 145, Rarity.COMMON, mage.cards.t.TownSentry.class));
        cards.add(new SetCardInfo("Tree Monkey", 88, Rarity.COMMON, mage.cards.t.TreeMonkey.class));
        cards.add(new SetCardInfo("Tremor", 118, Rarity.COMMON, mage.cards.t.Tremor.class));
        cards.add(new SetCardInfo("Trokin High Guard", 146, Rarity.COMMON, mage.cards.t.TrokinHighGuard.class));
        cards.add(new SetCardInfo("Undo", 59, Rarity.UNCOMMON, mage.cards.u.Undo.class));
        cards.add(new SetCardInfo("Untamed Wilds", 89, Rarity.UNCOMMON, mage.cards.u.UntamedWilds.class));
        cards.add(new SetCardInfo("Vampiric Spirit", 30, Rarity.RARE, mage.cards.v.VampiricSpirit.class));
        cards.add(new SetCardInfo("Vengeance", 147, Rarity.UNCOMMON, mage.cards.v.Vengeance.class));
        cards.add(new SetCardInfo("Volcanic Hammer", 119, Rarity.COMMON, mage.cards.v.VolcanicHammer.class));
        cards.add(new SetCardInfo("Volunteer Militia", 148, Rarity.COMMON, mage.cards.v.VolunteerMilitia.class));
        cards.add(new SetCardInfo("Wildfire", 120, Rarity.RARE, mage.cards.w.Wildfire.class));
        cards.add(new SetCardInfo("Wild Griffin", 150, Rarity.COMMON, mage.cards.w.WildGriffin.class));
        cards.add(new SetCardInfo("Wild Ox", 90, Rarity.UNCOMMON, mage.cards.w.WildOx.class));
        cards.add(new SetCardInfo("Wind Sail", 60, Rarity.COMMON, mage.cards.w.WindSail.class));
    }
}