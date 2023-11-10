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

    private static final String sawblades = "Spring-Loaded Sawblades"; // Craft with artifact {3}{W}
    private static final String chariot = "Bladewheel Chariot"; // back side Vehicle
    private static final String relic = "Darksteel Relic"; // Artifact {0} Indestructible

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

    private static final String standard = "Sunbird Standard"; // Craft with one or more {5}
    private static final String effigy = "Sunbird Effigy"; // back side artifact creature
    // Sunbird Effigy’s power and toughness are each equal to the number of colors among the exiled cards used to craft it.
    // {T}: For each color among the exiled cards used to craft Sunbird Effigy, add one mana of that color.
    private static final String thoctar = "Woolly Thoctar"; // RGW 5/4
    private static final String watchwolf = "Watchwolf"; // GW 3/3
    private static final String yearling = "Cerodon Yearling"; // RW 2/2 Vigilance Haste

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

    private static final String gnome = "Market Gnome";
    // When Market Gnome is exiled from the battlefield while you’re activating a craft ability, you gain 1 life and draw a card.

    @Test
    public void testMarketGnomeExilePermanent() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, sawblades);
        addCard(Zone.BATTLEFIELD, playerA, gnome);

        addTarget(playerA, gnome);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Craft");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, sawblades, 0);
        assertPermanentCount(playerA, chariot, 1);
        assertPermanentCount(playerA, gnome, 0);
        assertExileCount(playerA, gnome, 1);
        assertLife(playerA, 21);
        assertHandCount(playerA, 1);
    }

    @Test
    public void testMarketGnomeExileCard() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, sawblades);
        addCard(Zone.GRAVEYARD, playerA, gnome);

        addTarget(playerA, gnome);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Craft");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, sawblades, 0);
        assertPermanentCount(playerA, chariot, 1);
        assertGraveyardCount(playerA, gnome, 0);
        assertExileCount(playerA, gnome, 1);
        assertLife(playerA, 20);
        assertHandCount(playerA, 0);
    }

    @Test
    public void testMarketGnomeExiledExileOther() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, sawblades);
        addCard(Zone.BATTLEFIELD, playerA, relic);
        addCard(Zone.EXILED, playerA, gnome);

        addTarget(playerA, relic);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Craft");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, sawblades, 0);
        assertPermanentCount(playerA, chariot, 1);
        assertPermanentCount(playerA, relic, 0);
        assertExileCount(playerA, relic, 1);
        assertLife(playerA, 20);
        assertHandCount(playerA, 0);
    }

    @Test
    public void testMarketGnomeBattlefieldExileOther() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, sawblades);
        addCard(Zone.BATTLEFIELD, playerA, relic);
        addCard(Zone.BATTLEFIELD, playerA, gnome);

        addTarget(playerA, relic);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Craft");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, sawblades, 0);
        assertPermanentCount(playerA, chariot, 1);
        assertPermanentCount(playerA, relic, 0);
        assertExileCount(playerA, relic, 1);
        assertLife(playerA, 20);
        assertHandCount(playerA, 0);
    }

}
