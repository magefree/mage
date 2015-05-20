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
package mage.sets.judgment;

import java.util.UUID;
import mage.abilities.condition.common.CardsInControllerGraveCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TimingRule;
import mage.game.permanent.token.BearToken;

/**
 *
 * @author LevelX2
 */
public class GrizzlyFate extends CardImpl {

    public GrizzlyFate(UUID ownerId) {
        super(ownerId, 119, "Grizzly Fate", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{3}{G}{G}");
        this.expansionSetCode = "JUD";


        // Put two 2/2 green Bear creature tokens onto the battlefield.
        // Threshold - Put four 2/2 green Bear creature tokens onto the battlefield instead if seven or more cards are in your graveyard.
        Effect effect = new ConditionalOneShotEffect(new CreateTokenEffect(new BearToken(), 4),
                                                     new CreateTokenEffect(new BearToken(), 2),
                                                     new CardsInControllerGraveCondition(7),
                                                     "Put two 2/2 green Bear creature tokens onto the battlefield.<br/><br/><i>Threshold</i> - Put four 2/2 green Bear creature tokens onto the battlefield instead if seven or more cards are in your graveyard.");
        this.getSpellAbility().addEffect(effect);

        // Flashback {5}{G}{G}
        this.addAbility(new FlashbackAbility(new ManaCostsImpl("{5}{G}{G}"), TimingRule.SORCERY));
    }

    public GrizzlyFate(final GrizzlyFate card) {
        super(card);
    }

    @Override
    public GrizzlyFate copy() {
        return new GrizzlyFate(this);
    }
}
