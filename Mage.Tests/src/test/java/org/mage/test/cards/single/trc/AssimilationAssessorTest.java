package org.mage.test.cards.single.trc;

import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class AssimilationAssessorTest extends CardTestPlayerBase {

    @Test
    public void test_EntersExilesOpponentsGraveyard_AttackAssimilatesSingleExiledCreature() {
        // When this creature enters, exile target opponent's graveyard.
        // Whenever this creature attacks, assimilate a creature card exiled with it.
        addCard(Zone.HAND, playerA, "Assimilation Assessor"); // {4}{B} 5/5 Borg
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        //
        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion"); // own graveyard card, must not be exiled
        addCard(Zone.GRAVEYARD, playerB, "Grizzly Bears"); // 2/2 Bear
        addCard(Zone.GRAVEYARD, playerB, "Sol Ring"); // noncreature artifact card

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Assimilation Assessor");
        addTarget(playerA, playerB); // exile opponent's graveyard

        checkGraveyardCount("opponent's graveyard exiled", 1, PhaseStep.BEGIN_COMBAT, playerB, "Grizzly Bears", 0);
        checkExileCount("Grizzly Bears exiled", 1, PhaseStep.BEGIN_COMBAT, playerB, "Grizzly Bears", 1);
        checkExileCount("Sol Ring exiled", 1, PhaseStep.BEGIN_COMBAT, playerB, "Sol Ring", 1);
        checkGraveyardCount("own graveyard untouched", 1, PhaseStep.BEGIN_COMBAT, playerA, "Silvercoat Lion", 1);

        // Only one creature card is exiled with Assimilation Assessor, so it is assimilated without a choice dialog
        attack(3, playerA, "Assimilation Assessor", playerB);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertExileCount(playerB, "Grizzly Bears", 0);
        assertExileCount(playerB, "Sol Ring", 1);
        assertGraveyardCount(playerA, "Silvercoat Lion", 1);
        // gain
        assertType("Grizzly Bears", CardType.ARTIFACT, true);
        assertType("Grizzly Bears", CardType.CREATURE, true);
        assertSubtype("Grizzly Bears", SubType.BORG);
        assertCounterCount(playerA, "Grizzly Bears", CounterType.P1P1, 1);
        assertPowerToughness(playerA, "Grizzly Bears", 2 + 1, 2 + 1);
        // lost
        assertNotSubtype("Grizzly Bears", SubType.BEAR);
        assertLife(playerB, 20 - 5);
    }

    @Test
    public void test_AttackWithMultipleExiledCreatures_ChooseOnePerAttack() {
        // When this creature enters, exile target opponent's graveyard.
        // Whenever this creature attacks, assimilate a creature card exiled with it.
        addCard(Zone.HAND, playerA, "Assimilation Assessor"); // {4}{B} 5/5 Borg
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        //
        addCard(Zone.GRAVEYARD, playerB, "Grizzly Bears"); // 2/2 Bear
        addCard(Zone.GRAVEYARD, playerB, "Hill Giant"); // 3/3 Giant

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Assimilation Assessor");
        addTarget(playerA, playerB); // exile opponent's graveyard

        // First attack: two creature cards in exile, choose Hill Giant
        attack(3, playerA, "Assimilation Assessor", playerB);
        setChoice(playerA, "Hill Giant"); // choose creature card exiled with Assimilation Assessor

        checkPermanentCount("Hill Giant assimilated on turn 3", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Hill Giant", 1);
        checkExileCount("Grizzly Bears still exiled on turn 3", 3, PhaseStep.POSTCOMBAT_MAIN, playerB, "Grizzly Bears", 1);

        // Second attack: only Grizzly Bears remains in exile, assimilated automatically
        attack(5, playerA, "Assimilation Assessor", playerB);

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Hill Giant", 1);
        assertType("Hill Giant", CardType.ARTIFACT, true);
        assertSubtype("Hill Giant", SubType.BORG);
        assertNotSubtype("Hill Giant", SubType.GIANT);
        assertCounterCount(playerA, "Hill Giant", CounterType.P1P1, 1);
        assertPowerToughness(playerA, "Hill Giant", 3 + 1, 3 + 1);

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertType("Grizzly Bears", CardType.ARTIFACT, true);
        assertSubtype("Grizzly Bears", SubType.BORG);
        assertNotSubtype("Grizzly Bears", SubType.BEAR);
        assertCounterCount(playerA, "Grizzly Bears", CounterType.P1P1, 1);
        assertPowerToughness(playerA, "Grizzly Bears", 2 + 1, 2 + 1);
        assertExileCount(playerB, 0);
    }

    @Test
    public void test_AttackWithOnlyNoncreatureCardsExiled_DoesNothing() {
        // When this creature enters, exile target opponent's graveyard.
        // Whenever this creature attacks, assimilate a creature card exiled with it.
        addCard(Zone.HAND, playerA, "Assimilation Assessor"); // {4}{B} 5/5 Borg
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        //
        addCard(Zone.GRAVEYARD, playerB, "Sol Ring"); // noncreature artifact
        addCard(Zone.GRAVEYARD, playerB, "Island"); // land

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Assimilation Assessor");
        addTarget(playerA, playerB); // exile opponent's graveyard

        attack(3, playerA, "Assimilation Assessor", playerB);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertExileCount(playerB, "Sol Ring", 1);
        assertExileCount(playerB, "Island", 1);
        assertPermanentCount(playerA, "Sol Ring", 0);
        assertPermanentCount(playerA, "Island", 0);
        assertLife(playerB, 20 - 5);
    }

    @Test
    public void test_AttackDoesNotAssimilateCreatureExiledByAnotherCard() {
        // When this creature enters, exile target opponent's graveyard.
        // Whenever this creature attacks, assimilate a creature card exiled with it.
        addCard(Zone.HAND, playerA, "Assimilation Assessor"); // {4}{B} 5/5 Borg
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCustomEffect_TargetExile(playerA);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Hill Giant"); // 3/3 Giant, exiled by another effect

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target exile", "Hill Giant");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Assimilation Assessor");
        addTarget(playerA, playerB); // exile opponent's empty graveyard

        attack(3, playerA, "Assimilation Assessor", playerB);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertExileCount(playerB, "Hill Giant", 1);
        assertPermanentCount(playerA, "Hill Giant", 0);
    }

    @Test
    public void test_BlinkBeforeAttack_DoesNotAssimilatePreviouslyExiledCreature() {
        // When this creature enters, exile target opponent's graveyard.
        // Whenever this creature attacks, assimilate a creature card exiled with it.
        addCard(Zone.HAND, playerA, "Assimilation Assessor"); // {4}{B} 5/5 Borg
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCustomEffect_TargetBlink(playerA);
        //
        addCard(Zone.GRAVEYARD, playerB, "Grizzly Bears"); // 2/2 Bear

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Assimilation Assessor");
        addTarget(playerA, playerB); // first ETB exiles Grizzly Bears
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // Blink makes Assimilation Assessor a new object; its linked attack ability cannot see cards exiled by the old object
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target blink", "Assimilation Assessor");
        addTarget(playerA, playerB); // second ETB targets playerB (now empty graveyard)

        attack(3, playerA, "Assimilation Assessor", playerB);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertExileCount(playerB, "Grizzly Bears", 1);
        assertPermanentCount(playerA, "Grizzly Bears", 0);
        assertLife(playerB, 20 - 5);
    }

    @Test
    public void test_DestroyedInResponseToAttackTrigger_StillAssimilatesExiledCreature() {
        // When this creature enters, exile target opponent's graveyard.
        // Whenever this creature attacks, assimilate a creature card exiled with it.
        addCard(Zone.HAND, playerA, "Assimilation Assessor"); // {4}{B} 5/5 Borg
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        //
        addCard(Zone.GRAVEYARD, playerB, "Grizzly Bears"); // 2/2 Bear
        addCustomEffect_TargetDestroy(playerB);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Assimilation Assessor");
        addTarget(playerA, playerB); // exile opponent's graveyard

        // Destroy Assimilation Assessor while its attack trigger is on the stack
        attack(3, playerA, "Assimilation Assessor", playerB);
        activateAbility(3, PhaseStep.DECLARE_ATTACKERS, playerB, "target destroy", "Assimilation Assessor");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Assimilation Assessor", 0);
        assertGraveyardCount(playerA, "Assimilation Assessor", 1);
        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertExileCount(playerB, "Grizzly Bears", 0);
        assertType("Grizzly Bears", CardType.ARTIFACT, true);
        assertSubtype("Grizzly Bears", SubType.BORG);
        assertCounterCount(playerA, "Grizzly Bears", CounterType.P1P1, 1);
        assertPowerToughness(playerA, "Grizzly Bears", 2 + 1, 2 + 1);
    }

    @Test
    public void test_AssimilatesDoubleFacedCreatureExiledWithIt_DelverOfSecrets() {
        // When this creature enters, exile target opponent's graveyard.
        // Whenever this creature attacks, assimilate a creature card exiled with it.
        addCard(Zone.HAND, playerA, "Assimilation Assessor"); // {4}{B} 5/5 Borg
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        //
        addCard(Zone.GRAVEYARD, playerB, "Delver of Secrets"); // TDFC 1/1 Human Wizard

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Assimilation Assessor");
        addTarget(playerA, playerB); // exile opponent's graveyard

        attack(3, playerA, "Assimilation Assessor", playerB);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Delver of Secrets", 1);
        // gain
        assertType("Delver of Secrets", CardType.ARTIFACT, true);
        assertType("Delver of Secrets", CardType.CREATURE, true);
        assertSubtype("Delver of Secrets", SubType.BORG);
        assertCounterCount(playerA, "Delver of Secrets", CounterType.P1P1, 1);
        assertPowerToughness(playerA, "Delver of Secrets", 1 + 1, 1 + 1);
        // lost
        assertNotSubtype("Delver of Secrets", SubType.HUMAN);
        assertNotSubtype("Delver of Secrets", SubType.WIZARD);
    }
}
