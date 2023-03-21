package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

public final class CommanderMasters extends ExpansionSet {

    private static final CommanderMasters instance = new CommanderMasters();

    public static CommanderMasters getInstance() {
        return instance;
    }

    private CommanderMasters() {
        super("Commander Masters", "CMM", ExpansionSet.buildDate(2023, 8, 4), SetType.SUPPLEMENTAL);
        this.blockName = "Commander Masters";
        this.hasBasicLands = false;
        this.hasBoosters = false; //temporary

        cards.add(new SetCardInfo("Capture of Jingzhou", 79, Rarity.RARE, mage.cards.c.CaptureOfJingzhou.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Capture of Jingzhou", 483, Rarity.RARE, mage.cards.c.CaptureOfJingzhou.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jeweled Lotus", 396, Rarity.MYTHIC, mage.cards.j.JeweledLotus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jeweled Lotus", 611, Rarity.MYTHIC, mage.cards.j.JeweledLotus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jeweled Lotus", 702, Rarity.MYTHIC, mage.cards.j.JeweledLotus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Ur-Dragon", 361, Rarity.MYTHIC, mage.cards.t.TheUrDragon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Ur-Dragon", 594, Rarity.MYTHIC, mage.cards.t.TheUrDragon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Ur-Dragon", 689, Rarity.MYTHIC, mage.cards.t.TheUrDragon.class, NON_FULL_USE_VARIOUS));
    }
}
