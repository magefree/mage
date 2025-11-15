package mage.abilities.keyword;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.constants.TimingRule;
import mage.game.Game;
import mage.target.Target;
import mage.target.targetpointer.FixedTargets;

import java.util.stream.Collectors;

/**
 * 702.94. Overload
 * <p>
 * 702.94a. Overload is a keyword that represents two static abilities: one that
 * functions from any zone in which the spell with overload can be cast and
 * another that functions while the card is on the stack. Overload [cost] means
 * "You may choose to pay [cost] rather than pay this spell's mana cost" and "If
 * you chose to pay this spell's overload cost, change its text by replacing all
 * instances of the word 'target' with the word 'each.'" Using the overload
 * ability follows the rules for paying alternative costs in rules 601.2b and
 * 601.2e-g.
 * <p>
 * 702.94b. If a player chooses to pay the overload cost of a spell, that spell
 * won't require any targets. It may affect objects that couldn't be chosen as
 * legal targets if the spell were cast without its overload cost being paid.
 * <p>
 * 702.94c. Overload's second ability creates a text-changing effect. See rule
 * 612, "Text-Changing Effects."
 *
 * @author LevelX2
 */
public class OverloadAbility extends SpellAbility {

    public static void ImplementOverloadAbility(Card card, ManaCosts costs, Target target, Effect... effects) {
        card.getSpellAbility().addTarget(target.copy());
        Ability overload = new OverloadAbility(card, costs);
        for (Effect effect : effects) {
            card.getSpellAbility().addEffect(effect.copy());
            OverloadedEffect overloadEffect = new OverloadedEffect(effect, target.copy());
            overloadEffect.setText(effect.getText(card.getSpellAbility().getModes().getMode())
                    .replace("target", "each"));
            overload.addEffect(overloadEffect);
        }
        card.addAbility(overload);
    }

    public OverloadAbility(Card card, ManaCosts costs) {
        super(costs, card.getName() + " with overload");
        this.spellAbilityType = SpellAbilityType.BASE_ALTERNATE;
        this.timing = (card.isSorcery(null) ? TimingRule.SORCERY : TimingRule.INSTANT);
    }

    //TODO: Remove once all Overload cards have been converted
    public OverloadAbility(Card card, Effect effect, ManaCosts costs) {
        super(costs, card.getName() + " with overload");
        this.spellAbilityType = SpellAbilityType.BASE_ALTERNATE;
        this.addEffect(effect);
        this.timing = (card.isSorcery(null) ? TimingRule.SORCERY : TimingRule.INSTANT);
    }

    protected OverloadAbility(final OverloadAbility ability) {
        super(ability);
    }

    @Override
    public OverloadAbility copy() {
        return new OverloadAbility(this);
    }

    @Override
    public String getRule(boolean all) {
        return getRule();
    }

    @Override
    public String getRule() {
        return "Overload " + getManaCostsToPay().getText() + " <i>(You may cast this spell for its overload cost. If you do, change its text by replacing all instances of \"target\" with \"each.\")</i>";
    }

}

class OverloadedEffect extends OneShotEffect {
    Effect innerEffect;
    Target target;

    public OverloadedEffect(Effect innerEffect, Target target) {
        super(Outcome.Benefit);
        this.innerEffect = innerEffect;
        this.target = target.withNotTarget(true);
    }

    protected OverloadedEffect(final OverloadedEffect effect) {
        super(effect);
        this.innerEffect = effect.innerEffect;
        this.target = effect.target;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        innerEffect.setTargetPointer(new FixedTargets(
                target.possibleTargets(source.getControllerId(), source, game)
                        .stream().map(id -> new MageObjectReference(id, game))
                        .collect(Collectors.toSet())));
        if (innerEffect instanceof OneShotEffect) {
            return innerEffect.apply(game, source);
        } else {
            game.addEffect((ContinuousEffect) innerEffect, source);
            return true;
        }
    }

    @Override
    public OverloadedEffect copy() {
        return new OverloadedEffect(this);
    }
}
