package org.mage.test.combat;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2, JayDi85
 */
public class RemoveFromCombatTest extends CardTestPlayerBase {

    /**
     * In a test game against the AI, it attacked me with a Stomping Ground
     * animated by an Ambush Commander and boosted it with the Commander's
     * second ability. I killed the Commander. The now non-creature land
     * continued attacking and dealt 3 damage to me.
     */
    @Test
    public void test_LeavesCombatIfNoLongerACreature() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.HAND, playerA, "Lightning Blast", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Elvish Mystic", 1);

        // Forests you control are 1/1 green Elf creatures that are still lands.
        // {1}{G},Sacrifice an Elf: Target creature gets +3/+3 until end of turn.
        addCard(Zone.BATTLEFIELD, playerB, "Ambush Commander", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Stomping Ground");

        attack(2, playerB, "Stomping Ground");
        activateAbility(2, PhaseStep.DECLARE_ATTACKERS, playerB, "{1}{G}", "Stomping Ground");
        setChoice(playerB, "Elvish Mystic");
        castSpell(2, PhaseStep.DECLARE_BLOCKERS, playerA, "Lightning Blast", "Ambush Commander");
        setStopAt(2, PhaseStep.COMBAT_DAMAGE);
        execute();

        assertGraveyardCount(playerB, "Elvish Mystic", 1);
        assertGraveyardCount(playerA, "Lightning Blast", 1);
        assertGraveyardCount(playerB, "Ambush Commander", 1);

        assertPowerToughness(playerB, "Stomping Ground", 0, 0);

        Permanent stompingGround = getPermanent("Stomping Ground", playerB);
        Assert.assertEquals("Stomping Ground has to be removed from combat", false, stompingGround.isAttacking());

        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }

    @Test
    public void test_Defender_AttackPlayer() {
        // Enchant player
        // Creatures attacking enchanted player have trample.
        addCard(Zone.HAND, playerA, "Curse of Hospitality", 1); // {2}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Alpha Myr", 1); // 2/1

        // prepare
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Hospitality");
        addTarget(playerA, playerB);

        // attack and get trumple
        attack(1, playerA, "Grizzly Bears");
        block(1, playerB, "Alpha Myr", "Grizzly Bears");
        setChoiceAmount(playerA, 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 1); // must get 1 from trumple
    }

    @Test
    public void test_Defender_AttackPlaneswalkerAndRemoveDefender() {
        // possible bug: NPE error on defender remove from battle

        // Enchant player
        // Creatures attacking enchanted player have trample.
        addCard(Zone.HAND, playerA, "Curse of Hospitality", 1); // {2}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1); // 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Adaptive Snapjaw", 1); // 6/2
        addCard(Zone.BATTLEFIELD, playerB, "Jace, Memory Adept", 1); // 4
        addCard(Zone.BATTLEFIELD, playerB, "Alpha Myr", 1); // 2/1

        // prepare
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Hospitality");
        addTarget(playerA, playerB);

        // attack planeswalker and remove it from battlefield due damage
        attack(1, playerA, "Adaptive Snapjaw", "Jace, Memory Adept");
        attack(1, playerA, "Grizzly Bears", playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 2);
        assertGraveyardCount(playerB, "Jace, Memory Adept", 1);
    }
}
