package org.mage.test.cards.continuous;

import mage.abilities.keyword.FirstStrikeAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4Players;

/**
 * @author JayDi85
 */
public class CommandersCastTest extends CardTestCommander4Players {

    // Player order: A -> D -> C -> B

    @Test
    public void test_CastToBattlefieldOneTime() {
        addCard(Zone.COMMAND, playerA, "Balduvian Bears", 1); // {1}{G}, 2/2, commander
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        showCommand("commanders", 1, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();

        assertCommandZoneCount(playerA, "Balduvian Bears", 0);
        assertPermanentCount(playerA, "Balduvian Bears", 1);
        assertTappedCount("Forest", true, 2);
    }

    @Test
    public void test_CastToBattlefieldTwoTimes() {
        // Player order: A -> D -> C -> B
        addCard(Zone.COMMAND, playerA, "Balduvian Bears", 1); // {1}{G}, 2/2, commander
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6); // 2 + 4
        //
        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        // cast 1
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after cast 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears", 1);

        // destroy commander
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", "Balduvian Bears");
        setChoice(playerA, "Yes"); // put to command zone again
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after destroy", 1, PhaseStep.PRECOMBAT_MAIN, playerB, playerA, "Balduvian Bears", 0);

        // cast 2
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Balduvian Bears");
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        checkPermanentCount("after cast 2", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Balduvian Bears", 1);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();

        assertCommandZoneCount(playerA, "Balduvian Bears", 0);
        assertPermanentCount(playerA, "Balduvian Bears", 1);
        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertTappedCount("Forest", true, 2 + 4);
    }

    @Test
    public void test_PlayAsLandOneTime() {
        addCard(Zone.COMMAND, playerA, "Academy Ruins", 1);

        showAvaileableAbilities("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA);
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Academy Ruins");
        //castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Academy Ruins");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();

        assertCommandZoneCount(playerA, "Academy Ruins", 0);
        assertPermanentCount(playerA, "Academy Ruins", 1);
    }

    @Test
    public void test_PlayAsLandTwoTimes() {
        // Player order: A -> D -> C -> B
        addCard(Zone.COMMAND, playerA, "Academy Ruins", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2); // 0 + 2
        //
        addCard(Zone.HAND, playerA, "Pillage", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        // cast 1
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Academy Ruins");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after play 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Academy Ruins", 1);

        // destroy commander land
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pillage", "Academy Ruins");
        setChoice(playerA, "Yes"); // put to command zone again
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after destroy", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Academy Ruins", 0);

        // remove unnecessary mana, only 2 forest need (workaround to remove random mana payments)
        activateManaAbility(5, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateManaAbility(5, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateManaAbility(5, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");

        // cast 2
        playLand(5, PhaseStep.POSTCOMBAT_MAIN, playerA, "Academy Ruins");
        waitStackResolved(5, PhaseStep.POSTCOMBAT_MAIN);
        checkPermanentCount("after cast 2", 5, PhaseStep.POSTCOMBAT_MAIN, playerA, "Academy Ruins", 1);

        showBattlefield("end battlefield", 5, PhaseStep.END_TURN, playerA);

        setStopAt(5, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();

        assertCommandZoneCount(playerA, "Academy Ruins", 0);
        assertPermanentCount(playerA, "Academy Ruins", 1);
        assertGraveyardCount(playerA, "Pillage", 1);
        assertTappedCount("Forest", true, 2);
        assertTappedCount("Mountain", true, 3);
    }

    @Test
    public void test_ModesNormal() {
        // Player order: A -> D -> C -> B

        // Choose four. You may choose the same mode more than once.
        // • Create a 2/2 Citizen creature token that’s all colors.
        // • Return target permanent card from your graveyard to your hand.
        // • Proliferate.
        // • You gain 4 life.
        addCard(Zone.HAND, playerA, "Planewide Celebration", 1); // {5}{G}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 7);

        // cast (3 tokens + 4 life)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Planewide Celebration");
        setModeChoice(playerA, "1");
        setModeChoice(playerA, "1");
        setModeChoice(playerA, "1");
        setModeChoice(playerA, "4");

        checkPermanentCount("after", 1, PhaseStep.BEGIN_COMBAT, playerA, "Citizen", 3);
        checkLife("after", 1, PhaseStep.BEGIN_COMBAT, playerA, 20 + 4);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();
    }

    @Test
    public void test_ModesCommander() {
        // Player order: A -> D -> C -> B

        // Choose four. You may choose the same mode more than once.
        // • Create a 2/2 Citizen creature token that’s all colors.
        // • Return target permanent card from your graveyard to your hand.
        // • Proliferate.
        // • You gain 4 life.
        addCard(Zone.COMMAND, playerA, "Planewide Celebration", 1); // {5}{G}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 7);

        // cast (3 tokens + 4 life)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Planewide Celebration");
        setModeChoice(playerA, "1");
        setModeChoice(playerA, "1");
        setModeChoice(playerA, "1");
        setModeChoice(playerA, "4");
        setChoice(playerA, "Yes"); // return commander

        checkPermanentCount("after", 1, PhaseStep.BEGIN_COMBAT, playerA, "Citizen", 3);
        checkLife("after", 1, PhaseStep.BEGIN_COMBAT, playerA, 20 + 4);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();
    }

    @Test
    public void test_CastFromHandWithoutTaxIncrease() {
        // Player order: A -> D -> C -> B

        addCard(Zone.COMMAND, playerA, "Balduvian Bears", 1); // {1}{G}, 2/2, commander
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4); // cast from command for {1}{G}, from hand for {1}{G}, from command for {1}{G}{2}
        //
        // Counter target spell. If that spell is countered this way, put it into its owner’s hand instead of into that player’s graveyard.
        // Draw a card.
        addCard(Zone.HAND, playerB, "Remand", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2); // counter 2 times

        // cast 1 and counter (increase commander tax)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Remand", "Balduvian Bears", "Balduvian Bears");
        setChoice(playerA, "No"); // move to hand
        checkCommandCardCount("cast 1", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Balduvian Bears", 0);
        checkHandCardCount("cast 1", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Balduvian Bears", 1);
        checkPermanentCount("cast 1", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Balduvian Bears", 0);

        ///*
        // cast 2 from hand without tax
        castSpell(5, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears");
        castSpell(5, PhaseStep.PRECOMBAT_MAIN, playerB, "Remand", "Balduvian Bears", "Balduvian Bears");
        setChoice(playerA, "Yes"); // move to command zone
        checkCommandCardCount("cast 2", 5, PhaseStep.POSTCOMBAT_MAIN, playerA, "Balduvian Bears", 1);
        checkHandCardCount("cast 2", 5, PhaseStep.POSTCOMBAT_MAIN, playerA, "Balduvian Bears", 0);
        checkPermanentCount("cast 2", 5, PhaseStep.POSTCOMBAT_MAIN, playerA, "Balduvian Bears", 0);

        // cast 3 from command with tax for 1 play
        castSpell(9, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears");
        checkCommandCardCount("cast 3", 9, PhaseStep.BEGIN_COMBAT, playerA, "Balduvian Bears", 0);
        checkHandCardCount("cast 3", 9, PhaseStep.BEGIN_COMBAT, playerA, "Balduvian Bears", 0);
        checkPermanentCount("cast 3", 9, PhaseStep.BEGIN_COMBAT, playerA, "Balduvian Bears", 1);

        setStrictChooseMode(true);
        setStopAt(9, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
    }

    @Test
    public void test_AlternativeSpellNormal() {
        // Player order: A -> D -> C -> B

        // Weapon Surge
        // Target creature you control gets +1/+0 and gains first strike until end of turn.
        // Overload {1}{R} (You may cast this spell for its overload cost. If you do, change its text by replacing all instances of “target” with “each.”)
        addCard(Zone.HAND, playerA, "Weapon Surge", 1); // {R} or {1}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 2);

        // cast overload
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Weapon Surge with overload");
        checkAbility("after", 1, PhaseStep.BEGIN_COMBAT, playerA, "Balduvian Bears", FirstStrikeAbility.class, true);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();
    }

    @Test
    public void test_AlternativeSpellCommander() {
        // Player order: A -> D -> C -> B

        // Weapon Surge
        // Target creature you control gets +1/+0 and gains first strike until end of turn.
        // Overload {1}{R} (You may cast this spell for its overload cost. If you do, change its text by replacing all instances of “target” with “each.”)
        addCard(Zone.COMMAND, playerA, "Weapon Surge", 1); // {R} or {1}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 2);

        // cast overload
        showAvaileableAbilities("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Weapon Surge with overload");
        setChoice(playerA, "Yes"); // move to command zone
        checkAbility("after", 1, PhaseStep.BEGIN_COMBAT, playerA, "Balduvian Bears", FirstStrikeAbility.class, true);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();
    }
}
