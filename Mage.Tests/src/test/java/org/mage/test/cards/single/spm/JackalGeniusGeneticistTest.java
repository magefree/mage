package org.mage.test.cards.single.spm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author Jmlundeen
 */
public class JackalGeniusGeneticistTest extends CardTestPlayerBase {

    /*
    Jackal, Genius Geneticist
    {G}{U}
    Legendary Creature - Human Scientist Villain
    Trample
    Whenever you cast a creature spell with mana value equal to Jackal's power, copy that spell, except the copy isn't legendary. Then put a +1/+1 counter on Jackal.
    1/1
    */
    private static final String jackalGeniusGeneticist = "Jackal, Genius Geneticist";

    /*
    Bear Cub
    {1}{G}
    Creature - Bear
    
    2/2
    */
    private static final String bearCub = "Bear Cub";

    /*
    Ragavan, Nimble Pilferer
    {R}
    Legendary Creature - Monkey Pirate
    Whenever Ragavan, Nimble Pilferer deals combat damage to a player, create a Treasure token and exile the top card of that player's library. Until end of turn, you may cast that card.
    Dash {1}{R}
    2/1
    */
    private static final String ragavanNimblePilferer = "Ragavan, Nimble Pilferer";

    @Test
    public void testJackalGeniusGeneticist() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, jackalGeniusGeneticist);
        addCard(Zone.BATTLEFIELD, playerA, "Taiga", 3);
        addCard(Zone.HAND, playerA, ragavanNimblePilferer);
        addCard(Zone.HAND, playerA, bearCub);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ragavanNimblePilferer);
        setChoice(playerA, "Cast with no");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bearCub);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, ragavanNimblePilferer, 2);
        assertPermanentCount(playerA, bearCub, 2);
        assertCounterCount(playerA, jackalGeniusGeneticist, CounterType.P1P1, 2);
    }
}