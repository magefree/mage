package mage.abilities.effects.common.continuous;

import mage.MageObject;
import mage.MageObjectReference;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.TurnFaceUpAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.repository.TokenInfo;
import mage.cards.repository.TokenRepository;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.EmptyToken;
import mage.game.permanent.token.Token;
import mage.util.CardUtil;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * This effect lets the card be a 2/2 face-down creature, with no text, no name,
 * no subtypes, and no mana cost, if it's face down on the battlefield. And it
 * adds the a TurnFaceUpAbility ability.
 * <p>
 * Warning, if a card has multiple face down abilities then keep only one face up cost
 * Example: Mischievous Quanar
 * - a. Turn Mischievous Quanar face down - BecomesFaceDownCreatureEffect without turn up cost
 * - b. Morph - BecomesFaceDownCreatureEffect with turn up cost inside
 *
 * @author LevelX2, JayDi85
 */
public class BecomesFaceDownCreatureEffect extends ContinuousEffectImpl {

    private static final Logger logger = Logger.getLogger(BecomesFaceDownCreatureEffect.class);

    public enum FaceDownType {
        MANIFESTED,
        MANUAL,
        MORPHED,
        MEGAMORPHED,
        DISGUISED,
        CLOAKED
    }

    protected int zoneChangeCounter;
    protected Ability turnFaceUpAbility = null;
    protected MageObjectReference objectReference = null;
    protected boolean foundPermanent;
    protected FaceDownType faceDownType;

    public BecomesFaceDownCreatureEffect(Duration duration, FaceDownType faceDownType) {
        this(null, null, duration, faceDownType);
    }

    public BecomesFaceDownCreatureEffect(Costs<Cost> turnFaceUpCosts, FaceDownType faceDownType) {
        this(turnFaceUpCosts, null, faceDownType);
    }

    public BecomesFaceDownCreatureEffect(Costs<Cost> turnFaceUpCosts, MageObjectReference objectReference, FaceDownType faceDownType) {
        this(turnFaceUpCosts, objectReference, Duration.WhileOnBattlefield, faceDownType);
    }

    public BecomesFaceDownCreatureEffect(Cost cost, MageObjectReference objectReference, Duration duration, FaceDownType faceDownType) {
        this(createCosts(cost), objectReference, duration, faceDownType);
    }

    public BecomesFaceDownCreatureEffect(Costs<Cost> turnFaceUpCosts, MageObjectReference objectReference, Duration duration, FaceDownType faceDownType) {
        super(duration, Layer.CopyEffects_1, SubLayer.FaceDownEffects_1b, Outcome.BecomeCreature);
        this.objectReference = objectReference;
        this.zoneChangeCounter = Integer.MIN_VALUE;
        if (turnFaceUpCosts != null) {
            this.turnFaceUpAbility = new TurnFaceUpAbility(turnFaceUpCosts, faceDownType == FaceDownType.MEGAMORPHED);
        }
        staticText = "{this} becomes a 2/2 face-down creature, with no text, no name, no subtypes, and no mana cost";
        foundPermanent = false;
        this.faceDownType = faceDownType;
    }

    protected BecomesFaceDownCreatureEffect(final BecomesFaceDownCreatureEffect effect) {
        super(effect);
        this.zoneChangeCounter = effect.zoneChangeCounter;
        if (effect.turnFaceUpAbility != null) {
            this.turnFaceUpAbility = effect.turnFaceUpAbility.copy();
        }
        this.objectReference = effect.objectReference;
        this.foundPermanent = effect.foundPermanent;
        this.faceDownType = effect.faceDownType;
    }

    private static Costs<Cost> createCosts(Cost cost) {
        if (cost == null) {
            return null; // ignore warning, null is used specifically
        }
        Costs<Cost> costs = new CostsImpl<>();
        costs.add(cost);
        return costs;
    }

    @Override
    public BecomesFaceDownCreatureEffect copy() {
        return new BecomesFaceDownCreatureEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (faceDownType == FaceDownType.MANUAL) {
            Permanent permanent;
            if (objectReference != null) {
                permanent = objectReference.getPermanent(game);
            } else {
                permanent = game.getPermanent(source.getSourceId());
            }
            if (permanent != null) {
                permanent.setFaceDown(true, game);
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent;
        if (objectReference != null) {
            permanent = objectReference.getPermanent(game);
        } else {
            permanent = game.getPermanent(source.getSourceId());
        }

        if (permanent != null && permanent.isFaceDown(game)) {
            if (!foundPermanent) {
                foundPermanent = true;
                switch (faceDownType) {
                    case MANIFESTED:
                    case MANUAL: // sets manifested image
                        permanent.setManifested(true);
                        break;
                    case MORPHED:
                    case MEGAMORPHED:
                        permanent.setMorphed(true);
                        break;
                    default:
                        throw new UnsupportedOperationException("FaceDownType not yet supported: " + faceDownType);
                }
            }
            makeFaceDownObject(game, source.getSourceId(), permanent, faceDownType, this.turnFaceUpAbility);
        } else if (duration == Duration.Custom && foundPermanent) {
            discard();
        }
        return true;
    }

    public static FaceDownType findFaceDownType(Game game, Permanent permanent) {
        if (permanent.isMorphed()) {
            return BecomesFaceDownCreatureEffect.FaceDownType.MORPHED;
        } else if (permanent.isManifested()) {
            return BecomesFaceDownCreatureEffect.FaceDownType.MANIFESTED;
        } else if (permanent.isFaceDown(game)) {
            return BecomesFaceDownCreatureEffect.FaceDownType.MANUAL;
        } else {
            return null;
        }
    }

    /**
     * Convert any object (card, token) to face down (remove/hide all face up information and make it a 2/2 creature)
     */
    public static void makeFaceDownObject(Game game, UUID sourceId, MageObject object, FaceDownType faceDownType, Ability turnFaceUpAbility) {
        String originalObjectInfo = object.toString();

        // warning, it's a direct changes to the object (without game state, so no game param here)
        object.setName(EmptyNames.FACE_DOWN_CREATURE.toString());
        object.removeAllSuperTypes();
        object.getSubtype().clear();
        object.removeAllCardTypes();
        object.addCardType(CardType.CREATURE);
        object.getColor().setColor(ObjectColor.COLORLESS);

        // remove wrong abilities
        Card card = game.getCard(object.getId());
        List<Ability> abilitiesToRemove = new ArrayList<>();
        for (Ability ability : object.getAbilities()) {

            // keep gained abilities from other sources, removes only own (card text)
            if (card != null && !card.getAbilities().contains(ability)) {
                continue;
            }

            // 701.33c
            // If a card with morph is manifested, its controller may turn that card face up using
            // either the procedure described in rule 702.36e to turn a face-down permanent with morph face up
            // or the procedure described above to turn a manifested permanent face up.
            //
            // so keep all tune face up abilities and other face down compatible
            if (ability.getWorksFaceDown()) {
                // only face up abilities hidden by default (see below), so no needs in setRuleVisible
                //ability.setRuleVisible(true);
                continue;
            }

            if (!ability.getRuleVisible() && !ability.getEffects().isEmpty()) {
                if (ability.getEffects().get(0) instanceof BecomesFaceDownCreatureEffect) {
                    continue;
                }
            }
            abilitiesToRemove.add(ability);
        }

        // add face up abilities
        // TODO: add here all possible face up like morph/disguis, manifest/cloak?
        if (object instanceof Permanent) {
            // as permanent
            Permanent permanentObject = (Permanent) object;
            permanentObject.removeAbilities(abilitiesToRemove, sourceId, game);
            if (turnFaceUpAbility != null) {
                Ability faceUp = turnFaceUpAbility.copy();
                faceUp.setRuleVisible(true);
                permanentObject.addAbility(faceUp, sourceId, game);
            }
        } else if (object instanceof CardImpl) {
            // as card
            CardImpl cardObject = (CardImpl) object;
            cardObject.getAbilities().removeAll(abilitiesToRemove);
            if (turnFaceUpAbility != null) {
                Ability faceUp = turnFaceUpAbility.copy();
                faceUp.setRuleVisible(true);
                cardObject.addAbility(faceUp);
            }
        }

        object.getPower().setModifiedBaseValue(2);
        object.getToughness().setModifiedBaseValue(2);

        // image
        String tokenName;
        switch (faceDownType) {
            case MORPHED:
                tokenName = TokenRepository.XMAGE_IMAGE_NAME_FACE_DOWN_MORPH;
                break;
            case MEGAMORPHED:
                tokenName = TokenRepository.XMAGE_IMAGE_NAME_FACE_DOWN_MEGAMORPH;
                break;
            case MANIFESTED:
                tokenName = TokenRepository.XMAGE_IMAGE_NAME_FACE_DOWN_MANIFEST;
                break;
            case CLOAKED:
                tokenName = "TODO-CLOAKED";
                break;
            case DISGUISED:
                tokenName = "TODO-DISGUISED";
                break;
            case MANUAL:
                tokenName = TokenRepository.XMAGE_IMAGE_NAME_FACE_DOWN_MANUAL;
                break;
            default:
                throw new IllegalArgumentException("Un-supported face down type for image: " + faceDownType);
        }

        Token faceDownToken = new EmptyToken();
        TokenInfo faceDownInfo = TokenRepository.instance.findPreferredTokenInfoForXmage(tokenName, object.getId());
        if (faceDownInfo != null) {
            faceDownToken.setExpansionSetCode(faceDownInfo.getSetCode());
            faceDownToken.setCardNumber("0");
            faceDownToken.setImageFileName(faceDownInfo.getName());
            faceDownToken.setImageNumber(faceDownInfo.getImageNumber());
        } else {
            logger.error("Can't find face down image for " + tokenName + ": " + originalObjectInfo);
            // TODO: add default image like backface (warning, missing image info must be visible in card popup)?
        }

        CardUtil.copySetAndCardNumber(object, faceDownToken);

        // hide rarity info
        if (object instanceof Card) {
            ((Card) object).setRarity(Rarity.SPECIAL);
        }
    }

}
