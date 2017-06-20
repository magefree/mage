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
package mage.cards.e;

import mage.MageInt;
import mage.constants.ComparisonType;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class ErebossTitan extends CardImpl {

    private final static FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public ErebossTitan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{B}{B}");
        this.subtype.add("Giant");
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Erebos's Titan has indestructible as long as no opponent controls a creature.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.WhileOnBattlefield),
                        new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.EQUAL_TO, 0, false),
                        "As long as your opponents control no creatures, {this} has indestructible")));

        // Whenever a creature leaves an opponent's graveyard, you may discard a card. If you do, return Erebos's Titan from your graveyard to your hand.
        this.addAbility(new ErebossTitanTriggeredAbility());

    }

    public ErebossTitan(final ErebossTitan card) {
        super(card);
    }

    @Override
    public ErebossTitan copy() {
        return new ErebossTitan(this);
    }
}

class ErebossTitanTriggeredAbility extends TriggeredAbilityImpl {

    public ErebossTitanTriggeredAbility() {
        super(Zone.GRAVEYARD, new DoIfCostPaid(new ReturnSourceFromGraveyardToHandEffect(), new DiscardCardCost()));
    }

    public ErebossTitanTriggeredAbility(final ErebossTitanTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ErebossTitanTriggeredAbility copy() {
        return new ErebossTitanTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getFromZone() == Zone.GRAVEYARD) {
            Card card = game.getCard(zEvent.getTargetId());
            Player controller = game.getPlayer(getControllerId());
            return card != null
                    && card.isCreature()
                    && controller != null
                    && controller.hasOpponent(card.getOwnerId(), game);
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature leaves an opponent's graveyard, " + super.getRule();
    }
}
