package org.mage.test.cards.single.hob;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class AzogMoriasRuinTest extends CardTestPlayerBase {

    /**
     * Azog, Moria's Ruin {2}{B}
     * Legendary Creature - Goblin Soldier 1/3
     * When Azog enters, destroy up to one other target creature. Its controller amasses Goblins X, where X is that
     * creature's power. If you controlled that creature, draw a card.
     */
    private static final String azog = "Azog, Moria's Ruin";
    private static final String army = "Goblin Army Token";

    @Test
    public void test_OpponentCreature_OpponentAmasses() {
        addCard(Zone.HAND, playerA, azog);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Hill Giant"); // 3/3

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, azog);
        addTarget(playerA, "Hill Giant");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Hill Giant", 1);
        assertPermanentCount(playerA, army, 0);
        assertPermanentCount(playerB, army, 1);
        assertPowerToughness(playerB, army, 3, 3);
        assertHandCount(playerA, 0); // no draw
    }

    @Test
    public void test_OwnCreature_YouAmassAndDraw() {
        addCard(Zone.HAND, playerA, azog);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Hill Giant"); // 3/3

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, azog);
        addTarget(playerA, "Hill Giant");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Hill Giant", 1);
        assertPermanentCount(playerA, army, 1);
        assertPowerToughness(playerA, army, 3, 3);
        assertHandCount(playerA, 1);
    }

    @Test
    public void test_BothHaveArmies_CountersOnArmyOfThatPlayer() {
        // changelings are Army creatures, so nobody creates a new token
        addCard(Zone.HAND, playerA, azog);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Chameleon Colossus"); // 4/4, changeling
        addCard(Zone.BATTLEFIELD, playerB, "Changeling Outcast"); // 1/1, changeling
        addCard(Zone.BATTLEFIELD, playerB, "Hill Giant"); // 3/3

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, azog);
        addTarget(playerA, "Hill Giant");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, army, 0);
        assertPermanentCount(playerB, army, 0);
        assertPowerToughness(playerA, "Chameleon Colossus", 4, 4);
        assertPowerToughness(playerB, "Changeling Outcast", 1 + 3, 1 + 3);
    }

    @Test
    public void test_OnlyYouHaveArmy_OpponentCreatesToken() {
        addCard(Zone.HAND, playerA, azog);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Chameleon Colossus"); // 4/4, changeling
        addCard(Zone.BATTLEFIELD, playerB, "Hill Giant"); // 3/3

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, azog);
        addTarget(playerA, "Hill Giant");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, "Chameleon Colossus", 4, 4);
        assertPermanentCount(playerB, army, 1);
        assertPowerToughness(playerB, army, 3, 3);
    }

    @Test
    public void test_PowerAsItLastExisted() {
        // ruling: use the power of the creature as it last existed on the battlefield
        addCard(Zone.HAND, playerA, azog);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Hill Giant"); // 3/3
        addCard(Zone.HAND, playerB, "Giant Growth"); // {G}, +3/+3
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, azog);
        addTarget(playerA, "Hill Giant");

        // boost in response to the trigger
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Giant Growth", "Hill Giant", "When {this} enters");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Hill Giant", 1);
        assertPowerToughness(playerB, army, 3 + 3, 3 + 3);
    }

    @Test
    public void test_IndestructibleCreature_StillAmasses() {
        // amass and draw don't depend on the destroy result
        addCard(Zone.HAND, playerA, azog);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Darksteel Sentinel"); // 3/3, indestructible

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, azog);
        addTarget(playerA, "Darksteel Sentinel");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Darksteel Sentinel", 1);
        assertPowerToughness(playerA, army, 3, 3);
        assertHandCount(playerA, 1);
    }

    @Test
    public void test_NoTarget_NothingHappens() {
        // ruling: if no target is chosen, "its controller" is undefined and no player amasses Goblins
        addCard(Zone.HAND, playerA, azog);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Hill Giant"); // 3/3

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, azog);
        addTarget(playerA, TestPlayer.TARGET_SKIP);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Hill Giant", 1);
        assertPermanentCount(playerA, army, 0);
        assertHandCount(playerA, 0);
    }

    @Test
    public void test_BlinkedTarget_TriggerDoesNotResolve() {
        // 608.2b: a target that's no longer in the zone it was in when it was targeted is illegal,
        // so the ability doesn't resolve - no destroy, no amass, no draw
        addCard(Zone.HAND, playerA, azog);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Hill Giant"); // 3/3
        addCard(Zone.HAND, playerA, "Cloudshift"); // {W}, blink own creature
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3); // auto-payment of Azog can use 2 Plains

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, azog);
        addTarget(playerA, "Hill Giant");

        // blink in response to the trigger
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cloudshift", "Hill Giant", "When {this} enters");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Hill Giant", 1);
        assertPermanentCount(playerA, army, 0);
        assertHandCount(playerA, 0);
    }
}
