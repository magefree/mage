package org.mage.test.cards.single.atq;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class TawnossCoffinTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.t.TawnossCoffin}
     * <p>
     * {4} Artifact<br>
     * You may choose not to untap Tawnos's Coffin during your untap step.<br>
     * {3}, {T}: Exile target creature and all Auras attached to it. Note the number and kind of counters that were
     * on that creature. When Tawnos's Coffin leaves the battlefield or becomes untapped, return the exiled card to the
     * battlefield under its owner's control tapped with the noted number and kind of counters on it, and if you do,
     * return the exiled Aura cards to the battlefield under their owner's control attached to that permanent.
     */
    private static final String TAWNOSS_COFFIN = "Tawnos's Coffin";

    @Test
    public void test_ReturnTappedWithCountersAndAuras() {
        addCustomEffect_TargetDestroy(playerA);
        //
        addCard(Zone.BATTLEFIELD, playerA, TAWNOSS_COFFIN);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        //
        addCard(Zone.HAND, playerA, "Holy Strength"); // {W}, aura, +1/+2
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2

        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", CounterType.P1P1, 2);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Holy Strength", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}, {T}: Exile", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkExileCount("exiled", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 1);
        checkExileCount("exiled", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Holy Strength", 1);

        // return on leaves the battlefield
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "target destroy", TAWNOSS_COFFIN);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, TAWNOSS_COFFIN, 1);
        assertTapped("Grizzly Bears", true);
        assertCounterCount(playerA, "Grizzly Bears", CounterType.P1P1, 2);
        assertAttachedTo(playerA, "Holy Strength", "Grizzly Bears", true);
        assertPowerToughness(playerA, "Grizzly Bears", 2 + 2 + 1, 2 + 2 + 2);
    }

    @Test
    public void test_ReturnWithAuras_ModalDoubleFacedAura() {
        addCustomEffect_TargetDestroy(playerA);
        //
        addCard(Zone.BATTLEFIELD, playerA, TAWNOSS_COFFIN);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        //
        addCard(Zone.HAND, playerA, "Glasswing Grace"); // {3}{W/B}{W/B}, modal double-faced aura, +2/+2, flying, lifelink
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Glasswing Grace", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}, {T}: Exile", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkExileCount("exiled", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 1);
        checkExileCount("exiled", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Glasswing Grace", 1);

        // return on leaves the battlefield
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "target destroy", TAWNOSS_COFFIN);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAttachedTo(playerA, "Glasswing Grace", "Grizzly Bears", true);
        assertPowerToughness(playerA, "Grizzly Bears", 2 + 2, 2 + 2);
    }

    @Test
    public void test_ReturnWithAuras_ModalDoubleFacedCreature() {
        addCustomEffect_TargetDestroy(playerA);
        //
        addCard(Zone.BATTLEFIELD, playerA, TAWNOSS_COFFIN);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        //
        addCard(Zone.HAND, playerA, "Holy Strength"); // {W}, aura, +1/+2
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Tergrid, God of Fright"); // modal double-faced, front 4/5

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Holy Strength", "Tergrid, God of Fright");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}, {T}: Exile", "Tergrid, God of Fright");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkExileCount("exiled", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tergrid, God of Fright", 1);
        checkExileCount("exiled", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Holy Strength", 1);

        // return on leaves the battlefield
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "target destroy", TAWNOSS_COFFIN);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTapped("Tergrid, God of Fright", true);
        assertAttachedTo(playerA, "Holy Strength", "Tergrid, God of Fright", true);
        assertPowerToughness(playerA, "Tergrid, God of Fright", 4 + 1, 5 + 2);
    }
}
