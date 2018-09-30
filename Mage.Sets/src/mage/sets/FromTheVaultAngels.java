
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author fireshoes
 */
public final class FromTheVaultAngels extends ExpansionSet {

    private static final FromTheVaultAngels instance = new FromTheVaultAngels();

    public static FromTheVaultAngels getInstance() {
        return instance;
    }

    private FromTheVaultAngels() {
        super("From the Vault: Angels", "V15", ExpansionSet.buildDate(2015, 8, 21), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Akroma, Angel of Fury", 1, Rarity.MYTHIC, mage.cards.a.AkromaAngelOfFury.class));
        cards.add(new SetCardInfo("Akroma, Angel of Wrath", 2, Rarity.MYTHIC, mage.cards.a.AkromaAngelOfWrath.class));
        cards.add(new SetCardInfo("Archangel of Strife", 3, Rarity.MYTHIC, mage.cards.a.ArchangelOfStrife.class));
        cards.add(new SetCardInfo("Aurelia, the Warleader", 4, Rarity.MYTHIC, mage.cards.a.AureliaTheWarleader.class));
        cards.add(new SetCardInfo("Avacyn, Angel of Hope", 5, Rarity.MYTHIC, mage.cards.a.AvacynAngelOfHope.class));
        cards.add(new SetCardInfo("Baneslayer Angel", 6, Rarity.MYTHIC, mage.cards.b.BaneslayerAngel.class));
        cards.add(new SetCardInfo("Entreat the Angels", 7, Rarity.MYTHIC, mage.cards.e.EntreatTheAngels.class));
        cards.add(new SetCardInfo("Exalted Angel", 8, Rarity.MYTHIC, mage.cards.e.ExaltedAngel.class));
        cards.add(new SetCardInfo("Iona, Shield of Emeria", 9, Rarity.MYTHIC, mage.cards.i.IonaShieldOfEmeria.class));
        cards.add(new SetCardInfo("Iridescent Angel", 10, Rarity.MYTHIC, mage.cards.i.IridescentAngel.class));
        cards.add(new SetCardInfo("Jenara, Asura of War", 11, Rarity.MYTHIC, mage.cards.j.JenaraAsuraOfWar.class));
        cards.add(new SetCardInfo("Lightning Angel", 12, Rarity.MYTHIC, mage.cards.l.LightningAngel.class));
        cards.add(new SetCardInfo("Platinum Angel", 13, Rarity.MYTHIC, mage.cards.p.PlatinumAngel.class));
        cards.add(new SetCardInfo("Serra Angel", 14, Rarity.MYTHIC, mage.cards.s.SerraAngel.class));
        cards.add(new SetCardInfo("Tariel, Reckoner of Souls", 15, Rarity.MYTHIC, mage.cards.t.TarielReckonerOfSouls.class));
    }
}
