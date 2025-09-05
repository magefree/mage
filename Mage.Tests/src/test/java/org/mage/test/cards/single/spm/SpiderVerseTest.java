package org.mage.test.cards.single.spm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4Players;

/**
 *
 * @author Jmlundeen
 */
public class SpiderVerseTest extends CardTestCommander4Players {

    /*
    Spider-Verse
    {3}{R}{R}
    Enchantment
    The "legend rule" doesn't apply to Spiders you control.
    Whenever you cast a spell from anywhere other than your hand, you may copy it. If you do, you may choose new targets for the copy. If the copy is a permanent spell, it gains haste. Do this only once each turn.
    */
    private static final String spiderVerse = "Spider-Verse";

    /*
    Lightning Bolt
    {R}
    Instant
    Lightning Bolt deals 3 damage to any target.
    */
    private static final String lightningBolt = "Lightning Bolt";

    /*
    Bear Cub
    {1}{G}
    Creature - Bear
    
    2/2
    */
    private static final String bearCub = "Bear Cub";

    /*
    Ashiok, Nightmare Muse
    {3}{U}{B}
    Legendary Planeswalker - Ashiok

    +1: Create a 2/3 blue and black Nightmare creature token with "Whenever this creature attacks or blocks, each opponent exiles the top two cards of their library."
    -3: Return target nonland permanent to its owner's hand, then that player exiles a card from their hand.
    -7: You may cast up to three face-up cards your opponents own from exile without paying their mana costs.
    */
    private static final String ashiokNightmareMuse = "Ashiok, Nightmare Muse";

    @Test
    public void testSpiderVerse() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, spiderVerse);
        addCard(Zone.BATTLEFIELD, playerA, ashiokNightmareMuse);
        addCard(Zone.EXILED, playerB, lightningBolt);
        addCard(Zone.EXILED, playerB, bearCub);

        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, ashiokNightmareMuse, CounterType.LOYALTY, 9);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-7");
        setChoice(playerA, lightningBolt);
        setChoice(playerA, true);
        addTarget(playerA, playerD);
        setChoice(playerA, false, 2);
        setChoice(playerA, true, 2); // use spider-verse / new target
        addTarget(playerA, playerB);

        activateAbility(5, PhaseStep.PRECOMBAT_MAIN, playerA, "-7");
        setChoice(playerA, true, 2); // ashiok + spider-verse
        attack(5, playerA, bearCub, playerC); // copy has haste

        setStopAt(5, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 3);
        assertLife(playerD, 20 - 3);
        assertLife(playerC, 20 - 2);
    }
}