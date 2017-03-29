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
package mage.cards.d;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CastFromHandSourceCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.TapAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.watchers.common.CastFromHandWatcher;

import java.util.UUID;

/**
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class DreadCacodemon extends CardImpl {
    
    private static final FilterCreaturePermanent opponentsCreatures = new FilterCreaturePermanent("creatures your opponents control");
    static {
        opponentsCreatures.add(new ControllerPredicate(TargetController.OPPONENT));
    }
   
    private static final FilterCreaturePermanent otherCreaturesYouControl = new FilterCreaturePermanent("other creatures you control");
    static {
        otherCreaturesYouControl.add(new ControllerPredicate(TargetController.YOU));
        otherCreaturesYouControl.add(new AnotherPredicate());
    }
	
    public DreadCacodemon(UUID ownerId, CardSetInfo setInfo) {        
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{7}{B}{B}{B}");
        this.subtype.add("Demon");
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);
        
        // When Dread Cacodemon enters the battlefield, 
        // if you cast it from your hand, destroy all creatures your opponents control, then tap all other creatures you control. 
        TriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new DestroyAllEffect(opponentsCreatures, false));
        ability.addEffect(new TapAllEffect(otherCreaturesYouControl));
        this.addAbility(new ConditionalTriggeredAbility(ability, new CastFromHandSourceCondition(),
                "When {this} enters the battlefield, if you cast it from your hand, destroy all creatures your opponents control, then tap all other creatures you control."), new CastFromHandWatcher());
    }

    public DreadCacodemon(final DreadCacodemon card) {
        super(card);
    }

    @Override
    public DreadCacodemon copy() {
        return new DreadCacodemon(this);
    }
}