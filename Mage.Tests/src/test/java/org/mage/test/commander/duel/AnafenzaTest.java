
package org.mage.test.commander.duel;

import java.io.FileNotFoundException;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.GameException;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommanderDuelBase;

/**
 *
 * @author LevelX2
 */
public class AnafenzaTest extends CardTestCommanderDuelBase {

    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        setDecknamePlayerA("CommanderAnafenza_WBG.dck"); // Commander = Anafenza, the Foremost
        return super.createNewGameAndPlayers();
    }

    /**
     * I was using Anafenza, the Foremost as Commander. She attacked and traded
     * with two creatures. I moved Anafenza to the Command Zone, but the
     * opponent's creatures "when {this} dies" abilities triggered. Since
     * Anafenza and those creatures all received lethal damage at the same time,
     * the creatures should have been exiled due to Anafenza's replacement
     * effect, but I guess since the logic asks if you want to use the Command
     * Zone replacement effect first, that it doesn't see her leaving the
     * battlefield at the same time as the other creatures.
     *
     * http://blogs.magicjudges.org/rulestips/2015/05/anafenza-vs-deathmist-raptor/
     */
    @Test
    public void testAnafenzaExileInCombat() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);

        // When Runed Servitor dies, each player draws a card.
        addCard(Zone.BATTLEFIELD, playerB, "Runed Servitor", 2);

        // Whenever Anafenza, the Foremost attacks, put a +1/+1 counter on another target tapped creature you control.
        // If a creature card would be put into an opponent's graveyard from anywhere, exile it instead.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Anafenza, the Foremost");

        attack(3, playerA, "Anafenza, the Foremost");
        block(3, playerB, "Runed Servitor:0", "Anafenza, the Foremost");
        block(3, playerB, "Runed Servitor:1", "Anafenza, the Foremost");

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 40);
        assertLife(playerB, 40);

        assertExileCount("Runed Servitor", 2);

        assertCommandZoneCount(playerA, "Anafenza, the Foremost", 1);
        assertGraveyardCount(playerA, "Anafenza, the Foremost", 0);

        assertHandCount(playerA, 1); // turn 3 draw
        assertHandCount(playerB, 1); // turn 2 draw

    }

    /**
     * Token don't go to exile because they are no creature cards
     */
    @Test
    public void testAnafenzaExileInCombatOmnathToken() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Acidic Slime", 1);

        addCard(Zone.HAND, playerB, "Forest", 2);
        // <i>Landfall</i> &mdash; Whenever a land enters the battlefield under your control, put a 5/5 red and green Elemental creature token onto the battlefield.
        // Whenever Omnath, Locus of Rage or another Elemental you control dies, Omnath deals 3 damage to any target.
        addCard(Zone.BATTLEFIELD, playerB, "Omnath, Locus of Rage", 1);

        // Whenever Anafenza, the Foremost attacks, put a +1/+1 counter on another target tapped creature you control.
        // If a creature card would be put into an opponent's graveyard from anywhere, exile it instead.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Anafenza, the Foremost");

        playLand(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Forest");
        playLand(4, PhaseStep.PRECOMBAT_MAIN, playerB, "Forest");

        attack(5, playerA, "Acidic Slime");
        block(5, playerB, "Elemental Token:0", "Acidic Slime");
        attack(5, playerA, "Anafenza, the Foremost");
        block(5, playerB, "Elemental Token:1", "Anafenza, the Foremost");
        addTarget(playerB, playerA);

        setStopAt(5, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertExileCount(playerA, 0);
        assertExileCount(playerB, 0);

        assertPermanentCount(playerB, "Elemental Token", 1);

        assertGraveyardCount(playerA, "Acidic Slime", 1);
        assertGraveyardCount(playerA, "Anafenza, the Foremost", 0);
        assertCommandZoneCount(playerA, "Anafenza, the Foremost", 1);

        assertHandCount(playerA, 2); // turn 3 + 5 draw
        assertHandCount(playerB, 2); // turn 2 + 4 draw

        assertLife(playerA, 37);
        assertLife(playerB, 40);
    }

    // Anafenza + Animated permanents
    @Test
    public void testAnafenzaExileAnimatedPermanents() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);

        // {0}: Tap all lands you control. Chimeric Idol becomes a 3/3 Turtle artifact creature until end of turn.
        addCard(Zone.BATTLEFIELD, playerB, "Chimeric Idol");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 3);

        // Whenever Anafenza, the Foremost attacks, put a +1/+1 counter on another target tapped creature you control.
        // If a creature card would be put into an opponent's graveyard from anywhere, exile it instead.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Anafenza, the Foremost"); // 4/4

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{0}: Tap all lands you control");

        attack(2, playerB, "Chimeric Idol");
        block(2, playerA, "Anafenza, the Foremost", "Chimeric Idol");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 40);
        assertLife(playerB, 40);

        assertExileCount("Chimeric Idol", 1);
        assertGraveyardCount(playerB, "Chimeric Idol", 0);
        assertPermanentCount(playerB, "Chimeric Idol", 0);

        assertTappedCount("Mountain", true, 3);

    }
}
