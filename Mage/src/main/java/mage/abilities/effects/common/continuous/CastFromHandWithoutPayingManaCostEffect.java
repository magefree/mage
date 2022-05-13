package mage.abilities.effects.common.continuous;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceIsSpellCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.AdventureCardSpell;
import mage.cards.Card;
import mage.cards.ModalDoubleFacesCardHalf;
import mage.cards.SplitCardHalf;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.Spell;
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

enum IsBeingCastFromHandCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source);
        if (object instanceof SplitCardHalf || object instanceof AdventureCardSpell || object instanceof ModalDoubleFacesCardHalf) {
            UUID mainCardId = ((Card) object).getMainCard().getId();
            object = game.getObject(mainCardId);
        }
        if (object instanceof Spell) { // needed to check if it can be cast by alternate cost
            Spell spell = (Spell) object;
            return Zone.HAND.equals(spell.getFromZone());
        }
        if (object instanceof Card) { // needed for the check what's playable
            Card card = (Card) object;
            return game.getPlayer(card.getOwnerId()).getHand().get(card.getId(), game) != null;
        }
        return false;
    }
}
