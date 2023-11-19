package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class Fallout extends ExpansionSet {

    private static final Fallout instance = new Fallout();

    public static Fallout getInstance() {
        return instance;
    }

    private Fallout() {
        super("Fallout", "PIP", ExpansionSet.buildDate(2024, 3, 8), SetType.SUPPLEMENTAL);

        cards.add(new SetCardInfo("Alpha Deathclaw", 91, Rarity.RARE, mage.cards.a.AlphaDeathclaw.class));
        cards.add(new SetCardInfo("Arcane Signet", 356, Rarity.UNCOMMON, mage.cards.a.ArcaneSignet.class));
        cards.add(new SetCardInfo("Command Tower", 259, Rarity.COMMON, mage.cards.c.CommandTower.class));
        cards.add(new SetCardInfo("Crucible of Worlds", 357, Rarity.MYTHIC, mage.cards.c.CrucibleOfWorlds.class));
        cards.add(new SetCardInfo("Dr. Madison Li", 3, Rarity.MYTHIC, mage.cards.d.DrMadisonLi.class));
        cards.add(new SetCardInfo("Forest", 325, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gary Clone", 16, Rarity.UNCOMMON, mage.cards.g.GaryClone.class));
        cards.add(new SetCardInfo("Intelligence Bobblehead", 134, Rarity.UNCOMMON, mage.cards.i.IntelligenceBobblehead.class));
        cards.add(new SetCardInfo("Island", 319, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 323, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nuka-Cola Vending Machine", 137, Rarity.UNCOMMON, mage.cards.n.NukaColaVendingMachine.class));
        cards.add(new SetCardInfo("Plains", 317, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Radstorm", 37, Rarity.RARE, mage.cards.r.Radstorm.class));
        cards.add(new SetCardInfo("Sol Ring", 359, Rarity.MYTHIC, mage.cards.s.SolRing.class));
        cards.add(new SetCardInfo("Swamp", 321, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("V.A.T.S.", 50, Rarity.RARE, mage.cards.v.VATS.class));
        cards.add(new SetCardInfo("Vault 101: Birthday Party", 28, Rarity.RARE, mage.cards.v.Vault101BirthdayParty.class));
        cards.add(new SetCardInfo("Wasteland", 361, Rarity.RARE, mage.cards.w.Wasteland.class));
    }
}
