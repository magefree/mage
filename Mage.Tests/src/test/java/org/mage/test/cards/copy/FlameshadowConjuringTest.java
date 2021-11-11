
package org.mage.test.cards.copy;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class FlameshadowConjuringTest extends CardTestPlayerBase {

    /**
     * My opponent ran into an issue with Priest of the Blood Rite being copied
     * with Flameshadow Conjuring. Their copy was made and removed correctly at
     * the end of the turn, but the "lose two life a turn" trigger still
     * happened twice.
     *
     * TODO: Seems like there are too much triggered abilities in
     * TriggeredAbilities as the TriggeredAbilities get removed from
     * PlayerImpl.removeFromBattlefield()
     */
    @Test
    public void testCopyAndItsEffectsRemoved() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        // Whenever a nontoken creature enters the battlefield under your control, you may pay {R}. If you do, put a token onto the battlefield that's a copy of that creature.
        // That token gains haste. Exile it at the beginning of the next end step.
        addCard(Zone.BATTLEFIELD, playerA, "Flameshadow Conjuring", 1);

        // When Priest of the Blood Rite enters the battlefield, put a 5/5 black Demon creature token with flying onto the battlefield.
        // At the beginning of your upkeep, you lose 2 life.
        addCard(Zone.HAND, playerA, "Priest of the Blood Rite", 1); // {3}{B}{B}
        setChoice(playerB, true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Priest of the Blood Rite");
        setStopAt(3, PhaseStep.UPKEEP);
        execute();

        assertPermanentCount(playerA, "Priest of the Blood Rite", 1);
        assertPermanentCount(playerA, "Demon Token", 2);

        assertLife(playerB, 20);
        assertLife(playerA, 18);
    }

    /**
     * I created a token copy of Wurmcoil Engine and sacrificed it. This gave me
     * 4 tokens
     */
    @Test
    public void testWurmcoilEngine() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 7);
        // Whenever a nontoken creature enters the battlefield under your control, you may pay {R}. If you do, put a token onto the battlefield that's a copy of that creature.
        // That token gains haste. Exile it at the beginning of the next end step.
        addCard(Zone.BATTLEFIELD, playerA, "Flameshadow Conjuring", 1);
        // Deathtouch, lifelink
        // When Wurmcoil Engine dies, put a 3/3 colorless Wurm artifact creature token with deathtouch and a 3/3 colorless Wurm artifact creature token with lifelink onto the battlefield.
        addCard(Zone.HAND, playerA, "Wurmcoil Engine", 1); // 6/6 - {6}

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 3);
        // Destroy target attacking creature.
        addCard(Zone.HAND, playerB, "Kill Shot", 1); // {2}{U}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wurmcoil Engine");
        setChoice(playerA, true);

        attack(1, playerA, "Wurmcoil Engine");

        castSpell(1, PhaseStep.END_COMBAT, playerB, "Kill Shot", "Wurmcoil Engine");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Kill Shot", 1);
        assertPermanentCount(playerA, "Wurmcoil Engine", 1);
        assertLife(playerB, 14);
        assertLife(playerA, 26);

        assertPermanentCount(playerA, "Phyrexian Wurm Token", 2);

    }

}
