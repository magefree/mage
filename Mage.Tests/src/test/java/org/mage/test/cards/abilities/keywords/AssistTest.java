package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

/**
 * @author JayDi85
 */

public class AssistTest extends CardTestPlayerBaseWithAIHelps {

    @Test
    public void test_Playable_NoMana_NoAssist() {
        // {7}{G}
        // Assist (Another player can pay up to {7} of this spell's cost.)
        addCard(Zone.HAND, playerA, "Charging Binox", 1);

        checkPlayableAbility("all", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Charging Binox", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_Playable_Mana_NoAssist() {
        // {7}{G}
        // Assist (Another player can pay up to {7} of this spell's cost.)
        addCard(Zone.HAND, playerA, "Charging Binox", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 8);

        checkPlayableAbility("all", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Charging Binox", true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_Playable_NoMana_Assist() {
        // {7}{G}
        // Assist (Another player can pay up to {7} of this spell's cost.)
        addCard(Zone.HAND, playerA, "Charging Binox", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 2); // assist pay

        checkPlayableAbility("all", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Charging Binox", true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_Playable_Mana_Assist() {
        // {7}{G}
        // Assist (Another player can pay up to {7} of this spell's cost.)
        addCard(Zone.HAND, playerA, "Charging Binox", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 8);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 2); // assist pay

        checkPlayableAbility("all", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Charging Binox", true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_Playable_ManaPartly_AssistPartly() {
        // {7}{G}
        // Assist (Another player can pay up to {7} of this spell's cost.)
        addCard(Zone.HAND, playerA, "Charging Binox", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 2); // assist pay

        checkPlayableAbility("all", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Charging Binox", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_PlayAssist_Manual() {
        // {7}{G}
        // Assist (Another player can pay up to {7} of this spell's cost.)
        addCard(Zone.HAND, playerA, "Charging Binox", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 8 - 2);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 2); // assist pay

        // disabled auto-payment and prepare mana pool to control payment
        disableManaAutoPayment(playerA);
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}", 6);
        // cast and assist
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Charging Binox");
        setChoice(playerA, "Green", 6); // normal pay x6
        setChoice(playerA, "Assist"); // activate assist
        addTarget(playerA, playerB); // player to assist
        setChoice(playerB, "X=2"); // can pay (auto-pay from B to A's mana pool as colorless x2)
        setChoice(playerA, "Colorless", 2 - 1); // assist pay x2, but 1 mana was unlocked in assist code (wtf)

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Charging Binox", 1);
    }

    @Test
    public void test_PlayAssist_AI_MustIgnoreAssist() {
        // {7}{G}
        // Assist (Another player can pay up to {7} of this spell's cost.)
        addCard(Zone.HAND, playerA, "Charging Binox", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 8 - 2);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 2); // assist pay

        // AI must ignore assist
        checkPlayableAbility("playable", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Charging Binox", true);
        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, "Charging Binox", 1);
        assertTappedCount("Forest", false, 8); // no mana used
    }
}