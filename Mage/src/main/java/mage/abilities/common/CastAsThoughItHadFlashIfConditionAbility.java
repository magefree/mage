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
 * Implements:
 * You may cast {this} as though it had flash if [condition that depends on X value, targets, etc.]
 *
 * @author awjackson
 */
public class CastAsThoughItHadFlashIfConditionAbility extends SimpleStaticAbility {

    private final String rule;

    public CastAsThoughItHadFlashIfConditionAbility(Condition condition, String rule) {
        super(Zone.ALL, new CastAsThoughItHadFlashSourceEffect(Duration.EndOfGame));
        this.addEffect(new CantFlashUnlessConditionEffect(condition));
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

class CantFlashUnlessConditionEffect extends ContinuousRuleModifyingEffectImpl {

    private final Condition condition;

    public CantFlashUnlessConditionEffect(Condition condition) {
        super(Duration.EndOfGame, Outcome.Neutral);
        this.condition = condition;
    }

    private CantFlashUnlessConditionEffect(final CantFlashUnlessConditionEffect effect) {
        super(effect);
        this.condition = effect.condition;
    }

    @Override
    public CantFlashUnlessConditionEffect copy() {
        return new CantFlashUnlessConditionEffect(this);
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
        // the condition can't be evaluated until the spell is on the stack
        if (game.inCheckPlayableState()) {
            return false;
        }
        // ignore if casting as a sorcery
        if (game.isMainPhase() && game.isActivePlayer(event.getPlayerId()) && game.getStack().size() == 1) {
            return false;
        }
        // TODO: this is a hack and doesn't handle all other ways a spell could be cast as though it had flash
        if (Boolean.TRUE.equals(game.getState().getValue("PlayFromNotOwnHandZone" + source.getSourceId()))) {
            return false;
        }
        return !condition.apply(game, source);
    }
}
