package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class Warhammer40000 extends ExpansionSet {

    private static final Warhammer40000 instance = new Warhammer40000();

    public static Warhammer40000 getInstance() {
        return instance;
    }

    private Warhammer40000() {
        super("Warhammer 40,000", "40K", ExpansionSet.buildDate(2022, 4, 29), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;
        this.hasBoosters = false;

        cards.add(new SetCardInfo("Abaddon the Despoiler", 2, Rarity.MYTHIC, mage.cards.a.AbaddonTheDespoiler.class));
        cards.add(new SetCardInfo("Abundance", 210, Rarity.RARE, mage.cards.a.Abundance.class));
        cards.add(new SetCardInfo("Arcane Signet", 227, Rarity.COMMON, mage.cards.a.ArcaneSignet.class));
        cards.add(new SetCardInfo("Broodlord", 89, Rarity.RARE, mage.cards.b.Broodlord.class));
        cards.add(new SetCardInfo("Command Tower", 270, Rarity.COMMON, mage.cards.c.CommandTower.class));
        cards.add(new SetCardInfo("Deathleaper, Terror Weapon", 115, Rarity.RARE, mage.cards.d.DeathleaperTerrorWeapon.class));
        cards.add(new SetCardInfo("Fabricate", 181, Rarity.RARE, mage.cards.f.Fabricate.class));
        cards.add(new SetCardInfo("Hardened Scales", 215, Rarity.RARE, mage.cards.h.HardenedScales.class));
        cards.add(new SetCardInfo("Imotekh the Stormlord", 5, Rarity.MYTHIC, mage.cards.i.ImotekhTheStormlord.class));
        cards.add(new SetCardInfo("Inquisitor Greyfax", 3, Rarity.MYTHIC, mage.cards.i.InquisitorGreyfax.class));
        cards.add(new SetCardInfo("Noise Marine", 82, Rarity.UNCOMMON, mage.cards.n.NoiseMarine.class));
        cards.add(new SetCardInfo("Sol Ring", 249, Rarity.UNCOMMON, mage.cards.s.SolRing.class));
        cards.add(new SetCardInfo("Szarekh, the Silent King", 1, Rarity.MYTHIC, mage.cards.s.SzarekhTheSilentKing.class));
    }
}
