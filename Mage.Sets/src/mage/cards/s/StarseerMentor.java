package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.common.YouGainedOrLostLifeCondition;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DoUnlessTargetPlayerOrTargetsControllerPaysEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponent;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class StarseerMentor extends CardImpl {

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
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new DoUnlessTargetPlayerOrTargetsControllerPaysEffect(
                        new LoseLifeTargetEffect(3),
                        new OrCost(
                                "sacrifice a nonland permanent or discard a card",
                                new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_NON_LAND),
                                new DiscardCardCost()
                        ),
                        "Sacrifice a nonland permanent or discard a card to prevent losing 3 life?"
                ).setText("target opponent loses 3 life unless they sacrifice a nonland permanent of their choice or discard a card")
        ).withInterveningIf(YouGainedOrLostLifeCondition.instance);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability.addHint(YouGainedOrLostLifeCondition.getHint()), new PlayerGainedLifeWatcher());
    }

    private StarseerMentor(final StarseerMentor card) {
        super(card);
    }

    @Override
    public StarseerMentor copy() {
        return new StarseerMentor(this);
    }
}
