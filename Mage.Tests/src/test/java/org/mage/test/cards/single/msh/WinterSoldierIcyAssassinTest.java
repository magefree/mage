package org.mage.test.cards.single.msh;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.w.WinterSoldierIcyAssassin Winter Soldier, Icy Assassin} {W}{B}, 2/2
 * Vigilance, menace
 * Winter Soldier gets +2/+0 for each Equipment attached to him.
 * {3}{W}{B}: Return this card from your graveyard to the battlefield with a finality counter on him. Then you
 * may attach an Equipment you control to him.
 *
 * @author notgreat, Claude Opus 5.5
 */
public class WinterSoldierIcyAssassinTest extends CardTestPlayerBase {

    private static final String winterSoldier = "Winter Soldier, Icy Assassin";

    /**
     * 400.7j: "him" is the permanent the ability just returned, so the Equipment attaches to it.
     */
    @Test
    public void testReturnAndAttach() {
        addCard(Zone.BATTLEFIELD, playerA, "Scrubland", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Bonesplitter"); // Equipped creature gets +2/+0.
        addCard(Zone.GRAVEYARD, playerA, winterSoldier);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}{W}{B}:");
        setChoice(playerA, "Bonesplitter");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertAttachedTo(playerA, "Bonesplitter", winterSoldier, true);
        assertCounterCount(playerA, winterSoldier, CounterType.FINALITY, 1);
        assertPowerToughness(playerA, winterSoldier, 2 + 2 + 2, 2);
    }

    /**
     * An activation that returns nothing gives no finality counter, neither when the Cage stops the move nor
     * when something else returns him in response (400.7j: then there is no "him" to attach to either).
     */
    @Test
    public void testReturnsNothing() {
        addCard(Zone.BATTLEFIELD, playerA, "Scrubland", 17);
        addCard(Zone.BATTLEFIELD, playerA, "Bonesplitter");
        addCard(Zone.BATTLEFIELD, playerB, "Grafdigger's Cage"); // Creature cards in graveyards can't enter the battlefield.
        addCard(Zone.GRAVEYARD, playerA, winterSoldier);
        addCard(Zone.HAND, playerA, "Disenchant");
        addCard(Zone.HAND, playerA, "Live or Die"); // {3}{B}{B} instant, mode 1: return target creature card from your graveyard to the battlefield

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}{W}{B}:");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Disenchant", "Grafdigger's Cage", true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}{W}{B}:");
        setModeChoice(playerA, "1");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Live or Die", winterSoldier, "{3}{W}{B}:");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, winterSoldier, 1);
        assertAttachedTo(playerA, "Bonesplitter", winterSoldier, false);
        assertCounterCount(playerA, winterSoldier, CounterType.FINALITY, 0);
    }
}
