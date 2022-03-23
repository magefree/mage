package mage.cards.e;

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
import mage.players.Player;
import mage.target.TargetSource;

import java.util.UUID;

/**
 * @author L_J
 */
public final class EyeForAnEye extends CardImpl {

    public EyeForAnEye(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}{W}");

        // The next time a source of your choice would deal damage to you this turn, instead that source deals that much damage to you and Eye for an Eye deals that much damage to that source's controller.
        this.getSpellAbility().addEffect(new EyeForAnEyeEffect());
    }

    private EyeForAnEye(final EyeForAnEye card) {
        super(card);
    }

    @Override
    public EyeForAnEye copy() {
        return new EyeForAnEye(this);
    }
}

class EyeForAnEyeEffect extends ReplacementEffectImpl {

    private final TargetSource damageSource;

    public EyeForAnEyeEffect() {
        super(Duration.EndOfTurn, Outcome.RedirectDamage);
        staticText = "The next time a source of your choice would deal damage to you this turn, instead that source deals that much damage to you and {this} deals that much damage to that source's controller";
        this.damageSource = new TargetSource();
    }

    public EyeForAnEyeEffect(final EyeForAnEyeEffect effect) {
        super(effect);
        this.damageSource = effect.damageSource.copy();
    }

    @Override
    public EyeForAnEyeEffect copy() {
        return new EyeForAnEyeEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        this.damageSource.choose(Outcome.PreventDamage, source.getControllerId(), source.getSourceId(), source, game);
        super.init(source, game);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        DamageEvent damageEvent = (DamageEvent) event;
        if (controller != null) {
            controller.damage(damageEvent.getAmount(), damageEvent.getSourceId(), source, game, damageEvent.isCombatDamage(), damageEvent.isPreventable(), damageEvent.getAppliedEffects());
            UUID sourceControllerId = game.getControllerId(damageEvent.getSourceId());
            if (sourceControllerId != null) {
                Player sourceController = game.getPlayer(sourceControllerId);
                if (sourceController != null) {
                    sourceController.damage(damageEvent.getAmount(), source.getSourceId(), source, game);
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
        if (controller != null) {
            if (controller.getId().equals(damageEvent.getTargetId()) && damageEvent.getSourceId().equals(damageSource.getFirstTarget())) {
                this.discard();
                return true;
            }
        }
        return false;
    }
}
