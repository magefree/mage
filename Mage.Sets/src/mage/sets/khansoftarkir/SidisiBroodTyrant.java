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
package mage.sets.khansoftarkir;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.ZoneChangeTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.PutTopCardOfLibraryIntoGraveControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.token.ZombieToken;
import mage.game.stack.StackObject;

/**
 *
 * @author LevelX2
 */
public class SidisiBroodTyrant extends CardImpl {

    public SidisiBroodTyrant(UUID ownerId) {
        super(ownerId, 199, "Sidisi, Brood Tyrant", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{1}{B}{G}{U}");
        this.expansionSetCode = "KTK";
        this.supertype.add("Legendary");
        this.subtype.add("Naga");
        this.subtype.add("Shaman");

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Sidisi, Brood Tyrant enters the battlefield or attacks, put the top three cards of your library into your graveyard.
        this.addAbility(new SidisiBroodTyrantAbility());
        // Whenever one or more creature cards are put into your graveyard from your library, put a 2/2 black Zombie creature token onto the battlefield.
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

class SidisiBroodTyrantAbility extends TriggeredAbilityImpl {

    public SidisiBroodTyrantAbility() {
        super(Zone.BATTLEFIELD, new PutTopCardOfLibraryIntoGraveControllerEffect(3), false);
    }

    public SidisiBroodTyrantAbility(final SidisiBroodTyrantAbility ability) {
        super(ability);
    }

    @Override
    public SidisiBroodTyrantAbility copy() {
        return new SidisiBroodTyrantAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED || event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == EventType.ATTACKER_DECLARED && event.getSourceId().equals(this.getSourceId())) {
            return true;
        }
        if (event.getType() == EventType.ENTERS_THE_BATTLEFIELD && event.getTargetId().equals(this.getSourceId()) ) {
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} enters the battlefield or attacks, put the top three cards of your library into your graveyard.";
    }

}

class SidisiBroodTyrantTriggeredAbility extends ZoneChangeTriggeredAbility {

    UUID lastStackObjectId = null;

    public SidisiBroodTyrantTriggeredAbility() {
        super(Zone.BATTLEFIELD, Zone.LIBRARY, Zone.GRAVEYARD, new CreateTokenEffect(new ZombieToken("KTK")), "",  false);
    }

    public SidisiBroodTyrantTriggeredAbility(final SidisiBroodTyrantTriggeredAbility ability) {
        super(ability);
        this.lastStackObjectId = ability.lastStackObjectId;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent)event;
        if ((fromZone == null || zEvent.getFromZone() == fromZone) && (toZone == null || zEvent.getToZone() == toZone)) {
            Card card = game.getCard(event.getTargetId());
            if (card != null && card.getOwnerId().equals(getControllerId()) && card.getCardType().contains(CardType.CREATURE)) {
                StackObject stackObject = game.getStack().getStackObject(event.getSourceId());
                if (stackObject == null) {
                    stackObject = (StackObject) game.getLastKnownInformation(event.getSourceId(), Zone.STACK);
                }
                // If multiple cards go to graveyard from replacement effect (e.g. Dredge) each card is wrongly handled as a new event
                if (stackObject != null) {
                    if (stackObject.getId().equals(lastStackObjectId)) {
                        return false; // was already handled
                    }
                    lastStackObjectId = stackObject.getId();
                    return true;
                } else {
                    // special action or replacement effect, so we can't check yet if multiple cards are moved with one effect
                    return true;
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
        return "Whenever one or more creature cards are put into your graveyard from your library, put a 2/2 black Zombie creature token onto the battlefield.";
    }
}
