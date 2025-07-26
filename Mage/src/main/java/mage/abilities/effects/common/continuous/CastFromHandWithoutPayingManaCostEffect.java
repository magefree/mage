package mage.abilities.effects.common.continuous;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.IsBeingCastFromHandCondition;
import mage.abilities.condition.common.SourceIsSpellCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.List;

public class CastFromHandWithoutPayingManaCostEffect extends ContinuousEffectImpl {

    private final AlternativeCostSourceAbility alternativeCastingCostAbility;

    public CastFromHandWithoutPayingManaCostEffect() {
        this(StaticFilters.FILTER_CARDS_NON_LAND, true);
    }

    public CastFromHandWithoutPayingManaCostEffect(FilterCard filter, boolean fromHand) {
        this(filter, fromHand, Duration.WhileOnBattlefield);
    }

    public CastFromHandWithoutPayingManaCostEffect(FilterCard filter, boolean fromHand, Duration duration) {
        super(duration, Layer.RulesEffects, SubLayer.NA, Outcome.Detriment);
        Condition condition;
        if (fromHand) {
            condition = new CompoundCondition(SourceIsSpellCondition.instance, IsBeingCastFromHandCondition.instance);
        } else {
            condition = SourceIsSpellCondition.instance;
        }
        this.alternativeCastingCostAbility = new AlternativeCostSourceAbility(null, condition, null, filter, true);
        this.staticText = "You may cast " + filter.getMessage()
                + (fromHand ? " from your hand" : "")
                + " without paying their mana costs";
    }

    private CastFromHandWithoutPayingManaCostEffect(final CastFromHandWithoutPayingManaCostEffect effect) {
        super(effect);
        this.alternativeCastingCostAbility = effect.alternativeCastingCostAbility;
    }

    @Override
    public CastFromHandWithoutPayingManaCostEffect copy() {
        return new CastFromHandWithoutPayingManaCostEffect(this);
    }

    @Override
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
        for (MageItem object : affectedObjects) {
            Player player = (Player) object;
            alternativeCastingCostAbility.setSourceId(source.getSourceId());
            player.getAlternativeSourceCosts().add(alternativeCastingCostAbility);
        }
    }

    @Override
    public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        affectedObjects.add(controller);
        return true;
    }
}
