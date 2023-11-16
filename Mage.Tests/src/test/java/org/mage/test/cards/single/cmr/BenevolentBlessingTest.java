package org.mage.test.cards.single.cmr;

import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommanderDuelBase;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Shashakar
 */

public class BenevolentBlessingTest extends CardTestPlayerBase {

    // Flash
    // Enchant creature
    // As Benevolent Blessing enters the battlefield, choose a color.
    // Enchanted creature has protection from the chosen color. This effect doesn't remove Auras and Equipment you control that are already attached to it.

    @Test
    public void testPlayable() {
        String protectionColor = "White";

        SetupBaseState();

        setChoice(playerA, protectionColor);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Benevolent Blessing", "Serra Angel");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Serra Angel", 1);
        assertPermanentCount(playerA, "Benevolent Blessing", 1);
    }

    @Test
    public void test_DoesNotRemoveAurasOfProtectionColor() {
        String protectionColor = "White";

        SetupBaseState();
        addCard(Zone.HAND, playerA, "Pacifism", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pacifism", "Serra Angel");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        setChoice(playerA, protectionColor);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Benevolent Blessing", "Serra Angel");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Serra Angel", 1);
        assertPermanentCount(playerA, "Benevolent Blessing", 1);
        assertAttachedTo(playerA, "Pacifism", "Serra Angel", true);
    }

    @Test
    public void test_DoesNotRemoveEquipmentOfProtectionColor() {
        String protectionColor = "White";

        SetupBaseState();
        addCard(Zone.BATTLEFIELD, playerA, "Ancestral Katana", 1);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", "Serra Angel");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Benevolent Blessing", "Serra Angel");
        setChoice(playerA, protectionColor);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Serra Angel", 1);
        //assertPermanentCount(playerA, "Benevolent Blessing", 1);

        assertAttachedTo(playerA, "Ancestral Katana", "Serra Angel", true);
    }

    private void SetupBaseState()
    {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Serra Angel", 1);
        addCard(Zone.HAND, playerA, "Benevolent Blessing", 1);
    }
}
