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
package mage.abilities.effects.common.continious;

import java.util.ArrayList;
import java.util.List;
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

    protected int zoneChangeCounter;
    protected Ability turnFaceUpAbility = null;
    protected boolean useTargetPointer;


    public BecomesFaceDownCreatureEffect(Costs<Cost> morphCosts) {
        this(morphCosts, false);
    }

    public BecomesFaceDownCreatureEffect(Costs<Cost> morphCosts, boolean useTargetPointer) {
        this(morphCosts, useTargetPointer, Duration.WhileOnBattlefield);
    }

    public BecomesFaceDownCreatureEffect(Cost cost, boolean useTargetPointer, Duration duration) {
        this(createCosts(cost), useTargetPointer, duration);
    }

    public BecomesFaceDownCreatureEffect(Costs<Cost> morphCosts, boolean useTargetPointer, Duration duration) {
        super(duration, Outcome.BecomeCreature);
        this.useTargetPointer = useTargetPointer;
        this.zoneChangeCounter = Integer.MIN_VALUE;
        if (morphCosts != null) {
            this.turnFaceUpAbility = new TurnFaceUpAbility(morphCosts);
        }
        staticText = "{this} becomes a 2/2 face-down creature, with no text, no name, no subtypes, and no mana cost";
    }


    public BecomesFaceDownCreatureEffect(final BecomesFaceDownCreatureEffect effect) {
        super(effect);
        this.zoneChangeCounter = effect.zoneChangeCounter;
        if (effect.turnFaceUpAbility != null) {
            this.turnFaceUpAbility = effect.turnFaceUpAbility.copy();
        }
        this.useTargetPointer = effect.useTargetPointer;
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
        if (useTargetPointer) {
            permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        } else {
            permanent = game.getPermanent(source.getSourceId());
        }
        
        if (permanent != null && permanent.isFaceDown()) {
            switch (layer) {
                case TypeChangingEffects_4:
                    permanent.setName("");
                    permanent.getSupertype().clear();
                    permanent.getCardType().clear();
                    permanent.getCardType().add(CardType.CREATURE);
                    permanent.getSubtype().clear();
                    permanent.getManaCost().clear();
                    break;
                case ColorChangingEffects_5:
                    permanent.getColor().setColor(new ObjectColor());
                    break;
                case AbilityAddingRemovingEffects_6:
                    Card card = game.getCard(permanent.getId()); //
                    List<Ability> abilities = new ArrayList<>();
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
                        abilities.add(ability);
                    }
                    permanent.getAbilities().removeAll(abilities);
                    if (turnFaceUpAbility != null) {
                        permanent.addAbility(turnFaceUpAbility, game);
                    }
                    break;
                case PTChangingEffects_7:
                    if (sublayer == SubLayer.SetPT_7b) {
                        permanent.getPower().setValue(2);
                        permanent.getToughness().setValue(2);
                    }
            }
        } else {
            if (duration.equals(Duration.Custom)) {
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
