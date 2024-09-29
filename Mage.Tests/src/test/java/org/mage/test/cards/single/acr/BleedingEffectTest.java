package org.mage.test.cards.single.acr;

import mage.abilities.keyword.*;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class BleedingEffectTest extends CardTestPlayerBase {

    private static final String bleedingEffect = "Bleeding Effect";
    // At the beginning of combat on your turn, creatures you control gain flying until end of turn
    // if a creature card in your graveyard has flying. The same is true for first strike, double strike,
    // deathtouch, hexproof, indestructible, lifelink, menace, reach, trample, and vigilance.

    @Test
    public void testAbilitiesGained() {
        String baloth = "Rumbling Baloth"; // 4/4 vanilla
        addCard(Zone.BATTLEFIELD, playerA, bleedingEffect);
        addCard(Zone.GRAVEYARD, playerA, "Knight of Malice"); // First strike, hexproof from white
        addCard(Zone.GRAVEYARD, playerA, "Boggart Brute"); // Menace
        addCard(Zone.BATTLEFIELD, playerA, baloth); // vanilla 4/4

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertAbility(playerA, baloth, FirstStrikeAbility.getInstance(), true);
        assertAbility(playerA, baloth, HexproofFromWhiteAbility.getInstance(), true);
        assertAbility(playerA, baloth, new MenaceAbility(false), true);
        assertAbility(playerA, baloth, FlyingAbility.getInstance(), false);
        assertAbility(playerA, baloth, HexproofFromBlueAbility.getInstance(), false);
    }

}
