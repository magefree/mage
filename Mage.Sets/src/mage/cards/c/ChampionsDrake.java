package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;

import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 */
public final class ChampionsDrake extends CardImpl {

    private static final String rule = "{this} gets +3/+3 as long as you control a creature with three or more level counters on it.";

    public ChampionsDrake(UUID ownerId, CardSetInfo setInfo) {

        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.DRAKE);

        this.color.setBlue(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(FlyingAbility.getInstance());

        // Champion's Drake gets +3/+3 as long as you control a creature with three or more level counters on it.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(3, 3, Duration.WhileOnBattlefield),
                ChampionsDrakeCondition.instance, rule)));
    }

    private ChampionsDrake(final ChampionsDrake card) {
        super(card);
    }

    @Override
    public ChampionsDrake copy() {
        return new ChampionsDrake(this);
    }
}

enum ChampionsDrakeCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getBattlefield()
                .getActivePermanents(StaticFilters.FILTER_CONTROLLED_CREATURE, source.getControllerId(), source, game)
                .stream()
                .anyMatch(p -> p.getCounters(game).getCount(CounterType.LEVEL) >= 3);
    }

}
