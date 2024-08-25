package org.mage.test.cards.single.otj;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class ObekaSplitterOfSecondsTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.o.ObekaSplitterOfSeconds Obeka, Splitter of Seconds} {1}{U}{B}{R}
     * Legendary Creature — Ogre Warlock
     * Menace
     * Whenever Obeka, Splitter of Seconds deals combat damage to a player, you get that many additional upkeep steps after this phase.
     * 2/5
     */
    private static final String obeka = "Obeka, Splitter of Seconds";

    @Test
    public void test_ExtraUpkeep() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, obeka);
        // At the beginning of your upkeep, you gain 1 life.
        addCard(Zone.BATTLEFIELD, playerA, "Fountain of Renewal");

        attack(1, playerA, obeka, playerB);
        checkLife("Extra upkeeps are in extra phases after combat", 1, PhaseStep.END_COMBAT, playerA, 20 + 1);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 + 1 + 2); // 1 regular upkeep + 2 obeka ones
        assertTapped(obeka, true); // checks that no extra untap happened
        assertHandCount(playerA, 0); // checks that no draw step happened
    }

    // Bug: Extra upkeep is wrongly changing summoning sickness status
    @Test
    public void test_ExtraUpkeep_TapAbility() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, obeka);
        // At the beginning of your upkeep, you gain 1 life.
        addCard(Zone.BATTLEFIELD, playerA, "Fountain of Renewal");
        addCard(Zone.HAND, playerA, "Soulmender"); // "{T}: You gain 1 life
        addCard(Zone.BATTLEFIELD, playerA, "Plains");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Soulmender", true);
        checkPlayableAbility("Soulmender summoning sick pre-combat", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: You gain 1 life", false);

        attack(1, playerA, obeka, playerB);
        checkLife("Extra upkeeps are in extra phases after combat", 1, PhaseStep.END_COMBAT, playerA, 20 + 1);
        checkPlayableAbility("Soulmender summoning sick after extra upkeep", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}: You gain 1 life", false);

        checkPlayableAbility("Soulmender no longer summoning sick turn 3", 3, PhaseStep.UPKEEP, playerA, "{T}: You gain 1 life", true);
        activateAbility(3, PhaseStep.UPKEEP, playerA, "{T}: You gain 1 life");

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 + 2 + 2 + 1); // 2 regular upkeep + 2 obeka ones + 1 Soulmender activation
    }
}
