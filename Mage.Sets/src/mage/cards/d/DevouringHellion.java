package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DevouringHellion extends CardImpl {

    public DevouringHellion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.HELLION);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // As Devouring Hellion enters the battlefield, you may sacrifice any number of creatures and/or planeswalkers. If you do, it enters with twice that many +1/+1 counters on it.
        this.addAbility(new AsEntersBattlefieldAbility(new DevouringHellionEffect()));
    }

    private DevouringHellion(final DevouringHellion card) {
        super(card);
    }

    @Override
    public DevouringHellion copy() {
        return new DevouringHellion(this);
    }
}

class DevouringHellionEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledPermanent("creatures and/or planeswalkers");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.PLANESWALKER.getPredicate()
        ));
    }

    DevouringHellionEffect() {
        super(Outcome.Benefit);
        staticText = "you may sacrifice any number of creatures and/or planeswalkers. " +
                "If you do, it enters with twice that many +1/+1 counters on it.";
    }

    private DevouringHellionEffect(final DevouringHellionEffect effect) {
        super(effect);
    }

    @Override
    public DevouringHellionEffect copy() {
        return new DevouringHellionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Target target = new TargetPermanent(0, Integer.MAX_VALUE, filter, true);
        if (!player.choose(outcome, target, source, game)) {
            return false;
        }
        int xValue = 0;
        for (UUID targetId : target.getTargets()) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null && permanent.sacrifice(source, game)) {
                xValue++;
            }
        }
        return new AddCountersSourceEffect(CounterType.P1P1.createInstance(2 * xValue)).apply(game, source);
    }
}