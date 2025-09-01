package org.mage.test.cards.mana;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class LimitPerTurnTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.s.ShireScarecrow Shire Scarecrow} {2}
     * Artifact Creature — Scarecrow
     * Defender
     * {1}: Add one mana of any color. Activate only once each turn.
     * 0/3
     */
    private static final String scarecrow = "Shire Scarecrow";


    @Test
    public void test_LimitOncePerTurn() {
        String vanguard = "Elite Vanguard";  // {W} 2/1
        String goblin = "Raging Goblin";  // {R} 1/1
        addCard(Zone.BATTLEFIELD, playerA, scarecrow, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.HAND, playerA, vanguard, 1);
        addCard(Zone.HAND, playerA, goblin, 1);

        checkPlayableAbility("1: Vanguard can be cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + vanguard, true);
        checkPlayableAbility("1: goblin can be cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + goblin, true);

        setChoice(playerA, "White"); // choice for Scarecrow mana
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, vanguard);

        checkPlayableAbility("2: goblin can not be cast", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast " + goblin, false);

        checkPlayableAbility("3: goblin can be cast on turn 3", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + goblin, true);

        setChoice(playerA, "Red"); // choice for Scarecrow mana
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, goblin);

        setStopAt(3, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, 5);
    }

    @Test
    public void test_MultipleScarecrows() {
        String deus = "Deus of Calamity";  // {R/G}{R/G}{R/G}{R/G}{R/G}
        String boggart = "Boggart Ram-Gang";  // {R/G}{R/G}{R/G}
        addCard(Zone.BATTLEFIELD, playerA, scarecrow, 3);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.HAND, playerA, deus, 1);
        addCard(Zone.HAND, playerA, boggart, 1);

        checkPlayableAbility("1: boggart can be cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + boggart, true);
        checkPlayableAbility("1: deus can not be cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + deus, false);

        setChoice(playerA, "Red"); // choice for Scarecrow mana
        setChoice(playerA, "Red"); // choice for Scarecrow mana
        setChoice(playerA, "Green"); // choice for Scarecrow mana
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, boggart);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, boggart, 1);
    }
}
