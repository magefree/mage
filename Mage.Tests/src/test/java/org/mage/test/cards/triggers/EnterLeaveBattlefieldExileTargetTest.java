package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class EnterLeaveBattlefieldExileTargetTest extends CardTestPlayerBase {

    @Test
    public void testAngelOfSerenityExile() {
        // Flying
        // When Angel of Serenity enters the battlefield, you may exile up to three other target creatures from the battlefield and/or creature cards from graveyards.
        // When Angel of Serenity leaves the battlefield, return the exiled cards to their owners' hands.
        addCard(Zone.HAND, playerA, "Angel of Serenity");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Angel of Serenity");
        addTarget(playerA, "Silvercoat Lion^Pillarfield Ox");
        setChoice(playerA, true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Angel of Serenity", 1);
        assertExileCount("Silvercoat Lion", 1);
        assertExileCount("Pillarfield Ox", 1);

    }

    /**
     * When Angel of Serenity entered the battlefield on my opponent's main
     * phase it exiled 3 of my creatures (as it should), I cast Ultimate Price
     * (destroy monocolored creature) on my next main phase and destroyed the
     * Angel of Serenity. The log said the exiled cards were returned to my hand
     * but they remained in exile indefinitely.
     */
    @Test
    public void testAngelOfSerenityExileReturn() {
        // Flying
        // When Angel of Serenity enters the battlefield, you may exile up to three other target creatures from the battlefield and/or creature cards from graveyards.
        // When Angel of Serenity leaves the battlefield, return the exiled cards to their owners' hands.
        addCard(Zone.HAND, playerA, "Angel of Serenity");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);

        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox", 1);
        addCard(Zone.HAND, playerB, "Ultimate Price", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Angel of Serenity");
        addTarget(playerA, "Silvercoat Lion^Pillarfield Ox");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Ultimate Price", "Angel of Serenity");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Ultimate Price", 1);
        assertGraveyardCount(playerA, "Angel of Serenity", 1);
        assertHandCount(playerB, "Silvercoat Lion", 1);
        assertHandCount(playerB, "Pillarfield Ox", 1);
        assertExileCount(playerB, 0);
    }

    @Test
    public void testAngelOfSerenityTargets() {
        // test NPE error while AI targeting battlefield with tokens

        // Flying
        // When Angel of Serenity enters the battlefield, you may exile up to three other target creatures from the battlefield and/or creature cards from graveyards.
        addCard(Zone.HAND, playerA, "Angel of Serenity");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);
        //
        // Create two 2/2 white Knight Ally creature tokens.
        addCard(Zone.HAND, playerA, "Allied Reinforcements", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 2);
        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion", 2);
        addCard(Zone.GRAVEYARD, playerB, "Balduvian Bears", 2);

        // create tokens
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Allied Reinforcements");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // angel
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Angel of Serenity");
        setChoice(playerA, true);
        //addTarget(playerA, "Silvercoat Lion^Balduvian Bears"); // AI must target

        //setStrictChooseMode(true); // AI must target
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Knight Ally Token", 2);
        assertPermanentCount(playerA, "Angel of Serenity", 1);
    }
}
