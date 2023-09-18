package org.mage.test.cards.single.dka;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.a.AltarOfTheLost Altar of the Lost}
 * {3}
 * Artifact
 * Altar of the Lost enters the battlefield tapped.
 * {T}: Add two mana in any combination of colors.
 *      Spend this mana only to cast spells with flashback from a graveyard.
 *
 * @author BetaSteward
 */
public class AltarOfTheLostTest extends CardTestPlayerBase {

    /**
     * Test that the mana can be used to cast the spell from the graveyard using flashback.
     */
    @Test
    public void testFlashback() {
        addCard(Zone.BATTLEFIELD, playerA, "Altar of the Lost");
        // Put two 1/1 white Spirit creature tokens with flying onto the battlefield.
        // Flashback {1}{B}
        addCard(Zone.GRAVEYARD, playerA, "Lingering Souls");

        // Add 2 black mana (mana choice in WUBRG order)
        setChoice(playerA, "X=0");
        setChoice(playerA, "X=0");
        setChoice(playerA, "X=2");
        setChoice(playerA, TestPlayer.CHOICE_SKIP);

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback {1}{B}");
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Spirit Token", 2);
    }

    /**
     * Test that the same card cannot be cast from the hand even though it has a flashback ability.
     */
    @Test
    public void testCantCastFromHand() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains",2 );
        addCard(Zone.BATTLEFIELD, playerA, "Altar of the Lost");
        addCard(Zone.HAND, playerA, "Lingering Souls");

        checkPlayableAbility("mana not available", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Lingering", false);
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Spirit Token", 0);
    }

}
