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
package mage.sets.fatereforged;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.DashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.SetTargetPointer;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author LevelX2
 */
public class KolaghanTheStormsFury extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Dragon you control");
    
    static {
        filter.add(new SubtypePredicate("Dragon"));
        filter.add(new ControllerPredicate(TargetController.YOU));
    }
    
    public KolaghanTheStormsFury(UUID ownerId) {
        super(ownerId, 155, "Kolaghan, the Storm's Fury", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{B}{R}");
        this.expansionSetCode = "FRF";
        this.supertype.add("Legendary");
        this.subtype.add("Dragon");
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Whenever a Dragon you control attacks, creatures you control get +1/+0 until end of turn. 
        this.addAbility(new AttacksAllTriggeredAbility(
                new BoostControlledEffect(1, 0, Duration.EndOfTurn, new FilterCreaturePermanent("creatures you control"), false),
                false, filter, SetTargetPointer.NONE, false));
        
        // Dash {3}{B}{R}
        this.addAbility(new DashAbility(this, "{3}{B}{R}"));
    }

    public KolaghanTheStormsFury(final KolaghanTheStormsFury card) {
        super(card);
    }

    @Override
    public KolaghanTheStormsFury copy() {
        return new KolaghanTheStormsFury(this);
    }
}
