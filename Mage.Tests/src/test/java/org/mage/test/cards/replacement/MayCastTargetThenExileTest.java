package org.mage.test.cards.replacement;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class MayCastTargetThenExileTest extends CardTestPlayerBase {

    private static final String spike = "Lava Spike"; // Sorcery {R}; deals 3 damage to target player or planeswalker

    @Test
    public void testWrexial() {
        String wrexial = "Wrexial, the Risen Deep"; // 5/8
        /* Whenever Wrexial, the Risen Deep deals combat damage to a player,
         * you may cast target instant or sorcery card from that player’s graveyard without paying its mana cost.
         * If that spell would be put into a graveyard, exile it instead.
         */
        addCard(Zone.BATTLEFIELD, playerA, wrexial);
        addCard(Zone.GRAVEYARD, playerB, spike);

        attack(1, playerA, wrexial, playerB);
        setChoice(playerA, true);
        addTarget(playerA, spike);
        addTarget(playerA, playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 5 - 3);
        assertExileCount(spike, 1);
    }

    @Test
    public void testForager() {
        String forager = "Halo Forager"; // 3/1
        /*
         * When Halo Forager enters the battlefield, you may pay {X}.
         * When you do, you may cast target instant or sorcery card with mana value X from a graveyard without paying its mana cost.
         * If that spell would be put into a graveyard, exile it instead.
         */
        addCard(Zone.HAND, playerA, forager);
        addCard(Zone.GRAVEYARD, playerB, spike);
        addCard(Zone.BATTLEFIELD, playerA, "Underground Sea", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, forager);
        setChoice(playerA, true);
        setChoice(playerA, "X=1");
        addTarget(playerA, spike);
        setChoice(playerA, true);
        addTarget(playerA, playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 3);
        assertExileCount(spike, 1);
    }

    @Test
    public void testVohar() {
        String vohar = "Vohar, Vodalian Desecrator"; // 1/2
        /*
         * {2}, Sacrifice Vohar, Vodalian Desecrator: You may cast target instant or sorcery card from your graveyard this turn.
         * If that spell would be put into your graveyard, exile it instead. Activate only as a sorcery.
         */
        addCard(Zone.BATTLEFIELD, playerA, vohar);
        addCard(Zone.GRAVEYARD, playerA, spike);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, Sacrifice");
        addTarget(playerA, spike);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, spike, playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 3);
        assertExileCount(spike, 1);
    }

    @Test
    public void testDreadhordeArcanist() {
        String arcanist = "Dreadhorde Arcanist"; // 1/3
        /*
         * Whenever Dreadhorde Arcanist attacks, you may cast target instant or sorcery card with mana value
         * less than or equal to Dreadhorde Arcanist’s power from your graveyard without paying its mana cost.
         * If that spell would be put into your graveyard, exile it instead.
         */
        addCard(Zone.BATTLEFIELD, playerA, arcanist);
        addCard(Zone.GRAVEYARD, playerA, spike);

        attack(1, playerA, arcanist, playerB);
        setChoice(playerA, true);
        addTarget(playerA, spike);
        addTarget(playerA, playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 1 - 3);
        assertExileCount(spike, 1);
    }

    @Test
    public void testChandra() {
        String chandra = "Chandra, Acolyte of Flame"; // 4 loyalty
        /*
         * −2: You may cast target instant or sorcery card with mana value 3 or less from your graveyard.
         * If that spell would be put into your graveyard, exile it instead.
         */
        addCard(Zone.BATTLEFIELD, playerA, chandra);
        addCard(Zone.GRAVEYARD, playerA, spike);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-2: You may cast");
        addTarget(playerA, spike);
        setChoice(playerA, true);
        addTarget(playerA, playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 3);
        assertExileCount(spike, 1);
    }

}
