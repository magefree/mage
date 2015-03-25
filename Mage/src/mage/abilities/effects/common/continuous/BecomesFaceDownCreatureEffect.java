/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
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
import static mage.constants.Layer.AbilityAddingRemovingEffects_6;
import static mage.constants.Layer.ColorChangingEffects_5;
import static mage.constants.Layer.PTChangingEffects_7;
import static mage.constants.Layer.TypeChangingEffects_4;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * This effect lets the card be a 2/2 face-down creature, with no text,
 * no name, no subtypes, and no mana cost, if it's face down on the battlefield.
 * And it adds the a TurnFaceUpAbility ability.
 * 
 * @author LevelX2
 */

public class BecomesFaceDownCreatureEffect extends ContinuousEffectImpl implements SourceEffect {

    public enum FaceDownType {
        MORPHED,
        MEGAMORPHED,
        MANIFESTED
    }

    protected int zoneChangeCounter;
    protected Ability turnFaceUpAbility = null;
    protected MageObjectReference objectReference= null;
    protected boolean foundPermanent;
    protected FaceDownType faceDownType;


    public BecomesFaceDownCreatureEffect(Costs<Cost> turnFaceUpCosts, FaceDownType faceDownType){
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
            this.turnFaceUpAbility = new TurnFaceUpAbility(turnFaceUpCosts, faceDownType.equals(FaceDownType.MEGAMORPHED));
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
                switch(faceDownType) {
                    case MANIFESTED:
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
                    permanent.getSupertype().clear();
                    permanent.getCardType().clear();
                    permanent.getCardType().add(CardType.CREATURE);
                    permanent.getSubtype().clear();
                    break;
                case ColorChangingEffects_5:
                    permanent.getColor().setColor(new ObjectColor());
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
                        } else {
                            if (!ability.getRuleVisible() && !ability.getEffects().isEmpty()) {
                                if (ability.getEffects().get(0) instanceof BecomesFaceDownCreatureEffect) {
                                    continue;
                                }
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
        } else {
            if (duration.equals(Duration.Custom) && foundPermanent == true) {
                discard();
            }
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
