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
package mage.sets.conspiracy;

import java.util.UUID;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.game.permanent.token.WolfToken;

/**
 *
 * @author fireshoes
 */
public class PredatorsHowl extends CardImpl {

    public PredatorsHowl(UUID ownerId) {
        super(ownerId, 37, "Predator's Howl", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{3}{G}");
        this.expansionSetCode = "CNS";

        // Put a 2/2 green Wolf creature token onto the battlefield.
        // Morbid - Put three 2/2 green Wolf creature tokens onto the battlefield instead if a creature died this turn.
        Effect effect = new ConditionalOneShotEffect(
                new CreateTokenEffect(new WolfToken(), 3),
                new CreateTokenEffect(new WolfToken(), 1),
                new MorbidCondition(),
                "Put a 2/2 green Wolf creature token onto the battlefield. <br/><br/><i>Morbid</i> - Put three 2/2 green Wolf creature tokens onto the battlefield instead if a creature died this turn.");
        this.getSpellAbility().addEffect(effect);
    }

    public PredatorsHowl(final PredatorsHowl card) {
        super(card);
    }

    @Override
    public PredatorsHowl copy() {
        return new PredatorsHowl(this);
    }
}
