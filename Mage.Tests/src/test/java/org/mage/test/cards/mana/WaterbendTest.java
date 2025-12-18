package org.mage.test.cards.mana;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class WaterbendTest extends CardTestPlayerBase {

    private static final String waterbender = "Flexible Waterbender";

    @Test
    public void testJustMana() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, waterbender);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "waterbend");
        setChoice(playerA, TestPlayer.CHOICE_SKIP);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, waterbender, 5, 2);
        assertTapped(waterbender, false);
        assertTapped("Island", true);
    }

    private static final String relic = "Darksteel Relic";

    @Test
    public void testNoMana() {
        addCard(Zone.BATTLEFIELD, playerA, relic, 2);
        addCard(Zone.BATTLEFIELD, playerA, waterbender);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "waterbend");
        setChoice(playerA, waterbender);
        setChoice(playerA, relic);
        setChoice(playerA, relic);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, waterbender, 5, 2);
        assertTapped(waterbender, true);
        assertTapped(relic, true);
    }

    @Test
    public void testManaAndCreature() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, waterbender);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "waterbend");
        setChoice(playerA, waterbender);
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, waterbender, 5, 2);
        assertTapped(waterbender, true);
        assertTapped("Island", true);
    }

    private static final String katara = "Katara, Water Tribe's Hope";

    @Test
    public void testX() {
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, relic);
        addCard(Zone.BATTLEFIELD, playerA, katara);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "waterbend");
        setChoice(playerA, "X=3");
        setChoice(playerA, relic);
        setChoice(playerA, katara);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, katara, 3, 3);
        assertTapped(relic, true);
        assertTapped(katara, true);
        assertTapped("Island", true);
    }

    private static final String spirit = "Benevolent River Spirit";

    @Test
    public void testSpellCost() {
        removeAllCardsFromLibrary(playerA); // removes need to make scry choices
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerA, relic, 3);
        addCard(Zone.HAND, playerA, spirit);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, spirit);
        setChoice(playerA, relic);
        setChoice(playerA, relic);
        setChoice(playerA, relic);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTapped("Island", true);
        assertTapped(relic, true);
        assertTapped(spirit, false);
    }
}
