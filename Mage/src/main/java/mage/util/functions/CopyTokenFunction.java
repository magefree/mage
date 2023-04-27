package mage.util.functions;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.keyword.MorphAbility;
import mage.cards.Card;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.game.permanent.PermanentToken;
import mage.game.permanent.token.EmptyToken;
import mage.game.permanent.token.Token;
import mage.game.stack.Spell;

/**
 * @author nantuko
 */
public class CopyTokenFunction {

    protected final Token target;

    private CopyTokenFunction(Token target) {
        if (target == null) {
            throw new IllegalArgumentException("Target can't be null");
        }
        this.target = target;
    }

    public static Token createTokenCopy(Card source, Game game) {
        return createTokenCopy(source, game, null);
    }

    public static Token createTokenCopy(Card source, Game game, Spell spell) {
        return new CopyTokenFunction(new EmptyToken(source.isTransformable())).from(source, game, spell);
    }

    public void apply(Card source, Game game) {
        if (target == null) {
            throw new IllegalArgumentException("Target can't be null");
        }
        // A copy contains only the attributes of the basic card or basic Token that's the base of the permanent
        // else gained abililies would be copied too.
        target.setEntersTransformed(source instanceof Permanent && ((Permanent) source).isTransformed());
        MageObject sourceObj;
        if (source instanceof PermanentToken) {
            // create token from another token
            Token sourceToken = ((PermanentToken) source).getToken();
            sourceObj = sourceToken;
            copyToToken(target, sourceObj, game);
            if (sourceToken.getBackFace() != null) {
                target.getBackFace().setOriginalExpansionSetCode(sourceToken.getOriginalExpansionSetCode());
                target.getBackFace().setOriginalCardNumber(sourceToken.getOriginalCardNumber());
                target.getBackFace().setCopySourceCard(sourceToken.getBackFace().getCopySourceCard());
                copyToToken(target.getBackFace(), sourceToken.getBackFace(), game);
            }
            // to show the source image, the original values have to be used
            target.setOriginalExpansionSetCode(sourceToken.getOriginalExpansionSetCode());
            target.setOriginalCardNumber(sourceToken.getOriginalCardNumber());
            target.setCopySourceCard(((PermanentToken) source).getToken().getCopySourceCard());
            return;
        }
        if (source instanceof PermanentCard) {
            // create token from non-token permanent
            if (((PermanentCard) source).isMorphed() || ((PermanentCard) source).isManifested()) {
                MorphAbility.setPermanentToFaceDownCreature(target, game);
                return;
            }
            sourceObj = ((PermanentCard) source).getMainCard();
            copyToToken(target, sourceObj, game);
            if (((Card) sourceObj).isTransformable()) {
                target.getBackFace().setOriginalExpansionSetCode(source.getExpansionSetCode());
                target.getBackFace().setOriginalCardNumber(source.getCardNumber());
                target.getBackFace().setCopySourceCard(((Card) sourceObj).getSecondCardFace());
                copyToToken(target.getBackFace(), ((Card) sourceObj).getSecondCardFace(), game);
            }

            target.setOriginalExpansionSetCode(source.getExpansionSetCode());
            target.setOriginalCardNumber(source.getCardNumber());
            target.setCopySourceCard((Card) sourceObj);
            return;
        }
        sourceObj = source;
        copyToToken(target, sourceObj, game);
        if (source.isTransformable()) {
            target.getBackFace().setOriginalExpansionSetCode(source.getExpansionSetCode());
            target.getBackFace().setOriginalCardNumber(source.getCardNumber());
            target.getBackFace().setCopySourceCard(source.getSecondCardFace());
            copyToToken(target.getBackFace(), source.getSecondCardFace(), game);
        }
        // create token from non-permanent object like card (example: Embalm ability)
        target.setOriginalExpansionSetCode(source.getExpansionSetCode());
        target.setOriginalCardNumber(source.getCardNumber());
        target.setCopySourceCard(source);
    }

    private static Token copyToToken(Token target, MageObject sourceObj, Game game) {
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

        target.setPower(sourceObj.getPower().getBaseValue());
        target.setToughness(sourceObj.getToughness().getBaseValue());
        target.setStartingLoyalty(sourceObj.getStartingLoyalty());
        target.setStartingDefense(sourceObj.getStartingDefense());

        return target;
    }

    private Token from(Card source, Game game, Spell spell) {
        apply(source, game);

        // token's ZCC must be synced with original card to keep abilities settings
        // Example: kicker ability and kicked status
        if (spell != null) {
            // copied spell puts to battlefield as token, so that token's ZCC must be synced with spell instead card (card can be moved before resolve)
            target.setZoneChangeCounter(spell.getZoneChangeCounter(game), game);
            // Copy starting loyalty from spell (Ob Nixilis, the Adversary)
            target.setStartingLoyalty(spell.getStartingLoyalty());
            target.setStartingDefense(spell.getStartingDefense());
        } else {
            target.setZoneChangeCounter(source.getZoneChangeCounter(game), game);
        }

        return target;
    }
}
