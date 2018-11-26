
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

public final class DuelsOfThePlaneswalkers extends ExpansionSet {

    private static final DuelsOfThePlaneswalkers instance = new DuelsOfThePlaneswalkers();

    public static DuelsOfThePlaneswalkers getInstance() {
        return instance;
    }

    private DuelsOfThePlaneswalkers() {
        super("Duels of the Planeswalkers", "DPA", ExpansionSet.buildDate(2010, 6, 4), SetType.SUPPLEMENTAL);

        cards.add(new SetCardInfo("Abyssal Specter", 18, Rarity.UNCOMMON, mage.cards.a.AbyssalSpecter.class));
        cards.add(new SetCardInfo("Act of Treason", 37, Rarity.UNCOMMON, mage.cards.a.ActOfTreason.class));
        cards.add(new SetCardInfo("Air Elemental", 1, Rarity.UNCOMMON, mage.cards.a.AirElemental.class));
        cards.add(new SetCardInfo("Ascendant Evincar", 19, Rarity.RARE, mage.cards.a.AscendantEvincar.class));
        cards.add(new SetCardInfo("Banefire", 38, Rarity.RARE, mage.cards.b.Banefire.class));
        cards.add(new SetCardInfo("Blanchwood Armor", 55, Rarity.UNCOMMON, mage.cards.b.BlanchwoodArmor.class));
        cards.add(new SetCardInfo("Blaze", 39, Rarity.UNCOMMON, mage.cards.b.Blaze.class));
        cards.add(new SetCardInfo("Bloodmark Mentor", 40, Rarity.UNCOMMON, mage.cards.b.BloodmarkMentor.class));
        cards.add(new SetCardInfo("Boomerang", 2, Rarity.COMMON, mage.cards.b.Boomerang.class));
        cards.add(new SetCardInfo("Cancel", 3, Rarity.COMMON, mage.cards.c.Cancel.class));
        cards.add(new SetCardInfo("Cinder Pyromancer", 41, Rarity.COMMON, mage.cards.c.CinderPyromancer.class));
        cards.add(new SetCardInfo("Civic Wayfinder", 56, Rarity.COMMON, mage.cards.c.CivicWayfinder.class));
        cards.add(new SetCardInfo("Cloud Sprite", 4, Rarity.COMMON, mage.cards.c.CloudSprite.class));
        cards.add(new SetCardInfo("Coat of Arms", 90, Rarity.RARE, mage.cards.c.CoatOfArms.class));
        cards.add(new SetCardInfo("Consume Spirit", 20, Rarity.UNCOMMON, mage.cards.c.ConsumeSpirit.class));
        cards.add(new SetCardInfo("Counterbore", 5, Rarity.RARE, mage.cards.c.Counterbore.class));
        cards.add(new SetCardInfo("Crowd of Cinders", 21, Rarity.UNCOMMON, mage.cards.c.CrowdOfCinders.class));
        cards.add(new SetCardInfo("Deluge", 6, Rarity.UNCOMMON, mage.cards.d.Deluge.class));
        cards.add(new SetCardInfo("Demon's Horn", 91, Rarity.UNCOMMON, mage.cards.d.DemonsHorn.class));
        cards.add(new SetCardInfo("Denizen of the Deep", 7, Rarity.RARE, mage.cards.d.DenizenOfTheDeep.class));
        cards.add(new SetCardInfo("Dragon's Claw", 92, Rarity.UNCOMMON, mage.cards.d.DragonsClaw.class));
        cards.add(new SetCardInfo("Drove of Elves", 57, Rarity.UNCOMMON, mage.cards.d.DroveOfElves.class));
        cards.add(new SetCardInfo("Drudge Skeletons", 22, Rarity.COMMON, mage.cards.d.DrudgeSkeletons.class));
        cards.add(new SetCardInfo("Dusk Imp", 23, Rarity.COMMON, mage.cards.d.DuskImp.class));
        cards.add(new SetCardInfo("Duskdale Wurm", 58, Rarity.UNCOMMON, mage.cards.d.DuskdaleWurm.class));
        cards.add(new SetCardInfo("Earth Elemental", 42, Rarity.UNCOMMON, mage.cards.e.EarthElemental.class));
        cards.add(new SetCardInfo("Elven Riders", 59, Rarity.UNCOMMON, mage.cards.e.ElvenRiders.class));
        cards.add(new SetCardInfo("Elvish Champion", 60, Rarity.RARE, mage.cards.e.ElvishChampion.class));
        cards.add(new SetCardInfo("Elvish Eulogist", 61, Rarity.COMMON, mage.cards.e.ElvishEulogist.class));
        cards.add(new SetCardInfo("Elvish Promenade", 62, Rarity.UNCOMMON, mage.cards.e.ElvishPromenade.class));
        cards.add(new SetCardInfo("Elvish Visionary", 63, Rarity.COMMON, mage.cards.e.ElvishVisionary.class));
        cards.add(new SetCardInfo("Elvish Warrior", 64, Rarity.COMMON, mage.cards.e.ElvishWarrior.class));
        cards.add(new SetCardInfo("Enrage", 43, Rarity.UNCOMMON, mage.cards.e.Enrage.class));
        cards.add(new SetCardInfo("Essence Drain", 24, Rarity.COMMON, mage.cards.e.EssenceDrain.class));
        cards.add(new SetCardInfo("Essence Scatter", 8, Rarity.COMMON, mage.cards.e.EssenceScatter.class));
        cards.add(new SetCardInfo("Evacuation", 9, Rarity.RARE, mage.cards.e.Evacuation.class));
        cards.add(new SetCardInfo("Eyeblight's Ending", 25, Rarity.COMMON, mage.cards.e.EyeblightsEnding.class));
        cards.add(new SetCardInfo("Forest", 110, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 111, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 112, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 113, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Furnace of Rath", 44, Rarity.RARE, mage.cards.f.FurnaceOfRath.class));
        cards.add(new SetCardInfo("Gaea's Herald", 65, Rarity.RARE, mage.cards.g.GaeasHerald.class));
        cards.add(new SetCardInfo("Giant Growth", 66, Rarity.COMMON, mage.cards.g.GiantGrowth.class));
        cards.add(new SetCardInfo("Giant Spider", 67, Rarity.COMMON, mage.cards.g.GiantSpider.class));
        cards.add(new SetCardInfo("Goblin Piker", 45, Rarity.COMMON, mage.cards.g.GoblinPiker.class));
        cards.add(new SetCardInfo("Goblin Sky Raider", 46, Rarity.COMMON, mage.cards.g.GoblinSkyRaider.class));
        cards.add(new SetCardInfo("Greenweaver Druid", 68, Rarity.UNCOMMON, mage.cards.g.GreenweaverDruid.class));
        cards.add(new SetCardInfo("Hill Giant", 47, Rarity.COMMON, mage.cards.h.HillGiant.class));
        cards.add(new SetCardInfo("Howl of the Night Pack", 69, Rarity.UNCOMMON, mage.cards.h.HowlOfTheNightPack.class));
        cards.add(new SetCardInfo("Immaculate Magistrate", 70, Rarity.RARE, mage.cards.i.ImmaculateMagistrate.class));
        cards.add(new SetCardInfo("Imperious Perfect", 71, Rarity.UNCOMMON, mage.cards.i.ImperiousPerfect.class));
        cards.add(new SetCardInfo("Incinerate", 48, Rarity.COMMON, mage.cards.i.Incinerate.class));
        cards.add(new SetCardInfo("Island", 100, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 101, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 98, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 99, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jagged-Scar Archers", 72, Rarity.UNCOMMON, mage.cards.j.JaggedScarArchers.class));
        cards.add(new SetCardInfo("Kamahl, Pit Fighter", 49, Rarity.RARE, mage.cards.k.KamahlPitFighter.class));
        cards.add(new SetCardInfo("Kraken's Eye", 93, Rarity.UNCOMMON, mage.cards.k.KrakensEye.class));
        cards.add(new SetCardInfo("Lightning Elemental", 50, Rarity.COMMON, mage.cards.l.LightningElemental.class));
        cards.add(new SetCardInfo("Loxodon Warhammer", 94, Rarity.RARE, mage.cards.l.LoxodonWarhammer.class));
        cards.add(new SetCardInfo("Lys Alana Huntmaster", 73, Rarity.COMMON, mage.cards.l.LysAlanaHuntmaster.class));
        cards.add(new SetCardInfo("Mahamoti Djinn", 10, Rarity.RARE, mage.cards.m.MahamotiDjinn.class));
        cards.add(new SetCardInfo("Megrim", 26, Rarity.UNCOMMON, mage.cards.m.Megrim.class));
        cards.add(new SetCardInfo("Mind Control", 11, Rarity.UNCOMMON, mage.cards.m.MindControl.class));
        cards.add(new SetCardInfo("Mind Rot", 27, Rarity.COMMON, mage.cards.m.MindRot.class));
        cards.add(new SetCardInfo("Mind Shatter", 28, Rarity.RARE, mage.cards.m.MindShatter.class));
        cards.add(new SetCardInfo("Mind Spring", 12, Rarity.RARE, mage.cards.m.MindSpring.class));
        cards.add(new SetCardInfo("Molimo, Maro-Sorcerer", 74, Rarity.RARE, mage.cards.m.MolimoMaroSorcerer.class));
        cards.add(new SetCardInfo("Moonglove Winnower", 29, Rarity.COMMON, mage.cards.m.MoongloveWinnower.class));
        cards.add(new SetCardInfo("Mortivore", 30, Rarity.RARE, mage.cards.m.Mortivore.class));
        cards.add(new SetCardInfo("Mountain", 106, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 107, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 108, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 109, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Natural Spring", 75, Rarity.COMMON, mage.cards.n.NaturalSpring.class));
        cards.add(new SetCardInfo("Naturalize", 76, Rarity.COMMON, mage.cards.n.Naturalize.class));
        cards.add(new SetCardInfo("Nature's Spiral", 77, Rarity.UNCOMMON, mage.cards.n.NaturesSpiral.class));
        cards.add(new SetCardInfo("Negate", 13, Rarity.COMMON, mage.cards.n.Negate.class));
        cards.add(new SetCardInfo("Overrun", 78, Rarity.UNCOMMON, mage.cards.o.Overrun.class));
        cards.add(new SetCardInfo("Phantom Warrior", 14, Rarity.UNCOMMON, mage.cards.p.PhantomWarrior.class));
        cards.add(new SetCardInfo("Prodigal Pyromancer", 51, Rarity.UNCOMMON, mage.cards.p.ProdigalPyromancer.class));
        cards.add(new SetCardInfo("Rage Reflection", 52, Rarity.RARE, mage.cards.r.RageReflection.class));
        cards.add(new SetCardInfo("Rampant Growth", 79, Rarity.COMMON, mage.cards.r.RampantGrowth.class));
        cards.add(new SetCardInfo("Ravenous Rats", 31, Rarity.COMMON, mage.cards.r.RavenousRats.class));
        cards.add(new SetCardInfo("River Boa", 80, Rarity.UNCOMMON, mage.cards.r.RiverBoa.class));
        cards.add(new SetCardInfo("Roughshod Mentor", 81, Rarity.UNCOMMON, mage.cards.r.RoughshodMentor.class));
        cards.add(new SetCardInfo("Runeclaw Bear", 82, Rarity.COMMON, mage.cards.r.RuneclawBear.class));
        cards.add(new SetCardInfo("Sengir Vampire", 32, Rarity.RARE, mage.cards.s.SengirVampire.class));
        cards.add(new SetCardInfo("Severed Legion", 33, Rarity.COMMON, mage.cards.s.SeveredLegion.class));
        cards.add(new SetCardInfo("Shivan Dragon", 53, Rarity.RARE, mage.cards.s.ShivanDragon.class));
        cards.add(new SetCardInfo("Shock", 54, Rarity.COMMON, mage.cards.s.Shock.class));
        cards.add(new SetCardInfo("Snapping Drake", 15, Rarity.COMMON, mage.cards.s.SnappingDrake.class));
        cards.add(new SetCardInfo("Spined Wurm", 83, Rarity.COMMON, mage.cards.s.SpinedWurm.class));
        cards.add(new SetCardInfo("Swamp", 102, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 103, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 104, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 105, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Talara's Battalion", 84, Rarity.RARE, mage.cards.t.TalarasBattalion.class));
        cards.add(new SetCardInfo("Terror", 34, Rarity.COMMON, mage.cards.t.Terror.class));
        cards.add(new SetCardInfo("The Rack", 95, Rarity.UNCOMMON, mage.cards.t.TheRack.class));
        cards.add(new SetCardInfo("Thieving Magpie", 16, Rarity.UNCOMMON, mage.cards.t.ThievingMagpie.class));
        cards.add(new SetCardInfo("Trained Armodon", 85, Rarity.COMMON, mage.cards.t.TrainedArmodon.class));
        cards.add(new SetCardInfo("Troll Ascetic", 86, Rarity.RARE, mage.cards.t.TrollAscetic.class));
        cards.add(new SetCardInfo("Underworld Dreams", 35, Rarity.RARE, mage.cards.u.UnderworldDreams.class));
        cards.add(new SetCardInfo("Unholy Strength", 36, Rarity.COMMON, mage.cards.u.UnholyStrength.class));
        cards.add(new SetCardInfo("Unsummon", 17, Rarity.COMMON, mage.cards.u.Unsummon.class));
        cards.add(new SetCardInfo("Verdant Force", 87, Rarity.RARE, mage.cards.v.VerdantForce.class));
        cards.add(new SetCardInfo("Vigor", 88, Rarity.RARE, mage.cards.v.Vigor.class));
        cards.add(new SetCardInfo("Wall of Spears", 96, Rarity.COMMON, mage.cards.w.WallOfSpears.class));
        cards.add(new SetCardInfo("Wall of Wood", 89, Rarity.COMMON, mage.cards.w.WallOfWood.class));
        cards.add(new SetCardInfo("Wurm's Tooth", 97, Rarity.UNCOMMON, mage.cards.w.WurmsTooth.class));
    }
}
