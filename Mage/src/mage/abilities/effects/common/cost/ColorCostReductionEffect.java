package mage.abilities.effects.common.cost;

import mage.Constants;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.effects.CostModificationEffectImpl;
import mage.cards.Card;
import mage.game.Game;
import mage.util.CardUtil;

/**
 * @author noxx
 */
public class ColorCostReductionEffect extends CostModificationEffectImpl<ColorCostReductionEffect> {

    private ObjectColor color;
    private int reduceCount;

    public ColorCostReductionEffect(ObjectColor color, int reduceCount, String ruleText) {
        super(Constants.Duration.WhileOnBattlefield, Constants.Outcome.Benefit);
        staticText = ruleText;
        this.color = color;
        this.reduceCount = reduceCount;
    }

    private ColorCostReductionEffect(ColorCostReductionEffect effect) {
        super(effect);
        this.color = effect.color;
        this.reduceCount = effect.reduceCount;
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        SpellAbility spellAbility = (SpellAbility) abilityToModify;
        CardUtil.adjustCost(spellAbility, this.reduceCount);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if ( abilityToModify instanceof SpellAbility ) {
            Card sourceCard = game.getCard(abilityToModify.getSourceId());
            if ( sourceCard != null && sourceCard.getColor().contains(color) && sourceCard.getOwnerId().equals(source.getControllerId()) ) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ColorCostReductionEffect copy() {
        return new ColorCostReductionEffect(this);
    }

}