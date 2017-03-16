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
import mage.filter.common.FilterNonlandCard;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;

import java.util.UUID;

public class CastFromHandWithoutPayingManaCostEffect extends ContinuousEffectImpl {

    public CastFromHandWithoutPayingManaCostEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "You may cast nonland cards from your hand without paying their mana costs";
    }

    public CastFromHandWithoutPayingManaCostEffect(final CastFromHandWithoutPayingManaCostEffect effect) {
        super(effect);
    }

    @Override
    public CastFromHandWithoutPayingManaCostEffect copy() {
        return new CastFromHandWithoutPayingManaCostEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.getAlternativeSourceCosts().add(new AlternativeCostSourceAbility(
                    null, new CompoundCondition(SourceIsSpellCondition.instance, new IsBeingCastFromHandCondition()), null, new FilterNonlandCard(), true));
            return true;
        }
        return false;
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

class IsBeingCastFromHandCondition implements Condition {

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
