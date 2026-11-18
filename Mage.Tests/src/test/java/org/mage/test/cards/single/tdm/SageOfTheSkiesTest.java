package org.mage.test.cards.single.tdm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author adams-shaun
 */
public class SageOfTheSkiesTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.s.SageOfTheSkies Sage of the Skies} {2}{W}
     * Creature - Human Monk 2/3
     * When you cast this spell, if you've cast another spell this turn, copy this spell.
     * Flying, lifelink
     */
    private static final String sage = "Sage of the Skies";

    @Test
    public void test_FirstSpellOfTheTurnDoesNotTrigger() {
        addCard(Zone.HAND, playerA, sage);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, sage);
        checkStackSize("only the spell", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 1);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, sage, 1);
    }

    @Test
    public void test_SecondSpellOfTheTurnCopies() {
        addCard(Zone.HAND, playerA, sage);
        addCard(Zone.HAND, playerA, "Memnite");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Memnite");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, sage);
        checkStackSize("spell and trigger", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 2);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, sage, 2);
    }
}
