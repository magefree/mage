package mage.util.functions;

import mage.MageObject;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.effects.common.continuous.BecomesFaceDownCreatureEffect;
import mage.abilities.keyword.PrototypeAbility;
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

        // apply transformed status on ETB
        target.setEntersTransformed(source instanceof Permanent && ((Permanent) source).isTransformed());

        // 707.8a
        // If an effect creates a token that is a copy of a transforming permanent or a transforming double-faced
        // card not on the battlefield, the resulting token is a transforming token that has both a front face
        // and a back face. The characteristics of each face are determined by the copiable values of the same
        // face of the permanent it is a copy of, as modified by any other copy effects that apply to that permanent.
        // If the token is a copy of a transforming permanent with its back face up, the token enters the battlefield
        // with its back face up. This rule does not apply to tokens that are created with their own set of
        // characteristics and enter the battlefield as a copy of a transforming permanent due to a replacement effect.

        // from token permanent
        if (source instanceof PermanentToken) {
            // create token from another token
            Token sourceObj = ((PermanentToken) source).getToken();
            target.setCopySourceCard(sourceObj.getCopySourceCard()); // must link with original card
            // main side
            copyToToken(target, sourceObj, game);
            CardUtil.copySetAndCardNumber(target, source);
            // second side
            if (sourceObj.getBackFace() != null) {
                copyToToken(target.getBackFace(), sourceObj.getBackFace(), game);
                CardUtil.copySetAndCardNumber(target.getBackFace(), sourceObj.getBackFace());
            }
            return;
        }

        // from non-token permanent
        if (source instanceof PermanentCard) {
            // create token from non-token permanent

            // apply face down status
            PermanentCard sourcePermanent = (PermanentCard) source;
            BecomesFaceDownCreatureEffect.FaceDownType faceDownType = BecomesFaceDownCreatureEffect.findFaceDownType(game, sourcePermanent);
            if (faceDownType != null) {
                BecomesFaceDownCreatureEffect.makeFaceDownObject(game, null, target, faceDownType, null);
                return;
            }

            Card sourceObj = source.getMainCard();
            target.setCopySourceCard(sourceObj);
            // main side
            copyToToken(target, sourceObj, game);
            CardUtil.copySetAndCardNumber(target, sourceObj);
            // second side
            if (sourceObj.isTransformable()) {
                copyToToken(target.getBackFace(), sourceObj.getSecondCardFace(), game);
                CardUtil.copySetAndCardNumber(target.getBackFace(), sourceObj.getSecondCardFace());
            }

            // apply prototyped status
            if (((PermanentCard) source).isPrototyped()){
                Abilities<Ability> abilities = source.getAbilities();
                for (Ability ability : abilities){
                    if (ability instanceof PrototypeAbility) {
                        ((PrototypeAbility) ability).prototypePermanent(target, game);
                    }
                }
            }
            return;
        }

        // from another card (example: Embalm ability)
        Card sourceObj = CardUtil.getDefaultCardSideForBattlefield(game, source.getMainCard());
        target.setCopySourceCard(source);
        // main side
        copyToToken(target, sourceObj, game);
        CardUtil.copySetAndCardNumber(target, sourceObj);
        // second side
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
            //Don't re-add subabilities since they've already in sourceObj's abilities list
            target.addAbility(ability, true);
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
