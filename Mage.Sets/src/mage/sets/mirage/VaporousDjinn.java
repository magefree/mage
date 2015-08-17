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
package mage.sets.mirage;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public class VaporousDjinn extends CardImpl {

    public VaporousDjinn(UUID ownerId) {
        super(ownerId, 101, "Vaporous Djinn", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
        this.expansionSetCode = "MIR";
        this.subtype.add("Djinn");
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // At the beginning of your upkeep, Vaporous Djinn phases out unless you pay {U}{U}.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new VaporousDjinnEffect(), TargetController.YOU, false));
    }

    public VaporousDjinn(final VaporousDjinn card) {
        super(card);
    }

    @Override
    public VaporousDjinn copy() {
        return new VaporousDjinn(this);
    }
}

class VaporousDjinnEffect extends OneShotEffect {

    public VaporousDjinnEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} phases out unless you pay {U}{U}";
    }

    public VaporousDjinnEffect(final VaporousDjinnEffect effect) {
        super(effect);
    }

    @Override
    public VaporousDjinnEffect copy() {
        return new VaporousDjinnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cost cost = new ManaCostsImpl("{U}{U}");
            String message = "Would you like to pay {U}{U} to prevent {this} from phasing out?";
            if (!(controller.chooseUse(Outcome.Benefit, message, source, game)
                    && cost.pay(source, game, source.getSourceId(), controller.getId(), false))) {
                permanent.phaseOut(game);
            }
            return true;
        }
        return false;
    }

}
