
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

        assertPermanentCount(playerB, "Horror", 1);
        assertPowerToughness(playerB, "Horror", 1, 1);
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
        setChoice(playerA, "No"); // Let the commander go to graveyard because of Gift of Immortality

        setStopAt(3, PhaseStep.UPKEEP);
        execute();

        assertPermanentCount(playerB, "Horror", 1);
        assertPowerToughness(playerB, "Horror", 1, 1);

        assertPermanentCount(playerA, "Daxos of Meletis", 1);
        assertPermanentCount(playerA, "Gift of Immortality", 1);

    }
}
