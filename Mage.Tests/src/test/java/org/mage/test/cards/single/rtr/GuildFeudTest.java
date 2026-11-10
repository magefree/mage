package org.mage.test.cards.single.rtr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class GuildFeudTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.g.GuildFeud}
     * <p>
     * {5}{R} Enchantment<br>
     * At the beginning of your upkeep, target opponent reveals the top three cards of their library, may put a
     * creature card from among them onto the battlefield, then puts the rest into their graveyard. You do the same
     * with the top three cards of your library. If two creatures are put onto the battlefield this way, those
     * creatures fight each other.
     */
    private static final String GUILD_FEUD = "Guild Feud";

    @Test
    public void test_Fight() {
        addCard(Zone.BATTLEFIELD, playerA, GUILD_FEUD);
        //
        skipInitShuffling();
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Hill Giant"); // giant 3/3
        removeAllCardsFromLibrary(playerB);
        addCard(Zone.LIBRARY, playerB, "Grizzly Bears"); // bear 2/2

        addTarget(playerA, playerB); // opponent for the upkeep trigger
        setChoice(playerB, true); // put a creature
        setChoice(playerB, "Grizzly Bears"); // creature to put
        setChoice(playerA, true); // put a creature
        setChoice(playerA, "Hill Giant"); // creature to put

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerB, "Grizzly Bears", 1);
        assertDamageReceived(playerA, "Hill Giant", 2);
    }

    @Test
    public void test_Fight_ModalDoubleFaced() {
        addCard(Zone.BATTLEFIELD, playerA, GUILD_FEUD);
        //
        skipInitShuffling();
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Tergrid, God of Fright"); // modal double-faced, front 4/5
        removeAllCardsFromLibrary(playerB);
        addCard(Zone.LIBRARY, playerB, "Grizzly Bears"); // bear 2/2

        addTarget(playerA, playerB); // opponent for the upkeep trigger
        setChoice(playerB, true); // put a creature
        setChoice(playerB, "Grizzly Bears"); // creature to put
        setChoice(playerA, true); // put a creature
        setChoice(playerA, "Tergrid, God of Fright"); // creature to put

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerB, "Grizzly Bears", 1);
        assertDamageReceived(playerA, "Tergrid, God of Fright", 2);
    }
}
