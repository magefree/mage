
package mage.cards.b;

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

/**
 *
 * @author L_J
 */
public final class BloodOfTheMartyr extends CardImpl {

    public BloodOfTheMartyr(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}{W}{W}");

        // Until end of turn, if damage would be dealt to any creature, you may have that damage dealt to you instead.
        this.getSpellAbility().addEffect(new BloodOfTheMartyrEffect());
    }

    private BloodOfTheMartyr(final BloodOfTheMartyr card) {
        super(card);
    }

    @Override
    public BloodOfTheMartyr copy() {
        return new BloodOfTheMartyr(this);
    }
}

class BloodOfTheMartyrEffect extends ReplacementEffectImpl {

    public BloodOfTheMartyrEffect() {
        super(Duration.EndOfTurn, Outcome.RedirectDamage);
        staticText = "Until end of turn, if damage would be dealt to any creature, you may have that damage dealt to you instead";
    }

    private BloodOfTheMartyrEffect(final BloodOfTheMartyrEffect effect) {
        super(effect);
    }

    @Override
    public BloodOfTheMartyrEffect copy() {
        return new BloodOfTheMartyrEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PERMANENT;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        DamageEvent damageEvent = (DamageEvent) event;
        if (controller != null) {
            controller.damage(damageEvent.getAmount(), damageEvent.getSourceId(), source, game, damageEvent.isCombatDamage(), damageEvent.isPreventable(), damageEvent.getAppliedEffects());
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(event.getTargetId());
        DamageEvent damageEvent = (DamageEvent) event;
        return controller != null
                && permanent != null
                && permanent.isCreature(game)
                && controller.chooseUse(outcome, "Have " + damageEvent.getAmount() + " damage dealt to you instead of " + permanent.getLogName() + "?", source, game);
    }
}
