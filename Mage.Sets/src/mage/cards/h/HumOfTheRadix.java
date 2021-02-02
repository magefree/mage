package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterArtifactPermanent;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.UUID;
import mage.cards.Card;

/**
 * @author Pete Rossi
 */
public final class HumOfTheRadix extends CardImpl {

    public HumOfTheRadix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}{G}");

        // Each artifact spell costs {1} more to cast for each artifact its controller controls.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new HumOfTheRadixCostIncreaseEffect()));

    }

    private HumOfTheRadix(final HumOfTheRadix card) {
        super(card);
    }

    @Override
    public HumOfTheRadix copy() {
        return new HumOfTheRadix(this);
    }
}

class HumOfTheRadixCostIncreaseEffect extends CostModificationEffectImpl {

    HumOfTheRadixCostIncreaseEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, CostModificationType.INCREASE_COST);
        staticText = "each artifact spell costs {1} more to cast for each artifact its controller controls";
    }

    HumOfTheRadixCostIncreaseEffect(final HumOfTheRadixCostIncreaseEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        int additionalCost = game.getBattlefield().getAllActivePermanents(new FilterArtifactPermanent(), abilityToModify.getControllerId(), game).size();
        CardUtil.increaseCost(abilityToModify, additionalCost);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof SpellAbility) {
            Card spellCard = ((SpellAbility) abilityToModify).getCharacteristics(game);
            if (spellCard != null) {
                return !spellCard.isArtifact();
            }
        }
        return false;
    }

    @Override
    public HumOfTheRadixCostIncreaseEffect copy() {
        return new HumOfTheRadixCostIncreaseEffect(this);
    }
}
