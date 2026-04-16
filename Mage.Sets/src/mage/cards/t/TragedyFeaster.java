package mage.cards.t;

import mage.MageInt;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.ControllerGainedLifeCount;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.WardAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TragedyFeaster extends CardImpl {

    private static final Condition condition = new YouGainedLifeCondition(ComparisonType.FEWER_THAN, 1);

    public TragedyFeaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");

        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(7);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Ward--Discard a card.
        this.addAbility(new WardAbility(new DiscardCardCost()));

        // Infusion -- At the beginning of your end step, sacrifice a permanent unless you gained life this turn.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new ConditionalOneShotEffect(
                new SacrificeControllerEffect(StaticFilters.FILTER_PERMANENT_A, 1, ""),
                condition, "sacrifice a permanent unless you gained life this turn"
        )).setAbilityWord(AbilityWord.INFUSION).addHint(ControllerGainedLifeCount.getHint()), new PlayerGainedLifeWatcher());
    }

    private TragedyFeaster(final TragedyFeaster card) {
        super(card);
    }

    @Override
    public TragedyFeaster copy() {
        return new TragedyFeaster(this);
    }
}
