package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.OrCondition;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.condition.common.YouLostLifeCondition;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DoUnlessTargetPlayerOrTargetsControllerPaysEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetOpponent;
import mage.watchers.common.PlayerGainedLifeWatcher;
import mage.watchers.common.PlayerLostLifeWatcher;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class StarseerMentor extends CardImpl {

    private static final Condition condition =
            new OrCondition(
                    "if you gained or lost life this turn",
                    new YouGainedLifeCondition(),
                    new YouLostLifeCondition()
            );
    private static final Hint hint = new ConditionHint(condition);

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("nonland permanent");

    static {
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
    }

    public StarseerMentor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{B}");

        this.subtype.add(SubType.BAT);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // At the beginning of your end step, if you gained or lost life this turn, target opponent loses 3 life unless they sacrifice a nonland permanent or discard a card.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfEndStepTriggeredAbility(
                        new DoUnlessTargetPlayerOrTargetsControllerPaysEffect(
                                new LoseLifeTargetEffect(3),
                                new OrCost(
                                        "sacrifice a nonland permanent or discard a card",
                                        new SacrificeTargetCost(filter),
                                        new DiscardCardCost()
                                ),
                                "Sacrifice a nonland permanent or discard a card to prevent losing 3 life?"
                        ), TargetController.YOU, false
                ), condition, "At the beginning of your end step, if you gained or lost life this turn, "
                + "target opponent loses 3 life unless they sacrifice a nonland permanent or discard a card."
        );
        ability.addTarget(new TargetOpponent());
        ability.addWatcher(new PlayerGainedLifeWatcher());
        ability.addHint(hint);
        this.addAbility(ability);
    }

    private StarseerMentor(final StarseerMentor card) {
        super(card);
    }

    @Override
    public StarseerMentor copy() {
        return new StarseerMentor(this);
    }
}
