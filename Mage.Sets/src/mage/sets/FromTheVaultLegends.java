
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author fireshoes
 */
public final class FromTheVaultLegends extends ExpansionSet {

    private static final FromTheVaultLegends instance = new FromTheVaultLegends();

    public static FromTheVaultLegends getInstance() {
        return instance;
    }

    private FromTheVaultLegends() {
        super("From the Vault: Legends", "V11", ExpansionSet.buildDate(2011, 8, 26), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Cao Cao, Lord of Wei", 1, Rarity.MYTHIC, mage.cards.c.CaoCaoLordOfWei.class));
        cards.add(new SetCardInfo("Captain Sisay", 2, Rarity.MYTHIC, mage.cards.c.CaptainSisay.class));
        cards.add(new SetCardInfo("Doran, the Siege Tower", 3, Rarity.MYTHIC, mage.cards.d.DoranTheSiegeTower.class));
        cards.add(new SetCardInfo("Kiki-Jiki, Mirror Breaker", 4, Rarity.MYTHIC, mage.cards.k.KikiJikiMirrorBreaker.class));
        cards.add(new SetCardInfo("Kresh the Bloodbraided", 5, Rarity.MYTHIC, mage.cards.k.KreshTheBloodbraided.class));
        cards.add(new SetCardInfo("Mikaeus, the Lunarch", 6, Rarity.MYTHIC, mage.cards.m.MikaeusTheLunarch.class));
        cards.add(new SetCardInfo("Omnath, Locus of Mana", 7, Rarity.MYTHIC, mage.cards.o.OmnathLocusOfMana.class));
        cards.add(new SetCardInfo("Oona, Queen of the Fae", 8, Rarity.MYTHIC, mage.cards.o.OonaQueenOfTheFae.class));
        cards.add(new SetCardInfo("Progenitus", 9, Rarity.MYTHIC, mage.cards.p.Progenitus.class));
        cards.add(new SetCardInfo("Rafiq of the Many", 10, Rarity.MYTHIC, mage.cards.r.RafiqOfTheMany.class));
        cards.add(new SetCardInfo("Sharuum the Hegemon", 11, Rarity.MYTHIC, mage.cards.s.SharuumTheHegemon.class));
        cards.add(new SetCardInfo("Sun Quan, Lord of Wu", 12, Rarity.MYTHIC, mage.cards.s.SunQuanLordOfWu.class));
        cards.add(new SetCardInfo("Teferi, Mage of Zhalfir", 13, Rarity.MYTHIC, mage.cards.t.TeferiMageOfZhalfir.class));
        cards.add(new SetCardInfo("Ulamog, the Infinite Gyre", 14, Rarity.MYTHIC, mage.cards.u.UlamogTheInfiniteGyre.class));
        cards.add(new SetCardInfo("Visara the Dreadful", 15, Rarity.MYTHIC, mage.cards.v.VisaraTheDreadful.class));
    }
}
