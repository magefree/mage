package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.EntersWithCountersControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CombineGuildmage extends CardImpl {

    private static final FilterPermanent filter1
            = new FilterControlledCreaturePermanent("creature you control (to remove a counter from)");
    private static final FilterPermanent filter2
            = new FilterControlledCreaturePermanent("creature you control (to move a counter to)");

    public CombineGuildmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {1}{G}, {T}: This turn, each creature you control enters the battlefield with an additional +1/+1 counter on it.
        Ability ability = new SimpleActivatedAbility(new EntersWithCountersControlledEffect(
                Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURE,
                CounterType.P1P1.createInstance(), false
        ), new ManaCostsImpl<>("{1}{G}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {1}{U}, {T}: Move a +1/+1 counter from target creature you control onto another target creature you control.
        ability = new SimpleActivatedAbility(
                new CombineGuildmageCounterEffect(), new ManaCostsImpl<>("{1}{U}")
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter1));
        ability.addTarget(new TargetPermanent(filter2));
        this.addAbility(ability);
    }

    private CombineGuildmage(final CombineGuildmage card) {
        super(card);
    }

    @Override
    public CombineGuildmage copy() {
        return new CombineGuildmage(this);
    }
}

class CombineGuildmageCounterEffect extends OneShotEffect {

    CombineGuildmageCounterEffect() {
        super(Outcome.Benefit);
        staticText = "Move a +1/+1 counter from target creature you control onto another target creature you control.";
    }

    private CombineGuildmageCounterEffect(final CombineGuildmageCounterEffect effect) {
        super(effect);
    }

    @Override
    public CombineGuildmageCounterEffect copy() {
        return new CombineGuildmageCounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent fromPermanent = game.getPermanent(source.getFirstTarget());
        Permanent toPermanent = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (fromPermanent == null || toPermanent == null) {
            return false;
        }
        if (fromPermanent.getCounters(game).getCount(CounterType.P1P1) > 0) {
            fromPermanent.removeCounters(CounterType.P1P1.createInstance(), source, game);
            toPermanent.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
        }
        return true;
    }
}
