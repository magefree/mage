package org.mage.test.cards.single.mic;

import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import mage.constants.PhaseStep;
import mage.constants.Zone;

/**
 * {@link mage.cards.l.LyndeCheerfulTormentor Lynde, Cheerful Tormentor}
 * {1}{U}{B}{R}
 * Legendary Creature — Human Warlock
 * Deathtouch
 * Whenever a Curse is put into your graveyard from the battlefield, return it to the battlefield attached to you at the beginning of the next end step.
 * At the beginning of your upkeep, you may attach a Curse attached to you to one of your opponents. If you do, draw two cards.
 * 2/4
 *
 * @author alexander-novo
 */
public class LyndeCheerfulTormentorTest extends CardTestPlayerBase {
    private static final String lynde = "Lynde, Cheerful Tormentor";

    /**
     * Reported Bug: https://github.com/magefree/mage/issues/10045
     * 
     * Lynde can bring back curses from any zone if they were moved from the graveyard to somewhere else (such as by
     * and effect that exiles a player's graveyard) before being brought back.
     */
    @Test
    public void onlyBringsBackCursesFromGraveyard() {
        // {3}{R}{R} - Curse
        String curse = "Curse of Bloodletting";
        // {1}{W} - Destroy target enchantment
        String disenchant = "Disenchant";
        // Land - When ETB, exile target player's graveyard
        String bog = "Bojuka Bog";
        String mountain = "Mountain";
        String plains = "Plains";

        // The necessary cards for the test
        addCard(Zone.HAND, playerA, curse);
        addCard(Zone.BATTLEFIELD, playerA, lynde);

        addCard(Zone.HAND, playerB, disenchant);
        addCard(Zone.HAND, playerB, bog);

        // The mana needed to cast those cards
        addCard(Zone.BATTLEFIELD, playerA, mountain, 5);
        addCard(Zone.BATTLEFIELD, playerB, plains, 2);

        // Player A plays the curse
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, curse, playerB);

        // Player B destroys the curse with disenchant.
        // There are two things to resolve - disenchant and Lynde's trigger
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, disenchant, curse);
        waitStackResolved(2, PhaseStep.PRECOMBAT_MAIN, 2);

        // The curse should have been destroyed and sent to the graveyard
        checkPermanentCount("after disenchant", 2, PhaseStep.PRECOMBAT_MAIN, playerA, curse, 0);
        checkGraveyardCount("after disenchant", 2, PhaseStep.PRECOMBAT_MAIN, playerA, curse, 1);

        // Player B then exiles player A's graveyard with bojuka bog
        playLand(2, PhaseStep.PRECOMBAT_MAIN, playerB, bog);
        addTarget(playerB, playerA);
        waitStackResolved(2, PhaseStep.PRECOMBAT_MAIN, 1);

        // Bojuka bog should have exiled the curse from the graveyard
        checkGraveyardCount("after bog", 2, PhaseStep.PRECOMBAT_MAIN, playerA, curse, 0);
        checkExileCount("after bog", 2, PhaseStep.PRECOMBAT_MAIN, playerA, curse, 1);

        // Fast forward to the end step. Make sure Lynde's delayed trigger goes off
        checkStackObject("at end step", 2, PhaseStep.END_TURN, playerA,
                "At the beginning of the next end step, return it to the battlefield attached to you",
                1);

        waitStackResolved(2, PhaseStep.END_TURN, 1);

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        // The curse should still be in exile, not on the battlefield
        assertPermanentCount(playerA, curse, 0);
        assertExileCount(playerA, curse, 1);
    }

    /**
     * When Lynde brings back a card which is a copy of a curse and enters again as a copy of a curse, the game mistakenly asks who to attach the curse to,
     * when it should automatically be attached to the controller of Lynde.
     */
    @Test
    public void copyCardTarget() {
        // {1}{R} - Curse. On upkeep, deal 1 damage to enchanted player
        String curse = "Curse of the Pierced Heart";
        // {2}{U}{U} - Enters as a copy of a curse
        String copy = "Clever Impersonator";
        // {1} - Sacrifice a permanent
        String sac = "Claws of Gix";
        String island = "Island";
        String mountain = "Mountain";

        // The necessary cards for the test
        addCard(Zone.HAND, playerA, curse);
        addCard(Zone.HAND, playerB, copy);
        addCard(Zone.BATTLEFIELD, playerB, lynde);
        addCard(Zone.BATTLEFIELD, playerB, sac);

        // Mana needed for player A to cast curse and player B to cast copy and sac
        addCard(Zone.BATTLEFIELD, playerA, mountain, 2);
        addCard(Zone.BATTLEFIELD, playerB, island, 5);

        // Player A plays the curse
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, curse, playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1);

        // Do not use Lynde's ability on upkeep
        setChoice(playerB, false);

        // Player B took one damage from Player A's curse
        checkLife("Turn 2 Upkeep", 2, PhaseStep.PRECOMBAT_MAIN, playerB, 20 - 1);

        // Player B casts the copy spell, choosing to copy the curse and enchant player A
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, copy);
        setChoice(playerB, true);
        setChoice(playerB, curse);
        setChoice(playerB, playerA.getName());
        waitStackResolved(2, PhaseStep.PRECOMBAT_MAIN, 1);

        // Now there should be two curses
        checkPermanentCount("After copy", 2, PhaseStep.PRECOMBAT_MAIN, playerA, curse, 1);
        checkPermanentCount("After copy", 2, PhaseStep.PRECOMBAT_MAIN, playerB, curse, 1);

        // Player B sacrifices their copy of the curse
        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{1}, Sacrifice a permanent");
        setChoice(playerB, curse);

        // Make sure Lynde triggers
        checkStackObject("After sac", 2, PhaseStep.PRECOMBAT_MAIN, playerB,
                "Whenever a Curse is put into your graveyard from the battlefield", 1);
        waitStackResolved(2, PhaseStep.PRECOMBAT_MAIN, 2);

        // Have copy come back as copy of curse. It should be attached to playerB automatically, and no choice should be offered on who to attach it to.
        setChoice(playerB, true);
        setChoice(playerB, curse);

        // At the beginning of turn 4, player B should have two triggers
        setChoice(playerB, "At the beginning of enchanted");
        setChoice(playerB, false);

        setStopAt(4, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, curse, 1);
        assertPermanentCount(playerB, curse, 1);

        assertLife(playerA, 20);
        assertLife(playerB, 20 - 2);
    }
}
