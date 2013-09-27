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
package mage.sets.odyssey;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;

/**
 *
 * @author cbt33
 */
public class ThinkTank extends CardImpl<ThinkTank> {

    public ThinkTank(UUID ownerId) {
        super(ownerId, 104, "Think Tank", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");
        this.expansionSetCode = "ODY";

        this.color.setBlue(true);

        // At the beginning of your upkeep, look at the top card of your library. You may put that card into your graveyard.
       //Ability ability = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new LookLibraryControllerEffect(1), TargetController.YOU, false);
        Ability ability = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new LookLibraryAndPickControllerEffect(new StaticValue(1), false, new StaticValue(1), new FilterCard(), Zone.GRAVEYARD, false, false), TargetController.YOU, false);
        this.addAbility(ability);

       // Ability ability2 = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new PutTopCardOfYourLibraryIntoGraveEffect(1), TargetController.YOU, true);
        //this.addAbility(ability2);
    }

    public ThinkTank(final ThinkTank card) {
        super(card);
    }

    @Override
    public ThinkTank copy() {
        return new ThinkTank(this);
    }
}
