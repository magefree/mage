package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Idol of Endurance
 * {2}{W}
 * Artifact
 * When Idol of Endurance enters the battlefield, exile all creature cards with mana value 3 or less from your graveyard until Idol of Endurance leaves the battlefield.
 * {1}{W}, {T}: Until end of turn, you may cast a creature spell from among cards exiled with Idol of Endurance without paying its mana cost.
 *
 * @author TheElk801
 */
public class IdolOfEnduranceTest extends CardTestPlayerBase {

    private static final String idol = "Idol of Endurance";
    private static final String dsnchnt = "Disenchant";
    private static final String key = "Voltaic Key";
    private static final String sqr = "Squire";
    private static final String glrskr = "Glory Seeker";
    private static final String pnhrmcn = "Panharmonicon";
    private static final String bnyrdwrm = "Boneyard Wurm";

    /**
     * Test that you can cast a spell for free from those exiled
     */
    @Test
    public void testIdolCast() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.HAND, playerA, idol);
        addCard(Zone.GRAVEYARD, playerA, sqr);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, idol);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        // Exile the Squire
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{W}");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        // Play for free
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, sqr);

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, sqr, 1);
    }

    /**
     * Test that you can only cast 1 spell per activation.
     */
    @Test
    public void testIdolCast2() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.HAND, playerA, idol);
        addCard(Zone.GRAVEYARD, playerA, sqr);
        addCard(Zone.GRAVEYARD, playerA, glrskr);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, idol);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{W}");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        // Cast the first spell
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, sqr);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        // Ensure that you can't cast the second spell
        checkPlayableAbility("Can't cast 2nd", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + glrskr, false);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, sqr, 1);
    }

    @Test
    public void testIdolLeaves() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);
        addCard(Zone.HAND, playerA, idol);
        addCard(Zone.HAND, playerA, dsnchnt);
        addCard(Zone.GRAVEYARD, playerA, sqr);
        addCard(Zone.GRAVEYARD, playerA, glrskr);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, idol);

        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{W}");

        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, sqr);

        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, dsnchnt, idol);

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, sqr, 1);
        assertPermanentCount(playerA, idol, 0);
        assertGraveyardCount(playerA, glrskr, 1);
    }

    @Test
    public void testIdolPanharmonicon() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);
        addCard(Zone.BATTLEFIELD, playerA, pnhrmcn);
        addCard(Zone.BATTLEFIELD, playerA, bnyrdwrm, 2);
        addCard(Zone.HAND, playerA, idol);
        addCard(Zone.HAND, playerA, dsnchnt);
        addCard(Zone.GRAVEYARD, playerA, sqr);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, idol);

        // Boneyard Wurm will die between triggers
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{W}");

        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bnyrdwrm);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, dsnchnt, idol);

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, bnyrdwrm, 1);
        assertPermanentCount(playerA, idol, 0);
        assertGraveyardCount(playerA, sqr, 1);
        assertGraveyardCount(playerA, bnyrdwrm, 1);
    }

    @Test
    public void testIdolTwice() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 8);
        addCard(Zone.BATTLEFIELD, playerA, key);
        addCard(Zone.HAND, playerA, idol);
        addCard(Zone.GRAVEYARD, playerA, sqr);
        addCard(Zone.GRAVEYARD, playerA, glrskr);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, idol);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{W}");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1},", idol);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{W}");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, sqr);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, glrskr);

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, sqr, 1);
        assertPermanentCount(playerA, glrskr, 1);
    }

    @Test
    public void testIdolTwice2() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 8);
        addCard(Zone.BATTLEFIELD, playerA, key);
        addCard(Zone.HAND, playerA, idol);
        addCard(Zone.GRAVEYARD, playerA, sqr);
        addCard(Zone.GRAVEYARD, playerA, glrskr);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, idol);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{W}");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, sqr);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1},", idol);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{W}");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, glrskr);

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, sqr, 1);
        assertPermanentCount(playerA, glrskr, 1);
    }
}
