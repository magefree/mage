package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.watchers.common.AbilityResolvedWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScythecatCub extends CardImpl {

    public ScythecatCub(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.CAT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Landfall -- Whenever a land you control enters, put a +1/+1 counter on target creature you control. If this is the second time this ability has resolved this turn, double the number of +1/+1 counters on that creature instead.
        Ability ability = new LandfallAbility(new ScythecatCubEffect());
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability, new AbilityResolvedWatcher());
    }

    private ScythecatCub(final ScythecatCub card) {
        super(card);
    }

    @Override
    public ScythecatCub copy() {
        return new ScythecatCub(this);
    }
}

class ScythecatCubEffect extends OneShotEffect {

    ScythecatCubEffect() {
        super(Outcome.Benefit);
        staticText = "put a +1/+1 counter on target creature you control. " +
                "If this is the second time this ability has resolved this turn, " +
                "double the number of +1/+1 counters on that creature instead";
    }

    private ScythecatCubEffect(final ScythecatCubEffect effect) {
        super(effect);
    }

    @Override
    public ScythecatCubEffect copy() {
        return new ScythecatCubEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        int count = AbilityResolvedWatcher.getResolutionCount(game, source) == 2
                ? permanent.getCounters(game).getCount(CounterType.P1P1)
                : 1;
        return count > 0 && permanent.addCounters(CounterType.P1P1.createInstance(count), source, game);
    }
}
