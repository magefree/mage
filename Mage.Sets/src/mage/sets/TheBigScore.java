package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class TheBigScore extends ExpansionSet {

    private static final TheBigScore instance = new TheBigScore();

    public static TheBigScore getInstance() {
        return instance;
    }

    private TheBigScore() {
        super("The Big Score", "BIG", ExpansionSet.buildDate(2024, 4, 19), SetType.SUPPLEMENTAL_STANDARD_LEGAL);
        this.blockName = "Outlaws of Thunder Junction";
        this.hasBasicLands = false;
        this.hasBoosters = false;

        cards.add(new SetCardInfo("Legion Foundry", 12, Rarity.MYTHIC, mage.cards.l.LegionFoundry.class));
        cards.add(new SetCardInfo("Loot, the Key to Everything", 21, Rarity.MYTHIC, mage.cards.l.LootTheKeyToEverything.class));
        cards.add(new SetCardInfo("Lotus Ring", 24, Rarity.MYTHIC, mage.cards.l.LotusRing.class));
        cards.add(new SetCardInfo("Oltec Matterweaver", 3, Rarity.MYTHIC, mage.cards.o.OltecMatterweaver.class));
        cards.add(new SetCardInfo("Substitute Synthesizer", 6, Rarity.MYTHIC, mage.cards.s.SubstituteSynthesizer.class));
        cards.add(new SetCardInfo("Torpor Orb", 27, Rarity.MYTHIC, mage.cards.t.TorporOrb.class));
        cards.add(new SetCardInfo("Vaultborn Tyrant", 20, Rarity.MYTHIC, mage.cards.v.VaultbornTyrant.class));
    }
}
