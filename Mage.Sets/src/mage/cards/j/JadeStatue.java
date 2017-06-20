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
package mage.cards.j;

import mage.MageInt;
import mage.abilities.condition.common.IsPhaseCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TurnPhase;
import mage.constants.Zone;
import mage.game.permanent.token.Token;

import java.util.UUID;

/**
 *
 * @author anonymous
 */
public class JadeStatue extends CardImpl {

    public JadeStatue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");
        

        // {2}: Jade Statue becomes a 3/6 Golem artifact creature until end of combat. Activate this ability only during combat.
        this.addAbility(new ConditionalActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect (new JadeStatueToken(), "", Duration.EndOfCombat), new ManaCostsImpl("{2}"), new IsPhaseCondition(TurnPhase.COMBAT), "{2}: {this} becomes a 3/6 Golem artifact creature until end of combat. Activate this ability only during combat."));
    }

    public JadeStatue(final JadeStatue card) {
        super(card);
    }

    @Override
    public JadeStatue copy() {
        return new JadeStatue(this);
    }
    
    private static class JadeStatueToken extends Token {
        JadeStatueToken() {
            super("", "3/6 Golem artifact creature");
            cardType.add(CardType.ARTIFACT);
            cardType.add(CardType.CREATURE);
            this.subtype.add("Golem");
            power = new MageInt(3);
            toughness = new MageInt(6);
	}
    }
}
