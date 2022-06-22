
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LoneFox

 */
public final class MeteorStorm extends CardImpl {

    public MeteorStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{R}{G}");

        // {2}{R}{G}, Discard two cards at random: Meteor Storm deals 4 damage to any target.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(4), new ManaCostsImpl<>("{2}{R}{G}"));
        ability.addCost(new DiscardTargetCost(new TargetCardInHand(2, new FilterCard("two cards at random")), true));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private MeteorStorm(final MeteorStorm card) {
        super(card);
    }

    @Override
    public MeteorStorm copy() {
        return new MeteorStorm(this);
    }
}
