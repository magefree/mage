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

import mage.Constants;
import mage.Constants.Duration;
import mage.Constants.Layer;
import mage.Constants.Outcome;
import mage.Constants.SubLayer;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;

/**
 *
 * @author jeff
 */
public class BecomesCreatureAttachedEffect extends ContinuousEffectImpl<BecomesCreatureAttachedEffect> {

    protected Token token;
    protected String type;

    public BecomesCreatureAttachedEffect(Token token, String text, Duration duration) {
        super(duration, Outcome.BecomeCreature);
        this.token = token;
        staticText = text;
    }

    public BecomesCreatureAttachedEffect(final BecomesCreatureAttachedEffect effect) {
        super(effect);
        this.token = effect.token.copy();
        this.type = effect.type;
    }

    @Override
    public BecomesCreatureAttachedEffect copy() {
        return new BecomesCreatureAttachedEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null) {
            Permanent permanent = game.getPermanent(enchantment.getAttachedTo());
            if (permanent != null) {
                switch (layer) {
                    case TypeChangingEffects_4:
                        if (sublayer == SubLayer.NA) {
                            if (token.getSupertype().size() > 0) {
                                for (String t : token.getSupertype()) {
                                    if (!permanent.getSupertype().contains(t)) {
                                        permanent.getSupertype().add(t);
                                    }
                                }
                            }
                            if (token.getCardType().size() > 0) {
                                for (Constants.CardType t : token.getCardType()) {
                                    if (!permanent.getCardType().contains(t)) {
                                        permanent.getCardType().add(t);
                                    }
                                }
                            }
                            if (token.getSubtype().size() > 0) {
                                for (String t : token.getSubtype()) {
                                    if (!permanent.getSubtype().contains(t)) {
                                        permanent.getSubtype().add(t);
                                    }
                                }
                            }
                        }
                        break;
                    case ColorChangingEffects_5:
                        if (sublayer == SubLayer.NA) {
                            if (token.getColor().hasColor()) {
                                permanent.getColor().setColor(token.getColor());
                            }
                        }
                        break;
                    case AbilityAddingRemovingEffects_6:
                        if (sublayer == SubLayer.NA) {
                            if (token.getAbilities().size() > 0) {
                                for (Ability ability: token.getAbilities()) {
                                    permanent.addAbility(ability, game);
                                }
                            }
                        }
                        break;
                    case PTChangingEffects_7:
                        if (sublayer == SubLayer.SetPT_7b) {
                            int power = token.getPower().getValue();
                            int toughness = token.getToughness().getValue();
                            if (power != 0 && toughness != 0) {
                                permanent.getPower().setValue(power);
                                permanent.getToughness().setValue(toughness);
                            }
                        }
                }
            }
            return true;
        }
        return false;
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
