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
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class DragonlordKolaghan extends CardImpl {

    public DragonlordKolaghan(UUID ownerId) {
        super(ownerId, 218, "Dragonlord Kolaghan", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{4}{B}{R}");
        this.expansionSetCode = "DTK";
        this.supertype.add("Legendary");
        this.subtype.add("Elder");
        this.subtype.add("Dragon");        
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);
        
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Haste
        this.addAbility(HasteAbility.getInstance());
        
        // Other creatures you control have haste.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, 
                new GainAbilityControlledEffect(HasteAbility.getInstance(), Duration.WhileOnBattlefield, new FilterControlledCreaturePermanent(), true)));
        
        // Whenever an opponent casts a creature or planeswalker spell with the same name as a card in their graveyard, that player loses 10 life.
        Effect effect = new LoseLifeTargetEffect(10);
        effect.setText("that player loses 10 life");
        this.addAbility(new DragonlordKolaghanTriggeredAbility(effect));       

    }

    public DragonlordKolaghan(final DragonlordKolaghan card) {
        super(card);
    }

    @Override
    public DragonlordKolaghan copy() {
        return new DragonlordKolaghan(this);
    }
}

class DragonlordKolaghanTriggeredAbility extends TriggeredAbilityImpl {

    public DragonlordKolaghanTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
    }

    public DragonlordKolaghanTriggeredAbility(final DragonlordKolaghanTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DragonlordKolaghanTriggeredAbility copy() {
        return new DragonlordKolaghanTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }
    
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player controller = game.getPlayer(getControllerId());
        if (controller != null && controller.hasOpponent(event.getPlayerId(), game)) {
            Card card = game.getCard(event.getSourceId());
            if (card != null && (card.getCardType().contains(CardType.CREATURE) || card.getCardType().contains(CardType.PLANESWALKER))) {
                Player opponent = game.getPlayer(event.getPlayerId());
                boolean sameName = false;
                for (Card graveCard :opponent.getGraveyard().getCards(game)) {
                    if (graveCard.getName().equals(card.getName())) {
                        sameName = true;
                        break;
                    }
                }
                if (sameName) {
                    this.getEffects().get(0).setTargetPointer(new FixedTarget(event.getPlayerId()));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent casts a creature or planeswalker spell with the same name as a card in their graveyard, " + super.getRule();
    }
}