package mage.abilities.effects.common.continuous;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceIsSpellCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.Card;
import mage.cards.SplitCardHalf;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;

import java.util.UUID;

public class CastFromHandWithoutPayingManaCostEffect extends ContinuousEffectImpl {

    private final FilterCard filter;
    private final boolean fromHand;

    public CastFromHandWithoutPayingManaCostEffect() {
        this(StaticFilters.FILTER_CARDS_NON_LAND, true);
    }

    public CastFromHandWithoutPayingManaCostEffect(FilterCard filter, boolean fromHand) {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        this.filter = filter;
        this.fromHand = fromHand;
        staticText = "You may cast " + filter.getMessage()
                + (fromHand ? " from your hand" : "")
                + " without paying their mana costs";
    }

    private CastFromHandWithoutPayingManaCostEffect(final CastFromHandWithoutPayingManaCostEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.fromHand = effect.fromHand;
    }

    @Override
    public CastFromHandWithoutPayingManaCostEffect copy() {
        return new CastFromHandWithoutPayingManaCostEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Condition condition;
        if (fromHand) {
            condition = new CompoundCondition(SourceIsSpellCondition.instance, IsBeingCastFromHandCondition.instance);
        } else {
            condition = SourceIsSpellCondition.instance;
        }
        controller.getAlternativeSourceCosts().add(new AlternativeCostSourceAbility(
                null, condition, null, filter, true
        ));
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
        MageObject object = game.getObject(source.getSourceId());
        if (object instanceof SplitCardHalf) {
            UUID splitCardId = ((Card) object).getMainCard().getId();
            object = game.getObject(splitCardId);
        }
        if (object instanceof Spell) { // needed to check if it can be cast by alternate cost
            Spell spell = (Spell) object;
            return spell.getFromZone() == Zone.HAND;
        }
        if (object instanceof Card) { // needed for the check what's playable
            Card card = (Card) object;
            return game.getPlayer(card.getOwnerId()).getHand().get(card.getId(), game) != null;
        }
        return false;
    }
}
