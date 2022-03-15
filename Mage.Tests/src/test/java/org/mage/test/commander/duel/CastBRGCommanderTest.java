package org.mage.test.commander.duel;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.GameException;
import mage.watchers.common.CommanderInfoWatcher;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommanderDuelBase;

import java.io.FileNotFoundException;

/**
 * @author LevelX2
 */
public class CastBRGCommanderTest extends CardTestCommanderDuelBase {

    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        // Flying
        // When you cast Prossh, Skyraider of Kher, put X 0/1 red Kobold creature tokens named Kobolds of Kher Keep onto the battlefield, where X is the amount of mana spent to cast Prossh.
        // Sacrifice another creature: Prossh gets +1/+0 until end of turn.
        setDecknamePlayerA("Power Hungry.dck"); // Commander = Prosssh, Skyrider of Kher {3}{B}{R}{G}
        setDecknamePlayerB("CommanderDuel_UW.dck"); // Commander = Daxos of Meletis {1}{W}{U}
        return super.createNewGameAndPlayers();
    }

    @Test
    public void castCommanderWithFlash() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);

        // The next creature card you cast this turn can be cast as though it had flash.
        // That spell can't be countered. That creature enters the battlefield with an additional +1/+1 counter on it.
        addCard(Zone.HAND, playerA, "Savage Summoning");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Savage Summoning");
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Prossh, Skyraider of Kher"); // 5/5
        setStopAt(1, PhaseStep.END_COMBAT);

        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();

        assertGraveyardCount(playerA, "Savage Summoning", 1);
        assertPermanentCount(playerA, "Prossh, Skyraider of Kher", 1);
        assertPowerToughness(playerA, "Prossh, Skyraider of Kher", 6, 6); // +1/+1 by Savage Summoning
        assertPermanentCount(playerA, "Kobolds of Kher Keep", 6);

    }

    /**
     * Activating Karn Liberated 's ultimate in an edh game (human OR ai) causes
     * all the command zones to lose their generals upon the new game restart
     */
    @Test
    public void castCommanderAfterKarnUltimate() {
        // +4: Target player exiles a card from their hand.
        // -3: Exile target permanent.
        // -14: Restart the game, leaving in exile all non-Aura permanent cards exiled with Karn Liberated. Then put those cards onto the battlefield under your control.
        addCard(Zone.BATTLEFIELD, playerA, "Karn Liberated", 1);
        addCard(Zone.HAND, playerA, "Silvercoat Lion", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+4: Target player", playerA);
        addTarget(playerA, "Silvercoat Lion");
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "+4: Target player", playerA);
        addTarget(playerA, "Silvercoat Lion");
        activateAbility(5, PhaseStep.PRECOMBAT_MAIN, playerA, "-14: Restart");

        setStopAt(5, PhaseStep.BEGIN_COMBAT);

        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();

        assertGraveyardCount(playerA, "Karn Liberated", 0);
        assertPermanentCount(playerA, "Silvercoat Lion", 2);
        assertCommandZoneCount(playerA, "Prossh, Skyraider of Kher", 1);
        assertCommandZoneCount(playerB, "Daxos of Meletis", 1);

    }

    /**
     * If the commander is exiled by Karn (and not returned to the command
     * zone), it needs to restart the game in play and not the command zone.
     */
    @Test
    public void testCommanderRestoredToBattlefieldAfterKarnUltimate() {
        // +4: Target player exiles a card from their hand.
        // -3: Exile target permanent.
        // -14: Restart the game, leaving in exile all non-Aura permanent cards exiled with Karn Liberated. Then put those cards onto the battlefield under your control.
        addCard(Zone.BATTLEFIELD, playerA, "Karn Liberated", 1); // Planeswalker (6)
        addCard(Zone.HAND, playerA, "Silvercoat Lion", 3);

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 1);

        // exile from hand 1
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+4: Target player", playerA);
        addTarget(playerA, "Silvercoat Lion");

        // prepare commander
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Daxos of Meletis");

        // exile from hand 2
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "+4: Target player", playerA);
        addTarget(playerA, "Silvercoat Lion");

        // attack and get commander damage
        attack(4, playerB, "Daxos of Meletis");

        // exile commander
        activateAbility(5, PhaseStep.PRECOMBAT_MAIN, playerA, "-3: Exile target permanent", "Daxos of Meletis");
        setChoice(playerB, false); // Move commander NOT to command zone

        // exile from hand 3
        activateAbility(7, PhaseStep.PRECOMBAT_MAIN, playerA, "+4: Target player", playerA);
        addTarget(playerA, "Silvercoat Lion");

        // restart game and return to battlefield 1x commander and 3x lions
        activateAbility(9, PhaseStep.PRECOMBAT_MAIN, playerA, "-14: Restart");
        // warning:
        // - karn restart code can clear some game data
        // - current version ignores a card's ZCC
        // - so ZCC are same after game restart and SBA can't react on commander new move
        // - logic can be changed in the future, so game can ask commander move again here
        //setChoice(playerB, false); // Move commander NOT to command zone

        setStopAt(9, PhaseStep.BEGIN_COMBAT);

        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();

        assertGraveyardCount(playerA, "Karn Liberated", 0);
        assertPermanentCount(playerA, "Silvercoat Lion", 3);
        assertCommandZoneCount(playerA, "Prossh, Skyraider of Kher", 1);
        assertCommandZoneCount(playerB, "Daxos of Meletis", 0);
        assertPermanentCount(playerA, "Daxos of Meletis", 1); // Karn brings back the cards under the control of Karn's controller

        CommanderInfoWatcher watcher = currentGame.getState().getWatcher(CommanderInfoWatcher.class, playerB.getCommandersIds().iterator().next());
        Assert.assertEquals("Watcher is reset to 0 commander damage", 0, watcher.getDamageToPlayer().size());
    }

    /**
     * Mogg infestation creates tokens "for each creature that died this way".
     * When a commander is moved to a command zone, it doesn't "die", and thus
     * should not create tokens.
     */
    @Test
    public void castMoggInfestation() {
        // Destroy all creatures target player controls. For each creature that died this way, create two 1/1 red Goblin creature tokens under that player's control.
        addCard(Zone.HAND, playerA, "Mogg Infestation", 1); // Sorcery {3}{R}{R}

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 1);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Daxos of Meletis");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Mogg Infestation");
        addTarget(playerA, playerB);
        setChoice(playerB, true); // Move commander to command zone

        setStopAt(3, PhaseStep.BEGIN_COMBAT);

        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();

        assertGraveyardCount(playerA, "Mogg Infestation", 1);
        assertCommandZoneCount(playerB, "Daxos of Meletis", 1);

        assertPermanentCount(playerB, "Goblin Token", 2);

    }

}
