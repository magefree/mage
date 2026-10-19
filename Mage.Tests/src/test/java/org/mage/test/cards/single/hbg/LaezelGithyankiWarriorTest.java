package org.mage.test.cards.single.hbg;

import mage.constants.PhaseStep;
import mage.constants.Zone;

import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class LaezelGithyankiWarriorTest extends CardTestPlayerBase {

    @Test
    public void testTokenCanSpecialize() {
        addCard(Zone.BATTLEFIELD, playerB, "Lae'zel, Githyanki Warrior");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, "Croaking Counterpart");
        addCard(Zone.HAND, playerA, "Mountain");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Croaking Counterpart", "Lae'zel, Githyanki Warrior");
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Specialize");
        setChoice(playerA, "Mountain");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, "Lae'zel, Wrathful Warrior", 3, 6);
        assertPermanentCount(playerA, "Soldier Token", 2);
        assertPermanentCount(playerB, "Lae'zel, Githyanki Warrior", 1);
    }

    @Test
    public void testCopyCanSpecializeAndKeepsFormAfterBlink() {
        addCard(Zone.BATTLEFIELD, playerB, "Lae'zel, Githyanki Warrior");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.HAND, playerA, "Clone");
        addCard(Zone.HAND, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Cloudshift");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Clone");
        setChoice(playerA, true);
        setChoice(playerA, "Lae'zel, Githyanki Warrior");
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Specialize");
        setChoice(playerA, "Mountain");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cloudshift", "Lae'zel, Wrathful Warrior");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Lae'zel, Wrathful Warrior", 3, 6);
        assertPermanentCount(playerA, "Soldier Token", 4);
    }

    @Test
    public void testSpecializeRequiresSorceryTimingWithEligibleDiscard() {
        addCard(Zone.BATTLEFIELD, playerA, "Lae'zel, Githyanki Warrior");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.HAND, playerA, "Forest");
        checkPlayableAbility("upkeep", 1, PhaseStep.UPKEEP, playerA, "Specialize", false);
        checkPlayableAbility("main phase", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Specialize", true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
    }

    @Test
    public void testWhiteSpecializationSeeksAndPersistsThroughBlink() {
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");
        addCard(Zone.LIBRARY, playerA, "Hill Giant");
        addCard(Zone.BATTLEFIELD, playerA, "Lae'zel, Githyanki Warrior");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Plains");
        addCard(Zone.HAND, playerA, "Cloudshift");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Specialize");
        setChoice(playerA, "Plains");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cloudshift", "Lae'zel, Blessed Warrior");
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Lae'zel, Blessed Warrior", 3, 6);
        assertHandCount(playerA, "Grizzly Bears", 1);
        assertLibraryCount(playerA, "Hill Giant", 1);
        assertPermanentCount(playerA, "Lae'zel, Githyanki Warrior", 0);
    }

    @Test
    public void testBlueSpecializationConjuresPrismaticDuplicate() {
        removeAllCardsFromLibrary(playerB);
        addCard(Zone.LIBRARY, playerB, "Grizzly Bears");
        addCard(Zone.LIBRARY, playerB, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Lae'zel, Githyanki Warrior");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.HAND, playerA, "Island");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Specialize");
        setChoice(playerA, "Island");
        setChoice(playerA, playerB.getName());
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grizzly Bears");
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Lae'zel, Illithid Thrall", 3, 6);
        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertLibraryCount(playerB, "Grizzly Bears", 1);
    }

    @Test
    public void testBlackSpecializationReturnsTwoWithCombinedManaValueThree() {
        addCard(Zone.BATTLEFIELD, playerA, "Lae'zel, Githyanki Warrior");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.HAND, playerA, "Swamp");
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears");
        addCard(Zone.GRAVEYARD, playerA, "Llanowar Elves");
        addCard(Zone.GRAVEYARD, playerA, "Hill Giant");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Specialize");
        setChoice(playerA, "Swamp");
        addTarget(playerA, "Grizzly Bears^Llanowar Elves");
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Lae'zel, Callous Warrior", 3, 6);
        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertPermanentCount(playerA, "Llanowar Elves", 1);
        assertGraveyardCount(playerA, "Hill Giant", 1);
    }

    @Test
    public void testRedSpecializationCreatesTwoSoldiers() {
        addCard(Zone.BATTLEFIELD, playerA, "Lae'zel, Githyanki Warrior");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.HAND, playerA, "Mountain");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Specialize");
        setChoice(playerA, "Mountain");
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Lae'zel, Wrathful Warrior", 3, 6);
        assertPermanentCount(playerA, "Soldier Token", 2);
    }

    @Test
    public void testGreenSpecializationBoostsHandAndOtherCreatures() {
        addCard(Zone.BATTLEFIELD, playerA, "Lae'zel, Githyanki Warrior");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.HAND, playerA, "Forest");
        addCard(Zone.HAND, playerA, "Hill Giant");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Specialize");
        setChoice(playerA, "Forest");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Hill Giant");
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Lae'zel, Primal Warrior", 3, 6);
        assertPowerToughness(playerA, "Grizzly Bears", 3, 3);
        assertPowerToughness(playerA, "Hill Giant", 4, 4);
    }

    @Test
    public void testColorlessCardWithoutLandTypeCannotPayAndSorceryTiming() {
        addCard(Zone.BATTLEFIELD, playerA, "Lae'zel, Githyanki Warrior");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.HAND, playerA, "Memnite");
        checkPlayableAbility("colorless cannot pay", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Specialize", false);
        checkPlayableAbility("sorcery timing", 1, PhaseStep.UPKEEP, playerA, "Specialize", false);
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Lae'zel, Githyanki Warrior", 1);
    }

    @Test
    public void testCastShieldBlinksOnlyOnce() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.HAND, playerA, "Lae'zel, Githyanki Warrior");
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 4);
        addCard(Zone.HAND, playerB, "Doom Blade", 2);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lae'zel, Githyanki Warrior");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Doom Blade", "Lae'zel, Githyanki Warrior");
        setChoice(playerA, true);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerB, "Doom Blade", "Lae'zel, Githyanki Warrior");
        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Lae'zel, Githyanki Warrior", 1);
        assertPermanentCount(playerA, "Lae'zel, Githyanki Warrior", 0);
    }

}
