package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/psom
 */
public class ScarsOfMirrodinPromos extends ExpansionSet {

    private static final ScarsOfMirrodinPromos instance = new ScarsOfMirrodinPromos();

    public static ScarsOfMirrodinPromos getInstance() {
        return instance;
    }

    private ScarsOfMirrodinPromos() {
        super("Scars of Mirrodin Promos", "PSOM", ExpansionSet.buildDate(2010, 9, 30), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Memnite", 174, Rarity.UNCOMMON, mage.cards.m.Memnite.class));
        cards.add(new SetCardInfo("Memoricide", "69*", Rarity.RARE, mage.cards.m.Memoricide.class));
        cards.add(new SetCardInfo("Steel Hellkite", "205*", Rarity.RARE, mage.cards.s.SteelHellkite.class));
        cards.add(new SetCardInfo("Tempered Steel", 24, Rarity.RARE, mage.cards.t.TemperedSteel.class));
        cards.add(new SetCardInfo("Wurmcoil Engine", "223*", Rarity.MYTHIC, mage.cards.w.WurmcoilEngine.class));
    }
}
