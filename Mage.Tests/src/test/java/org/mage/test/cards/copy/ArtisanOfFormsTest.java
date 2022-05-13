
package org.mage.test.cards.copy;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class ArtisanOfFormsTest extends CardTestPlayerBase {

    /**
     * Targeting a Artisan of Forms triggers it Heroic ability. So it can copy a
     * target creature. If Cackling Counterpart later resolves, it should copy
     * the creature that Artisan of Forms copies, not the Artisan itself.
     */
    @Test
    public void testCopyTriggeredByCracklingCounterpart() {
        // Heroic - Whenever you cast a spell that targets Artisan of Forms, you may have Artisan of Forms become a copy of target creature and gain this ability.
        addCard(Zone.HAND, playerA, "Artisan of Forms"); // {1}{U}
        // {1}{U}{U} Create a tokenonto the battlefield that's a copy of target creature you control.
        addCard(Zone.HAND, playerA, "Cackling Counterpart");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Artisan of Forms");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cackling Counterpart", "Artisan of Forms");
        addTarget(playerA, "Silvercoat Lion");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, "Cackling Counterpart", 1);
        assertPermanentCount(playerA, "Artisan of Forms", 0);
        assertPermanentCount(playerB, "Silvercoat Lion", 1);
        // 1 + 2 Silvercoat Lion at the end
        assertPermanentCount(playerA, "Silvercoat Lion", 2);

        for (Permanent permanent : currentGame.getBattlefield().getAllActivePermanents(playerA.getId())) {
            if (permanent.getName().equals("Silvercoat Lion")) {
                Assert.assertEquals("Creature has to have Cast + Heroic ability", 2, permanent.getAbilities().size());
            }
        }
    }

    /**
     * Targeting a Artisan of Forms triggers it Heroic ability. So it can copy a
     * target creature. If populate spell later resolves, it should copy the
     * creature that Artisan of Forms copies, not the Artisan itself.
     */
    @Test
    public void testCopyTriggeredByPopulate() {
        // Heroic - Whenever you cast a spell that targets Artisan of Forms, you may have
        // Artisan of Forms become a copy of target creature and gain this ability.
        addCard(Zone.BATTLEFIELD, playerA, "Artisan of Forms");
        // {1}{U}{U} Create a tokenonto the battlefield that's a copy of target creature you control.
        addCard(Zone.HAND, playerA, "Cackling Counterpart");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.HAND, playerA, "Eyes in the Skies");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cackling Counterpart", "Artisan of Forms");
        addTarget(playerA, "Silvercoat Lion");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Eyes in the Skies");
        addTarget(playerA, "Silvercoat Lion");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, "Cackling Counterpart", 1);
        assertPermanentCount(playerA, "Artisan of Forms", 0);
        assertPermanentCount(playerB, "Silvercoat Lion", 1);
        assertPermanentCount(playerA, "Bird Token", 1);
        // 3 Silvercoat Lion at the end
        assertPermanentCount(playerA, "Silvercoat Lion", 3);

        for (Permanent permanent : currentGame.getBattlefield().getAllActivePermanents(playerA.getId())) {
            if (permanent.getName().equals("Silvercoat Lion")) {
                Assert.assertEquals("Creature has to have Cast + Heroic ability", 2, permanent.getAbilities().size());
            }
        }
    }

}
