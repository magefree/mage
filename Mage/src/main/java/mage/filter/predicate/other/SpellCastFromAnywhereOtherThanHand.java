package mage.filter.predicate.other;

import mage.cards.Card;
import mage.constants.Zone;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.stack.Spell;

public enum SpellCastFromAnywhereOtherThanHand implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        if (input.getObject() instanceof Spell) {
            return !input.getObject().isOwnedBy(input.getPlayerId())
                    || !Zone.HAND.match(((Spell) input.getObject()).getFromZone());
        } else {
            return !input.getObject().isOwnedBy(input.getPlayerId())
                    || !Zone.HAND.match(game.getState().getZone(input.getObject().getId()));
        }
    }
}
