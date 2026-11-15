package org.mage.test.cards.single.all;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class ScarabOfTheUnseenTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.s.ScarabOfTheUnseen}
     * <p>
     * {2} Artifact<br>
     * {T}, Sacrifice Scarab of the Unseen: Return all Auras attached to target permanent you own to their owners'
     * hands. Draw a card at the beginning of the next turn's upkeep.
     */
    private static final String SCARAB_OF_THE_UNSEEN = "Scarab of the Unseen";

    @Test
    public void test_ReturnAllAuras() {
        addCard(Zone.BATTLEFIELD, playerA, SCARAB_OF_THE_UNSEEN);
        //
        addCard(Zone.HAND, playerA, "Holy Strength"); // {W}, aura, +1/+2
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.HAND, playerB, "Unholy Strength"); // {B}, aura, +2/+1
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Holy Strength", "Grizzly Bears");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Unholy Strength", "Grizzly Bears");
        waitStackResolved(2, PhaseStep.PRECOMBAT_MAIN);

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Sacrifice", "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, "Holy Strength", 1);
        assertHandCount(playerB, "Unholy Strength", 1);
        assertPowerToughness(playerA, "Grizzly Bears", 2, 2);
    }
}
