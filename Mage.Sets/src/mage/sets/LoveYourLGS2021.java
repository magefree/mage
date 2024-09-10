package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/plg21
 */
public class LoveYourLGS2021 extends ExpansionSet {

    private static final LoveYourLGS2021 instance = new LoveYourLGS2021();

    public static LoveYourLGS2021 getInstance() {
        return instance;
    }

    private LoveYourLGS2021() {
        super("Love Your LGS 2021", "PLG21", ExpansionSet.buildDate(2021, 6, 22), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Aven Mindcensor", 1, Rarity.RARE, mage.cards.a.AvenMindcensor.class));
        cards.add(new SetCardInfo("Bolas's Citadel", 3, Rarity.RARE, mage.cards.b.BolassCitadel.class));
        cards.add(new SetCardInfo("Dig Through Time", 2, Rarity.RARE, mage.cards.d.DigThroughTime.class));
        cards.add(new SetCardInfo("Goblin Guide", 4, Rarity.RARE, mage.cards.g.GoblinGuide.class));
        cards.add(new SetCardInfo("Orb of Dragonkind", "J1", Rarity.RARE, mage.cards.o.OrbOfDragonkind.class,NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Orb of Dragonkind", "J2", Rarity.RARE, mage.cards.o.OrbOfDragonkind.class,NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Orb of Dragonkind", "J3", Rarity.RARE, mage.cards.o.OrbOfDragonkind.class,NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Path of Ancestry", "C3", Rarity.RARE, mage.cards.p.PathOfAncestry.class));
        cards.add(new SetCardInfo("Scavenging Ooze", 5, Rarity.RARE, mage.cards.s.ScavengingOoze.class));
        cards.add(new SetCardInfo("Xantcha, Sleeper Agent", "C1", Rarity.RARE, mage.cards.x.XantchaSleeperAgent.class));
        cards.add(new SetCardInfo("Yuriko, the Tiger's Shadow", "C2", Rarity.RARE, mage.cards.y.YurikoTheTigersShadow.class));     }
}
