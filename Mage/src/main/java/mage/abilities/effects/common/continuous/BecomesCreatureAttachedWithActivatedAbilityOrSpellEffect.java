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
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;

/**
 * @author jeffwadsworth
 */
public class BecomesCreatureAttachedWithActivatedAbilityOrSpellEffect extends ContinuousEffectImpl {

    public enum LoseType {

        NONE, ALL, ALL_BUT_COLOR, ABILITIES, ABILITIES_SUBTYPE_AND_PT
    }

    protected Token token;
    protected String type;
    protected LoseType loseType;  // what attributes are lost

    public BecomesCreatureAttachedWithActivatedAbilityOrSpellEffect(Token token, String text, Duration duration) {
        this(token, text, duration, LoseType.NONE);
    }

    public BecomesCreatureAttachedWithActivatedAbilityOrSpellEffect(Token token, String text, Duration duration, LoseType loseType) {
        super(duration, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.BecomeCreature);
        this.token = token;
        this.loseType = loseType;
        staticText = text;
    }

    public BecomesCreatureAttachedWithActivatedAbilityOrSpellEffect(final BecomesCreatureAttachedWithActivatedAbilityOrSpellEffect effect) {
        super(effect);
        this.token = effect.token.copy();
        this.type = effect.type;
        this.loseType = effect.loseType;
    }

    @Override
    public BecomesCreatureAttachedWithActivatedAbilityOrSpellEffect copy() {
        return new BecomesCreatureAttachedWithActivatedAbilityOrSpellEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        Permanent attachedPermanent = game.getPermanent(source.getSourceId());
        if (attachedPermanent != null) {
            Permanent permanentAttachedTo = game.getPermanent(attachedPermanent.getAttachedTo());
            if (permanentAttachedTo != null) {
                affectedObjectList.add(new MageObjectReference(permanentAttachedTo, game));
            }
        }
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        boolean attachedExists = false;
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null) {
            for (MageObjectReference mageObjectReference : affectedObjectList) {
                Permanent permanentAttachedTo = mageObjectReference.getPermanent(game);
                if (permanentAttachedTo != null) {
                    attachedExists = true;
                    switch (layer) {
                        case TypeChangingEffects_4:
                            if (sublayer == SubLayer.NA) {
                                for (SuperType superType : token.getSuperType()) {
                                        permanentAttachedTo.addSuperType(superType);

                                }
                                // card type
                                switch (loseType) {
                                    case ALL:
                                    case ALL_BUT_COLOR:
                                        permanentAttachedTo.getCardType().clear();
                                        break;
                                }
                                for (CardType cardType : token.getCardType()) {
                                    permanentAttachedTo.addCardType(cardType);
                                }

                                // sub type
                                switch (loseType) {
                                    case ALL:
                                    case ALL_BUT_COLOR:
                                    case ABILITIES_SUBTYPE_AND_PT:
                                        permanentAttachedTo.getSubtype(game).retainAll(SubType.getLandTypes(false));
                                        break;
                                }
                                for (SubType subType : token.getSubtype(game)) {
                                    if (!permanentAttachedTo.getSubtype(game).contains(subType)) {
                                        permanentAttachedTo.getSubtype(game).add(subType);
                                    }
                                }

                            }
                            break;
                        case ColorChangingEffects_5:
                            if (sublayer == SubLayer.NA) {
                                if (loseType == LoseType.ALL) {
                                    permanentAttachedTo.getColor(game).setBlack(false);
                                    permanentAttachedTo.getColor(game).setGreen(false);
                                    permanentAttachedTo.getColor(game).setBlue(false);
                                    permanentAttachedTo.getColor(game).setWhite(false);
                                    permanentAttachedTo.getColor(game).setRed(false);
                                }
                                if (token.getColor(game).hasColor()) {
                                    permanentAttachedTo.getColor(game).setColor(token.getColor(game));
                                }
                            }
                            break;
                        case AbilityAddingRemovingEffects_6:
                            if (sublayer == SubLayer.NA) {
                                switch (loseType) {
                                    case ALL:
                                    case ALL_BUT_COLOR:
                                    case ABILITIES:
                                    case ABILITIES_SUBTYPE_AND_PT:
                                        permanentAttachedTo.removeAllAbilities(source.getSourceId(), game);
                                        break;
                                }
                                for (Ability ability : token.getAbilities()) {
                                    permanentAttachedTo.addAbility(ability, source.getSourceId(), game);
                                }

                            }
                            break;
                        case PTChangingEffects_7:
                            if (sublayer == SubLayer.SetPT_7b) {
                                permanentAttachedTo.getPower().setValue(token.getPower().getValue());
                                permanentAttachedTo.getToughness().setValue(token.getToughness().getValue());
                                break;
                            }
                    }
                }
                if (!attachedExists) {
                    discard();
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.PTChangingEffects_7
                || layer == Layer.AbilityAddingRemovingEffects_6
                || layer == Layer.ColorChangingEffects_5
                || layer == Layer.TypeChangingEffects_4;
    }

}
