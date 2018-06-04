
package org.mage.test.cards.enchantments;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class OathOfLiegesTest extends CardTestPlayerBase {

    @Test
    public void testSearchLandOwner() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        // At the beginning of each player's upkeep, that player chooses target player who controls more lands than he or she does and is their opponent.
        // The first player may search their library for a basic land card, put that card onto the battlefield, then shuffle their library.
        addCard(Zone.HAND, playerA, "Oath of Lieges", 1); // {1}{W}
        addCard(Zone.LIBRARY, playerA, "Plains", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Oath of Lieges");
        addTarget(playerA, playerB);
        addTarget(playerA, "Plains");

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Oath of Lieges", 1);
        assertPermanentCount(playerA, "Plains", 3);

    }

    @Test
    public void testSearchLandOpponent() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        // At the beginning of each player's upkeep, that player chooses target player who controls more lands than he or she does and is their opponent.
        // The first player may search their library for a basic land card, put that card onto the battlefield, then shuffle their library.
        addCard(Zone.HAND, playerA, "Oath of Lieges", 1); // {1}{W}

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 1);
        addCard(Zone.LIBRARY, playerB, "Plains", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Oath of Lieges");
        addTarget(playerB, playerA);
        addTarget(playerB, "Plains");

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Oath of Lieges", 1);
        assertPermanentCount(playerA, "Plains", 2);
        assertPermanentCount(playerB, "Plains", 2);
    }

    @Test
    public void testSearchLandOwnerCopy() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        // At the beginning of each player's upkeep, that player chooses target player who controls more lands than he or she does and is their opponent.
        // The first player may search their library for a basic land card, put that card onto the battlefield, then shuffle their library.
        addCard(Zone.HAND, playerA, "Oath of Lieges", 1); // {1}{W}
        addCard(Zone.LIBRARY, playerA, "Plains", 3);
        addCard(Zone.HAND, playerA, "Plains", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Island", 3);
        addCard(Zone.HAND, playerB, "Copy Enchantment", 1); // {2}{U}
        addCard(Zone.LIBRARY, playerB, "Plains", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Oath of Lieges");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Copy Enchantment");
        setChoice(playerB, "Oath of Lieges");

        // turn 3
        addTarget(playerA, playerB);
        addTarget(playerA, "Plains"); // 3rd land
        addTarget(playerA, "Plains"); // second trigger will fail because target player has no longer more lands than controller

        playLand(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Plains"); // 4th land

        // turn 4
        addTarget(playerB, playerA);
        addTarget(playerB, "Plains");
        addTarget(playerB, "Plains"); // second trigger will fail because target player has no longer more lands than controller

        setStopAt(4, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerB, "Oath of Lieges", 1);
        assertPermanentCount(playerA, "Oath of Lieges", 1);

        assertPermanentCount(playerB, "Plains", 1);
        assertPermanentCount(playerA, "Plains", 4);

    }
}
