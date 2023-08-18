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

    private static final String courtier = "Cursed Courtier";
    private static final String rage = "Monstrous Rage";
    private static final String wardens = "Nexus Wardens";
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
}
