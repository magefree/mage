package org.mage.test.combat;

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
 * 508.4
 * If a creature is put onto the battlefield attacking, its controller chooses which defending player, planeswalker
 * a defending player controls, or battle a defending player protects it’s attacking as it enters the battlefield
 * (unless the effect that put it onto the battlefield specifies what it’s attacking). Similarly, if an effect states
 * that a creature is attacking, its controller chooses which defending player, planeswalker a defending player
 * controls, or battle a defending player protects it’s attacking (unless the effect has already specified). Such
 * creatures are “attacking” but, for the purposes of trigger events and effects, they never “attacked.” They remain
 * attacking creatures until they’re removed from combat or the combat phase ends, whichever comes first.
 * <p>
 * 506.4
 * A permanent is removed from combat if it leaves the battlefield, if its controller or protector changes, if it
 * phases out, if an effect specifically removes it from combat, if it’s a planeswalker that’s being attacked and stops
 * being a planeswalker, if it’s a battle that’s being attacked and stops being a battle, or if it’s an attacking or
 * blocking creature that regenerates (see rule 701.19), stops being a creature, or becomes a battle. A creature that’s
 * removed from combat stops being an attacking, blocking, blocked, and/or unblocked creature. A planeswalker or battle
 * that’s removed from combat stops being attacked.
 *
 * @author JayDi85
 */
public class AttackingByEffectsTest extends CardTestMultiPlayerBase {

    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        Game game = new FreeForAll(MultiplayerAttackOption.MULTIPLE, RangeOfInfluence.ALL, MulliganType.GAME_DEFAULT.getMulligan(0), 20, 0);
        // Player order: A -> C -> B
        playerA = createPlayer(game, "PlayerA");
        playerB = createPlayer(game, "PlayerB");
        playerC = createPlayer(game, "PlayerC");
        return game;
    }

    @Test
    public void test_ReselectAttackedPlayer_Capricopian() {
        // This creature enters with X +1/+1 counters on it.
        // {2}: Put a +1/+1 counter on this creature, then you may reselect which player this creature is attacking.
        // Only the player this creature is attacking may activate this ability and only during the declare attackers step.
        addCard(Zone.HAND, playerA, "Capricopian"); // {X}{G}, 0/0
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2); // {2} for the ability

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Capricopian");
        setChoice(playerA, "X=2");

        // turns: A, C, B, A
        attack(4, playerA, "Capricopian", playerB);

        activateAbility(4, PhaseStep.DECLARE_ATTACKERS, playerB, "{2}: Put a +1/+1 counter");
        setChoice(playerB, true); // reselect the attacked player
        setChoice(playerB, "PlayerC"); // new attacked player

        setStrictChooseMode(true);
        setStopAt(4, PhaseStep.END_COMBAT);
        execute();

        assertPowerToughness(playerA, "Capricopian", 2 + 1, 2 + 1);
        assertTapped("Capricopian", true); // reselect doesn't untap the attacker
        assertLife(playerB, 20);
        assertLife(playerC, 20 - 3);
    }

    @Test
    public void test_GainControlAndAttacking_TahngarthFirstMate() {
        // Whenever an opponent attacks with one or more creatures, if Tahngarth is tapped, you may have that opponent
        // gain control of Tahngarth until end of combat. If you do, choose a player or planeswalker that opponent
        // is attacking. Tahngarth is attacking that player or planeswalker.
        addCard(Zone.BATTLEFIELD, playerA, "Tahngarth, First Mate"); // 5/5
        //
        addCard(Zone.BATTLEFIELD, playerC, "Grizzly Bears"); // bear 2/2, attacker

        // turns: A, C
        attack(1, playerA, "Tahngarth, First Mate", playerC); // Tahngarth becomes tapped

        attack(2, playerC, "Grizzly Bears", playerB);
        setChoice(playerA, true); // give control of Tahngarth
        setChoice(playerA, "PlayerB"); // attacked player for Tahngarth

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20 - 2 - 5);
        assertLife(playerC, 20 - 5);
    }
}
