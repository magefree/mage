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
package mage.sets.shadowsoverinnistrad;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.PutTopCardOfLibraryIntoGraveControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeGroupEvent;
import mage.game.permanent.token.InsectToken;

/**
 *
 * @author LevelX2
 */
public class CrawlingSensation extends CardImpl {

    public CrawlingSensation(UUID ownerId) {
        super(ownerId, 199, "Crawling Sensation", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");
        this.expansionSetCode = "SOI";

        // At the beginning of your upkeep, you may put the top two cards of your library into your graveyard.
        this.addAbility(new OnEventTriggeredAbility(GameEvent.EventType.UPKEEP_STEP_PRE, "beginning of your upkeep", new PutTopCardOfLibraryIntoGraveControllerEffect(2), true));

        // Whenever one or more land cards are put into your graveyard from anywhere for the first time each turn, put a 1/1 green Insect creature token onto the battlefield.
        this.addAbility(new CrawlingSensationTriggeredAbility());
    }

    public CrawlingSensation(final CrawlingSensation card) {
        super(card);
    }

    @Override
    public CrawlingSensation copy() {
        return new CrawlingSensation(this);
    }
}

class CrawlingSensationTriggeredAbility extends TriggeredAbilityImpl {

    public CrawlingSensationTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new InsectToken()), false);
    }

    public CrawlingSensationTriggeredAbility(final CrawlingSensationTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE_GROUP;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeGroupEvent zEvent = (ZoneChangeGroupEvent) event;
        if (Zone.GRAVEYARD == zEvent.getToZone()) {
            Integer usedOnTurn = (Integer) game.getState().getValue("usedOnTurn" + getOriginalId());
            if (usedOnTurn == null || usedOnTurn < game.getTurnNum()) {
                for (Card card : zEvent.getCards()) {
                    if (card.getOwnerId().equals(getControllerId()) && card.getCardType().contains(CardType.LAND)) {
                        game.getState().setValue("usedOnTurn" + getOriginalId(), game.getTurnNum());
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public CrawlingSensationTriggeredAbility copy() {
        return new CrawlingSensationTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever one or more land cards are put into your graveyard from anywhere for the first time each turn, put a 1/1 green Insect creature token onto the battlefield.";
    }
}
