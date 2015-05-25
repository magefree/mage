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
package mage.sets.alliances;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.common.SacrificeAllCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.EnterBattlefieldPayCostOrPutGraveyardEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.NamePredicate;

/**
 *
 * @author anonymous
 */
public class ShelteredValley extends CardImpl {
    
    private static final FilterLandPermanent filter = new FilterLandPermanent();
    private static final FilterPermanent filterShelteredValley = new FilterPermanent("permanent named Sheltered Valley");
    
    static {
        filterShelteredValley.add(new NamePredicate("Sheltered Valley"));
    }

    public ShelteredValley(UUID ownerId) {
        super(ownerId, 187, "Sheltered Valley", Rarity.RARE, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "ALL";

        // If Sheltered Valley would enter the battlefield, instead sacrifice each other permanent named Sheltered Valley you control, then put Sheltered Valley onto the battlefield.
        Effect effect = new EnterBattlefieldPayCostOrPutGraveyardEffect(new SacrificeAllCost(filterShelteredValley));
        effect.setText("If {this} would enter the battlefield, instead sacrifice each other permanent named {this} you control, then put {this} onto the battlefield.");
        Ability ability = new SimpleStaticAbility(Zone.ALL, effect);
        this.addAbility(ability);
        
        // At the beginning of your upkeep, if you control three or fewer lands, you gain 1 life.
        Condition controls = new PermanentsOnTheBattlefieldCondition(filter, PermanentsOnTheBattlefieldCondition.CountType.FEWER_THAN, 4);
        effect = new ConditionalOneShotEffect(new GainLifeEffect(1), controls);
        effect.setText("if you control three or fewer lands, you gain 1 life");
        ability = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, effect, TargetController.YOU, false);
        this.addAbility(ability);
        // {tap}: Add {1} to your mana pool.
        this.addAbility(new ColorlessManaAbility());
    }

    public ShelteredValley(final ShelteredValley card) {
        super(card);
    }

    @Override
    public ShelteredValley copy() {
        return new ShelteredValley(this);
    }
}
