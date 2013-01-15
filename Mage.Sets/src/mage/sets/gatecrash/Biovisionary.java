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
package mage.sets.gatecrash;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.common.ControlsPermanentCondition;
import mage.abilities.effects.common.WinGameEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;

/**
 *
 * @author Plopman
 */
public class Biovisionary extends CardImpl<Biovisionary> {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("if you control four or more creatures named Biovisionary");
    static{
        filter.add(new NamePredicate("Biovisionary"));
    }
           
    public Biovisionary(UUID ownerId) {
        super(ownerId, 146, "Biovisionary", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{G}{U}");
        this.expansionSetCode = "GTC";
        this.subtype.add("Human");
        this.subtype.add("Wizard");
        

        this.color.setGreen(true);
        this.color.setBlue(true);
        
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        //At the beginning of the end step, if you control four or more creatures named Biovisionary, you win the game.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(Constants.Zone.BATTLEFIELD, new WinGameEffect(), Constants.TargetController.ANY, new ControlsPermanentCondition(filter, ControlsPermanentCondition.CountType.MORE_THAN, 3), false));
    }

    public Biovisionary(final Biovisionary card) {
        super(card);
    }

    @Override
    public Biovisionary copy() {
        return new Biovisionary(this);
    }
}

