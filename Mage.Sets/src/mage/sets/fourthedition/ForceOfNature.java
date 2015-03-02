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
package mage.sets.fourthedition;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public class ForceOfNature extends CardImpl {

    public ForceOfNature(UUID ownerId) {
        super(ownerId, 129, "Force of Nature", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{G}{G}{G}{G}");
        this.expansionSetCode = "4ED";
        this.subtype.add("Elemental");
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // At the beginning of your upkeep, Force of Nature deals 8 damage to you unless you pay {G}{G}{G}{G}.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new ForceOfNatureEffect(), TargetController.YOU, false));

    }

    public ForceOfNature(final ForceOfNature card) {
        super(card);
    }

    @Override
    public ForceOfNature copy() {
        return new ForceOfNature(this);
    }
}

class ForceOfNatureEffect extends OneShotEffect {

    public ForceOfNatureEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals 8 damage to you unless you pay {G}{G}{G}{G}";
    }

    public ForceOfNatureEffect(final ForceOfNatureEffect effect) {
        super(effect);
    }

    @Override
    public ForceOfNatureEffect copy() {
        return new ForceOfNatureEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cost cost = new ManaCostsImpl("{G}{G}{G}{G}");
            String message = "Would you like to pay {G}{G}{G}{G} to prevent taking 8 damage from {this}?";
            if (!(controller.chooseUse(Outcome.Benefit, message, game)
                    && cost.pay(source, game, source.getSourceId(), controller.getId(), false))) {
                controller.damage(8, source.getSourceId(), game, false, true);
            }
            return true;
        }
        return false;
    }

}
