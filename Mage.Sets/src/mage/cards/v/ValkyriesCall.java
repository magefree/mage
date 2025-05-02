package mage.cards.v;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author ciaccona007
 */
public final class ValkyriesCall extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("a nontoken, non-Angel creature you control");

    static {
        filter.add(TokenPredicate.FALSE);
        filter.add(Predicates.not(SubType.ANGEL.getPredicate()));
    }

    public ValkyriesCall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}{W}");

        // Whenever a nontoken, non-Angel creature you control dies, return that card to the battlefield under its owner's control with a +1/+1 counter on it. It has flying and is an Angel in addition to its other types.
        this.addAbility(new DiesCreatureTriggeredAbility(new ValkyriesCallEffect(), false, filter, true));
    }

    private ValkyriesCall(final ValkyriesCall card) {
        super(card);
    }

    @Override
    public ValkyriesCall copy() {
        return new ValkyriesCall(this);
    }
}

class ValkyriesCallEffect extends OneShotEffect {

    ValkyriesCallEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "return that card to the battlefield under its owner's control with a +1/+1 counter on it. "
                + "It has flying and is an Angel in addition to its other types";
    }

    private ValkyriesCallEffect(final ValkyriesCallEffect effect) {
        super(effect);
    }

    @Override
    public ValkyriesCallEffect copy() {
        return new ValkyriesCallEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (card == null) {
            return false;
        }
        game.addEffect(new ValkyriesCallCounterEffect(card, game), source);
        game.addEffect(new ValkyriesCallContinuousEffect(card, game), source);
        return player.moveCards(card, Zone.BATTLEFIELD, source, game);
    }
}

class ValkyriesCallCounterEffect extends ReplacementEffectImpl {

    private final MageObjectReference mor;

    ValkyriesCallCounterEffect(Card card, Game game) {
        super(Duration.EndOfStep, Outcome.BoostCreature);
        this.mor = new MageObjectReference(card, game);
    }

    private ValkyriesCallCounterEffect(final ValkyriesCallCounterEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        if (creature != null && mor.refersTo(creature, game)) {
            creature.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game, event.getAppliedEffects());
            discard();
        }
        return false;
    }

    @Override
    public ValkyriesCallCounterEffect copy() {
        return new ValkyriesCallCounterEffect(this);
    }
}

class ValkyriesCallContinuousEffect extends ContinuousEffectImpl {

    private final MageObjectReference mor;

    ValkyriesCallContinuousEffect(Card card, Game game) {
        super(Duration.Custom, Outcome.Neutral);
        this.mor = new MageObjectReference(card.getId(), card.getZoneChangeCounter(game) + 1, game);
    }


    private ValkyriesCallContinuousEffect(final ValkyriesCallContinuousEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent creature = mor.getPermanent(game);
        if (creature != null) {
            switch (layer) {
                case TypeChangingEffects_4:
                    creature.addSubType(game, SubType.ANGEL);
                    break;
                case AbilityAddingRemovingEffects_6:
                    creature.addAbility(FlyingAbility.getInstance(), source.getSourceId(), game);
                    break;
            }
            return true;
        } else {
            this.used = true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public ValkyriesCallContinuousEffect copy() {
        return new ValkyriesCallContinuousEffect(this);
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.TypeChangingEffects_4
                || layer == Layer.AbilityAddingRemovingEffects_6;
    }
}
