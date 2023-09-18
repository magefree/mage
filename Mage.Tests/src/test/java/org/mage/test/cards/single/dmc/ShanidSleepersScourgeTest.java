package org.mage.test.cards.single.dmc;

import mage.abilities.keyword.MenaceAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.s.ShanidSleepersScourge Shanid, Sleepers' Scourge}
 * {1}{R}{W}{B}
 *
 * Menace
 * Other legendary creatures you control have menace.
 * Whenever you play a legendary land or cast a legendary spell, you draw a card and you lose 1 life.
 *
 * @author freaisdead
 */
public class ShanidSleepersScourgeTest extends CardTestPlayerBase {

    /**
     * Test that you correctly draw a card for playing a legendary land and legendary artifact
     */
    @Test
    public void testShanidSleepersScourge() {
        addCard(Zone.BATTLEFIELD, playerA, "Shanid, Sleepers' Scourge");

        addCard(Zone.HAND, playerA, "Academy Ruins", 1); // Legendary Land
        addCard(Zone.HAND, playerA, "Mox Amber", 1); // Legendary Artifact

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Academy Ruins");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Mox Amber");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertLife(playerA, 20 - 2);
        assertHandCount(playerA, 2);
        assertLife(playerB, 20);
    }

    /**
     * Test that the draw card ability does not trigger for non-legendary
     */
    @Test
    public void testShanidSleepersScourgeNoTrigger() {
        addCard(Zone.BATTLEFIELD, playerA, "Shanid, Sleepers' Scourge");

        addCard(Zone.HAND, playerA, "Forest");
        addCard(Zone.HAND, playerA, "Memnite", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Memnite", true);
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Forest");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertLife(playerA, 20);
        assertHandCount(playerA, 0);
        assertLife(playerB, 20);
    }

    /**
     * Test that it correctly gives menace to your own legendary creatures,
     * but not to your non-legendary creatures nor to your opponent's legendary creatures.
     */
    @Test
    public void testShanidSleepersScourgeMenace() {
        // Legendary
        String gaddock = "Gaddock Teeg";
        addCard(Zone.BATTLEFIELD, playerA, "Shanid, Sleepers' Scourge");
        addCard(Zone.BATTLEFIELD, playerA, "Memnite"); // Non-legendary
        addCard(Zone.BATTLEFIELD, playerA, gaddock);
        addCard(Zone.BATTLEFIELD, playerB, gaddock); // Legendary

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAbility(playerA, gaddock, new MenaceAbility(false), true);
        assertAbility(playerA, "Memnite", new MenaceAbility(false), false);
        assertAbility(playerB, gaddock, new MenaceAbility(false), false);
    }
}
