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
package mage.sets.shadowmoor;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class RiteOfConsumption extends CardImpl<RiteOfConsumption> {

    public RiteOfConsumption(UUID ownerId) {
        super(ownerId, 76, "Rite of Consumption", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{1}{B}");
        this.expansionSetCode = "SHM";

        this.color.setBlack(true);

        // As an additional cost to cast Rite of Consumption, sacrifice a creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1,1,new FilterControlledCreaturePermanent("a creature"), false)));
        // Rite of Consumption deals damage equal to the sacrificed creature's power to target player. You gain life equal to the damage dealt this way.
        this.getSpellAbility().addEffect(new RiteOfConsumptionEffect());
        this.getSpellAbility().addTarget(new TargetPlayer(true));
    }

    public RiteOfConsumption(final RiteOfConsumption card) {
        super(card);
    }

    @Override
    public RiteOfConsumption copy() {
        return new RiteOfConsumption(this);
    }
}

class RiteOfConsumptionEffect extends OneShotEffect<RiteOfConsumptionEffect> {

    public RiteOfConsumptionEffect() {
        super(Outcome.Benefit);
        this.staticText = "{this} deals damage equal to the sacrificed creature's power to target player. You gain life equal to the damage dealt this way";
    }

    public RiteOfConsumptionEffect(final RiteOfConsumptionEffect effect) {
        super(effect);
    }

    @Override
    public RiteOfConsumptionEffect copy() {
        return new RiteOfConsumptionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (targetPlayer != null && controller != null) {
            Permanent sacrificedCreature = null;
            for (Cost cost :source.getCosts()) {
                if (cost instanceof SacrificeTargetCost) {
                    SacrificeTargetCost sacCost = (SacrificeTargetCost) cost;
                    for(Permanent permanent : sacCost.getPermanents()) {
                        sacrificedCreature = permanent;
                        break;
                    }
                }
            }
            if (sacrificedCreature != null) {
                int damage = sacrificedCreature.getPower().getValue();
                if (damage > 0) {
                    int damageDealt = targetPlayer.damage(damage, source.getSourceId(), game, false, true);
                    if (damageDealt > 0) {
                        controller.gainLife(damage, game);
                    }

                }
                return true;
            }
        }
        return false;
    }
}
