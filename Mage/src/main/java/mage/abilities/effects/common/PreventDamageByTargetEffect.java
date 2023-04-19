package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.PreventionEffectImpl;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.Target;
import mage.target.TargetSpell;

/**
 * @author nantuko
 */
public class PreventDamageByTargetEffect extends PreventionEffectImpl {

    public PreventDamageByTargetEffect(Duration duration) {
        this(duration, false);
    }

    public PreventDamageByTargetEffect(Duration duration, int amount) {
        this(duration, amount, false);
    }

    public PreventDamageByTargetEffect(Duration duration, boolean onlyCombat) {
        this(duration, Integer.MAX_VALUE, onlyCombat);
    }

    public PreventDamageByTargetEffect(Duration duration, int amount, boolean onlyCombat) {
        super(duration, amount, onlyCombat);
    }

    public PreventDamageByTargetEffect(final PreventDamageByTargetEffect effect) {
        super(effect);
    }

    @Override
    public PreventDamageByTargetEffect copy() {
        return new PreventDamageByTargetEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!this.used && super.applies(event, source, game)) {
            MageObject mageObject = game.getObject(event.getSourceId());
            if (mageObject != null
                    && mageObject.isInstantOrSorcery(game)) {
                for (Target target : source.getTargets()) {
                    if (target instanceof TargetSpell) {
                        if (((TargetSpell) target).getSourceIds().contains(event.getSourceId())) {
                            return true;
                        }
                    }
                }
            }
            return this.getTargetPointer().getTargets(game, source).contains(event.getSourceId());
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        if (amountToPrevent == Integer.MAX_VALUE) {
            sb.append("Prevent all");
            if (onlyCombat) {
                sb.append(" combat");
            }
            sb.append(" damage target ");
        } else {
            sb.append("Prevent the next ");
            sb.append(amountToPrevent);
            if (onlyCombat) {
                sb.append(" combat");
            }
            sb.append(" damage that ");
        }
        sb.append(mode.getTargets().get(0).getTargetName());
        sb.append(" would deal ");
        if (duration == Duration.EndOfTurn) {
            sb.append("this turn");
        } else {
            sb.append(duration);
        }
        return sb.toString();
    }
}
