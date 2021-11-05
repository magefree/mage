package org.mage.test.cards.conditional;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneAllEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCardInOpponentsGraveyard;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class ConditionalAsThoughTest extends CardTestPlayerBase {

    @Test
    public void test_PlayFromNotOwnHandZoneAllEffect() {
        removeAllCardsFromLibrary(playerA);
        removeAllCardsFromLibrary(playerB);

        addCustomCardWithAbility("play any library on any creature", playerA, new SimpleStaticAbility(
                Zone.ALL,
                new ConditionalAsThoughEffect(
                        new PlayFromNotOwnHandZoneAllEffect(
                                StaticFilters.FILTER_CARD,
                                Zone.LIBRARY,
                                false,
                                TargetController.ANY,
                                Duration.EndOfTurn
                        ),
                        new PermanentsOnTheBattlefieldCondition(
                                StaticFilters.FILTER_PERMANENT_CREATURE,
                                ComparisonType.MORE_THAN,
                                0
                        )
                )
        ));
        addCard(Zone.HAND, playerA, "Grizzly Bears");
        addCard(Zone.LIBRARY, playerA, "Silvercoat Lion");
        addCard(Zone.LIBRARY, playerB, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);

        // can't play lib's card before good condition
        checkPlayableAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Grizzly Bears", true);
        checkPlayableAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Silvercoat Lion", false);
        checkPlayableAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Lightning Bolt", false);

        // make good condition - now we can play any lib's card
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Grizzly Bears", false);
        checkPlayableAbility("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Silvercoat Lion", true);
        checkPlayableAbility("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Lightning Bolt", true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_TargetCardInLibrary_CantUseAsAbilityTarget() {
        Ability ability = new SimpleActivatedAbility(
                Zone.ALL,
                new InfoEffect("test"),
                new ManaCostsImpl("{R}")
        );
        ability.addTarget(new TargetCardInLibrary());
    }

    @Test
    public void test_PlayFromNotOwnHandZoneTargetEffect() {
        Ability ability = new SimpleActivatedAbility(
                Zone.ALL,
                new ConditionalAsThoughEffect(
                        new PlayFromNotOwnHandZoneTargetEffect(
                                Zone.GRAVEYARD,
                                TargetController.ANY,
                                Duration.EndOfTurn
                        ),
                        new PermanentsOnTheBattlefieldCondition(
                                StaticFilters.FILTER_PERMANENT_CREATURE,
                                ComparisonType.MORE_THAN,
                                0
                        )
                ).setText("allow target cast"),
                new ManaCostsImpl("{R}")
        );
        ability.addTarget(new TargetCardInOpponentsGraveyard(StaticFilters.FILTER_CARD));
        addCustomCardWithAbility("play any opponent hand", playerA, ability);

        addCard(Zone.HAND, playerA, "Grizzly Bears");
        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion");
        addCard(Zone.GRAVEYARD, playerB, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);

        // can't play grave before
        checkPlayableAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Grizzly Bears", true);
        checkPlayableAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Silvercoat Lion", false);
        checkPlayableAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Lightning Bolt", false);

        // activate target effect
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{R}: Allow");
        addTarget(playerA, "Lightning Bolt");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // can't play grave after but without good condition
        checkPlayableAbility("after bad", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Grizzly Bears", true);
        checkPlayableAbility("after bad", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Silvercoat Lion", false);
        checkPlayableAbility("after bad", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Lightning Bolt", false);

        // make good condition - now we can play opponent's grave
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("after good", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Grizzly Bears", false);
        checkPlayableAbility("after good", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Silvercoat Lion", false);
        checkPlayableAbility("after good", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Lightning Bolt", true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();
    }
}
