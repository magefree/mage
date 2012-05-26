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

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author anonymous
 */
public class ChandrasPhoenix extends CardImpl<ChandrasPhoenix> {

    public ChandrasPhoenix(UUID ownerId) {
        super(ownerId, 126, "Chandra's Phoenix", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");
        this.expansionSetCode = "M12";
        this.subtype.add("Phoenix");

        this.color.setRed(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());
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

class ChandrasPhoenixTriggeredAbility extends TriggeredAbilityImpl<ChandrasPhoenixTriggeredAbility> {
    ChandrasPhoenixTriggeredAbility() {
        super(Constants.Zone.GRAVEYARD, new ReturnToHandSourceEffect());
    }

    ChandrasPhoenixTriggeredAbility(final ChandrasPhoenixTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ChandrasPhoenixTriggeredAbility copy() {
        return new ChandrasPhoenixTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER) {
            if (game.getOpponents(this.controllerId).contains(event.getPlayerId())) {
                Card c = game.getCard(event.getSourceId());
                if (c != null) {
                    if (c.getColor().isRed() && (c.getCardType().contains(CardType.PLANESWALKER) || c.getCardType().contains(CardType.INSTANT) || c.getCardType().contains(CardType.SORCERY))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent is dealt damage by a red instant or sorcery spell you control or by a red planeswalker you control, return {this} from your graveyard to your hand";
    }
}
