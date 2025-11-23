package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 * @author North
 */
public class DamageTargetEffect extends OneShotEffect {

    private final DynamicValue amount;
    private boolean preventable = true;
    private String sourceName = "{this}";

    public DamageTargetEffect(int amount) {
        this(StaticValue.get(amount));
    }

    public DamageTargetEffect(int amount, String whoDealDamageName) {
        this(amount);
        this.sourceName = whoDealDamageName;
    }

    public DamageTargetEffect(DynamicValue amount) {
        super(Outcome.Damage);
        this.amount = amount;
    }

    public DamageTargetEffect(DynamicValue amount, String whoDealDamageName) {
        this(amount);
        this.sourceName = whoDealDamageName;
    }

    protected DamageTargetEffect(final DamageTargetEffect effect) {
        super(effect);
        this.amount = effect.amount.copy();
        this.preventable = effect.preventable;
        this.sourceName = effect.sourceName;
    }

    public DamageTargetEffect withCantBePrevented() {
        this.preventable = false;
        return this;
    }

    @Override
    public DamageTargetEffect copy() {
        return new DamageTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID targetId : this.getTargetPointer().getTargets(game, source)) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null) {
                permanent.damage(amount.calculate(game, source, this), source.getSourceId(), source, game, false, preventable);
            } else {
                Player player = game.getPlayer(targetId);
                if (player != null) {
                    player.damage(amount.calculate(game, source, this), source.getSourceId(), source, game, false, preventable);
                }
            }
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        String message = amount.getMessage();
        sb.append(this.sourceName).append(" deals ");
        if (!message.equals("1")) {
            sb.append(amount);
        }
        if (!sb.toString().endsWith(" ")) {
            sb.append(' ');
        }
        sb.append("damage to ");
        String targetDescription = getTargetPointer().describeTargets(mode.getTargets(), "that target");
        if (targetDescription.startsWith("up to") && !targetDescription.startsWith("up to one")) {
            sb.append("each of ");
        }
        sb.append(targetDescription);
        if (!message.isEmpty()) {
            if (message.equals("1")) {
                sb.append(" equal to the number of ");
            } else {
                if (message.startsWith("the") || message.startsWith("that") || message.startsWith("twice")) {
                    sb.append(" equal to ");
                } else {
                    sb.append(" for each ");
                }
            }
            sb.append(message);
        }
        if (!preventable) {
            sb.append(". The damage can't be prevented");
        }
        return sb.toString();
    }
}
