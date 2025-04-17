package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class MarvelsSpiderManEternal extends ExpansionSet {

    private static final MarvelsSpiderManEternal instance = new MarvelsSpiderManEternal();

    public static MarvelsSpiderManEternal getInstance() {
        return instance;
    }

    private MarvelsSpiderManEternal() {
        super("Marvel's Spider-Man Eternal", "SPE", ExpansionSet.buildDate(2025, 9, 26), SetType.EXPANSION);
        this.blockName = "Marvel's Spider-Man"; // for sorting in GUI
        this.hasBasicLands = false; // temporary

        cards.add(new SetCardInfo("Doc Ock, Evil Inventor", 24, Rarity.RARE, mage.cards.d.DocOckEvilInventor.class));
        cards.add(new SetCardInfo("Grasping Tentacles", 21, Rarity.RARE, mage.cards.g.GraspingTentacles.class));
        cards.add(new SetCardInfo("Green Goblin, Nemesis", 23, Rarity.RARE, mage.cards.g.GreenGoblinNemesis.class));
        cards.add(new SetCardInfo("Pumpkin Bombs", 26, Rarity.RARE, mage.cards.p.PumpkinBombs.class));
        cards.add(new SetCardInfo("Venom, Deadly Devourer", 22, Rarity.RARE, mage.cards.v.VenomDeadlyDevourer.class));
    }
}
