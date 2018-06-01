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
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.NameACardEffect;
import mage.abilities.effects.common.NameACardEffect.TypeOfName;
import mage.abilities.effects.common.cost.SpellsCostReductionAllEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;

/**
 *
 * @author TheElk801
 */
public final class CheeringFanatic extends CardImpl {

    public CheeringFanatic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Cheering Fanatic attacks, choose a card name. Spells with the chosen name cost {1} less to cast this turn.
        this.addAbility(new AttacksTriggeredAbility(new CheeringFanaticEffect(), false));
    }

    public CheeringFanatic(final CheeringFanatic card) {
        super(card);
    }

    @Override
    public CheeringFanatic copy() {
        return new CheeringFanatic(this);
    }
}

class CheeringFanaticEffect extends OneShotEffect {

    CheeringFanaticEffect() {
        super(Outcome.Benefit);
        this.staticText = "choose a card name. Spells with the chosen name cost {1} less to cast this turn";
    }

    CheeringFanaticEffect(final CheeringFanaticEffect effect) {
        super(effect);
    }

    @Override
    public CheeringFanaticEffect copy() {
        return new CheeringFanaticEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        new NameACardEffect(TypeOfName.ALL).apply(game, source);
        String cardName = (String) game.getState().getValue(source.getSourceId().toString() + NameACardEffect.INFO_KEY);
        FilterCard filter = new FilterCard();
        filter.add(new NamePredicate(cardName));
        ContinuousEffect effect = new SpellsCostReductionAllEffect(filter, 1);
        effect.setDuration(Duration.EndOfTurn);
        game.addEffect(effect, source);
        return true;
    }
}
