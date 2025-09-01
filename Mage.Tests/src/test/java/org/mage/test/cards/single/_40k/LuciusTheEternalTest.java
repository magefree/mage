package org.mage.test.cards.single._40k;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class LuciusTheEternalTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.l.LuciusTheEternal Lucius the Eternal} {3}{B}{R}
     * Legendary Creature — Astartes Warrior
     * Haste
     * Armour of Shrieking Souls — When Lucius the Eternal dies, exile it and choose target creature an opponent controls. When that creature leaves the battlefield, return this card from exile to the battlefield under its owner’s control.
     * 5/3
     */
    private static final String lucius = "Lucius the Eternal";

    /**
     * Go for the Throat {1}{B} Instant
     * Destroy target nonartifact creature.
     */
    private static final String goForTheThroat = "Go for the Throat";
    /**
     * Unsummon {U} Instant
     * Return target creature to its owner’s hand.
     */
    private static final String unsummon = "Unsummon";
    /**
     * 2/2 bear
     */
    private static final String bear = "Grizzly Bears";

    @Test
    public void testLuciusDelayedOnDieTrigger() {
        addCard(Zone.BATTLEFIELD, playerA, lucius, 1);
        addCard(Zone.BATTLEFIELD, playerB, bear, 1);
        addCard(Zone.HAND, playerA, goForTheThroat, 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, goForTheThroat, lucius);
        addTarget(playerA, bear);// Lucius dies trigger
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        checkExileCount("Lucius in exile", 1, PhaseStep.PRECOMBAT_MAIN, playerA, lucius, 1);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, goForTheThroat, bear);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        checkPermanentCount("Lucius in play", 1, PhaseStep.PRECOMBAT_MAIN, playerA, lucius, 1);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerA, 2);
        assertPermanentCount(playerA, lucius, 1);
        assertGraveyardCount(playerB, 1);
    }

    @Test
    public void testLuciusDelayedOnReturnTrigger() {
        addCard(Zone.BATTLEFIELD, playerA, lucius, 1);
        addCard(Zone.BATTLEFIELD, playerB, bear, 1);
        addCard(Zone.HAND, playerA, goForTheThroat, 1);
        addCard(Zone.HAND, playerA, unsummon, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Underground Sea", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, goForTheThroat, lucius);
        addTarget(playerA, bear);// Lucius dies trigger
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        checkExileCount("Lucius in exile", 1, PhaseStep.PRECOMBAT_MAIN, playerA, lucius, 1);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, unsummon, bear);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        checkPermanentCount("Lucius in play", 1, PhaseStep.PRECOMBAT_MAIN, playerA, lucius, 1);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerA, 2);
        assertPermanentCount(playerA, lucius, 1);
        assertHandCount(playerB, bear, 1);
    }

    @Test
    public void testLuciusNeverReturn() {
        addCard(Zone.BATTLEFIELD, playerA, lucius, 1);
        addCard(Zone.BATTLEFIELD, playerB, bear, 1);
        addCard(Zone.HAND, playerA, goForTheThroat, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, goForTheThroat, lucius);
        addTarget(playerA, bear);// Lucius dies trigger
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerA, 1);
        assertExileCount(playerA, lucius, 1);
    }
}
