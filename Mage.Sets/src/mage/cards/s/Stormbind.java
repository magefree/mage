
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LoneFox

 */
public final class Stormbind extends CardImpl {

    public Stormbind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{R}{G}");

        // {2}, Discard a card at random: Stormbind deals 2 damage to any target.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(2), new ManaCostsImpl<>("{2}"));
        ability.addCost(new DiscardCardCost(true));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private Stormbind(final Stormbind card) {
        super(card);
    }

    @Override
    public Stormbind copy() {
        return new Stormbind(this);
    }
}
