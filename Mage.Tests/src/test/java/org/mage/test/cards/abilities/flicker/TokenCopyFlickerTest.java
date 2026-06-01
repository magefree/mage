package org.mage.test.cards.abilities.flicker;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author PurpleCrowbar
 */
public class TokenCopyFlickerTest extends CardTestPlayerBase {

    // {1} Instant: Exile target creature you control, then return that card to the battlefield under your control.
    private static final String flicker = "Cloudshift";
    // {1}{W/U} Instant: Exile target creature. Return that card to the battlefield under its owner's control at the beginning of the next end step.
    private static final String slowFlicker = "Turn to Mist";

    /**
     * Tests that a token (which isn't a copy of any other permanent) is correctly removed from the game when flickered.
     * This test passing is proof of tokens not returning to the battlefield when flickered.
     */
    @Test
    public void testNonCopyTokenFlicker() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Champion of the Perished");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.HAND, playerA, "Reap the Seagraf"); // {2}{B} Sorcery: Create a 2/2 black Zombie creature token.
        addCard(Zone.HAND, playerA, flicker);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reap the Seagraf");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, flicker, "Zombie Token");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Zombie Token", 0);
        assertCounterCount("Champion of the Perished", CounterType.P1P1, 1); // only one +1/+1 counter added
    }

    /**
     * Creates a token copy of a permanent and flickers the copy. Expected behavior: token is removed from game, nothing else happens.
     * Current, bugged behavior: token is removed from game, then the original, copied creature is returned to the battlefield from wherever (GY in this case)
     */
    @Test
    public void testCopyTokenFlicker() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Gilded Goose"); // Creature: When ~ enters, create a Food token.
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);

        addCard(Zone.HAND, playerA, "Cackling Counterpart"); // {1}{U}{U} Instant: Create a token that's a copy of target creature you control.
        addCard(Zone.HAND, playerA, "Incandescent Aria"); // {R}{G}{W} Sorcery: Deal 3 damage to each nontoken creature.
        addCard(Zone.HAND, playerA, flicker);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cackling Counterpart", "Gilded Goose", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Incandescent Aria", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, flicker, "Gilded Goose");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Gilded Goose", 0); // 0; original in GY, token removed from game
        assertGraveyardCount(playerA, "Gilded Goose", 1); // original Goose in GY
        assertPermanentCount(playerA, "Food Token", 1); // 1 from the Cackling Counterpart. 2 if a flickered Goose erroneously returned to the battlefield
    }

    /**
     * As above, but uses a slow flicker (exiles, then returns to battlefield at end of turn).
     */
    @Test
    public void testCopyTokenSlowFlicker() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Gilded Goose"); // Creature: When ~ enters, create a Food token.
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);

        addCard(Zone.HAND, playerA, "Cackling Counterpart"); // {1}{U}{U} Instant: Create a token that's a copy of target creature you control.
        addCard(Zone.HAND, playerA, "Incandescent Aria"); // {R}{G}{W} Sorcery: Deal 3 damage to each nontoken creature.
        addCard(Zone.HAND, playerA, slowFlicker);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cackling Counterpart", "Gilded Goose", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Incandescent Aria", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, slowFlicker, "Gilded Goose");

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Gilded Goose", 0); // 0; original in GY, token removed from game
        assertGraveyardCount(playerA, "Gilded Goose", 1); // original Goose in GY
        assertPermanentCount(playerA, "Food Token", 1); // 1 from the Cackling Counterpart. 2 if a flickered Goose erroneously returned to the battlefield
    }
}
