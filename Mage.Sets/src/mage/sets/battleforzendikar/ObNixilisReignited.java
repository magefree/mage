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
package mage.sets.battleforzendikar;

import java.util.UUID;
import mage.abilities.LoyaltyAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GetEmblemTargetPlayerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author fireshoes
 */
public class ObNixilisReignited extends CardImpl {

    public ObNixilisReignited(UUID ownerId) {
        super(ownerId, 119, "Ob Nixilis Reignited", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{3}{B}{B}");
        this.expansionSetCode = "BFZ";
        this.subtype.add("Nixilis");

        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(5)), false));   
        
        // +1: You draw a card and you lose 1 life.
        LoyaltyAbility ability1 = new LoyaltyAbility(new DrawCardSourceControllerEffect(1), 1);
        ability1.addEffect(new LoseLifeSourceControllerEffect(1));
        this.addAbility(ability1);
        
        // -3: Destroy target creature.
        LoyaltyAbility ability2 = new LoyaltyAbility(new DestroyTargetEffect(), -3);
        ability2.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability2);
        
        // -8: Target opponent gets an emblem with "Whenever a player draws a card, you lose 2 life."
        Effect effect = new GetEmblemTargetPlayerEffect(new ObNixilisReignitedEmblem());
        effect.setText("Target opponent gets an emblem with \"Whenever a player draws a card, you lose 2 life.\"");
        LoyaltyAbility ability3 = new LoyaltyAbility(effect, -8);
        ability3.addTarget(new TargetOpponent());
        this.addAbility(ability3);
    }

    public ObNixilisReignited(final ObNixilisReignited card) {
        super(card);
    }

    @Override
    public ObNixilisReignited copy() {
        return new ObNixilisReignited(this);
    }
}

class ObNixilisReignitedEmblem extends Emblem {

    public ObNixilisReignitedEmblem() {
        setName("EMBLEM: Ob Nixilis Reignited");
        
        this.getAbilities().add(new ObNixilisEmblemTriggeredAbility(new LoseLifeSourceControllerEffect(2), false));
    }
}

class ObNixilisEmblemTriggeredAbility  extends TriggeredAbilityImpl {

    public ObNixilisEmblemTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.COMMAND, effect, optional);
    }

    public ObNixilisEmblemTriggeredAbility(final ObNixilisEmblemTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DREW_CARD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId() != null;
    }

    @Override
    public String getRule() {
        return "Whenever a player draws a card, you lose 2 life.";
    }

    @Override
    public ObNixilisEmblemTriggeredAbility copy() {
        return new ObNixilisEmblemTriggeredAbility(this);
    }
}