package org.mage.test.cards.single.lci;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author Jmlundeen
 */
public class SaheelisLatticeTest extends CardTestPlayerBase {

    /*
    Saheeli's Lattice
    {1}{R}
    Artifact
    When Saheeli's Lattice enters the battlefield, you may discard a card. If you do, draw two cards.
    Craft with one or more Dinosaurs {4}{R}
    Mastercraft Raptor
    Artifact Creature - Dinosaur
    Mastercraft Raptor's power is equal to the total power of the exiled cards used to craft it.
    0/4
    */
    private static final String saheelisLattice = "Saheeli's Lattice";
    private static final String mastercraftRaptor = "Mastercraft Raptor";

    /*
    Balamb T-Rexaur
    {4}{G}{G}
    Creature - Dinosaur
    Trample
    When this creature enters, you gain 3 life.
    Forestcycling {2}
    6/6
    */
    private static final String balambTRexaur = "Balamb T-Rexaur";

    /*
    Zetalpa, Primal Dawn
    {6}{W}{W}
    Legendary Creature - Elder Dinosaur
    Flying, double strike, vigilance, trample, indestructible
    4/8
    */
    private static final String zetalpaPrimalDawn = "Zetalpa, Primal Dawn";

    @Test
    public void testSaheelisLattice() {
        addCard(Zone.BATTLEFIELD, playerA, saheelisLattice);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.GRAVEYARD, playerA, balambTRexaur);
        addCard(Zone.GRAVEYARD, playerA, zetalpaPrimalDawn);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Craft with one");
        addTarget(playerA, balambTRexaur + "^" + zetalpaPrimalDawn);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, mastercraftRaptor, 1);
        assertPowerToughness(playerA, mastercraftRaptor, 6 + 4, 4);
        assertExileCount(playerA, balambTRexaur, 1);
        assertExileCount(playerA, zetalpaPrimalDawn, 1);
    }
}