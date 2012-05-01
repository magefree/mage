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
package mage.sets.avacynrestored;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.delayed.AtEndOfTurnDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.token.Token;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author North
 */
public class ThatcherRevolt extends CardImpl<ThatcherRevolt> {

    public ThatcherRevolt(UUID ownerId) {
        super(ownerId, 158, "Thatcher Revolt", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{2}{R}");
        this.expansionSetCode = "AVR";

        this.color.setRed(true);

        // Put three 1/1 red Human creature tokens with haste onto the battlefield. Sacrifice those tokens at the beginning of the next end step.
        this.getSpellAbility().addEffect(new ThatcherRevoltEffect());
    }

    public ThatcherRevolt(final ThatcherRevolt card) {
        super(card);
    }

    @Override
    public ThatcherRevolt copy() {
        return new ThatcherRevolt(this);
    }
}

class ThatcherRevoltEffect extends OneShotEffect<ThatcherRevoltEffect> {

    public ThatcherRevoltEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Put three 1/1 red Human creature tokens with haste onto the battlefield. Sacrifice those tokens at the beginning of the next end step";
    }

    public ThatcherRevoltEffect(final ThatcherRevoltEffect effect) {
        super(effect);
    }

    @Override
    public ThatcherRevoltEffect copy() {
        return new ThatcherRevoltEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (int i = 0; i < 3; i++) {
            RedHumanToken token = new RedHumanToken();
            token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());

            SacrificeTargetEffect sacrificeEffect = new SacrificeTargetEffect("sacrifice this token");
            sacrificeEffect.setTargetPointer(new FixedTarget(token.getLastAddedToken()));
            DelayedTriggeredAbility delayedAbility = new AtEndOfTurnDelayedTriggeredAbility(sacrificeEffect);
            delayedAbility.setSourceId(source.getSourceId());
            delayedAbility.setControllerId(source.getControllerId());
            game.addDelayedTriggeredAbility(delayedAbility);
        }
        return true;
    }
}

class RedHumanToken extends Token {

    public RedHumanToken() {
        super("Human", "1/1 red Human creature token with haste");
        this.cardType.add(CardType.CREATURE);
        this.subtype.add("Human");

        this.color = ObjectColor.RED;
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(HasteAbility.getInstance());
    }
}
