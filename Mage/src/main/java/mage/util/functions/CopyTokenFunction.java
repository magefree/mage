package mage.util.functions;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.keyword.MorphAbility;
import mage.cards.Card;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.permanent.PermanentCard;
import mage.game.permanent.PermanentToken;
import mage.game.permanent.token.Token;
import mage.game.stack.Spell;

/**
 * @author nantuko
 */
public class CopyTokenFunction implements Function<Token, Card> {

    protected Token target;

    public CopyTokenFunction(Token target) {
        if (target == null) {
            throw new IllegalArgumentException("Target can't be null");
        }
        this.target = target;
    }

    @Override
    public Token apply(Card source, Game game) {
        if (target == null) {
            throw new IllegalArgumentException("Target can't be null");
        }
        // A copy contains only the attributes of the basic card or basic Token that's the base of the permanent
        // else gained abililies would be copied too.

        MageObject sourceObj = source;
        if (source instanceof PermanentToken) {
            sourceObj = ((PermanentToken) source).getToken();
            // to show the source image, the original values have to be used
            target.setOriginalExpansionSetCode(((Token) sourceObj).getOriginalExpansionSetCode());
            target.setOriginalCardNumber(((Token) sourceObj).getOriginalCardNumber());
            target.setCopySourceCard(((PermanentToken) source).getToken().getCopySourceCard());
        } else if (source instanceof PermanentCard) {
            if (((PermanentCard) source).isMorphed() || ((PermanentCard) source).isManifested()) {
                MorphAbility.setPermanentToFaceDownCreature(target, game);
                return target;
            } else {
                if (((PermanentCard) source).isTransformed() && source.getSecondCardFace() != null) {
                    sourceObj = ((PermanentCard) source).getSecondCardFace();
                } else {
                    sourceObj = ((PermanentCard) source).getCard();
                }

                target.setOriginalExpansionSetCode(source.getExpansionSetCode());
                target.setOriginalCardNumber(source.getCardNumber());
                target.setCopySourceCard((Card) sourceObj);
            }
        } else {
            target.setOriginalExpansionSetCode(source.getExpansionSetCode());
            target.setOriginalCardNumber(source.getCardNumber());
            target.setCopySourceCard(source);
        }

        // modify all attributes permanently (without game usage)
        target.setName(sourceObj.getName());
        target.getColor().setColor(sourceObj.getColor());
        target.getManaCost().clear();
        target.getManaCost().add(sourceObj.getManaCost().copy());
        target.removeAllCardTypes();
        for (CardType type : sourceObj.getCardType()) {
            target.addCardType(type);
        }
        target.getSubtype().copyFrom(sourceObj.getSubtype());
        target.getSuperType().clear();
        for (SuperType type : sourceObj.getSuperType()) {
            target.addSuperType(type);
        }

        target.getAbilities().clear();

        for (Ability ability0 : sourceObj.getAbilities()) {
            Ability ability = ability0.copy();

            // The token is independant from the copy from object so it need a new original Id,
            // otherwise there are problems to check for created continuous effects to check if
            // the source (the Token) has still this ability
            ability.newOriginalId();

            target.addAbility(ability);
        }

        target.getPower().modifyBaseValue(sourceObj.getPower().getBaseValueModified());
        target.getToughness().modifyBaseValue(sourceObj.getToughness().getBaseValueModified());
        target.setStartingLoyalty(sourceObj.getStartingLoyalty());

        return target;
    }

    public Token from(Card source, Game game) {
        return from(source, game, null);
    }

    public Token from(Card source, Game game, Spell spell) {
        apply(source, game);

        // token's ZCC must be synced with original card to keep abilities settings
        // Example: kicker ability and kicked status
        if (spell != null) {
            // copied spell puts to battlefield as token, so that token's ZCC must be synced with spell instead card (card can be moved before resolve)
            target.setZoneChangeCounter(spell.getZoneChangeCounter(game), game);
        } else {
            target.setZoneChangeCounter(source.getZoneChangeCounter(game), game);
        }

        return target;
    }
}
