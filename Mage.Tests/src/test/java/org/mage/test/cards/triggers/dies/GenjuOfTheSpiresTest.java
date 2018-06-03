
package org.mage.test.cards.triggers.dies;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class GenjuOfTheSpiresTest extends CardTestPlayerBase {

    /**
     * Genju of the Spires isn't working properly: enchanted mountain got
     * destroyed by a Strip Mine, the ability triggered, I chose Yes, but it
     * didn't come back to my hand. There was nothing in play that could have
     * affected it. We rolled back to confirm and it happened a second time. I
     * haven't checked every one of them, but I think there's a chance that the
     * other 5 Genjus could also have the same problem?
     */
    @Test
    public void testGenjuReturnsToHand() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        // Enchant Mountain
        // {2}: Enchanted Mountain becomes a 6/1 red Spirit creature until end of turn. It's still a land.
        // When enchanted Mountain is put into a graveyard, you may return Genju of the Spires from your graveyard to your hand.
        addCard(Zone.HAND, playerA, "Genju of the Spires", 1); // {R}

        // {T}: Add {C}.
        // {T}, Sacrifice Strip Mine: Destroy target land.
        addCard(Zone.BATTLEFIELD, playerB, "Strip Mine", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Genju of the Spires", "Mountain");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{T}, Sacrifice", "Mountain", "Genju of the Spires", StackClause.WHILE_NOT_ON_STACK);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerB, "Strip Mine", 1);

        assertGraveyardCount(playerA, "Genju of the Spires", 0);
        assertPermanentCount(playerA, "Genju of the Spires", 0);
        assertHandCount(playerA, "Genju of the Spires", 1);
    }

}
