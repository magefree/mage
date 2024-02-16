
package org.mage.test.cards.mana;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class CryptGhastTest extends CardTestPlayerBase {

    @Test
    public void TestNormal() {
        //Extort (Whenever you cast a spell, you may pay {WB}. If you do, each opponent loses 1 life and you gain that much life.)
        // Whenever you tap a Swamp for mana, add {B} (in addition to the mana the land produces).
        addCard(Zone.BATTLEFIELD, playerA, "Crypt Ghast", 1);
        addCard(Zone.HAND, playerA, "Erebos's Titan", 1);

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        // Swamp Mountain
        addCard(Zone.BATTLEFIELD, playerA, "Badlands", 1);

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {B}");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Erebos's Titan");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Erebos's Titan", 1);

    }

    /**
     * Crypt Ghast was still effective (doubling swamp {B} Mana and providiong
     * the option to extort) as if it was on the battlefield after being killed
     * with Nin, the Pain Artist controlled by me and then exiled into a Mimic
     * Vat controlled by Crypt Ghast's controller.
     */
    @Test
    public void TestExiled() {
        // Extort (Whenever you cast a spell, you may pay {WB}. If you do, each opponent loses 1 life and you gain that much life.)
        // Whenever you tap a Swamp for mana, add {B} (in addition to the mana the land produces).
        addCard(Zone.BATTLEFIELD, playerA, "Crypt Ghast", 1);
        // Imprint - Whenever a nontoken creature dies, you may exile that card. If you do, return each other card exiled with Mimic Vat to its owner's graveyard.
        // {3},{T}: Create a tokenonto the battlefield that's a copy of the exiled card. It gains haste. Exile it at the beginning of the next end step.
        addCard(Zone.BATTLEFIELD, playerA, "Mimic Vat", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        // Swamp Mountain
        addCard(Zone.BATTLEFIELD, playerA, "Badlands", 1);
        addCard(Zone.HAND, playerA, "Erebos's Titan", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);
        // {X}{U}{R},{T}: Nin, the Pain Artist deals X damage to target creature. That creature's controller draws X cards.
        addCard(Zone.BATTLEFIELD, playerB, "Nin, the Pain Artist", 1);

        // Remove the Crypt Ghast
        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{X}{U}{R}, {T}: {this} deals X damage to target creature", "Crypt Ghast");
        setChoice(playerB, "X=2");

        // Without Crypt Ghast, the land won't give extra mana
        checkPlayableAbility("Not enough mana", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Erebos's", false);

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertTapped("Nin, the Pain Artist", true);
        assertExileCount("Crypt Ghast", 1);
    }
}
