package org.mage.test.cards.single.eoe;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class SeedshipImpactTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.s.SeedshipImpact Seedship Impact} {1}{G}
     * Instant
     * Destroy target artifact or enchantment. If its mana value was 2 or less, create a Lander token.
     * (It's an artifact with "{2}, {T}, Sacrifice this token: Search your library for a basic land card,
     * put it onto the battlefield tapped, then shuffle.")
     */
    private static final String impact = "Seedship Impact";

    @Test
    public void test_NoLander() {
        addCard(Zone.HAND, playerA, impact);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Crucible of Worlds"); // {3}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, impact, "Crucible of Worlds");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerB, "Crucible of Worlds", 1);
        assertPermanentCount(playerA, "Lander Token", 0);
    }

    @Test
    public void test_Lander() {
        addCard(Zone.HAND, playerA, impact);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Anthem of Champions"); // {W}{G}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, impact, "Anthem of Champions");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerB, "Anthem of Champions", 1);
        assertPermanentCount(playerA, "Lander Token", 1);
    }
}
