package org.mage.test.cards.single.mh3;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class DetectivesPhoenixTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.d.DetectivesPhoenix Detective's Phoenix} {2}{R}
     * Enchantment Creature — Phoenix
     * Bestow—{R}, Collect evidence 6. (To pay this bestow cost, pay {R} and exile cards with total mana value 6 or greater from your graveyard.)
     * Flying, haste
     * Enchanted creature gets +2/+2 and has flying and haste.
     * You may cast Detective's Phoenix from your graveyard using its bestow ability.
     * 2/2
     */
    private static final String phoenix = "Detective's Phoenix";

    @Test
    public void test_Cast_FromHand_Normal() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        addCard(Zone.HAND, playerA, phoenix);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        checkPlayableAbility("Can cast normal way", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + phoenix, true);
        checkPlayableAbility("Can cast normal way", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + phoenix + " using bestow", false);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, phoenix);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTappedCount("Mountain", true, 3);
        assertPowerToughness(playerA, phoenix, 2, 2);
    }

    @Test
    public void test_Cast_FromHand_Bestow() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        addCard(Zone.HAND, playerA, phoenix);
        addCard(Zone.GRAVEYARD, playerA, "Grave Titan");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        checkPlayableAbility("Can cast normal way", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + phoenix, true);
        checkPlayableAbility("Can cast normal way", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + phoenix + " using bestow", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, phoenix + " using bestow", "Memnite");
        setChoice(playerA, "Grave Titan"); // pay for Collect Evidence.

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTappedCount("Mountain", true, 1);
        assertExileCount("Grave Titan", 1);
        assertPowerToughness(playerA, "Memnite", 1 + 2, 1 + 2);
    }

    @Test
    public void test_Cast_FromGraveyard_Normal_NotPossible() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        addCard(Zone.GRAVEYARD, playerA, phoenix);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        checkPlayableAbility("Can cast normal way", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + phoenix, false);
        checkPlayableAbility("Can cast normal way", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + phoenix + " using bestow", false);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
    }

    @Test
    public void test_Cast_FromGraveyard_Bestow() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        addCard(Zone.GRAVEYARD, playerA, phoenix);
        addCard(Zone.GRAVEYARD, playerA, "Grave Titan");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        checkPlayableAbility("Can cast normal way", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + phoenix, true); // It is a prefix check, so sadly can not check that can't cast using non-bestow
        checkPlayableAbility("Can cast normal way", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + phoenix + " using bestow", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, phoenix + " using bestow", "Memnite");
        setChoice(playerA, "Grave Titan"); // pay for Collect Evidence.

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTappedCount("Mountain", true, 1);
        assertExileCount("Grave Titan", 1);
        assertPowerToughness(playerA, "Memnite", 1 + 2, 1 + 2);
    }

    @Test
    public void test_Cast_FromGraveyard_BestowPossible_NotRegular() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        addCard(Zone.GRAVEYARD, playerA, phoenix);
        addCard(Zone.GRAVEYARD, playerA, "Grave Titan");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        checkPlayableAbility("Can cast normal way", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + phoenix, true); // It is a prefix check, so sadly can not check that can't cast using non-bestow
        checkPlayableAbility("Can cast normal way", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + phoenix + " using bestow", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, phoenix, "Memnite");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        try {
            execute();
            Assert.fail("should have failed");
        } catch (Throwable e) {
            if (!e.getMessage().contains("Cast " + phoenix)) {
                Assert.fail("Should have thrown error about not being able to cast the Phoenix, but got:\n" + e.getMessage());
            }
        }
    }
}
