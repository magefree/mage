package org.mage.test.cards.single.cmr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */

public class GilanraCallerOfWirewoodTest extends CardTestPlayerBase {

    @Test
    public void test_EffectMustBeDiscardedOnNextTurn_SinglePlay_FromPool() {
        // https://github.com/magefree/mage/issues/7191
        removeAllCardsFromHand(playerA);

        // {T}: Add {G}. When you spend this mana to cast a spell with converted mana cost 6 or greater, draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Gilanra, Caller of Wirewood", 1);
        //
        // Angel of Deliverance
        addCard(Zone.HAND, playerA, "Angel of Deliverance", 1); // {6}{W}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 8 - 1);

        // play and use 1 trigger
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}. When");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Angel of Deliverance");
        checkStackSize("must have one trigger", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 1 + 1); // card + trigger

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Angel of Deliverance", 1);
        assertHandCount(playerA, 1); // draw from trigger
    }

    @Test
    public void test_EffectMustBeDiscardedOnNextTurn_SinglePlay_FromAutoPay() {
        // https://github.com/magefree/mage/issues/7191
        removeAllCardsFromHand(playerA);

        // {T}: Add {G}. When you spend this mana to cast a spell with converted mana cost 6 or greater, draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Gilanra, Caller of Wirewood", 1);
        //
        // Angel of Deliverance
        addCard(Zone.HAND, playerA, "Angel of Deliverance", 1); // {6}{W}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 8 - 1);

        // play and use 1 trigger (auto-pay must work)
        //activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}. When");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Angel of Deliverance");
        checkStackSize("must have one trigger", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 1 + 1); // card + trigger

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Angel of Deliverance", 1);
        assertHandCount(playerA, 1); // draw from trigger
    }

    @Test
    public void test_EffectMustBeDiscardedOnNextTurn_DoublePlay_ByTurns() {
        // https://github.com/magefree/mage/issues/7191
        removeAllCardsFromHand(playerA);

        // {T}: Add {G}. When you spend this mana to cast a spell with converted mana cost 6 or greater, draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Gilanra, Caller of Wirewood", 1);
        //
        // Angel of Deliverance
        addCard(Zone.HAND, playerA, "Angel of Deliverance", 1); // {6}{W}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 8 - 1);

        // turn 1 - activate mana, but don't use
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}. When");

        // turn 3 - activate mana and use it to cast card
        // possible bug: draw 2 cards intead 1
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}. When");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Angel of Deliverance");
        checkStackSize("must have one trigger", 3, PhaseStep.PRECOMBAT_MAIN, playerA, 1 + 1); // card + trigger

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Angel of Deliverance", 1);
        assertHandCount(playerA, 1 + 1); // draw from turn 3 and draw from trigger
    }

    @Test
    public void test_EffectMustBeDiscardedOnNextTurn_DoublePlay_ByUntap() {
        // https://github.com/magefree/mage/issues/7191
        removeAllCardsFromHand(playerA);

        // {T}: Add {G}. When you spend this mana to cast a spell with converted mana cost 6 or greater, draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Gilanra, Caller of Wirewood", 1);
        //
        // Angel of Deliverance
        addCard(Zone.HAND, playerA, "Angel of Deliverance", 1); // {6}{W}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 8 - 2);
        //
        addCard(Zone.HAND, playerA, "Burst of Energy", 1); // {W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        // activate mana two times (by untap - cast untap first to use normal mana)
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {W}");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Burst of Energy", "Gilanra, Caller of Wirewood");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}. When");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}. When");
        checkManaPool("must have 2 green", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "G", 2);

        // cast card and use 2 green mana -- must triggers two times
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Angel of Deliverance");
        checkStackSize("must have two trigger", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 1 + 2); // card + 2 trigger
        setChoice(playerA, "When you spend this mana"); // two triggers order

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Angel of Deliverance", 1);
        assertHandCount(playerA, 2); // 2 draws from 2 triggers
    }
}
