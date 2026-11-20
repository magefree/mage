package org.mage.test.cards.single.rav;

import mage.constants.PhaseStep;
import mage.counters.CounterType;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class FlickerformTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.f.Flickerform}
     * <p>
     * {1}{W} Enchantment - Aura<br>
     * Enchant creature<br>
     * {2}{W}{W}: Exile enchanted creature and all Auras attached to it. At the beginning of the next end step,
     * return that card to the battlefield under its owner's control. If you do, return the other cards exiled this way
     * to the battlefield under their owners' control attached to that creature.
     */
    private static final String FLICKERFORM = "Flickerform";

    @Test
    public void test_ReturnWithAuras() {
        addCard(Zone.HAND, playerA, FLICKERFORM);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2 + 4);
        //
        addCard(Zone.HAND, playerA, "Holy Strength"); // {W}, aura, +1/+2
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, FLICKERFORM, "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Holy Strength", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{2}{W}{W}: Exile");
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        checkExileCount("exiled", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grizzly Bears", 1);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertAttachedTo(playerA, FLICKERFORM, "Grizzly Bears", true);
        assertAttachedTo(playerA, "Holy Strength", "Grizzly Bears", true);
        assertPowerToughness(playerA, "Grizzly Bears", 2 + 1, 2 + 2);
    }

    @Test
    public void test_ReturnWithAuras_ModalDoubleFacedAura() {
        addCard(Zone.HAND, playerA, FLICKERFORM);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2 + 4);
        //
        addCard(Zone.HAND, playerA, "Glasswing Grace"); // {3}{W/B}{W/B}, modal double-faced aura, +2/+2, flying, lifelink
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, FLICKERFORM, "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Glasswing Grace", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{2}{W}{W}: Exile");
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        checkExileCount("exiled", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grizzly Bears", 1);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertAttachedTo(playerA, FLICKERFORM, "Grizzly Bears", true);
        assertAttachedTo(playerA, "Glasswing Grace", "Grizzly Bears", true);
        assertPowerToughness(playerA, "Grizzly Bears", 2 + 2, 2 + 2);
    }

    @Test
    public void test_ReturnWithAuras_ModalDoubleFacedCreature() {
        addCard(Zone.HAND, playerA, FLICKERFORM);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2 + 4);
        //
        addCard(Zone.HAND, playerA, "Holy Strength"); // {W}, aura, +1/+2
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Tergrid, God of Fright"); // modal double-faced, front 4/5

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, FLICKERFORM, "Tergrid, God of Fright");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Holy Strength", "Tergrid, God of Fright");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{2}{W}{W}: Exile");
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        checkExileCount("exiled", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Tergrid, God of Fright", 1);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertAttachedTo(playerA, FLICKERFORM, "Tergrid, God of Fright", true);
        assertAttachedTo(playerA, "Holy Strength", "Tergrid, God of Fright", true);
        assertPowerToughness(playerA, "Tergrid, God of Fright", 4 + 1, 5 + 2);
    }

    @Test
    public void test_ExileAtSameTime_OneOrMore_KetramoseTheNewDawn() {
        // Whenever one or more cards are put into exile from graveyards and/or the battlefield during your turn,
        // you draw a card and lose 1 life.
        addCard(Zone.BATTLEFIELD, playerA, "Ketramose, the New Dawn"); // 4/4
        //
        addCard(Zone.HAND, playerA, FLICKERFORM);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2 + 4);
        addCard(Zone.HAND, playerA, "Holy Strength"); // {W}, aura, +1/+2
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, FLICKERFORM, "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Holy Strength", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // creature and auras are exiled at the same time, so it triggers once
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{2}{W}{W}: Exile");
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        checkExileCount("exiled", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grizzly Bears", 1);
        checkExileCount("exiled", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, FLICKERFORM, 1);
        checkExileCount("exiled", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Holy Strength", 1);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertLife(playerA, 20 - 1);
        assertHandCount(playerA, 1);
        assertAttachedTo(playerA, "Holy Strength", "Grizzly Bears", true);
    }

    @Test
    public void test_ExileAtSameTime_EachPermanent_SuperShredder() {
        // Whenever another permanent leaves the battlefield, put a +1/+1 counter on Super Shredder.
        addCard(Zone.BATTLEFIELD, playerA, "Super Shredder"); // 1/1
        //
        addCard(Zone.HAND, playerA, FLICKERFORM);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2 + 4);
        addCard(Zone.HAND, playerA, "Holy Strength"); // {W}, aura, +1/+2
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, FLICKERFORM, "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Holy Strength", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // creature and two auras leave the battlefield
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{2}{W}{W}: Exile");
        setChoice(playerA, "Whenever another permanent leaves the battlefield", 3 - 1); // order of 3 triggers

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertCounterCount(playerA, "Super Shredder", CounterType.P1P1, 3);
        assertAttachedTo(playerA, FLICKERFORM, "Grizzly Bears", true);
        assertAttachedTo(playerA, "Holy Strength", "Grizzly Bears", true);
    }
}
