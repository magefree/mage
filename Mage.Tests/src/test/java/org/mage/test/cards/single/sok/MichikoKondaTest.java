package org.mage.test.cards.single.sok;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class MichikoKondaTest extends CardTestPlayerBase {

    private static final String michiko = "Michiko Konda, Truth Seeker"; // 2/2
    // Whenever a source an opponent controls deals damage to you, that player sacrifices a permanent.
    private static final String pinger = "Cunning Sparkmage"; // 0/1
    private static final String pingAbility = "{T}: {this} deals 1 damage to any target";
    private static final String devil = "Mayhem Devil"; // 3/3
    // Whenever a player sacrifices a permanent, Mayhem Devil deals 1 damage to any target.
    private static final String elves = "Elves of Deep Shadow";
    private static final String manaAbility = "{T}: Add {B}. {this} deals 1 damage to you.";
    private static final String zombie = "Walking Corpse"; // vanilla 2/2
    private static final String vampire = "Barony Vampire"; // vanilla 3/2

    @Test
    public void testOppDealsToYou() {
        addCard(Zone.BATTLEFIELD, playerA, michiko);
        addCard(Zone.BATTLEFIELD, playerA, devil);
        addCard(Zone.BATTLEFIELD, playerA, zombie);
        addCard(Zone.BATTLEFIELD, playerB, vampire);
        addCard(Zone.BATTLEFIELD, playerB, pinger);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, pingAbility, playerA);
        setChoice(playerB, vampire); // to sacrifice
        addTarget(playerA, devil); // resulting ping

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 19);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, 3);
        assertPermanentCount(playerB, 1);
        assertGraveyardCount(playerB, 1);
        assertPermanentCount(playerA, michiko, 1);
        assertDamageReceived(playerA, devil, 1);
        assertGraveyardCount(playerB, vampire, 1);
    }

    @Test
    public void testOppDealsToYours() {
        addCard(Zone.BATTLEFIELD, playerA, michiko);
        addCard(Zone.BATTLEFIELD, playerA, devil);
        addCard(Zone.BATTLEFIELD, playerA, zombie);
        addCard(Zone.BATTLEFIELD, playerB, vampire);
        addCard(Zone.BATTLEFIELD, playerB, pinger);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, pingAbility, zombie);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, 3);
        assertPermanentCount(playerB, 2);
        assertPermanentCount(playerA, michiko, 1);
        assertDamageReceived(playerA, zombie, 1);
    }

    @Test
    public void testYouDealToYourself() {
        addCard(Zone.BATTLEFIELD, playerA, michiko);
        addCard(Zone.BATTLEFIELD, playerA, devil);
        addCard(Zone.BATTLEFIELD, playerA, zombie);
        addCard(Zone.BATTLEFIELD, playerB, vampire);
        addCard(Zone.BATTLEFIELD, playerA, elves);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, manaAbility);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 19);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, 4);
        assertPermanentCount(playerB, 1);
    }

}
