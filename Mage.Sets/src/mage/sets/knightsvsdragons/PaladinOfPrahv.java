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
package mage.sets.knightsvsdragons;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.DealsDamageGainLifeSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.keyword.ForecastAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author FenrisulfrX
 */
public class PaladinOfPrahv extends CardImpl {

    public PaladinOfPrahv(UUID ownerId) {
        super(ownerId, 22, "Paladin of Prahv", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");
        this.expansionSetCode = "DDG";
        this.subtype.add("Human");
        this.subtype.add("Knight");
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever Paladin of Prahv deals damage, you gain that much life.
        this.addAbility(new DealsDamageGainLifeSourceTriggeredAbility());
        
        // Forecast - {1}{W}, Reveal Paladin of Prahv from your hand: Whenever target creature deals damage this turn, you gain that much life.
        Ability ability = new ForecastAbility(new CreateDelayedTriggeredAbilityEffect(
                new PaladinOfPrahvTriggeredAbility()), new ManaCostsImpl("{1}{W}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public PaladinOfPrahv(final PaladinOfPrahv card) {
        super(card);
    }

    @Override
    public PaladinOfPrahv copy() {
        return new PaladinOfPrahv(this);
    }
}

class PaladinOfPrahvTriggeredAbility extends DelayedTriggeredAbility {
    
    public PaladinOfPrahvTriggeredAbility() {
        super(new PaladinOfPrahvEffect(), Duration.EndOfTurn, false);
    }
    
    public PaladinOfPrahvTriggeredAbility(final PaladinOfPrahvTriggeredAbility ability) {
        super(ability);
    }
    

    @Override
    public PaladinOfPrahvTriggeredAbility copy() {
        return new PaladinOfPrahvTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        switch(event.getType()) {
            case DAMAGED_CREATURE:
            case DAMAGED_PLAYER:
            case DAMAGED_PLANESWALKER:
                return true;
        }
        return false;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent target = game.getPermanent(this.getFirstTarget());
        if (target != null && event.getSourceId().equals(target.getId())) {
            for (Effect effect : this.getEffects()) {
                effect.setValue("damage", event.getAmount());
            }
            return true;
        }
        return false;
    }
    
    @Override
    public String getRule() {
        return "Whenever target creature deals damage this turn, " + super.getRule();
    }
}

class PaladinOfPrahvEffect extends OneShotEffect {
    
    public PaladinOfPrahvEffect() {
        super(Outcome.GainLife);
        this.staticText = "you gain that much life";
    }
    
    public PaladinOfPrahvEffect(final PaladinOfPrahvEffect effect) {
        super(effect);
    }
    
    @Override
    public PaladinOfPrahvEffect copy() {
        return new PaladinOfPrahvEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int amount = (Integer) getValue("damage");
            if (amount > 0) {
                controller.gainLife(amount, game);
                return true;
            }
            return true;
        }
        return false;
    }
}
