package mage.cards.r;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author weirddan455
 */
public final class Recommission extends CardImpl {

    private static final FilterCard filter
            = new FilterCard("artifact or creature card with mana value 3 or less from your graveyard");

    static {
        filter.add(Predicates.or(CardType.ARTIFACT.getPredicate(), CardType.CREATURE.getPredicate()));
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public Recommission(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}");

        // Return target artifact or creature card with mana value 3 or less from your graveyard to the battlefield. If a creature enters the battlefield this way, it enters with an additional +1/+1 counter on it.
        this.getSpellAbility().addEffect(new RecommissionEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(filter));
    }

    private Recommission(final Recommission card) {
        super(card);
    }

    @Override
    public Recommission copy() {
        return new Recommission(this);
    }
}

class RecommissionEffect extends OneShotEffect {

    public RecommissionEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Return target artifact or creature card with mana value 3 or less from your graveyard to the battlefield. If a creature enters the battlefield this way, it enters with an additional +1/+1 counter on it.";
    }

    private RecommissionEffect(final RecommissionEffect effect) {
        super(effect);
    }

    @Override
    public RecommissionEffect copy() {
        return new RecommissionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(targetPointer.getFirst(game, source));
        if (controller == null || card == null) {
            return false;
        }
        game.addEffect(new RecommissionCounterEffect(), source);
        controller.moveCards(card, Zone.BATTLEFIELD, source, game);
        return true;
    }
}

class RecommissionCounterEffect extends ReplacementEffectImpl {

    public RecommissionCounterEffect() {
        super(Duration.EndOfStep, Outcome.BoostCreature);
    }

    private RecommissionCounterEffect(final RecommissionCounterEffect effect) {
        super(effect);
    }

    @Override
    public RecommissionCounterEffect copy() {
        return new RecommissionCounterEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return targetPointer.getTargets(game, source).contains(event.getTargetId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
        if (permanent == null || !permanent.isCreature(game)) {
            return false;
        }
        permanent.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game, event.getAppliedEffects());
        discard();
        return false;
    }
}
