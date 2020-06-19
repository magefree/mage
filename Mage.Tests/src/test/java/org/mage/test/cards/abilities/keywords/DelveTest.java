package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

/**
 * @author JayDi85
 */

public class DelveTest extends CardTestPlayerBaseWithAIHelps {

    // no simple playable tests for delve, it's same as ConvokeTest

    @Test
    public void test_PlayDelve_Manual() {
        // {4}{U}{U} creature
        // Delve (Each card you exile from your graveyard while casting this spell pays for {1}.)
        addCard(Zone.HAND, playerA, "Ethereal Forager", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.GRAVEYARD, playerA, "Balduvian Bears", 4); // delve pay

        // use special action to pay (need disabled auto-payment and prepared mana pool)
        disableManaAutoPayment(playerA);
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 2);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ethereal Forager");
        setChoice(playerA, "Blue"); // pay 1
        setChoice(playerA, "Blue"); // pay 2
        // delve can be payed in test only by one card
        setChoice(playerA, "Exile cards");
        setChoice(playerA, "Balduvian Bears"); // pay 3 as delve
        setChoice(playerA, "Exile cards");
        setChoice(playerA, "Balduvian Bears"); // pay 4 as delve
        setChoice(playerA, "Exile cards");
        setChoice(playerA, "Balduvian Bears"); // pay 5 as delve
        setChoice(playerA, "Exile cards");
        setChoice(playerA, "Balduvian Bears"); // pay 6 as delve

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Ethereal Forager", 1);
    }

    @Test
    public void test_PlayDelve_AI_AutoPay() {
        // {4}{U}{U} creature
        // Delve (Each card you exile from your graveyard while casting this spell pays for {1}.)
        addCard(Zone.HAND, playerA, "Ethereal Forager", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.GRAVEYARD, playerA, "Balduvian Bears", 4); // delve pay

        // AI must use special actions to pay as delve
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ethereal Forager");

        //setStrictChooseMode(true); AI must choose targets
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Ethereal Forager", 1);
    }

    @Test
    public void test_PlayDelve_AI_FullPlay() {
        // {4}{U}{U} creature
        // Delve (Each card you exile from your graveyard while casting this spell pays for {1}.)
        addCard(Zone.HAND, playerA, "Ethereal Forager", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.GRAVEYARD, playerA, "Balduvian Bears", 4); // delve pay

        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Ethereal Forager", 1);
    }
}