
package org.mage.test.cards.copy;

import mage.abilities.keyword.HasteAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class KikiJikiMirrorBreakerTest extends CardTestPlayerBase {

    @Test
    public void testSimpleCopy() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Kiki-Jiki, Mirror Breaker", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Voice of Resurgence", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Create a token that's a copy of target nonlegendary creature you control, except it has haste. Sacrifice it at the beginning of the next end step.");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Voice of Resurgence", 2);
    }

    @Test
    public void testSimpleCopySacrificeAtEnd() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Kiki-Jiki, Mirror Breaker", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Voice of Resurgence", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Create a token that's a copy of target nonlegendary creature you control, except it has haste. Sacrifice it at the beginning of the next end step.");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Elemental Token", 1); // because the copy was sacrificed
        assertPermanentCount(playerA, "Voice of Resurgence", 1);
    }

    @Test
    public void testCopyAndCopiedTokenDies() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Kiki-Jiki, Mirror Breaker", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Voice of Resurgence", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 3);
        // Flamebreak deals 3 damage to each creature without flying and each player. Creatures dealt damage this way can't be regenerated this turn.
        addCard(Zone.HAND, playerB, "Flamebreak");

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Create a token that's a copy of target nonlegendary creature you control, except it has haste. Sacrifice it at the beginning of the next end step.");

        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Flamebreak");
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 17);
        assertLife(playerB, 17);

        assertGraveyardCount(playerB, "Flamebreak", 1);

        assertPermanentCount(playerA, "Voice of Resurgence", 0);
        assertGraveyardCount(playerA, "Voice of Resurgence", 1);

        assertPermanentCount(playerA, "Elemental Token", 2);

    }

    /**
     * Kiki-Jiki, Mirror Breaker creates a copy of Humble Defector, activate
     * Humble defector, token gets sacrificed while under opponents control.
     */
    @Test
    public void testTokenNotSacrificedIfNotControlled() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        // Tap target creature you don't control.
        // Overload {3}{U}
        addCard(Zone.HAND, playerA, "Blustersquall", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Kiki-Jiki, Mirror Breaker", 1);
        // {T}: Draw two cards. Target opponent gains control of Humble Defector. Activate this ability only during your turn.
        addCard(Zone.BATTLEFIELD, playerB, "Humble Defector", 1);

        castSpell(2, PhaseStep.UPKEEP, playerA, "Blustersquall", "Humble Defector"); // Tap nontoken Defector so only the Token can be used later

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{T}: Create a token that's a copy of target nonlegendary creature you control, except it has haste. Sacrifice it at the beginning of the next end step.");

        activateAbility(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "{T}: Draw two cards. Target opponent gains control");

        setStopAt(3, PhaseStep.UPKEEP);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertHandCount(playerB, 3); // normal 1 draw of turn two + 2 from Defector

        assertGraveyardCount(playerA, "Blustersquall", 1);
        assertPermanentCount(playerB, "Humble Defector", 1);
        assertPermanentCount(playerA, "Humble Defector", 1);

    }

    /**
     *
     * Kiki-Jiki, Mirror Breaker tokens are not entering with haste...
     *
     * I just tried to reproduce this but I was not able--
     *
     * In the game in question I used a card (Body Double) to target a card in a
     * graveyard (don't remember the name) and copy it
     *
     * I then used Kiki to copy Body Double that copied a graveyard card of my
     * opponent---that copy did not have haste...
     */
    @Test
    public void testCopyBodyDouble() {
        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerB, "Kiki-Jiki, Mirror Breaker", 1);
        // You may have Body Double enter the battlefield as a copy of any creature card in a graveyard.
        addCard(Zone.HAND, playerB, "Body Double", 1); // {4}{U}

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Body Double");
        setChoice(playerB, "Silvercoat Lion");

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{T}: Create a token that's a copy of target nonlegendary creature you control, except it has haste. Sacrifice it at the beginning of the next end step.");

        attack(2, playerB, "Silvercoat Lion");
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Silvercoat Lion", 1);
        assertPermanentCount(playerB, "Silvercoat Lion", 2); // one from Body Double and one from Kiki

        Permanent kikiCopy = null;
        for (Permanent permanent : currentGame.getState().getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, currentGame)) {
            if (permanent.getName().equals("Silvercoat Lion") && (permanent instanceof PermanentToken)) {
                kikiCopy = permanent;
                break;
            }
        }
        if (kikiCopy != null) {
            Assert.assertEquals("Has to have haste", kikiCopy.getAbilities(currentGame).containsClass(HasteAbility.class), true);
        } else {
            Assert.assertEquals("Silvercoat Lion copied by Kiki is missing", kikiCopy != null, true);
        }

        assertLife(playerA, 18);
        assertLife(playerB, 20);

    }
}
