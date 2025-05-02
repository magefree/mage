package org.mage.test.cards.single.nph;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class PhyrexianIngesterTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.p.PhyrexianIngester Phyrexian Ingester} {6}{U}
     * Creature — Phyrexian Beast
     * Imprint — When Phyrexian Ingester enters the battlefield, you may exile target nontoken creature.
     * Phyrexian Ingester gets +X/+Y, where X is the exiled creature card’s power and Y is its toughness.
     * 3/3
     */
    private static final String ingester = "Phyrexian Ingester";

    @Test
    public void test_Ingest() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, ingester);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 7);
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard"); // 2/1

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ingester);
        addTarget(playerA, "Elite Vanguard"); // target for trigger
        setChoice(playerA, true); // yes to "you may"

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount(playerA, "Elite Vanguard", 1);
        assertPowerToughness(playerA, ingester, 3 + 2, 3 + 1);
    }

    @Test
    public void test_Panharmonicon() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, ingester);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 7);
        addCard(Zone.BATTLEFIELD, playerA, "Panharmonicon");
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard"); // 2/1
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ingester);
        setChoice(playerA, "<i>Imprint</i>"); // stack triggers
        addTarget(playerA, "Elite Vanguard"); // target for trigger
        setChoice(playerA, true); // yes to "you may"
        addTarget(playerA, "Grizzly Bears"); // target for trigger
        setChoice(playerA, true); // yes to "you may"

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount(playerA, "Elite Vanguard", 1);
        assertExileCount(playerA, "Grizzly Bears", 1);
        assertPowerToughness(playerA, ingester, 3 + 2 + 2, 3 + 1 + 2);
    }

    @Test
    public void test_DestroyInResponse() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, ingester);
        addCard(Zone.BATTLEFIELD, playerA, "Underground Sea", 7 + 2);
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard"); // 2/1
        addCard(Zone.HAND, playerA, "Doom Blade");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ingester, true);
        addTarget(playerA, "Elite Vanguard"); // target for trigger
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Doom Blade", ingester);
        setChoice(playerA, true); // yes to "you may"

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount(playerA, "Elite Vanguard", 1);
        assertGraveyardCount(playerA, ingester, 1);
    }

    @Test
    public void test_BlinkInResponse() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, ingester);
        addCard(Zone.BATTLEFIELD, playerA, "Tundra", 7 + 1);
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard"); // 2/1
        addCard(Zone.HAND, playerA, "Ephemerate");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ingester);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA, true);
        addTarget(playerA, "Elite Vanguard"); // target for oldest trigger
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ephemerate", ingester);
        addTarget(playerA, "Elite Vanguard"); // target for latest trigger
        setChoice(playerA, false); // no to "you may" of latest ingester trigger
        setChoice(playerA, true); // yes to "you may" of oldest ingester trigger

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount(playerA, "Elite Vanguard", 1);
        assertPowerToughness(playerA, ingester, 3, 3);
    }
}
