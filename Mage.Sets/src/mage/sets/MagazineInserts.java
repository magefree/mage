package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pmei
 */
public class MagazineInserts extends ExpansionSet {

    private static final MagazineInserts instance = new MagazineInserts();

    public static MagazineInserts getInstance() {
        return instance;
    }

    private MagazineInserts() {
        super("Magazine Inserts", "PMEI", ExpansionSet.buildDate(2020, 3, 26), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        // some cards are non-English (most are Japanese, Jamuraan Lion has a German printing), but it's ok - scryfall can download it

        cards.add(new SetCardInfo("Archangel", 29, Rarity.RARE, mage.cards.a.Archangel.class));
        cards.add(new SetCardInfo("Ascendant Evincar", 28, Rarity.RARE, mage.cards.a.AscendantEvincar.class));
        cards.add(new SetCardInfo("Blue Elemental Blast", 5, Rarity.COMMON, mage.cards.b.BlueElementalBlast.class));
        cards.add(new SetCardInfo("Cast Down", 30, Rarity.UNCOMMON, mage.cards.c.CastDown.class));
        cards.add(new SetCardInfo("Chandra's Outrage", 18, Rarity.COMMON, mage.cards.c.ChandrasOutrage.class));
        cards.add(new SetCardInfo("Chandra's Spitfire", 19, Rarity.UNCOMMON, mage.cards.c.ChandrasSpitfire.class));
        cards.add(new SetCardInfo("Cunning Sparkmage", 17, Rarity.UNCOMMON, mage.cards.c.CunningSparkmage.class));
        cards.add(new SetCardInfo("Darksteel Juggernaut", 16, Rarity.RARE, mage.cards.d.DarksteelJuggernaut.class));
        cards.add(new SetCardInfo("Daxos, Blessed by the Sun", 36, Rarity.UNCOMMON, mage.cards.d.DaxosBlessedByTheSun.class));
        cards.add(new SetCardInfo("Diabolic Edict", 31, Rarity.RARE, mage.cards.d.DiabolicEdict.class));
        cards.add(new SetCardInfo("Duress", 34, Rarity.RARE, mage.cards.d.Duress.class));
        cards.add(new SetCardInfo("Fireball", 4, Rarity.COMMON, mage.cards.f.Fireball.class));
        cards.add(new SetCardInfo("Jamuraan Lion", "10*", Rarity.COMMON, mage.cards.j.JamuraanLion.class));
        cards.add(new SetCardInfo("Kuldotha Phoenix", 20, Rarity.RARE, mage.cards.k.KuldothaPhoenix.class));
        cards.add(new SetCardInfo("Lava Coil", 33, Rarity.UNCOMMON, mage.cards.l.LavaCoil.class));
        cards.add(new SetCardInfo("Lightning Hounds", 10, Rarity.COMMON, mage.cards.l.LightningHounds.class));
        cards.add(new SetCardInfo("Parallax Dementia", 27, Rarity.COMMON, mage.cards.p.ParallaxDementia.class));
        cards.add(new SetCardInfo("Phantasmal Dragon", 21, Rarity.UNCOMMON, mage.cards.p.PhantasmalDragon.class));
        cards.add(new SetCardInfo("Phyrexian Rager", 14, Rarity.COMMON, mage.cards.p.PhyrexianRager.class));
        cards.add(new SetCardInfo("Sandbar Crocodile", 22, Rarity.COMMON, mage.cards.s.SandbarCrocodile.class));
        cards.add(new SetCardInfo("Scent of Cinder", 9, Rarity.COMMON, mage.cards.s.ScentOfCinder.class));
        cards.add(new SetCardInfo("Shivan Dragon", 15, Rarity.RARE, mage.cards.s.ShivanDragon.class));
        cards.add(new SetCardInfo("Shock", 32, Rarity.RARE, mage.cards.s.Shock.class));
        cards.add(new SetCardInfo("Shrieking Drake", 24, Rarity.COMMON, mage.cards.s.ShriekingDrake.class));
        cards.add(new SetCardInfo("Silver Drake", 13, Rarity.COMMON, mage.cards.s.SilverDrake.class));
        cards.add(new SetCardInfo("Spined Wurm", 11, Rarity.COMMON, mage.cards.s.SpinedWurm.class));
        cards.add(new SetCardInfo("Staggering Insight", 37, Rarity.RARE, mage.cards.s.StaggeringInsight.class));
        cards.add(new SetCardInfo("Stream of Life", 25, Rarity.COMMON, mage.cards.s.StreamOfLife.class));
        cards.add(new SetCardInfo("Thorn Elemental", 26, Rarity.RARE, mage.cards.t.ThornElemental.class));
        cards.add(new SetCardInfo("Voltaic Key", 35, Rarity.RARE, mage.cards.v.VoltaicKey.class));
        cards.add(new SetCardInfo("Warmonger", 12, Rarity.UNCOMMON, mage.cards.w.Warmonger.class));
        cards.add(new SetCardInfo("Zhalfirin Knight", 23, Rarity.COMMON, mage.cards.z.ZhalfirinKnight.class));
    }
}
