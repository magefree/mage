package org.mage.test.cards.single.ltr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author jimga150
 */
public class FiresOfMountDoomTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.f.FiresOfMountDoom Fires of Mount Doom}
     * Legendary Enchantment
     * When Fires of Mount Doom enters the battlefield, it deals 2 damage to target creature an opponent controls.
     * Destroy all Equipment attached to that creature.
     * {2}{R}: Exile the top card of your library. You may play that card this turn.
     * When you play a card this way, Fires of Mount Doom deals 2 damage to each player.
     */
    private static final String fires = "Fires of Mount Doom";

    @Test
    public void test_castWithDamage() {
        // Test the damaging triggered ability

        addCard(Zone.BATTLEFIELD, playerA, fires, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        addCard(Zone.LIBRARY, playerA, "Squee, the Immortal", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}{R}");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Squee, the Immortal");
        setChoice(playerA, fires); // Choose Fires as the approving object

        skipInitShuffling();
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Squee, the Immortal", 1);
        assertLife(playerB, currentGame.getStartingLife() - 2);
    }

    @Test
    public void test_castNoDamage() {
        // Test that the damaging triggered ability doesnt trigger if something else allows the exiled card to be cast

        addCard(Zone.BATTLEFIELD, playerA, fires, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        addCard(Zone.LIBRARY, playerA, "Squee, the Immortal", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}{R}");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Squee, the Immortal");
        setChoice(playerA, "Squee"); // Choose Squee as the approving object (which casts it normally)

        skipInitShuffling();
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Squee, the Immortal", 1);
        assertLife(playerB, currentGame.getStartingLife());
    }
}
