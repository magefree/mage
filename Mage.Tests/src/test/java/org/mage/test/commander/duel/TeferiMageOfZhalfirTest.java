package org.mage.test.commander.duel;


import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.GameException;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommanderDuelBase;

import java.io.FileNotFoundException;

/**
 * @author LevelX2
 */

public class TeferiMageOfZhalfirTest extends CardTestCommanderDuelBase {

    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        setDecknamePlayerA("CommanderDuel_UW.dck"); // Commander = Daxos of Meletis
        return super.createNewGameAndPlayers();
    }

    @Test
    public void castCommanderWithFlash() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);

        addCard(Zone.BATTLEFIELD, playerA, "Teferi, Mage of Zhalfir");

        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Daxos of Meletis");
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, "Daxos of Meletis", 1);
    }

    @Test
    public void testCommanderDamage() {
        setLife(playerA, 20);
        setLife(playerB, 20);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        // Enchant creature
        // Enchanted creature gets +4/+4, has flying and first strike, and is an Angel in addition to its other types.
        // When enchanted creature dies, return Angelic Destiny to its owner's hand.
        addCard(Zone.HAND, playerA, "Angelic Destiny");

        addCard(Zone.BATTLEFIELD, playerA, "Teferi, Mage of Zhalfir");

        // Daxos of Meletis can't be blocked by creatures with power 3 or greater.
        // Whenever Daxos of Meletis deals combat damage to a player, exile the top card of that player's library.
        // You gain life equal to that card's converted mana cost. Until end of turn, you may cast that card
        // and you may spend mana as though it were mana of any color to cast it.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Daxos of Meletis");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Angelic Destiny", "Daxos of Meletis");

        attack(3, playerA, "Daxos of Meletis");
        attack(5, playerA, "Daxos of Meletis");
        attack(7, playerA, "Daxos of Meletis");
        attack(9, playerA, "Daxos of Meletis");
        checkPT("before lost", 9, PhaseStep.PRECOMBAT_MAIN, playerA, "Daxos of Meletis", 6, 6);

        setStrictChooseMode(true);
        setStopAt(9, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Daxos of Meletis", 1);
        assertPowerToughness(playerA, "Daxos of Meletis", 6, 6); // no effects removes after game over -- users and tests can get last game state with all affected effects

        assertWonTheGame(playerA);
        assertLostTheGame(playerB);
    }
}