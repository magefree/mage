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
package mage.sets.newphyrexia;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.continious.GainAbilityControlledEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public class ArmWithAEther extends CardImpl<ArmWithAEther> {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Creatures you control");

    public ArmWithAEther(UUID ownerId) {
        super(ownerId, 28, "Arm with AEther", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{2}{U}");
        this.expansionSetCode = "NPH";

        this.color.setBlue(true);

        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(new ArmWithAEtherTriggeredAbility(), Duration.EndOfTurn, filter));
    }

    public ArmWithAEther(final ArmWithAEther card) {
        super(card);
    }

    @Override
    public ArmWithAEther copy() {
        return new ArmWithAEther(this);
    }
}

class ArmWithAEtherTriggeredAbility extends TriggeredAbilityImpl<ArmWithAEtherTriggeredAbility> {

    public ArmWithAEtherTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ReturnToHandTargetEffect(), true);
    }

    public ArmWithAEtherTriggeredAbility(final ArmWithAEtherTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ArmWithAEtherTriggeredAbility copy() {
        return new ArmWithAEtherTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player opponent = game.getPlayer(event.getPlayerId());
        if (opponent != null && event.getType() == GameEvent.EventType.DAMAGED_PLAYER && event.getSourceId().equals(this.sourceId)) {
            this.getTargets().clear();
            FilterCreaturePermanent filter = new FilterCreaturePermanent("creature " + opponent.getName() + " controls");
            filter.getControllerId().add(opponent.getId());
            this.addTarget(new TargetCreaturePermanent(filter));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever this creature deals damage to an opponent, you may return target creature that player controls to its owner's hand.";
    }
}
