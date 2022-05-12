package org.mage.test.cards.single.akh;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class AnnointedProcessionTest extends CardTestPlayerBase {

    public final String anointedProcession = "Anointed Procession";


    @Test
    public void createDoubleTokens() {

        addCard(Zone.BATTLEFIELD, playerA, anointedProcession, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Anointer Priest", 1);
        addCard(Zone.HAND, playerA, "Liliana's Mastery");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Liliana's Mastery");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Zombie Token", 4);
        assertLife(playerA, 24);
    }

    @Test
    public void createTwoDoubleTokens() {

        addCard(Zone.BATTLEFIELD, playerA, anointedProcession, 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Anointer Priest", 1);
        addCard(Zone.HAND, playerA, "Liliana's Mastery");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Liliana's Mastery");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Zombie Token", 8);
        assertLife(playerA, 28);
    }

    @Test
    public void notTriggerWhenNotOwned(){

        addCard(Zone.BATTLEFIELD, playerB, anointedProcession, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);
        addCard(Zone.BATTLEFIELD, playerB, "Anointer Priest", 1);
        addCard(Zone.HAND, playerA, "Liliana's Mastery");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Liliana's Mastery");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Zombie Token", 2);
        assertLife(playerA, 20);
    }

    @Test
    public void createDoubleTokenOnEmbalm() {
        addCard(Zone.GRAVEYARD, playerA, "Sacred Cat", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Anointed Procession", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Embalm");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Sacred Cat", 2);

    }

    @Test
    public void cycleStirTheSands(){
        addCard(Zone.BATTLEFIELD, playerA, anointedProcession, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);
        addCard(Zone.HAND, playerA, "Stir the Sands");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Stir the Sands");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Zombie Token", 6);
    }
}
