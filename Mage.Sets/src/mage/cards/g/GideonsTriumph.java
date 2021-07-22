package mage.cards.g;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPlaneswalkerPermanent;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetOpponent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GideonsTriumph extends CardImpl {

    public GideonsTriumph(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Target opponent sacrifices a creature that attacked or blocked this turn. If you control a Gideon planeswalker, that player sacrifices two of those creatures instead.
        this.getSpellAbility().addEffect(new GideonsTriumphEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addWatcher(new GideonsTriumphWatcher());
    }

    private GideonsTriumph(final GideonsTriumph card) {
        super(card);
    }

    @Override
    public GideonsTriumph copy() {
        return new GideonsTriumph(this);
    }
}

class GideonsTriumphEffect extends OneShotEffect {

    private static final FilterControlledPlaneswalkerPermanent filterGideon
            = new FilterControlledPlaneswalkerPermanent(SubType.GIDEON);
    private static final FilterPermanent filterSacrifice
            = new FilterPermanent("creature that attacked or blocked this turn");

    static {
        filterSacrifice.add(GideonsTriumphPredicate.instance);
    }

    GideonsTriumphEffect() {
        super(Outcome.Benefit);
        staticText = "Target opponent sacrifices a creature that attacked or blocked this turn. " +
                "If you control a Gideon planeswalker, that player sacrifices two of those creatures instead.";
    }

    private GideonsTriumphEffect(final GideonsTriumphEffect effect) {
        super(effect);
    }

    @Override
    public GideonsTriumphEffect copy() {
        return new GideonsTriumphEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int count = 1;
        if (!game.getBattlefield().getActivePermanents(filterGideon, source.getControllerId(), game).isEmpty()) {
            count++;
        }
        return new SacrificeEffect(filterSacrifice, count, "Target opponent").apply(game, source);
    }
}

enum GideonsTriumphPredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        GideonsTriumphWatcher watcher = game.getState().getWatcher(GideonsTriumphWatcher.class);
        return input.isCreature(game) && watcher.attackedOrBlockedThisTurn(input, game);
    }
}

class GideonsTriumphWatcher extends Watcher {

    private final Set<MageObjectReference> attackedOrBlockedThisTurnCreatures = new HashSet<>();

    public GideonsTriumphWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ATTACKER_DECLARED || event.getType() == GameEvent.EventType.BLOCKER_DECLARED) {
            this.attackedOrBlockedThisTurnCreatures.add(new MageObjectReference(event.getSourceId(), game));
        }
    }

    boolean attackedOrBlockedThisTurn(Permanent permanent, Game game) {
        return this.attackedOrBlockedThisTurnCreatures.contains(new MageObjectReference(permanent, game));
    }

    @Override
    public void reset() {
        attackedOrBlockedThisTurnCreatures.clear();
    }

}
