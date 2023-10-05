package org.mage.test.cards.single.war;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class IlhargTheRazeBoarTest extends CardTestPlayerBase {

    /**
     * Ilharg, the Raze-Boar
     * {3}{R}{R}
     * Legendary Creature — Boar God
     * <p>
     * Trample
     * <p>
     * Whenever Ilharg, the Raze-Boar attacks, you may put a creature card from your hand onto the battlefield tapped and attacking. Return that creature to your hand at the beginning of the next end step.
     * <p>
     * When Ilharg, the Raze-Boar dies or is put into exile from the battlefield, you may put it into its owner’s library third from the top.
     * <p>
     * 6/6
     */
    public final String ilharg = "Ilharg, the Raze-Boar";

    // Reported bug: #10313
    // When attacking with [[Ilharg, the Raze-Boar]] and selecting a creature card that is also a MDFC, the card does not enter attacking.
    // The card enters tapped, but not attacking.
    // The card is also not returned to hand at the end of turn
    @Test
    public void putMDFCFrontInPlay() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, ilharg);
        addCard(Zone.HAND, playerA, "Valentin, Dean of the Vein"); // 1/1 menace lifelink // 4/4

        attack(1, playerA, ilharg);
        setChoice(playerA, true); // yes to ilharg
        setChoice(playerA, "Valentin, Dean of the Vein"); // choose that face

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertLife(playerB, 20 - 6 - 1);
        assertLife(playerA, 20 + 1);
        assertHandCount(playerA, "Valentin, Dean of the Vein", 1);
    }
}