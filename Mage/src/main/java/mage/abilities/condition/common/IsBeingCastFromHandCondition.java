package mage.abilities.condition.common;


import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.cards.AdventureCardSpell;
import mage.cards.Card;
import mage.cards.ModalDoubleFacedCardHalf;
import mage.cards.SplitCardHalf;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.stack.Spell;

import java.util.UUID;

public enum IsBeingCastFromHandCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source);
        if (object instanceof SplitCardHalf || object instanceof AdventureCardSpell || object instanceof ModalDoubleFacedCardHalf) {
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
