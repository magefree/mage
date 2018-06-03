
package mage.abilities.effects.common.continuous;

import java.util.ArrayList;
import java.util.List;
import mage.MageObjectReference;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.TurnFaceUpAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.Card;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * This effect lets the card be a 2/2 face-down creature, with no text, no name,
 * no subtypes, and no mana cost, if it's face down on the battlefield. And it
 * adds the a TurnFaceUpAbility ability.
 *
 * @author LevelX2
 */
public class BecomesFaceDownCreatureEffect extends ContinuousEffectImpl implements SourceEffect {

    public enum FaceDownType {
        MANIFESTED,
        MANUAL,
        MEGAMORPHED,
        MORPHED
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
        super(duration, Outcome.BecomeCreature);
        this.objectReference = objectReference;
        this.zoneChangeCounter = Integer.MIN_VALUE;
        if (turnFaceUpCosts != null) {
            this.turnFaceUpAbility = new TurnFaceUpAbility(turnFaceUpCosts, faceDownType == FaceDownType.MEGAMORPHED);
        }
        staticText = "{this} becomes a 2/2 face-down creature, with no text, no name, no subtypes, and no mana cost";
        foundPermanent = false;
        this.faceDownType = faceDownType;
    }

    public BecomesFaceDownCreatureEffect(final BecomesFaceDownCreatureEffect effect) {
        super(effect);
        this.zoneChangeCounter = effect.zoneChangeCounter;
        if (effect.turnFaceUpAbility != null) {
            this.turnFaceUpAbility = effect.turnFaceUpAbility.copy();
        }
        this.objectReference = effect.objectReference;
        this.foundPermanent = effect.foundPermanent;
        this.faceDownType = effect.faceDownType;
    }

    @Override
    public BecomesFaceDownCreatureEffect copy() {
        return new BecomesFaceDownCreatureEffect(this);
    }

    private static Costs<Cost> createCosts(Cost cost) {
        if (cost == null) {
            return null;
        }
        Costs<Cost> costs = new CostsImpl<>();
        costs.add(cost);
        return costs;
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
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
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
                }
            }
            switch (layer) {
                case TypeChangingEffects_4:
                    permanent.setName("");
                    permanent.getSuperType().clear();
                    permanent.getCardType().clear();
                    permanent.addCardType(CardType.CREATURE);
                    permanent.getSubtype(game).clear();
                    break;
                case ColorChangingEffects_5:
                    permanent.getColor(game).setColor(new ObjectColor());
                    break;
                case AbilityAddingRemovingEffects_6:
                    Card card = game.getCard(permanent.getId()); //
                    List<Ability> abilitiesToRemove = new ArrayList<>();
                    for (Ability ability : permanent.getAbilities()) {
                        if (card != null && !card.getAbilities().contains(ability)) {
                            // gained abilities from other sources won't be removed
                            continue;
                        }
                        if (ability.getWorksFaceDown()) {
                            ability.setRuleVisible(false);
                            continue;
                        } else if (!ability.getRuleVisible() && !ability.getEffects().isEmpty()) {
                            if (ability.getEffects().get(0) instanceof BecomesFaceDownCreatureEffect) {
                                continue;
                            }
                        }
                        abilitiesToRemove.add(ability);
                    }
                    permanent.getAbilities().removeAll(abilitiesToRemove);
                    if (turnFaceUpAbility != null) {
                        permanent.addAbility(turnFaceUpAbility, source.getSourceId(), game);
                    }
                    break;
                case PTChangingEffects_7:
                    if (sublayer == SubLayer.SetPT_7b) {
                        permanent.getPower().setValue(2);
                        permanent.getToughness().setValue(2);
                    }
            }
        } else if (duration == Duration.Custom && foundPermanent == true) {
            discard();
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.PTChangingEffects_7 || layer == Layer.AbilityAddingRemovingEffects_6 || layer == Layer.ColorChangingEffects_5 || layer == Layer.TypeChangingEffects_4;
    }

}
