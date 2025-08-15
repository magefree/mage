package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * German and French versions of Renaissance
 * <p>
 * https://scryfall.com/sets/rin
 * https://mtg.wiki/page/Renaissance
 *
 * @author JayDi85
 */
public final class Renaissance extends ExpansionSet {

    private static final Renaissance instance = new Renaissance();

    public static Renaissance getInstance() {
        return instance;
    }

    private Renaissance() {
        super("Renaissance", "REN", ExpansionSet.buildDate(1995, 8, 1), SetType.SUPPLEMENTAL);
        this.hasBasicLands = true;

        this.enableCollectorBooster(Integer.MAX_VALUE, 0, 6, 2, 0);

        cards.add(new SetCardInfo("Abomination", 46, Rarity.UNCOMMON, mage.cards.a.Abomination.class));
        cards.add(new SetCardInfo("Alabaster Potion", 2, Rarity.COMMON, mage.cards.a.AlabasterPotion.class));
        cards.add(new SetCardInfo("Ali Baba", 71, Rarity.UNCOMMON, mage.cards.a.AliBaba.class));
        cards.add(new SetCardInfo("Amrou Kithkin", 3, Rarity.COMMON, mage.cards.a.AmrouKithkin.class));
        cards.add(new SetCardInfo("Amulet of Kroog", 99, Rarity.COMMON, mage.cards.a.AmuletOfKroog.class));
        cards.add(new SetCardInfo("Angry Mob", 4, Rarity.UNCOMMON, mage.cards.a.AngryMob.class));
        cards.add(new SetCardInfo("Apprentice Wizard", 23, Rarity.UNCOMMON, mage.cards.a.ApprenticeWizard.class));
        cards.add(new SetCardInfo("Ashes to Ashes", 47, Rarity.COMMON, mage.cards.a.AshesToAshes.class));
        cards.add(new SetCardInfo("Ashnod's Battle Gear", 104, Rarity.UNCOMMON, mage.cards.a.AshnodsBattleGear.class));
        cards.add(new SetCardInfo("Backfire", 24, Rarity.UNCOMMON, mage.cards.b.Backfire.class));
        cards.add(new SetCardInfo("Ball Lightning", 73, Rarity.UNCOMMON, mage.cards.b.BallLightning.class));
        cards.add(new SetCardInfo("Battering Ram", 107, Rarity.COMMON, mage.cards.b.BatteringRam.class));
        cards.add(new SetCardInfo("Bird Maiden", 74, Rarity.COMMON, mage.cards.b.BirdMaiden.class));
        cards.add(new SetCardInfo("Black Mana Battery", 108, Rarity.UNCOMMON, mage.cards.b.BlackManaBattery.class));
        cards.add(new SetCardInfo("Blight", 48, Rarity.UNCOMMON, mage.cards.b.Blight.class));
        cards.add(new SetCardInfo("Blood Lust", 75, Rarity.UNCOMMON, mage.cards.b.BloodLust.class));
        cards.add(new SetCardInfo("Blue Mana Battery", 112, Rarity.UNCOMMON, mage.cards.b.BlueManaBattery.class));
        cards.add(new SetCardInfo("Bog Imp", 49, Rarity.COMMON, mage.cards.b.BogImp.class));
        cards.add(new SetCardInfo("Brainwash", 6, Rarity.COMMON, mage.cards.b.Brainwash.class));
        //cards.add(new SetCardInfo("Bronze Tablet", 115, Rarity.UNCOMMON, mage.cards.b.BronzeTablet.class));
        cards.add(new SetCardInfo("Brothers of Fire", 76, Rarity.UNCOMMON, mage.cards.b.BrothersOfFire.class));
        cards.add(new SetCardInfo("Carnivorous Plant", 117, Rarity.COMMON, mage.cards.c.CarnivorousPlant.class));
        cards.add(new SetCardInfo("Carrion Ants", 51, Rarity.UNCOMMON, mage.cards.c.CarrionAnts.class));
        cards.add(new SetCardInfo("Cave People", 78, Rarity.UNCOMMON, mage.cards.c.CavePeople.class));
        cards.add(new SetCardInfo("Circle of Protection: Artifacts", 7, Rarity.UNCOMMON, mage.cards.c.CircleOfProtectionArtifacts.class));
        cards.add(new SetCardInfo("Clay Statue", 120, Rarity.COMMON, mage.cards.c.ClayStatue.class));
        cards.add(new SetCardInfo("Clockwork Avian", 122, Rarity.UNCOMMON, mage.cards.c.ClockworkAvian.class));
        cards.add(new SetCardInfo("Colossus of Sardia", 123, Rarity.UNCOMMON, mage.cards.c.ColossusOfSardia.class));
        cards.add(new SetCardInfo("Coral Helm", 124, Rarity.UNCOMMON, mage.cards.c.CoralHelm.class));
        cards.add(new SetCardInfo("Cosmic Horror", 52, Rarity.UNCOMMON, mage.cards.c.CosmicHorror.class));
        cards.add(new SetCardInfo("Crimson Manticore", 80, Rarity.UNCOMMON, mage.cards.c.CrimsonManticore.class));
        cards.add(new SetCardInfo("Cursed Rack", 126, Rarity.COMMON, mage.cards.c.CursedRack.class));
        cards.add(new SetCardInfo("Cyclopean Mummy", 54, Rarity.COMMON, mage.cards.c.CyclopeanMummy.class));
        cards.add(new SetCardInfo("Detonate", 83, Rarity.UNCOMMON, mage.cards.d.Detonate.class));
        cards.add(new SetCardInfo("Diabolic Machine", 129, Rarity.UNCOMMON, mage.cards.d.DiabolicMachine.class));
        cards.add(new SetCardInfo("Divine Transformation", 8, Rarity.UNCOMMON, mage.cards.d.DivineTransformation.class));
        cards.add(new SetCardInfo("Durkwood Boars", 131, Rarity.COMMON, mage.cards.d.DurkwoodBoars.class));
        cards.add(new SetCardInfo("Elder Land Wurm", 9, Rarity.UNCOMMON, mage.cards.e.ElderLandWurm.class));
        cards.add(new SetCardInfo("Elven Riders", 133, Rarity.UNCOMMON, mage.cards.e.ElvenRiders.class));
        cards.add(new SetCardInfo("Energy Tap", 28, Rarity.COMMON, mage.cards.e.EnergyTap.class));
        cards.add(new SetCardInfo("Erosion", 29, Rarity.COMMON, mage.cards.e.Erosion.class));
        cards.add(new SetCardInfo("Eternal Warrior", 84, Rarity.UNCOMMON, mage.cards.e.EternalWarrior.class));
        cards.add(new SetCardInfo("Fellwar Stone", 136, Rarity.UNCOMMON, mage.cards.f.FellwarStone.class));
        cards.add(new SetCardInfo("Fissure", 85, Rarity.COMMON, mage.cards.f.Fissure.class));
        cards.add(new SetCardInfo("Flood", 30, Rarity.UNCOMMON, mage.cards.f.Flood.class));
        cards.add(new SetCardInfo("Fortified Area", 11, Rarity.UNCOMMON, mage.cards.f.FortifiedArea.class));
        cards.add(new SetCardInfo("Gaseous Form", 32, Rarity.COMMON, mage.cards.g.GaseousForm.class));
        cards.add(new SetCardInfo("Ghost Ship", 33, Rarity.COMMON, mage.cards.g.GhostShip.class));
        cards.add(new SetCardInfo("Giant Strength", 86, Rarity.COMMON, mage.cards.g.GiantStrength.class));
        cards.add(new SetCardInfo("Giant Tortoise", 34, Rarity.COMMON, mage.cards.g.GiantTortoise.class));
        cards.add(new SetCardInfo("Goblin Rock Sled", 87, Rarity.COMMON, mage.cards.g.GoblinRockSled.class));
        cards.add(new SetCardInfo("Grapeshot Catapult", 143, Rarity.COMMON, mage.cards.g.GrapeshotCatapult.class));
        cards.add(new SetCardInfo("Greed", 56, Rarity.UNCOMMON, mage.cards.g.Greed.class));
        cards.add(new SetCardInfo("Green Mana Battery", 145, Rarity.UNCOMMON, mage.cards.g.GreenManaBattery.class));
        cards.add(new SetCardInfo("Hurr Jackal", 88, Rarity.COMMON, mage.cards.h.HurrJackal.class));
        cards.add(new SetCardInfo("Immolation", 89, Rarity.COMMON, mage.cards.i.Immolation.class));
        cards.add(new SetCardInfo("Inferno", 90, Rarity.UNCOMMON, mage.cards.i.Inferno.class));
        cards.add(new SetCardInfo("Ironclaw Orcs", 91, Rarity.COMMON, mage.cards.i.IronclawOrcs.class));
        cards.add(new SetCardInfo("Junun Efreet", 57, Rarity.UNCOMMON, mage.cards.j.JununEfreet.class));
        cards.add(new SetCardInfo("Killer Bees", 146, Rarity.UNCOMMON, mage.cards.k.KillerBees.class));
        cards.add(new SetCardInfo("Kismet", 12, Rarity.UNCOMMON, mage.cards.k.Kismet.class));
        cards.add(new SetCardInfo("Land Leeches", 147, Rarity.COMMON, mage.cards.l.LandLeeches.class));
        cards.add(new SetCardInfo("Land Tax", 13, Rarity.UNCOMMON, mage.cards.l.LandTax.class));
        cards.add(new SetCardInfo("Leviathan", 36, Rarity.UNCOMMON, mage.cards.l.Leviathan.class));
        cards.add(new SetCardInfo("Lost Soul", 58, Rarity.COMMON, mage.cards.l.LostSoul.class));
        cards.add(new SetCardInfo("Mana Clash", 92, Rarity.UNCOMMON, mage.cards.m.ManaClash.class));
        cards.add(new SetCardInfo("Marsh Gas", 59, Rarity.COMMON, mage.cards.m.MarshGas.class));
        cards.add(new SetCardInfo("Marsh Viper", 149, Rarity.COMMON, mage.cards.m.MarshViper.class));
        cards.add(new SetCardInfo("Mind Bomb", 37, Rarity.UNCOMMON, mage.cards.m.MindBomb.class));
        cards.add(new SetCardInfo("Mishra's Factory", 187, Rarity.UNCOMMON, mage.cards.m.MishrasFactory.class));
        cards.add(new SetCardInfo("Morale", 15, Rarity.COMMON, mage.cards.m.Morale.class));
        cards.add(new SetCardInfo("Murk Dwellers", 62, Rarity.COMMON, mage.cards.m.MurkDwellers.class));
        cards.add(new SetCardInfo("Nafs Asp", 151, Rarity.COMMON, mage.cards.n.NafsAsp.class));
        cards.add(new SetCardInfo("Oasis", 188, Rarity.UNCOMMON, mage.cards.o.Oasis.class));
        cards.add(new SetCardInfo("Osai Vultures", 16, Rarity.COMMON, mage.cards.o.OsaiVultures.class));
        cards.add(new SetCardInfo("Piety", 17, Rarity.COMMON, mage.cards.p.Piety.class));
        cards.add(new SetCardInfo("Pikemen", 18, Rarity.COMMON, mage.cards.p.Pikemen.class));
        cards.add(new SetCardInfo("Pit Scorpion", 63, Rarity.COMMON, mage.cards.p.PitScorpion.class));
        cards.add(new SetCardInfo("Pradesh Gypsies", 152, Rarity.UNCOMMON, mage.cards.p.PradeshGypsies.class));
        cards.add(new SetCardInfo("Psionic Entity", 38, Rarity.UNCOMMON, mage.cards.p.PsionicEntity.class));
        cards.add(new SetCardInfo("Pyrotechnics", 93, Rarity.COMMON, mage.cards.p.Pyrotechnics.class));
        cards.add(new SetCardInfo("Radjan Spirit", 153, Rarity.UNCOMMON, mage.cards.r.RadjanSpirit.class));
        cards.add(new SetCardInfo("Rag Man", 64, Rarity.UNCOMMON, mage.cards.r.RagMan.class));
        //cards.add(new SetCardInfo("Rebirth", 154, Rarity.UNCOMMON, mage.cards.r.Rebirth.class));
        cards.add(new SetCardInfo("Red Mana Battery", 155, Rarity.UNCOMMON, mage.cards.r.RedManaBattery.class));
        cards.add(new SetCardInfo("Relic Bind", 39, Rarity.UNCOMMON, mage.cards.r.RelicBind.class));
        cards.add(new SetCardInfo("Sandstorm", 156, Rarity.COMMON, mage.cards.s.Sandstorm.class));
        cards.add(new SetCardInfo("Seeker", 19, Rarity.UNCOMMON, mage.cards.s.Seeker.class));
        cards.add(new SetCardInfo("Segovian Leviathan", 40, Rarity.UNCOMMON, mage.cards.s.SegovianLeviathan.class));
        cards.add(new SetCardInfo("Shapeshifter", 157, Rarity.UNCOMMON, mage.cards.s.Shapeshifter.class));
        cards.add(new SetCardInfo("Sindbad", 41, Rarity.UNCOMMON, mage.cards.s.Sindbad.class));
        cards.add(new SetCardInfo("Sisters of the Flame", 94, Rarity.UNCOMMON, mage.cards.s.SistersOfTheFlame.class));
        cards.add(new SetCardInfo("Spirit Link", 20, Rarity.UNCOMMON, mage.cards.s.SpiritLink.class));
        cards.add(new SetCardInfo("Spirit Shackle", 65, Rarity.COMMON, mage.cards.s.SpiritShackle.class));
        cards.add(new SetCardInfo("Strip Mine", 189, Rarity.UNCOMMON, mage.cards.s.StripMine.class));
        cards.add(new SetCardInfo("Sunken City", 42, Rarity.COMMON, mage.cards.s.SunkenCity.class));
        cards.add(new SetCardInfo("Sylvan Library", 158, Rarity.UNCOMMON, mage.cards.s.SylvanLibrary.class));
        cards.add(new SetCardInfo("Tawnos's Wand", 159, Rarity.UNCOMMON, mage.cards.t.TawnossWand.class));
        cards.add(new SetCardInfo("Tawnos's Weaponry", 160, Rarity.UNCOMMON, mage.cards.t.TawnossWeaponry.class));
        //cards.add(new SetCardInfo("Tempest Efreet", 95, Rarity.UNCOMMON, mage.cards.t.TempestEfreet.class));
        cards.add(new SetCardInfo("Tetravus", 161, Rarity.UNCOMMON, mage.cards.t.Tetravus.class));
        cards.add(new SetCardInfo("The Brute", 96, Rarity.COMMON, mage.cards.t.TheBrute.class));
        cards.add(new SetCardInfo("Time Elemental", 43, Rarity.UNCOMMON, mage.cards.t.TimeElemental.class));
        cards.add(new SetCardInfo("Triskelion", 162, Rarity.UNCOMMON, mage.cards.t.Triskelion.class));
        cards.add(new SetCardInfo("Tundra Wolves", 21, Rarity.COMMON, mage.cards.t.TundraWolves.class));
        cards.add(new SetCardInfo("Twiddle", 44, Rarity.COMMON, mage.cards.t.Twiddle.class));
        cards.add(new SetCardInfo("Uncle Istvan", 66, Rarity.UNCOMMON, mage.cards.u.UncleIstvan.class));
        cards.add(new SetCardInfo("Untamed Wilds", 163, Rarity.UNCOMMON, mage.cards.u.UntamedWilds.class));
        cards.add(new SetCardInfo("Urza's Avenger", 164, Rarity.UNCOMMON, mage.cards.u.UrzasAvenger.class));
        cards.add(new SetCardInfo("Vampire Bats", 67, Rarity.COMMON, mage.cards.v.VampireBats.class));
        cards.add(new SetCardInfo("Venom", 165, Rarity.COMMON, mage.cards.v.Venom.class));
        cards.add(new SetCardInfo("Visions", 22, Rarity.UNCOMMON, mage.cards.v.Visions.class));
        cards.add(new SetCardInfo("Wall of Dust", 97, Rarity.UNCOMMON, mage.cards.w.WallOfDust.class));
        cards.add(new SetCardInfo("Wall of Spears", 166, Rarity.UNCOMMON, mage.cards.w.WallOfSpears.class));
        cards.add(new SetCardInfo("Whirling Dervish", 167, Rarity.UNCOMMON, mage.cards.w.WhirlingDervish.class));
        cards.add(new SetCardInfo("White Mana Battery", 168, Rarity.UNCOMMON, mage.cards.w.WhiteManaBattery.class));
        cards.add(new SetCardInfo("Winds of Change", 98, Rarity.UNCOMMON, mage.cards.w.WindsOfChange.class));
        cards.add(new SetCardInfo("Winter Blast", 169, Rarity.UNCOMMON, mage.cards.w.WinterBlast.class));
        cards.add(new SetCardInfo("Word of Binding", 68, Rarity.COMMON, mage.cards.w.WordOfBinding.class));
        cards.add(new SetCardInfo("Xenic Poltergeist", 69, Rarity.UNCOMMON, mage.cards.x.XenicPoltergeist.class));
        cards.add(new SetCardInfo("Yotian Soldier", 170, Rarity.COMMON, mage.cards.y.YotianSoldier.class));
        cards.add(new SetCardInfo("Zephyr Falcon", 45, Rarity.COMMON, mage.cards.z.ZephyrFalcon.class));
    }
}
