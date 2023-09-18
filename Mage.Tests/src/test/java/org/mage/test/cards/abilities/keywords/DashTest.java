
package org.mage.test.cards.abilities.keywords;

import mage.abilities.keyword.HasteAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * 702.109. {@link mage.abilities.keyword.DashAbility Dash}
 * 702.109a Dash represents three abilities: two static abilities that
 * function while the card with dash is on the stack, one of which may
 * create a delayed triggered ability, and a static ability that functions
 * while the object with dash is on the battlefield.
 *
 * “Dash [cost]” means “You may cast this card by paying [cost] rather that
 * its mana cost,” “If this spell's dash cost was paid, return the permanent this
 * spell becomes to its owner's hand at the beginning of the next end step,”
 * and “As long as this permanent's dash cost was paid, it has haste.”
 *
 * Paying a card's dash cost follows the rules for paying alternative costs
 * in rules 601.2b and 601.2e–g.
 *
 * @author BetaSteward
 */
public class DashTest extends CardTestPlayerBase {
    /**
     * Screamreach Brawler
     * Creature — Orc Berserker 2/3
     * {2}{R}
     *
     * Dash {1}{R} (You may cast this spell for its dash cost. If you do, it gains haste, and it's returned
     * from the battlefield to its owner's hand at the beginning of the next end step.)
     */
    @Test
    public void testDash() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Screamreach Brawler");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Screamreach Brawler");
        setChoice(playerA, true);
        attack(1, playerA, "Screamreach Brawler");

        setStopAt(2, PhaseStep.UNTAP);
        execute();

        assertLife(playerB, 18);
        assertPermanentCount(playerA, "Screamreach Brawler", 0);
        assertHandCount(playerA, "Screamreach Brawler", 1);

    }

    @Test
    public void testNoDash() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, "Screamreach Brawler");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Screamreach Brawler");
        setChoice(playerA, false);
        checkPlayableAbility("attack", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "attack: Scream", false);

        setStopAt(2, PhaseStep.UNTAP);
        execute();

        // TODO: Must be a better way to check if a creature can't attack

        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Screamreach Brawler", 1);
        assertHandCount(playerA, "Screamreach Brawler", 0);

    }

    /**
     * Also dash returns creatures to your hand at end of turn even if they died
     * that turn.
     */
    @Test
    public void testDashedCreatureDiesInCombat() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Screamreach Brawler"); // 2/3

        addCard(Zone.BATTLEFIELD, playerB, "Geist of the Moors", 1); // 3/1

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Screamreach Brawler");
        setChoice(playerA, true);
        attack(1, playerA, "Screamreach Brawler");
        block(1, playerB, "Geist of the Moors", "Screamreach Brawler");

        setStopAt(2, PhaseStep.UNTAP);
        execute();

        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Screamreach Brawler", 0);
        assertHandCount(playerA, "Screamreach Brawler", 0);
        assertGraveyardCount(playerA, "Screamreach Brawler", 1);
        assertGraveyardCount(playerB, "Geist of the Moors", 1);

    }

    /**
     * Test that the creature got Dash again if cast again
     */
    @Test
    public void testDashedCreatureDiesInCombatAndIsLaterRecast() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Screamreach Brawler"); // 2/3

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Screamreach Brawler");
        setChoice(playerA, true);
        attack(1, playerA, "Screamreach Brawler");

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Screamreach Brawler");
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 18);
        assertPermanentCount(playerA, "Screamreach Brawler", 1);
        assertHandCount(playerA, "Screamreach Brawler", 0);
        assertAbility(playerA, "Screamreach Brawler", HasteAbility.getInstance(), true);
    }

    @Test
    public void testWarbringerCostReduction() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Warbringer");
        addCard(Zone.HAND, playerA, "Warbringer");

        setStrictChooseMode(true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Warbringer");
        setChoice(playerA, true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Warbringer", 2);
        assertHandCount(playerA, "Warbringer", 0);
    }

    @Test
    public void testRegularCostReduction() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Ruby Medallion");
        addCard(Zone.HAND, playerA, "Screamreach Brawler");

        setStrictChooseMode(true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Screamreach Brawler");
        setChoice(playerA, true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Ruby Medallion", 1);
        assertPermanentCount(playerA, "Screamreach Brawler", 1);
        assertHandCount(playerA, "Screamreach Brawler", 0);
    }
}
