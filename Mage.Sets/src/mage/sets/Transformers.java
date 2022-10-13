package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class Transformers extends ExpansionSet {

    private static final Transformers instance = new Transformers();

    public static Transformers getInstance() {
        return instance;
    }

    private Transformers() {
        super("Transformers", "BOT", ExpansionSet.buildDate(2022, 11, 18), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Flamewar, Brash Veteran", 10, Rarity.MYTHIC, mage.cards.f.FlamewarBrashVeteran.class));
        cards.add(new SetCardInfo("Flamewar, Streetwise Operative", 10, Rarity.MYTHIC, mage.cards.f.FlamewarStreetwiseOperative.class));
        cards.add(new SetCardInfo("Goldbug, Humanity's Ally", 11, Rarity.MYTHIC, mage.cards.g.GoldbugHumanitysAlly.class));
        cards.add(new SetCardInfo("Goldbug, Scrappy Scout", 11, Rarity.MYTHIC, mage.cards.g.GoldbugScrappyScout.class));
        cards.add(new SetCardInfo("Ultra Magnus, Armored Carrier", 15, Rarity.MYTHIC, mage.cards.u.UltraMagnusArmoredCarrier.class));
        cards.add(new SetCardInfo("Ultra Magnus, Tactician", 15, Rarity.MYTHIC, mage.cards.u.UltraMagnusTactician.class));
    }
}
