package org.mage.test.cards.single.iko;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class KinnanBonderProdigyTest extends CardTestPlayerBase {

    private static final String kinnan = "Kinnan, Bonder Prodigy";
    private static final String egg = "Golden Egg";
    private static final String hovermyr = "Hovermyr";
    private static final String strike = "Strike It Rich";

    @Test
    public void testSacrificedPermanent() {
        // Whenever you tap a nonland permanent for mana, add one mana of any type that permanent produced.
        addCard(Zone.BATTLEFIELD, playerA, kinnan);
        //
        // {1}, {T}, Sacrifice Golden Egg: Add one mana of any color.
        addCard(Zone.BATTLEFIELD, playerA, egg);
        //
        addCard(Zone.HAND, playerA, hovermyr); // {2}
        addCard(Zone.BATTLEFIELD, playerA, "Forest");

        // sacrifice egg and add additional mana
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1},");
        setChoice(playerA, "Red");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, hovermyr);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, hovermyr, 1);
        assertPermanentCount(playerA, egg, 0);
        assertGraveyardCount(playerA, egg, 1);
    }

    @Test
    public void testSacrificedToken() {
        // Whenever you tap a nonland permanent for mana, add one mana of any type that permanent produced.
        addCard(Zone.BATTLEFIELD, playerA, kinnan);
        //
        // Create a Treasure token.
        addCard(Zone.HAND, playerA, strike); // {R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        //
        addCard(Zone.HAND, playerA, hovermyr); // {2}

        // prepare treasure token
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, strike);

        // sacrifice treasure and add additional mana
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Sacrifice");
        setChoice(playerA, "Red");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, hovermyr);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, hovermyr, 1);
        assertPermanentCount(playerA, "Treasure Token", 0);
    }
}
