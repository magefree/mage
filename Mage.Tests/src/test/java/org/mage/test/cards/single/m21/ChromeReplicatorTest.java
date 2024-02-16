package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ChromeReplicatorTest extends CardTestPlayerBase {

    @Test
    public void controlTwoSameNonTokens(){
        // When Chrome Replicator enters the battlefield,
        // if you control two or more nonland, nontoken permanents with the same name as one another,
        // create a 4/4 colorless Construct artifact creature token.
        addCard(Zone.HAND, playerA, "Chrome Replicator");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chrome Replicator");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, "Construct Token", 1);
    }

    @Test
    public void controlTwoSameLands(){
        // When Chrome Replicator enters the battlefield,
        // if you control two or more nonland, nontoken permanents with the same name as one another,
        // create a 4/4 colorless Construct artifact creature token.
        addCard(Zone.HAND, playerA, "Chrome Replicator");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chrome Replicator");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, "Construct Token", 0);
    }

    @Test
    public void controlTwoSameTokens(){
        // When Chrome Replicator enters the battlefield,
        // if you control two or more nonland, nontoken permanents with the same name as one another,
        // create a 4/4 colorless Construct artifact creature token.
        addCard(Zone.HAND, playerA, "Chrome Replicator");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        // create 2 soldier tokens
        addCard(Zone.HAND, playerA, "Raise the Alarm");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Raise the Alarm", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chrome Replicator");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, "Construct Token", 0);
    }
}
