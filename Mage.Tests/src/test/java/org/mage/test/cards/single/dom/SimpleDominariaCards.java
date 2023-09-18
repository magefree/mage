package org.mage.test.cards.single.dom;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;


public class SimpleDominariaCards extends CardTestPlayerBase {

    @Test
    public void benalishMarshall(){
        addCard(Zone.BATTLEFIELD, playerA, "Benalish Marshal", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Wood Elves", 1);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, "Benalish Marshal", 3, 3);
        assertPowerToughness(playerA, "Grizzly Bears", 3, 3);
        assertPowerToughness(playerB, "Wood Elves", 1, 1);
    }

    @Test
    public void testCharge(){
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Wood Elves", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Charge", 1);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Charge");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Grizzly Bears", 3, 3);
        assertPowerToughness(playerB, "Wood Elves", 1, 1);
    }

    @Test
    public void testKnightOfGraceBlackSpell(){
        addCard(Zone.BATTLEFIELD, playerA, "Knight of Grace");
        addCard(Zone.HAND, playerB, "Terror");
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 3);

        // Knight of Grace has protection from Black so Terror should not be castable
        checkPlayableAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast Terror", false);
        execute();

        assertGraveyardCount(playerA, "Knight of Grace", 0);
        assertHandCount(playerB,      "Terror",          1);
    }

    @Test
    public void testKnightOfGraceRedSpell(){
        addCard(Zone.BATTLEFIELD, playerA, "Knight of Grace");
        addCard(Zone.HAND, playerB, "Geistflame");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Geistflame", "Knight of Grace");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Knight of Grace", 0);
        assertDamageReceived(playerA, "Knight of Grace", 1);
        assertPowerToughness(playerA, "Knight of Grace", 2, 2);
    }

    @Test
    public void testKnightOfGraceBlackAbility(){
        addCard(Zone.BATTLEFIELD, playerA, "Knight of Grace");
        addCard(Zone.BATTLEFIELD, playerB, "Avatar of Woe");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{T}");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);

        execute();

        assertGraveyardCount(playerA, "Knight of Grace", 0);
        assertGraveyardCount(playerB, "Avatar of Woe", 1); // Autokills itself since its only valid target
    }

    @Test
    public void testKnightOfGraceAnyPlayerControls(){
        addCard(Zone.BATTLEFIELD, playerA, "Knight of Grace");
        addCard(Zone.BATTLEFIELD, playerB, "Royal Assassin");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Knight of Grace", 3, 2);
    }

    @Test
    public void jhoiraCastHistoric(){
        addCard(Zone.BATTLEFIELD, playerA, "Jhoira, Weatherlight Captain");
        addCard(Zone.HAND, playerA, "Ornithopter");
        addCard(Zone.LIBRARY, playerA, "Forest", 10);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ornithopter");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertHandCount(playerA, 1);

    }

    @Test
    public void jhoiraCastNonHistoric(){
        addCard(Zone.BATTLEFIELD, playerA, "Jhoira, Weatherlight Captain");
        addCard(Zone.HAND, playerA, "Giant Growth");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Giant Growth", "Jhoira, Weatherlight Captain");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertHandCount(playerA, 0);

    }
}
