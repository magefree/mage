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
package mage.sets.returntoravnica;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.DetainTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author LevelX2
 */
public class ArchonOfTheTriumvirate extends CardImpl<ArchonOfTheTriumvirate> {

    private static final FilterNonlandPermanent filter = new FilterNonlandPermanent("nonland permanents your opponents control");
 
    static {
        filter.add(new ControllerPredicate(Constants.TargetController.OPPONENT));
    }
    
    public ArchonOfTheTriumvirate(UUID ownerId) {
        super(ownerId, 142, "Archon of the Triumvirate", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{5}{W}{U}");
        this.expansionSetCode = "RTR";
        this.subtype.add("Archon");

        this.color.setBlue(true);
        this.color.setWhite(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Whenever Archon of the Triumvirate attacks, detain up to two target nonland permanents your opponents control.
        // (Until your next turn, those permanents can't attack or block and their activated abilities can't be activated.)
        Ability ability = new AttacksTriggeredAbility(new DetainTargetEffect(), false);
        ability.addTarget(new TargetNonlandPermanent(0,2,filter, false));
        this.addAbility(ability);
    }

    public ArchonOfTheTriumvirate(final ArchonOfTheTriumvirate card) {
        super(card);
    }

    @Override
    public ArchonOfTheTriumvirate copy() {
        return new ArchonOfTheTriumvirate(this);
    }
}
