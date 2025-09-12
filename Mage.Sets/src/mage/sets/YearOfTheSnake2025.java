package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pl25
 */
public class YearOfTheSnake2025 extends ExpansionSet {

    private static final YearOfTheSnake2025 instance = new YearOfTheSnake2025();

    public static YearOfTheSnake2025 getInstance() {
        return instance;
    }

    private YearOfTheSnake2025() {
        super("Year of the Snake 2025", "PL25", ExpansionSet.buildDate(2025, 2, 14), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Forest", 6, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Kaseto, Orochi Archmage", 5, Rarity.MYTHIC, mage.cards.k.KasetoOrochiArchmage.class));
        cards.add(new SetCardInfo("Lotus Cobra", 3, Rarity.RARE, mage.cards.l.LotusCobra.class));
        cards.add(new SetCardInfo("Sakura-Tribe Elder", 4, Rarity.RARE, mage.cards.s.SakuraTribeElder.class));
        cards.add(new SetCardInfo("Xyris, the Writhing Storm", 1, Rarity.MYTHIC, mage.cards.x.XyrisTheWrithingStorm.class));
    }
}
