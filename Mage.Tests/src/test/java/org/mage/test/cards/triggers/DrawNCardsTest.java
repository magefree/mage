package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class DrawNCardsTest extends CardTestPlayerBase {

    private static final String snacker = "Sneaky Snacker";
    private static final String mists = "Reach Through Mists";
    private static final String looting = "Faithless Looting";

    @Test
    public void testSnacker() {
        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 1 + 1);
        addCard(Zone.GRAVEYARD, playerA, snacker);
        addCard(Zone.HAND, playerA, mists);
        addCard(Zone.HAND, playerA, looting);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, mists);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, looting);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTapped(snacker, true);
    }

    // https://github.com/magefree/mage/issues/15962
    @Test
    public void testAdditionalTriggers() {
        addCard(Zone.BATTLEFIELD, playerA, "Krang, the All-Powerful");
        addCard(Zone.BATTLEFIELD, playerA, "Throne of Eldraine");
        addCard(Zone.BATTLEFIELD, playerA, "Iron Man, Armored Avenger");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        setChoice(playerA, "Blue");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}, {T}: Draw two cards");

        setChoice(playerA, "Whenever you draw a card");
        setChoice(playerA, "Whenever you draw a card");
        setChoice(playerA, "Whenever you draw a card");
        setChoice(playerA, "Whenever you draw a card");
        setChoice(playerA, "Whenever a player draws their second card each turn");
        addTarget(playerA, "Iron Man, Armored Avenger");
        addTarget(playerA, "Iron Man, Armored Avenger");
        addTarget(playerA, "Iron Man, Armored Avenger");
        addTarget(playerA, "Iron Man, Armored Avenger");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Iron Man, Armored Avenger", 6, 6);
        assertPowerToughness(playerA, "Krang, the All-Powerful", 5, 5);
    }
}
