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
package mage.sets.betrayersofkamigawa;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.TargetController;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.common.RemoveCounterTargetCost;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.SoulshiftAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterPermanentOrPlayerWithCounter;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.common.TargetPermanentOrPlayerWithCounter;

/**
 *
 * @author LevelX2
 */
public class ChiseiHeartOfOceans extends CardImpl<ChiseiHeartOfOceans> {

    private final static  FilterPermanentOrPlayerWithCounter filter = new FilterPermanentOrPlayerWithCounter("remove a counter from a permanent you control");
    
    static {
        filter.add(new ControllerPredicate(Constants.TargetController.YOU));
    }       
    
    public ChiseiHeartOfOceans(UUID ownerId) {
        super(ownerId, 32, "Chisei, Heart of Oceans", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
        this.expansionSetCode = "BOK";
        this.supertype.add("Legendary");
        this.subtype.add("Spirit");

        this.color.setBlue(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // At the beginning of your upkeep, sacrifice Chisei, Heart of Oceans unless you remove a counter from a permanent you control.
        TargetPermanentOrPlayerWithCounter target = new TargetPermanentOrPlayerWithCounter(1);
        target.setFilter(filter);
        target.setTargetName("a permanent you control");
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new RemoveCounterTargetCost(target)),TargetController.YOU, false));

    }

    public ChiseiHeartOfOceans(final ChiseiHeartOfOceans card) {
        super(card);
    }

    @Override
    public ChiseiHeartOfOceans copy() {
        return new ChiseiHeartOfOceans(this);
    }
}
