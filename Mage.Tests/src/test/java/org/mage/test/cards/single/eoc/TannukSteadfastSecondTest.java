package org.mage.test.cards.single.eoc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class TannukSteadfastSecondTest extends CardTestPlayerBase {

    // Other creatures you control have haste.
    // Artifact cards and red creature cards in your hand have warp {2}{R}.
    private static final String tannuk = "Tannuk, Steadfast Second";

    // When Clive enters, you may discard your hand, then draw cards equal to your devotion to red.
    // {4}{R}{R}, {T}: Exile Clive, then return it to the battlefield transformed under its owner's control. Activate only as a sorcery.
    private static final String clive = "Clive, Ifrit's Dominant";
    private static final String ifrit = "Ifrit, Warden of Inferno";

    @Test
    public void testWarpSingleFaced() {
        addCard(Zone.BATTLEFIELD, playerA, tannuk);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, "Hill Giant");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hill Giant with Warp");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.CLEANUP);
        execute();

        assertPermanentCount(playerA, "Hill Giant", 0);
        assertExileCount(playerA, "Hill Giant", 1);
    }

    @Test
    public void testWarpTransformingDoubleFaced() {
        addCard(Zone.BATTLEFIELD, playerA, tannuk);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, clive);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, clive + " with Warp");
        setChoice(playerA, false); // don't discard hand

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.CLEANUP);
        execute();

        assertPermanentCount(playerA, clive, 0);
        assertExileCount(playerA, clive, 1);
    }

    @Test
    public void testWarpTransformingDoubleFacedCastFromExile() {
        addCard(Zone.BATTLEFIELD, playerA, tannuk);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        addCard(Zone.HAND, playerA, clive);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, clive + " with Warp");
        setChoice(playerA, false); // don't discard hand

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, clive);
        setChoice(playerA, false); // don't discard hand

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, clive, 1);
        assertExileCount(playerA, clive, 0);
    }

    @Test
    public void testWarpThenTransformIsNewObject() {
        addCard(Zone.BATTLEFIELD, playerA, tannuk);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3 + 6);
        addCard(Zone.HAND, playerA, clive);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, clive + " with Warp");
        setChoice(playerA, false); // don't discard hand

        // haste from Tannuk
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{4}{R}{R}, {T}: Exile");
        addTarget(playerA, TestPlayer.TARGET_SKIP); // chapter I, up to one target

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.CLEANUP);
        execute();

        // exiled and returned, so it's a new object that warp doesn't exile
        assertPermanentCount(playerA, ifrit, 1);
        assertExileCount(playerA, clive, 0);
    }

    @Test
    public void testWarpTransformingDoubleFacedOther() {
        addCard(Zone.BATTLEFIELD, playerA, tannuk);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        // At the beginning of each upkeep, if no spells were cast last turn, transform Afflicted Deserter.
        addCard(Zone.HAND, playerA, "Afflicted Deserter");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Afflicted Deserter with Warp");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.CLEANUP);
        execute();

        assertPermanentCount(playerA, "Afflicted Deserter", 0);
        assertExileCount(playerA, "Afflicted Deserter", 1);
    }

    @Test
    public void testWarpModalDoubleFaced() {
        addCard(Zone.BATTLEFIELD, playerA, tannuk);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        // Trample
        // Whenever a creature or planeswalker an opponent controls is dealt excess noncombat damage, Toralf, God of Fury deals damage equal to the excess to any target other than that permanent.
        addCard(Zone.HAND, playerA, "Toralf, God of Fury");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Toralf, God of Fury with Warp");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.CLEANUP);
        execute();

        assertPermanentCount(playerA, "Toralf, God of Fury", 0);
        assertExileCount(playerA, "Toralf, God of Fury", 1);
    }
}
