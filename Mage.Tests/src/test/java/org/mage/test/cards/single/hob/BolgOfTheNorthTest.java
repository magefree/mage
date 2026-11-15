package org.mage.test.cards.single.hob;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author muz
 */
public class BolgOfTheNorthTest extends CardTestPlayerBase {

    // When Bolg enters, you may sacrifice another creature. When you do, Bolg deals damage equal to
    // that creature's power to another target creature. If excess damage was dealt this way,
    // amass Goblins X, where X is that excess damage.
    private static final String bolg = "Bolg of the North";
    private static final String elemental = "Fire Elemental"; // 5/4; supplies 5 power when sacrificed.
    private static final String bears = "Grizzly Bears"; // 2/2; lethal damage is 2 with no damage marked.
    // Amass creates a 0/0 Goblin Army and puts X +1/+1 counters on it.
    private static final String army = "Goblin Army Token";

    private void prepare(String sacrifice, String target) {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, sacrifice);
        addCard(Zone.HAND, playerA, bolg);
        addCard(Zone.BATTLEFIELD, playerB, target);
    }

    private void castBolgAndSacrifice(String sacrifice, String target) {
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolg);
        setChoice(playerA, true); // Choose to sacrifice another creature.
        setChoice(playerA, sacrifice); // Select the creature to sacrifice during the ETB trigger.
        addTarget(playerA, target); // Choose the damage target for the subsequent reflexive trigger.
    }

    private void finish() {
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
    }

    @Test
    public void sacrificeCreatesReflexiveTriggerAndAmassesExcess() {
        prepare(elemental, bears);
        castBolgAndSacrifice(elemental, bears);
        finish();

        assertPermanentCount(playerA, bolg, 1);
        assertGraveyardCount(playerA, elemental, 1);
        assertGraveyardCount(playerB, bears, 1);
        assertPermanentCount(playerA, army, 1);
        assertPowerToughness(playerA, army, 3, 3); // 5 damage minus 2 lethal = amass 3.
    }

    @Test
    public void usesSacrificedCreaturesBoostedPower() {
        prepare(elemental, bears);
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        // Target creature gets +3/+3 until end of turn, so Fire Elemental has 8 power when sacrificed.
        addCard(Zone.HAND, playerA, "Giant Growth");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Giant Growth", elemental, true);
        castBolgAndSacrifice(elemental, bears);
        finish();

        assertGraveyardCount(playerA, elemental, 1);
        assertGraveyardCount(playerB, bears, 1);
        assertPowerToughness(playerA, army, 6, 6); // Last-known power 8 minus 2 lethal = amass 6.
    }

    @Test
    public void dealsFullDamageToIndestructibleCreature() {
        // 0/1 with indestructible; survives lethal damage, allowing all 5 marked damage to be checked.
        String myr = "Darksteel Myr";
        prepare(elemental, myr);
        castBolgAndSacrifice(elemental, myr);
        finish();

        assertGraveyardCount(playerA, elemental, 1);
        assertPermanentCount(playerB, myr, 1);
        assertDamageReceived(playerB, myr, 5);
        assertPowerToughness(playerA, army, 4, 4); // 5 damage minus 1 lethal = amass 4.
    }

    @Test
    public void preventedDamageDoesNotAmass() {
        // 2/2; prevent all damage that would be dealt to Cho-Manno, Revolutionary.
        String choManno = "Cho-Manno, Revolutionary";
        prepare(elemental, choManno);
        castBolgAndSacrifice(elemental, choManno);
        finish();

        assertGraveyardCount(playerA, elemental, 1);
        assertPermanentCount(playerB, choManno, 1);
        assertDamageReceived(playerB, choManno, 0);
        assertPermanentCount(playerA, army, 0);
    }

    @Test
    public void lethalDamageWithoutExcessDoesNotAmass() {
        // 2/2; supplies exactly the 2 power needed to deal lethal damage to Grizzly Bears.
        String lion = "Silvercoat Lion";
        prepare(lion, bears);
        castBolgAndSacrifice(lion, bears);
        finish();

        assertGraveyardCount(playerA, lion, 1);
        assertGraveyardCount(playerB, bears, 1);
        assertPermanentCount(playerA, army, 0);
    }

    @Test
    public void decliningSacrificeDoesNotCreateReflexiveTrigger() {
        prepare(elemental, bears);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolg);
        setChoice(playerA, false);
        finish();

        assertPermanentCount(playerA, bolg, 1);
        assertPermanentCount(playerA, elemental, 1);
        assertPermanentCount(playerB, bears, 1);
        assertDamageReceived(playerB, bears, 0);
        assertPermanentCount(playerA, army, 0);
    }
}
