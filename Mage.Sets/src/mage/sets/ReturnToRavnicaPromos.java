package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/prtr
 */
public class ReturnToRavnicaPromos extends ExpansionSet {

    private static final ReturnToRavnicaPromos instance = new ReturnToRavnicaPromos();

    public static ReturnToRavnicaPromos getInstance() {
        return instance;
    }

    private ReturnToRavnicaPromos() {
        super("Return to Ravnica Promos", "PRTR", ExpansionSet.buildDate(2012, 10, 5), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Archon of the Triumvirate", "142*", Rarity.RARE, mage.cards.a.ArchonOfTheTriumvirate.class));
        cards.add(new SetCardInfo("Carnival Hellsteed", "147*", Rarity.RARE, mage.cards.c.CarnivalHellsteed.class));
        cards.add(new SetCardInfo("Corpsejack Menace", "152*", Rarity.RARE, mage.cards.c.CorpsejackMenace.class));
        cards.add(new SetCardInfo("Cryptborn Horror", 212, Rarity.RARE, mage.cards.c.CryptbornHorror.class));
        cards.add(new SetCardInfo("Deadbridge Goliath", "120*", Rarity.RARE, mage.cards.d.DeadbridgeGoliath.class));
        cards.add(new SetCardInfo("Dreg Mangler", 158, Rarity.UNCOMMON, mage.cards.d.DregMangler.class));
        cards.add(new SetCardInfo("Dryad Militant", 214, Rarity.UNCOMMON, mage.cards.d.DryadMilitant.class));
        cards.add(new SetCardInfo("Grove of the Guardian", "240*", Rarity.RARE, mage.cards.g.GroveOfTheGuardian.class));
        cards.add(new SetCardInfo("Hypersonic Dragon", "170*", Rarity.RARE, mage.cards.h.HypersonicDragon.class));
        cards.add(new SetCardInfo("Supreme Verdict", "201*", Rarity.RARE, mage.cards.s.SupremeVerdict.class));
    }
}
