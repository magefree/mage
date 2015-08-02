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
package mage.sets.alarareborn;

import java.util.UUID;

import mage.constants.*;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.MonocoloredPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author jeffwadsworth
 */
public class DefilerOfSouls extends CardImpl {

    public DefilerOfSouls(UUID ownerId) {
        super(ownerId, 37, "Defiler of Souls", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{3}{B}{B}{R}");
        this.expansionSetCode = "ARB";
        this.subtype.add("Demon");


        
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // At the beginning of each player's upkeep, that player sacrifices a monocolored creature.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new DefilerOfSoulsEffect(), TargetController.ANY, false, true));
    }

    public DefilerOfSouls(final DefilerOfSouls card) {
        super(card);
    }

    @Override
    public DefilerOfSouls copy() {
        return new DefilerOfSouls(this);
    }
}

class DefilerOfSoulsEffect extends OneShotEffect {

    DefilerOfSoulsEffect() {
        super(Outcome.Sacrifice);
        staticText = "that player sacrifices a monocolored creature";
    }

    DefilerOfSoulsEffect(final DefilerOfSoulsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("monocolored creature");
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player == null) {
            return false;
        }
        filter.add(new MonocoloredPredicate());
        
        int amount;
        int realCount = game.getBattlefield().countAll(filter, player.getId(), game);
        amount = Math.min(1, realCount);
        
        Target target = new TargetControlledPermanent(amount, amount, filter, false);
        target.setNotTarget(true);

        //A spell or ability could have removed the only legal target this player
        //had, if thats the case this ability should fizzle.
        if (amount > 0 && target.canChoose(source.getSourceId(), player.getId(), game)) {
            boolean abilityApplied = false;
            while (player.canRespond() && !target.isChosen() && target.canChoose(player.getId(), game)) {
                player.choose(Outcome.Sacrifice, target, source.getSourceId(), game);
            }

            for ( int idx = 0; idx < target.getTargets().size(); idx++) {
                Permanent permanent = game.getPermanent((UUID)target.getTargets().get(idx));

                if ( permanent != null ) {
                    abilityApplied |= permanent.sacrifice(source.getSourceId(), game);
                }
            }

            return abilityApplied;
        }
        return false;
    }

    @Override
    public DefilerOfSoulsEffect copy() {
        return new DefilerOfSoulsEffect(this);
    }
}