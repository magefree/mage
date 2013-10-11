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

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.Card;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class BecomesCreatureSourceEffect extends ContinuousEffectImpl<BecomesCreatureSourceEffect> implements SourceEffect {

    protected Token token;
    protected String type;
    protected int zoneChangeCounter;

    public BecomesCreatureSourceEffect(Token token, String type, Duration duration) {
        super(duration, Outcome.BecomeCreature);
        this.token = token;
        this.type = type;
        setText();
    }

    public BecomesCreatureSourceEffect(final BecomesCreatureSourceEffect effect) {
        super(effect);
        this.token = effect.token.copy();
        this.type = effect.type;
        this.zoneChangeCounter = effect.zoneChangeCounter;
    }

    @Override
    public BecomesCreatureSourceEffect copy() {
        return new BecomesCreatureSourceEffect(this);
    }
    
    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        this.getAffectedObjects().add(source.getSourceId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            this.zoneChangeCounter = permanent.getZoneChangeCounter();
        }
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null && permanent.getZoneChangeCounter() == this.zoneChangeCounter) {
            switch (layer) {
                case TypeChangingEffects_4:
                    if (sublayer == SubLayer.NA) {
                        if (token.getCardType().size() > 0) {
                            for (CardType t : token.getCardType()) {
                                if (!permanent.getCardType().contains(t)) {
                                    permanent.getCardType().add(t);
                                }
                            }
                        }
                        if ("".equals(type) || type == null) {
                            permanent.getSubtype().clear();
                        }
                        if (token.getSubtype().size() > 0) {
                            permanent.getSubtype().addAll(token.getSubtype());
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
                        MageInt power = token.getPower();
                        MageInt toughness = token.getToughness();
                        if (power != null && toughness != null) {
                            permanent.getPower().setValue(power.getValue());
                            permanent.getToughness().setValue(toughness.getValue());
                        }
                    }
            }
            return true;
        } else {
            if (duration.equals(Duration.Custom)) {
                this.discard();
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    private void setText() {
        if (type.length() > 0) {
            staticText = duration.toString() + " {this} becomes a " + token.getDescription() + " that's still a " + this.type;
        }
        else {
            staticText = duration.toString() + " {this} becomes a " + token.getDescription();
        }
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.PTChangingEffects_7 || layer == Layer.AbilityAddingRemovingEffects_6 || layer == Layer.ColorChangingEffects_5 || layer == Layer.TypeChangingEffects_4;
    }

}
