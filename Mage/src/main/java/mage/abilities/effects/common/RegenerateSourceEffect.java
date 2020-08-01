package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.AttachedToPermanentCondition;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.hint.ConditionHint;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class RegenerateSourceEffect extends ReplacementEffectImpl {

    public RegenerateSourceEffect() {
        super(Duration.EndOfTurn, Outcome.Regenerate);
        staticText = "Regenerate {this}";
    }

    public RegenerateSourceEffect(String targetName) {
        super(Duration.EndOfTurn, Outcome.Regenerate);
        staticText = "Regenerate " + targetName;
    }

    public RegenerateSourceEffect(final RegenerateSourceEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        //20110204 - 701.11
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null && permanent.regenerate(source, game)) {
            this.used = true;
            return true;
        }
        return false;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);

        RegenerateSourceEffect.initRegenerationInfo(game, source, source.getSourceId());
    }

    @Override
    public RegenerateSourceEffect copy() {
        return new RegenerateSourceEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return apply(game, source);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DESTROY_PERMANENT;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        //20110204 - 701.11c - event.getAmount() is used to signal if regeneration is allowed
        return event.getAmount() == 0 && event.getTargetId().equals(source.getSourceId()) && !this.used;
    }

    public static void initRegenerationInfo(Game game, Ability source, UUID permanentId) {
        // enable regen info
        Permanent permanent = game.getPermanent(permanentId);
        if (permanent != null) {
            game.getState().setValue(CardUtil.getCardZoneString("RegenerationActivated", permanent.getId(), game), Boolean.TRUE);
            InfoEffect.addCardHintToPermanent(game, source, permanent,
                    new ConditionHint(RegeneratingCanBeUsedCondition.instance, "Permanent will be regenerated instead destroy"),
                    Duration.EndOfTurn
            );
        }
    }

    public static void initRegenerationInfoWhileAttached(Game game, Ability source, UUID permanentId) {
        // enable regen info for attached permanent
        Permanent permanent = game.getPermanent(permanentId);
        if (permanent != null) {
            game.getState().setValue(CardUtil.getCardZoneString("RegenerationActivated", permanent.getId(), game), Boolean.TRUE);

            InfoEffect.addCardHintToPermanentConditional(game, source, permanent,
                    new ConditionHint(RegeneratingCanBeUsedCondition.instance, "Permanent will be regenerated instead destroy"),
                    Duration.EndOfTurn,
                    new AttachedToPermanentCondition(permanent.getId())
            );
        }
    }
}


enum RegeneratingCanBeUsedCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            Boolean shieldActivated = (Boolean) game.getState().getValue(CardUtil.getCardZoneString("RegenerationActivated", permanent.getId(), game));
            return shieldActivated != null && shieldActivated;
        }
        return false;
    }
}
