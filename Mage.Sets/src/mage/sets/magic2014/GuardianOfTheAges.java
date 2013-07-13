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
package mage.sets.magic2014;

import java.util.UUID;
import mage.cards.CardImpl;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.effects.common.continious.GainAbilitySourceEffect;
import mage.abilities.effects.common.continious.LoseAbilitySourceEffect;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author Noahsark
 */
public class GuardianOfTheAges extends CardImpl<GuardianOfTheAges>{
    public GuardianOfTheAges(UUID ownerId){
        super(ownerId, 211, "Guardian of the Ages", Rarity.RARE, new CardType[]{CardType.CREATURE, CardType.ARTIFACT}, "{7}");
        this.expansionSetCode = "M14";
        this.subtype.add("Golem");
        
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);
        
        this.addAbility(DefenderAbility.getInstance());
        //Whenever a creature attacks you or a planeswalker you control, if Guardian of the Ages has defender, it loses defender and gains trample.
        this.addAbility(new GuardianOfTheAgesTriggerAbility());
    }
    
    public GuardianOfTheAges(final GuardianOfTheAges card){
        super(card);
    }
    
    @Override
    public GuardianOfTheAges copy(){
        return new GuardianOfTheAges(this);
    }
}

class GuardianOfTheAgesTriggerAbility extends TriggeredAbilityImpl<GuardianOfTheAgesTriggerAbility> {
    
    private static final FilterCard filter = new FilterCard("creature");
    static{
        filter.add(Predicates.and(new CardTypePredicate(CardType.CREATURE)));
    }
    public GuardianOfTheAgesTriggerAbility(){
        super(Zone.BATTLEFIELD, new GainAbilitySourceEffect(TrampleAbility.getInstance()));
        this.addEffect(new LoseAbilitySourceEffect(DefenderAbility.getInstance()));
    }
    public GuardianOfTheAgesTriggerAbility(final GuardianOfTheAgesTriggerAbility ability){
        super(ability);
    }
    
    @Override
    public boolean checkTrigger(GameEvent event, Game game){
        if(event.getType() == GameEvent.EventType.ATTACKER_DECLARED){
            Permanent creature = game.getPermanent(event.getSourceId());
           if(creature != null && filter.match(creature, game)
                   && game.getOpponents(this.getControllerId()).contains(creature.getControllerId())
                   && game.getPermanent(this.getSourceId()).getAbilities().contains(DefenderAbility.getInstance())) 
            return true;
        }
        return false;
    }
    
    @Override
    public String getRule(){
        return "Whenever a creature attacks you or a planeswalker you control, if {this} has defender, it loses defender and gains trample.";
    }
    
    @Override
    public GuardianOfTheAgesTriggerAbility copy(){
        return new GuardianOfTheAgesTriggerAbility(this);
    }
}
