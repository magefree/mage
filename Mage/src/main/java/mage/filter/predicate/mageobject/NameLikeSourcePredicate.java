package mage.filter.predicate.mageobject;

import mage.MageObject;
import mage.cards.CardWithHalves;
import mage.cards.SplitCard;
import mage.constants.SpellAbilityType;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.util.CardUtil;

/**
 * @author Susucr
 */
public enum NameLikeSourcePredicate implements ObjectSourcePlayerPredicate<MageObject> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<MageObject> sourceInput, Game game) {
        MageObject sourceObject = sourceInput.getSource().getSourceObject(game);
        if (sourceObject == null) {
            return false;
        }

        String name = sourceObject.getName();
        if (name == null || name.isEmpty()) {
            return false;
        }

        MageObject input = sourceInput.getObject();

        // If a player names a card, the player may name either half of a split card, but not both. 
        // A split card has the chosen name if one of its two names matches the chosen name.
        // Same for modal double faces cards
        if (input instanceof CardWithHalves) {
            return CardUtil.haveSameNames(name, ((CardWithHalves) input).getLeftHalfCard().getName()) ||
                    CardUtil.haveSameNames(name, ((CardWithHalves) input).getRightHalfCard().getName()) ||
                    CardUtil.haveSameNames(name, input.getName());
        } else if (input instanceof Spell && ((Spell) input).getSpellAbility().getSpellAbilityType() == SpellAbilityType.SPLIT_FUSED) {
            SplitCard card = (SplitCard) ((Spell) input).getCard();
            return CardUtil.haveSameNames(name, card.getLeftHalfCard().getName()) ||
                    CardUtil.haveSameNames(name, card.getRightHalfCard().getName()) ||
                    CardUtil.haveSameNames(name, card.getName());
        } else if (input instanceof Spell && ((Spell) input).isFaceDown(game)) {
            // face down spells don't have names, so it's not equal, see https://github.com/magefree/mage/issues/6569
            return false;
        } else {
            if (name.contains(" // ")) {
                String leftName = name.substring(0, name.indexOf(" // "));
                String rightName = name.substring(name.indexOf(" // ") + 4);
                return CardUtil.haveSameNames(leftName, input.getName()) ||
                        CardUtil.haveSameNames(rightName, input.getName());
            } else {
                return CardUtil.haveSameNames(name, input.getName());
            }
        }
    }

    @Override
    public String toString() {
        return "NamedLikeSource";
    }
}
