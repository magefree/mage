package org.mage.test.cards.abilities.other;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneAllEffect;
import mage.constants.Duration;
import mage.constants.PhaseStep;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class FeatherTheRedeemedTest extends CardTestPlayerBase {

    // Feather, the Redeemed {R}{W}{W}
    /*
    Whenever you cast an instant or sorcery spell that targets a creature you control, exile that card
    instead of putting it into your graveyard as it resolves. If you do, return it to your hand at the beginning of the next end step.
     */

    @Test
    public void test_ExileSpellWithReturnAtTheEnd() {
        // cast bolt, put to exile, return to hand
        addCard(Zone.BATTLEFIELD, playerA, "Feather, the Redeemed");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 2);

        // cast and put to exile
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Grizzly Bears");
        checkPermanentCount("turn 1", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grizzly Bears", 1);
        checkExileCount("turn 1", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", 1);
        checkHandCardCount("turn 1", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", 0);

        // return to hand at the next end step
        checkExileCount("turn 1 after", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", 0);
        checkHandCardCount("turn 1 after", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", 1);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();
    }

    @Test
    public void test_ExileSpellAndRecastWithReturnAtTheEnd() {
        // cast bolt, put to exile, cast from exile, put to exile, return to hand
        addCustomCardWithAbility("cast from exile", playerA, new SimpleStaticAbility(
                new PlayFromNotOwnHandZoneAllEffect(StaticFilters.FILTER_CARD, Zone.EXILED, false, TargetController.ANY, Duration.WhileOnBattlefield)
        ));

        addCard(Zone.BATTLEFIELD, playerA, "Feather, the Redeemed");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 3);

        // cast and put to exile
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("turn 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 3 - 1);
        checkExileCount("turn 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", 1);
        checkHandCardCount("turn 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", 0);

        // cast from exile and put to exile again
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("turn 1 recast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 3 - 2);
        checkExileCount("turn 1 recast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", 1);
        checkHandCardCount("turn 1 recast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", 0);

        // return to hand at the next end step
        setChoice(playerA, "At the beginning of the next end step"); // two triggeres from two cast (card's code adds two same effects on each trigger)
        checkExileCount("turn 1 after", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", 0);
        checkHandCardCount("turn 1 after", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", 1);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();
    }

    @Test
    public void test_ExileSpellAndRecastWithoutReturn() {
        // cast bolt, put to exile, cast from exile with different target, put to graveyard, not return
        addCustomCardWithAbility("cast from exile", playerA, new SimpleStaticAbility(
                new PlayFromNotOwnHandZoneAllEffect(StaticFilters.FILTER_CARD, Zone.EXILED, false, TargetController.ANY, Duration.WhileOnBattlefield)
        ));

        addCard(Zone.BATTLEFIELD, playerA, "Feather, the Redeemed");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 3);

        // cast and put to exile
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("turn 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 3 - 1);
        checkExileCount("turn 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", 1);
        checkHandCardCount("turn 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", 0);

        // cast from exile to target player (without trigger) and put to graveyard
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("turn 1 recast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 3 - 1); // no changes
        checkLife("turn 1 recast", 1, PhaseStep.PRECOMBAT_MAIN, playerB, 20 - 3);
        checkExileCount("turn 1 recast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", 0);
        checkHandCardCount("turn 1 recast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", 0);

        // not return to hand at the next end step
        checkExileCount("turn 1 after", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", 0);
        checkHandCardCount("turn 1 after", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", 0);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();
    }
}
