package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
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

    @Test
    @Ignore // TODO: enable and search code by "int takeMaxTargetsPerChoose"
    public void test_JadeSeedstonesAndMultiTargets() {
        // testing multiple addTarget support (possible bug: one ability can take target definition from other ability)

        // Jade Seedstones:
        // Craft with creature {5}{G}{G} ({5}{G}{G}, Exile this artifact, Exile a creature you control or a
        // creature card from your graveyard: Return this card transformed under its owner’s control.
        // Craft only as a sorcery.)
        // Jadeheart Attendant:
        // When Jadeheart Attendant enters the battlefield, you gain life equal to the mana value of the
        // exiled card used to craft it.
        addCard(Zone.BATTLEFIELD, playerA, "Jade Seedstones"); // {3}{G}
        addCard(Zone.GRAVEYARD, playerA, "Elvish Mystic"); // {G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 8);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves");
        //
        // When Bond Beetle enters the battlefield, put a +1/+1 counter on target creature.
        addCard(Zone.HAND, playerA, "Bond Beetle"); // {G}

        // craft, transform and gain 1 life from exiled elvish
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Craft");
        addTarget(playerA, "Elvish Mystic");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after craft", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Jadeheart Attendant", 1);
        checkLife("after craft", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 20 + 1);

        // cast beetle and add counter to elves
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bond Beetle");
        addTarget(playerA,"Llanowar Elves");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCounters("after beetle", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Llanowar Elves", CounterType.P1P1, 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    /**
     * Ore-Rich Stalactite {1}{R}
     * Artifact
     * {T}: Add {R}. Spend this mana only to cast an instant or sorcery spell.
     * Craft with four or more red instant and/or sorcery cards {3}{R}{R}.
     */
    @Test
    public void test_OreRichStalactite() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1 + 5);
        addCard(Zone.BATTLEFIELD, playerA, "Ore-Rich Stalactite");
        addCard(Zone.GRAVEYARD, playerA, "Ancestral Recall");
        addCard(Zone.GRAVEYARD, playerA, "Arc Lightning");
        addCard(Zone.GRAVEYARD, playerA, "Lightning Helix");
        addCard(Zone.GRAVEYARD, playerA, "Lightning Strike");
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        // Test that craft cannot be activated yet
        checkPlayableAbility("craft not available", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Craft", false);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN, 1);

        // Test that craft can now be activated
        checkPlayableAbility("craft available", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Craft", true);

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Craft");
        addTarget(playerA, "Lightning Bolt^Lightning Helix^Lightning Strike^Arc Lightning");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Cosmium Catalyst", 1);
    }
}
