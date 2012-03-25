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
package mage.abilities.keyword;

import mage.Constants;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.Card;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author nantuko
 */
public class TransformAbility extends SimpleStaticAbility {

    public static final String NO_SPELLS_TRANSFORM_RULE = "At the beginning of each upkeep, if no spells were cast last turn, transform {this}";
    public static final String TWO_OR_MORE_SPELLS_TRANSFORM_RULE = "At the beginning of each upkeep, if a player cast two or more spells last turn, transform {this}";

    public TransformAbility() {
        super(Constants.Zone.BATTLEFIELD, new TransformEffect());
    }

    public TransformAbility(final TransformAbility ability) {
        super(ability);
    }

    @Override
    public SimpleStaticAbility copy() {
        return new TransformAbility(this);
    }

    @Override
    public String getRule() {
        return "";
    }
}

class TransformEffect extends ContinuousEffectImpl<TransformEffect> {

    public TransformEffect() {
        super(Constants.Duration.WhileOnBattlefield, Constants.Layer.CopyEffects_1, Constants.SubLayer.NA, Constants.Outcome.BecomeCreature);
        staticText = "";
    }

    public TransformEffect(final TransformEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());

        if (permanent == null) {
            return false;
        }

        if (!permanent.isTransformed()) {
            // keep original card
            return true;
        }

        Card card = permanent.getSecondCardFace();

        if (card == null) {
            return false;
        }

        permanent.setName(card.getName());
        permanent.getColor().setColor(card.getColor());
        permanent.getManaCost().clear();
        permanent.getManaCost().add(card.getManaCost());
        permanent.getCardType().clear();
        for (Constants.CardType type : card.getCardType()) {
            permanent.getCardType().add(type);
        }
        permanent.getSubtype().clear();
        for (String type : card.getSubtype()) {
            permanent.getSubtype().add(type);
        }
        permanent.getSupertype().clear();
        for (String type : card.getSupertype()) {
            permanent.getSupertype().add(type);
        }
        permanent.setExpansionSetCode(card.getExpansionSetCode());
        permanent.getAbilities().clear();
        for (Ability ability : card.getAbilities()) {
            permanent.addAbility(ability, game);
        }
        permanent.getPower().setValue(card.getPower().getValue());
        permanent.getToughness().setValue(card.getToughness().getValue());

        return true;

    }

    @Override
    public TransformEffect copy() {
        return new TransformEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        return "";
    }

}
