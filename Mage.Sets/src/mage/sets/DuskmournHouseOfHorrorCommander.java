package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class DuskmournHouseOfHorrorCommander extends ExpansionSet {

    private static final DuskmournHouseOfHorrorCommander instance = new DuskmournHouseOfHorrorCommander();

    public static DuskmournHouseOfHorrorCommander getInstance() {
        return instance;
    }

    private DuskmournHouseOfHorrorCommander() {
        super("Duskmourn: House of Horror Commander", "DSC", ExpansionSet.buildDate(2024, 9, 27), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Arcane Signet", 92, Rarity.COMMON, mage.cards.a.ArcaneSignet.class));
        cards.add(new SetCardInfo("Archon of Cruelty", 371, Rarity.MYTHIC, mage.cards.a.ArchonOfCruelty.class));
        cards.add(new SetCardInfo("Ash Barrens", 260, Rarity.COMMON, mage.cards.a.AshBarrens.class));
        cards.add(new SetCardInfo("Beast Within", 80, Rarity.UNCOMMON, mage.cards.b.BeastWithin.class));
        cards.add(new SetCardInfo("Cackling Counterpart", 72, Rarity.RARE, mage.cards.c.CacklingCounterpart.class));
        cards.add(new SetCardInfo("Citanul Hierophants", 81, Rarity.RARE, mage.cards.c.CitanulHierophants.class));
        cards.add(new SetCardInfo("Command Tower", 96, Rarity.COMMON, mage.cards.c.CommandTower.class));
        cards.add(new SetCardInfo("Crypt Ghast", 368, Rarity.MYTHIC, mage.cards.c.CryptGhast.class));
        cards.add(new SetCardInfo("Damn", 369, Rarity.MYTHIC, mage.cards.d.Damn.class));
        cards.add(new SetCardInfo("Exhume", 370, Rarity.MYTHIC, mage.cards.e.Exhume.class));
        cards.add(new SetCardInfo("Goryo's Vengeance", 372, Rarity.MYTHIC, mage.cards.g.GoryosVengeance.class));
        cards.add(new SetCardInfo("Growth Spiral", 88, Rarity.COMMON, mage.cards.g.GrowthSpiral.class));
        cards.add(new SetCardInfo("Living Death", 373, Rarity.MYTHIC, mage.cards.l.LivingDeath.class));
        cards.add(new SetCardInfo("Sol Ring", 94, Rarity.COMMON, mage.cards.s.SolRing.class));
    }
}
