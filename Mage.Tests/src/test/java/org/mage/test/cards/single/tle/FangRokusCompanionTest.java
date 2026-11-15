package org.mage.test.cards.single.tle;

import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.f.FangRokusCompanion Fang, Roku's Companion} {3}{R}{R}
 * Legendary Creature — Dragon 4/4
 * Flying
 * Whenever Fang attacks, another target legendary creature you control gets +X/+0 until end of turn, where X is Fang's power.
 * When Fang dies, if he wasn't a Spirit, return this card to the battlefield under your control.
 * He's a Spirit in addition to his other types.
 */
public class FangRokusCompanionTest extends CardTestPlayerBase {

    private static final String fang = "Fang, Roku's Companion";
    private static final String murder = "Murder";

    @Test
    public void test_ReturnsOnceAsUntappedSpirit() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, fang);
        addCard(Zone.HAND, playerA, murder, 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, murder, fang);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentTapped("returned untapped", 1, PhaseStep.PRECOMBAT_MAIN, playerA, fang, false, 1);
        checkSubType("returned as Spirit", 1, PhaseStep.PRECOMBAT_MAIN, playerA, fang, SubType.SPIRIT, true);
        checkSubType("keeps its types", 1, PhaseStep.PRECOMBAT_MAIN, playerA, fang, SubType.DRAGON, true);

        // now a Spirit, so the second death sticks
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, murder, fang);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, fang, 0);
        assertGraveyardCount(playerA, fang, 1);
    }

    @Test
    public void test_NoReturnIfExiledInResponse() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, fang);
        addCard(Zone.HAND, playerA, murder);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Tormod's Crypt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, murder, fang);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA, true); // Murder only; the dies trigger stays on the stack
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Sacrifice", playerA);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, fang, 0);
        assertExileCount(playerA, fang, 1);
    }
}
