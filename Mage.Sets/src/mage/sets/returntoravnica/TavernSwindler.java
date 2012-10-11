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
package mage.sets.returntoravnica;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class TavernSwindler extends CardImpl<TavernSwindler> {

    public TavernSwindler(UUID ownerId) {
        super(ownerId, 79, "Tavern Swindler", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.expansionSetCode = "RTR";
        this.subtype.add("Human");
        this.subtype.add("Rogue");

        this.color.setBlack(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {T}, Pay 3 life: Flip a coin. If you win the flip, you gain 6 life.
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new TavernSwindlerEffect(),new TapSourceCost());
        ability.addCost(new PayLifeCost(3));
        this.addAbility(ability);
    }

    public TavernSwindler(final TavernSwindler card) {
        super(card);
    }

    @Override
    public TavernSwindler copy() {
        return new TavernSwindler(this);
    }
}

class TavernSwindlerEffect extends OneShotEffect<TavernSwindlerEffect> {

    public TavernSwindlerEffect() {
        super(Constants.Outcome.Damage);
        staticText = "Flip a coin. If you win the flip, you gain 6 life";
    }

    public TavernSwindlerEffect(TavernSwindlerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (controller.flipCoin(game)) {
                game.informPlayers(controller.getName() + " got " + controller.gainLife(6, game)+ " live");
            }
        }
        return false;
    }

    @Override
    public TavernSwindlerEffect copy() {
        return new TavernSwindlerEffect(this);
    }
}