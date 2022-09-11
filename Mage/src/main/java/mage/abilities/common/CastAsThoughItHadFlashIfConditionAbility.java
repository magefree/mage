package mage.abilities.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashSourceEffect;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author awjackson
 */
public class CastAsThoughItHadFlashIfConditionAbility extends SimpleStaticAbility {

    private final String rule;

    public CastAsThoughItHadFlashIfConditionAbility(Condition condition, String rule) {
        super(Zone.ALL, new CastAsThoughItHadFlashSourceEffect(Duration.EndOfGame));
        this.addEffect(new CastAsThoughItHadFlashIfConditionEffect(condition));
        this.setRuleAtTheTop(true);
        this.rule = rule;
    }

    private CastAsThoughItHadFlashIfConditionAbility(final CastAsThoughItHadFlashIfConditionAbility ability) {
        super(ability);
        this.rule = ability.rule;
    }

    @Override
    public CastAsThoughItHadFlashIfConditionAbility copy() {
        return new CastAsThoughItHadFlashIfConditionAbility(this);
    }

    @Override
    public String getRule() {
        return rule;
    }
}

class CastAsThoughItHadFlashIfConditionEffect extends ContinuousRuleModifyingEffectImpl {

    private final Condition condition;

    public CastAsThoughItHadFlashIfConditionEffect(Condition condition) {
        super(Duration.EndOfGame, Outcome.Neutral);
        this.condition = condition;
    }

    private CastAsThoughItHadFlashIfConditionEffect(final CastAsThoughItHadFlashIfConditionEffect effect) {
        super(effect);
        this.condition = effect.condition;
    }

    @Override
    public CastAsThoughItHadFlashIfConditionEffect copy() {
        return new CastAsThoughItHadFlashIfConditionEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL_LATE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!event.getSourceId().equals(source.getSourceId())) {
            return false;
        }
        if (game.isMainPhase() && game.isActivePlayer(event.getPlayerId()) && game.getStack().size() == 1) {
            return false;
        }
        if (game.inCheckPlayableState()) {
            return false;
        }
        return !condition.apply(game, source);
    }
}
