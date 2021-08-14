
package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class KarmicJusticeTest extends CardTestPlayerBase {

    /*
    Karmic Justice
    Whenever a spell or ability an opponent controls destroys a noncreature permanent you control,
    you may destroy target permanent that opponent controls.
     */
    /**
     * Karmic Justice should triggers for its own destroyment
     */
    @Test
    public void testFirstTriggeredAbility() {
        addCard(Zone.BATTLEFIELD, playerA, "Karmic Justice");

        addCard(Zone.HAND, playerB, "Naturalize");
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Naturalize", "Karmic Justice");
        setChoice(playerA, true);
        addTarget(playerA, "Forest");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Naturalize", 1);
        assertGraveyardCount(playerA, "Karmic Justice", 1);
        assertPermanentCount(playerB, "Forest", 1);

    }

    /**
     * Karmic Justice should triggers for each destroyed permanent
     */
    @Test
    public void testMultiplePermanentsDestroyedTriggeredAbility() {
        // At the beginning of each upkeep, if you lost life last turn, create a 1/1 white Soldier creature token.
        addCard(Zone.BATTLEFIELD, playerA, "First Response", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Karmic Justice");

        // Planar Cleansing {3}{W}{W}{W}
        // Sorcery
        // Destroy all nonland permanents.
        addCard(Zone.HAND, playerB, "Planar Cleansing");
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 1);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Planar Cleansing");
        setChoice(playerA, true);
        addTarget(playerA, "Plains");
        setChoice(playerA, true);
        addTarget(playerA, "Swamp");
        setChoice(playerA, true);
        addTarget(playerA, "Mountain");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Planar Cleansing", 1);
        assertGraveyardCount(playerA, "Karmic Justice", 1);
        assertGraveyardCount(playerA, "First Response", 2);
        assertGraveyardCount(playerB, "Mountain", 1);
        assertGraveyardCount(playerB, "Swamp", 1);
        assertGraveyardCount(playerB, "Plains", 1);

    }

}
