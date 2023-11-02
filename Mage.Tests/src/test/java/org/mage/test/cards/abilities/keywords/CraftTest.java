package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class CraftTest extends CardTestPlayerBase {

    private static final String sawblades = "Spring-Loaded Sawblades";
    private static final String chariot = "Bladewheel Chariot";
    private static final String relic = "Darksteel Relic";

    @Test
    public void testExilePermanent() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, sawblades);
        addCard(Zone.BATTLEFIELD, playerA, relic);

        addTarget(playerA, relic);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Craft");

        setStopAt(1, PhaseStep.END_TURN);

        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, sawblades, 0);
        assertPermanentCount(playerA, chariot, 1);
        assertPermanentCount(playerA, relic, 0);
        assertExileCount(playerA, relic, 1);
    }

    @Test
    public void testExileCard() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, sawblades);
        addCard(Zone.GRAVEYARD, playerA, relic);

        addTarget(playerA, relic);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Craft");

        setStopAt(1, PhaseStep.END_TURN);

        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, sawblades, 0);
        assertPermanentCount(playerA, chariot, 1);
        assertGraveyardCount(playerA, relic, 0);
        assertExileCount(playerA, relic, 1);
    }

    private static final String standard = "Sunbird Standard";
    private static final String effigy = "Sunbird Effigy";
    private static final String thoctar = "Woolly Thoctar";
    private static final String watchwolf = "Watchwolf";
    private static final String yearling = "Cerodon Yearling";

    @Test
    public void testEffigy() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.BATTLEFIELD, playerA, standard);
        addCard(Zone.BATTLEFIELD, playerA, thoctar);
        addCard(Zone.HAND, playerA, thoctar);

        addTarget(playerA, thoctar);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Craft");

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: For each");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, thoctar);

        setStopAt(3, PhaseStep.END_TURN);

        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, standard, 0);
        assertPermanentCount(playerA, thoctar, 1);
        assertPowerToughness(playerA, effigy, 3, 3);
    }

    @Ignore // test fails due to issue with test player target handling
    @Test
    public void testEffigyMultiple() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.BATTLEFIELD, playerA, standard);
        addCard(Zone.BATTLEFIELD, playerA, yearling);
        addCard(Zone.GRAVEYARD, playerA, watchwolf);
        addCard(Zone.HAND, playerA, thoctar);

        addTarget(playerA, yearling);
        addTarget(playerA, watchwolf);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Craft");

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: For each");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, thoctar);

        setStopAt(3, PhaseStep.END_TURN);

        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, standard, 0);
        assertPermanentCount(playerA, thoctar, 1);
        assertPowerToughness(playerA, effigy, 3, 3);
    }
}
