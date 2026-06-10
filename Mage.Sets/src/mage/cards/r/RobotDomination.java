package mage.cards.r;

import java.util.UUID;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.permanent.token.RobotVillainToken;
import mage.abilities.Ability;
import mage.abilities.common.PlanCounterThresholdTriggeredAbility;
import mage.abilities.common.PutCardIntoGraveFromAnywhereAllTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class RobotDomination extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("one or more creature cards");

    public RobotDomination(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}");

        this.subtype.add(SubType.PLAN);

        // Whenever one or more creature cards are put into your graveyard from anywhere, you draw a card, lose 1 life, and put a plan counter on this enchantment.
        Ability ability = new PutCardIntoGraveFromAnywhereAllTriggeredAbility(
            new DrawCardSourceControllerEffect(1), false, filter, TargetController.YOU
        );
        ability.addEffect(new LoseLifeSourceControllerEffect(1).concatBy(","));
        ability.addEffect(new AddCountersSourceEffect(CounterType.PLAN.createInstance()).concatBy("and"));
        this.addAbility(ability);

        // When the third plan counter is put on this enchantment, sacrifice it and create three 2/2 colorless Robot Villain artifact creature tokens.
        Ability thresholdAbility = new PlanCounterThresholdTriggeredAbility(3);
        thresholdAbility.addEffect(new CreateTokenEffect(new RobotVillainToken(), 3).concatBy("and"));
        this.addAbility(thresholdAbility);
    }

    private RobotDomination(final RobotDomination card) {
        super(card);
    }

    @Override
    public RobotDomination copy() {
        return new RobotDomination(this);
    }
}
