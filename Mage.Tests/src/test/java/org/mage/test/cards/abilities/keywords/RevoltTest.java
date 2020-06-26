package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class RevoltTest extends CardTestPlayerBase {

    /**
     * In a duel commander match, I played a turn 1 Narnam Renegade off a basic
     * forest, and it entered the battlefield with a +1/+1 counter (it shouldn't
     * have).
     */
    @Test
    public void testFalseCondition() {
        // Deathtouch
        // <i>Revolt</i> &mdash; Narnam Renegade enters the battlefield with a +1/+1 counter on it if a permanent you controlled left this battlefield this turn.
        addCard(Zone.HAND, playerA, "Narnam Renegade", 1); // Creature 1/2 {G}
        addCard(Zone.HAND, playerA, "Forest", 1);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Forest");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Narnam Renegade");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Narnam Renegade", 1, 2);
    }

    @Test
    public void testTrueCondition() {
        // Deathtouch
        // <i>Revolt</i> &mdash; Narnam Renegade enters the battlefield with a +1/+1 counter on it if a permanent you controlled left this battlefield this turn.
        addCard(Zone.HAND, playerA, "Narnam Renegade", 1); // Creature 1/2 {G}
        // {T}, Sacrifice Terramorphic Expanse: Search your library for a basic land card and put it onto the battlefield tapped. Then shuffle your library.
        addCard(Zone.BATTLEFIELD, playerA, "Terramorphic Expanse", 1);
        addCard(Zone.HAND, playerA, "Forest", 1);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Forest");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Sacrifice");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Narnam Renegade");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Terramorphic Expanse", 1);
        assertPowerToughness(playerA, "Narnam Renegade", 2, 3);
    }

}
