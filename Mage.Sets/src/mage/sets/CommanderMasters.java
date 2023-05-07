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

        cards.add(new SetCardInfo("Anikthea, Hand of Erebos", 705, Rarity.MYTHIC, mage.cards.a.AniktheaHandOfErebos.class));
        cards.add(new SetCardInfo("Capture of Jingzhou", 79, Rarity.RARE, mage.cards.c.CaptureOfJingzhou.class));
        cards.add(new SetCardInfo("Commodore Guff", 706, Rarity.MYTHIC, mage.cards.c.CommodoreGuff.class));
        cards.add(new SetCardInfo("Jeweled Lotus", 396, Rarity.MYTHIC, mage.cards.j.JeweledLotus.class));
        cards.add(new SetCardInfo("Personal Tutor", 110, Rarity.RARE, mage.cards.p.PersonalTutor.class));
        cards.add(new SetCardInfo("Selvala, Heart of the Wilds", 220, Rarity.MYTHIC, mage.cards.s.SelvalaHeartOfTheWilds.class));
        cards.add(new SetCardInfo("Sliver Gravemother", 707, Rarity.MYTHIC, mage.cards.s.SliverGravemother.class));
        cards.add(new SetCardInfo("The Ur-Dragon", 361, Rarity.MYTHIC, mage.cards.t.TheUrDragon.class));
    }
}