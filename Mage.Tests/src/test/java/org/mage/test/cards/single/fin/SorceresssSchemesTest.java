package org.mage.test.cards.single.fin;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class SorceresssSchemesTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.s.SorceresssSchemes Sorceress's Schemes} {3}{R}
     * Sorcery
     * Return target instant or sorcery card from your graveyard or exiled card with flashback you own to your hand. Add {R}.
     * Flashback {4}{R}
     */
    private static final String schemes = "Sorceress's Schemes";

    @Test
    public void test_target_in_graveyard() {
        addCard(Zone.HAND, playerA, schemes, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.GRAVEYARD, playerA, "Lightning Bolt", 1);
        addCard(Zone.EXILED, playerA, "Deep Analysis", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, schemes, "Lightning Bolt");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, "Lightning Bolt", 1);
    }

    @Test
    public void test_target_in_exile() {
        addCard(Zone.HAND, playerA, schemes, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.GRAVEYARD, playerA, "Lightning Bolt", 1);
        addCard(Zone.EXILED, playerA, "Deep Analysis", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, schemes, "Deep Analysis");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, "Deep Analysis", 1);
    }

    @Test
    public void test_auto_target_in_graveyard() {
        addCard(Zone.HAND, playerA, schemes, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.GRAVEYARD, playerA, "Lightning Bolt", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, schemes);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, "Lightning Bolt", 1);
    }

    @Test
    public void test_auto_target_in_exile() {
        addCard(Zone.HAND, playerA, schemes, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.EXILED, playerA, "Deep Analysis", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, schemes);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, "Deep Analysis", 1);
    }

    @Test
    public void test_various_nontarget() {
        addCard(Zone.HAND, playerA, schemes, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        addCard(Zone.GRAVEYARD, playerA, "Goblin Piker", 1); // not instant/sorcery
        addCard(Zone.GRAVEYARD, playerA, "Bloomvine Regent", 1); // omen part not looked at
        addCard(Zone.EXILED, playerA, "Lightning Bolt", 1); // doesn't have flashback
        addCard(Zone.GRAVEYARD, playerB, "Lightning Bolt", 1); // not in your graveyard
        addCard(Zone.EXILED, playerB, "Deep Analysis", 1); // in exile not owned

        checkPlayableAbility("can not cast Schemes", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + schemes, false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
    }
}
