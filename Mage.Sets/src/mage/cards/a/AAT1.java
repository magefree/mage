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
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.RepairAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetPlayer;

/**
 *
 * @author Styxo
 */
public class AAT1 extends CardImpl {

    public AAT1(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{1}{W}{U}{B}");
        this.subtype.add("Droid");
        this.subtype.add("Construct");
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever a repair counter is removed from a creature card your graveyard, you may pay {1}. If you do, target player loses 1 life and you gain 1 life. 
        DoIfCostPaid effect = new DoIfCostPaid(new LoseLifeTargetEffect(1), new GenericManaCost(1));
        Effect additionalEffect = new GainLifeEffect(1);
        additionalEffect.setText("and you gain 1 life");
        effect.addEffect(additionalEffect);
        Ability ability = new AAT1TriggeredAbility(effect);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // Repair 4
        this.addAbility(new RepairAbility(4));
    }

    public AAT1(final AAT1 card) {
        super(card);
    }

    @Override
    public AAT1 copy() {
        return new AAT1(this);
    }
}

class AAT1TriggeredAbility extends TriggeredAbilityImpl {

    public AAT1TriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
    }

    public AAT1TriggeredAbility(AAT1TriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTER_REMOVED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Card card = game.getCard(event.getTargetId());
        if (event.getPlayerId().equals(game.getControllerId(sourceId))
                && card.getCardType().contains(CardType.CREATURE)
                && game.getState().getZone(card.getId()) == Zone.GRAVEYARD
                && event.getData().equals("repair")) {
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a repair counter is removed from a creature card in your graveyard " + super.getRule();
    }

    @Override
    public AAT1TriggeredAbility copy() {
        return new AAT1TriggeredAbility(this);
    }
}
