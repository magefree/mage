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
package mage.sets.worldwake;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public class SlaveringNulls extends CardImpl<SlaveringNulls> {

    public SlaveringNulls(UUID ownerId) {
        super(ownerId, 92, "Slavering Nulls", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.expansionSetCode = "WWK";
        this.subtype.add("Goblin");
        this.subtype.add("Zombie");

        this.color.setRed(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever Slavering Nulls deals combat damage to a player, if you control a Swamp, you may have that player discard a card.
        this.addAbility(new SlaveringNullsTriggeredAbility());
    }

    public SlaveringNulls(final SlaveringNulls card) {
        super(card);
    }

    @Override
    public SlaveringNulls copy() {
        return new SlaveringNulls(this);
    }
}

class SlaveringNullsTriggeredAbility extends TriggeredAbilityImpl<SlaveringNullsTriggeredAbility> {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent();

    static {
        filter.add(new SubtypePredicate("Swamp"));
    }

    public SlaveringNullsTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new DiscardTargetEffect(1), true);
    }

    public SlaveringNullsTriggeredAbility(final SlaveringNullsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SlaveringNullsTriggeredAbility copy() {
        return new SlaveringNullsTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER && event.getSourceId().equals(this.sourceId)
                && ((DamagedPlayerEvent) event).isCombatDamage()) {
            if (game.getBattlefield().countAll(filter, controllerId, game) > 0) {
                Permanent slaveringNulls = game.getPermanent(event.getSourceId());
                Player player = game.getPlayer(event.getTargetId());
                if (slaveringNulls != null) {
                    Effect effect = this.getEffects().get(0);
                    effect.setTargetPointer(new FixedTarget(player.getId()));
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public String getRule() {
        return "Whenever {this} deals combat damage to a player, if you control a Swamp, you may have that player discard a card.";
    }

}
