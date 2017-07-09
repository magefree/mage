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
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.common.DrawCardControllerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.token.TheLocustGodInsectToken;

/**
 *
 * @author spjspj
 */
public class TheLocustGod extends CardImpl {

    public TheLocustGod(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{R}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("God");
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you draw a card, create a 1/1 blue and red Insect creature token with flying and haste.
        this.addAbility(new DrawCardControllerTriggeredAbility(new CreateTokenEffect(new TheLocustGodInsectToken(), 1, false, false), false));

        // {2}{U}{R}: Draw a card, then discard a card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawDiscardControllerEffect(1, 1, false), new ManaCostsImpl("{2}{U}{R}"));
        this.addAbility(ability);

        // When The Locust God dies, return it to its owner's hand at the beginning of the next end step.
        this.addAbility(new DiesTriggeredAbility(new TheLocustGodEffect()));
    }

    public TheLocustGod(final TheLocustGod card) {
        super(card);
    }

    @Override
    public TheLocustGod copy() {
        return new TheLocustGod(this);
    }
}

class TheLocustGodEffect extends OneShotEffect {

    private static final String effectText = "return it to its owner's hand at the beginning of the next end step.";

    TheLocustGodEffect() {
        super(Outcome.Benefit);
        staticText = effectText;
    }

    TheLocustGodEffect(TheLocustGodEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // Create delayed triggered ability
        Effect effect = new ReturnToHandSourceEffect(false, true);
        effect.setText(staticText);
        DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect);
        game.addDelayedTriggeredAbility(delayedAbility, source);
        return true;
    }

    @Override
    public TheLocustGodEffect copy() {
        return new TheLocustGodEffect(this);
    }
}
