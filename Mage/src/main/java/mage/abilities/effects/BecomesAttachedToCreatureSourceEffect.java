package mage.abilities.effects;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.condition.Condition;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.StackObject;
import mage.players.Player;

/**
 * Based on {@link EntersBattlefieldEffect}.
 * This allows rule wording such as "as {this} becomes attached to a creature...".
 * See [[Dinosaur Headdress]].
 * For this, we do not want a trigger, so a replacement effect is used to add
 * one or more effects to the attach effect.
 *
 * @author DominionSpy
 */
public class BecomesAttachedToCreatureSourceEffect extends ReplacementEffectImpl {

    protected Effects baseEffects = new Effects();
    protected String text;
    protected Condition condition;
    protected boolean optional;

    public static final String SOURCE_OBJECT_STACK_ABILITY = "sourceObjectStackAbility";

    public BecomesAttachedToCreatureSourceEffect(Effect baseEffect) {
        this(baseEffect, "");
    }

    public BecomesAttachedToCreatureSourceEffect(Effect baseEffect, String text) {
        this(baseEffect, text, false);
    }

    public BecomesAttachedToCreatureSourceEffect(Effect baseEffect, String text, boolean optional) {
        this(baseEffect, null, text, optional);
    }

    public BecomesAttachedToCreatureSourceEffect(Effect baseEffect, Condition condition, String text, boolean optional) {
        super(Duration.WhileOnBattlefield, baseEffect.getOutcome(), false);
        this.baseEffects.add(baseEffect);
        this.text = text;
        this.condition = condition;
        this.optional = optional;
    }

    protected BecomesAttachedToCreatureSourceEffect(final BecomesAttachedToCreatureSourceEffect effect) {
        super(effect);
        this.baseEffects = effect.baseEffects.copy();
        this.text = effect.text;
        this.condition = effect.condition;
        this.optional = effect.optional;
    }

    @Override
    public BecomesAttachedToCreatureSourceEffect copy() {
        return new BecomesAttachedToCreatureSourceEffect(this);
    }

    public void addEffect(Effect effect) {
        baseEffects.add(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return GameEvent.EventType.ATTACH == event.getType();
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getSourceId().equals(source.getSourceId())) {
            return condition == null || condition.apply(game, source);
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
        StackObject attachesByObject = game.getStack().getStackObject(event.getSourceId());
        if (attachesByObject == null) {
            attachesByObject = (StackObject) game.getLastKnownInformation(event.getSourceId(), Zone.STACK);
        }
        for (Effect effect : baseEffects) {
            if (effect instanceof ContinuousEffect) {
                game.addEffect((ContinuousEffect) effect, source);
            } else {
                if (attachesByObject != null) {
                    effect.setValue(SOURCE_OBJECT_STACK_ABILITY, attachesByObject.getStackAbility());
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
}
