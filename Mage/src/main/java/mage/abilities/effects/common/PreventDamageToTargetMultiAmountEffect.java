package mage.abilities.effects.common;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.PreventionEffectImpl;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetAmount;

/**
 *
 * @author LevelX2
 */
public class PreventDamageToTargetMultiAmountEffect extends PreventionEffectImpl {

    private final Map<UUID, Integer> targetAmountMap = new HashMap<>();

    public PreventDamageToTargetMultiAmountEffect(Duration duration, int amount) {
        super(duration, amount, false);
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
        MageObject sourceObject = game.getObject(source.getSourceId());
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
        GameEvent preventEvent = new GameEvent(GameEvent.EventType.PREVENT_DAMAGE, event.getTargetId(), source.getSourceId(), source.getControllerId(), event.getAmount(), false);
        if (!game.replaceEvent(preventEvent)) {
            if (event.getAmount() >= targetAmount) {
                int damage = targetAmount;
                event.setAmount(event.getAmount() - targetAmount);
                targetAmountMap.remove(event.getTargetId());
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE, event.getTargetId(), source.getSourceId(), source.getControllerId(), damage));
            } else {
                int damage = event.getAmount();
                event.setAmount(0);
                targetAmountMap.put(event.getTargetId(), targetAmount -= damage);
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE, event.getTargetId(), source.getSourceId(), source.getControllerId(), damage));
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
        sb.append("prevent the next ").append(amountToPrevent).append(" damage that would be dealt ");
        if (duration == Duration.EndOfTurn) {
            sb.append("this turn ");
        }
        sb.append("to any number of targets, divided as you choose");
        return sb.toString();
    }

}
