
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author fireshoes
 */
public final class FromTheVaultDragons extends ExpansionSet {

    private static final FromTheVaultDragons instance = new FromTheVaultDragons();

    public static FromTheVaultDragons getInstance() {
        return instance;
    }

    private FromTheVaultDragons() {
        super("From the Vault: Dragons", "DRB", ExpansionSet.buildDate(2008, 8, 29), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;
        cards.add(new SetCardInfo("Bladewing the Risen", 1, Rarity.RARE, mage.cards.b.BladewingTheRisen.class));
        cards.add(new SetCardInfo("Bogardan Hellkite", 2, Rarity.RARE, mage.cards.b.BogardanHellkite.class));
        cards.add(new SetCardInfo("Draco", 3, Rarity.RARE, mage.cards.d.Draco.class));
        cards.add(new SetCardInfo("Dragonstorm", 5, Rarity.RARE, mage.cards.d.Dragonstorm.class));
        cards.add(new SetCardInfo("Dragon Whelp", 4, Rarity.RARE, mage.cards.d.DragonWhelp.class));
        cards.add(new SetCardInfo("Ebon Dragon", 6, Rarity.RARE, mage.cards.e.EbonDragon.class));
        cards.add(new SetCardInfo("Form of the Dragon", 7, Rarity.RARE, mage.cards.f.FormOfTheDragon.class));
        cards.add(new SetCardInfo("Hellkite Overlord", 8, Rarity.RARE, mage.cards.h.HellkiteOverlord.class));
        cards.add(new SetCardInfo("Kokusho, the Evening Star", 9, Rarity.RARE, mage.cards.k.KokushoTheEveningStar.class));
        cards.add(new SetCardInfo("Nicol Bolas", 10, Rarity.RARE, mage.cards.n.NicolBolas.class));
        cards.add(new SetCardInfo("Niv-Mizzet, the Firemind", 11, Rarity.RARE, mage.cards.n.NivMizzetTheFiremind.class));
        cards.add(new SetCardInfo("Rith, the Awakener", 12, Rarity.RARE, mage.cards.r.RithTheAwakener.class));
        cards.add(new SetCardInfo("Shivan Dragon", 13, Rarity.RARE, mage.cards.s.ShivanDragon.class));
        cards.add(new SetCardInfo("Thunder Dragon", 14, Rarity.RARE, mage.cards.t.ThunderDragon.class));
        cards.add(new SetCardInfo("Two-Headed Dragon", 15, Rarity.RARE, mage.cards.t.TwoHeadedDragon.class));
    }
}
