package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author JayDi85
 */
public final class RavnicaAllegianceGuildKits extends ExpansionSet {

    private static final RavnicaAllegianceGuildKits instance = new RavnicaAllegianceGuildKits();

    public static RavnicaAllegianceGuildKits getInstance() {
        return instance;
    }

    private RavnicaAllegianceGuildKits() {
        super("Ravnica Allegiance Guild Kits", "GK2", ExpansionSet.buildDate(2019, 2, 15), SetType.SUPPLEMENTAL);
        this.blockName = "Guild Kits";
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Forest", 106, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 135, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 152, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 27, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Isperia, Supreme Judge", 1, Rarity.MYTHIC, mage.cards.i.IsperiaSupremeJudge.class));
        cards.add(new SetCardInfo("Mountain", 105, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 79, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 26, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 50, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rakdos, Lord of Riots", 52, Rarity.MYTHIC, mage.cards.r.RakdosLordOfRiots.class));
        cards.add(new SetCardInfo("Ruric Thar, the Unbowed", 80, Rarity.RARE, mage.cards.r.RuricTharTheUnbowed.class));
        cards.add(new SetCardInfo("Swamp", 51, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 78, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Teysa, Orzhov Scion", 28, Rarity.RARE, mage.cards.t.TeysaOrzhovScion.class));
        cards.add(new SetCardInfo("Zegana, Utopian Speaker", 107, Rarity.RARE, mage.cards.z.ZeganaUtopianSpeaker.class));
    }
}
