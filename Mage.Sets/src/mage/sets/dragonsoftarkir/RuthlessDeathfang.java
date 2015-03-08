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
package mage.sets.dragonsoftarkir;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author fireshoes
 */
public class RuthlessDeathfang extends CardImpl {

    public RuthlessDeathfang(UUID ownerId) {
        super(ownerId, 229, "Ruthless Deathfang", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{4}{U}{B}");
        this.expansionSetCode = "DTK";
        this.subtype.add("Dragon");
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you sacrifice a creature, target opponent sacrifices a creature.
        Ability ability = new RuthlessDeathfangTriggeredAbility();
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    public RuthlessDeathfang(final RuthlessDeathfang card) {
        super(card);
    }

    @Override
    public RuthlessDeathfang copy() {
        return new RuthlessDeathfang(this);
    }
}

class RuthlessDeathfangTriggeredAbility extends TriggeredAbilityImpl {

    public RuthlessDeathfangTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SacrificeEffect(new FilterCreaturePermanent("creature"), 1, "target opponent"), false);
    }

    public RuthlessDeathfangTriggeredAbility(final RuthlessDeathfangTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RuthlessDeathfangTriggeredAbility copy() {
        return new RuthlessDeathfangTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SACRIFICED_PERMANENT 
                && event.getPlayerId().equals(this.getControllerId())
                && game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD).getCardType().contains(CardType.CREATURE)) {
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you sacrifice a creature, " + super.getRule();
    }
}