package org.mage.test.cards.mana;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

/**
 * @author JayDi85
 */
public class InfiniteManaUsagesTest extends CardTestPlayerBaseWithAIHelps {

    @Test
    public void test_EnergyRefractor_Single_AddToPool() {
        // {2}: Add one mana of any color.
        addCard(Zone.BATTLEFIELD, playerA, "Energy Refractor", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        // make sure it works
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}: ");
        setChoice(playerA, "Red");
        checkManaPool("mana", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "R", 1);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
    }

    @Test
    public void test_EnergyRefractor_Multiple_AddToPool() {
        int cardsAmount = 20;

        // {2}: Add one mana of any color.
        addCard(Zone.BATTLEFIELD, playerA, "Energy Refractor", cardsAmount);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2 * cardsAmount);

        // make sure it works
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}: ");
        setChoice(playerA, "Red");
        checkManaPool("mana", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "R", 1);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
    }

    @Test
    public void test_EnergyRefractor_CastBears_Manual() {
        // {2}: Add one mana of any color.
        addCard(Zone.BATTLEFIELD, playerA, "Energy Refractor", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2 + 1);
        //
        addCard(Zone.HAND, playerA, "Grizzly Bears", 1); // {1}{G}

        // make sure it works
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");
        setChoice(playerA, "Green");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
    }

    @Test
    public void test_EnergyRefractor_CastBears_AI() {
        // possible bug: StackOverflowError on mana usage

        // {2}: Add one mana of any color.
        addCard(Zone.BATTLEFIELD, playerA, "Energy Refractor", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2 + 1);
        //
        addCard(Zone.HAND, playerA, "Grizzly Bears", 1); // {1}{G}

        // ai must see playable card and cast it
        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
    }
}
