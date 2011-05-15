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
package mage.sets.scarsofmirrodin;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author North
 */
public class EzurisArchers extends CardImpl<EzurisArchers> {

    public EzurisArchers(UUID ownerId) {
        super(ownerId, 120, "Ezuri's Archers", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{G}");
        this.expansionSetCode = "SOM";
        this.subtype.add("Elf");
        this.subtype.add("Archer");

        this.color.setGreen(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        this.addAbility(ReachAbility.getInstance());
        // Whenever Ezuri's Archers blocks a creature with flying, Ezuri's Archers gets +3/+0 until end of turn.
        this.addAbility(new BlocksCreatureWithFlyingTriggeredAbility(new BoostSourceEffect(3, 0, Duration.EndOfTurn), false));
    }

    public EzurisArchers(final EzurisArchers card) {
        super(card);
    }

    @Override
    public EzurisArchers copy() {
        return new EzurisArchers(this);
    }
}

class BlocksCreatureWithFlyingTriggeredAbility extends TriggeredAbilityImpl<BlocksCreatureWithFlyingTriggeredAbility> {

    public BlocksCreatureWithFlyingTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
    }

    public BlocksCreatureWithFlyingTriggeredAbility(final BlocksCreatureWithFlyingTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == EventType.BLOCKER_DECLARED && event.getSourceId().equals(this.getSourceId())
                && game.getPermanent(event.getTargetId()).getAbilities().containsKey(FlyingAbility.getInstance().getId())) {
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} blocks a creature with flying, " + super.getRule();
    }

    @Override
    public BlocksCreatureWithFlyingTriggeredAbility copy() {
        return new BlocksCreatureWithFlyingTriggeredAbility(this);
    }
}