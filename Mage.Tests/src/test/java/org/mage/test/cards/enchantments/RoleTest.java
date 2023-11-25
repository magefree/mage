package org.mage.test.cards.enchantments;

import mage.abilities.keyword.TrampleAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class RoleTest extends CardTestPlayerBase {

    private static final String courtier = "Cursed Courtier"; // When Cursed Courtier enters the battlefield, create a Cursed Role token attached to it.
    private static final String rage = "Monstrous Rage"; // Target creature gets +2/+0 until end of turn. Create a Monster Role token attached to it.
    private static final String wardens = "Nexus Wardens"; // Whenever an enchantment enters the battlefield under your control, you gain 2 life.
    private static final String murder = "Murder";

    @Test
    public void testRegular() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, wardens);
        addCard(Zone.HAND, playerA, courtier);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, courtier);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, courtier, 1);
        assertPermanentCount(playerA, "Cursed", 1);
        assertPowerToughness(playerA, courtier, 1, 1);
        assertLife(playerA, 20 + 2);
    }

    @Test
    public void testReplace() {
        addCard(Zone.BATTLEFIELD, playerA, "Plateau", 4);
        addCard(Zone.BATTLEFIELD, playerA, wardens);
        addCard(Zone.HAND, playerA, courtier);
        addCard(Zone.HAND, playerA, rage);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, courtier);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, rage, courtier);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, courtier, 1);
        assertGraveyardCount(playerA, rage, 1);
        assertPermanentCount(playerA, "Cursed", 0);
        assertPermanentCount(playerA, "Monster", 1);
        assertPowerToughness(playerA, courtier, 3 + 2 + 1, 3 + 1);
        assertAbility(playerA, courtier, TrampleAbility.getInstance(), true);
        assertLife(playerA, 20 + 2 + 2);
    }

    @Test
    public void testReplaceResponse() {
        addCard(Zone.BATTLEFIELD, playerA, "Plateau", 4);
        addCard(Zone.BATTLEFIELD, playerA, wardens);
        addCard(Zone.HAND, playerA, courtier);
        addCard(Zone.HAND, playerA, rage);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, courtier);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, rage, courtier);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, courtier, 1);
        assertGraveyardCount(playerA, rage, 1);
        assertPermanentCount(playerA, "Cursed", 1);
        assertPermanentCount(playerA, "Monster", 0);
        assertPowerToughness(playerA, courtier, 1 + 2, 1);
        assertAbility(playerA, courtier, TrampleAbility.getInstance(), false);
        assertLife(playerA, 20 + 2 + 2);
    }

    @Test
    public void testSeparatePlayer() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, wardens);
        addCard(Zone.BATTLEFIELD, playerB, wardens);
        addCard(Zone.HAND, playerA, courtier);
        addCard(Zone.HAND, playerB, rage);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, courtier);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, rage, courtier);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, courtier, 1);
        assertGraveyardCount(playerB, rage, 1);
        assertPermanentCount(playerA, "Cursed", 1);
        assertPermanentCount(playerB, "Monster", 1);
        assertPowerToughness(playerA, courtier, 1 + 2 + 1, 1 + 1);
        assertAbility(playerA, courtier, TrampleAbility.getInstance(), true);
        assertLife(playerA, 20 + 2);
        assertLife(playerB, 20 + 2);
    }

    @Test
    public void testDoesntEnter() {
        addCard(Zone.BATTLEFIELD, playerA, "Scrubland", 3 + 3);
        addCard(Zone.BATTLEFIELD, playerA, wardens);
        addCard(Zone.HAND, playerA, courtier);
        addCard(Zone.HAND, playerA, murder);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, courtier);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, murder, courtier);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, courtier, 0);
        assertPermanentCount(playerA, "Cursed", 0);
        assertGraveyardCount(playerA, courtier, 1);
        assertGraveyardCount(playerA, murder, 1);
        assertLife(playerA, 20);
    }

    @Test
    public void testProtectionNotCreated() {
        String firstwing = "Azorius First-Wing"; // 2/2 Flying, protection from enchantments
        addCard(Zone.BATTLEFIELD, playerA, firstwing);
        addCard(Zone.BATTLEFIELD, playerA, wardens);
        addCard(Zone.HAND, playerA, rage);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, rage, firstwing);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, rage, 1);
        assertPowerToughness(playerA, firstwing, 4, 2);
        assertLife(playerA, 20);
    }

    @Test
    public void testBecomeBrutes() {
        String bear = "Runeclaw Bear"; // 2/2
        String become = "Become Brutes";
        // One or two target creatures each gain haste until end of turn.
        // For each of those creatures, create a Monster Role token attached to it.
        addCard(Zone.BATTLEFIELD, playerA, bear);
        addCard(Zone.BATTLEFIELD, playerA, wardens);
        addCard(Zone.HAND, playerA, become);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        addTarget(playerA, bear + "^" + wardens);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, become);
        setChoice(playerA, ""); // order triggers

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, become, 1);
        assertPowerToughness(playerA, bear, 3, 3);
        assertPowerToughness(playerA, wardens, 2, 5);
        assertLife(playerA, 20 + 2 + 2);
    }

}
