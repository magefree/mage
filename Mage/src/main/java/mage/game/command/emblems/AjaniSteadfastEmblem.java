package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventionEffectImpl;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 * @author spjspj
 */
public final class AjaniSteadfastEmblem extends Emblem {

    public AjaniSteadfastEmblem() {
        super("Emblem Ajani");
        this.getAbilities().add(new SimpleStaticAbility(Zone.COMMAND, new AjaniSteadfastPreventEffect()));
    }

    private AjaniSteadfastEmblem(final AjaniSteadfastEmblem card) {
        super(card);
    }

    @Override
    public AjaniSteadfastEmblem copy() {
        return new AjaniSteadfastEmblem(this);
    }
}

class AjaniSteadfastPreventEffect extends PreventionEffectImpl {

    public AjaniSteadfastPreventEffect() {
        super(Duration.EndOfGame);
        this.staticText = "If a source would deal damage to you or a planeswalker you control, prevent all but 1 of that damage";
        consumable = false;
    }

    public AjaniSteadfastPreventEffect(AjaniSteadfastPreventEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        int damage = event.getAmount();
        if (damage > 1) {
            amountToPrevent = damage - 1;
            preventDamageAction(event, source, game);
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGE_PLAYER
                && event.getTargetId().equals(source.getControllerId())) {
            return super.applies(event, source, game);
        }

        if (event.getType() == GameEvent.EventType.DAMAGE_PERMANENT) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null && permanent.isPlaneswalker(game) && permanent.isControlledBy(source.getControllerId())) {
                return super.applies(event, source, game);
            }
        }
        return false;
    }

    @Override
    public AjaniSteadfastPreventEffect copy() {
        return new AjaniSteadfastPreventEffect(this);
    }
}
