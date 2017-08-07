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
import mage.cards.n.NafsAsp;
import mage.cards.p.Piety;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author North
 */
public class FourthEdition extends ExpansionSet {

    private static final FourthEdition instance = new FourthEdition();

    public static FourthEdition getInstance() {
        return instance;
    }

    private FourthEdition() {
        super("Fourth Edition", "4ED", ExpansionSet.buildDate(1995, 3, 1), SetType.CORE);
        this.hasBoosters = true;
        this.numBoosterLands = 0;
        this.numBoosterCommon = 11;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 0;
        cards.add(new SetCardInfo("Abomination", 1, Rarity.UNCOMMON, mage.cards.a.Abomination.class));
        cards.add(new SetCardInfo("Air Elemental", 59, Rarity.UNCOMMON, mage.cards.a.AirElemental.class));
        cards.add(new SetCardInfo("Aladdin's Lamp", 309, Rarity.RARE, mage.cards.a.AladdinsLamp.class));
        cards.add(new SetCardInfo("Aladdin's Ring", 310, Rarity.RARE, mage.cards.a.AladdinsRing.class));
        cards.add(new SetCardInfo("Ali Baba", 193, Rarity.UNCOMMON, mage.cards.a.AliBaba.class));
        cards.add(new SetCardInfo("Amrou Kithkin", 252, Rarity.COMMON, mage.cards.a.AmrouKithkin.class));
        cards.add(new SetCardInfo("Amulet of Kroog", 311, Rarity.COMMON, mage.cards.a.AmuletOfKroog.class));
        cards.add(new SetCardInfo("Angry Mob", 253, Rarity.UNCOMMON, mage.cards.a.AngryMob.class));
        cards.add(new SetCardInfo("Animate Artifact", 60, Rarity.UNCOMMON, mage.cards.a.AnimateArtifact.class));
        cards.add(new SetCardInfo("Animate Dead", 2, Rarity.UNCOMMON, mage.cards.a.AnimateDead.class));
        cards.add(new SetCardInfo("Animate Wall", 254, Rarity.RARE, mage.cards.a.AnimateWall.class));
        cards.add(new SetCardInfo("Ankh of Mishra", 312, Rarity.RARE, mage.cards.a.AnkhOfMishra.class));
        cards.add(new SetCardInfo("Apprentice Wizard", 61, Rarity.COMMON, mage.cards.a.ApprenticeWizard.class));
        cards.add(new SetCardInfo("Armageddon", 255, Rarity.RARE, mage.cards.a.Armageddon.class));
        cards.add(new SetCardInfo("Armageddon Clock", 313, Rarity.RARE, mage.cards.a.ArmageddonClock.class));
        cards.add(new SetCardInfo("Ashes to Ashes", 3, Rarity.UNCOMMON, mage.cards.a.AshesToAshes.class));
        cards.add(new SetCardInfo("Ashnod's Battle Gear", 314, Rarity.UNCOMMON, mage.cards.a.AshnodsBattleGear.class));
        cards.add(new SetCardInfo("Aspect of Wolf", 117, Rarity.RARE, mage.cards.a.AspectOfWolf.class));
        cards.add(new SetCardInfo("Backfire", 62, Rarity.UNCOMMON, mage.cards.b.Backfire.class));
        cards.add(new SetCardInfo("Bad Moon", 4, Rarity.RARE, mage.cards.b.BadMoon.class));
        cards.add(new SetCardInfo("Balance", 256, Rarity.RARE, mage.cards.b.Balance.class));
        cards.add(new SetCardInfo("Ball Lightning", 194, Rarity.RARE, mage.cards.b.BallLightning.class));
        cards.add(new SetCardInfo("Bird Maiden", 195, Rarity.COMMON, mage.cards.b.BirdMaiden.class));
        cards.add(new SetCardInfo("Birds of Paradise", 118, Rarity.RARE, mage.cards.b.BirdsOfParadise.class));
        cards.add(new SetCardInfo("Black Knight", 5, Rarity.UNCOMMON, mage.cards.b.BlackKnight.class));
        cards.add(new SetCardInfo("Black Vise", 317, Rarity.UNCOMMON, mage.cards.b.BlackVise.class));
        cards.add(new SetCardInfo("Black Ward", 258, Rarity.UNCOMMON, mage.cards.b.BlackWard.class));
        cards.add(new SetCardInfo("Blessing", 259, Rarity.RARE, mage.cards.b.Blessing.class));
        cards.add(new SetCardInfo("Blight", 6, Rarity.UNCOMMON, mage.cards.b.Blight.class));
        cards.add(new SetCardInfo("Blood Lust", 196, Rarity.COMMON, mage.cards.b.BloodLust.class));
        cards.add(new SetCardInfo("Blue Elemental Blast", 63, Rarity.COMMON, mage.cards.b.BlueElementalBlast.class));
        cards.add(new SetCardInfo("Blue Ward", 260, Rarity.UNCOMMON, mage.cards.b.BlueWard.class));
        cards.add(new SetCardInfo("Bog Imp", 7, Rarity.COMMON, mage.cards.b.BogImp.class));
        cards.add(new SetCardInfo("Bog Wraith", 8, Rarity.UNCOMMON, mage.cards.b.BogWraith.class));
        cards.add(new SetCardInfo("Bottle of Suleiman", 319, Rarity.RARE, mage.cards.b.BottleOfSuleiman.class));
        cards.add(new SetCardInfo("Brainwash", 261, Rarity.COMMON, mage.cards.b.Brainwash.class));
        cards.add(new SetCardInfo("Brass Man", 320, Rarity.UNCOMMON, mage.cards.b.BrassMan.class));
        cards.add(new SetCardInfo("Brothers of Fire", 197, Rarity.COMMON, mage.cards.b.BrothersOfFire.class));
        cards.add(new SetCardInfo("Burrowing", 198, Rarity.UNCOMMON, mage.cards.b.Burrowing.class));
        cards.add(new SetCardInfo("Carnivorous Plant", 119, Rarity.COMMON, mage.cards.c.CarnivorousPlant.class));
        cards.add(new SetCardInfo("Carrion Ants", 9, Rarity.UNCOMMON, mage.cards.c.CarrionAnts.class));
        cards.add(new SetCardInfo("Castle", 262, Rarity.UNCOMMON, mage.cards.c.Castle.class));
        cards.add(new SetCardInfo("Cave People", 199, Rarity.UNCOMMON, mage.cards.c.CavePeople.class));
        cards.add(new SetCardInfo("Celestial Prism", 322, Rarity.UNCOMMON, mage.cards.c.CelestialPrism.class));
        cards.add(new SetCardInfo("Channel", 120, Rarity.UNCOMMON, mage.cards.c.Channel.class));
        cards.add(new SetCardInfo("Chaoslace", 200, Rarity.RARE, mage.cards.c.Chaoslace.class));
        cards.add(new SetCardInfo("Circle of Protection: Artifacts", 263, Rarity.COMMON, mage.cards.c.CircleOfProtectionArtifacts.class));
        cards.add(new SetCardInfo("Circle of Protection: Black", 264, Rarity.COMMON, mage.cards.c.CircleOfProtectionBlack.class));
        cards.add(new SetCardInfo("Circle of Protection: Blue", 265, Rarity.COMMON, mage.cards.c.CircleOfProtectionBlue.class));
        cards.add(new SetCardInfo("Circle of Protection: Green", 266, Rarity.COMMON, mage.cards.c.CircleOfProtectionGreen.class));
        cards.add(new SetCardInfo("Circle of Protection: Red", 267, Rarity.COMMON, mage.cards.c.CircleOfProtectionRed.class));
        cards.add(new SetCardInfo("Circle of Protection: White", 268, Rarity.COMMON, mage.cards.c.CircleOfProtectionWhite.class));
        cards.add(new SetCardInfo("Clay Statue", 323, Rarity.COMMON, mage.cards.c.ClayStatue.class));
        cards.add(new SetCardInfo("Clockwork Avian", 324, Rarity.RARE, mage.cards.c.ClockworkAvian.class));
        cards.add(new SetCardInfo("Clockwork Beast", 325, Rarity.RARE, mage.cards.c.ClockworkBeast.class));
        cards.add(new SetCardInfo("Cockatrice", 121, Rarity.RARE, mage.cards.c.Cockatrice.class));
        cards.add(new SetCardInfo("Colossus of Sardia", 326, Rarity.RARE, mage.cards.c.ColossusOfSardia.class));
        cards.add(new SetCardInfo("Conservator", 327, Rarity.UNCOMMON, mage.cards.c.Conservator.class));
        cards.add(new SetCardInfo("Control Magic", 64, Rarity.UNCOMMON, mage.cards.c.ControlMagic.class));
        cards.add(new SetCardInfo("Conversion", 269, Rarity.UNCOMMON, mage.cards.c.Conversion.class));
        cards.add(new SetCardInfo("Coral Helm", 328, Rarity.RARE, mage.cards.c.CoralHelm.class));
        cards.add(new SetCardInfo("Cosmic Horror", 10, Rarity.RARE, mage.cards.c.CosmicHorror.class));
        cards.add(new SetCardInfo("Counterspell", 65, Rarity.UNCOMMON, mage.cards.c.Counterspell.class));
        cards.add(new SetCardInfo("Craw Wurm", 122, Rarity.COMMON, mage.cards.c.CrawWurm.class));
        cards.add(new SetCardInfo("Creature Bond", 66, Rarity.COMMON, mage.cards.c.CreatureBond.class));
        cards.add(new SetCardInfo("Crimson Manticore", 201, Rarity.RARE, mage.cards.c.CrimsonManticore.class));
        cards.add(new SetCardInfo("Crumble", 123, Rarity.UNCOMMON, mage.cards.c.Crumble.class));
        cards.add(new SetCardInfo("Crusade", 270, Rarity.RARE, mage.cards.c.Crusade.class));
        cards.add(new SetCardInfo("Crystal Rod", 329, Rarity.UNCOMMON, mage.cards.c.CrystalRod.class));
        cards.add(new SetCardInfo("Cursed Land", 11, Rarity.UNCOMMON, mage.cards.c.CursedLand.class));
        cards.add(new SetCardInfo("Cursed Rack", 330, Rarity.UNCOMMON, mage.cards.c.CursedRack.class));
        cards.add(new SetCardInfo("Cyclopean Mummy", 12, Rarity.COMMON, mage.cards.c.CyclopeanMummy.class));
        cards.add(new SetCardInfo("Dancing Scimitar", 331, Rarity.RARE, mage.cards.d.DancingScimitar.class));
        cards.add(new SetCardInfo("Dark Ritual", 13, Rarity.COMMON, mage.cards.d.DarkRitual.class));
        cards.add(new SetCardInfo("Deathgrip", 14, Rarity.UNCOMMON, mage.cards.d.Deathgrip.class));
        cards.add(new SetCardInfo("Deathlace", 15, Rarity.RARE, mage.cards.d.Deathlace.class));
        cards.add(new SetCardInfo("Death Ward", 271, Rarity.COMMON, mage.cards.d.DeathWard.class));
        cards.add(new SetCardInfo("Desert Twister", 124, Rarity.UNCOMMON, mage.cards.d.DesertTwister.class));
        cards.add(new SetCardInfo("Detonate", 202, Rarity.UNCOMMON, mage.cards.d.Detonate.class));
        cards.add(new SetCardInfo("Diabolic Machine", 332, Rarity.UNCOMMON, mage.cards.d.DiabolicMachine.class));
        cards.add(new SetCardInfo("Dingus Egg", 333, Rarity.RARE, mage.cards.d.DingusEgg.class));
        cards.add(new SetCardInfo("Disenchant", 272, Rarity.COMMON, mage.cards.d.Disenchant.class));
        cards.add(new SetCardInfo("Disintegrate", 203, Rarity.COMMON, mage.cards.d.Disintegrate.class));
        cards.add(new SetCardInfo("Disrupting Scepter", 334, Rarity.RARE, mage.cards.d.DisruptingScepter.class));
        cards.add(new SetCardInfo("Divine Transformation", 273, Rarity.UNCOMMON, mage.cards.d.DivineTransformation.class));
        cards.add(new SetCardInfo("Dragon Engine", 335, Rarity.RARE, mage.cards.d.DragonEngine.class));
        cards.add(new SetCardInfo("Dragon Whelp", 204, Rarity.UNCOMMON, mage.cards.d.DragonWhelp.class));
        cards.add(new SetCardInfo("Drain Life", 16, Rarity.COMMON, mage.cards.d.DrainLife.class));
        cards.add(new SetCardInfo("Drudge Skeletons", 17, Rarity.COMMON, mage.cards.d.DrudgeSkeletons.class));
        cards.add(new SetCardInfo("Durkwood Boars", 125, Rarity.COMMON, mage.cards.d.DurkwoodBoars.class));
        cards.add(new SetCardInfo("Dwarven Warriors", 205, Rarity.COMMON, mage.cards.d.DwarvenWarriors.class));
        cards.add(new SetCardInfo("Earth Elemental", 206, Rarity.UNCOMMON, mage.cards.e.EarthElemental.class));
        cards.add(new SetCardInfo("Earthquake", 207, Rarity.RARE, mage.cards.e.Earthquake.class));
        cards.add(new SetCardInfo("Ebony Horse", 336, Rarity.RARE, mage.cards.e.EbonyHorse.class));
        cards.add(new SetCardInfo("El-Hajjaj", 18, Rarity.RARE, mage.cards.e.ElHajjaj.class));
        cards.add(new SetCardInfo("Elven Riders", 126, Rarity.UNCOMMON, mage.cards.e.ElvenRiders.class));
        cards.add(new SetCardInfo("Elvish Archers", 127, Rarity.RARE, mage.cards.e.ElvishArchers.class));
        cards.add(new SetCardInfo("Energy Flux", 68, Rarity.UNCOMMON, mage.cards.e.EnergyFlux.class));
        cards.add(new SetCardInfo("Energy Tap", 69, Rarity.COMMON, mage.cards.e.EnergyTap.class));
        cards.add(new SetCardInfo("Erg Raiders", 19, Rarity.COMMON, mage.cards.e.ErgRaiders.class));
        cards.add(new SetCardInfo("Eternal Warrior", 208, Rarity.COMMON, mage.cards.e.EternalWarrior.class));
        cards.add(new SetCardInfo("Evil Presence", 20, Rarity.UNCOMMON, mage.cards.e.EvilPresence.class));
        cards.add(new SetCardInfo("Eye for an Eye", 275, Rarity.RARE, mage.cards.e.EyeForAnEye.class));
        cards.add(new SetCardInfo("Fear", 21, Rarity.COMMON, mage.cards.f.Fear.class));
        cards.add(new SetCardInfo("Feedback", 71, Rarity.UNCOMMON, mage.cards.f.Feedback.class));
        cards.add(new SetCardInfo("Fellwar Stone", 337, Rarity.UNCOMMON, mage.cards.f.FellwarStone.class));
        cards.add(new SetCardInfo("Fireball", 210, Rarity.COMMON, mage.cards.f.Fireball.class));
        cards.add(new SetCardInfo("Firebreathing", 211, Rarity.COMMON, mage.cards.f.Firebreathing.class));
        cards.add(new SetCardInfo("Fire Elemental", 209, Rarity.UNCOMMON, mage.cards.f.FireElemental.class));
        cards.add(new SetCardInfo("Fissure", 212, Rarity.COMMON, mage.cards.f.Fissure.class));
        cards.add(new SetCardInfo("Flashfires", 213, Rarity.UNCOMMON, mage.cards.f.Flashfires.class));
        cards.add(new SetCardInfo("Flight", 72, Rarity.COMMON, mage.cards.f.Flight.class));
        cards.add(new SetCardInfo("Flood", 73, Rarity.COMMON, mage.cards.f.Flood.class));
        cards.add(new SetCardInfo("Flying Carpet", 338, Rarity.RARE, mage.cards.f.FlyingCarpet.class));
        cards.add(new SetCardInfo("Fog", 128, Rarity.COMMON, mage.cards.f.Fog.class));
        cards.add(new SetCardInfo("Force of Nature", 129, Rarity.RARE, mage.cards.f.ForceOfNature.class));
        cards.add(new SetCardInfo("Forest", 175, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 176, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 177, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Frozen Shade", 22, Rarity.COMMON, mage.cards.f.FrozenShade.class));
        cards.add(new SetCardInfo("Fungusaur", 130, Rarity.RARE, mage.cards.f.Fungusaur.class));
        cards.add(new SetCardInfo("Gaea's Liege", 131, Rarity.RARE, mage.cards.g.GaeasLiege.class));
        cards.add(new SetCardInfo("Gaseous Form", 74, Rarity.COMMON, mage.cards.g.GaseousForm.class));
        cards.add(new SetCardInfo("Ghost Ship", 75, Rarity.UNCOMMON, mage.cards.g.GhostShip.class));
        cards.add(new SetCardInfo("Giant Growth", 132, Rarity.COMMON, mage.cards.g.GiantGrowth.class));
        cards.add(new SetCardInfo("Giant Spider", 133, Rarity.COMMON, mage.cards.g.GiantSpider.class));
        cards.add(new SetCardInfo("Giant Strength", 214, Rarity.COMMON, mage.cards.g.GiantStrength.class));
        cards.add(new SetCardInfo("Giant Tortoise", 76, Rarity.COMMON, mage.cards.g.GiantTortoise.class));
        cards.add(new SetCardInfo("Glasses of Urza", 339, Rarity.UNCOMMON, mage.cards.g.GlassesOfUrza.class));
        cards.add(new SetCardInfo("Gloom", 23, Rarity.UNCOMMON, mage.cards.g.Gloom.class));
        cards.add(new SetCardInfo("Goblin Balloon Brigade", 215, Rarity.UNCOMMON, mage.cards.g.GoblinBalloonBrigade.class));
        cards.add(new SetCardInfo("Goblin King", 216, Rarity.RARE, mage.cards.g.GoblinKing.class));
        cards.add(new SetCardInfo("Grapeshot Catapult", 340, Rarity.COMMON, mage.cards.g.GrapeshotCatapult.class));
        cards.add(new SetCardInfo("Gray Ogre", 218, Rarity.COMMON, mage.cards.g.GrayOgre.class));
        cards.add(new SetCardInfo("Greed", 24, Rarity.RARE, mage.cards.g.Greed.class));
        cards.add(new SetCardInfo("Green Ward", 277, Rarity.UNCOMMON, mage.cards.g.GreenWard.class));
        cards.add(new SetCardInfo("Grizzly Bears", 134, Rarity.COMMON, mage.cards.g.GrizzlyBears.class));
        cards.add(new SetCardInfo("Healing Salve", 278, Rarity.COMMON, mage.cards.h.HealingSalve.class));
        cards.add(new SetCardInfo("Hill Giant", 219, Rarity.COMMON, mage.cards.h.HillGiant.class));
        cards.add(new SetCardInfo("Holy Armor", 279, Rarity.COMMON, mage.cards.h.HolyArmor.class));
        cards.add(new SetCardInfo("Holy Strength", 280, Rarity.COMMON, mage.cards.h.HolyStrength.class));
        cards.add(new SetCardInfo("Howl from Beyond", 25, Rarity.COMMON, mage.cards.h.HowlFromBeyond.class));
        cards.add(new SetCardInfo("Howling Mine", 343, Rarity.RARE, mage.cards.h.HowlingMine.class));
        cards.add(new SetCardInfo("Hurkyl's Recall", 77, Rarity.RARE, mage.cards.h.HurkylsRecall.class));
        cards.add(new SetCardInfo("Hurloon Minotaur", 220, Rarity.COMMON, mage.cards.h.HurloonMinotaur.class));
        cards.add(new SetCardInfo("Hurricane", 135, Rarity.UNCOMMON, mage.cards.h.Hurricane.class));
        cards.add(new SetCardInfo("Hurr Jackal", 221, Rarity.RARE, mage.cards.h.HurrJackal.class));
        cards.add(new SetCardInfo("Hypnotic Specter", 26, Rarity.UNCOMMON, mage.cards.h.HypnoticSpecter.class));
        cards.add(new SetCardInfo("Immolation", 222, Rarity.COMMON, mage.cards.i.Immolation.class));
        cards.add(new SetCardInfo("Inferno", 223, Rarity.RARE, mage.cards.i.Inferno.class));
        cards.add(new SetCardInfo("Instill Energy", 136, Rarity.UNCOMMON, mage.cards.i.InstillEnergy.class));
        cards.add(new SetCardInfo("Ironclaw Orcs", 224, Rarity.COMMON, mage.cards.i.IronclawOrcs.class));
        cards.add(new SetCardInfo("Ironroot Treefolk", 137, Rarity.COMMON, mage.cards.i.IronrootTreefolk.class));
        cards.add(new SetCardInfo("Iron Star", 344, Rarity.UNCOMMON, mage.cards.i.IronStar.class));
        cards.add(new SetCardInfo("Island", 178, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 179, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 180, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island Fish Jasconius", 78, Rarity.RARE, mage.cards.i.IslandFishJasconius.class));
        cards.add(new SetCardInfo("Island Sanctuary", 281, Rarity.RARE, mage.cards.i.IslandSanctuary.class));
        cards.add(new SetCardInfo("Ivory Cup", 345, Rarity.UNCOMMON, mage.cards.i.IvoryCup.class));
        cards.add(new SetCardInfo("Ivory Tower", 346, Rarity.RARE, mage.cards.i.IvoryTower.class));
        cards.add(new SetCardInfo("Jade Monolith", 347, Rarity.RARE, mage.cards.j.JadeMonolith.class));
        cards.add(new SetCardInfo("Jandor's Saddlebags", 348, Rarity.RARE, mage.cards.j.JandorsSaddlebags.class));
        cards.add(new SetCardInfo("Jayemdae Tome", 349, Rarity.RARE, mage.cards.j.JayemdaeTome.class));
        cards.add(new SetCardInfo("Jump", 79, Rarity.COMMON, mage.cards.j.Jump.class));
        cards.add(new SetCardInfo("Junún Efreet", 27, Rarity.UNCOMMON, mage.cards.j.JununEfreet.class));
        cards.add(new SetCardInfo("Karma", 282, Rarity.UNCOMMON, mage.cards.k.Karma.class));
        cards.add(new SetCardInfo("Keldon Warlord", 225, Rarity.UNCOMMON, mage.cards.k.KeldonWarlord.class));
        cards.add(new SetCardInfo("Killer Bees", 138, Rarity.UNCOMMON, mage.cards.k.KillerBees.class));
        cards.add(new SetCardInfo("Kismet", 283, Rarity.UNCOMMON, mage.cards.k.Kismet.class));
        cards.add(new SetCardInfo("Kormus Bell", 350, Rarity.RARE, mage.cards.k.KormusBell.class));
        cards.add(new SetCardInfo("Land Leeches", 139, Rarity.COMMON, mage.cards.l.LandLeeches.class));
        cards.add(new SetCardInfo("Land Tax", 284, Rarity.RARE, mage.cards.l.LandTax.class));
        cards.add(new SetCardInfo("Ley Druid", 140, Rarity.UNCOMMON, mage.cards.l.LeyDruid.class));
        cards.add(new SetCardInfo("Library of Leng", 351, Rarity.UNCOMMON, mage.cards.l.LibraryOfLeng.class));
        cards.add(new SetCardInfo("Lifeforce", 141, Rarity.UNCOMMON, mage.cards.l.Lifeforce.class));
        cards.add(new SetCardInfo("Lifelace", 142, Rarity.RARE, mage.cards.l.Lifelace.class));
        cards.add(new SetCardInfo("Lifetap", 81, Rarity.UNCOMMON, mage.cards.l.Lifetap.class));
        cards.add(new SetCardInfo("Lightning Bolt", 226, Rarity.COMMON, mage.cards.l.LightningBolt.class));
        cards.add(new SetCardInfo("Living Artifact", 143, Rarity.RARE, mage.cards.l.LivingArtifact.class));
        cards.add(new SetCardInfo("Living Lands", 144, Rarity.RARE, mage.cards.l.LivingLands.class));
        cards.add(new SetCardInfo("Llanowar Elves", 145, Rarity.COMMON, mage.cards.l.LlanowarElves.class));
        cards.add(new SetCardInfo("Lord of Atlantis", 82, Rarity.RARE, mage.cards.l.LordOfAtlantis.class));
        cards.add(new SetCardInfo("Lord of the Pit", 28, Rarity.RARE, mage.cards.l.LordOfThePit.class));
        cards.add(new SetCardInfo("Lost Soul", 29, Rarity.COMMON, mage.cards.l.LostSoul.class));
        cards.add(new SetCardInfo("Lure", 146, Rarity.UNCOMMON, mage.cards.l.Lure.class));
        cards.add(new SetCardInfo("Magnetic Mountain", 227, Rarity.RARE, mage.cards.m.MagneticMountain.class));
        cards.add(new SetCardInfo("Mahamoti Djinn", 84, Rarity.RARE, mage.cards.m.MahamotiDjinn.class));
        cards.add(new SetCardInfo("Manabarbs", 230, Rarity.RARE, mage.cards.m.Manabarbs.class));
        cards.add(new SetCardInfo("Mana Clash", 228, Rarity.RARE, mage.cards.m.ManaClash.class));
        cards.add(new SetCardInfo("Mana Flare", 229, Rarity.RARE, mage.cards.m.ManaFlare.class));
        cards.add(new SetCardInfo("Mana Short", 85, Rarity.RARE, mage.cards.m.ManaShort.class));
        cards.add(new SetCardInfo("Mana Vault", 352, Rarity.RARE, mage.cards.m.ManaVault.class));
        cards.add(new SetCardInfo("Marsh Gas", 30, Rarity.COMMON, mage.cards.m.MarshGas.class));
        cards.add(new SetCardInfo("Marsh Viper", 147, Rarity.COMMON, mage.cards.m.MarshViper.class));
        cards.add(new SetCardInfo("Meekstone", 353, Rarity.RARE, mage.cards.m.Meekstone.class));
        cards.add(new SetCardInfo("Merfolk of the Pearl Trident", 86, Rarity.COMMON, mage.cards.m.MerfolkOfThePearlTrident.class));
        cards.add(new SetCardInfo("Millstone", 354, Rarity.RARE, mage.cards.m.Millstone.class));
        cards.add(new SetCardInfo("Mind Twist", 31, Rarity.RARE, mage.cards.m.MindTwist.class));
        cards.add(new SetCardInfo("Mishra's Factory", 181, Rarity.UNCOMMON, mage.cards.m.MishrasFactory.class));
        cards.add(new SetCardInfo("Mons's Goblin Raiders", 231, Rarity.COMMON, mage.cards.m.MonssGoblinRaiders.class));
        cards.add(new SetCardInfo("Morale", 288, Rarity.COMMON, mage.cards.m.Morale.class));
        cards.add(new SetCardInfo("Mountain", 182, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 183, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 184, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Murk Dwellers", 32, Rarity.COMMON, mage.cards.m.MurkDwellers.class));
        cards.add(new SetCardInfo("Nafs Asp", 148, Rarity.COMMON, NafsAsp.class));
        cards.add(new SetCardInfo("Nether Shadow", 33, Rarity.RARE, mage.cards.n.NetherShadow.class));
        cards.add(new SetCardInfo("Nevinyrral's Disk", 356, Rarity.RARE, mage.cards.n.NevinyrralsDisk.class));
        cards.add(new SetCardInfo("Nightmare", 34, Rarity.RARE, mage.cards.n.Nightmare.class));
        cards.add(new SetCardInfo("Northern Paladin", 287, Rarity.RARE, mage.cards.n.NorthernPaladin.class));
        cards.add(new SetCardInfo("Oasis", 185, Rarity.UNCOMMON, mage.cards.o.Oasis.class));
        cards.add(new SetCardInfo("Obsianus Golem", 357, Rarity.UNCOMMON, mage.cards.o.ObsianusGolem.class));
        cards.add(new SetCardInfo("Onulet", 358, Rarity.RARE, mage.cards.o.Onulet.class));
        cards.add(new SetCardInfo("Orcish Artillery", 232, Rarity.UNCOMMON, mage.cards.o.OrcishArtillery.class));
        cards.add(new SetCardInfo("Orcish Oriflamme", 233, Rarity.UNCOMMON, mage.cards.o.OrcishOriflamme.class));
        cards.add(new SetCardInfo("Ornithopter", 359, Rarity.UNCOMMON, mage.cards.o.Ornithopter.class));
        cards.add(new SetCardInfo("Osai Vultures", 288, Rarity.UNCOMMON, mage.cards.o.OsaiVultures.class));
        cards.add(new SetCardInfo("Paralyze", 35, Rarity.COMMON, mage.cards.p.Paralyze.class));
        cards.add(new SetCardInfo("Pearled Unicorn", 289, Rarity.COMMON, mage.cards.p.PearledUnicorn.class));
        cards.add(new SetCardInfo("Personal Incarnation", 290, Rarity.RARE, mage.cards.p.PersonalIncarnation.class));
        cards.add(new SetCardInfo("Pestilence", 36, Rarity.COMMON, mage.cards.p.Pestilence.class));
        cards.add(new SetCardInfo("Phantasmal Forces", 88, Rarity.UNCOMMON, mage.cards.p.PhantasmalForces.class));
        cards.add(new SetCardInfo("Phantasmal Terrain", 89, Rarity.COMMON, mage.cards.p.PhantasmalTerrain.class));
        cards.add(new SetCardInfo("Phantom Monster", 90, Rarity.UNCOMMON, mage.cards.p.PhantomMonster.class));
        cards.add(new SetCardInfo("Piety", 291, Rarity.COMMON, Piety.class));
        cards.add(new SetCardInfo("Pirate Ship", 91, Rarity.RARE, mage.cards.p.PirateShip.class));
        cards.add(new SetCardInfo("Pit Scorpion", 37, Rarity.COMMON, mage.cards.p.PitScorpion.class));
        cards.add(new SetCardInfo("Plague Rats", 38, Rarity.COMMON, mage.cards.p.PlagueRats.class));
        cards.add(new SetCardInfo("Plains", 186, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 187, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 188, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Power Sink", 93, Rarity.COMMON, mage.cards.p.PowerSink.class));
        cards.add(new SetCardInfo("Power Surge", 234, Rarity.RARE, mage.cards.p.PowerSurge.class));
        cards.add(new SetCardInfo("Pradesh Gypsies", 149, Rarity.COMMON, mage.cards.p.PradeshGypsies.class));
        cards.add(new SetCardInfo("Primal Clay", 360, Rarity.RARE, mage.cards.p.PrimalClay.class));
        cards.add(new SetCardInfo("Prodigal Sorcerer", 94, Rarity.COMMON, mage.cards.p.ProdigalSorcerer.class));
        cards.add(new SetCardInfo("Psychic Venom", 96, Rarity.COMMON, mage.cards.p.PsychicVenom.class));
        cards.add(new SetCardInfo("Purelace", 293, Rarity.RARE, mage.cards.p.Purelace.class));
        cards.add(new SetCardInfo("Pyrotechnics", 235, Rarity.UNCOMMON, mage.cards.p.Pyrotechnics.class));
        cards.add(new SetCardInfo("Radjan Spirit", 150, Rarity.UNCOMMON, mage.cards.r.RadjanSpirit.class));
        cards.add(new SetCardInfo("Rag Man", 39, Rarity.RARE, mage.cards.r.RagMan.class));
        cards.add(new SetCardInfo("Raise Dead", 40, Rarity.COMMON, mage.cards.r.RaiseDead.class));
        cards.add(new SetCardInfo("Red Elemental Blast", 236, Rarity.COMMON, mage.cards.r.RedElementalBlast.class));
        cards.add(new SetCardInfo("Red Ward", 294, Rarity.UNCOMMON, mage.cards.r.RedWard.class));
        cards.add(new SetCardInfo("Regeneration", 152, Rarity.COMMON, mage.cards.r.Regeneration.class));
        cards.add(new SetCardInfo("Reverse Damage", 295, Rarity.RARE, mage.cards.r.ReverseDamage.class));
        cards.add(new SetCardInfo("Righteousness", 296, Rarity.RARE, mage.cards.r.Righteousness.class));
        cards.add(new SetCardInfo("Rod of Ruin", 362, Rarity.UNCOMMON, mage.cards.r.RodOfRuin.class));
        cards.add(new SetCardInfo("Royal Assassin", 41, Rarity.RARE, mage.cards.r.RoyalAssassin.class));
        cards.add(new SetCardInfo("Samite Healer", 297, Rarity.COMMON, mage.cards.s.SamiteHealer.class));
        cards.add(new SetCardInfo("Sandstorm", 153, Rarity.COMMON, mage.cards.s.Sandstorm.class));
        cards.add(new SetCardInfo("Savannah Lions", 298, Rarity.RARE, mage.cards.s.SavannahLions.class));
        cards.add(new SetCardInfo("Scathe Zombies", 42, Rarity.COMMON, mage.cards.s.ScatheZombies.class));
        cards.add(new SetCardInfo("Scavenging Ghoul", 43, Rarity.UNCOMMON, mage.cards.s.ScavengingGhoul.class));
        cards.add(new SetCardInfo("Scryb Sprites", 154, Rarity.COMMON, mage.cards.s.ScrybSprites.class));
        cards.add(new SetCardInfo("Sea Serpent", 98, Rarity.COMMON, mage.cards.s.SeaSerpent.class));
        cards.add(new SetCardInfo("Segovian Leviathan", 99, Rarity.UNCOMMON, mage.cards.s.SegovianLeviathan.class));
        cards.add(new SetCardInfo("Sengir Vampire", 44, Rarity.UNCOMMON, mage.cards.s.SengirVampire.class));
        cards.add(new SetCardInfo("Serra Angel", 300, Rarity.UNCOMMON, mage.cards.s.SerraAngel.class));
        cards.add(new SetCardInfo("Shanodin Dryads", 155, Rarity.COMMON, mage.cards.s.ShanodinDryads.class));
        cards.add(new SetCardInfo("Shapeshifter", 363, Rarity.UNCOMMON, mage.cards.s.Shapeshifter.class));
        cards.add(new SetCardInfo("Shatter", 237, Rarity.COMMON, mage.cards.s.Shatter.class));
        cards.add(new SetCardInfo("Shivan Dragon", 238, Rarity.RARE, mage.cards.s.ShivanDragon.class));
        cards.add(new SetCardInfo("Simulacrum", 45, Rarity.UNCOMMON, mage.cards.s.Simulacrum.class));
        cards.add(new SetCardInfo("Sindbad", 100, Rarity.UNCOMMON, mage.cards.s.Sindbad.class));
        cards.add(new SetCardInfo("Siren's Call", 101, Rarity.UNCOMMON, mage.cards.s.SirensCall.class));
        cards.add(new SetCardInfo("Sisters of the Flame", 239, Rarity.COMMON, mage.cards.s.SistersOfTheFlame.class));
        cards.add(new SetCardInfo("Smoke", 240, Rarity.RARE, mage.cards.s.Smoke.class));
        cards.add(new SetCardInfo("Sorceress Queen", 46, Rarity.RARE, mage.cards.s.SorceressQueen.class));
        cards.add(new SetCardInfo("Soul Net", 364, Rarity.UNCOMMON, mage.cards.s.SoulNet.class));
        cards.add(new SetCardInfo("Spell Blast", 103, Rarity.COMMON, mage.cards.s.SpellBlast.class));
        cards.add(new SetCardInfo("Spirit Link", 301, Rarity.UNCOMMON, mage.cards.s.SpiritLink.class));
        cards.add(new SetCardInfo("Spirit Shackle", 47, Rarity.UNCOMMON, mage.cards.s.SpiritShackle.class));
        cards.add(new SetCardInfo("Stasis", 104, Rarity.RARE, mage.cards.s.Stasis.class));
        cards.add(new SetCardInfo("Steal Artifact", 105, Rarity.UNCOMMON, mage.cards.s.StealArtifact.class));
        cards.add(new SetCardInfo("Stone Giant", 241, Rarity.UNCOMMON, mage.cards.s.StoneGiant.class));
        cards.add(new SetCardInfo("Stone Rain", 242, Rarity.COMMON, mage.cards.s.StoneRain.class));
        cards.add(new SetCardInfo("Stream of Life", 156, Rarity.COMMON, mage.cards.s.StreamOfLife.class));
        cards.add(new SetCardInfo("Strip Mine", 189, Rarity.UNCOMMON, mage.cards.s.StripMine.class));
        cards.add(new SetCardInfo("Sunglasses of Urza", 365, Rarity.RARE, mage.cards.s.SunglassesOfUrza.class));
        cards.add(new SetCardInfo("Sunken City", 106, Rarity.COMMON, mage.cards.s.SunkenCity.class));
        cards.add(new SetCardInfo("Swamp", 190, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 191, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 192, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swords to Plowshares", 302, Rarity.UNCOMMON, mage.cards.s.SwordsToPlowshares.class));
        cards.add(new SetCardInfo("Sylvan Library", 157, Rarity.RARE, mage.cards.s.SylvanLibrary.class));
        cards.add(new SetCardInfo("Tawnos's Wand", 366, Rarity.UNCOMMON, mage.cards.t.TawnossWand.class));
        cards.add(new SetCardInfo("Tawnos's Weaponry", 367, Rarity.UNCOMMON, mage.cards.t.TawnossWeaponry.class));
        cards.add(new SetCardInfo("Terror", 48, Rarity.COMMON, mage.cards.t.Terror.class));
        cards.add(new SetCardInfo("Tetravus", 368, Rarity.RARE, mage.cards.t.Tetravus.class));
        cards.add(new SetCardInfo("The Brute", 244, Rarity.COMMON, mage.cards.t.TheBrute.class));
        cards.add(new SetCardInfo("The Hive", 369, Rarity.RARE, mage.cards.t.TheHive.class));
        cards.add(new SetCardInfo("The Rack", 370, Rarity.UNCOMMON, mage.cards.t.TheRack.class));
        cards.add(new SetCardInfo("Thicket Basilisk", 158, Rarity.UNCOMMON, mage.cards.t.ThicketBasilisk.class));
        cards.add(new SetCardInfo("Thoughtlace", 107, Rarity.RARE, mage.cards.t.Thoughtlace.class));
        cards.add(new SetCardInfo("Throne of Bone", 371, Rarity.UNCOMMON, mage.cards.t.ThroneOfBone.class));
        cards.add(new SetCardInfo("Titania's Song", 160, Rarity.RARE, mage.cards.t.TitaniasSong.class));
        cards.add(new SetCardInfo("Tranquility", 161, Rarity.COMMON, mage.cards.t.Tranquility.class));
        cards.add(new SetCardInfo("Triskelion", 372, Rarity.RARE, mage.cards.t.Triskelion.class));
        cards.add(new SetCardInfo("Tsunami", 162, Rarity.UNCOMMON, mage.cards.t.Tsunami.class));
        cards.add(new SetCardInfo("Tundra Wolves", 303, Rarity.COMMON, mage.cards.t.TundraWolves.class));
        cards.add(new SetCardInfo("Tunnel", 245, Rarity.UNCOMMON, mage.cards.t.Tunnel.class));
        cards.add(new SetCardInfo("Twiddle", 109, Rarity.COMMON, mage.cards.t.Twiddle.class));
        cards.add(new SetCardInfo("Uncle Istvan", 49, Rarity.UNCOMMON, mage.cards.u.UncleIstvan.class));
        cards.add(new SetCardInfo("Unholy Strength", 50, Rarity.COMMON, mage.cards.u.UnholyStrength.class));
        cards.add(new SetCardInfo("Unstable Mutation", 110, Rarity.COMMON, mage.cards.u.UnstableMutation.class));
        cards.add(new SetCardInfo("Unsummon", 111, Rarity.COMMON, mage.cards.u.Unsummon.class));
        cards.add(new SetCardInfo("Untamed Wilds", 163, Rarity.UNCOMMON, mage.cards.u.UntamedWilds.class));
        cards.add(new SetCardInfo("Uthden Troll", 246, Rarity.UNCOMMON, mage.cards.u.UthdenTroll.class));
        cards.add(new SetCardInfo("Vampire Bats", 51, Rarity.COMMON, mage.cards.v.VampireBats.class));
        cards.add(new SetCardInfo("Venom", 164, Rarity.COMMON, mage.cards.v.Venom.class));
        cards.add(new SetCardInfo("Verduran Enchantress", 165, Rarity.RARE, mage.cards.v.VerduranEnchantress.class));
        cards.add(new SetCardInfo("Volcanic Eruption", 112, Rarity.RARE, mage.cards.v.VolcanicEruption.class));
        cards.add(new SetCardInfo("Wall of Air", 113, Rarity.UNCOMMON, mage.cards.w.WallOfAir.class));
        cards.add(new SetCardInfo("Wall of Bone", 52, Rarity.UNCOMMON, mage.cards.w.WallOfBone.class));
        cards.add(new SetCardInfo("Wall of Brambles", 166, Rarity.UNCOMMON, mage.cards.w.WallOfBrambles.class));
        cards.add(new SetCardInfo("Wall of Fire", 248, Rarity.UNCOMMON, mage.cards.w.WallOfFire.class));
        cards.add(new SetCardInfo("Wall of Ice", 167, Rarity.UNCOMMON, mage.cards.w.WallOfIce.class));
        cards.add(new SetCardInfo("Wall of Spears", 374, Rarity.COMMON, mage.cards.w.WallOfSpears.class));
        cards.add(new SetCardInfo("Wall of Stone", 249, Rarity.UNCOMMON, mage.cards.w.WallOfStone.class));
        cards.add(new SetCardInfo("Wall of Swords", 305, Rarity.UNCOMMON, mage.cards.w.WallOfSwords.class));
        cards.add(new SetCardInfo("Wall of Water", 114, Rarity.UNCOMMON, mage.cards.w.WallOfWater.class));
        cards.add(new SetCardInfo("Wall of Wood", 168, Rarity.COMMON, mage.cards.w.WallOfWood.class));
        cards.add(new SetCardInfo("Wanderlust", 169, Rarity.UNCOMMON, mage.cards.w.Wanderlust.class));
        cards.add(new SetCardInfo("War Mammoth", 170, Rarity.COMMON, mage.cards.w.WarMammoth.class));
        cards.add(new SetCardInfo("Warp Artifact", 53, Rarity.RARE, mage.cards.w.WarpArtifact.class));
        cards.add(new SetCardInfo("Water Elemental", 115, Rarity.UNCOMMON, mage.cards.w.WaterElemental.class));
        cards.add(new SetCardInfo("Weakness", 54, Rarity.COMMON, mage.cards.w.Weakness.class));
        cards.add(new SetCardInfo("Web", 171, Rarity.RARE, mage.cards.w.Web.class));
        cards.add(new SetCardInfo("Whirling Dervish", 172, Rarity.UNCOMMON, mage.cards.w.WhirlingDervish.class));
        cards.add(new SetCardInfo("White Knight", 306, Rarity.UNCOMMON, mage.cards.w.WhiteKnight.class));
        cards.add(new SetCardInfo("White Ward", 307, Rarity.UNCOMMON, mage.cards.w.WhiteWard.class));
        cards.add(new SetCardInfo("Wild Growth", 173, Rarity.COMMON, mage.cards.w.WildGrowth.class));
        cards.add(new SetCardInfo("Will-o'-the-Wisp", 55, Rarity.RARE, mage.cards.w.WillOTheWisp.class));
        cards.add(new SetCardInfo("Winds of Change", 250, Rarity.RARE, mage.cards.w.WindsOfChange.class));
        cards.add(new SetCardInfo("Winter Orb", 376, Rarity.RARE, mage.cards.w.WinterOrb.class));
        cards.add(new SetCardInfo("Wooden Sphere", 377, Rarity.UNCOMMON, mage.cards.w.WoodenSphere.class));
        cards.add(new SetCardInfo("Word of Binding", 56, Rarity.COMMON, mage.cards.w.WordOfBinding.class));
        cards.add(new SetCardInfo("Wrath of God", 308, Rarity.RARE, mage.cards.w.WrathOfGod.class));
        cards.add(new SetCardInfo("Xenic Poltergeist", 57, Rarity.RARE, mage.cards.x.XenicPoltergeist.class));
        cards.add(new SetCardInfo("Yotian Soldier", 378, Rarity.COMMON, mage.cards.y.YotianSoldier.class));
        cards.add(new SetCardInfo("Zephyr Falcon", 116, Rarity.COMMON, mage.cards.z.ZephyrFalcon.class));
        cards.add(new SetCardInfo("Zombie Master", 58, Rarity.RARE, mage.cards.z.ZombieMaster.class));
    }
}
