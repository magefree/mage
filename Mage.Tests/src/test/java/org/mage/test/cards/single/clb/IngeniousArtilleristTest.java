package org.mage.test.cards.single.clb;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class IngeniousArtilleristTest extends CardTestPlayerBase {

    @Test
    public void test_TwoArtifactsEnter_ThatMuchDamage() {
        // Whenever one or more artifacts enter the battlefield under you control, Ingenious Artillerist deals that much damage to each opponent.
        addCard(Zone.BATTLEFIELD, playerA, "Ingenious Artillerist");
        //
        // Create two 1/1 colorless Servo artifact creature tokens.
        addCard(Zone.HAND, playerA, "Servo Exhibition"); // {1}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Servo Exhibition");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Servo Token", 2);
        assertLife(playerB, 20 - 2);
    }

    @Test
    public void test_NonArtifactEnter_NoTrigger() {
        // Whenever one or more artifacts enter the battlefield under you control, Ingenious Artillerist deals that much damage to each opponent.
        addCard(Zone.BATTLEFIELD, playerA, "Ingenious Artillerist");
        //
        addCard(Zone.HAND, playerA, "Grizzly Bears"); // {1}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertLife(playerB, 20);
    }

    @Test
    public void test_OpponentArtifactsEnter_NoTrigger() {
        // Whenever one or more artifacts enter the battlefield under you control, Ingenious Artillerist deals that much damage to each opponent.
        addCard(Zone.BATTLEFIELD, playerA, "Ingenious Artillerist");
        //
        // Create two 1/1 colorless Servo artifact creature tokens.
        addCard(Zone.HAND, playerB, "Servo Exhibition"); // {1}{W}
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Servo Exhibition");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Servo Token", 2);
        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }

    @Test
    public void test_EachPlayerArtifactsEnter_OnlyYoursCount() {
        // Whenever one or more artifacts enter the battlefield under you control, Ingenious Artillerist deals that much damage to each opponent.
        addCard(Zone.BATTLEFIELD, playerA, "Ingenious Artillerist");
        //
        // {T}: Each player creates a colorless artifact token named Banana with "{T}, Sacrifice this artifact: Add {R} or {G}. You gain 2 life."
        addCard(Zone.BATTLEFIELD, playerA, "Kibo, Uktabi Prince");

        // all players create artifacts at once
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Each player creates");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Banana", 1);
        assertPermanentCount(playerB, "Banana", 1);
        assertLife(playerB, 20 - 1);
    }

    @Test
    public void test_Panharmonicon_TriggersAdditionalTime() {
        // Whenever one or more artifacts enter the battlefield under you control, Ingenious Artillerist deals that much damage to each opponent.
        addCard(Zone.BATTLEFIELD, playerA, "Ingenious Artillerist");
        //
        // If an artifact or creature entering the battlefield causes a triggered ability of a permanent you control to trigger, that ability triggers an additional time.
        addCard(Zone.BATTLEFIELD, playerA, "Panharmonicon");
        //
        // Create two 1/1 colorless Servo artifact creature tokens.
        addCard(Zone.HAND, playerA, "Servo Exhibition"); // {1}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        // two artifacts enter at once - one trigger + one additional trigger, 2 damage each
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Servo Exhibition");
        setChoice(playerA, "Whenever one or more artifacts"); // two triggers order

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Servo Token", 2);
        assertLife(playerB, 20 - 2 * 2);
    }

    @Test
    public void test_CurseOfOpulence_YouAttack() {
        // Whenever one or more artifacts enter the battlefield under you control, Ingenious Artillerist deals that much damage to each opponent.
        addCard(Zone.BATTLEFIELD, playerA, "Ingenious Artillerist");
        //
        // Enchant player
        // Whenever enchanted player is attacked, create a colorless artifact token named Gold.
        // Each opponent attacking that player does the same.
        addCard(Zone.HAND, playerA, "Curse of Opulence"); // {R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1); // 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Opulence", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // attack enchanted player - you create a Gold
        attack(1, playerA, "Grizzly Bears", playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Gold Token", 1);
        assertLife(playerB, 20 - 1 - 2);
    }

    @Test
    public void test_CurseOfOpulence_OpponentAttacks() {
        // Whenever one or more artifacts enter the battlefield under you control, Ingenious Artillerist deals that much damage to each opponent.
        addCard(Zone.BATTLEFIELD, playerA, "Ingenious Artillerist");
        //
        // Enchant player
        // Whenever enchanted player is attacked, create a colorless artifact token named Gold.
        // Each opponent attacking that player does the same.
        addCard(Zone.HAND, playerA, "Curse of Opulence"); // {R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 1); // 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Opulence", playerA);

        // opponent attacks enchanted player - you and the opponent create a Gold at once, only yours counts
        attack(2, playerB, "Grizzly Bears", playerA);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Gold Token", 1);
        assertPermanentCount(playerB, "Gold Token", 1);
        assertLife(playerA, 20 - 2);
        assertLife(playerB, 20 - 1);
    }
}
