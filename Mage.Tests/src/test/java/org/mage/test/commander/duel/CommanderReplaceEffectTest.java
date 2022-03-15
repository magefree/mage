package org.mage.test.commander.duel;

import java.io.FileNotFoundException;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.GameException;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommanderDuelBase;

/**
 * This tests checks for problems that could arise from the possible commander
 * returns to the command zone option.
 *
 * @author LevelX2
 */
public class CommanderReplaceEffectTest extends CardTestCommanderDuelBase {

    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        setDecknamePlayerA("CommanderDuel_UW.dck"); // Commander = Daxos of Meletis
        setDecknamePlayerB("CommanderDuel_UW.dck"); // Commander = Daxos of Meletis
        return super.createNewGameAndPlayers();
    }

    @Test
    public void castCommanderWithFlash() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 6);
        addCard(Zone.HAND, playerB, "Phyrexian Rebirth", 1);

        // Daxos of Meletis can't be blocked by creatures with power 3 or greater.
        // Whenever Daxos of Meletis deals combat damage to a player, exile the top card of that player's library. You gain life equal to that card's converted mana cost.
        // Until end of turn, you may cast that card and you may spend mana as though it were mana of any color to cast it.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Daxos of Meletis");

        // Destroy all creatures, then put an X/X colorless Horror artifact creature token onto the battlefield, where X is the number of creatures destroyed this way.
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Phyrexian Rebirth");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Daxos of Meletis", 0);
        assertGraveyardCount(playerA, "Daxos of Meletis", 0);

        assertPermanentCount(playerB, "Phyrexian Horror Token", 1);
        assertPowerToughness(playerB, "Phyrexian Horror Token", 1, 1);
    }

    @Test
    public void saveCommanderWithGiftOfImmortality() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);

        // Enchant creature
        // When enchanted creature dies, return that card to the battlefield under its owner's control.
        // Return Gift of Immortality to the battlefield attached to that creature at the beginning of the next end step.
        addCard(Zone.HAND, playerA, "Gift of Immortality", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 6);
        addCard(Zone.HAND, playerB, "Phyrexian Rebirth", 1);

        // Daxos of Meletis can't be blocked by creatures with power 3 or greater.
        // Whenever Daxos of Meletis deals combat damage to a player, exile the top card of that player's library. You gain life equal to that card's converted mana cost. Until end of turn, you may cast that card and you may spend mana as though it were mana of any color to cast it.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Daxos of Meletis");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Gift of Immortality", "Daxos of Meletis");

        // Destroy all creatures, then put an X/X colorless Horror artifact creature token onto the battlefield, where X is the number of creatures destroyed this way.
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Phyrexian Rebirth");
        setChoice(playerA, false); // Let the commander go to graveyard because of Gift of Immortality

        setStopAt(3, PhaseStep.UPKEEP);
        execute();

        assertPermanentCount(playerB, "Phyrexian Horror Token", 1);
        assertPowerToughness(playerB, "Phyrexian Horror Token", 1, 1);

        assertPermanentCount(playerA, "Daxos of Meletis", 1);
        assertPermanentCount(playerA, "Gift of Immortality", 1);

    }

    // https://github.com/magefree/mage/issues/5905
    /* From the rulings of Soulherder:
        If a creature is exiled but ends up in another zone (most likely because
        it’s a player’s commander in the Commander variant), Soulherder’s first ability triggers.
    I exiled an opponents Commander, but Soulherder did not trigger.*/
    @Test
    public void soulherderAndExiledCommanders() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        // Whenever a creature is exiled from the battlefield, put a +1/+1 counter on Soulherder.
        // At the beginning of your end step, you may exile another target creature you control,
        // then return that card to the battlefield under its owner's control.
        addCard(Zone.HAND, playerA, "Soulherder", 1); // Creature {1}{W}{U}

        // Daxos of Meletis can't be blocked by creatures with power 3 or greater.
        // Whenever Daxos of Meletis deals combat damage to a player, exile the top card of that player's library. You gain life equal to that card's converted mana cost. Until end of turn, you may cast that card and you may spend mana as though it were mana of any color to cast it.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Daxos of Meletis");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Soulherder");

        setChoice(playerA, true); // Use Soulherder's triggered ability
        addTarget(playerA, "Daxos of Meletis");
        setChoice(playerA, true); // Move Daxos to command Zone

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertPermanentCount(playerA, "Soulherder", 1);
        assertPermanentCount(playerA, "Daxos of Meletis", 1);
        assertCommandZoneCount(playerA, "Daxos of Meletis", 0);
        assertPowerToughness(playerA, "Soulherder", 2, 2);

    }

    @Test
    public void soulherderAndDestroyedCommanders() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        // Whenever a creature is exiled from the battlefield, put a +1/+1 counter on Soulherder.
        // At the beginning of your end step, you may exile another target creature you control,
        // then return that card to the battlefield under its owner's control.
        addCard(Zone.HAND, playerA, "Soulherder", 1); // Creature {1}{W}{U}

        // Farm {2}{W} - Instant
        // Destroy target attacking or blocking creature.
        // Market {2}{U} - Sorcery
        // Aftermath (Cast this spell only from your graveyard. Then exile it.)
        // Draw two cards, then discard two cards.
        addCard(Zone.HAND, playerB, "Farm // Market", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 3);

        // Daxos of Meletis can't be blocked by creatures with power 3 or greater.
        // Whenever Daxos of Meletis deals combat damage to a player, exile the top card of that player's library. You gain life equal to that card's converted mana cost. Until end of turn, you may cast that card and you may spend mana as though it were mana of any color to cast it.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Daxos of Meletis");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Soulherder");

        setChoice(playerA, false); // Use Soulherder's triggered ability

        attack(3, playerA, "Daxos of Meletis");

        castSpell(3, PhaseStep.DECLARE_BLOCKERS, playerB, "Farm", "Daxos of Meletis");

        setChoice(playerA, true); // Move Daxos to command Zone

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 40);

        assertPermanentCount(playerA, "Soulherder", 1);
        assertHandCount(playerB, "Farm // Market", 0);
        assertGraveyardCount(playerB, "Farm // Market", 1);
        assertPermanentCount(playerA, "Daxos of Meletis", 0);
        assertCommandZoneCount(playerA, "Daxos of Meletis", 1);

        assertPowerToughness(playerA, "Soulherder", 1, 1);

    }
}
