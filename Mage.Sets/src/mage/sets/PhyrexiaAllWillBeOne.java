package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class PhyrexiaAllWillBeOne extends ExpansionSet {

    private static final PhyrexiaAllWillBeOne instance = new PhyrexiaAllWillBeOne();

    public static PhyrexiaAllWillBeOne getInstance() {
        return instance;
    }

    private PhyrexiaAllWillBeOne() {
        super("Phyrexia: All Will Be One", "ONE", ExpansionSet.buildDate(2023, 1, 10), SetType.EXPANSION);
        this.blockName = "Phyrexia: All Will Be One";
        this.hasBoosters = false; // temporary

        cards.add(new SetCardInfo("Blackcleave Cliffs", 248, Rarity.RARE, mage.cards.b.BlackcleaveCliffs.class));
        cards.add(new SetCardInfo("Bloated Contaminator", 159, Rarity.RARE, mage.cards.b.BloatedContaminator.class));
        cards.add(new SetCardInfo("Blue Sun's Twilight", 43, Rarity.RARE, mage.cards.b.BlueSunsTwilight.class));
        cards.add(new SetCardInfo("Copperline Gorge", 249, Rarity.RARE, mage.cards.c.CopperlineGorge.class));
        cards.add(new SetCardInfo("Dragonwing Glider", 126, Rarity.RARE, mage.cards.d.DragonwingGlider.class));
        cards.add(new SetCardInfo("Elesh Norn, Mother of Machines", 10, Rarity.MYTHIC, mage.cards.e.EleshNornMotherOfMachines.class));
        cards.add(new SetCardInfo("Evolved Spinoderm", 166, Rarity.RARE, mage.cards.e.EvolvedSpinoderm.class));
        cards.add(new SetCardInfo("Ezuri, Stalker of Spheres", 201, Rarity.RARE, mage.cards.e.EzuriStalkerOfSpheres.class));
        cards.add(new SetCardInfo("Forest", 276, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 273, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Karumonix, the Rat King", 292, Rarity.RARE, mage.cards.k.KarumonixTheRatKing.class));
        cards.add(new SetCardInfo("Koth, Fire of Resistance", 138, Rarity.RARE, mage.cards.k.KothFireOfResistance.class));
        cards.add(new SetCardInfo("Mindsplice Apparatus", 63, Rarity.RARE, mage.cards.m.MindspliceApparatus.class));
        cards.add(new SetCardInfo("Mirrex", 254, Rarity.RARE, mage.cards.m.Mirrex.class));
        cards.add(new SetCardInfo("Mountain", 275, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Norn's Wellspring", 24, Rarity.RARE, mage.cards.n.NornsWellspring.class));
        cards.add(new SetCardInfo("Phyrexian Arena", 104, Rarity.RARE, mage.cards.p.PhyrexianArena.class));
        cards.add(new SetCardInfo("Phyrexian Obliterator", 105, Rarity.MYTHIC, mage.cards.p.PhyrexianObliterator.class));
        cards.add(new SetCardInfo("Plains", 272, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Razorverge Thicket", 257, Rarity.RARE, mage.cards.r.RazorvergeThicket.class));
        cards.add(new SetCardInfo("Seachrome Coast", 258, Rarity.RARE, mage.cards.s.SeachromeCoast.class));
        cards.add(new SetCardInfo("Skrelv's Hive", 34, Rarity.RARE, mage.cards.s.SkrelvsHive.class));
        cards.add(new SetCardInfo("Swamp", 274, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Monumental Facade", 255, Rarity.RARE, mage.cards.t.TheMonumentalFacade.class));
    }

//    @Override
//    public BoosterCollator createCollator() {
//        return new PhyrexiaAllWillBeOneCollator();
//    }
}
