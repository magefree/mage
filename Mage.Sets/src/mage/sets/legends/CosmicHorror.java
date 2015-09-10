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
package mage.sets.legends;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FirstStrikeAbility;
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
 * @author jeffwadsworth
 */
public class CosmicHorror extends CardImpl {

    public CosmicHorror(UUID ownerId) {
        super(ownerId, 6, "Cosmic Horror", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{B}{B}{B}");
        this.expansionSetCode = "LEG";
        this.subtype.add("Horror");
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // At the beginning of your upkeep, destroy Cosmic Horror unless you pay {3}{B}{B}{B}. If Cosmic Horror is destroyed this way, it deals 7 damage to you.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new CosmicHorrorEffect(new ManaCostsImpl("{3}{B}{B}{B}")), TargetController.YOU, false));
    }

    public CosmicHorror(final CosmicHorror card) {
        super(card);
    }

    @Override
    public CosmicHorror copy() {
        return new CosmicHorror(this);
    }
}

class CosmicHorrorEffect extends OneShotEffect {

    protected Cost cost;

    public CosmicHorrorEffect(Cost cost) {
        super(Outcome.DestroyPermanent);
        this.cost = cost;
        staticText = "destroy {this} unless you pay {3}{B}{B}{B}.  If {this} is destroyed this way it deals 7 damage to you";
    }

    public CosmicHorrorEffect(final CosmicHorrorEffect effect) {
        super(effect);
        this.cost = effect.cost.copy();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent cosmicHorror = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && cosmicHorror != null) {
            StringBuilder sb = new StringBuilder(cost.getText()).append("?");
            if (!sb.toString().toLowerCase().startsWith("exile ") && !sb.toString().toLowerCase().startsWith("return ")) {
                sb.insert(0, "Pay ");
            }
            if (controller.chooseUse(Outcome.Benefit, sb.toString(), source, game)) {
                cost.clearPaid();
                if (cost.pay(source, game, source.getSourceId(), source.getControllerId(), false)) {
                    return true;
                }
            }
            if (cosmicHorror.destroy(source.getSourceId(), game, false)) {
                controller.damage(7, source.getSourceId(), game, false, true);
                return true;
            }
        }
        return false;
    }

    @Override
    public CosmicHorrorEffect copy() {
        return new CosmicHorrorEffect(this);
    }
}
