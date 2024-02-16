package org.mage.test.cards.single.mh2;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class DauthiVoidwalkerTest extends CardTestPlayerBase {

    @Test
    public void test_FromBattlefield() {
        // If a card would be put into an opponent's graveyard from anywhere, instead exile it with a void counter on it.
        // {T}, Sacrifice Dauthi Voidwalker: Choose an exiled card an opponent owns with a void counter on it. You may play it this turn without paying its mana cost.
        addCard(Zone.BATTLEFIELD, playerA, "Dauthi Voidwalker", 1);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 1);
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");

        // kill B's creature and exile with void counter
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Balduvian Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkExileCount("after exile", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears", 1);

        // can play it for free
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Sacrifice");
        setChoice(playerA, "Balduvian Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after play", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears", 1);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_FromStack() {
        // If a card would be put into an opponent's graveyard from anywhere, instead exile it with a void counter on it.
        // {T}, Sacrifice Dauthi Voidwalker: Choose an exiled card an opponent owns with a void counter on it. You may play it this turn without paying its mana cost.
        addCard(Zone.BATTLEFIELD, playerA, "Dauthi Voidwalker", 1);
        //
        addCard(Zone.HAND, playerB, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");
        //
        // Counter target spell
        addCard(Zone.HAND, playerA, "Cancel"); // {1}{U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        // B try to cast and get counter
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", playerA);
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 3);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cancel", "Lightning Bolt", "Lightning Bolt");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        // countered bolt must be exiled and got void counter
        checkExileCount("after exile", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", 1);

        // can play it for free
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}, Sacrifice");
        setChoice(playerA, "Lightning Bolt");
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerB);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20 - 3);
    }
}
