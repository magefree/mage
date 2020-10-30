package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/f09
 */
public class FridayNightMagic2009 extends ExpansionSet {

    private static final FridayNightMagic2009 instance = new FridayNightMagic2009();

    public static FridayNightMagic2009 getInstance() {
        return instance;
    }

    private FridayNightMagic2009() {
        super("Friday Night Magic 2009", "F09", ExpansionSet.buildDate(2009, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Browbeat", 10, Rarity.RARE, mage.cards.b.Browbeat.class));
        cards.add(new SetCardInfo("Kitchen Finks", 3, Rarity.RARE, mage.cards.k.KitchenFinks.class));
        cards.add(new SetCardInfo("Lightning Greaves", 8, Rarity.RARE, mage.cards.l.LightningGreaves.class));
        cards.add(new SetCardInfo("Magma Jet", 1, Rarity.RARE, mage.cards.m.MagmaJet.class));
        cards.add(new SetCardInfo("Merrow Reejerey", 4, Rarity.RARE, mage.cards.m.MerrowReejerey.class));
        cards.add(new SetCardInfo("Mulldrifter", 6, Rarity.RARE, mage.cards.m.Mulldrifter.class));
        cards.add(new SetCardInfo("Murderous Redcap", 7, Rarity.RARE, mage.cards.m.MurderousRedcap.class));
        cards.add(new SetCardInfo("Myr Enforcer", 2, Rarity.RARE, mage.cards.m.MyrEnforcer.class));
        cards.add(new SetCardInfo("Oblivion Ring", 11, Rarity.RARE, mage.cards.o.OblivionRing.class));
        cards.add(new SetCardInfo("Sakura-Tribe Elder", 12, Rarity.RARE, mage.cards.s.SakuraTribeElder.class));
        cards.add(new SetCardInfo("Watchwolf", 9, Rarity.RARE, mage.cards.w.Watchwolf.class));
        cards.add(new SetCardInfo("Wren's Run Vanquisher", 5, Rarity.RARE, mage.cards.w.WrensRunVanquisher.class));
     }
}
