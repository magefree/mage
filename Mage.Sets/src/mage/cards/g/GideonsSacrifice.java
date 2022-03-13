package mage.cards.g;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GideonsSacrifice extends CardImpl {

    public GideonsSacrifice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Choose a creature or planeswalker you control. All damage that would be dealt this turn to you and permanents you control is dealt to the chosen permanent instead.
        this.getSpellAbility().addEffect(new GideonsSacrificeEffect());
    }

    private GideonsSacrifice(final GideonsSacrifice card) {
        super(card);
    }

    @Override
    public GideonsSacrifice copy() {
        return new GideonsSacrifice(this);
    }
}

class GideonsSacrificeEffect extends OneShotEffect {

    private static final FilterPermanent filter
            = new FilterControlledPermanent("creature or planeswalker you controls");

    static {
        filter.add(Predicates.or(
                CardType.PLANESWALKER.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
    }

    GideonsSacrificeEffect() {
        super(Outcome.Benefit);
        staticText = "Choose a creature or planeswalker you control. " +
                "All damage that would be dealt this turn to you " +
                "and permanents you control is dealt to the chosen permanent instead.";
    }

    private GideonsSacrificeEffect(final GideonsSacrificeEffect effect) {
        super(effect);
    }

    @Override
    public GideonsSacrificeEffect copy() {
        return new GideonsSacrificeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Target target = new TargetPermanent(filter);
        target.setNotTarget(true);
        if (!player.choose(outcome, target, source, game)) {
            return false;
        }
        game.addEffect(new GideonsSacrificeEffectReplacementEffect(
                new MageObjectReference(target.getFirstTarget(), game)
        ), source);
        return true;
    }
}

class GideonsSacrificeEffectReplacementEffect extends ReplacementEffectImpl {

    private final MageObjectReference mor;

    GideonsSacrificeEffectReplacementEffect(MageObjectReference mor) {
        super(Duration.EndOfTurn, Outcome.RedirectDamage);
        this.mor = mor;
    }

    private GideonsSacrificeEffectReplacementEffect(final GideonsSacrificeEffectReplacementEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case DAMAGE_PLAYER:
            case DAMAGE_PERMANENT:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGE_PLAYER
                && event.getPlayerId().equals(source.getControllerId())) {
            return true;
        }
        if (event.getType() == GameEvent.EventType.DAMAGE_PERMANENT) {
            Permanent targetPermanent = game.getPermanent(event.getTargetId());
            if (targetPermanent != null
                    && targetPermanent.isControlledBy(source.getControllerId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        DamageEvent damageEvent = (DamageEvent) event;

        Permanent permanent = mor.getPermanent(game);

        if (permanent == null) {
            return false;
        }

        // Name of old target
        Permanent targetPermanent = game.getPermanent(event.getTargetId());
        StringBuilder message = new StringBuilder();
        message.append(permanent.getName()).append(": gets ");
        message.append(damageEvent.getAmount()).append(" damage redirected from ");
        if (targetPermanent != null) {
            message.append(targetPermanent.getName());
        } else {
            Player targetPlayer = game.getPlayer(event.getTargetId());
            if (targetPlayer != null) {
                message.append(targetPlayer.getLogName());
            } else {
                message.append("unknown");
            }

        }
        game.informPlayers(message.toString());
        // Redirect damage
        permanent.damage(
                damageEvent.getAmount(), damageEvent.getSourceId(), source, game,
                damageEvent.isCombatDamage(), damageEvent.isPreventable(), event.getAppliedEffects()
        );
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public GideonsSacrificeEffectReplacementEffect copy() {
        return new GideonsSacrificeEffectReplacementEffect(this);
    }
}
