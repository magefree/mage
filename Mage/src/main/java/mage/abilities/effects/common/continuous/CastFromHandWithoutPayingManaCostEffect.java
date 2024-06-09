package mage.abilities.effects.common.continuous;

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

import java.util.UUID;

public class CastFromHandWithoutPayingManaCostEffect extends ContinuousEffectImpl {

    private final AlternativeCostSourceAbility alternativeCastingCostAbility;

    public CastFromHandWithoutPayingManaCostEffect() {
        this(StaticFilters.FILTER_CARDS_NON_LAND, true);
    }

    public CastFromHandWithoutPayingManaCostEffect(FilterCard filter, boolean fromHand) {
        this(filter, fromHand, Duration.WhileOnBattlefield);
    }

    public CastFromHandWithoutPayingManaCostEffect(FilterCard filter, boolean fromHand, Duration duration) {
        super(duration, Outcome.Detriment);
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
    public void init(Ability source, Game game, UUID activePlayerId) {
        super.init(source, game, activePlayerId);
        alternativeCastingCostAbility.setSourceId(source.getSourceId());
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        controller.getAlternativeSourceCosts().add(alternativeCastingCostAbility);
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.RulesEffects;
    }
}