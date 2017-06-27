package mage.sets;

import java.util.Date;
import mage.cards.CardGraphicInfo;
import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

public class EighthEditionBox extends ExpansionSet {
    
    private static final EighthEditionBox instance = new EighthEditionBox();
    
    public static EighthEditionBox getInstance() {
        return instance;
    }


    private EighthEditionBox() {
        super("EighthEditionBox","8EB", ExpansionSet.buildDate(2003, 7, 28), SetType.CORE);
        this.hasBoosters=false;
        cards.add(new SetCardInfo("Eager Cadet", 1, Rarity.COMMON, mage.cards.e.EagerCadet.class));
        cards.add(new SetCardInfo("Vengeance", 2, Rarity.UNCOMMON, mage.cards.v.Vengeance.class));
        cards.add(new SetCardInfo("Giant Octopus", 3, Rarity.COMMON, mage.cards.g.GiantOctopus.class));
        cards.add(new SetCardInfo("Sea Eagle", 4, Rarity.COMMON, mage.cards.s.SeaEagle.class));
        cards.add(new SetCardInfo("Vizzerdrix", 5, Rarity.RARE, mage.cards.v.Vizzerdrix.class));
        cards.add(new SetCardInfo("Enormous Baloth", 6, Rarity.UNCOMMON, mage.cards.e.EnormousBaloth.class));
        cards.add(new SetCardInfo("Silverback Ape", 7, Rarity.UNCOMMON, mage.cards.s.SilverbackApe.class));
    }
}            