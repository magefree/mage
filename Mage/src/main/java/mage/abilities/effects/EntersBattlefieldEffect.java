
package mage.abilities.effects;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.condition.Condition;
import mage.constants.Duration;
import mage.constants.EnterEventType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class EntersBattlefieldEffect extends ReplacementEffectImpl {

    protected Effects baseEffects = new Effects();
    protected String text;
    protected Condition condition;
    protected boolean optional;
    protected EnterEventType enterEventType;

    public static final String SOURCE_CAST_SPELL_ABILITY = "sourceCastSpellAbility";

    public EntersBattlefieldEffect(Effect baseEffect) {
        this(baseEffect, "");
    }

    public EntersBattlefieldEffect(Effect baseEffect, String text) {
        this(baseEffect, null, text, true, false);
    }

    public EntersBattlefieldEffect(Effect baseEffect, String text, boolean optional) {
        this(baseEffect, null, text, true, optional);
    }

    public EntersBattlefieldEffect(Effect baseEffect, Condition condition, String text, boolean selfScope, boolean optional) {
        this(baseEffect, condition, text, selfScope, optional, EnterEventType.OTHER);
    }

    public EntersBattlefieldEffect(Effect baseEffect, Condition condition, String text, boolean selfScope, boolean optional, EnterEventType enterEventType) {
        super(Duration.WhileOnBattlefield, baseEffect.getOutcome(), selfScope);
        this.baseEffects.add(baseEffect);
        this.enterEventType = enterEventType;
        this.text = text;
        this.condition = condition;
        this.optional = optional;
    }

    public EntersBattlefieldEffect(final EntersBattlefieldEffect effect) {
        super(effect);
        this.baseEffects = effect.baseEffects.copy();
        this.text = effect.text;
        this.condition = effect.condition;
        this.optional = effect.optional;
        this.enterEventType = effect.enterEventType;
    }

    public void addEffect(Effect effect) {
        baseEffects.add(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        switch (enterEventType) {
            case OTHER:
                return EventType.ENTERS_THE_BATTLEFIELD == event.getType();
            case SELF:
                return EventType.ENTERS_THE_BATTLEFIELD_SELF == event.getType();
            case CONTROL:
                return EventType.ENTERS_THE_BATTLEFIELD_CONTROL == event.getType();
            case COPY:
                return EventType.ENTERS_THE_BATTLEFIELD_COPY == event.getType();
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getTargetId().equals(source.getSourceId())) {
            if (condition == null || condition.apply(game, source)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        if (optional) {
            Player controller = game.getPlayer(source.getControllerId());
            MageObject object = game.getPermanentEntering(source.getSourceId());
            if (object == null) {
                object = game.getObject(source);
            }
            if (controller == null || object == null) {
                return false;
            }
            if (!controller.chooseUse(outcome, "Use effect of " + object.getLogName() + '?', source, game)) {
                return false;
            }
        }
        Spell entersBySpell = game.getStack().getSpell(event.getSourceId());
        if (entersBySpell == null) {
            StackObject stackObject = (StackObject) game.getLastKnownInformation(event.getSourceId(), Zone.STACK);
            if (stackObject instanceof Spell) {
                entersBySpell = (Spell) stackObject;
            }
        }
        for (Effect effect : baseEffects) {
            if (effect instanceof ContinuousEffect) {
                game.addEffect((ContinuousEffect) effect, source);
            } else {
                if (entersBySpell != null) {
                    effect.setValue(SOURCE_CAST_SPELL_ABILITY, entersBySpell.getSpellAbility());
                }
                effect.setValue("appliedEffects", event.getAppliedEffects());
                effect.apply(game, source);
            }
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return (text == null || text.isEmpty()) ? baseEffects.getText(mode) : text;
    }

    @Override
    public EntersBattlefieldEffect copy() {
        return new EntersBattlefieldEffect(this);
    }

}
