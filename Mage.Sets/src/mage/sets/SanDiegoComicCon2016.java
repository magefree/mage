package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/ps16
 */
public class SanDiegoComicCon2016 extends ExpansionSet {

    private static final SanDiegoComicCon2016 instance = new SanDiegoComicCon2016();

    public static SanDiegoComicCon2016 getInstance() {
        return instance;
    }

    private SanDiegoComicCon2016() {
        super("San Diego Comic-Con 2016", "PS16", ExpansionSet.buildDate(2016, 10, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Chandra, Flamecaller", 104, Rarity.MYTHIC, mage.cards.c.ChandraFlamecaller.class));
        cards.add(new SetCardInfo("Gideon, Ally of Zendikar", 29, Rarity.MYTHIC, mage.cards.g.GideonAllyOfZendikar.class));
        cards.add(new SetCardInfo("Jace, Unraveler of Secrets", 69, Rarity.MYTHIC, mage.cards.j.JaceUnravelerOfSecrets.class));
        cards.add(new SetCardInfo("Liliana, the Last Hope", 93, Rarity.MYTHIC, mage.cards.l.LilianaTheLastHope.class));
        cards.add(new SetCardInfo("Nissa, Voice of Zendikar", 138, Rarity.MYTHIC, mage.cards.n.NissaVoiceOfZendikar.class));
     }
}
