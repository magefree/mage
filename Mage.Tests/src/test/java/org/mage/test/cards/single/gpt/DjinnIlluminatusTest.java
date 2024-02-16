package org.mage.test.cards.single.gpt;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.d.DjinnIlluminatus Djinn Illuminatus}
 * <p>
 * Each instant and sorcery spell you cast has replicate.
 * The replicate cost is equal to its mana cost.
 * (When you cast it, copy it for each time you paid its replicate cost. You may choose new targets for the copies.)
 *
 * @author Alex-Vasile
 */
public class DjinnIlluminatusTest extends CardTestPlayerBase {

    private static final String djinnIlluminatus = "Djinn Illuminatus";
    private static final String lightningBolt = "Lightning Bolt";
    private static final String mountain = "Mountain";

    /**
     * Test that it works for you spells on your turn.
     */
    @Test
    public void testYourSpellYourTurn() {
        addCard(Zone.BATTLEFIELD, playerA, djinnIlluminatus);
        addCard(Zone.BATTLEFIELD, playerA, mountain, 2);
        addCard(Zone.HAND, playerA, lightningBolt);

        setStrictChooseMode(true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, lightningBolt, playerB);
        setChoice(playerA, "Yes"); // Replicate
        setChoice(playerA, "No"); // Only replicate once
        setChoice(playerA, "No"); // Don't change the target

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertLife(playerB, 14);
    }

    /**
     * Test that it works for your spell on other's turn.
     */
    @Test
    public void testYourSpellNotYourTurn() {
        addCard(Zone.BATTLEFIELD, playerA, djinnIlluminatus);
        addCard(Zone.BATTLEFIELD, playerA, mountain, 2);
        addCard(Zone.HAND, playerA, lightningBolt);

        setStrictChooseMode(true);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, lightningBolt, playerB);
        setChoice(playerA, "Yes"); // Replicate
        setChoice(playerA, "No"); // Only replicate once
        setChoice(playerA, "No"); // Don't change the target

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertLife(playerB, 14);
    }

    /**
     * Test that it does not copy other's spell.
     */
    @Test
    public void testOthersSpell() {
        addCard(Zone.BATTLEFIELD, playerA, djinnIlluminatus);
        addCard(Zone.BATTLEFIELD, playerB, mountain, 2);
        addCard(Zone.HAND, playerB, lightningBolt);

        setStrictChooseMode(true);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, lightningBolt, playerA);

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertLife(playerA, 17);
    }
}
