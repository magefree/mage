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

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class BecomesCreatureSourceEffect extends ContinuousEffectImpl implements SourceEffect {

    protected Token token;
    protected String type;
    protected boolean losePreviousTypes;
    protected DynamicValue power = null;
    protected DynamicValue toughness = null;

    public BecomesCreatureSourceEffect(Token token, String type, Duration duration) {
        this(token, type, duration, false, false);
    }

    public BecomesCreatureSourceEffect(Token token, String type, Duration duration, boolean losePreviousTypes, boolean characterDefining) {
        this(token, type, duration, losePreviousTypes, characterDefining, null, null);
    }

    public BecomesCreatureSourceEffect(Token token, String type, Duration duration, boolean losePreviousTypes, boolean characterDefining, DynamicValue power, DynamicValue toughness) {
        super(duration, Outcome.BecomeCreature);
        this.characterDefining = characterDefining;
        this.token = token;
        this.type = type;
        this.losePreviousTypes = losePreviousTypes;
        this.power = power;
        this.toughness = toughness;
        setText();
    }

    public BecomesCreatureSourceEffect(final BecomesCreatureSourceEffect effect) {
        super(effect);
        this.token = effect.token.copy();
        this.type = effect.type;
        this.losePreviousTypes = effect.losePreviousTypes;
        if (effect.power != null) {
            this.power = effect.power.copy();
        }
        if (effect.toughness != null) {
            this.toughness = effect.toughness.copy();
        }
    }

    @Override
    public BecomesCreatureSourceEffect copy() {
        return new BecomesCreatureSourceEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (affectedObjectsSet) {
            affectedObjectList.add(new MageObjectReference(source.getSourceId(), game));
        }
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent;
        if (affectedObjectsSet) {
            permanent = affectedObjectList.get(0).getPermanent(game);
        } else {
            permanent = game.getPermanent(source.getSourceId());
        }
        if (permanent != null) {
            switch (layer) {
                case TypeChangingEffects_4:
                    if (sublayer == SubLayer.NA) {
                        if (losePreviousTypes) {
                            permanent.getCardType().clear();
                        }
                        for (CardType t : token.getCardType()) {
                            permanent.addCardType(t);
                        }
                        if (type != null && type.isEmpty() || type == null && permanent.isLand()) {
                            permanent.getSubtype(game).retainAll(SubType.getLandTypes(false));
                        }
                        if (!token.getSubtype(game).isEmpty()) {
                            permanent.getSubtype(game).addAll(token.getSubtype(game));
                        }
                        permanent.setIsAllCreatureTypes(token.isAllCreatureTypes());
                    }
                    break;
                case ColorChangingEffects_5:
                    if (sublayer == SubLayer.NA) {
                        if (token.getColor(game).hasColor()) {
                            permanent.getColor(game).setColor(token.getColor(game));
                        }
                    }
                    break;
                case AbilityAddingRemovingEffects_6:
                    if (sublayer == SubLayer.NA) {
                        for (Ability ability : token.getAbilities()) {
                            permanent.addAbility(ability, source.getSourceId(), game);
                        }

                    }
                    break;
                case PTChangingEffects_7:
                    if ((sublayer == SubLayer.CharacteristicDefining_7a && isCharacterDefining())
                            || (sublayer == SubLayer.SetPT_7b && !isCharacterDefining())) {
                        if (power != null) {
                            permanent.getPower().setValue(power.calculate(game, source, this));
                        } else if (token.getPower() != null) {
                            permanent.getPower().setValue(token.getPower().getValue());
                        }
                        if (toughness != null) {
                            permanent.getToughness().setValue(toughness.calculate(game, source, this));
                        } else if (token.getToughness() != null) {
                            permanent.getToughness().setValue(token.getToughness().getValue());
                        }
                    }
            }
            return true;
        } else if (duration == Duration.Custom) {
            this.discard();
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    private void setText() {
        if (type != null && !type.isEmpty()) {
            staticText = duration.toString() + " {this} becomes a " + token.getDescription() + " that's still a " + this.type;
        } else {
            staticText = duration.toString() + " {this} becomes a " + token.getDescription();
        }
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.PTChangingEffects_7 || layer == Layer.AbilityAddingRemovingEffects_6 || layer == Layer.ColorChangingEffects_5 || layer == Layer.TypeChangingEffects_4;
    }

}
