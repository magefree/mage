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
package mage.cards.s;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.PutTopCardOfLibraryIntoGraveControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeGroupEvent;
import mage.game.permanent.token.ZombieToken;

/**
 *
 * @author LevelX2
 */
public class SidisiBroodTyrant extends CardImpl {

    public SidisiBroodTyrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}{U}");
        this.supertype.add("Legendary");
        this.subtype.add("Naga");
        this.subtype.add("Shaman");

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Sidisi, Brood Tyrant enters the battlefield or attacks, put the top three cards of your library into your graveyard.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new PutTopCardOfLibraryIntoGraveControllerEffect(3)));

        // Whenever one or more creature cards are put into your graveyard from your library, create a 2/2 black Zombie creature token.
        this.addAbility(new SidisiBroodTyrantTriggeredAbility());
    }

    public SidisiBroodTyrant(final SidisiBroodTyrant card) {
        super(card);
    }

    @Override
    public SidisiBroodTyrant copy() {
        return new SidisiBroodTyrant(this);
    }
}

class SidisiBroodTyrantTriggeredAbility extends TriggeredAbilityImpl {

    public SidisiBroodTyrantTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new ZombieToken()), false);
    }

    public SidisiBroodTyrantTriggeredAbility(final SidisiBroodTyrantTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE_GROUP;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeGroupEvent zEvent = (ZoneChangeGroupEvent) event;
        if (zEvent != null && Zone.LIBRARY == zEvent.getFromZone() && Zone.GRAVEYARD == zEvent.getToZone() && zEvent.getCards() != null) {
            for (Card card : zEvent.getCards()) {
                if (card != null) {

                    UUID cardOwnerId = card.getOwnerId();
                    List<CardType> cardType = card.getCardType();

                    if (cardOwnerId != null
                            && card.getOwnerId().equals(getControllerId())
                            && cardType != null
                            && card.getCardType().contains(CardType.CREATURE)) {
                        return true;
                    }
                }

            }
        }
        return false;
    }

    @Override
    public SidisiBroodTyrantTriggeredAbility copy() {
        return new SidisiBroodTyrantTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever one or more creature cards are put into your graveyard from your library, create a 2/2 black Zombie creature token.";
    }
}
