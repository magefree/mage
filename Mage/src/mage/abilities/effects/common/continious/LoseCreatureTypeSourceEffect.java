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

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import static mage.constants.Layer.TypeChangingEffects_4;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class LoseCreatureTypeSourceEffect extends ContinuousEffectImpl<LoseCreatureTypeSourceEffect> implements SourceEffect {

    private final DynamicValue dynamicValue;
    private final int lessThan;

    /**
     * Permanent loses the creature type as long as the dynamic value is less than the value of lessThan.
     * 
     * @param dynamicValue
     * @param lessThan 
     */
    public LoseCreatureTypeSourceEffect(DynamicValue dynamicValue, int lessThan) {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        this.dynamicValue = dynamicValue;
        this.lessThan = lessThan;
        setText();
    }

    public LoseCreatureTypeSourceEffect(final LoseCreatureTypeSourceEffect effect) {
        super(effect);
        this.dynamicValue = effect.dynamicValue;
        this.lessThan = effect.lessThan;
    }

    @Override
    public LoseCreatureTypeSourceEffect copy() {
        return new LoseCreatureTypeSourceEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        if (dynamicValue.calculate(game, source) >= lessThan) {
            return false;
        }
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            switch (layer) {
                case TypeChangingEffects_4:
                    if (sublayer == SubLayer.NA) {
                        permanent.getCardType().remove(CardType.CREATURE);
                        permanent.getSubtype().clear();
                        if (permanent.isAttacking() || permanent.getBlocking() > 0) {
                            permanent.removeFromCombat(game);
                        }
                    }
                    break;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    private void setText() {
        StringBuilder sb = new StringBuilder("As long as your ");
        sb.append(dynamicValue.getMessage()).append(" is less than ");
        sb.append(CardUtil.numberToText(lessThan)).append(", {this} isn't a creature");
        staticText = sb.toString();
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.TypeChangingEffects_4;
    }

}
