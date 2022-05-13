package mage.abilities.effects;

import mage.MageObject;
import mage.abilities.Ability;
import mage.constants.Duration;
import mage.constants.EffectType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class RedirectionEffect extends ReplacementEffectImpl {

    public enum UsageType {
        ACCORDING_DURATION,
        ONE_USAGE_ABSOLUTE,
        ONE_USAGE_AT_THE_SAME_TIME; // all damage dealt at the same time
    }

    protected Target redirectTarget;
    protected int amountToRedirect;
    protected UsageType usageType;
    protected int applyEffectsCounter;

    public RedirectionEffect(Duration duration) {
        this(duration, Integer.MAX_VALUE, UsageType.ACCORDING_DURATION);
        applyEffectsCounter = -1;
    }

    public RedirectionEffect(Duration duration, int amountToRedirect, UsageType usageType) {
        super(duration, Outcome.RedirectDamage);
        this.effectType = EffectType.REDIRECTION;
        this.amountToRedirect = amountToRedirect;
        this.usageType = usageType;
    }

    public RedirectionEffect(final RedirectionEffect effect) {
        super(effect);
        this.redirectTarget = effect.redirectTarget;
        this.amountToRedirect = effect.amountToRedirect;
        this.usageType = effect.usageType;
        this.applyEffectsCounter = effect.applyEffectsCounter;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case DAMAGE_PERMANENT:
            case DAMAGE_PLAYER:
                return true;
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        int damageToRedirect = event.getAmount();
        if (damageToRedirect < 1) { // if multiple replacement effect apply, the rest damage can be 0, so the effect is not applied/replaced
            return false;
        }
        String sourceLogName = source != null ? game.getObject(source).getLogName() + ": " : "";
        DamageEvent damageEvent = (DamageEvent) event;
        int restDamage = 0;
        if (damageEvent.getAmount() > amountToRedirect) {
            restDamage = damageEvent.getAmount() - amountToRedirect;
            damageToRedirect = amountToRedirect;
        }
        if (damageToRedirect > 0 && usageType != UsageType.ACCORDING_DURATION) {
            if (UsageType.ONE_USAGE_ABSOLUTE == usageType) {
                this.discard();
            }
            if (applyEffectsCounter > 0) {
                if (applyEffectsCounter < game.getState().getApplyEffectsCounter()) {
                    this.discard();
                    return false;
                }
            } else {
                applyEffectsCounter = game.getState().getApplyEffectsCounter();
            }
        }
        Permanent permanent = game.getPermanent(redirectTarget.getFirstTarget());
        if (permanent != null) {
            permanent.damage(damageToRedirect, event.getSourceId(), source, game, damageEvent.isCombatDamage(), damageEvent.isPreventable(), event.getAppliedEffects());
            game.informPlayers(sourceLogName + "Redirected " + damageToRedirect + " damage" + getRedirectedFromText(event, game) + " to " + permanent.getLogName());
        } else {
            Player player = game.getPlayer(redirectTarget.getFirstTarget());
            if (player != null) {
                player.damage(damageToRedirect, event.getSourceId(), source, game, damageEvent.isCombatDamage(), damageEvent.isPreventable(), event.getAppliedEffects());
                game.informPlayers(sourceLogName + "Redirected " + damageToRedirect + " damage" + getRedirectedFromText(event, game) + " to " + player.getLogName());
            }
        }
        if (restDamage > 0) {
            damageEvent.setAmount(restDamage);
            return false;
        }
        return true;
    }

    private String getRedirectedFromText(GameEvent event, Game game) {
        Player player = game.getPlayer(event.getTargetId());
        if (player != null) {
            return " from " + player.getLogName();
        }
        MageObject mageObject = game.getObject(event.getTargetId());
        if (mageObject != null) {
            return " from " + mageObject.getLogName();
        }
        return "";

    }
}
