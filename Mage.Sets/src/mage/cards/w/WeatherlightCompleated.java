package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WeatherlightCompleated extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.PHYRESIS, 7);

    public WeatherlightCompleated(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // As long as Weatherlight Compleated has four or more phyresis counters on it, it's a Phyrexian creature in addition to its other types.
        this.addAbility(new SimpleStaticAbility(new WeatherlightCompleatedEffect()));

        // Whenever a creature you control dies, put a phyresis counter on Weatherlight Compleated. Then draw a card if it has seven or more phyresis counters on it. If it doesn't, scry 1.
        Ability ability = new DiesCreatureTriggeredAbility(
                new AddCountersSourceEffect(CounterType.PHYRESIS.createInstance()),
                false, StaticFilters.FILTER_CONTROLLED_A_CREATURE
        );
        ability.addEffect(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(1), new ScryEffect(1), condition,
                "Then draw a card if it has seven or more phyresis counters on it. If it doesn't, scry 1"
        ));
        this.addAbility(ability);
    }

    private WeatherlightCompleated(final WeatherlightCompleated card) {
        super(card);
    }

    @Override
    public WeatherlightCompleated copy() {
        return new WeatherlightCompleated(this);
    }
}

class WeatherlightCompleatedEffect extends ContinuousEffectImpl {

    WeatherlightCompleatedEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        staticText = "as long as {this} has four or more phyresis counters on it, " +
                "it's a Phyrexian creature in addition to its other types";
    }

    private WeatherlightCompleatedEffect(final WeatherlightCompleatedEffect effect) {
        super(effect);
    }

    @Override
    public WeatherlightCompleatedEffect copy() {
        return new WeatherlightCompleatedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null || permanent.getCounters(game).getCount(CounterType.PHYRESIS) < 4) {
            return false;
        }
        permanent.addCardType(game, CardType.CREATURE);
        permanent.addSubType(game, SubType.PHYREXIAN);
        return true;
    }
}
