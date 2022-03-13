package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.PreventionEffectImpl;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.events.PreventDamageEvent;
import mage.game.events.PreventedDamageEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetAmount;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author LevelX2
 */
public class PreventDamageToTargetMultiAmountEffect extends PreventionEffectImpl {

    private final Map<UUID, Integer> targetAmountMap = new HashMap<>();

    public PreventDamageToTargetMultiAmountEffect(Duration duration, int amount) {
        super(duration, amount, false);
    }

    public PreventDamageToTargetMultiAmountEffect(Duration duration, int amount, boolean onlyCombat, boolean consumable, DynamicValue dynamicValue) {
        super(duration, amount, onlyCombat, consumable, dynamicValue);
    }

    public PreventDamageToTargetMultiAmountEffect(final PreventDamageToTargetMultiAmountEffect effect) {
        super(effect);
    }

    @Override
    public PreventDamageToTargetMultiAmountEffect copy() {
        return new PreventDamageToTargetMultiAmountEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        Target target = source.getTargets().get(0);
        MageObject sourceObject = game.getObject(source);
        if (target instanceof TargetAmount && sourceObject != null) {
            TargetAmount multiTarget = (TargetAmount) target;
            for (UUID targetId : multiTarget.getTargets()) {
                Player player = null;
                Permanent permanent = game.getPermanent(targetId);
                if (permanent == null) {
                    player = game.getPlayer(targetId);
                }
                targetAmountMap.put(targetId, multiTarget.getTargetAmount(targetId));
                if (!game.isSimulation()) {
                    StringBuilder sb = new StringBuilder(sourceObject.getName()).append(": Prevent the next ");
                    sb.append(multiTarget.getTargetAmount(targetId)).append(" damage to ");
                    if (player != null) {
                        sb.append(player.getLogName());
                    } else if (permanent != null) {
                        sb.append(permanent.getName());
                    }
                    game.informPlayers(sb.toString());
                }
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        int targetAmount = targetAmountMap.get(event.getTargetId());
        GameEvent preventEvent = new PreventDamageEvent(event.getTargetId(), source.getSourceId(), source, source.getControllerId(), event.getAmount(), ((DamageEvent) event).isCombatDamage());
        if (!game.replaceEvent(preventEvent)) {
            if (event.getAmount() >= targetAmount) {
                event.setAmount(event.getAmount() - targetAmount);
                targetAmountMap.remove(event.getTargetId());
                game.fireEvent(new PreventedDamageEvent(event.getTargetId(), source.getSourceId(), source, source.getControllerId(), targetAmount));
            } else {
                int damage = event.getAmount();
                event.setAmount(0);
                targetAmountMap.put(event.getTargetId(), targetAmount - damage);
                game.fireEvent(new PreventedDamageEvent(event.getTargetId(), source.getSourceId(), source, source.getControllerId(), damage));
            }
            if (targetAmountMap.isEmpty()) {
                this.used = true;
            }
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return !used && super.applies(event, source, game) && targetAmountMap.containsKey(event.getTargetId());
    }

    @Override
    public String getText(Mode mode) {
        StringBuilder sb = new StringBuilder();
        if (staticText.isEmpty()) {
            sb.append("prevent the next ").append(amountToPrevent).append(" damage that would be dealt ");
            if (duration == Duration.EndOfTurn) {
                sb.append("this turn ");
            }
            sb.append("to any number of targets, divided as you choose");
            return sb.toString();
        }
        return staticText;
    }

}
