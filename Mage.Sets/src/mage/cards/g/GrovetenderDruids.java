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
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AllyEntersBattlefieldTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.GrovetenderDruidsPlantToken;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public class GrovetenderDruids extends CardImpl {

    public GrovetenderDruids(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // <i>Rally</i>-Whenever Grovetender Druids or another Ally enters the battlefield under your control, you may pay {1}.
        // If you do, create a 1/1 green Plant creature token.
        this.addAbility(new AllyEntersBattlefieldTriggeredAbility(new GrovetenderDruidsEffect(), false));
    }

    public GrovetenderDruids(final GrovetenderDruids card) {
        super(card);
    }

    @Override
    public GrovetenderDruids copy() {
        return new GrovetenderDruids(this);
    }
}

class GrovetenderDruidsEffect extends OneShotEffect {

    GrovetenderDruidsEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may pay {1}. If you do, create a 1/1 green Plant creature token";
    }

    GrovetenderDruidsEffect(final GrovetenderDruidsEffect effect) {
        super(effect);
    }

    @Override
    public GrovetenderDruidsEffect copy() {
        return new GrovetenderDruidsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            if (player.chooseUse(Outcome.BoostCreature, "Do you want to to pay {1}?", source, game)) {
                Cost cost = new ManaCostsImpl("{1}");
                if (cost.pay(source, game, source.getSourceId(), source.getControllerId(), false, null)) {
                    new CreateTokenEffect(new GrovetenderDruidsPlantToken()).apply(game, source);
                }
                return true;
            }
        }
        return false;
    }
}
