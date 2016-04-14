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
package mage.sets.magic2012;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.StackAbility;
import mage.game.stack.StackObject;

/**
 *
 * @author anonymous
 */
public class ChandrasPhoenix extends CardImpl {

    public ChandrasPhoenix(UUID ownerId) {
        super(ownerId, 126, "Chandra's Phoenix", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");
        this.expansionSetCode = "M12";
        this.subtype.add("Phoenix");

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Haste (This creature can attack and as soon as it comes under your control.)
        this.addAbility(HasteAbility.getInstance());
        // Whenever an opponent is dealt damage by a red instant or sorcery spell you control or by a red planeswalker you control, return Chandra's Phoenix from your graveyard to your hand.
        this.addAbility(new ChandrasPhoenixTriggeredAbility());
    }

    public ChandrasPhoenix(final ChandrasPhoenix card) {
        super(card);
    }

    @Override
    public ChandrasPhoenix copy() {
        return new ChandrasPhoenix(this);
    }
}

class ChandrasPhoenixTriggeredAbility extends TriggeredAbilityImpl {

    ChandrasPhoenixTriggeredAbility() {
        super(Zone.GRAVEYARD, new ReturnToHandSourceEffect());
    }

    ChandrasPhoenixTriggeredAbility(final ChandrasPhoenixTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ChandrasPhoenixTriggeredAbility copy() {
        return new ChandrasPhoenixTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getOpponents(this.controllerId).contains(event.getPlayerId())) {
            StackObject stackObject = game.getStack().getStackObject(event.getSourceId());
            if (stackObject != null) {
                MageObject sourceObjectDamage;
                if (stackObject instanceof StackAbility) {
                    sourceObjectDamage = ((StackAbility) stackObject).getSourceObject(game);
                } else {
                    sourceObjectDamage = stackObject;
                }
                if (sourceObjectDamage != null) {
                    if (sourceObjectDamage.getColor(game).isRed()
                            && (sourceObjectDamage.getCardType().contains(CardType.PLANESWALKER)
                            || sourceObjectDamage.getCardType().contains(CardType.INSTANT)
                            || sourceObjectDamage.getCardType().contains(CardType.SORCERY))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent is dealt damage by a red instant or sorcery spell you control or by a red planeswalker you control, return {this} from your graveyard to your hand.";
    }
}
