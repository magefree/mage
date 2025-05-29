package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.MoogleToken;
import mage.players.Player;
import mage.target.common.TargetDiscard;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MogMoogleWarrior extends CardImpl {

    public MogMoogleWarrior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MOOGLE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Dance -- At the beginning of your end step, each player may discard a card. Each player who discarded a card this way draws a card. If a creature card was discarded this way, you create a 1/2 white Moogle creature token with lifelink. Then if a noncreature card was discarded this way, put a +1/+1 counter on each Moogle you control.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new MogMoogleWarriorEffect()).withFlavorWord("Dance"));
    }

    private MogMoogleWarrior(final MogMoogleWarrior card) {
        super(card);
    }

    @Override
    public MogMoogleWarrior copy() {
        return new MogMoogleWarrior(this);
    }
}

class MogMoogleWarriorEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.MOOGLE);

    MogMoogleWarriorEffect() {
        super(Outcome.Benefit);
        staticText = "each player may discard a card. Each player who discarded a card this way draws a card. " +
                "If a creature card was discarded this way, you create a 1/2 white Moogle creature token with lifelink. " +
                "Then if a noncreature card was discarded this way, put a +1/+1 counter on each Moogle you control";
    }

    private MogMoogleWarriorEffect(final MogMoogleWarriorEffect effect) {
        super(effect);
    }

    @Override
    public MogMoogleWarriorEffect copy() {
        return new MogMoogleWarriorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Map<UUID, Card> map = new HashMap<>();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            TargetDiscard target = new TargetDiscard(0, 1, StaticFilters.FILTER_CARD, playerId);
            player.choose(outcome, player.getHand(), target, source, game);
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                map.put(playerId, card);
            }
        }
        Cards cards = new CardsImpl();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            Card card = map.getOrDefault(playerId, null);
            if (player != null && card != null && player.discard(card, false, source, game)) {
                cards.add(card);
                player.drawCards(1, source, game);
            }
        }
        if (cards.count(StaticFilters.FILTER_CARD_CREATURE, game) > 0) {
            new MoogleToken().putOntoBattlefield(1, game, source);
        }
        if (cards.count(StaticFilters.FILTER_CARD_NON_CREATURE, game) < 1) {
            return true;
        }
        game.processAction();
        for (Permanent permanent : game
                .getBattlefield()
                .getActivePermanents(filter, source.getControllerId(), source, game)) {
            permanent.addCounters(CounterType.P1P1.createInstance(), source, game);
        }
        return true;
    }
}
