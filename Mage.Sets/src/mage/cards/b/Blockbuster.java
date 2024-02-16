
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;

/**
 *
 * @author fireshoes
 */
public final class Blockbuster extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("tapped creature");

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    public Blockbuster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{R}{R}");

        // {1}{R}, Sacrifice Blockbuster: Blockbuster deals 3 damage to each tapped creature and each player.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageEverythingEffect(3, filter), new ManaCostsImpl<>("{1}{R}"));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private Blockbuster(final Blockbuster card) {
        super(card);
    }

    @Override
    public Blockbuster copy() {
        return new Blockbuster(this);
    }
}
