package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author weirddan455
 */
public class WardTest extends CardTestPlayerBase {

    @Test
    public void wardMultipleAbilities() {
        addCard(Zone.HAND, playerA, "Solitude"); // https://github.com/magefree/mage/issues/8378 Test that ward counters correct ability
        addCard(Zone.HAND, playerA, "Healer's Hawk"); // Card to pitch to Solitude
        addCard(Zone.BATTLEFIELD, playerB, "Waterfall Aerialist");  // Flying, Ward 2
        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Solitude");
        setChoice(playerA, "Cast with Evoke alternative cost: Exile a white card from your hand (source: Solitude");
        setChoice(playerA, "Healer's Hawk");
        setChoice(playerA, "When {this} enters, exile up to one other target creature"); // Put exile trigger on the stack first (evoke trigger will resolve first)
        addTarget(playerA, "Waterfall Aerialist");
        setChoice(playerA, "No"); // Do not pay Ward cost
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount("Healer's Hawk", 1);
        assertGraveyardCount(playerA, "Solitude", 1);
        assertPermanentCount(playerB, "Waterfall Aerialist", 1);
    }

    @Test
    public void wardPanharmonicon() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Panharmonicon");
        addCard(Zone.BATTLEFIELD, playerA, "Young Red Dragon");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 7);
        addCard(Zone.BATTLEFIELD, playerB, "Roaming Throne");
        addCard(Zone.HAND, playerA, "Scourge of Valkas");

        setChoice(playerB, "Dragon");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Scourge of Valkas");
        setChoice(playerA, "Whenever {this} or another Dragon");
        addTarget(playerA, "Roaming Throne");
        addTarget(playerA, "Roaming Throne");
        setChoice(playerB, "ward {2}");
        setChoice(playerA, "Yes");
        setChoice(playerA, "No");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertPermanentCount(playerB, "Roaming Throne", 1);
        assertDamageReceived(playerB, "Roaming Throne", 2);
    }

    @Test
    public void wardPanharmoniconCounter() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Panharmonicon");
        addCard(Zone.BATTLEFIELD, playerA, "Young Red Dragon");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerB, "Roaming Throne");
        addCard(Zone.HAND, playerA, "Scourge of Valkas");

        setChoice(playerB, "Dragon");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Scourge of Valkas");
        setChoice(playerA, "Whenever {this} or another Dragon");
        addTarget(playerA, "Roaming Throne");
        addTarget(playerA, "Roaming Throne");
        setChoice(playerB, "ward {2}");
        setChoice(playerA, "No");
        setChoice(playerA, "No");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertPermanentCount(playerB, "Roaming Throne", 1);
        assertDamageReceived(playerB, "Roaming Throne", 0);
    }
}
