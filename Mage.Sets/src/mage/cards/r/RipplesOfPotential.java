package mage.cards.r;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.watchers.Watcher;

/**
 * Ripples of Potential {1}{U}
 * Instant
 * Proliferate, then choose any number of permanents you control that had a counter put on them this way. Those permanents phase out.
 *
 * @author DominionSpy
 */
public class RipplesOfPotential extends CardImpl {

    public RipplesOfPotential(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Proliferate, then choose any number of permanents you control that had a counter put on them this way. Those permanents phase out.
        // A watcher is required as another effect may prevent counters from being put on permanents (e.g. Solemnity)
        this.getSpellAbility().addEffect(new ProliferateEffect(false));
        this.getSpellAbility().addEffect(new RipplesOfPotentialEffect());
        this.getSpellAbility().addWatcher(new RipplesOfPotentialWatcher());
    }

    private RipplesOfPotential(final RipplesOfPotential card) {
        super(card);
    }

    @Override
    public RipplesOfPotential copy() {
        return new RipplesOfPotential(this);
    }
}

class RipplesOfPotentialEffect extends OneShotEffect {

    RipplesOfPotentialEffect() {
        super(Outcome.Benefit);
        staticText = ", then choose any number of permanents you control that had a counter put on them this way. Those permanents phase out" +
                ". <i>(To proliferate, choose any number of permanents and/or players, then give each another counter of each kind already there. " +
                "Treat phased-out permanents and anything attached to them as though they don't exist until their controller's next turn.)</i>";
    }

    private RipplesOfPotentialEffect(final RipplesOfPotentialEffect effect) {
        super(effect);
    }

    @Override
    public RipplesOfPotentialEffect copy() {
        return new RipplesOfPotentialEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        RipplesOfPotentialWatcher watcher = game.getState().getWatcher(RipplesOfPotentialWatcher.class, source.getSourceId());
        if (watcher == null) {
            return false;
        }

        FilterPermanent filter = new FilterPermanent("permanents");
        filter.add(Predicates.or(
                watcher
                        .getProliferatedPermanents()
                        .stream()
                        .map(mor -> mor.getPermanentOrLKIBattlefield(game))
                        .filter(Objects::nonNull)
                        .filter(permanent -> permanent.isControlledBy(source.getControllerId()))
                        .map(Permanent::getId)
                        .map(PermanentIdPredicate::new)
                        .collect(Collectors.toSet())
        ));
        TargetPermanent target = new TargetPermanent(0, Integer.MAX_VALUE, filter, true);
        controller.choose(outcome, target.withChooseHint("to phase out"), source, game);
        for (UUID targetId : target.getTargets()) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null) {
                permanent.phaseOut(game);
            }
        }
        watcher.reset();
        return true;
    }
}

class RipplesOfPotentialWatcher extends Watcher {

    private final Set<MageObjectReference> proliferatedPermanents = new HashSet<>();

    RipplesOfPotentialWatcher() {
        super(WatcherScope.CARD);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.COUNTER_ADDED) {
            MageObjectReference mor = new MageObjectReference(event.getTargetId(), game);
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (sourceId.equals(event.getSourceId()) && permanent != null) {
                proliferatedPermanents.add(mor);
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        proliferatedPermanents.clear();
    }

    Set<MageObjectReference> getProliferatedPermanents() {
        return proliferatedPermanents;
    }
}
