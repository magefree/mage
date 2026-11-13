package org.mage.test.cards.single.msc;

import mage.constants.MultiplayerAttackOption;
import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.FreeForAll;
import mage.game.Game;
import mage.game.GameException;
import mage.game.mulligan.MulliganType;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestMultiPlayerBase;

import java.io.FileNotFoundException;

/**
 * 508.1d
 * The active player checks each creature they control to see whether it's affected by any requirements (effects
 * that say a creature attacks if able, or that it attacks if some condition is met). If the number of requirements
 * that are being obeyed is fewer than the maximum possible number of requirements that could be obeyed without
 * disobeying any restrictions, the declaration of attackers is illegal. If a creature can't attack unless a player
 * pays a cost, that player is not required to pay that cost, even if attacking with that creature would increase the
 * number of requirements being obeyed. If a requirement that says a creature attacks if able during a certain turn
 * refers to a turn with multiple combat phases, the creature attacks if able during each declare attackers step in
 * that turn.
 *
 * @author DarioBarrabinoDev
 */
public class GalactusDevourerOfWorldsTest extends CardTestMultiPlayerBase {

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
        // When Galactus enters, exile target permanent.
        addCard(Zone.HAND, playerA, "Galactus, Devourer of Worlds"); // {10}
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 10);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion"); // lion 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Galactus, Devourer of Worlds");
        addTarget(playerA, "Silvercoat Lion"); // exile target

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Galactus, Devourer of Worlds", 1);
        assertExileCount(playerB, "Silvercoat Lion", 1);
    }

    @Test
    public void test_MustAttackOpponentWithMostLife() {
        // Insatiable Hunger -- Galactus attacks an opponent with the most life among your opponents each combat if able
        // unless you control a creature named Silver Surfer, Galactus's Herald.
        addCard(Zone.BATTLEFIELD, playerD, "Galactus, Devourer of Worlds"); // 12/12, flying, trample, indestructible
        setLife(playerB, 30);

        // turn 2: Galactus must attack playerB, the only opponent with the most life

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 30 - 12);
        assertLife(playerC, 20);
    }

    @Test
    public void test_MustAttackOpponentWithMostLife_TiedChooseFirst() {
        // Insatiable Hunger -- Galactus attacks an opponent with the most life among your opponents each combat if able
        // unless you control a creature named Silver Surfer, Galactus's Herald.
        addCard(Zone.BATTLEFIELD, playerD, "Galactus, Devourer of Worlds"); // 12/12, flying, trample, indestructible
        setLife(playerB, 30);
        setLife(playerC, 30);

        addTarget(playerD, playerB); // tied for most life, any of them can be attacked

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 30 - 12);
        assertLife(playerC, 30);
    }

    @Test
    public void test_MustAttackOpponentWithMostLife_TiedChooseSecond() {
        // Insatiable Hunger -- Galactus attacks an opponent with the most life among your opponents each combat if able
        // unless you control a creature named Silver Surfer, Galactus's Herald.
        addCard(Zone.BATTLEFIELD, playerD, "Galactus, Devourer of Worlds"); // 12/12, flying, trample, indestructible
        setLife(playerB, 30);
        setLife(playerC, 30);

        addTarget(playerD, playerC); // tied for most life, any of them can be attacked

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 30);
        assertLife(playerC, 30 - 12);
    }

    @Test
    public void test_MustAttackOpponentWithMostLife_TiedOneWithPropaganda() {
        // Insatiable Hunger -- Galactus attacks an opponent with the most life among your opponents each combat if able
        // unless you control a creature named Silver Surfer, Galactus's Herald.
        addCard(Zone.BATTLEFIELD, playerD, "Galactus, Devourer of Worlds"); // 12/12, flying, trample, indestructible
        //
        // Creatures can't attack you unless their controller pays {2} for each creature they control that's attacking you.
        addCard(Zone.BATTLEFIELD, playerB, "Propaganda");
        setLife(playerB, 30);
        setLife(playerC, 30);

        // attacking PlayerB requires a cost, so the only tied opponent left to attack is PlayerC

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 30);
        assertLife(playerC, 30 - 12);
    }

    @Test
    public void test_MustAttackOpponentWithMostLife_AllWithPropaganda() {
        // Insatiable Hunger -- Galactus attacks an opponent with the most life among your opponents each combat if able
        // unless you control a creature named Silver Surfer, Galactus's Herald.
        addCard(Zone.BATTLEFIELD, playerD, "Galactus, Devourer of Worlds"); // 12/12, flying, trample, indestructible
        //
        // Creatures can't attack you unless their controller pays {2} for each creature they control that's attacking you.
        addCard(Zone.BATTLEFIELD, playerB, "Propaganda");
        addCard(Zone.BATTLEFIELD, playerC, "Propaganda");
        setLife(playerB, 30);
        setLife(playerC, 30);

        // attacking any opponent with the most life requires a cost, so Galactus doesn't have to attack PlayerA instead

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 30);
        assertLife(playerC, 30);
    }

    @Test
    public void test_MustAttackOpponentWithMostLife_TiedAI() {
        // Insatiable Hunger -- Galactus attacks an opponent with the most life among your opponents each combat if able
        // unless you control a creature named Silver Surfer, Galactus's Herald.
        addCard(Zone.BATTLEFIELD, playerD, "Galactus, Devourer of Worlds"); // 12/12, flying, trample, indestructible
        setLife(playerB, 30);
        setLife(playerC, 30);

        // no choice set, so the AI picks one of the tied opponents

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        Assert.assertEquals("Galactus must attack one of the tied opponents",
                30 + 30 - 12, playerB.getLife() + playerC.getLife());
    }

    @Test
    public void test_YouControlSilverSurfer_NotForcedToAttack() {
        // Insatiable Hunger -- Galactus attacks an opponent with the most life among your opponents each combat if able
        // unless you control a creature named Silver Surfer, Galactus's Herald.
        addCard(Zone.BATTLEFIELD, playerD, "Galactus, Devourer of Worlds"); // 12/12, flying, trample, indestructible
        addCard(Zone.BATTLEFIELD, playerD, "Silver Surfer, Galactus's Herald"); // 4/5, flying
        setLife(playerB, 30);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 30);
        assertLife(playerC, 20);
    }

    @Test
    public void test_OpponentControlsSilverSurfer_StillForcedToAttack() {
        // Insatiable Hunger -- Galactus attacks an opponent with the most life among your opponents each combat if able
        // unless you control a creature named Silver Surfer, Galactus's Herald.
        addCard(Zone.BATTLEFIELD, playerD, "Galactus, Devourer of Worlds"); // 12/12, flying, trample, indestructible
        //
        addCard(Zone.BATTLEFIELD, playerB, "Silver Surfer, Galactus's Herald"); // 4/5, flying
        setLife(playerB, 30);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 30 - 12);
        assertLife(playerC, 20);
    }

    @Test
    public void test_MustAttackOpponentWithMostLife_LifeChangesBeforeCombat() {
        // Insatiable Hunger -- Galactus attacks an opponent with the most life among your opponents each combat if able
        // unless you control a creature named Silver Surfer, Galactus's Herald.
        addCard(Zone.BATTLEFIELD, playerD, "Galactus, Devourer of Worlds"); // 12/12, flying, trample, indestructible
        addCard(Zone.HAND, playerD, "Lightning Bolt"); // {R}, 3 damage to any target
        addCard(Zone.BATTLEFIELD, playerD, "Mountain");
        setLife(playerB, 30);
        setLife(playerC, 28);

        // most life is checked on declare attackers, so after the bolt it's playerC
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerD, "Lightning Bolt", playerB);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 30 - 3);
        assertLife(playerC, 28 - 12);
    }

    @Test
    public void test_SilverSurferLeavesBeforeCombat_ForcedToAttack() {
        // Insatiable Hunger -- Galactus attacks an opponent with the most life among your opponents each combat if able
        // unless you control a creature named Silver Surfer, Galactus's Herald.
        addCard(Zone.BATTLEFIELD, playerD, "Galactus, Devourer of Worlds"); // 12/12, flying, trample, indestructible
        addCard(Zone.BATTLEFIELD, playerD, "Silver Surfer, Galactus's Herald"); // 4/5, flying
        addCard(Zone.HAND, playerD, "Murder"); // {1}{B}{B}, destroy target creature
        addCard(Zone.BATTLEFIELD, playerD, "Swamp", 3);
        setLife(playerB, 30);

        // without Silver Surfer the requirement applies again in the same turn
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerD, "Murder", "Silver Surfer, Galactus's Herald");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerD, "Silver Surfer, Galactus's Herald", 1);
        assertLife(playerA, 20);
        assertLife(playerB, 30 - 12);
        assertLife(playerC, 20);
    }

    @Test
    public void test_MustAttackOpponentWithMostLife_NotTheirPlaneswalker() {
        // Insatiable Hunger -- Galactus attacks an opponent with the most life among your opponents each combat if able
        // unless you control a creature named Silver Surfer, Galactus's Herald.
        addCard(Zone.BATTLEFIELD, playerD, "Galactus, Devourer of Worlds"); // 12/12, flying, trample, indestructible
        //
        addCard(Zone.BATTLEFIELD, playerB, "Ajani Goldmane"); // planeswalker, loyalty 4
        setLife(playerB, 30);

        // Galactus must attack the opponent, so playerB's planeswalker isn't a valid choice

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 30 - 12);
        assertLife(playerC, 20);
        assertCounterCount(playerB, "Ajani Goldmane", CounterType.LOYALTY, 4);
    }

    @Test
    public void test_MustAttackOpponentWithMostLife_OpponentLeftTheGame() {
        // Insatiable Hunger -- Galactus attacks an opponent with the most life among your opponents each combat if able
        // unless you control a creature named Silver Surfer, Galactus's Herald.
        addCard(Zone.BATTLEFIELD, playerD, "Galactus, Devourer of Worlds"); // 12/12, flying, trample, indestructible
        setLife(playerB, 30);

        // playerB had the most life but left the game, so playerA and playerC are tied
        concede(1, PhaseStep.POSTCOMBAT_MAIN, playerB);
        checkPlayerInGame("playerB left", 2, PhaseStep.PRECOMBAT_MAIN, playerD, playerB, false);

        addTarget(playerD, playerC); // tied for most life, any of them can be attacked

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerC, 20 - 12);
    }
}
