package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Pete Rossi
 */
public final class HumOfTheRadix extends CardImpl {

    public HumOfTheRadix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}{G}");

        // Each artifact spell costs {1} more to cast for each artifact its controller controls.
        this.addAbility(new SimpleStaticAbility(new HumOfTheRadixCostIncreaseEffect()));
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
        CardUtil.increaseCost(abilityToModify, game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT,
                abilityToModify.getControllerId(), source, game
        ).size());
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (!(abilityToModify instanceof SpellAbility)) {
            return false;
        }
        Card spellCard = ((SpellAbility) abilityToModify).getCharacteristics(game);
        return spellCard != null && spellCard.isArtifact(game);
    }

    @Override
    public HumOfTheRadixCostIncreaseEffect copy() {
        return new HumOfTheRadixCostIncreaseEffect(this);
    }
}
