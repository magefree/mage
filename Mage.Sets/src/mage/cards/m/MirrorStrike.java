
package mage.cards.m;

import java.util.Objects;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.UnblockedPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author L_J
 */
public final class MirrorStrike extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("unblocked creature");

    static {
        filter.add(UnblockedPredicate.instance);
    }

    public MirrorStrike(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{W}");

        // All combat damage that would be dealt to you this turn by target unblocked creature is dealt to its controller instead.
        this.getSpellAbility().addEffect(new MirrorStrikeEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
    }

    private MirrorStrike(final MirrorStrike card) {
        super(card);
    }

    @Override
    public MirrorStrike copy() {
        return new MirrorStrike(this);
    }
}

class MirrorStrikeEffect extends ReplacementEffectImpl {

    public MirrorStrikeEffect() {
        super(Duration.EndOfTurn, Outcome.RedirectDamage);
        staticText = "All combat damage that would be dealt to you this turn by target unblocked creature is dealt to its controller instead";
    }

    private MirrorStrikeEffect(final MirrorStrikeEffect effect) {
        super(effect);
    }

    @Override
    public MirrorStrikeEffect copy() {
        return new MirrorStrikeEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        DamageEvent damageEvent = (DamageEvent) event;
        if (controller != null) {
            Permanent targetPermanent = game.getPermanent(source.getFirstTarget());
            if (targetPermanent != null) {
                Player targetsController = game.getPlayer(targetPermanent.getControllerId());
                if (targetsController != null) {
                    targetsController.damage(damageEvent.getAmount(), damageEvent.getSourceId(), source, game, damageEvent.isCombatDamage(), damageEvent.isPreventable(), damageEvent.getAppliedEffects());
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        DamageEvent damageEvent = (DamageEvent) event;
        Permanent targetPermanent = game.getPermanent(source.getFirstTarget());
        if (controller != null && targetPermanent != null) {
            return (damageEvent.isCombatDamage()
                    && Objects.equals(controller.getId(), damageEvent.getTargetId())
                    && Objects.equals(targetPermanent.getId(), damageEvent.getSourceId()));
        }
        return false;
    }
}
