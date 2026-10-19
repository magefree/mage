package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author muz
 */
public final class MarvelDarkholdDestiny extends ExpansionSet {

    private static final MarvelDarkholdDestiny instance = new MarvelDarkholdDestiny();

    public static MarvelDarkholdDestiny getInstance() {
        return instance;
    }

    private MarvelDarkholdDestiny() {
        super("Marvel: Darkhold Destiny", "MDD", ExpansionSet.buildDate(2027, 04, 9), SetType.EXPANSION);
        this.blockName = "Marvel: Darkhold Destiny"; // for sorting in GUI
        this.hasBasicLands = true;

        // this.enablePlayBooster(358);

        cards.add(new SetCardInfo("Forest", 301, Rarity.LAND, mage.cards.basiclands.Forest.class));
        cards.add(new SetCardInfo("Island", 294, Rarity.LAND, mage.cards.basiclands.Island.class));
        cards.add(new SetCardInfo("Magik, Illyana Rasputin", 242, Rarity.MYTHIC, mage.cards.m.MagikIllyanaRasputin.class));
        cards.add(new SetCardInfo("Mountain", 298, Rarity.LAND, mage.cards.basiclands.Mountain.class));
        cards.add(new SetCardInfo("Penance Stare", 28, Rarity.UNCOMMON, mage.cards.p.PenanceStare.class));
        cards.add(new SetCardInfo("Plains", 292, Rarity.LAND, mage.cards.basiclands.Plains.class));
        cards.add(new SetCardInfo("Rise from the Nether", 161, Rarity.UNCOMMON, mage.cards.r.RiseFromTheNether.class));
        cards.add(new SetCardInfo("Swamp", 296, Rarity.LAND, mage.cards.basiclands.Swamp.class));
        cards.add(new SetCardInfo("The Darkhold", 92, Rarity.MYTHIC, mage.cards.t.TheDarkhold.class));
    }
}
