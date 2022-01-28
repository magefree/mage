package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class KamigawaNeonDynasty extends ExpansionSet {

    private static final KamigawaNeonDynasty instance = new KamigawaNeonDynasty();

    public static KamigawaNeonDynasty getInstance() {
        return instance;
    }

    private KamigawaNeonDynasty() {
        super("Kamigawa: Neon Dynasty", "NEO", ExpansionSet.buildDate(2022, 2, 18), SetType.EXPANSION);
        this.blockName = "Kamigawa: Neon Dynasty";
        this.hasBoosters = true;
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Atsushi, the Blazing Sky", 134, Rarity.MYTHIC, mage.cards.a.AtsushiTheBlazingSky.class));
        cards.add(new SetCardInfo("Forest", 301, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Goro-Goro, Disciple of Ryusei", 145, Rarity.RARE, mage.cards.g.GoroGoroDiscipleOfRyusei.class));
        cards.add(new SetCardInfo("Hidetsugu, Devouring Chaos", 99, Rarity.RARE, mage.cards.h.HidetsuguDevouringChaos.class));
        cards.add(new SetCardInfo("Island", 295, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Kaito Shizuki", 226, Rarity.MYTHIC, mage.cards.k.KaitoShizuki.class));
        cards.add(new SetCardInfo("Mountain", 299, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Nameless Conqueror", 162, Rarity.COMMON, mage.cards.n.NamelessConqueror.class));
        cards.add(new SetCardInfo("Plains", 293, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Satoru Umezawa", 234, Rarity.RARE, mage.cards.s.SatoruUmezawa.class));
        cards.add(new SetCardInfo("Swamp", 297, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("The Modern Age", 66, Rarity.COMMON, mage.cards.t.TheModernAge.class));
        cards.add(new SetCardInfo("The Shattered States Era", 162, Rarity.COMMON, mage.cards.t.TheShatteredStatesEra.class));
        cards.add(new SetCardInfo("Vector Glider", 66, Rarity.COMMON, mage.cards.v.VectorGlider.class));
    }
}
