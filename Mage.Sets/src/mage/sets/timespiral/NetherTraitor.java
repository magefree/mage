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
package mage.sets.timespiral;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.ShadowAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;

/**
 *
 * @author emerald000
 */
public class NetherTraitor extends CardImpl {

    public NetherTraitor(UUID ownerId) {
        super(ownerId, 120, "Nether Traitor", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{B}{B}");
        this.expansionSetCode = "TSP";
        this.subtype.add("Spirit");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        
        // Shadow
        this.addAbility(ShadowAbility.getInstance());
        
        // Whenever another creature is put into your graveyard from the battlefield, you may pay {B}. If you do, return Nether Traitor from your graveyard to the battlefield.
        this.addAbility(new NetherTraitorTriggeredAbility());
    }

    public NetherTraitor(final NetherTraitor card) {
        super(card);
    }

    @Override
    public NetherTraitor copy() {
        return new NetherTraitor(this);
    }
}

class NetherTraitorTriggeredAbility extends TriggeredAbilityImpl {
    
    NetherTraitorTriggeredAbility(){
        super(Zone.GRAVEYARD, new DoIfCostPaid(new ReturnSourceFromGraveyardToBattlefieldEffect(), new ColoredManaCost(ColoredManaSymbol.B)));
    }
    
    NetherTraitorTriggeredAbility(final NetherTraitorTriggeredAbility ability) {
        super(ability);
    }
    
    @Override
    public NetherTraitorTriggeredAbility copy(){
        return new NetherTraitorTriggeredAbility(this);
    }
    
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getFromZone() == Zone.BATTLEFIELD && zEvent.getToZone() == Zone.GRAVEYARD) {
                Card card = game.getCard(event.getTargetId());
                if (card != null &&
                        card.getOwnerId().equals(this.getControllerId()) &&
                        card.getCardType().contains(CardType.CREATURE)&&
                        !card.getId().equals(this.getSourceId())) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public String getRule() {
        return "Whenever another creature is put into your graveyard from the battlefield, you may pay {B}. If you do, return {this} from your graveyard to the battlefield.";
    }
}
