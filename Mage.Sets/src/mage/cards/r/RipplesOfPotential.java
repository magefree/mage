package mage.cards.r;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.filter.predicate.permanent.PermanentInListPredicate;
import mage.filter.predicate.permanent.PermanentReferenceInCollectionPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;
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

    public RipplesOfPotentialEffect() {
        super(Outcome.Benefit);
        staticText = "Proliferate, then choose any number of permanents you control that had a counter put on them this way. Those permanents phase out.";
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
        ProliferateEffect proliferate = new ProliferateEffect();
        proliferate.apply(game, source);

        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        RipplesOfPotentialWatcher watcher = game.getState().getWatcher(RipplesOfPotentialWatcher.class, source.getSourceId());
        if (watcher != null) {
            FilterPermanent filter = new FilterControlledPermanent();
            filter.add(new PermanentIdPredicate());
            TargetCard target = new TargetCard(0, Integer.MAX_VALUE, Zone.BATTLEFIELD, new FilterCard("permanents to phase out"));
            target.withNotTarget(true);
            controller.choose(outcome, watcher.getProliferatedPermanents(), target, source, game);
            for (UUID targetId : target.getTargets()) {
                Permanent permanent = game.getPermanent(targetId);
                if (permanent != null) {
                    permanent.phaseOut(game);
                }
            }
        }
        return true;
    }
}

class RipplesOfPotentialWatcher extends Watcher {

    private final Cards proliferatedPermanents = new CardsImpl();

    RipplesOfPotentialWatcher() {
        super(WatcherScope.CARD);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.COUNTER_ADDED) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (sourceId.equals(event.getSourceId()) && permanent != null) {
                proliferatedPermanents.add(event.getTargetId());
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        proliferatedPermanents.clear();
    }

    Cards getProliferatedPermanents() {
        return  proliferatedPermanents;
    }
}
