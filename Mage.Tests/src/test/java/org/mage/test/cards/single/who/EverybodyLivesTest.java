package org.mage.test.cards.single.who;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.LoseGameSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeAllPlayersEffect;
import mage.abilities.effects.common.WinGameSourceControllerEffect;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4Players;

public class EverybodyLivesTest extends CardTestCommander4Players {

    /*
    Everybody Lives!
    {1}{W}
    Instant

    All creatures gain hexproof and indestructible until end of turn. Players gain hexproof until end of turn.
    Players can’t lose life this turn and players can’t lose the game or win the game this turn.
     */
    private static final String everybodyLives = "Everybody Lives!";

    @Test
    public void testEverybodyLivesCantLoseLifeAndHexproof() {
        setStrictChooseMode(true);

        addCustomCardWithAbility("lose life effect", playerA, new SimpleActivatedAbility(
                new LoseLifeAllPlayersEffect(20),
                new ManaCostsImpl<>(""))
        );
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.HAND, playerA, everybodyLives);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, everybodyLives, true);
        checkPlayableAbility("Can't cast lightning bolt", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Lightning Bolt", false);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "each player");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertLife(playerC, 20);
        assertLife(playerD, 20);
    }

    @Test
    public void testEverybodyLivesCantLoseGame() {
        setStrictChooseMode(true);

        addCustomCardWithAbility("lose game effect", playerA, new SimpleActivatedAbility(
                new LoseGameSourceControllerEffect(),
                new ManaCostsImpl<>(""))
        );
        addCard(Zone.HAND, playerA, everybodyLives);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, everybodyLives, true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "you lose");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHasNotLostTheGame(playerA);

    }

    @Test
    public void testEverybodyLivesCantWinGame() {
        setStrictChooseMode(true);

        addCustomCardWithAbility("win game effect", playerA, new SimpleActivatedAbility(
                new WinGameSourceControllerEffect(),
                new ManaCostsImpl<>(""))
        );
        addCard(Zone.HAND, playerA, everybodyLives);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, everybodyLives, true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "you win");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHasNotWonTheGame(playerA);

    }

}
