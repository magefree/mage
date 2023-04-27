package mage.abilities.effects.common.continuous;

import mage.MageObjectReference;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.TurnFaceUpAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.Card;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.ArrayList;
import java.util.List;

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
                    permanent.removeAllCardTypes(game);
                    permanent.addCardType(game, CardType.CREATURE);
                    permanent.removeAllSubTypes(game);
                    break;
                case ColorChangingEffects_5:
                    permanent.getColor(game).setColor(new ObjectColor());
                    break;
                case AbilityAddingRemovingEffects_6:
                    Card card = game.getCard(permanent.getId()); //
                    List<Ability> abilitiesToRemove = new ArrayList<>();
                    for (Ability ability : permanent.getAbilities()) {

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
                            ability.setRuleVisible(false);
                            continue;
                        }

                        if (!ability.getRuleVisible() && !ability.getEffects().isEmpty()) {
                            if (ability.getEffects().get(0) instanceof BecomesFaceDownCreatureEffect) {
                                continue;
                            }
                        }
                        abilitiesToRemove.add(ability);
                    }
                    permanent.removeAbilities(abilitiesToRemove, source.getSourceId(), game);
                    if (turnFaceUpAbility != null) {
                        permanent.addAbility(turnFaceUpAbility, source.getSourceId(), game);
                    }
                    break;
                case PTChangingEffects_7:
                    if (sublayer == SubLayer.SetPT_7b) {
                        permanent.getPower().setModifiedBaseValue(2);
                        permanent.getToughness().setModifiedBaseValue(2);
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
