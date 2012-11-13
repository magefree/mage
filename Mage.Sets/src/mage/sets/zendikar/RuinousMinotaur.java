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
package mage.sets.zendikar;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author North
 */
public class RuinousMinotaur extends CardImpl<RuinousMinotaur> {

    public RuinousMinotaur(UUID ownerId) {
        super(ownerId, 145, "Ruinous Minotaur", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");
        this.expansionSetCode = "ZEN";
        this.subtype.add("Minotaur");
        this.subtype.add("Warrior");

        this.color.setRed(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(2);

        // Whenever Ruinous Minotaur deals damage to an opponent, sacrifice a land.
        this.addAbility(new RuinousMinotaurTriggeredAbility());
    }

    public RuinousMinotaur(final RuinousMinotaur card) {
        super(card);
    }

    @Override
    public RuinousMinotaur copy() {
        return new RuinousMinotaur(this);
    }
}

class RuinousMinotaurTriggeredAbility extends TriggeredAbilityImpl<RuinousMinotaurTriggeredAbility> {

    private static final FilterControlledPermanent filter = new FilterControlledLandPermanent();

    public RuinousMinotaurTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SacrificeTargetEffect(), true);
        this.addTarget(new TargetControlledPermanent(filter));
    }

    public RuinousMinotaurTriggeredAbility(final RuinousMinotaurTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RuinousMinotaurTriggeredAbility copy() {
        return new RuinousMinotaurTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER && event.getSourceId().equals(this.sourceId)
                && game.getOpponents(this.getControllerId()).contains(event.getTargetId())) {
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals damage to an opponent, sacrifice a land.";
    }
}
