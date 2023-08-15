package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class BargainTest extends CardTestPlayerBase {

    // Troublemaker Ouphe
    // {1}{G}
    // Creature — Ouphe
    //
    // Bargain (You may sacrifice an artifact, enchantment, or token as you cast this spell.)
    // When Troublemaker Ouphe enters the battlefield, if it was bargained, exile target artifact or enchantment an opponent controls.
    private static final String troublemakerOuphe = "Troublemaker Ouphe";

    // Artifact to be targetted by Troublemaker's Ouphe trigger.
    private static final String glider = "Aesthir Glider";


    @Test
    public void testBargainNotPaidOuphe() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, troublemakerOuphe);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, glider); // Could be bargain.

        addCard(Zone.BATTLEFIELD, playerB, glider);

        setStrictChooseMode(true);
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, troublemakerOuphe);
        setChoice(playerA, false); // Do not bargain.

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, glider, 1);
    }

    @Test
    public void testBargainNothingToBargain() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, troublemakerOuphe);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        addCard(Zone.BATTLEFIELD, playerB, glider);

        setStrictChooseMode(true);
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, troublemakerOuphe);
        // Nothing to bargain.

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, glider, 1);
    }

    @Test
    public void testBargainPaidOupheNothingToTarget() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, troublemakerOuphe);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, glider); // Is an artifact, hence something you can sac to Bargain.

        setStrictChooseMode(true);
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, troublemakerOuphe);
        setChoice(playerA, true); // true to bargain
        setChoice(playerA, glider); // choose to sac glider.

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, glider, 1);
    }

    @Test
    public void testBargainPaidOuphe() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, troublemakerOuphe);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, glider); // Is an artifact, hence something you can sac to Bargain.

        addCard(Zone.BATTLEFIELD, playerB, glider);

        setStrictChooseMode(true);
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, troublemakerOuphe);
        setChoice(playerA, true); // true to bargain
        setChoice(playerA, glider); // choose to sac glider.
        addTarget(playerA, glider); // exile opponent's glider.

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, glider, 0);
        assertExileCount(playerB, glider, 1);
        assertGraveyardCount(playerA, glider, 1);
    }
}
