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
package mage.sets.urzaslegacy;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Plopman
 */
public class Rivalry extends CardImpl<Rivalry> {

    public Rivalry(UUID ownerId) {
        super(ownerId, 90, "Rivalry", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");
        this.expansionSetCode = "ULG";

        this.color.setRed(true);

        // At the beginning of each player's upkeep, if that player controls more lands than each other player, Rivalry deals 2 damage to him or her.
        this.addAbility(new RivalryTriggeredAbility());
        
    }

    public Rivalry(final Rivalry card) {
        super(card);
    }

    @Override
    public Rivalry copy() {
        return new Rivalry(this);
    }
}

class RivalryTriggeredAbility extends TriggeredAbilityImpl<RivalryTriggeredAbility> {

    private final static FilterPermanent filter = new FilterLandPermanent();



    public RivalryTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new DamageTargetEffect(2), false);
    }

    public RivalryTriggeredAbility(final RivalryTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RivalryTriggeredAbility copy() {
        return new RivalryTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE) {
            if (getTargets().size() == 0) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        UUID player = game.getActivePlayerId();
        int land = game.getBattlefield().countAll(filter, player, game);
        if(player != null){
            for(UUID opponent : game.getOpponents(player)){
                if(land <= game.getBattlefield().countAll(filter, opponent, game)){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "At the beginning of each player's upkeep, if that player controls more lands than each other player, Rivalry deals 2 damage to him or her.";
    }
}