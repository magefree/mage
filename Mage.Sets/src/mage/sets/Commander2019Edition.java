package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class Commander2019Edition extends ExpansionSet {

    private static final Commander2019Edition instance = new Commander2019Edition();

    public static Commander2019Edition getInstance() {
        return instance;
    }

    private Commander2019Edition() {
        super("Commander 2019 Edition", "C19", ExpansionSet.buildDate(2019, 8, 23), SetType.SUPPLEMENTAL);
        this.blockName = "Command Zone";
        this.hasBasicLands = false; // temporary fix for tests

        cards.add(new SetCardInfo("Anje Falkenrath", 37, Rarity.MYTHIC, mage.cards.a.AnjeFalkenrath.class));
        cards.add(new SetCardInfo("Anje's Ravager", 22, Rarity.RARE, mage.cards.a.AnjesRavager.class));
        cards.add(new SetCardInfo("Gerrard, Weatherlight Hero", 41, Rarity.RARE, mage.cards.g.GerrardWeatherlightHero.class));
        cards.add(new SetCardInfo("Ghired's Belligerence", 25, Rarity.RARE, mage.cards.g.GhiredsBelligerence.class));
        cards.add(new SetCardInfo("Ghired, Conclave Exile", 42, Rarity.MYTHIC, mage.cards.g.GhiredConclaveExile.class));
        cards.add(new SetCardInfo("Ixidron", 87, Rarity.RARE, mage.cards.i.Ixidron.class));
        cards.add(new SetCardInfo("Kadena's Silencer", 8, Rarity.RARE, mage.cards.k.KadenasSilencer.class));
        cards.add(new SetCardInfo("Kadena, Slinking Sorcerer", 45, Rarity.MYTHIC, mage.cards.k.KadenaSlinkingSorcerer.class));
        cards.add(new SetCardInfo("Leadership Vacuum", 9, Rarity.UNCOMMON, mage.cards.l.LeadershipVacuum.class));
        cards.add(new SetCardInfo("Sanctum of Eternity", 59, Rarity.RARE, mage.cards.s.SanctumOfEternity.class));
        cards.add(new SetCardInfo("Scaretiller", 57, Rarity.COMMON, mage.cards.s.Scaretiller.class));
        cards.add(new SetCardInfo("Seedborn Muse", 179, Rarity.RARE, mage.cards.s.SeedbornMuse.class));
        cards.add(new SetCardInfo("Sevinne's Reclamation", 5, Rarity.RARE, mage.cards.s.SevinnesReclamation.class));
        cards.add(new SetCardInfo("Sevinne, the Chronoclasm", 49, Rarity.MYTHIC, mage.cards.s.SevinneTheChronoclasm.class));
        cards.add(new SetCardInfo("Strionic Resonator", 224, Rarity.RARE, mage.cards.s.StrionicResonator.class));
        cards.add(new SetCardInfo("Thespian's Stage", 282, Rarity.RARE, mage.cards.t.ThespiansStage.class));
        cards.add(new SetCardInfo("Vesuvan Shapeshifter", 101, Rarity.RARE, mage.cards.v.VesuvanShapeshifter.class));
    }
}
