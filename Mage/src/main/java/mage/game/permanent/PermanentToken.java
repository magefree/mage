/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
package mage.game.permanent;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCost;
import mage.game.Game;
import mage.game.permanent.token.Token;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class PermanentToken extends PermanentImpl {

    protected Token token;

    public PermanentToken(Token token, UUID controllerId, String expansionSetCode, Game game) {
        super(controllerId, controllerId, token.getName());
        this.expansionSetCode = expansionSetCode;
        this.token = token.copy();
        this.token.getAbilities().newId(); // neccessary if token has ability like DevourAbility()
        this.token.getAbilities().setSourceId(objectId);
        this.power.modifyBaseValue(token.getPower().getBaseValueModified());
        this.toughness.modifyBaseValue(token.getToughness().getBaseValueModified());
        this.copyFromToken(this.token, game, false); // needed to have at this time (e.g. for subtypes for entersTheBattlefield replacement effects)
    }

    public PermanentToken(final PermanentToken permanent) {
        super(permanent);
        this.token = permanent.token.copy();
        this.expansionSetCode = permanent.expansionSetCode;
    }

    @Override
    public void reset(Game game) {
        copyFromToken(token, game, true);
        super.reset(game);
        // Because the P/T objects have there own base value for reset we have to take it from there instead of from the basic token object
        this.power.resetToBaseValue();
        this.toughness.resetToBaseValue();
    }

    private void copyFromToken(Token token, Game game, boolean reset) {
        this.name = token.getName();
        this.abilities.clear();
        if (reset) {
            this.abilities.addAll(token.getAbilities());
        } else {
            // first time -> create ContinuousEffects only once
            for (Ability ability : token.getAbilities()) {
                this.addAbility(ability, game);
            }
        }
        this.abilities.setControllerId(this.controllerId);
        this.manaCost.clear();
        for (ManaCost cost : token.getManaCost()) {
            this.getManaCost().add(cost.copy());
        }
        this.cardType = token.getCardType();
        this.color = token.getColor(game).copy();
        this.frameColor = token.getFrameColor(game);
        this.frameStyle = token.getFrameStyle();
        this.supertype = token.getSupertype();
        this.subtype = token.getSubtype(game);
        this.tokenDescriptor = token.getTokenDescriptor();
    }

    public Token getToken() {
        return token;
    }

    @Override
    public PermanentToken copy() {
        return new PermanentToken(this);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (getToken().getCopySourceCard() != null) {
            getToken().getCopySourceCard().adjustTargets(ability, game);
        } else {
            super.adjustTargets(ability, game);
        }
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        if (getToken().getCopySourceCard() != null) {
            getToken().getCopySourceCard().adjustCosts(ability, game);
        } else {
            super.adjustCosts(ability, game);
        }
    }

}
