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
package mage.sets.dragonsoftarkir;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.decorator.ConditionalContinuousRuleModifyingEffect;
import mage.abilities.effects.ContinuousRuleModifyingEffect;
import mage.abilities.effects.common.CantBeCounteredSourceEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author jeffwadsworth
 */
public class DragonlordsPrerogative extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Dragon card from your hand");

    static {
        filter.add(new SubtypePredicate("Dragon"));
    }

    public DragonlordsPrerogative(UUID ownerId) {
        super(ownerId, 52, "Dragonlord's Prerogative", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{4}{U}{U}");
        this.expansionSetCode = "DTK";

        // As an additional cost to cast Dragonlord's Prerogative, you may reveal a Dragon card from your hand.
        this.getSpellAbility().addEffect(new InfoEffect("As an additional cost to cast {this}, you may reveal a Dragon card from your hand"));
        
        // If you revealed a Dragon card or controlled a Dragon as you cast Dragonlord's Prerogative, Dragonlord's Prerogative can't be countered.        
        Condition condition = new DragonlordsPrerogativeCondition();
        ContinuousRuleModifyingEffect cantBeCountered = new CantBeCounteredSourceEffect();
        ConditionalContinuousRuleModifyingEffect conditionalCantBeCountered = new ConditionalContinuousRuleModifyingEffect(cantBeCountered, condition);
        conditionalCantBeCountered.setText("<br/>If you revealed a Dragon card or controlled a Dragon as you cast {this}, {this} can't be countered");
        Ability ability = new SimpleStaticAbility(Zone.STACK, conditionalCantBeCountered);
        this.addAbility(ability);
        
        // Draw four cards.        
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(4));

    }
    
    @Override
    public void adjustCosts(Ability ability, Game game) {
        Player controller = game.getPlayer(ability.getControllerId());
        if (controller != null) {
            if (controller.getHand().count(filter, game) > 0) {
                ability.addCost(new RevealTargetFromHandCost(new TargetCardInHand(0,1, filter)));
            }
        }
    }
    
    public DragonlordsPrerogative(final DragonlordsPrerogative card) {
        super(card);
    }

    @Override
    public DragonlordsPrerogative copy() {
        return new DragonlordsPrerogative(this);
    }
}

class DragonlordsPrerogativeCondition implements Condition {

    private final static FilterControlledPermanent filter = new FilterControlledPermanent("Dragon");

    static {
        filter.add(new SubtypePredicate("Dragon"));
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean applies = false;
        Spell spell = game.getStack().getSpell(source.getSourceId());
        if (spell != null && spell.getSpellAbility() != null) {
            for(Cost cost: spell.getSpellAbility().getCosts()) {
                if (cost instanceof RevealTargetFromHandCost) {
                    applies = !((RevealTargetFromHandCost)cost).getTargets().isEmpty();
                    break;
                }
            }
        }
        if (!applies) {
            applies = game.getBattlefield().countAll(filter, source.getControllerId(), game) > 0;
        }
        return applies;
    }
}
