package org.mage.test.cards.single.lcc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.Filter;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class AshlingTheLimitlessTest extends CardTestPlayerBase {

    @Test
    public void testGrantedEvokeDoesNotActivatePrintedEvoke() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Ashling, the Limitless");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.HAND, playerA, "Solitude");
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Solitude");
        setChoice(playerA, "Cast with Evoke alternative cost: {4}");
        setChoice(playerA, "When {this} enters, exile up to one other target creature");
        addTarget(playerA, "Silvercoat Lion");
        addTarget(playerA, TestPlayer.TARGET_SKIP); // Solitude token's enters ability

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Solitude", 1);
        assertPermanentCount(playerA, "Solitude", 1);
        assertExileCount("Silvercoat Lion", 1);
    }

    @Test
    public void testGrantedEvokeCreatesHastyCopy() {
        addCard(Zone.BATTLEFIELD, playerA, "Ashling, the Limitless");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.HAND, playerA, "Thorn Elemental");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thorn Elemental");
        setChoice(playerA, "Cast with Evoke alternative cost: {4}");
        attack(1, playerA, "Thorn Elemental", playerB);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Thorn Elemental", 1);
        assertPermanentCount(playerA, "Thorn Elemental", 1);
        assertLife(playerB, 13);
    }

    @Test
    public void testCounteredGrantedEvokeDoesNotSacrificeCardThatEntersLater() {
        addCard(Zone.BATTLEFIELD, playerA, "Ashling, the Limitless");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.HAND, playerA, "Thorn Elemental");
        addCard(Zone.HAND, playerA, "Reanimate");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, "Counterspell");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thorn Elemental");
        setChoice(playerA, "Cast with Evoke alternative cost: {4}");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Counterspell", "Thorn Elemental");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Reanimate", "Thorn Elemental");

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Thorn Elemental", 0);
        assertPermanentCount(playerA, "Thorn Elemental", 1);
    }

    @Test
    public void testCopyIsSacrificedUnlessWubrgIsPaid() {
        addCard(Zone.BATTLEFIELD, playerA, "Ashling, the Limitless");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.HAND, playerA, "Thorn Elemental");

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}", 4);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thorn Elemental");
        setChoice(playerA, "Cast with Evoke alternative cost: {4}");
        setChoice(playerA, true);

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertGraveyardCount(playerA, "Thorn Elemental", 1);
        assertPermanentCount(playerA, "Thorn Elemental", 1);
    }

    @Test
    public void testDelayedPaymentTriggerRemainsAfterAshlingLeavesBattlefield() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Ashling, the Limitless");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 8);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.HAND, playerA, "Thorn Elemental");
        addCard(Zone.HAND, playerA, "Murder");

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {B}", 4);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thorn Elemental");
        setChoice(playerA, "Cast with Evoke alternative cost: {4}");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {B}", 3);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Murder", "Ashling, the Limitless");
        setChoice(playerA, true); // Pay WUBRG for the delayed trigger despite Ashling being gone

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertGraveyardCount(playerA, "Ashling, the Limitless", 1);
        assertGraveyardCount(playerA, "Thorn Elemental", 1);
        assertPermanentCount(playerA, "Thorn Elemental", 1);
    }

    @Test
    public void testSinglePaymentKeepsAllCopiesCreatedByTokenDoubler() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Ashling, the Limitless");
        addCard(Zone.BATTLEFIELD, playerA, "Parallel Lives");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.HAND, playerA, "Thorn Elemental");

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}", 4);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thorn Elemental");
        setChoice(playerA, "Cast with Evoke alternative cost: {4}");
        setChoice(playerA, true); // One WUBRG payment applies to both copies

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertGraveyardCount(playerA, "Thorn Elemental", 1);
        assertPermanentCount(playerA, "Thorn Elemental", 2);
    }

    @Test
    public void testJubilationWithTwinflameTravelersAndYarok() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Ashling, the Limitless");
        addCard(Zone.BATTLEFIELD, playerA, "Twinflame Travelers");
        addCard(Zone.BATTLEFIELD, playerA, "Yarok, the Desecrated");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.HAND, playerA, "Jubilation");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Jubilation");
        setChoice(playerA, "Cast with Evoke alternative cost: {4}");
        setChoice(playerA, "Yarok, the Desecrated"); // Order Yarok and Twinflame for the original ETB
        setChoice(playerA, "When this permanent enters"); // Resolve evoke before Jubilation's triggers
        setChoice(playerA, "When {this} enters", 2); // Order the original's three identical triggers
        setChoice(playerA, "Whenever you sacrifice a nontoken Elemental"); // Order Ashling's two triggers
        setChoice(playerA, "Yarok, the Desecrated"); // Order replacements for the first token's ETB
        setChoice(playerA, "When {this} enters", 2); // Order the first token's three triggers
        setChoice(playerA, "Yarok, the Desecrated"); // Order replacements for the second token's ETB
        setChoice(playerA, "When {this} enters", 2); // Order the second token's three triggers

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // Jubilation's original ETB and each token's ETB trigger three times: once
        // normally, once for Yarok, and once for Twinflame Travelers. Twinflame
        // also makes Ashling's sacrifice ability trigger twice, creating two tokens.
        assertGraveyardCount(playerA, "Jubilation", 1);
        assertPermanentCount(playerA, "Jubilation", 2);
        assertPowerToughness(playerA, "Ashling, the Limitless", 2 + 18, 3 + 18);
        assertPowerToughness(playerA, "Twinflame Travelers", 3 + 18, 3 + 18);
        assertPowerToughness(playerA, "Yarok, the Desecrated", 3 + 18, 5 + 18);
        assertPowerToughness(playerA, "Jubilation", 5 + 12, 5 + 12, Filter.ComparisonScope.Any);
        assertPowerToughness(playerA, "Jubilation", 5 + 6, 5 + 6, Filter.ComparisonScope.Any);
    }
}
