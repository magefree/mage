package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

public final class EighthEditionBox extends ExpansionSet {

    private static final EighthEditionBox instance = new EighthEditionBox();

    public static EighthEditionBox getInstance() {
        return instance;
    }

    private EighthEditionBox() {
        super("Eighth Edition Box", "8EB", ExpansionSet.buildDate(2003, 7, 28), SetType.CORE);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        // http://www.magiclibrarities.net/540-rarities-eighth-edition-box-set-cards-english-cards-index.html
        cards.add(new SetCardInfo("Eager Cadet", "S1", Rarity.COMMON, mage.cards.e.EagerCadet.class));
        cards.add(new SetCardInfo("Enormous Baloth", "S6", Rarity.UNCOMMON, mage.cards.e.EnormousBaloth.class));
        cards.add(new SetCardInfo("Giant Octopus", "S3", Rarity.COMMON, mage.cards.g.GiantOctopus.class));
        cards.add(new SetCardInfo("Sea Eagle", "S4", Rarity.COMMON, mage.cards.s.SeaEagle.class));
        cards.add(new SetCardInfo("Silverback Ape", "S7", Rarity.UNCOMMON, mage.cards.s.SilverbackApe.class));
        cards.add(new SetCardInfo("Vengeance", "S2", Rarity.UNCOMMON, mage.cards.v.Vengeance.class));
        cards.add(new SetCardInfo("Vizzerdrix", "S5", Rarity.RARE, mage.cards.v.Vizzerdrix.class));
    }
}
