package org.mage.test.cards.single.zen;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class GomazoaTest extends CardTestPlayerBase {

    // see issue #13774

    private static final String gomazoa = "Gomazoa"; // 0/3 defender flying
    // {T}: Put this creature and each creature it’s blocking on top of their owners’ libraries, then those players shuffle.

    private static final String aetherplasm = "Aetherplasm";
    // Whenever this creature blocks a creature, you may return this creature to its owner’s hand.
    // If you do, you may put a creature card from your hand onto the battlefield blocking that creature.

    private static final String crab = "Fortress Crab"; // 1/6
    private static final String wishcoin = "Wishcoin Crab"; // 2/5

    private static final String labyrinth = "Labyrinth of Skophos";
    // {4}, {T}: Remove target attacking or blocking creature from combat.

    private static final String warlord = "Balduvian Warlord";
    // {T}: Remove target blocking creature from combat. Creatures it was blocking that hadn’t become blocked
    // by another creature this combat become unblocked, then it blocks an attacking creature of your choice.
    // Activate only during the declare blockers step.

    @Test
    public void testSimple() {
        addCard(Zone.BATTLEFIELD, playerA, crab);
        addCard(Zone.BATTLEFIELD, playerB, gomazoa);

        attack(1, playerA, crab, playerB);
        block(1, playerB, gomazoa, crab);

        activateAbility(1, PhaseStep.DECLARE_BLOCKERS, playerB, "{T}: Put");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLibraryCount(playerB, gomazoa, 1);
        assertLibraryCount(playerA, crab, 1);
    }

    @Test
    public void testRemoveAttackerFromCombat() {
        addCard(Zone.BATTLEFIELD, playerA, crab);
        addCard(Zone.BATTLEFIELD, playerB, gomazoa);
        addCard(Zone.BATTLEFIELD, playerA, labyrinth);
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 4);

        attack(1, playerA, crab, playerB);
        block(1, playerB, gomazoa, crab);

        activateAbility(1, PhaseStep.DECLARE_BLOCKERS, playerA, "{4}, {T}: Remove", crab);
        waitStackResolved(1, PhaseStep.DECLARE_BLOCKERS);
        activateAbility(1, PhaseStep.DECLARE_BLOCKERS, playerB, "{T}: Put");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLibraryCount(playerB, gomazoa, 1);
        assertPermanentCount(playerA, crab, 1);
    }

    @Test
    public void testRemoveGomazoaFromCombat() {
        addCard(Zone.BATTLEFIELD, playerA, crab);
        addCard(Zone.BATTLEFIELD, playerB, gomazoa);
        addCard(Zone.BATTLEFIELD, playerA, labyrinth);
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 4);

        attack(1, playerA, crab, playerB);
        block(1, playerB, gomazoa, crab);

        activateAbility(1, PhaseStep.DECLARE_BLOCKERS, playerA, "{4}, {T}: Remove", gomazoa);
        waitStackResolved(1, PhaseStep.DECLARE_BLOCKERS);
        activateAbility(1, PhaseStep.DECLARE_BLOCKERS, playerB, "{T}: Put");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLibraryCount(playerB, gomazoa, 1);
        assertPermanentCount(playerA, crab, 1);
    }


    @Test
    public void testRemoveGomazoaInResponse() {
        addCard(Zone.BATTLEFIELD, playerA, crab);
        addCard(Zone.BATTLEFIELD, playerB, gomazoa);
        addCard(Zone.BATTLEFIELD, playerB, "Blood Bairn");

        attack(1, playerA, crab, playerB);
        block(1, playerB, gomazoa, crab);

        activateAbility(1, PhaseStep.DECLARE_BLOCKERS, playerB, "{T}: Put");
        activateAbility(1, PhaseStep.DECLARE_BLOCKERS, playerB, "Sacrifice");
        setChoice(playerB, gomazoa);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, gomazoa, 1);
        assertLibraryCount(playerA, crab, 1);
    }

    @Test
    public void testWithAetherplasm() {
        addCard(Zone.BATTLEFIELD, playerA, crab);
        addCard(Zone.BATTLEFIELD, playerB, aetherplasm);
        addCard(Zone.HAND, playerB, gomazoa);
        addCard(Zone.BATTLEFIELD, playerB, "Fervor"); // creatures you control have haste

        attack(1, playerA, crab, playerB);
        block(1, playerB, aetherplasm, crab);

        setChoice(playerB, true); // yes to ability
        setChoice(playerB, true); // yes to put creature
        setChoice(playerB, gomazoa); // from hand to blocker

        waitStackResolved(1, PhaseStep.DECLARE_BLOCKERS);
        activateAbility(1, PhaseStep.DECLARE_BLOCKERS, playerB, "{T}: Put");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLibraryCount(playerB, gomazoa, 1);
        assertLibraryCount(playerA, crab, 1);
        assertHandCount(playerB, aetherplasm, 1);
    }

    @Test
    public void testWithBalduvianWarlord() {
        addCard(Zone.BATTLEFIELD, playerA, crab);
        addCard(Zone.BATTLEFIELD, playerA, wishcoin);
        addCard(Zone.BATTLEFIELD, playerA, warlord);
        addCard(Zone.BATTLEFIELD, playerB, gomazoa);

        attack(1, playerA, crab, playerB);
        attack(1, playerA, wishcoin, playerB);
        block(1, playerB, gomazoa, crab);

        activateAbility(1, PhaseStep.DECLARE_BLOCKERS, playerA, "{T}: Remove", gomazoa);
        addTarget(playerA, wishcoin);
        waitStackResolved(1, PhaseStep.DECLARE_BLOCKERS);
        activateAbility(1, PhaseStep.DECLARE_BLOCKERS, playerB, "{T}: Put");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLibraryCount(playerB, gomazoa, 1);
        assertPermanentCount(playerA, crab, 1);
        assertLibraryCount(playerA, wishcoin, 1);
    }

}
