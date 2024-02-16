package org.mage.test.cards.abilities.mana;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Note: game engine does not currently support checkPlayable for mana source filter, hence try/catch for tests
 *
 * @author xenohedron
 */
public class SpendOnlyManaProducedByTest extends CardTestPlayerBase {

    private static final String imperiosaur = "Imperiosaur"; // 2GG
    // Spend only mana produced by basic lands to cast this spell.

    private static final String myrSuperion = "Myr Superion"; // 2
    // Spend only mana produced by creatures to cast this spell.

    private static final String securityRhox = "Security Rhox"; // 2RG
    // You may pay {R}{G} rather than pay this spell’s mana cost. Spend only mana produced by Treasures to cast it this way.

    private static final String rapaciousDragon = "Rapacious Dragon"; // 4R
    // When Rapacious Dragon enters the battlefield, create two Treasure tokens.

    @Test
    public void testImperiosaurPlayable() {
        addCard(Zone.HAND, playerA, imperiosaur);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, imperiosaur);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, imperiosaur, 1);
    }

    @Test
    public void tryToCastImperiosaur() {
        addCard(Zone.HAND, playerA, imperiosaur);
        addCard(Zone.BATTLEFIELD, playerA, "Tree of Tales", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, imperiosaur);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        String expectedError = "Can't find ability to activate command: Cast Imperiosaur";
        String foundError = "";
        try {
            execute();
        } catch (AssertionError e) {
            foundError = e.getMessage();
        }
        Assert.assertEquals(expectedError, foundError);
    }

    @Test
    public void tryToCastImperiosaurAgain() {
        addCard(Zone.HAND, playerA, imperiosaur);
        addCard(Zone.BATTLEFIELD, playerA, "Elvish Mystic", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, imperiosaur);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        String expectedError = "Can't find ability to activate command: Cast Imperiosaur";
        String foundError = "";
        try {
            execute();
        } catch (AssertionError e) {
            foundError = e.getMessage();
        }
        Assert.assertEquals(expectedError, foundError);
    }

    @Test
    public void imperiosaurManamorphose() {
        String manamorphose = "Manamorphose"; // add two mana in any combination of colors

        addCard(Zone.HAND, playerA, imperiosaur);
        addCard(Zone.HAND, playerA, manamorphose);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, manamorphose);
        setChoiceAmount(playerA, 0, 0, 0, 0, 2);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, imperiosaur);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        String expectedError = "Can't find ability to activate command: Cast Imperiosaur";
        String foundError = "";
        try {
            execute();
        } catch (AssertionError e) {
            foundError = e.getMessage();
        }
        Assert.assertEquals(expectedError, foundError);
    }

    @Test
    public void tryToCastMyrSuperion() {
        addCard(Zone.HAND, playerA, myrSuperion);
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, myrSuperion);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        String expectedError = "Can't find ability to activate command: Cast Myr Superion";
        String foundError = "";
        try {
            execute();
        } catch (AssertionError e) {
            foundError = e.getMessage();
        }
        Assert.assertEquals(expectedError, foundError);
    }

    @Test
    public void castMyrSuperion() {
        addCard(Zone.HAND, playerA, myrSuperion);
        addCard(Zone.BATTLEFIELD, playerA, "Palladium Myr");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, myrSuperion);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, myrSuperion, 1);
    }

    @Test
    public void castMyrSuperionWithConvoke() {
        addCard(Zone.HAND, playerA, myrSuperion);
        addCard(Zone.BATTLEFIELD, playerA, "Silver Myr");
        addCard(Zone.BATTLEFIELD, playerA, "Chief Engineer"); // artifact creature spells you cast have convoke

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, myrSuperion);
        addTarget(playerA, "Chief Engineer"); // tap for convoke

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, myrSuperion, 1);
    }

    @Test
    public void tryToCastMyrSuperionWithConvoke() {
        addCard(Zone.HAND, playerA, myrSuperion);
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Chief Engineer"); // artifact creature spells you cast have convoke

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, myrSuperion);
        addTarget(playerA, "Chief Engineer"); // tap for convoke

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        String expectedError = "Can't find ability to activate command: Cast Myr Superion";
        String foundError = "";
        try {
            execute();
        } catch (AssertionError e) {
            foundError = e.getMessage();
        }
        Assert.assertEquals(expectedError, foundError);
    }

    @Test
    public void castSecurityRhox() {
        addCard(Zone.HAND, playerA, rapaciousDragon);
        addCard(Zone.HAND, playerA, securityRhox);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, rapaciousDragon);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, securityRhox);
        setChoice(playerA, true); // pay alternative cost
        setChoice(playerA, "Red");
        setChoice(playerA, "Green");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, rapaciousDragon, 1);
        assertPermanentCount(playerA, securityRhox, 1);
    }

    @Test
    public void cantCastSecurityRhox() {
        addCard(Zone.HAND, playerA, securityRhox);
        addCard(Zone.BATTLEFIELD, playerA, "Taiga", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, securityRhox);
        setChoice(playerA, true); // pay alternative cost

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        String expectedError = "Can't find ability to activate command: Cast Security Rhox";
        String foundError = "";
        try {
            execute();
        } catch (AssertionError e) {
            foundError = e.getMessage();
        }
        Assert.assertEquals(expectedError, foundError);
    }

}
