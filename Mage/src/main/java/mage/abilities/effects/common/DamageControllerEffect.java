package mage.abilities.effects.common;

import mage.constants.Outcome;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class DamageControllerEffect extends OneShotEffect {

    protected DynamicValue amount;
    protected boolean preventable;
    private String sourceName = "{this}";

    public DamageControllerEffect(int amount) {
        this(amount, true);
    }

    public DamageControllerEffect(int amount, boolean preventable) {
        this(amount, preventable, "{this}");
    }

    public DamageControllerEffect(int amount, String whoDealDamageName) {
        this(amount, true, whoDealDamageName);
    }

    public DamageControllerEffect(int amount, boolean preventable, String whoDealDamageName) {
        this(StaticValue.get(amount), preventable, whoDealDamageName);
    }

    public DamageControllerEffect(DynamicValue amount) {
        this(amount, "{this}");
    }

    public DamageControllerEffect(DynamicValue amount, String whoDealDamageName) {
        this(amount, true, whoDealDamageName);
    }

    public DamageControllerEffect(DynamicValue amount, boolean preventable, String whoDealDamageName) {
        super(Outcome.Damage);
        this.amount = amount;
        this.preventable = preventable;
        this.sourceName = whoDealDamageName;
    }

    public DamageControllerEffect(final DamageControllerEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.preventable = effect.preventable;
        this.sourceName = effect.sourceName;
    }

    @Override
    public DamageControllerEffect copy() {
        return new DamageControllerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.damage(amount.calculate(game, source, this), source.getSourceId(), source, game, false, preventable);
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        String message = amount.getMessage();
        sb.append(this.sourceName).append(" deals ");
        if (message.isEmpty() || !message.equals("1")) {
            sb.append(amount);
        }
        sb.append(" damage to you");
        if (!message.isEmpty()) {
            if (message.equals("1")) {
                sb.append(" equal to the number of ");
            } else {
                if (message.startsWith("the") || message.startsWith("twice")) {
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
