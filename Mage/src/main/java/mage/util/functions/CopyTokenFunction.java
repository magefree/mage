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
import mage.util.CardUtil;

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

        // from another token
        if (source instanceof PermanentToken) {
            // create token from another token
            Token sourceToken = ((PermanentToken) source).getToken();
            sourceObj = sourceToken;
            target.setCopySourceCard(((PermanentToken) source).getToken().getCopySourceCard());

            copyToToken(target, sourceObj, game);
            CardUtil.copySetAndCardNumber(target, source);
            if (sourceToken.getBackFace() != null) {
                copyToToken(target.getBackFace(), sourceToken.getBackFace(), game);
                CardUtil.copySetAndCardNumber(target.getBackFace(), sourceToken.getBackFace());
            }
            return;
        }

        // from a permanent
        if (source instanceof PermanentCard) {
            // create token from non-token permanent

            // morph/manifest must hide all info
            if (((PermanentCard) source).isMorphed()
                    || ((PermanentCard) source).isManifested()
                    || source.isFaceDown(game)) {
                MorphAbility.setPermanentToFaceDownCreature(target, (PermanentCard) source, game);
                return;
            }

            sourceObj = source.getMainCard();
            target.setCopySourceCard((Card) sourceObj);

            copyToToken(target, sourceObj, game);
            CardUtil.copySetAndCardNumber(target, sourceObj);
            if (((Card) sourceObj).isTransformable()) {
                copyToToken(target.getBackFace(), ((Card) sourceObj).getSecondCardFace(), game);
                CardUtil.copySetAndCardNumber(target.getBackFace(), ((Card) sourceObj).getSecondCardFace());
            }
            return;
        }

        // from another object like card (example: Embalm ability)
        sourceObj = source;
        target.setCopySourceCard(source);

        copyToToken(target, sourceObj, game);
        CardUtil.copySetAndCardNumber(target, sourceObj);
        if (source.isTransformable()) {
            if (target.getBackFace() == null) {
                // if you catch this then a weird use case here: card with single face copy another token with double face??
                // must create back face??
                throw new IllegalStateException("Wrong code usage: back face must be non null: " + target.getName() + " - " + target.getClass().getSimpleName());
            }
            copyToToken(target.getBackFace(), source.getSecondCardFace(), game);
            CardUtil.copySetAndCardNumber(target.getBackFace(), source.getSecondCardFace());
        }
    }

    private static void copyToToken(Token target, MageObject sourceObj, Game game) {
        // modify all attributes permanently (without game usage)
        // ignore images settings here, it will be setup later due needs in face down
        // (after copy or after put to battlefield)
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
