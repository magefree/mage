package org.mage.test.cards.single.eoe;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class LightstallInquisitorTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.l.LightstallInquisitor Lightstall Inquisitor} {W}
     * Creature — Angel Wizard
     * Vigilance
     * When this creature enters, each opponent exiles a card from their hand and may play that card for as long as it remains exiled. Each spell cast this way costs {1} more to cast. Each land played this way enters tapped.
     * 2/1
     */
    private static final String inquisitor = "Lightstall Inquisitor";

    @Test
    public void test_Land() {
        addCard(Zone.HAND, playerA, inquisitor);
        addCard(Zone.HAND, playerB, "Savannah", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, inquisitor);
        setChoice(playerB, "Savannah");

        playLand(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Savannah");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerB, "Savannah", 1);
        assertTappedCount("Savannah", true, 1);
    }

    @Test
    public void test_NonLand() {
        addCard(Zone.HAND, playerA, inquisitor);
        addCard(Zone.HAND, playerB, "Centaur Courser", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, inquisitor);
        setChoice(playerB, "Centaur Courser");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Centaur Courser");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerB, "Centaur Courser", 1);
        assertTappedCount("Forest", true, 4);
        assertTappedCount("Centaur Courser", false, 1);
    }
}
