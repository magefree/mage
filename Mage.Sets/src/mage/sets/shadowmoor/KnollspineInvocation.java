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
package mage.sets.shadowmoor;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.filter.FilterCard;
import mage.filter.common.FilterOwnedCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author anonymous
 */
public class KnollspineInvocation extends CardImpl {

    public KnollspineInvocation(UUID ownerId) {
        super(ownerId, 99, "Knollspine Invocation", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}{R}");
        this.expansionSetCode = "SHM";

        // {X}, Discard a card with converted mana cost X: Knollspine Invocation deals X damage to target creature or player.
        Ability ability = new KnollspineInvocationAbility();
        ability.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(ability);
    }

    public KnollspineInvocation(final KnollspineInvocation card) {
        super(card);
    }

    @Override
    public KnollspineInvocation copy() {
        return new KnollspineInvocation(this);
    }
}

class KnollspineInvocationAbility extends SimpleActivatedAbility {
    public KnollspineInvocationAbility() {
        super(Zone.BATTLEFIELD, new KnollspineInvocationEffect(), new ManaCostsImpl("{0}"));
        //If cost gets added by ManaCostsImpl.add from ActivatedAbilityImpl constructor, this will destroy our custom mana cost
        //Here comes the hack
        //A less hacky solution would be to implement this as a ManaCost proper, 
        //using ManaCostImpl (but it would involve duplicating code from ManaCostsImpl
        //and, of course, it would deny access to ManaCostsImpl's API)
        this.manaCosts = new KnollspineInvocationAbilityCost("{X}");
        this.manaCostsToPay = this.manaCosts.copy();
    }
}

class KnollspineInvocationEffect extends OneShotEffect {
    //See DrainLife for this class' implementation

    public KnollspineInvocationEffect() {
        super(Outcome.Damage);
        staticText = "Knollspine Invocation deals X damage to target creature or player";
    }

    public KnollspineInvocationEffect(final KnollspineInvocationEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = source.getManaCostsToPay().getX();
        if (amount > 0) {            
            Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (permanent != null ) {
                permanent.damage(amount, source.getSourceId(), game, false, true);
            } else {
                Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
                if (player != null) {
                    player.damage(amount, source.getSourceId(), game, false, true);
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public KnollspineInvocationEffect copy() {
        return new KnollspineInvocationEffect(this);
    }
}

class KnollspineInvocationAbilityCost extends ManaCostsImpl {
    protected DiscardCardCost additional;
    
    public KnollspineInvocationAbilityCost(final KnollspineInvocationAbilityCost cost) {
        super(cost);
        this.additional = cost.additional;
    }
    
    public KnollspineInvocationAbilityCost (String s) {
        super(s);
        //we can only apply the additional discard cost AFTER we resolve this one!
        FilterCard filter = new FilterOwnedCard("a card with converted mana cost " + super.getText());
        filter.add(new ConvertedManaCostPredicate(Filter.ComparisonType.Equal, super.getX()));
        additional = new DiscardCardCost(filter);
    }
    
    @Override
    public KnollspineInvocationAbilityCost copy() {
        return new KnollspineInvocationAbilityCost(this);
    }
    
    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        if (super.pay(ability, game, sourceId, controllerId, noMana)) {
            //Now we get the real deal
            FilterCard filter = new FilterOwnedCard("a card with converted mana cost " + this.getX());
            filter.add(new ConvertedManaCostPredicate(Filter.ComparisonType.Equal, this.getX()));
            return new DiscardCardCost(filter).pay(ability, game, sourceId, controllerId, noMana);
        }
        return false;
    }
    
    @Override
    public String getText() {
        StringBuilder sb = new StringBuilder(super.getText());
        sb.append(", ");
        sb.append(additional.getText());
        System.out.println("Knollspine Invocation tooltip: " + sb.toString());
        return sb.toString();
    }
}