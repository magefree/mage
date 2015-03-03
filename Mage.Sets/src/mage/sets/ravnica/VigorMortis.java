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
package mage.sets.ravnica;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author emerald000
 */
public class VigorMortis extends CardImpl {

    public VigorMortis(UUID ownerId) {
        super(ownerId, 111, "Vigor Mortis", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{2}{B}{B}");
        this.expansionSetCode = "RAV";

        // Return target creature card from your graveyard to the battlefield. If {G} was spent to cast Vigor Mortis, that creature enters the battlefield with an additional +1/+1 counter on it.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(new FilterCreatureCard()));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new VigorMortisAddCounterEffect(), new ManaWasSpentCondition(ColoredManaSymbol.G)));
    }

    public VigorMortis(final VigorMortis card) {
        super(card);
    }

    @Override
    public VigorMortis copy() {
        return new VigorMortis(this);
    }
}

class VigorMortisAddCounterEffect extends OneShotEffect {
    
    VigorMortisAddCounterEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "that creature enters the battlefield with an additional +1/+1 counter on it";
    }
    
    VigorMortisAddCounterEffect(final VigorMortisAddCounterEffect effect) {
        super(effect);
    }
    
    @Override
    public VigorMortisAddCounterEffect copy() {
        return new VigorMortisAddCounterEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        // targetPointer can't be used because target moved from graveyard to battlefield
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
                permanent.addCounters(CounterType.P1P1.createInstance(), game);
        }
        return false;
    }
}
