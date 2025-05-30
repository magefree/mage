package org.mage.test.cards.single.m3c;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class CoramTheUndertakerTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.c.CoramTheUndertaker Coram, the Undertaker} {1}{B}{R}{G}
     * Legendary Creature — Human Warrior
     * Coram, the Undertaker gets +X/+0, where X is the greatest power among creature cards in all graveyards.
     * Whenever Coram attacks, each player mills a card.
     * During each of your turns, you may play a land and cast a spell from among cards in graveyards that were put there from libraries this turn.
     * 0/5
     */
    private static final String coram = "Coram, the Undertaker";

    @Test
    public void test_DoubleMode() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.GRAVEYARD, playerA, "Taiga");
        addCard(Zone.GRAVEYARD, playerA, "Ornithopter");
        addCard(Zone.LIBRARY, playerB, "Plains");
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, coram);

        checkPT("1: Coram is 0/5", 1, PhaseStep.PRECOMBAT_MAIN, playerA, coram, 0, 5);

        attack(1, playerA, coram, playerB);

        checkPT("2: Coram is 2/5", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, coram, 2, 5);

        checkPlayableAbility("3: can not play Taiga, was not milled", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Play Taiga", false);
        checkPlayableAbility("3: can play Plains, was milled", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Play Plains", true);
        checkPlayableAbility("3: can not cast Ornithopter, was not milled", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Ornithopter", false);
        checkPlayableAbility("3: can cast Grizzly Bears, was milled", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Grizzly Bears", true);
        playLand(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Plains");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grizzly Bears");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, coram, 0, 5);
        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertPermanentCount(playerA, "Plains", 1);
        assertGraveyardCount(playerA, "Ornithopter", 1);
        assertGraveyardCount(playerA, "Taiga", 1);
    }

    @Test
    public void test_CantDoublePlayLand() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.LIBRARY, playerB, "Plains");
        addCard(Zone.LIBRARY, playerA, "Taiga");
        addCard(Zone.BATTLEFIELD, playerA, coram);
        addCard(Zone.BATTLEFIELD, playerA, "Exploration");

        attack(1, playerA, coram, playerB);

        checkPlayableAbility("1: can play Taiga, was milled", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Play Taiga", true);
        checkPlayableAbility("1: can play Plains, was milled", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Play Plains", true);
        playLand(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Plains");
        checkPlayableAbility("2: can not play Taiga, limit 1/turn", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Play Taiga", false);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Plains", 1);
        assertGraveyardCount(playerA, "Taiga", 1);
    }

    @Test
    public void test_CantDoubleCastSpell() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.LIBRARY, playerB, "Bear Cub");
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, coram);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);

        attack(1, playerA, coram, playerB);

        checkPlayableAbility("1: can cast Bear Cub, was milled", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Bear Cub", true);
        checkPlayableAbility("1: can cast Grizzly Bears, was milled", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Grizzly Bears", true);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Bear Cub");
        checkPlayableAbility("2: can not cast Grizzly Bears, limit 1/turn", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Grizzly Bears", false);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Bear Cub", 1);
        assertGraveyardCount(playerA, "Grizzly Bears", 1);
    }
}
