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
package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.token.AkoumStonewakerElementalToken;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class AkoumStonewaker extends CardImpl {

    public AkoumStonewaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // <i>Landfall</i> — Whenever a land enters the battlefield under your control, you may pay {2}{R}. If you do, create a 3/1 red Elemental creature token with trample and haste.
        // Exile that token at the beginning of the next end step.
        this.addAbility(new LandfallAbility(new DoIfCostPaid(new AkoumStonewakerEffect(), new ManaCostsImpl("{2}{R}")), false));

    }

    public AkoumStonewaker(final AkoumStonewaker card) {
        super(card);
    }

    @Override
    public AkoumStonewaker copy() {
        return new AkoumStonewaker(this);
    }
}

class AkoumStonewakerEffect extends OneShotEffect {

    public AkoumStonewakerEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "create a 3/1 red Elemental creature token with trample and haste. Exile that token at the beginning of the next end step";
    }

    public AkoumStonewakerEffect(final AkoumStonewakerEffect effect) {
        super(effect);
    }

    @Override
    public AkoumStonewakerEffect copy() {
        return new AkoumStonewakerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {

        CreateTokenEffect effect = new CreateTokenEffect(new AkoumStonewakerElementalToken());
        if (effect.apply(game, source)) {
            effect.exileTokensCreatedAtNextEndStep(game, source);
            return true;
        }
        return false;
    }
}
