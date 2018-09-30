
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author fireshoes
 */
public final class FromTheVaultExiled extends ExpansionSet {

    private static final FromTheVaultExiled instance = new FromTheVaultExiled();

    public static FromTheVaultExiled getInstance() {
        return instance;
    }

    private FromTheVaultExiled() {
        super("From the Vault: Exiled", "V09", ExpansionSet.buildDate(2009, 8, 28), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Balance", 1, Rarity.MYTHIC, mage.cards.b.Balance.class));
        cards.add(new SetCardInfo("Berserk", 2, Rarity.MYTHIC, mage.cards.b.Berserk.class));
        cards.add(new SetCardInfo("Channel", 3, Rarity.MYTHIC, mage.cards.c.Channel.class));
        cards.add(new SetCardInfo("Gifts Ungiven", 4, Rarity.MYTHIC, mage.cards.g.GiftsUngiven.class));
        cards.add(new SetCardInfo("Goblin Lackey", 5, Rarity.MYTHIC, mage.cards.g.GoblinLackey.class));
        cards.add(new SetCardInfo("Kird Ape", 6, Rarity.MYTHIC, mage.cards.k.KirdApe.class));
        cards.add(new SetCardInfo("Lotus Petal", 7, Rarity.MYTHIC, mage.cards.l.LotusPetal.class));
        cards.add(new SetCardInfo("Mystical Tutor", 8, Rarity.MYTHIC, mage.cards.m.MysticalTutor.class));
        cards.add(new SetCardInfo("Necropotence", 9, Rarity.MYTHIC, mage.cards.n.Necropotence.class));
        cards.add(new SetCardInfo("Sensei's Divining Top", 10, Rarity.MYTHIC, mage.cards.s.SenseisDiviningTop.class));
        cards.add(new SetCardInfo("Serendib Efreet", 11, Rarity.MYTHIC, mage.cards.s.SerendibEfreet.class));
        cards.add(new SetCardInfo("Skullclamp", 12, Rarity.MYTHIC, mage.cards.s.Skullclamp.class));
        cards.add(new SetCardInfo("Strip Mine", 13, Rarity.MYTHIC, mage.cards.s.StripMine.class));
        cards.add(new SetCardInfo("Tinker", 14, Rarity.MYTHIC, mage.cards.t.Tinker.class));
        cards.add(new SetCardInfo("Trinisphere", 15, Rarity.MYTHIC, mage.cards.t.Trinisphere.class));
    }
}
