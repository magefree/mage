package org.mage.test.cards.single.msc;

import mage.constants.MultiplayerAttackOption;
import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.constants.Zone;
import mage.game.FreeForAll;
import mage.game.Game;
import mage.game.GameException;
import mage.game.mulligan.MulliganType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestMultiPlayerBase;

import java.io.FileNotFoundException;

/**
 * @author DarioBarrabinoDev
 */
public class GalactusDevourerOfWorldsTest extends CardTestMultiPlayerBase {

    /**
     * Galactus, Devourer of Worlds {10}
     * Legendary Creature - Elder Alien 12/12
     * Flying, trample, indestructible
     * When Galactus enters, exile target permanent.
     * Insatiable Hunger -- Galactus attacks an opponent with the most life among your opponents each combat if able
     * unless you control a creature named Silver Surfer, Galactus's Herald.
     */
    private static final String galactus = "Galactus, Devourer of Worlds";

    /**
     * Creatures can't attack you unless their controller pays {2} for each creature they control that's attacking you.
     */
    private static final String propaganda = "Propaganda";

    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        Game game = new FreeForAll(MultiplayerAttackOption.MULTIPLE, RangeOfInfluence.ALL, MulliganType.GAME_DEFAULT.getMulligan(0), 20, 7);
        // Player order: A -> D -> C -> B
        playerA = createPlayer(game, "PlayerA");
        playerB = createPlayer(game, "PlayerB");
        playerC = createPlayer(game, "PlayerC");
        playerD = createPlayer(game, "PlayerD");
        return game;
    }

    @Test
    public void test_EntersExileTargetPermanent() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, galactus);
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 10);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, galactus);
        addTarget(playerA, "Silvercoat Lion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, galactus, 1);
        assertExileCount(playerB, "Silvercoat Lion", 1);
    }

    @Test
    public void test_MustAttackOpponentWithMostLife() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerD, galactus);
        setLife(playerB, 30);

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 30 - 12);
        assertLife(playerC, 20);
    }

    @Test
    public void test_MustAttackOpponentWithMostLife_TiedChooseFirst() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerD, galactus);
        setLife(playerB, 30);
        setLife(playerC, 30);

        addTarget(playerD, playerB); // tied for most life, any of them can be attacked

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 30 - 12);
        assertLife(playerC, 30);
    }

    @Test
    public void test_MustAttackOpponentWithMostLife_TiedChooseSecond() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerD, galactus);
        setLife(playerB, 30);
        setLife(playerC, 30);

        addTarget(playerD, playerC); // tied for most life, any of them can be attacked

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 30);
        assertLife(playerC, 30 - 12);
    }

    @Test
    public void test_MustAttackOpponentWithMostLife_TiedOneWithPropaganda() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerD, galactus);
        addCard(Zone.BATTLEFIELD, playerB, propaganda);
        setLife(playerB, 30);
        setLife(playerC, 30);

        // attacking PlayerB requires a cost, so the only tied opponent left to attack is PlayerC

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 30);
        assertLife(playerC, 30 - 12);
    }
}
