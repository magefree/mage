package org.mage.test.cards.single.clb;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class BabaLysagaNightWitchTest extends CardTestPlayerBase {

    /**
     * Baba Lysaga, Night Witch {1}{B}{G}
     * Legendary Creature — Human Warlock
     * <p>
     * {T}, Sacrifice up to three permanents: If there were three or more card types among the sacrificed permanents, each opponent loses 3 life, you gain 3 life, and you draw three cards.
     */

    private final static String baba = "Baba Lysaga, Night Witch";

    @Test
    public void SacrificeAnimatedMishra() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, baba);
        addCard(Zone.BATTLEFIELD, playerA, "Mishra's Factory"); // animates into a Land Artifact Creature

        activateAbility(1, PhaseStep.UPKEEP, playerA, "{1}: {this} becomes a 2/2 Assembly-Worker artifact creature until end of turn. It's still a land.");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Sacrifice up to three permanents: If there ");
        setChoice(playerA, "Mishra's Factory");

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 + 3);
        assertLife(playerB, 20 - 3);
        assertHandCount(playerA, 3);
        assertGraveyardCount(playerA, "Mishra's Factory", 1);
    }

    @Test
    public void SacrificeNonAnimatedMishra() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, baba);
        addCard(Zone.BATTLEFIELD, playerA, "Mishra's Factory"); // just a Land if not animated

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Sacrifice up to three permanents: If there ");
        setChoice(playerA, "Mishra's Factory");

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertHandCount(playerA, 0);
        assertGraveyardCount(playerA, "Mishra's Factory", 1);
    }
}