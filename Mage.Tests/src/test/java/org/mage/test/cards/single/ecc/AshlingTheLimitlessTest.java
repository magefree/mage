package org.mage.test.cards.single.ecc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class AshlingTheLimitlessTest extends CardTestPlayerBase {

    @Test
    public void testGrantedEvokeMakesElementalPermanentSpellPlayable() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Ashling, the Limitless");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.HAND, playerA, "Air Elemental");

        checkPlayableAbility("Can cast using Ashling's granted evoke", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Air Elemental", true);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
    }

    @Test
    public void testGrantedEvokeDoesNotApplyToNonElementalPermanentSpell() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Ashling, the Limitless");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.HAND, playerA, "Serra Angel");

        checkPlayableAbility("Cannot cast non-Elemental spell with Ashling's granted evoke", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Serra Angel", false);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
    }

    @Test
    public void testGrantedEvokeStillRequiresEvokeCostToBePayable() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Ashling, the Limitless");
        addCard(Zone.HAND, playerA, "Air Elemental");

        checkPlayableAbility("Cannot cast Elemental spell with Ashling's granted evoke without mana", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Air Elemental", false);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
    }

    @Test
    public void testSacrificingNontokenElementalCreatesOneTokenCopy() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Ashling, the Limitless");
        addCard(Zone.BATTLEFIELD, playerA, "Ashnod's Altar");
        addCard(Zone.BATTLEFIELD, playerA, "Air Elemental");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice a creature: Add {C}{C}");
        setChoice(playerA, "Air Elemental");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice a creature: Add {C}{C}");
        setChoice(playerA, "Air Elemental");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Air Elemental", 1);
        assertPermanentCount(playerA, "Air Elemental", 0);
    }

    @Test
    public void testEndStepSacrificeOfTokenFromAshling() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Ashling, the Limitless");
        addCard(Zone.BATTLEFIELD, playerA, "Ashnod's Altar");
        addCard(Zone.BATTLEFIELD, playerA, "Air Elemental");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice a creature: Add {C}{C}");
        setChoice(playerA, "Air Elemental");
        setChoice(playerA, false); // Pay {W}{U}{B}{R}{G}? If you don't, sacrifice the token.

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Air Elemental", 1);
        assertPermanentCount(playerA, "Air Elemental", 0);
    }

    @Test
    public void testEndStepPaymentKeepsTokenFromAshling() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Ashling, the Limitless");
        addCard(Zone.BATTLEFIELD, playerA, "Ashnod's Altar");
        addCard(Zone.BATTLEFIELD, playerA, "Air Elemental");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice a creature: Add {C}{C}");
        setChoice(playerA, "Air Elemental");
        setChoice(playerA, true); // Pay {W}{U}{B}{R}{G}? If you don't, sacrifice the token.

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Air Elemental", 1);
        assertPermanentCount(playerA, "Air Elemental", 1);
    }
}
