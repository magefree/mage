package org.mage.test.cards.single.fic;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class HildibrandMandervilleTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.h.HildibrandManderville Hildibrand Manderville} {1}{W}
     * Legendary Creature — Human Detective
     * Creature tokens you control get +1/+1.
     * When Hildibrand Manderville dies, you may cast it from your graveyard as an Adventure until the end of your next turn.
     * <p>
     * Gentleman's Rise {2}{B}
     * Instant — Adventure
     * Create a 2/2 black Zombie creature token. (Then exile this card. You may cast the creature later from exile.)
     */
    private static final String hildibrand = "Hildibrand Manderville";
    private static final String rise = "Gentleman's Rise";

    /**
     * {1}{B} Instant Destroy target non-black creature.
     */
    private static final String blade = "Doom Blade";

    @Test
    public void test_CastFromGraveyard() {
        addCard(Zone.BATTLEFIELD, playerA, hildibrand, 1);
        addCard(Zone.HAND, playerA, blade, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Scrubland", 5);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, blade, hildibrand);
        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerA, rise);

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, hildibrand);

        setStopAt(3, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, hildibrand, 1);
        assertTokenCount(playerA, "Zombie Token", 1);
    }

    @Test
    public void test_CheckOnlyAsAdventureAndTimingForRise() {
        addCard(Zone.BATTLEFIELD, playerA, hildibrand, 1);
        addCard(Zone.HAND, playerA, blade, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Scrubland", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, blade, hildibrand);

        checkPlayableAbility("1: rise: right after", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast " + rise, true);
        checkPlayableAbility("1: hildibrand: right after", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast " + hildibrand, false);

        checkPlayableAbility("2: rise: on opp turn", 2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast " + rise, true);
        checkPlayableAbility("2: hildibrand: on opp turn", 2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast " + hildibrand, false);

        checkPlayableAbility("3: rise: on your next turn", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + rise, true);
        checkPlayableAbility("3: hildibrand: on your next turn", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + hildibrand, false);

        checkPlayableAbility("4: rise: after duration", 4, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + rise, false);
        checkPlayableAbility("4: hildibrand: after duration", 4, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + hildibrand, false);

        setStopAt(4, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerA, hildibrand, 1);
        assertTokenCount(playerA, "Zombie Token", 0);
    }
}
