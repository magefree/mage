package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/rqs
 */
public class RivalsQuickStartSet extends ExpansionSet {

    private static final RivalsQuickStartSet instance = new RivalsQuickStartSet();

    public static RivalsQuickStartSet getInstance() {
        return instance;
    }

    private RivalsQuickStartSet() {
        super("Rivals Quick Start Set", "RQS", ExpansionSet.buildDate(1996, 7, 1), SetType.SUPPLEMENTAL);
        this.hasBoosters = false;
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Alabaster Potion", 1, Rarity.COMMON, mage.cards.a.AlabasterPotion.class, RETRO_ART));
        cards.add(new SetCardInfo("Battering Ram", 47, Rarity.COMMON, mage.cards.b.BatteringRam.class, RETRO_ART));
        cards.add(new SetCardInfo("Bog Imp", 15, Rarity.COMMON, mage.cards.b.BogImp.class, RETRO_ART));
        cards.add(new SetCardInfo("Bog Wraith", 16, Rarity.UNCOMMON, mage.cards.b.BogWraith.class, RETRO_ART));
        cards.add(new SetCardInfo("Circle of Protection: Black", 2, Rarity.COMMON, mage.cards.c.CircleOfProtectionBlack.class, RETRO_ART));
        cards.add(new SetCardInfo("Circle of Protection: Red", 3, Rarity.COMMON, mage.cards.c.CircleOfProtectionRed.class, RETRO_ART));
        cards.add(new SetCardInfo("Clockwork Beast", 48, Rarity.RARE, mage.cards.c.ClockworkBeast.class, RETRO_ART));
        cards.add(new SetCardInfo("Cursed Land", 17, Rarity.UNCOMMON, mage.cards.c.CursedLand.class, RETRO_ART));
        cards.add(new SetCardInfo("Dark Ritual", 18, Rarity.COMMON, mage.cards.d.DarkRitual.class, RETRO_ART));
        cards.add(new SetCardInfo("Detonate", 29, Rarity.UNCOMMON, mage.cards.d.Detonate.class, RETRO_ART));
        cards.add(new SetCardInfo("Disintegrate", 30, Rarity.COMMON, mage.cards.d.Disintegrate.class, RETRO_ART));
        cards.add(new SetCardInfo("Durkwood Boars", 38, Rarity.COMMON, mage.cards.d.DurkwoodBoars.class, RETRO_ART));
        cards.add(new SetCardInfo("Elven Riders", 39, Rarity.UNCOMMON, mage.cards.e.ElvenRiders.class, RETRO_ART));
        cards.add(new SetCardInfo("Elvish Archers", 40, Rarity.RARE, mage.cards.e.ElvishArchers.class, RETRO_ART));
        cards.add(new SetCardInfo("Energy Flux", 8, Rarity.UNCOMMON, mage.cards.e.EnergyFlux.class, RETRO_ART));
        cards.add(new SetCardInfo("Feedback", 9, Rarity.UNCOMMON, mage.cards.f.Feedback.class, RETRO_ART));
        cards.add(new SetCardInfo("Fireball", 31, Rarity.COMMON, mage.cards.f.Fireball.class, RETRO_ART));
        cards.add(new SetCardInfo("Forest", 63, Rarity.LAND, mage.cards.basiclands.Forest.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 64, Rarity.LAND, mage.cards.basiclands.Forest.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 65, Rarity.LAND, mage.cards.basiclands.Forest.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Glasses of Urza", 49, Rarity.UNCOMMON, mage.cards.g.GlassesOfUrza.class, RETRO_ART));
        cards.add(new SetCardInfo("Grizzly Bears", 41, Rarity.COMMON, mage.cards.g.GrizzlyBears.class, RETRO_ART));
        cards.add(new SetCardInfo("Healing Salve", 4, Rarity.COMMON, mage.cards.h.HealingSalve.class, RETRO_ART));
        cards.add(new SetCardInfo("Hill Giant", 32, Rarity.COMMON, mage.cards.h.HillGiant.class, RETRO_ART));
        cards.add(new SetCardInfo("Ironclaw Orcs", 33, Rarity.COMMON, mage.cards.i.IronclawOrcs.class, RETRO_ART));
        cards.add(new SetCardInfo("Island", 55, Rarity.LAND, mage.cards.basiclands.Island.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 56, Rarity.LAND, mage.cards.basiclands.Island.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Jayemdae Tome", 50, Rarity.RARE, mage.cards.j.JayemdaeTome.class, RETRO_ART));
        cards.add(new SetCardInfo("Lost Soul", 19, Rarity.COMMON, mage.cards.l.LostSoul.class, RETRO_ART));
        cards.add(new SetCardInfo("Merfolk of the Pearl Trident", 10, Rarity.COMMON, mage.cards.m.MerfolkOfThePearlTrident.class, RETRO_ART));
        cards.add(new SetCardInfo("Mesa Pegasus", 5, Rarity.COMMON, mage.cards.m.MesaPegasus.class, RETRO_ART));
        cards.add(new SetCardInfo("Mons's Goblin Raiders", 34, Rarity.COMMON, mage.cards.m.MonssGoblinRaiders.class, RETRO_ART));
        cards.add(new SetCardInfo("Mountain", 60, Rarity.LAND, mage.cards.basiclands.Mountain.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 61, Rarity.LAND, mage.cards.basiclands.Mountain.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 62, Rarity.LAND, mage.cards.basiclands.Mountain.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Murk Dwellers", 20, Rarity.COMMON, mage.cards.m.MurkDwellers.class, RETRO_ART));
        cards.add(new SetCardInfo("Orcish Artillery", 35, Rarity.UNCOMMON, mage.cards.o.OrcishArtillery.class, RETRO_ART));
        cards.add(new SetCardInfo("Orcish Oriflamme", 36, Rarity.UNCOMMON, mage.cards.o.OrcishOriflamme.class, RETRO_ART));
        cards.add(new SetCardInfo("Pearled Unicorn", 6, Rarity.COMMON, mage.cards.p.PearledUnicorn.class, RETRO_ART));
        cards.add(new SetCardInfo("Plains", 52, Rarity.LAND, mage.cards.basiclands.Plains.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 53, Rarity.LAND, mage.cards.basiclands.Plains.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 54, Rarity.LAND, mage.cards.basiclands.Plains.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Power Sink", 11, Rarity.COMMON, mage.cards.p.PowerSink.class, RETRO_ART));
        cards.add(new SetCardInfo("Pyrotechnics", 37, Rarity.UNCOMMON, mage.cards.p.Pyrotechnics.class, RETRO_ART));
        cards.add(new SetCardInfo("Raise Dead", 21, Rarity.COMMON, mage.cards.r.RaiseDead.class, RETRO_ART));
        cards.add(new SetCardInfo("Reverse Damage", 7, Rarity.RARE, mage.cards.r.ReverseDamage.class, RETRO_ART));
        cards.add(new SetCardInfo("Rod of Ruin", 51, Rarity.UNCOMMON, mage.cards.r.RodOfRuin.class, RETRO_ART));
        cards.add(new SetCardInfo("Scathe Zombies", 22, Rarity.COMMON, mage.cards.s.ScatheZombies.class, RETRO_ART));
        cards.add(new SetCardInfo("Scryb Sprites", 42, Rarity.COMMON, mage.cards.s.ScrybSprites.class, RETRO_ART));
        cards.add(new SetCardInfo("Sorceress Queen", 23, Rarity.RARE, mage.cards.s.SorceressQueen.class, RETRO_ART));
        cards.add(new SetCardInfo("Swamp", 57, Rarity.LAND, mage.cards.basiclands.Swamp.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 58, Rarity.LAND, mage.cards.basiclands.Swamp.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 59, Rarity.LAND, mage.cards.basiclands.Swamp.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Terror", 24, Rarity.COMMON, mage.cards.t.Terror.class, RETRO_ART));
        cards.add(new SetCardInfo("Twiddle", 12, Rarity.COMMON, mage.cards.t.Twiddle.class, RETRO_ART));
        cards.add(new SetCardInfo("Unsummon", 13, Rarity.COMMON, mage.cards.u.Unsummon.class, RETRO_ART));
        cards.add(new SetCardInfo("Untamed Wilds", 43, Rarity.UNCOMMON, mage.cards.u.UntamedWilds.class, RETRO_ART));
        cards.add(new SetCardInfo("Vampire Bats", 25, Rarity.COMMON, mage.cards.v.VampireBats.class, RETRO_ART));
        cards.add(new SetCardInfo("Wall of Bone", 26, Rarity.UNCOMMON, mage.cards.w.WallOfBone.class, RETRO_ART));
        cards.add(new SetCardInfo("War Mammoth", 44, Rarity.COMMON, mage.cards.w.WarMammoth.class, RETRO_ART));
        cards.add(new SetCardInfo("Warp Artifact", 27, Rarity.RARE, mage.cards.w.WarpArtifact.class, RETRO_ART));
        cards.add(new SetCardInfo("Weakness", 28, Rarity.COMMON, mage.cards.w.Weakness.class, RETRO_ART));
        cards.add(new SetCardInfo("Whirling Dervish", 45, Rarity.UNCOMMON, mage.cards.w.WhirlingDervish.class, RETRO_ART));
        cards.add(new SetCardInfo("Winter Blast", 46, Rarity.UNCOMMON, mage.cards.w.WinterBlast.class, RETRO_ART));
        cards.add(new SetCardInfo("Zephyr Falcon", 14, Rarity.COMMON, mage.cards.z.ZephyrFalcon.class, RETRO_ART));
     }
}
