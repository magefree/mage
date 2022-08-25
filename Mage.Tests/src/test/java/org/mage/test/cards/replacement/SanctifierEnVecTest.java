package org.mage.test.cards.replacement;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class SanctifierEnVecTest extends CardTestPlayerBase {

    private static final String sanctifier = "Sanctifier en-Vec";
    private static final String painter = "Painter's Servant";
    private static final String jace = "Jace, the Mind Sculptor";

    @Test
    public void testEntersBattlefieldAbility() {
        addCard(Zone.GRAVEYARD, playerA, "Divination");
        addCard(Zone.GRAVEYARD, playerA, "Lightning Bolt");
        addCard(Zone.GRAVEYARD, playerB, "Fatal Push");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, sanctifier);
        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, sanctifier);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, sanctifier, 1);
        assertGraveyardCount(playerA, "Divination", 1);
        assertExileCount(playerA, "Lightning Bolt", 1);
        assertExileCount(playerB, "Fatal Push", 1);
    }

    @Test
    public void testReplacementEffect() {
        addCard(Zone.HAND, playerA, "Lightning Bolt", 2);
        addCard(Zone.HAND, playerA, "Divination");
        addCard(Zone.HAND, playerB, jace);
        addCard(Zone.HAND, playerB, "Fatal Push");
        addCard(Zone.HAND, playerB, "One with Nothing");
        addCard(Zone.BATTLEFIELD, playerA, sanctifier);
        addCard(Zone.BATTLEFIELD, playerB, "Midnight Reaper");
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 1);
        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Midnight Reaper");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Divination");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "One with Nothing");
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        // Test that Midnight Reaper did not trigger
        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, sanctifier, 1);
        assertGraveyardCount(playerA, "Divination", 1);
        assertGraveyardCount(playerB, "Grizzly Bears", 1);
        assertExileCount(playerA, "Lightning Bolt", 2);
        assertExileCount(playerB, "Midnight Reaper", 1);

        assertExileCount(playerB, "One with Nothing", 1);
        assertGraveyardCount(playerB, jace, 1);
        assertExileCount(playerB, "Fatal Push", 1);
    }

    @Test
    public void testEtbWithPaintersServant() {
        addCard(Zone.GRAVEYARD, playerA, "Divination");
        addCard(Zone.GRAVEYARD, playerA, "Lightning Bolt");
        addCard(Zone.GRAVEYARD, playerB, "Fatal Push");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.HAND, playerA, sanctifier);
        addCard(Zone.HAND, playerA, painter);
        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, painter, true);
        setChoice(playerA, "Black");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, sanctifier);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, painter, 1);
        assertPermanentCount(playerA, sanctifier, 1);
        assertExileCount(playerA, "Divination", 1);
        assertExileCount(playerA, "Lightning Bolt", 1);
        assertExileCount(playerB, "Fatal Push", 1);
    }

    @Test
    public void testReplacementWithPaintersServant() {
        addCard(Zone.HAND, playerA, "Lightning Bolt", 2);
        addCard(Zone.HAND, playerA, "Divination");
        addCard(Zone.HAND, playerA, painter);
        addCard(Zone.HAND, playerB, jace);
        addCard(Zone.HAND, playerB, "Fatal Push");
        addCard(Zone.HAND, playerB, "One with Nothing");
        addCard(Zone.BATTLEFIELD, playerA, sanctifier);
        addCard(Zone.BATTLEFIELD, playerB, "Midnight Reaper");
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 1);
        setStrictChooseMode(true);

        // Tap correctly for painter
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {C}",2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, painter);
        setChoice(playerA, "Red");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Midnight Reaper");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Divination");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "One with Nothing");
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        // Test that Midnight Reaper did not trigger
        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, painter, 1);
        assertPermanentCount(playerA, sanctifier, 1);
        assertExileCount(playerB, jace, 1);
        assertExileCount(playerA, "Divination", 1);
        assertExileCount(playerB, "Grizzly Bears", 1);
        assertExileCount(playerA, "Lightning Bolt", 2);
        assertExileCount(playerB, "Midnight Reaper", 1);

        assertExileCount(playerB, "One with Nothing", 1);
        assertExileCount(playerB, jace, 1);
        assertExileCount(playerB, "Fatal Push", 1);
    }
}
