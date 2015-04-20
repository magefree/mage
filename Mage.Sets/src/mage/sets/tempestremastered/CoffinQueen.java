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
package mage.sets.tempestremastered;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SkipUntapOptionalAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInGraveyard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author anonymous
 */
public class CoffinQueen extends CardImpl {

    public CoffinQueen(UUID ownerId) {
        super(ownerId, 87, "Coffin Queen", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.expansionSetCode = "TPR";
        this.subtype.add("Zombie");
        this.subtype.add("Wizard");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // You may choose not to untap Coffin Queen during your untap step.
        this.addAbility(new SkipUntapOptionalAbility());
        
        // {2}{B}, {tap}: Put target creature card from a graveyard onto the battlefield under your control. When Coffin Queen becomes untapped or you lose control of Coffin Queen, exile that creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnFromGraveyardToBattlefieldTargetEffect(), new ManaCostsImpl("{2}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCardInGraveyard(new FilterCreatureCard("creature card from a graveyard")));
        ability.addEffect(new CoffinQueenCreateDelayedTriggerEffect());        
        this.addAbility(ability);
        
    }

    public CoffinQueen(final CoffinQueen card) {
        super(card);
    }

    @Override
    public CoffinQueen copy() {
        return new CoffinQueen(this);
    }
}
class CoffinQueenCreateDelayedTriggerEffect extends OneShotEffect {
    
    public CoffinQueenCreateDelayedTriggerEffect() {
        super(Outcome.Detriment);
        this.staticText = "When Coffin Queen becomes untapped or you lose control of Coffin Queen, exile that creature";
    }
    
    public CoffinQueenCreateDelayedTriggerEffect(final CoffinQueenCreateDelayedTriggerEffect effect) {
        super(effect);
    }
    
    @Override
    public CoffinQueenCreateDelayedTriggerEffect copy() {
        return new CoffinQueenCreateDelayedTriggerEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Permanent controlledCreature = game.getPermanent(source.getFirstTarget());
        if (controlledCreature != null) {
            DelayedTriggeredAbility delayedAbility = new CoffinQueenDelayedTriggeredAbility();
            delayedAbility.getEffects().get(0).setTargetPointer(new FixedTarget(controlledCreature.getId()));
            delayedAbility.setSourceId(source.getSourceId());
            delayedAbility.setControllerId(source.getControllerId());
            delayedAbility.setSourceObject(source.getSourceObject(game), game);
            delayedAbility.init(game);            
            game.addDelayedTriggeredAbility(delayedAbility);
            return true;
        }
        return false;
    }
}

class CoffinQueenDelayedTriggeredAbility extends DelayedTriggeredAbility {

    CoffinQueenDelayedTriggeredAbility() {
        super(new ExileTargetEffect(), Duration.EndOfGame, true); 
    }

    CoffinQueenDelayedTriggeredAbility(CoffinQueenDelayedTriggeredAbility ability) {
        super(ability);
    }

    
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (GameEvent.EventType.LOST_CONTROL.equals(event.getType())
                && event.getSourceId().equals(getSourceId())) {
            return true;
        }
        return GameEvent.EventType.UNTAPPED.equals(event.getType()) 
                && event.getTargetId() != null && event.getTargetId().equals(getSourceId());
    }
    
    @Override
    public CoffinQueenDelayedTriggeredAbility copy() {
        return new CoffinQueenDelayedTriggeredAbility(this);
    }
    
    @Override
    public String getRule() {
        return "When {this} becomes untapped or you lose control of {this}, exile that creature";
    }
}
