package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.CounterAnyPredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TradeRouteEnvoy extends CardImpl {

    public TradeRouteEnvoy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.DOG);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When this creature enters, draw a card if you control a creature with a counter on it. If you don't draw a card this way, put a +1/+1 counter on this creature.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TradeRouteEnvoyEffect()));
    }

    private TradeRouteEnvoy(final TradeRouteEnvoy card) {
        super(card);
    }

    @Override
    public TradeRouteEnvoy copy() {
        return new TradeRouteEnvoy(this);
    }
}

class TradeRouteEnvoyEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(CounterAnyPredicate.instance);
    }

    TradeRouteEnvoyEffect() {
        super(Outcome.Benefit);
        staticText = "draw a card if you control a creature with a counter on it. " +
                "If you don't draw a card this way, put a +1/+1 counter on this creature";
    }

    private TradeRouteEnvoyEffect(final TradeRouteEnvoyEffect effect) {
        super(effect);
    }

    @Override
    public TradeRouteEnvoyEffect copy() {
        return new TradeRouteEnvoyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (game.getBattlefield().contains(filter, source, game, 1)) {
            Player player = game.getPlayer(source.getControllerId());
            if (player != null && player.drawCards(1, source, game) > 0) {
                return true;
            }
        }
        Optional.ofNullable(source.getSourcePermanentIfItStillExists(game))
                .ifPresent(permanent -> permanent.addCounters(CounterType.P1P1.createInstance(), source, game));
        return true;
    }
}
