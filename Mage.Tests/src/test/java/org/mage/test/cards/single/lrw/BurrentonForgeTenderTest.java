package org.mage.test.cards.single.lrw;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Kranken, LevelX2
 */

public class BurrentonForgeTenderTest extends CardTestPlayerBase {

    @Test
    public void testPreventDamageFromStack() {
        // Sacrifice Burrenton Forge-Tender: Prevent all damage a red source of your choice would deal this turn.
        addCard(Zone.BATTLEFIELD, playerA, "Burrenton Forge-Tender");
        addCard(Zone.BATTLEFIELD, playerA, "Soldier of the Pantheon");

        addCard(Zone.HAND, playerB, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", "Soldier of the Pantheon");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice {this}: Prevent all damage a red source of your choice would deal this turn.", TestPlayer.NO_TARGET, "Cast Lightning Bolt");
        playerA.addChoice("Lightning Bolt");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Lightning Bolt", 1);

        assertGraveyardCount(playerA, "Burrenton Forge-Tender", 1);
        assertPermanentCount(playerA, "Soldier of the Pantheon", 1);
    }

    @Test
    public void testPreventDamageFromFlametongueKavu() {
        addCard(Zone.BATTLEFIELD, playerA, "Burrenton Forge-Tender");
        addCard(Zone.BATTLEFIELD, playerA, "Soldier of the Pantheon");

        // When Flametongue Kavu enters the battlefield, it deals 4 damage to target creature.
        addCard(Zone.HAND, playerB, "Flametongue Kavu");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 4);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Flametongue Kavu");
        addTarget(playerB, "Soldier of the Pantheon");
        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice {this}: Prevent all damage a red source of your choice would deal this turn.",
                TestPlayer.NO_TARGET, "When {this} enters the battlefield, it deals 4 damage to target creature.");
        playerA.addChoice("Flametongue Kavu");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, "Flametongue Kavu", 1);

        assertGraveyardCount(playerA, "Burrenton Forge-Tender", 1);
        assertPermanentCount(playerA, "Soldier of the Pantheon", 1);
    }

    @Test
    public void testPreventDamageFromFlametongueKavuNotAfterCloudshift() {
        addCard(Zone.BATTLEFIELD, playerA, "Burrenton Forge-Tender");
        addCard(Zone.BATTLEFIELD, playerA, "Soldier of the Pantheon");

        // When Flametongue Kavu enters the battlefield, it deals 4 damage to target creature.
        addCard(Zone.HAND, playerB, "Flametongue Kavu");
        // Exile target creature you control, then return that card to the battlefield under your control.
        addCard(Zone.HAND, playerB, "Cloudshift");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 5);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Flametongue Kavu");
        addTarget(playerB, "Soldier of the Pantheon");
        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice {this}: Prevent all damage a red source of your choice would deal this turn.",
                TestPlayer.NO_TARGET, "When {this} enters the battlefield, it deals 4 damage to target creature.");
        playerA.addChoice("Flametongue Kavu");

        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Cloudshift", "Flametongue Kavu");
        addTarget(playerB, "Soldier of the Pantheon"); // now the damage may not be prevented because it's a new object

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Flametongue Kavu", 1);
        assertGraveyardCount(playerB, "Cloudshift", 1);

        assertGraveyardCount(playerA, "Burrenton Forge-Tender", 1);
        assertGraveyardCount(playerA, "Soldier of the Pantheon", 1);
    }


    @Test
    public void testPreventDamageFromToken() {
        addCard(Zone.BATTLEFIELD, playerA, "Burrenton Forge-Tender");
        addCard(Zone.BATTLEFIELD, playerA, "Soldier of the Pantheon");
        // Sacrifice Mogg Fanatic: Mogg Fanatic deals 1 damage to any target.
        addCard(Zone.BATTLEFIELD, playerA, "Mogg Fanatic");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        // Choose one - Return target creature you control and all Auras you control attached to it to their owner's hand; 
        // or destroy target creature and you lose life equal to its toughness; 
        // or return target creature card with converted mana cost 1 or less from your graveyard to the battlefield.
        addCard(Zone.HAND, playerA, "Orzhov Charm");

        // Kicker {5} (You may pay an additional as you cast this spell.)
        // Create a tokenonto the battlefield that's a copy of target creature. If Rite of Replication was kicked, put five of those tokens onto the battlefield instead.
        addCard(Zone.HAND, playerB, "Rite of Replication");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Rite of Replication", "Mogg Fanatic");
        setChoice(playerB, false); // no kicker

        castSpell(2, PhaseStep.BEGIN_COMBAT, playerA, "Orzhov Charm", "Mogg Fanatic");
        setModeChoice(playerA, "1");

        activateAbility(2, PhaseStep.END_COMBAT, playerA, "Sacrifice {this}: Prevent all damage a red source of your choice would deal this turn.");
        playerA.addChoice("Mogg Fanatic");

        activateAbility(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Sacrifice {this}: {this} deals 1 damage to ", "Soldier of the Pantheon");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Mogg Fanatic", 0);
        assertGraveyardCount(playerA, "Orzhov Charm", 1);
        assertHandCount(playerA, "Mogg Fanatic", 1);
        assertGraveyardCount(playerB, "Rite of Replication", 1);

        assertGraveyardCount(playerA, "Burrenton Forge-Tender", 1);
        assertPermanentCount(playerA, "Soldier of the Pantheon", 1);
    }


}