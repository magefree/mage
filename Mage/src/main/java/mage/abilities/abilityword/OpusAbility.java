package mage.abilities.abilityword;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.constants.AbilityWord;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.util.CardUtil;

/**
 * @author TheElk801
 */
public class OpusAbility extends SpellCastControllerTriggeredAbility {

    public OpusAbility(Effect mainEffect, Effect extraEffect, String text, boolean doInstead) {
        super(null, StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false);
        if (doInstead) {
            this.addEffect(new ConditionalOneShotEffect(
                    wrapEffect(extraEffect), wrapEffect(mainEffect), OpusCondition.instance, text
            ));
        } else {
            this.addEffect(mainEffect);
            this.addEffect(new ConditionalOneShotEffect(wrapEffect(extraEffect), OpusCondition.instance));
        }
        this.setAbilityWord(AbilityWord.OPUS);
    }

    private OneShotEffect wrapEffect(Effect effect) {
        if (effect instanceof ContinuousEffect) {
            return new AddContinuousEffectToGame((ContinuousEffect) effect);
        }
        return (OneShotEffect) effect;
    }

    private OpusAbility(final OpusAbility ability) {
        super(ability);
    }

    @Override
    public OpusAbility copy() {
        return new OpusAbility(this);
    }
}

enum OpusCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return CardUtil
                .getEffectValueFromAbility(source, "spellCast", Spell.class)
                .map(Spell::getStackAbility)
                .map(Ability::getManaCostsToPay)
                .map(ManaCost::getUsedManaToPay)
                .map(Mana::count)
                .filter(x -> x >= 5)
                .isPresent();
    }

    @Override
    public String toString() {
        return "if five or more mana was spent to cast that spell";
    }
}
