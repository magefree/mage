package org.mage.test.cards.single.clb;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class LaezelsAcrobaticsTest extends CardTestPlayerBase {

    // {3}{W} Instant
    // Exile all nontoken creatures you control, then roll a d20.
    // 1—9 | Return those cards to the battlefield under their owner’s control at the beginning of the next end step.
    // 10—20 | Return those cards to the battlefield under their owner’s control, then exile them again. Return those cards to the battlefield under their owner’s control at the beginning of the next end step.
    private static final String acrobatics = "Lae'zel's Acrobatics";

    // {2}{W} Creature — Human Knight
    // First strike
    // When Attended Knight enters the battlefield, create a 1/1 white Soldier creature token.
    private static final String knight = "Attended Knight";
    // When Auramancer enters the battlefield, you may return target enchantment card from your graveyard to your hand.
    private static final String auramancer = "Auramancer";
    private static final String wings = "Nimbus Wings"; // aura
    // Whenever you roll one or more dice, Brazen Dwarf deals 1 damage to each opponent.
    private static final String dwarf = "Brazen Dwarf"; // not on the battlefield when die rolled, so should not trigger

    @Test
    public void testRollLow() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);
        addCard(Zone.BATTLEFIELD, playerA, auramancer);
        addCard(Zone.BATTLEFIELD, playerA, dwarf);
        addCard(Zone.HAND, playerA, knight);
        addCard(Zone.HAND, playerA, acrobatics);
        addCard(Zone.GRAVEYARD, playerA, wings, 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, knight);
        setDieRollResult(playerA, 9);
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, acrobatics);

        checkExileCount("Exiled", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, knight, 1);
        checkExileCount("Exiled", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, auramancer, 1);
        checkExileCount("Exiled", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, dwarf, 1);

        setChoice(playerA, "When {this} enters the battlefield, create"); // order triggers
        setChoice(playerA, true); // Auramancer: yes to return
        addTarget(playerA, wings); // enchantment to return

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertGraveyardCount(playerA, wings, 1);
        assertHandCount(playerA, wings, 1);
        assertPermanentCount(playerA, "Soldier Token", 2);
        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }

    @Test
    public void testRollHigh() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);
        addCard(Zone.BATTLEFIELD, playerA, auramancer);
        addCard(Zone.BATTLEFIELD, playerA, dwarf);
        addCard(Zone.HAND, playerA, knight);
        addCard(Zone.HAND, playerA, acrobatics);
        addCard(Zone.GRAVEYARD, playerA, wings, 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, knight);
        setDieRollResult(playerA, 10);
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, acrobatics);
        setChoice(playerA, "When {this} enters the battlefield, create"); // order triggers
        setChoice(playerA, true); // Auramancer: yes to return
        addTarget(playerA, wings); // enchantment to return

        checkExileCount("Exiled", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, knight, 1);
        checkExileCount("Exiled", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, auramancer, 1);
        checkExileCount("Exiled", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, dwarf, 1);

        setChoice(playerA, "When {this} enters the battlefield, create"); // order triggers
        setChoice(playerA, true); // Auramancer: yes to return
        addTarget(playerA, wings); // enchantment to return

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertGraveyardCount(playerA, wings, 0);
        assertHandCount(playerA, wings, 2);
        assertPermanentCount(playerA, "Soldier Token", 3);
        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }

}
