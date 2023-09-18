package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class AngelicAscensionTest extends CardTestPlayerBase {




    @Test
    public void exileCreatureOpponent(){
        // Exile target creature or planeswalker. Its controller creates a 4/4 white Angel creature token with flying.
        addCard(Zone.HAND, playerA, "Angelic Ascension");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Angelic Ascension", "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertExileCount(playerB, 1);
        assertPermanentCount(playerB,  "Angel Token", 1);
        assertPowerToughness(playerB, "Angel Token", 4, 4);
    }

    @Test
    public void exileOwnCreature(){
        addCard(Zone.HAND, playerA, "Angelic Ascension");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Angelic Ascension", "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertExileCount(playerA, 1);
        assertPermanentCount(playerA,  "Angel Token", 1);
        assertPowerToughness(playerA, "Angel Token", 4, 4);
    }
}
