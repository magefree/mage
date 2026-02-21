package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pw26
 *
 * @author muz
 */
public class WizardsPlayNetwork2026 extends ExpansionSet {

    private static final WizardsPlayNetwork2026 instance = new WizardsPlayNetwork2026();

    public static WizardsPlayNetwork2026 getInstance() {
        return instance;
    }

    private WizardsPlayNetwork2026() {
        super("Wizards Play Network 2026", "PW26", ExpansionSet.buildDate(2026, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Courier of Comestibles", 6, Rarity.RARE, mage.cards.c.CourierOfComestibles.class));
        cards.add(new SetCardInfo("Farhaven Elf", 2, Rarity.RARE, mage.cards.f.FarhavenElf.class, RETRO_ART));
        cards.add(new SetCardInfo("Gilded Lotus", 3, Rarity.RARE, mage.cards.g.GildedLotus.class));
        cards.add(new SetCardInfo("Lightning Bolt", 5, Rarity.RARE, mage.cards.l.LightningBolt.class));
        cards.add(new SetCardInfo("Nowhere to Run", 1, Rarity.RARE, mage.cards.n.NowhereToRun.class, RETRO_ART));
        cards.add(new SetCardInfo("Spicy Oatmeal Pizza", 7, Rarity.RARE, mage.cards.s.SpicyOatmealPizza.class));
        cards.add(new SetCardInfo("The Eternal Wanderer", 4, Rarity.MYTHIC, mage.cards.t.TheEternalWanderer.class));
    }
}
