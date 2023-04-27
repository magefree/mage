package org.mage.test.cards.single.afr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class XanatharGuildKingpinTest extends CardTestPlayerBase {

    @Test
    public void test_Play() {
        removeAllCardsFromLibrary(playerA);
        removeAllCardsFromLibrary(playerB);
        skipInitShuffling();

        // At the beginning of your upkeep, choose target opponent.
        // Until end of turn, that player can’t cast spells, you may look at the top card of their library any time,
        // you may play the top card of their library, and you may spend mana as though it were mana of any color
        // to cast spells this way.
        addCard(Zone.BATTLEFIELD, playerA, "Xanathar, Guild Kingpin");
        //
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        //
        addCard(Zone.HAND, playerA, "Thunderbolt");
        //
        addCard(Zone.HAND, playerB, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);
        //
        addCard(Zone.LIBRARY, playerB, "Mountain");
        addCard(Zone.LIBRARY, playerB, "Lightning Bolt");
        addCard(Zone.LIBRARY, playerB, "Grizzly Bears");

        // activate on opponent
        addTarget(playerA, playerB);

        // B can't cast spells
        checkPlayableAbility("B can't cast", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast Lightning Bolt", false);
        // A can cast own and from B library
        checkPlayableAbility("A can cast own", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Thunderbolt", true);
        checkPlayableAbility("A can cast from B", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Grizzly Bears", true);

        // cast from B and try another one with any color and full stack
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {B}", 2); // pay for {G} as any
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", null, "Grizzly Bears");
        addTarget(playerA, playerB); // bolt
        checkStackSize("multi cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 2);

        // B can cast again
        checkPlayableAbility("B can cast", 2, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast Lightning Bolt", true);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();
    }
}
