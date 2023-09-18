package org.mage.test.cards.control;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class GainControlOfOwnedCreaturesTest extends CardTestPlayerBase {

    /**
     *
     *
     */
    @Test
    public void TrostaniDiscordantTest() {
        // Other creatures you control get +1/+1.
        // When Trostani Discordant enters the battlefield, create two 1/1 white Soldier creature tokens with lifelink.
        // At the beginning of your end step, each player gains control of all creatures they own.
        addCard(Zone.LIBRARY, playerA, "Trostani Discordant", 1); // Creature {3}{G}{W}  1/4

        // Your opponent can't cast spells with even converted mana costs. (Zero is even.)
        // Your opponents can't block with creatures with even converted mana costs.        
        addCard(Zone.LIBRARY, playerA, "Void Winnower", 1); // Creature 11/9      
        skipInitShuffling();

        // Look at the top ten cards of your library, exile up to two creature cards from among them, then shuffle your library. 
        // Target opponent may choose one of the exiled cards and put it onto the battlefield under their control. Put the rest onto the battlefield under your control.
        addCard(Zone.HAND, playerA, "Dubious Challenge", 1); // Sorcery {3}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dubious Challenge");
        setChoice(playerA, "Trostani Discordant^Void Winnower");

        addTarget(playerA, playerB);

        setChoice(playerB, "Trostani Discordant");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Dubious Challenge", 1);
        assertPermanentCount(playerA, "Void Winnower", 1);
        assertPowerToughness(playerA, "Void Winnower", 11, 9);
        assertPermanentCount(playerB, "Trostani Discordant", 1);
        assertPowerToughness(playerB, "Trostani Discordant", 1, 4);

        assertPowerToughness(playerA, "Silvercoat Lion", 2, 2);
        assertPowerToughness(playerB, "Silvercoat Lion", 3, 3);
    }

    /**
     * https://www.slightlymagic.net/forum/viewtopic.php?f=70&t=29805&p=243997#p243989
     *
     * I was playing a Dubious Challenge Pionner deck. In my game, Trostani
     * Discordant was put on the battlefield after Ive launched a Dubious
     * Challenge sorcery. My opponent took the Trostani Discordant and I took a
     * Void Winnower . Unfortnately, at the end step, the Trostani Discordant
     * ability that makes all players gain control of the creatures they OWN
     * doesn't happened.
     */
    @Test
    public void TrostaniDiscordantTriggerTest() {
        // Other creatures you control get +1/+1.
        // When Trostani Discordant enters the battlefield, create two 1/1 white Soldier creature tokens with lifelink.
        // At the beginning of your end step, each player gains control of all creatures they own.
        addCard(Zone.LIBRARY, playerA, "Trostani Discordant", 1); // Creature {3}{G}{W}  1/4

        // Your opponent can't cast spells with even converted mana costs. (Zero is even.)
        // Your opponents can't block with creatures with even converted mana costs.        
        addCard(Zone.LIBRARY, playerA, "Void Winnower", 1); // Creature 11/9      
        skipInitShuffling();

        // Look at the top ten cards of your library, exile up to two creature cards from among them, then shuffle your library. 
        // Target opponent may choose one of the exiled cards and put it onto the battlefield under their control. Put the rest onto the battlefield under your control.
        addCard(Zone.HAND, playerA, "Dubious Challenge", 1); // Sorcery {3}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dubious Challenge");
        setChoice(playerA, "Trostani Discordant^Void Winnower");

        addTarget(playerA, playerB);

        setChoice(playerB, "Trostani Discordant");

        setStrictChooseMode(true);
        setStopAt(8, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Dubious Challenge", 1);
        assertPermanentCount(playerA, "Void Winnower", 1);
        assertPowerToughness(playerA, "Void Winnower", 12, 10);
        assertPermanentCount(playerA, "Trostani Discordant", 1);
        assertPowerToughness(playerA, "Trostani Discordant", 1, 4);

        assertPowerToughness(playerA, "Silvercoat Lion", 3, 3);
        assertPowerToughness(playerB, "Silvercoat Lion", 2, 2);
    }
}
