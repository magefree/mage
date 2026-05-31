package mage.abilities.effects.common;

import mage.abilities.common.delayed.ManaSpentDelayedTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 * @author xenohedron, Susucr
 */
public class ManaSpentOnSpellGainsAbilityEffect extends CreateDelayedTriggeredAbilityEffect {

    public ManaSpentOnSpellGainsAbilityEffect(FilterSpell filter, ContinuousEffect effect) {
        super(new ManaSpentToCastSpellDelayedTriggeredAbility(filter, new AddContinuousEffectToGame(effect)));
    }

    public ManaSpentOnSpellGainsAbilityEffect(FilterSpell filter, OneShotEffect effect) {
        super(new ManaSpentToCastSpellDelayedTriggeredAbility(filter, effect));
    }

    protected ManaSpentOnSpellGainsAbilityEffect(final ManaSpentOnSpellGainsAbilityEffect effect) {
        super(effect);
    }

    @Override
    public ManaSpentOnSpellGainsAbilityEffect copy() {
        return new ManaSpentOnSpellGainsAbilityEffect(this);
    }

}

class ManaSpentToCastSpellDelayedTriggeredAbility extends ManaSpentDelayedTriggeredAbility {

    ManaSpentToCastSpellDelayedTriggeredAbility(FilterSpell filter, Effect effect) {
        super(effect, filter);
        this.usesStack = false;
        this.triggerOnlyOnce = false;
        setTriggerPhrase("If that mana is spent on " + CardUtil.addArticle(filter.getMessage()) + ", ");
    }

    private ManaSpentToCastSpellDelayedTriggeredAbility(final ManaSpentToCastSpellDelayedTriggeredAbility effect) {
        super(effect);
    }

    @Override
    public ManaSpentToCastSpellDelayedTriggeredAbility copy() {
        return new ManaSpentToCastSpellDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!super.checkTrigger(event, game)) {
            return false;
        }
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell == null) {
            return false;
        }
        getEffects().setTargetPointer(new FixedTarget(spell.getCard(), game));
        return true;
    }
}
