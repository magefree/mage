package org.mage.test.cards.single.mh3;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class AbstruseAppropriationTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.a.AbstruseAppropriation Abstruse Appropriation} {2}{W}{B}
     * Instant
     * Devoid (This card has no color.)
     * Exile target nonland permanent. You may cast that card for as long as it remains exiled, and you may spend colorless mana as though it were mana of any color to cast that spell.
     */
    private static final String appropriation = "Abstruse Appropriation";

    @Test
    public void test_NoColorless() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, appropriation);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.BATTLEFIELD, playerB, "Darkheart Sliver"); // cost {B}{G}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, appropriation, "Darkheart Sliver", true);
        checkPlayableAbility(
                "Can not cast Darkheart Sliver out of Swamps",
                1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Darkheart Sliver",
                true // Should be false, but the playability check does not compute the "may spend colorless" at this point.
        );
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Darkheart Sliver");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        try {
            execute();
            Assert.fail("should have failed to execute on cast");
        } catch (Throwable e) {
            if (!e.getMessage().contains("Can't find ability to activate command: Cast Darkheart Sliver")) {
                Assert.fail("Should have thrown error about not being able to cast Darkheart Sliver, but got:\n" + e.getMessage());
            }
        }
    }

    @Test
    public void test_ColorlessAsAnyColor() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, appropriation);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.HAND, playerA, "Wastes", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Darkheart Sliver"); // cost {B}{G}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, appropriation, "Darkheart Sliver", true);
        checkPlayableAbility(
                "Can not cast Darkheart Sliver out of 1 mana",
                1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Darkheart Sliver", false
        );
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wastes");
        checkPlayableAbility(
                "Can cast Darkheart Sliver out of {B}{C} with the effect from Appropriation",
                1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Darkheart Sliver", true
        );
        // tap the Swamp so that its mana will be used in priority compared to the Wastes mana.
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {B}");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Darkheart Sliver");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Darkheart Sliver", 1);
        assertTappedCount("Wastes", true, 1);
        assertTappedCount("Plains", true, 1);
        assertTappedCount("Swamp", true, 4);
    }
}
