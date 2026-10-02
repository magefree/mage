package org.mage.test.cards.single.wth;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class TariffTest extends CardTestPlayerBase {

    /*
    Tariff
    {1}{W}
    Sorcery

    Each player sacrifices the creature they control with the highest mana value unless they pay that creature's
    mana cost. If two or more creatures a player controls are tied for highest mana value, that player chooses one.
     */
    private static final String tariff = "Tariff";

    // creatures with mana value 2
    private static final String grizzlyBears = "Grizzly Bears"; // 2/2
    private static final String watchwolf = "Watchwolf"; // 3/3
    // creatures with mana value 3
    private static final String grayOgre = "Gray Ogre"; // 2/2
    private static final String centaurCourser = "Centaur Courser"; // 3/3

    private void prepareTiedCreatures() {
        addCard(Zone.HAND, playerA, tariff);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, grizzlyBears);
        addCard(Zone.BATTLEFIELD, playerA, watchwolf);

        addCard(Zone.BATTLEFIELD, playerB, grayOgre);
        addCard(Zone.BATTLEFIELD, playerB, centaurCourser);
        addCard(Zone.BATTLEFIELD, playerB, "Llanowar Elves"); // lower mana value, must be ignored
    }

    @Test
    public void test_TiedCreatures_ControllersChoose() {
        setStrictChooseMode(true);
        prepareTiedCreatures();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, tariff);
        setChoice(playerA, watchwolf);
        setChoice(playerA, false); // don't pay
        setChoice(playerB, grayOgre);
        setChoice(playerB, false); // don't pay

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, watchwolf, 1);
        assertPermanentCount(playerA, grizzlyBears, 1);
        assertGraveyardCount(playerB, grayOgre, 1);
        assertPermanentCount(playerB, centaurCourser, 1);
        assertPermanentCount(playerB, "Llanowar Elves", 1);
    }

    @Test
    public void test_TiedCreatures_AIChooses() {
        // AI must be able to choose among tied creatures (it used to fail on an unsupported battlefield card target)
        setStrictChooseMode(false);
        prepareTiedCreatures();

        // nobody has mana left to pay, so the chosen creatures are sacrificed
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, tariff);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // AI chooses its least valuable creature
        assertGraveyardCount(playerA, grizzlyBears, 1);
        assertPermanentCount(playerA, watchwolf, 1);
        assertGraveyardCount(playerB, grayOgre, 1);
        assertPermanentCount(playerB, centaurCourser, 1);
    }
}
