

package mage.abilities.abilityword;

import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.AnotherCardPredicate;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author emerald000
 */

public class GrandeurAbility extends ActivatedAbilityImpl {
    
    protected final String cardName;

    public GrandeurAbility(Effect effect, String cardName) {
        super(Zone.BATTLEFIELD, effect);
        this.cardName = cardName;
        
        FilterCard filter = new FilterCard("another card named " + cardName);
        filter.add(new NamePredicate(cardName));
        filter.add(new AnotherCardPredicate());
        this.addCost(new DiscardTargetCost(new TargetCardInHand(filter)));
    }

    public GrandeurAbility(final GrandeurAbility ability) {
        super(ability);
        this.cardName = ability.cardName;
    }

    @Override
    public GrandeurAbility copy() {
        return new GrandeurAbility(this);
    }
    
    @Override
    public String getRule() {
        return new StringBuilder("<i>Grandeur</i> &mdash; ").append(super.getRule()).toString();
    }
}
