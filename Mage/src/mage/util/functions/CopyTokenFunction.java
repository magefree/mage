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
package mage.util.functions;

import mage.Constants;
import mage.MageObject;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.game.permanent.PermanentCard;
import mage.game.permanent.PermanentToken;
import mage.game.permanent.token.Token;

/**
 * @author nantuko
 */
public class CopyTokenFunction implements Function<Token, Card> {

    protected Token target;

    public CopyTokenFunction(Token target) {
        if (target == null) {
            throw new IllegalArgumentException("Target can't be null");
        }
        this.target = target;
    }

    @Override
    public Token apply(Card source) {
        if (target == null) {
            throw new IllegalArgumentException("Target can't be null");
        }
        // A copy contains only the attributes of the basic card or basic Token that's the base of the permanent
        // else gained abililies would be copied too.
        MageObject sourceObj = source;
        if (source instanceof PermanentToken) {
            sourceObj = ((PermanentToken) source).getToken();
        } else if (source instanceof PermanentCard) {
            sourceObj = ((PermanentCard) source).getCard();
        }

        target.setName(sourceObj.getName());
        target.getColor().setColor(sourceObj.getColor());
        target.getManaCost().clear();
        target.getManaCost().add(sourceObj.getManaCost());
        target.getCardType().clear();
        for (Constants.CardType type : sourceObj.getCardType()) {
            target.getCardType().add(type);
        }
        target.getSubtype().clear();
        for (String type : sourceObj.getSubtype()) {
            target.getSubtype().add(type);
        }
        target.getSupertype().clear();
        for (String type : sourceObj.getSupertype()) {
            target.getSupertype().add(type);
        }
        //target.setExpansionSetCode(source.getExpansionSetCode());
        target.getAbilities().clear();

        for (Ability ability0 : sourceObj.getAbilities()) {
            Ability ability = ability0.copy();
            ability.newId();
            ability.setSourceId(target.getId());
            target.addAbility(ability);
        }
        // Needed to do it this way because only the cardValue does not include the increased value from cards like "Intangible Virtue" will be copied.
        target.getPower().initValue(Integer.parseInt(sourceObj.getPower().toString()));
        target.getToughness().initValue(Integer.parseInt(sourceObj.getToughness().toString()));

        return target;
    }

    public Token from(Card source) {
        return apply(source);
    }
}
