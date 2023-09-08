package mage.cards.t;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.ExileTargetCost;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TetraviteToken;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.util.CardUtil;

/**
 *
 * @author TheElk801
 */
public final class Tetravus extends CardImpl {

    public Tetravus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Tetravus enters the battlefield with three +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(3)),
                "with three +1/+1 counters on it"
        ));

        // At the beginning of your upkeep, you may remove any number of +1/+1 counters from Tetravus. If you do, create that many 1/1 colorless Tetravite artifact creature tokens. They each have flying and "This creature can't be enchanted."
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new TetravusCreateTokensEffect(),
                TargetController.YOU, true
        ));

        // At the beginning of your upkeep, you may exile any number of tokens created with Tetravus. If you do, put that many +1/+1 counters on Tetravus.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new TetravusAddCountersEffect(),
                TargetController.YOU, true
        ));

    }

    private Tetravus(final Tetravus card) {
        super(card);
    }

    @Override
    public Tetravus copy() {
        return new Tetravus(this);
    }
}

class TetravusCreateTokensEffect extends OneShotEffect {

    public TetravusCreateTokensEffect() {
        super(Outcome.Benefit);
        this.staticText = "remove any number of +1/+1 counters from {this}. "
                + "If you do, create that many 1/1 colorless Tetravite artifact creature tokens. "
                + "They each have flying and \"This creature can't be enchanted.\"";
    }

    private TetravusCreateTokensEffect(final TetravusCreateTokensEffect effect) {
        super(effect);
    }

    @Override
    public TetravusCreateTokensEffect copy() {
        return new TetravusCreateTokensEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (player == null || permanent == null) {
            return false;
        }
        int countersToRemove = permanent.getCounters(game).getCount(CounterType.P1P1);
        if (countersToRemove == 0) {
            return false;
        }
        countersToRemove = player.getAmount(0, countersToRemove, "Choose an amount of counters to remove", game);
        Cost cost = new RemoveCountersSourceCost(CounterType.P1P1.createInstance(countersToRemove));
        if (cost.pay(source, game, source, source.getControllerId(), true)) {
            CreateTokenEffect effect = new CreateTokenEffect(new TetraviteToken(), countersToRemove);
            effect.apply(game, source);
            Object object = game.getState().getValue(CardUtil.getObjectZoneString("_tokensCreated", permanent, game));
            Set<UUID> tokensCreated;
            if (object != null) {
                tokensCreated = (Set<UUID>) object;
            } else {
                tokensCreated = new HashSet<>();
            }
            for (UUID tokenId : effect.getLastAddedTokenIds()) {
                if (tokenId != null) {
                    tokensCreated.add(tokenId);
                }
            }
            game.getState().setValue(CardUtil.getCardZoneString("_tokensCreated", source.getSourceId(), game), tokensCreated);
        }
        return false;
    }
}

class TetravusAddCountersEffect extends OneShotEffect {

    public TetravusAddCountersEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile any number of tokens created with {this}. "
                + "If you do, put that many +1/+1 counters on {this}";
    }

    private TetravusAddCountersEffect(final TetravusAddCountersEffect effect) {
        super(effect);
    }

    @Override
    public TetravusAddCountersEffect copy() {
        return new TetravusAddCountersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (player == null || permanent == null) {
            return false;
        }
        FilterControlledPermanent filter = new FilterControlledPermanent("tokens created with " + permanent.getName());
        filter.add(new TetravusPredicate(new MageObjectReference(permanent, game)));
        filter.add(TokenPredicate.TRUE);
        ExileTargetCost cost = new ExileTargetCost(new TargetControlledPermanent(0, Integer.MAX_VALUE, filter, true));
        if (cost.pay(source, game, source, player.getId(), true)) {
            return new AddCountersSourceEffect(CounterType.P1P1.createInstance(cost.getPermanents().size())).apply(game, source);
        }
        return false;
    }
}

class TetravusPredicate implements Predicate<Permanent> {

    private final MageObjectReference mor;

    public TetravusPredicate(MageObjectReference mor) {
        this.mor = mor;
    }

    @Override
    public boolean apply(Permanent input, Game game) {
        if (mor == null) {
            return false;
        }
        Permanent permanent = mor.getPermanent(game);
        if (permanent == null) {
            return false;
        }
        Object object = game.getState().getValue(CardUtil.getObjectZoneString("_tokensCreated", permanent, game));
        if (object == null) {
            return false;
        }
        Set<UUID> tokensCreated = (Set<UUID>) object;
        return tokensCreated.contains(input.getId());
    }

    @Override
    public String toString() {
        return "";
    }
}
