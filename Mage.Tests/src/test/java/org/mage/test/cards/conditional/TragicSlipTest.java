package org.mage.test.cards.conditional;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class TragicSlipTest extends CardTestPlayerBase {

    @Test
    public void testNoCreatureDied() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");

        // Tragic Slip - Instant, B - Target creature gets -1/-1 until end of turn.
        // Morbid — That creature gets -13/-13 until end of turn instead if a creature died this turn.
        addCard(Zone.HAND, playerA, "Tragic Slip");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerA, "Pillarfield Ox");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tragic Slip", "Pillarfield Ox");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Pillarfield Ox", 1, 3);
    }

    @Test
    public void testCreatureDiedAfter() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        // Tragic Slip - Instant, B - Target creature gets -1/-1 until end of turn.
        // Morbid — That creature gets -13/-13 until end of turn instead if a creature died this turn.
        addCard(Zone.HAND, playerA, "Tragic Slip");
        // Searing Spear - Instant, 1R - Searing Spear deals 3 damage to any target.
        addCard(Zone.HAND, playerA, "Searing Spear");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerA, "Pillarfield Ox");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tragic Slip", "Pillarfield Ox");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Searing Spear", "Silvercoat Lion");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, "Pillarfield Ox", 1, 3);
    }

    @Test
    public void testCreatureDiedBefore() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        // Tragic Slip - Instant, B - Target creature gets -1/-1 until end of turn.
        // Morbid — That creature gets -13/-13 until end of turn instead if a creature died this turn.
        addCard(Zone.HAND, playerA, "Tragic Slip");
        // Searing Spear - Instant, 1R - Searing Spear deals 3 damage to any target.
        addCard(Zone.HAND, playerA, "Searing Spear");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerA, "Pillarfield Ox");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Searing Spear", "Silvercoat Lion");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Tragic Slip", "Pillarfield Ox");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Silvercoat Lion", 0);
        assertPermanentCount(playerA, "Pillarfield Ox", 0);
    }

    /**
     * Reported bug:
     *      Killed an opponent's Young Pyromancer with Ulcerate then flashed back Tragic Slip with Snapcaster Mage targeting their Tarmogoyf.
     *      Morbid didn't seem to work and only applied -1/-1 to the Tarmogoyf.
     */
    @Test
    public void testPlayedWithFlashbackAgain() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        // Tragic Slip - Instant, B - Target creature gets -1/-1 until end of turn.
        // Morbid — That creature gets -13/-13 until end of turn instead if a creature died this turn.
        addCard(Zone.HAND, playerA, "Tragic Slip");

        // Creature - Human Wizard
        // 2/1
        // Flash
        // When Snapcaster Mage enters the battlefield, target instant or sorcery card in your graveyard gains flashback until end of turn. The flashback cost is equal to its mana cost.
        addCard(Zone.HAND, playerA, "Snapcaster Mage");
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");
        // Tarmogoyf's power is equal to the number of card types among cards in all graveyards and its toughness is equal to that number plus 1
        addCard(Zone.BATTLEFIELD, playerB, "Tarmogoyf");
        addCard(Zone.GRAVEYARD, playerB, "Mountain");

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tragic Slip", "Silvercoat Lion");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Snapcaster Mage");
        addTarget(playerA, "Tragic Slip");
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", "Snapcaster Mage");
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Flashback {B}"); // now snapcaster mage is ded so -13/-13
        addTarget(playerA, "Tarmogoyf");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, "Tragic Slip", 0);
        assertPermanentCount(playerA, "Snapcaster Mage", 0);
        assertExileCount("Tragic Slip", 1);
        assertPermanentCount(playerB, "Silvercoat Lion", 1);
        assertPowerToughness(playerB, "Silvercoat Lion", 1, 1);
        assertGraveyardCount(playerB, "Tarmogoyf", 1);
    }
}
