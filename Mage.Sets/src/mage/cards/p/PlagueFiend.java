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
package mage.sets.prophecy;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToACreatureTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public class PlagueFiend extends CardImpl {

    public PlagueFiend(UUID ownerId) {
        super(ownerId, 73, "Plague Fiend", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.expansionSetCode = "PCY";
        this.subtype.add("Insect");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Plague Fiend deals combat damage to a creature, destroy that creature unless its controller pays {2}.
        this.addAbility(new DealsCombatDamageToACreatureTriggeredAbility(new PlagueFiendEffect(new GenericManaCost(2)), false, true));
    }

    public PlagueFiend(final PlagueFiend card) {
        super(card);
    }

    @Override
    public PlagueFiend copy() {
        return new PlagueFiend(this);
    }
}

class PlagueFiendEffect extends OneShotEffect {

    protected Cost cost;

    public PlagueFiendEffect(Cost cost) {
        super(Outcome.Detriment);
        this.staticText = "destroy that creature unless its controller pays {2}";
        this.cost = cost;
    }

    public PlagueFiendEffect(final PlagueFiendEffect effect) {
        super(effect);
        this.cost = effect.cost.copy();
    }

    @Override
    public PlagueFiendEffect copy() {
        return new PlagueFiendEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (targetCreature != null) {
                Player player = game.getPlayer(targetCreature.getControllerId());
                if (player != null) {
                    cost.clearPaid();
                    final StringBuilder sb = new StringBuilder("Pay {2}? (Otherwise ").append(targetCreature.getName()).append(" will be destroyed)");
                    if (player.chooseUse(Outcome.Benefit, sb.toString(), source, game)) {
                        cost.pay(source, game, targetCreature.getControllerId(), targetCreature.getControllerId(), false, null);
                    }
                    if (!cost.isPaid()) {
                        targetCreature.destroy(source.getSourceId(), game, false);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
