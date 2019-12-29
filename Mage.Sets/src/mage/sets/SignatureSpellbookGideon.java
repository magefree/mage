package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author JayDi85
 */
public final class SignatureSpellbookGideon extends ExpansionSet {

    private static final SignatureSpellbookGideon instance = new SignatureSpellbookGideon();

    public static SignatureSpellbookGideon getInstance() {
        return instance;
    }

    private SignatureSpellbookGideon() {
        super("Signature Spellbook: Gideon", "SS2", ExpansionSet.buildDate(2019, 6, 28), SetType.SUPPLEMENTAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Blackblade Reforged", 8, Rarity.RARE, mage.cards.b.BlackbladeReforged.class));
        cards.add(new SetCardInfo("Gideon Jura", 1, Rarity.MYTHIC, mage.cards.g.GideonJura.class));
        cards.add(new SetCardInfo("Martyr's Bond", 2, Rarity.RARE, mage.cards.m.MartyrsBond.class));
        cards.add(new SetCardInfo("Path to Exile", 3, Rarity.RARE, mage.cards.p.PathToExile.class));
        cards.add(new SetCardInfo("Rest in Peace", 4, Rarity.RARE, mage.cards.r.RestInPeace.class));
        cards.add(new SetCardInfo("Shielded by Faith", 5, Rarity.RARE, mage.cards.s.ShieldedByFaith.class));
        cards.add(new SetCardInfo("True Conviction", 6, Rarity.RARE, mage.cards.t.TrueConviction.class));
        cards.add(new SetCardInfo("Worship", 7, Rarity.RARE, mage.cards.w.Worship.class));
    }
}
