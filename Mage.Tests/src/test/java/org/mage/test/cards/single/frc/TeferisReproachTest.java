package org.mage.test.cards.single.frc;

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

/**
 * 702.16j
 * “Protection from everything” is a variant of the protection ability. A permanent or player with protection from
 * everything has protection from each object regardless of that object’s characteristic values. Such a permanent or
 * player can’t be targeted by spells or abilities and can’t be enchanted by Auras. Such a permanent can’t be equipped
 * by Equipment, fortified by Fortifications, or blocked by creatures. All damage that would be dealt to such a
 * permanent or player is prevented.
 * <p>
 * 702.26b
 * If a permanent phases out, its status changes to “phased out.” Except for rules and effects that specifically
 * mention phased-out permanents, a phased-out permanent is treated as though it does not exist. It can’t affect or be
 * affected by anything else in the game. A permanent that phases out is removed from combat. (See rule 506.4.)
 * <p>
 * 502.1
 * First, all phased-in permanents with phasing that the active player controls phase out, and all phased-out
 * permanents that the active player controlled when they phased out phase in. This all happens simultaneously. This
 * turn-based action doesn’t use the stack. See rule 702.26, “Phasing.”
 * <p>
 * 608.2b
 * If the spell or ability specifies targets, it checks whether the targets are still legal. A target that’s no longer
 * in the zone it was in when it was targeted is illegal. Other changes to the game state may cause a target to no
 * longer be legal; for example, its characteristics may have changed or an effect may have changed the text of the
 * spell. If the source of an ability has left the zone it was in, its last known information is used during this
 * process. If all its targets, for every instance of the word “target,” are now illegal, the spell or ability doesn’t
 * resolve. It’s removed from the stack and, if it’s a spell, put into its owner’s graveyard. Otherwise, the spell or
 * ability will resolve normally. Illegal targets, if any, won’t be affected by parts of a resolving spell’s effect for
 * which they’re illegal. Other parts of the effect for which those targets are not illegal may still affect them. If
 * the spell or ability creates any continuous effects that affect game rules (see rule 613.11), those effects don’t
 * apply to illegal targets. If part of the effect requires information about an illegal target, it fails to determine
 * any such information. Any part of the effect that requires that information won’t happen.
 *
 * @author DarioBarrabinoDev
 */
public class TeferisReproachTest extends CardTestMultiPlayerBase {

    @Override
    protected Game createNewGameAndPlayers() throws GameException {
        Game game = new FreeForAll(MultiplayerAttackOption.MULTIPLE, RangeOfInfluence.ALL, MulliganType.GAME_DEFAULT.getMulligan(0), 20, 7);
        // Player order: A -> D -> C -> B
        playerA = createPlayer(game, "PlayerA");
        playerB = createPlayer(game, "PlayerB");
        playerC = createPlayer(game, "PlayerC");
        playerD = createPlayer(game, "PlayerD");
        return game;
    }

    @Test
    public void test_ProtectionLifeAndPhasingUntilThatPlayersNextTurn() {
        // Choose target opponent. Until that player's next turn, they gain protection from everything and their
        // life total can't change. All nonland permanents they control phase out.
        // Exile Teferi's Reproach.
        addCard(Zone.HAND, playerA, "Teferi's Reproach"); // {2}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        //
        addCard(Zone.HAND, playerA, "Blood Tithe"); // {3}{B}, each opponent loses 3 life, you gain life equal to the life lost
        addCard(Zone.BATTLEFIELD, playerA, "Pestilence"); // {B}: 1 damage to each creature and each player
        addCard(Zone.BATTLEFIELD, playerA, "Darksteel Myr"); // indestructible 0/1, keeps Pestilence on the battlefield
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 9);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // bear 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Glorious Anthem"); // creatures you control get +1/+1
        addCard(Zone.BATTLEFIELD, playerB, "Forest");

        // turn 1 (A): B's nonland permanents phase out, B can't lose life or be dealt damage
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Teferi's Reproach", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("bears phased out", 1, PhaseStep.PRECOMBAT_MAIN, playerA, playerB, "Grizzly Bears", 0);
        checkPermanentCount("anthem phased out", 1, PhaseStep.PRECOMBAT_MAIN, playerA, playerB, "Glorious Anthem", 0);
        checkPermanentCount("land stays", 1, PhaseStep.PRECOMBAT_MAIN, playerA, playerB, "Forest", 1);
        checkExileCount("reproach exiled", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Teferi's Reproach", 1);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Blood Tithe");
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{B}: {this} deals 1 damage to each creature and each player.");
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);

        // turn 3 (C): still protected
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{B}: {this} deals 1 damage to each creature and each player.");
        waitStackResolved(3, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("turn 3 - bears still phased out", 3, PhaseStep.PRECOMBAT_MAIN, playerA, playerB, "Grizzly Bears", 0);

        // turn 4 (B): effect is over and permanents phased in
        checkPermanentCount("turn 4 - bears phased in", 4, PhaseStep.UPKEEP, playerA, playerB, "Grizzly Bears", 1);
        checkPermanentCount("turn 4 - anthem phased in", 4, PhaseStep.UPKEEP, playerA, playerB, "Glorious Anthem", 1);
        activateAbility(4, PhaseStep.PRECOMBAT_MAIN, playerA, "{B}: {this} deals 1 damage to each creature and each player.");

        setStrictChooseMode(true);
        setStopAt(4, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 1);
        assertLife(playerC, 20 - 3 - 3);
        assertLife(playerD, 20 - 3 - 3);
        assertLife(playerA, 20 + 6 - 3);
        assertPowerToughness(playerB, "Grizzly Bears", 3, 3);
        assertExileCount(playerA, "Teferi's Reproach", 1);
    }

    @Test
    public void test_CastDuringThatPlayersTurn_LastsUntilTheirNextTurn() {
        // Choose target opponent. Until that player's next turn, they gain protection from everything and their
        // life total can't change. All nonland permanents they control phase out.
        // Exile Teferi's Reproach.
        addCard(Zone.HAND, playerA, "Teferi's Reproach"); // {2}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Pestilence"); // {B}: 1 damage to each creature and each player
        addCard(Zone.BATTLEFIELD, playerA, "Darksteel Myr"); // indestructible 0/1, keeps Pestilence on the battlefield
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        //
        addCard(Zone.BATTLEFIELD, playerD, "Grizzly Bears"); // bear 2/2

        // turn 2 (D): cast on D during D's own turn
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Teferi's Reproach", playerD);
        waitStackResolved(2, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("bears phased out", 2, PhaseStep.PRECOMBAT_MAIN, playerA, playerD, "Grizzly Bears", 0);

        // turn 5 (A): caster's next turn doesn't end it
        activateAbility(5, PhaseStep.PRECOMBAT_MAIN, playerA, "{B}: {this} deals 1 damage to each creature and each player.");
        waitStackResolved(5, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("turn 5 - bears still phased out", 5, PhaseStep.PRECOMBAT_MAIN, playerA, playerD, "Grizzly Bears", 0);

        // turn 6 (D): D's next turn ends it
        checkPermanentCount("turn 6 - bears phased in", 6, PhaseStep.UPKEEP, playerA, playerD, "Grizzly Bears", 1);
        activateAbility(6, PhaseStep.PRECOMBAT_MAIN, playerA, "{B}: {this} deals 1 damage to each creature and each player.");

        setStrictChooseMode(true);
        setStopAt(6, PhaseStep.END_TURN);
        execute();

        assertLife(playerD, 20 - 1);
        assertLife(playerA, 20 - 2);
        assertLife(playerB, 20 - 2);
        assertLife(playerC, 20 - 2);
        assertPermanentCount(playerD, "Grizzly Bears", 1);
    }

    @Test
    public void test_SpellTargetingThatPlayerFizzles() {
        // Choose target opponent. Until that player's next turn, they gain protection from everything and their
        // life total can't change. All nonland permanents they control phase out.
        // Exile Teferi's Reproach.
        addCard(Zone.HAND, playerA, "Teferi's Reproach"); // {2}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        //
        addCard(Zone.HAND, playerC, "Lightning Bolt"); // {R}, 3 damage to any target
        addCard(Zone.BATTLEFIELD, playerC, "Mountain");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerC, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Teferi's Reproach", "targetPlayer=PlayerB", "Lightning Bolt"); // in response to the bolt

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20);
        assertGraveyardCount(playerC, "Lightning Bolt", 1); // fizzled, B has protection from everything
        assertExileCount(playerA, "Teferi's Reproach", 1);
    }

    @Test
    public void test_AttackingThatPlayer_CombatDamagePrevented() {
        // Choose target opponent. Until that player's next turn, they gain protection from everything and their
        // life total can't change. All nonland permanents they control phase out.
        // Exile Teferi's Reproach.
        addCard(Zone.HAND, playerA, "Teferi's Reproach"); // {2}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        //
        addCard(Zone.BATTLEFIELD, playerC, "Silvercoat Lion"); // lion 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Teferi's Reproach", playerB);

        // turn 3 (C): B can still be attacked, but the combat damage is prevented
        attack(3, playerC, "Silvercoat Lion", playerB);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertTapped("Silvercoat Lion", true); // attacked
        assertLife(playerB, 20);
    }

    @Test
    public void test_LifeGainHasNoEffect_RestOfSpellResolves() {
        // Choose target opponent. Until that player's next turn, they gain protection from everything and their
        // life total can't change. All nonland permanents they control phase out.
        // Exile Teferi's Reproach.
        addCard(Zone.HAND, playerA, "Teferi's Reproach"); // {2}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        //
        addCard(Zone.HAND, playerB, "Revitalize"); // {1}{W}, instant, you gain 3 life, draw a card
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Teferi's Reproach", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // turn 1 (A): B gains no life, but still draws
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Revitalize");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Revitalize", 1);
        assertLife(playerB, 20);
        assertHandCount(playerB, 1); // draw from Revitalize
    }

    @Test
    public void test_AuraAttachedToThatPlayerFallsOff() {
        // Choose target opponent. Until that player's next turn, they gain protection from everything and their
        // life total can't change. All nonland permanents they control phase out.
        // Exile Teferi's Reproach.
        addCard(Zone.HAND, playerA, "Teferi's Reproach"); // {2}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        //
        addCard(Zone.HAND, playerA, "Curse of the Pierced Heart"); // {1}{R}, aura curse, enchant player
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of the Pierced Heart", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("curse on B", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of the Pierced Heart", 1);

        // the curse is A's permanent, so it doesn't phase out, it goes to the graveyard as a state-based action
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Teferi's Reproach", playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Curse of the Pierced Heart", 0);
        assertGraveyardCount(playerA, "Curse of the Pierced Heart", 1);
    }
}
