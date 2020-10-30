package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/itp
 */
public class IntroductoryTwoPlayerSet extends ExpansionSet {

    private static final IntroductoryTwoPlayerSet instance = new IntroductoryTwoPlayerSet();

    public static IntroductoryTwoPlayerSet getInstance() {
        return instance;
    }

    private IntroductoryTwoPlayerSet() {
        super("Introductory Two-Player Set", "ITP", ExpansionSet.buildDate(1996, 12, 31), SetType.SUPPLEMENTAL);
        this.hasBoosters = false;
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Alabaster Potion", 1, Rarity.COMMON, mage.cards.a.AlabasterPotion.class));
        cards.add(new SetCardInfo("Battering Ram", 48, Rarity.COMMON, mage.cards.b.BatteringRam.class));
        cards.add(new SetCardInfo("Bog Imp", 16, Rarity.COMMON, mage.cards.b.BogImp.class));
        cards.add(new SetCardInfo("Bog Wraith", 17, Rarity.UNCOMMON, mage.cards.b.BogWraith.class));
        cards.add(new SetCardInfo("Circle of Protection: Black", 2, Rarity.COMMON, mage.cards.c.CircleOfProtectionBlack.class));
        cards.add(new SetCardInfo("Circle of Protection: Red", 3, Rarity.COMMON, mage.cards.c.CircleOfProtectionRed.class));
        cards.add(new SetCardInfo("Clockwork Beast", 49, Rarity.RARE, mage.cards.c.ClockworkBeast.class));
        cards.add(new SetCardInfo("Cursed Land", 18, Rarity.UNCOMMON, mage.cards.c.CursedLand.class));
        cards.add(new SetCardInfo("Dark Ritual", 19, Rarity.COMMON, mage.cards.d.DarkRitual.class));
        cards.add(new SetCardInfo("Detonate", 30, Rarity.UNCOMMON, mage.cards.d.Detonate.class));
        cards.add(new SetCardInfo("Disintegrate", 31, Rarity.COMMON, mage.cards.d.Disintegrate.class));
        cards.add(new SetCardInfo("Durkwood Boars", 39, Rarity.COMMON, mage.cards.d.DurkwoodBoars.class));
        cards.add(new SetCardInfo("Elven Riders", 40, Rarity.UNCOMMON, mage.cards.e.ElvenRiders.class));
        cards.add(new SetCardInfo("Elvish Archers", 41, Rarity.RARE, mage.cards.e.ElvishArchers.class));
        cards.add(new SetCardInfo("Energy Flux", 8, Rarity.UNCOMMON, mage.cards.e.EnergyFlux.class));
        cards.add(new SetCardInfo("Feedback", 9, Rarity.UNCOMMON, mage.cards.f.Feedback.class));
        cards.add(new SetCardInfo("Fireball", 32, Rarity.COMMON, mage.cards.f.Fireball.class));
        cards.add(new SetCardInfo("Forest", 67, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 66, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 65, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Glasses of Urza", 50, Rarity.UNCOMMON, mage.cards.g.GlassesOfUrza.class));
        cards.add(new SetCardInfo("Grizzly Bears", 42, Rarity.COMMON, mage.cards.g.GrizzlyBears.class));
        cards.add(new SetCardInfo("Healing Salve", 4, Rarity.COMMON, mage.cards.h.HealingSalve.class));
        cards.add(new SetCardInfo("Hill Giant", 33, Rarity.COMMON, mage.cards.h.HillGiant.class));
        cards.add(new SetCardInfo("Ironclaw Orcs", 34, Rarity.COMMON, mage.cards.i.IronclawOrcs.class));
        cards.add(new SetCardInfo("Island", 58, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 57, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 56, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jayemdae Tome", 51, Rarity.RARE, mage.cards.j.JayemdaeTome.class));
        cards.add(new SetCardInfo("Lost Soul", 20, Rarity.COMMON, mage.cards.l.LostSoul.class));
        cards.add(new SetCardInfo("Merfolk of the Pearl Trident", 10, Rarity.COMMON, mage.cards.m.MerfolkOfThePearlTrident.class));
        cards.add(new SetCardInfo("Mesa Pegasus", 5, Rarity.COMMON, mage.cards.m.MesaPegasus.class));
        cards.add(new SetCardInfo("Mons's Goblin Raiders", 35, Rarity.COMMON, mage.cards.m.MonssGoblinRaiders.class));
        cards.add(new SetCardInfo("Mountain", 64, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 63, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 62, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Murk Dwellers", 21, Rarity.COMMON, mage.cards.m.MurkDwellers.class));
        cards.add(new SetCardInfo("Orcish Artillery", 36, Rarity.UNCOMMON, mage.cards.o.OrcishArtillery.class));
        cards.add(new SetCardInfo("Orcish Oriflamme", 37, Rarity.UNCOMMON, mage.cards.o.OrcishOriflamme.class));
        cards.add(new SetCardInfo("Pearled Unicorn", 6, Rarity.COMMON, mage.cards.p.PearledUnicorn.class));
        cards.add(new SetCardInfo("Phantom Monster", 11, Rarity.UNCOMMON, mage.cards.p.PhantomMonster.class));
        cards.add(new SetCardInfo("Plains", 55, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 54, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 53, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Power Sink", 12, Rarity.COMMON, mage.cards.p.PowerSink.class));
        cards.add(new SetCardInfo("Pyrotechnics", 38, Rarity.UNCOMMON, mage.cards.p.Pyrotechnics.class));
        cards.add(new SetCardInfo("Raise Dead", 22, Rarity.COMMON, mage.cards.r.RaiseDead.class));
        cards.add(new SetCardInfo("Reverse Damage", 7, Rarity.RARE, mage.cards.r.ReverseDamage.class));
        cards.add(new SetCardInfo("Rod of Ruin", 52, Rarity.UNCOMMON, mage.cards.r.RodOfRuin.class));
        cards.add(new SetCardInfo("Scathe Zombies", 23, Rarity.COMMON, mage.cards.s.ScatheZombies.class));
        cards.add(new SetCardInfo("Scryb Sprites", 43, Rarity.COMMON, mage.cards.s.ScrybSprites.class));
        cards.add(new SetCardInfo("Sorceress Queen", 24, Rarity.RARE, mage.cards.s.SorceressQueen.class));
        cards.add(new SetCardInfo("Swamp", 61, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 60, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 59, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Terror", 25, Rarity.COMMON, mage.cards.t.Terror.class));
        cards.add(new SetCardInfo("Twiddle", 13, Rarity.COMMON, mage.cards.t.Twiddle.class));
        cards.add(new SetCardInfo("Unsummon", 14, Rarity.COMMON, mage.cards.u.Unsummon.class));
        cards.add(new SetCardInfo("Untamed Wilds", 44, Rarity.UNCOMMON, mage.cards.u.UntamedWilds.class));
        cards.add(new SetCardInfo("Vampire Bats", 26, Rarity.COMMON, mage.cards.v.VampireBats.class));
        cards.add(new SetCardInfo("Wall of Bone", 27, Rarity.UNCOMMON, mage.cards.w.WallOfBone.class));
        cards.add(new SetCardInfo("War Mammoth", 45, Rarity.COMMON, mage.cards.w.WarMammoth.class));
        cards.add(new SetCardInfo("Warp Artifact", 28, Rarity.RARE, mage.cards.w.WarpArtifact.class));
        cards.add(new SetCardInfo("Weakness", 29, Rarity.COMMON, mage.cards.w.Weakness.class));
        cards.add(new SetCardInfo("Whirling Dervish", 46, Rarity.UNCOMMON, mage.cards.w.WhirlingDervish.class));
        cards.add(new SetCardInfo("Winter Blast", 47, Rarity.UNCOMMON, mage.cards.w.WinterBlast.class));
        cards.add(new SetCardInfo("Zephyr Falcon", 15, Rarity.COMMON, mage.cards.z.ZephyrFalcon.class));
     }
}
